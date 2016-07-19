package edu.asu.spring.quadriga.service.transformation.mapper;

import edu.asu.spring.quadriga.domain.workspace.ITransformationFile;
import edu.asu.spring.quadriga.dto.TransformFilesDTO;

/**
 * Interface for mapping transformationDTO to transformation Files and
 * transformationFiles to transformationDTo
 * 
 * @author yoganandakishore
 *
 */
public interface ITransformationMapper {

    /**
     * maps transformFilesDTO to transformation file
     * 
     * @param transformFilesDTO
     * @return returns a transformationFile object
     */
    public abstract ITransformationFile getTransformFile(TransformFilesDTO transformFilesDTO);

    /**
     * maps transformation file to transformFilesDTO
     * 
     * @param transformationFile
     * @return transformFileDTO object
     */
    public abstract TransformFilesDTO getTransformFilesDTO(ITransformationFile transformationFile);

}
