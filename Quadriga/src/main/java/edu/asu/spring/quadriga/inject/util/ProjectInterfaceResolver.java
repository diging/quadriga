package edu.asu.spring.quadriga.inject.util;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.impl.Project;

public class ProjectInterfaceResolver implements HandlerMethodArgumentResolver {

    @Override
    public Object resolveArgument(MethodParameter arg0,
            ModelAndViewContainer arg1, NativeWebRequest arg2,
            WebDataBinderFactory arg3) throws Exception {
        return new Project();
    }

    @Override
    public boolean supportsParameter(MethodParameter arg0) {
        return arg0.getParameterType().equals(IProject.class);
    }

}
