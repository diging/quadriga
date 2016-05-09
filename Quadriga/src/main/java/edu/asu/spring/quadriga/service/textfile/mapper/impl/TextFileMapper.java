package edu.asu.spring.quadriga.service.textfile.mapper.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.impl.workspace.TextFile;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.dto.TextFileDTO;
import edu.asu.spring.quadriga.service.textfile.mapper.ITextFileMapper;

/**
 * @author Nischal Samji
 * 
 */
@Service
public class TextFileMapper implements ITextFileMapper {

  
    @Override
    public ITextFile getTextFile(TextFileDTO tfDTO) {
        ITextFile txtFile = new TextFile();
        txtFile.setTextId(tfDTO.getTextId());
        txtFile.setRefId(tfDTO.getRefId());
        txtFile.setFileName(tfDTO.getFilename());
        txtFile.setProjectId(tfDTO.getProjectId());
        txtFile.setWorkspaceId(tfDTO.getWorkspaceId());
        txtFile.setAccessibility(tfDTO.getAccessibility());
        return txtFile;
    }

    @Override
    public TextFileDTO getTextFileDTO(ITextFile txtFile) {

        TextFileDTO tfDTO = new TextFileDTO();
        tfDTO.setFilename(txtFile.getFileName());
        tfDTO.setProjectId(txtFile.getProjectId());
        tfDTO.setRefId(txtFile.getRefId());
        tfDTO.setWorkspaceId(txtFile.getWorkspaceId());
        tfDTO.setAccessibility(txtFile.getAccessibility());
        return tfDTO;
    }

}
