package edu.asu.spring.quadriga.service.transformation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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

    /**
     * Retrieve the absolute file paths of patternFile and MappingFile as an
     * arraylist of strings.
     * 
     * @param transformationId
     *            Transformation ID of the transformatioin.
     * @return Returns the absolute file paths in an arraylist of Strings
     * @throws FileNotFoundException
     * @throws IOException
     * @throws FileStorageException
     */
    ArrayList<String> retrieveTransformationFilePaths(String transformationId) throws FileStorageException;

}
