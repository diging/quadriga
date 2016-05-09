package edu.asu.spring.quadriga.mapper.workbench;

import edu.asu.spring.quadriga.domain.passthroughproject.IPassThroughProject;
import edu.asu.spring.quadriga.dto.PassThroughProjectDTO;

public interface IPassThroughProjectMapper extends IProjectBaseMapper {

    public abstract PassThroughProjectDTO getPassThroughProjectDTO(IPassThroughProject project);

}