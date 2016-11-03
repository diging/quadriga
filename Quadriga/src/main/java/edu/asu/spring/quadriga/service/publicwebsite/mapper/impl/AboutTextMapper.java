package edu.asu.spring.quadriga.service.publicwebsite.mapper.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.settings.IAboutText;
import edu.asu.spring.quadriga.domain.settings.impl.AboutText;
import edu.asu.spring.quadriga.dto.AboutTextDTO;
import edu.asu.spring.quadriga.service.publicwebsite.mapper.IAboutTextMapper;

/**
 * @author Nischal Samji
 * 
 *         Implementation class for {@link IAboutTextMapper}
 *
 */
@Service
public class AboutTextMapper implements IAboutTextMapper {

    @Override
    public IAboutText aboutTextDTOtoBean(AboutTextDTO abtDTO) {
        IAboutText abtText = new AboutText();
        abtText.setDescription(abtDTO.getDescription());
        abtText.setTitle(abtDTO.getTitle());
        abtText.setId(abtDTO.getId());
        abtText.setProjectId(abtDTO.getProjectId());
        return abtText;
    }

    @Override
    public AboutTextDTO aboutTextBeanToDTO(IAboutText abtText) {
        AboutTextDTO abtDTO = new AboutTextDTO();
        abtDTO.setDescription(abtText.getDescription());
        abtDTO.setId(abtText.getId());
        abtDTO.setTitle(abtText.getTitle());
        abtDTO.setProjectId(abtText.getProjectId());

        return abtDTO;
    }

}
