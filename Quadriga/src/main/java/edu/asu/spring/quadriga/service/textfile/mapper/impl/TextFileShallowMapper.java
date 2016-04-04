package edu.asu.spring.quadriga.service.textfile.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.impl.workspace.TextFile;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.dto.TextFileDTO;
import edu.asu.spring.quadriga.service.textfile.mapper.ITextFileShallowMapper;

/**
 * @author Nischal Samji
 * 
 */
@Service
public class TextFileShallowMapper implements ITextFileShallowMapper {


    @Override
    public List<ITextFile> getTextFileList(List<TextFileDTO> txtFileDTOList) {

        List<ITextFile> tfList = new ArrayList<ITextFile>();
        
        for (TextFileDTO tfDTO : txtFileDTOList) {
            ITextFile tfProxy = new TextFile();
            tfProxy.setFileName(tfDTO.getFilename());
            tfProxy.setTextId(tfDTO.getTextId());
            tfProxy.setRefId(tfDTO.getRefId());
            tfList.add(tfProxy);
        }
        return tfList;

    }

    @Override
    public ITextFile getTextFile(TextFileDTO tfDTO) {
        ITextFile txtFile = new TextFile();
        txtFile.setTextId(tfDTO.getTextId());
        txtFile.setRefId(tfDTO.getRefId());
        txtFile.setFileName(tfDTO.getFilename());
        txtFile.setProjectId(tfDTO.getProjectId());
        txtFile.setWorkspaceId(tfDTO.getWorkspaceId());
      
        return null;
    }


    @Override
    public TextFileDTO getTextFileDTO(ITextFile txtFile) {

        TextFileDTO tfDTO = new TextFileDTO();
        tfDTO.setFilename(txtFile.getFileName());
        tfDTO.setProjectId(txtFile.getProjectId());
        tfDTO.setRefId(txtFile.getRefId());
        tfDTO.setWorkspaceId(txtFile.getWorkspaceId());

        return tfDTO;
    }

   

}
