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
public class EditResolverController {

    @Autowired
    private IProjectHandleResolverManager resolverManager;

    @Autowired
    private ProjectHandleResolverValidator validator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @RequestMapping(value = "/auth/resolvers/{resolverId}/edit", method = RequestMethod.POST)
    public String editPage(Principal principal, Model model, @RequestParam("resolverId") String resolverId) {

        IProjectHandleResolver projectHandleResolver = resolverManager.getProjectHandleResolver(resolverId);
        model.addAttribute("resolver", projectHandleResolver);
        return "auth/resolvers/edit";
    }

    @RequestMapping(value = "/auth/resolvers/update", method = RequestMethod.POST)
    public String editResolver(Principal principal,
            @Validated @ModelAttribute("resolver") ProjectHandleResolver resolver, BindingResult results, Model model) {

        if (results.hasErrors()) {
            model.addAttribute("resolver", resolver);
            return "auth/resolvers/edit";
        }

        resolverManager.saveProjectHandleResolver(resolver, principal.getName());
        return "redirect:/auth/resolvers";
    }

    @RequestMapping(method = RequestMethod.POST, value = "auth/resolvers/testEdit")
    public @ResponseBody ResponseEntity<String> testEditPageSuccess(@RequestParam("data") JSONObject data)
            throws JSONException {

        IProjectHandleResolver projectHandleResolver = new ProjectHandleResolver();

        projectHandleResolver.setHandlePattern(data.getString("handlePattern"));
        projectHandleResolver.setResolvedHandlePattern(data.getString("resolvedHandlePattern"));
        projectHandleResolver.setHandleExample(data.getString("handleExample"));
        projectHandleResolver.setResolvedHandleExample(data.getString("resolvedHandleExample"));

        if (resolverManager.validateProjectResolverHandle(projectHandleResolver, false) == Status.PASSED) {
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("FAILURE", HttpStatus.OK);
        }

    }
}
