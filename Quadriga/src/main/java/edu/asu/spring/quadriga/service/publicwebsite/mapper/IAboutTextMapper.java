package edu.asu.spring.quadriga.service.publicwebsite.mapper;

import edu.asu.spring.quadriga.domain.settings.IAboutText;
import edu.asu.spring.quadriga.dto.AboutTextDTO;
import edu.asu.spring.quadriga.web.settings.AboutTextBackingBean;

public interface IAboutTextMapper {
    
    public AboutTextBackingBean aboutTextDTOtoBean(AboutTextDTO abtDTO);
    public AboutTextDTO aboutTextBeanToDTO(AboutTextBackingBean abtText);

}
