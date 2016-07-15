package edu.asu.spring.quadriga.service.transformation.mapper.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.impl.workspace.TransformationFile;
import edu.asu.spring.quadriga.domain.workspace.ITransformationFile;
import edu.asu.spring.quadriga.dto.TransformFilesDTO;
import edu.asu.spring.quadriga.service.transformation.mapper.ITransformationMapper;

/**
 * 
 * @author yoganandakishore
 *
 */
@Service
public class TransformationMapper implements ITransformationMapper {

    @Override
    public ITransformationFile getTransformFile(TransformFilesDTO transformFilesDTO) {

        TransformationFile transformFile = new TransformationFile();
        transformFile.setTitle(transformFilesDTO.getTitle());
        transformFile.setDescription(transformFilesDTO.getDescription());
        transformFile.setPatternFileName(transformFilesDTO.getPatternFileName());
        transformFile.setPatternDescription(transformFilesDTO.getMappingDescription());
        transformFile.setPatternTitle(transformFilesDTO.getPatternTitle());
        transformFile.setMappingFileName(transformFilesDTO.getMappingFileName());
        transformFile.setMappingDescription(transformFilesDTO.getMappingDescription());
        transformFile.setMappingTitle(transformFilesDTO.getMappingTitle());
        transformFile.setUserName(transformFilesDTO.getUserName());
        return transformFile;
    }

    @Override
    public TransformFilesDTO getTransformFilesDTO(ITransformationFile transformationFile) {

        TransformFilesDTO transformFilesDTO = new TransformFilesDTO();
        transformFilesDTO.setTitle(transformationFile.getTitle());
        transformFilesDTO.setDescription(transformationFile.getDescription());
        transformFilesDTO.setPatternFileName(transformationFile.getPatternFileName());
        transformFilesDTO.setPatternDescription(transformationFile.getDescription());
        transformFilesDTO.setPatternTitle(transformationFile.getPatternTitle());
        transformFilesDTO.setMappingFileName(transformationFile.getMappingFileName());
        transformFilesDTO.setMappingDescription(transformationFile.getMappingDescription());
        transformFilesDTO.setMappingTitle(transformationFile.getMappingTitle());
        transformFilesDTO.setUserName(transformationFile.getUserName());
        return transformFilesDTO;
    }

}
