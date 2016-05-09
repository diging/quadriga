package edu.asu.spring.quadriga.mapper;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.passthroughproject.IPassThroughProject;
import edu.asu.spring.quadriga.dto.PassThroughProjectDTO;
import edu.asu.spring.quadriga.mapper.workbench.impl.ProjectDTOMapper;

/**
 * The purpose of this class is to map the PassThroughProjectDTO class objects
 * to the PassThroughProject domain objects used in Quadriga
 * 
 */
@Service("passThroughProjectDTOMapper")
public class PassThroughProjectDTOMapper extends ProjectDTOMapper {

    public PassThroughProjectDTO getPassThroughProjectDTO(IPassThroughProject project, IUser user) {
        PassThroughProjectDTO projectDTO = new PassThroughProjectDTO();
        super.fillProjectDTO(project, projectDTO);
        
        // external project details
        projectDTO.setExternalProjectid(project.getExternalProjectid());
        projectDTO.setExternalUserId(project.getExternalUserId());
        projectDTO.setExternalUserName(project.getExternalUserName());
        projectDTO.setClient(project.getClient());
        return projectDTO;

    }

}
