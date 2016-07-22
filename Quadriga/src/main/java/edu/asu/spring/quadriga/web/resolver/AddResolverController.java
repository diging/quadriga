package edu.asu.spring.quadriga.web.resolver;

import java.security.Principal;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.spring.quadriga.domain.resolver.IProjectHandleResolver;
import edu.asu.spring.quadriga.domain.resolver.Status;
import edu.asu.spring.quadriga.domain.resolver.impl.ProjectHandleResolver;
import edu.asu.spring.quadriga.service.resolver.IProjectHandleResolverManager;
import edu.asu.spring.quadriga.validator.ProjectHandleResolverValidator;

@Controller
public class AddResolverController {

    @Autowired
    private IProjectHandleResolverManager resolverManager;

    @Autowired
    private ProjectHandleResolverValidator validator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @RequestMapping(value = "/auth/resolvers/add", method = RequestMethod.GET)
    public String preparePage(Principal principal, Model model) {
        model.addAttribute("resolver", new ProjectHandleResolver());
        return "auth/resolvers/add";
    }

    @RequestMapping(value = "/auth/resolvers/add", method = RequestMethod.POST)
    public String addResolver(Principal principal,
            @Validated @ModelAttribute("resolver") ProjectHandleResolver resolver, BindingResult results, Model model) {

        if (results.hasErrors()) {
            model.addAttribute("resolver", resolver);
            return "auth/resolvers/add";
        }

        resolverManager.saveProjectHandleResolver(resolver, principal.getName());

        return "redirect:/auth/resolvers";
    }

    @RequestMapping(method = RequestMethod.POST, value = "auth/resolvers/testAdd")
    public @ResponseBody ResponseEntity<String> testAddPageSuccess(@RequestParam("data") JSONObject data)
            throws JSONException {

        IProjectHandleResolver projectHandleResolver = new ProjectHandleResolver();

        projectHandleResolver.setProjectName(data.getString("projectName"));
        projectHandleResolver.setDescription(data.getString("projectDescription"));
        projectHandleResolver.setProjectUrl(data.getString("projectUrl"));
        projectHandleResolver.setHandlePattern(data.getString("handlePattern"));
        projectHandleResolver.setResolvedHandlePattern(data.getString("resolvedHandlePattern"));
        projectHandleResolver.setHandleExample(data.getString("handleExample"));
        projectHandleResolver.setResolvedHandleExample(data.getString("resolvedHandleExample"));

        if ((resolverManager.getValidationProjectHandleResolver(projectHandleResolver)) == Status.PASSED) {
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("FAILURE", HttpStatus.OK);
        }
    }

}
