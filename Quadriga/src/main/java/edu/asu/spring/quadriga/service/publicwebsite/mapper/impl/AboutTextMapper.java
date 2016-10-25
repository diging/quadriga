package edu.asu.spring.quadriga.service.publicwebsite.mapper.impl;

import edu.asu.spring.quadriga.domain.settings.IAboutText;
import edu.asu.spring.quadriga.dto.AboutTextDTO;
import edu.asu.spring.quadriga.service.publicwebsite.mapper.IAboutTextMapper;
import edu.asu.spring.quadriga.web.settings.AboutTextBackingBean;

public class AboutTextMapper implements IAboutTextMapper {

    @Override
    public AboutTextBackingBean aboutTextDTOtoBean(AboutTextDTO abtDTO) {
        AboutTextBackingBean abtText = new AboutTextBackingBean();
        abtText.setDescription(abtDTO.getDescription());
        abtText.setTitle(abtDTO.getTitle());
        return abtText;
    }

    @Override
    public AboutTextDTO aboutTextBeanToDTO(AboutTextBackingBean abtText) {
        // TODO Auto-generated method stub
        return null;
    }

}
