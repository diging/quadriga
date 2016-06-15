package edu.asu.spring.quadriga.service.textfile.mapper.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.enums.ETextAccessibility;
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

    private static final Logger logger = LoggerFactory.getLogger(TextFileMapper.class);

    @Override
    public ITextFile getTextFile(TextFileDTO tfDTO) {
        ITextFile txtFile = new TextFile();
        txtFile.setTextId(tfDTO.getTextId());
        txtFile.setRefId(tfDTO.getRefId());
        txtFile.setFileName(tfDTO.getFilename());
        txtFile.setProjectId(tfDTO.getProjectId());
        txtFile.setWorkspaceId(tfDTO.getWorkspaceId());
        try {
            txtFile.setAccessibility(ETextAccessibility.valueOf(tfDTO.getAccessibility()));
        } catch (NullPointerException npe) {
            logger.error("error:", npe);
            txtFile.setAccessibility(ETextAccessibility.PRIVATE);
        }
        return txtFile;
    }

    @Override
    public TextFileDTO getTextFileDTO(ITextFile txtFile) {

        TextFileDTO tfDTO = new TextFileDTO();
        tfDTO.setFilename(txtFile.getFileName());
        tfDTO.setProjectId(txtFile.getProjectId());
        tfDTO.setRefId(txtFile.getRefId());
        tfDTO.setWorkspaceId(txtFile.getWorkspaceId());
        tfDTO.setAccessibility(txtFile.getAccessibility().name());
        return tfDTO;
    }

}
