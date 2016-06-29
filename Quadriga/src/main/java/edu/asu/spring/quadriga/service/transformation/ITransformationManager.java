package edu.asu.spring.quadriga.service.transformation;

import java.util.List;

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

}
