package edu.asu.spring.quadriga.service.transformation.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.transform.ITransformFilesDAO;
import edu.asu.spring.quadriga.domain.impl.networks.Transformation;
import edu.asu.spring.quadriga.domain.network.tranform.ITransformation;
import edu.asu.spring.quadriga.domain.workspace.ITransformationFile;
import edu.asu.spring.quadriga.dto.TransformFilesDTO;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.service.transformation.ITransformationManager;
import edu.asu.spring.quadriga.service.transformation.ITransformationSaveService;
import edu.asu.spring.quadriga.service.transformation.mapper.ITransformationMapper;

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

    @Autowired
    private ITransformationMapper transformationMapper;

    @Transactional
    @Override
    public void saveTransformations(ITransformationFile transformationFile) throws FileStorageException {
        TransformFilesDTO transformDTO = transformationMapper.getTransformFilesDTO(transformationFile);
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
    public ITransformationFile getTransformationFile(String transformationId) {

        TransformFilesDTO transformDTO = (TransformFilesDTO) transformationDAO.getDTO(transformationId);

        String fileLocation = transformationFileService.getTransformFileLocation();
        String absolutePatternFilePath = transformationFileService.getAbsoluteFilePath(fileLocation, transformationId,
                transformDTO.getPatternFileName());
        String absoluteMappingFilePath = transformationFileService.getAbsoluteFilePath(fileLocation, transformationId,
                transformDTO.getMappingFileName());

        ITransformationFile transformFile = transformationMapper.getTransformFile(transformDTO);
        transformFile.setAbsolutePatternFilePath(absolutePatternFilePath);
        transformFile.setAbsoluteMappingFilePath(absoluteMappingFilePath);

        return transformFile;
    }

    @Transactional
    @Override
    public List<ITransformation> getTransformations(String[] transformationIdsList) {

        List<ITransformation> transformations = new ArrayList<ITransformation>();

        for (int i = 0; i < transformationIdsList.length; i++) {

            ITransformationFile transformFile = getTransformationFile(transformationIdsList[i]);
            ITransformation transform = new Transformation();
            transform.setPatternFilePath(transformFile.getAbsolutePatternFilePath());
            transform.setTransformationFilePath(transformFile.getAbsoluteMappingFilePath());
            transformations.add(transform);
        }
        return transformations;
    }

}
