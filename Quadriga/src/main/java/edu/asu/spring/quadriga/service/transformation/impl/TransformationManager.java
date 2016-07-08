package edu.asu.spring.quadriga.service.transformation.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.transform.ITransformFilesDAO;
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

    @Transactional
    @Override
    public ArrayList<String> retrieveTransformationFilePaths(String transformationId) throws FileStorageException {

        TransformFilesDTO transformDTO = (TransformFilesDTO) transformationDAO.getDTO(transformationId);
        ArrayList<String> fileNames = new ArrayList<String>();
        String fileLocation = transformationFileService.getTransformFileLocation();
        String absolutePatternFilePath = getAbsoluteFilePath(fileLocation, transformationId,
                transformDTO.getPatternFileName());
        String absoluteMappingFilePath = getAbsoluteFilePath(fileLocation, transformationId,
                transformDTO.getMappingFileName());

        fileNames.add(absolutePatternFilePath);
        fileNames.add(absoluteMappingFilePath);

        return fileNames;
    }

    private String getAbsoluteFilePath(String location, String dirName, String fileName) {

        StringBuffer filePath = new StringBuffer();

        filePath.append(location);
        filePath.append("/");
        filePath.append(dirName);
        filePath.append("/");
        filePath.append(fileName);

        return filePath.toString();
    }
}
