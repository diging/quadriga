package edu.asu.spring.quadriga.service.transformation.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.transform.ITransformFilesDAO;
import edu.asu.spring.quadriga.domain.impl.networks.Transformation;
import edu.asu.spring.quadriga.domain.impl.workspace.TransformationFile;
import edu.asu.spring.quadriga.domain.network.tranform.ITransformation;
import edu.asu.spring.quadriga.domain.workspace.ITransformationFile;
import edu.asu.spring.quadriga.dto.TransformFilesDTO;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.service.transformation.ITransformationManager;
import edu.asu.spring.quadriga.service.transformation.ITransformationSaveService;

/**
 * This class is a service which takes the transformation files metadata and
 * creates a DTO and saves this DTO in database using DAO
 * 
 * @author JayaVenkat
 *
 */
@Service
public class TransformationManager implements ITransformationManager {

    @Autowired
    private ITransformFilesDAO transformationDAO;

    @Autowired
    private ITransformationSaveService transformationFileService;

    @Transactional
    @Override
    public void saveTransformations(ITransformationFile transformationFile) throws FileStorageException {
        TransformFilesDTO transformDTO = new TransformFilesDTO(transformationFile.getTitle(),
                transformationFile.getDescription(), transformationFile.getPatternFileName(),
                transformationFile.getPatternTitle(), transformationFile.getPatternDescription(),
                transformationFile.getMappingFileName(), transformationFile.getMappingTitle(),
                transformationFile.getMappingDescription(), transformationFile.getUserName());
        String id = transformationDAO.generateUniqueID();
        transformDTO.setId(id);
        transformationDAO.saveNewDTO(transformDTO);
        transformationFile.setId(id);

        transformationFileService.saveFileToLocal(transformationFile);
    }

    @Transactional
    @Override
    public List<TransformFilesDTO> getTransformationsList() {
        return transformationDAO.getAllTransformations();
    }

    /**
     * Retrieve the transformation file object which contains the absolute file
     * paths of patternFile and MappingFile .
     * 
     * @param transformationId
     *            Transformation ID of the transformation.
     * @return Returns the transformation file object
     */
    @Transactional
    @Override
    public ITransformationFile retrieveTransformationFilePaths(String transformationId) {

        TransformFilesDTO transformDTO = (TransformFilesDTO) transformationDAO.getDTO(transformationId);

        String fileLocation = transformationFileService.getTransformFileLocation();
        String absolutePatternFilePath = getAbsoluteFilePath(fileLocation, transformationId,
                transformDTO.getPatternFileName());
        String absoluteMappingFilePath = getAbsoluteFilePath(fileLocation, transformationId,
                transformDTO.getMappingFileName());

        TransformationFile transformFile = new TransformationFile();
        transformFile.setAbsolutePatternFilePath(absolutePatternFilePath);
        transformFile.setAbsoluteMappingFilePath(absoluteMappingFilePath);

        return transformFile;
    }

    /**
     * appends the location with dirName and fileName and returns the resultant
     * string as absoluteFilePath
     * 
     * @param location
     * @param dirName
     * @param fileName
     * @return
     */
    private String getAbsoluteFilePath(String location, String dirName, String fileName) {

        StringBuffer absoluteFilePath = new StringBuffer();

        absoluteFilePath.append(location);
        absoluteFilePath.append("/");
        absoluteFilePath.append(dirName);
        absoluteFilePath.append("/");
        absoluteFilePath.append(fileName);

        return absoluteFilePath.toString();
    }

    @Transactional
    @Override
    public List<ITransformation> getTransformations(String transformationId) {

        ITransformationFile transformFile = retrieveTransformationFilePaths(transformationId);

        ITransformation transform = new Transformation();
        transform.setPatternFilePath(transformFile.getAbsolutePatternFilePath());
        transform.setTransformationFilePath(transformFile.getAbsoluteMappingFilePath());

        List<ITransformation> transformations = new ArrayList<ITransformation>();
        transformations.add(transform);

        return transformations;
    }
}
