package edu.asu.spring.quadriga.service.textfile.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.textfile.ITextFileDAO;
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

    @Autowired
    private ITextFileDAO tfDAO;

    @Override
    public List<ITextFile> getTextFileListbyWsId(String wsId) {

        List<ITextFile> tfList = new ArrayList<ITextFile>();
        List<TextFileDTO> tfDTOList = tfDAO.getTextFileDTObyWsId(wsId);
        for (TextFileDTO tfDTO : tfDTOList) {
            ITextFile tfProxy = new TextFile();
            tfProxy.setFileName(tfDTO.getFilename());
            tfProxy.setTextId(tfDTO.getTextId());
            tfProxy.setRefId(tfDTO.getRefId());
            tfList.add(tfProxy);
        }
        return tfList;

    }

    @Override
    public ITextFile getTextFile(String textId) {
        // TODO Auto-generated method stub
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
