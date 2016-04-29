package edu.asu.spring.quadriga.domain.impl.workbench;

import org.springframework.stereotype.Service;

/**
 * @description : Statistics Settings Bean helper class to support model
 *              attribute in forms
 * 
 * @author : Ajay Modi
 * 
 */
@Service
public class StatisticsSettingsBean {
    private String[] names;

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }
}
