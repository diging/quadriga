package edu.asu.spring.quadriga.mapper;

import java.util.Date;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.passthroughproject.IPassThroughProject;
import edu.asu.spring.quadriga.dto.PassThroughProjectDTO;

/**
 * The purpose of this class is to map the PassThroughProjectDTO class objects
 * to the PassThroughProject domain objects used in Quadriga
 * 
 */
@Service
public class PassThroughProjectDTOMapper extends BaseMapper {

    public PassThroughProjectDTO getPassThroughProjectDTO(IPassThroughProject project, IUser user) {
        PassThroughProjectDTO projectDTO = new PassThroughProjectDTO();
        // internal project details
        // projectDTO.setProjectid(projectId);
        projectDTO.setProjectname(project.getProjectName());
        projectDTO.setDescription(project.getDescription());
        // Since we are not passing unix name in REST request, we are assigning
        // the unix name as projet name
        projectDTO.setUnixname(project.getProjectName());
        projectDTO.setProjectid(project.getProjectId());
        // projectDTO.setProjectowner(quadrigaUsers.get(0));
        projectDTO.setCreatedby(user.getUserName());
        projectDTO.setCreateddate(new Date());
        projectDTO.setUpdatedby(user.getUserName());
        projectDTO.setUpdateddate(new Date());
        projectDTO.setAccessibility(EProjectAccessibility.PUBLIC.name());

        // external project details
        projectDTO.setExternalProjectid(project.getExternalProjectid());
        projectDTO.setExternalUserId(project.getExternalUserId());
        projectDTO.setExternalUserName(project.getExternalUserName());
        projectDTO.setClient(project.getClient());
        return projectDTO;

    }

}
