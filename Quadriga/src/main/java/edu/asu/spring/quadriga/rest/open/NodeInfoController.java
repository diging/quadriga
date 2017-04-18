package edu.asu.spring.quadriga.rest.open;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.enums.ETextAccessibility;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.domain.impl.TextOccurance;
import edu.asu.spring.quadriga.service.network.domain.impl.TextPhrase;

@PropertySource(value = "classpath:/settings.properties")
@Controller
public class NodeInfoController {

    @Autowired
    private INetworkManager networkManager;

    @Autowired
    private Environment env;

    @RequestMapping(value = "public/concept/texts", method = RequestMethod.GET)
    public ResponseEntity<String> getTextsForConcepts(@RequestParam String conceptId, @RequestParam String projectUnix)
            throws Exception {
        Set<TextOccurance> occurances = networkManager.getTextsForConceptId(conceptId, ETextAccessibility.PUBLIC);
        JSONArray projectTexts = new JSONArray();
        JSONArray otherProjectsTexts = new JSONArray();

        for (TextOccurance occur : occurances) {
            if ((occur.getProject().getUnixName().equals(projectUnix.trim()) || projectUnix.trim().isEmpty())
                    && occur.getProject().getProjectAccess() == EProjectAccessibility.PUBLIC) {
                JSONObject occurance = new JSONObject();
                jsonKeyValueAppend(occurance, occur);
                JSONArray phraseArray = new JSONArray();

                List<TextPhrase> phrases = occur.getTextPhrases();
                Collections.sort(phrases, new Comparator<TextPhrase>() {

                    @Override
                    public int compare(TextPhrase o1, TextPhrase o2) {
                        return o1.getPosition() - o2.getPosition();
                    }
                });
                Integer offset = new Integer(env.getProperty("network.display.text.offset"));

                int start = 0;
                int end = 0;
                if (phrases.size() > 0) {
                    start = phrases.get(0).getPosition();
                    end = phrases.get(phrases.size() - 1).getPosition();

                    if (start < offset) {
                        start = 0;
                    } else {
                        start = start - offset;
                    }

                    if (occur.getContents().length() < end + offset) {
                        end = occur.getContents().length() - 1;
                    } else {
                        end = end + offset;
                    }

                    String cutContent = occur.getContents().substring(start, end);
                    occurance.append("textContent", cutContent);

                }
                for (TextPhrase phrase : phrases) {
                    if (phrase.getFormattedPointer() == null || phrase.getFormattedPointer().trim().isEmpty()) {
                        JSONObject jsonPhrase = new JSONObject();
                        int position = phrase.getPosition();
                        if (position > start) {
                            position -= start;
                        }
                        jsonPhrase.put("position", position);
                        jsonPhrase.put("expression", phrase.getExpression());
                        phraseArray.put(jsonPhrase);
                    }
                }
                occurance.put("phrases", phraseArray);
                projectTexts.put(occurance);
            } else {
                if (occur.getProject().getProjectAccess() == EProjectAccessibility.PUBLIC) {
                    JSONObject occurance = new JSONObject();                    
                    jsonKeyValueAppend(occurance, occur);
                    occurance.append("projectUnix", occur.getProject().getUnixName());
                    occurance.append("projectName", occur.getProject().getProjectName());
                    otherProjectsTexts.put(occurance);
                }
            }

        }

        JSONObject result = new JSONObject();
        result.put("projects", projectTexts);
        result.put("otherProjects", otherProjectsTexts);
        return new ResponseEntity<String>(result.toString(), HttpStatus.OK);

    }

    public void jsonKeyValueAppend(JSONObject occurance, TextOccurance occur) throws JSONException {
        occurance.append("text", occur.getTextUri());
        occurance.append("textId", occur.getTextId());
        occurance.append("textAuthor", occur.getAuthor());
        occurance.append("textTitle", occur.getTitle());
        occurance.append("textCreationDate", occur.getCreationDate());
    }
}