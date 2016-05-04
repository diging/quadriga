package edu.asu.spring.quadriga.mapper;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.factory.passthroughproject.IPassThroughProjectFactory;
import edu.asu.spring.quadriga.domain.impl.passthroughproject.PassThroughProject;
import edu.asu.spring.quadriga.domain.impl.passthroughproject.XMLInfo;
import edu.asu.spring.quadriga.domain.passthroughproject.IPassThroughProject;
import edu.asu.spring.quadriga.dto.PassThroughProjectDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;

/**
 * The purpose of this class is to map the PassThroughProjectDTO class objects
 * to the PassThroughProject domain objects used in Quadriga
 * 
 */
@Service("passThroughProjectDTOMapper")
public class PassThroughProjectDTOMapper extends ProjectDTOMapper {

    @Autowired
    private IPassThroughProjectFactory passthrprojfactory;

    public PassThroughProjectDTO getPassThroughProjectDTO(IPassThroughProject project, IUser user) {
        PassThroughProjectDTO projectDTO = new PassThroughProjectDTO();
        QuadrigaUserDTO quadrigaUser = getUserDTO(user.getUserName());
        projectDTO.setProjectname(project.getProjectName());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setUnixname(project.getUnixName());
        projectDTO.setProjectid(project.getProjectId());
        projectDTO.setProjectowner(quadrigaUser);
        projectDTO.setCreatedby(user.getUserName());
        projectDTO.setCreateddate(new Date());
        projectDTO.setUpdatedby(user.getUserName());
        projectDTO.setUpdateddate(new Date());
        projectDTO.setAccessibility(project.getProjectAccess().name());
        // external project details
        projectDTO.setExternalProjectid(project.getExternalProjectid());
        projectDTO.setExternalUserId(project.getExternalUserId());
        projectDTO.setExternalUserName(project.getExternalUserName());
        projectDTO.setClient(project.getClient());
        return projectDTO;

    }

    /**
     * This method will create a {@link PassThroughProject} instance from the
     * provided {@link XMLInfo}.
     * 
     * @param passThroughProjectInfo
     *            The {@link XMLInfo} object.
     * @return The {@link PassThroughProject} object.
     */
    public IPassThroughProject getPassThroughProject(XMLInfo passThroughProjectInfo) {
        IPassThroughProject project = passthrprojfactory.createPassThroughProjectObject();
        project.setExternalProjectid(passThroughProjectInfo.getExternalProjectId());
        project.setExternalUserName(passThroughProjectInfo.getExternalUserName());
        project.setExternalUserId(passThroughProjectInfo.getExternalUserId());
        project.setProjectName(passThroughProjectInfo.getName());
        project.setDescription(passThroughProjectInfo.getDescription());
        project.setClient(passThroughProjectInfo.getSender());
        project.setProjectAccess(EProjectAccessibility.PUBLIC);
        // Since we are not passing unix name in REST request, we are assigning
        // the unix name as projet name
        project.setUnixName(passThroughProjectInfo.getName());
        return project;
    }
}
