package edu.asu.spring.quadriga.service.impl.transformation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.transform.ITransformFilesDAO;
import edu.asu.spring.quadriga.domain.workspace.ITransformationFile;
import edu.asu.spring.quadriga.dto.TransformFilesDTO;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
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
    public void saveTransformation(String title, String description,
            String patternFileName, String patternTitle,
            String patternDescription, String mappingFileName,
            String mappingTitle, String mappingDescription,String userName) {
        TransformFilesDTO tranformDTO = new TransformFilesDTO(title,
                description, patternFileName, patternTitle, patternDescription,
                mappingFileName, mappingTitle, mappingDescription, userName);
        tranformDTO.setId(transformationDAO.generateUniqueID());
        transformationDAO.saveNewDTO(tranformDTO);
    }

    @Transactional
    @Override
    public List<TransformFilesDTO> getTransformationsList() {
        return transformationDAO.getAllTransformations();
    }

    @Override
    public void saveFiles(ITransformationFile transformationFile) throws FileStorageException {
        // TODO Auto-generated method stub
        transformationFileService.saveFileToLocal(transformationFile);
    }

}
