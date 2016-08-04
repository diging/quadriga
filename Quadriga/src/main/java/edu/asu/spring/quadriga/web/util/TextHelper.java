package edu.asu.spring.quadriga.web.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.impl.networks.AppellationEventType;
import edu.asu.spring.quadriga.domain.impl.networks.CreationEvent;
import edu.asu.spring.quadriga.domain.impl.networks.PrintedRepresentationType;
import edu.asu.spring.quadriga.domain.impl.networks.TermPartType;
import edu.asu.spring.quadriga.domain.impl.networks.TermType;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;

@Service
public class TextHelper {

    public ResponseEntity<String> getResponse(String content, HttpServletResponse response)
            throws QuadrigaAccessException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        String respMessage = content.replace("\n", "<br>");
        return new ResponseEntity<String>(respMessage, HttpStatus.OK);
    }

    public String highlightAppellationEvents(String text, List<CreationEvent> creationEvents) {
        List<CreationEvent> appellationEvents = creationEvents.stream()
                .filter(event -> event instanceof AppellationEventType && hasPosition((AppellationEventType) event))
                .collect(Collectors.toList());
        
        List<TermPartType> termParts = new ArrayList<>();
        
        appellationEvents.forEach(event -> termParts.addAll(((AppellationEventType)event).getTermType().getPrintedRepresentation().getTermParts()));
        
        Collections.sort(termParts, new Comparator<TermPartType>() {

            @Override
            public int compare(TermPartType o1, TermPartType o2) {
                return new Integer(o1.getPosition()).compareTo(new Integer(o2.getPosition()));
            }
            
        });
        
        StringBuffer finalText = new StringBuffer();
        
        int lastIdx = 0;
        TermPartType previousTermPart = null;
        for (TermPartType termPart : termParts) {
            // avoid duplications
            if (previousTermPart != null) {
                if (isTheSame(previousTermPart, termPart)) {
                    continue;
                }
            }
            previousTermPart = termPart;
            
            int highlightBegin = new Integer(termPart.getPosition());
            // if we have appellations that are in positions that are greater
            // than text length, exit the loop
            if (highlightBegin >= text.length()) {
                break;
            }
            // if the next appellation start in the midth of the last
            if (highlightBegin < lastIdx) {
                highlightBegin = lastIdx;
            }
            finalText.append(text.substring(lastIdx, highlightBegin));
            finalText.append("<span class=\"highlight-phrase\">");
            
            int hightlightEnd = highlightBegin + termPart.getExpression().length();
            // if we have an appellation that is longer than the text,
            // exit the loop.
            if (hightlightEnd >= text.length()) {
                finalText.append("</span>");
                break;
            }
            finalText.append(text.substring(highlightBegin, hightlightEnd));
            finalText.append("</span>");
            lastIdx = hightlightEnd;
        }
        
        if (lastIdx < text.length()) {
            finalText.append(text.substring(lastIdx));
        }
        
        
        return finalText.toString();
    }

    private boolean isTheSame(TermPartType termPart1, TermPartType termPart2) {
        if (termPart1.getExpression().equals(termPart2.getExpression()) && termPart1.getPosition().equals(termPart2.getPosition())) {
            return true;
        }
        return false;            
    }
    
    private boolean hasPosition(AppellationEventType appEvent) {
        TermType term = appEvent.getTermType();
        if (term == null) {
            return false;
        }

        PrintedRepresentationType printedRepre = term.getPrintedRepresentation();
        if (printedRepre == null) {
            return false;
        }

        List<TermPartType> termParts = printedRepre.getTermParts();
        if (termParts == null || termParts.isEmpty()) {
            return false;
        }

        for (TermPartType termPart : termParts) {
            if (termPart.getPosition() != null && !termPart.getPosition().isEmpty()) {
                return true;
            }
            if (termPart.getFormattedPointer() != null && !termPart.getFormattedPointer().isEmpty()) {
                return true;
            }
        }

        return false;
    }
}
