package edu.asu.spring.quadriga.service.transformation;

import java.util.List;

import edu.asu.spring.quadriga.domain.network.tranform.ITransformation;
import edu.asu.spring.quadriga.domain.workspace.ITransformationFile;
import edu.asu.spring.quadriga.dto.TransformFilesDTO;
import edu.asu.spring.quadriga.exceptions.FileStorageException;

/**
 * 
 * @author JayaVenkat
 *
 */
public interface ITransformationManager {

    public void saveTransformations(ITransformationFile transformations) throws FileStorageException;

    public List<TransformFilesDTO> getTransformationsList();

    /**
     * each transformationId is used to get transformation, all the obtained
     * transformations are returned in a list
     * 
     * @param transformationIds
     * @return list of transformations
     */
    public List<ITransformation> getTransformations(String[] transformationIds);

    public ITransformationFile getTransformationFile(String transformationId);

}
