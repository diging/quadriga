package edu.asu.spring.quadriga.domain.impl.workbench;

import edu.asu.spring.quadriga.domain.workbench.IPublicPage;

/**
 * @description : Public Statistics Page class describing the properties of a
 *              settings for Statisctics Page object
 * 
 * @author : Ajay Modi
 * 
 */
public class PublicStatisticsPage implements IPublicPage {
    private String[] names;

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    @Override
    public String getTitle() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setTitle(String title) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDescription(String description) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getOrder() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setOrder(int order) {
        // TODO Auto-generated method stub

    }
}
