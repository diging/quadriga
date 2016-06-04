package edu.asu.spring.quadriga.service.textfile.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.textfile.ITextFileDAO;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.dto.TextFileDTO;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.textfile.IFileSaveService;
import edu.asu.spring.quadriga.service.textfile.ITextFileManager;
import edu.asu.spring.quadriga.service.textfile.mapper.ITextFileMapper;

/**
 * @author Nischal Samji
 *
 */
@Service
@Transactional
public class TextFileManager implements ITextFileManager {

    @Autowired
    private ITextFileDAO txtFileDAO;

    @Autowired
    private IFileSaveService fileSaveServ;

    @Autowired
    private ITextFileMapper tfSMapper;

    @Resource(name = "projectconstants")
    private Properties messages;

    @Override
    public boolean saveTextFile(ITextFile txtFile) throws FileStorageException, QuadrigaStorageException {
        String textURI = messages.getProperty("textfiles.uri");
        String txtId = txtFileDAO.generateUniqueID();
        txtFile.setTextId(txtId);
        txtFile.setTextFileURI(textURI);
        TextFileDTO txtFileDTO = tfSMapper.getTextFileDTO(txtFile);
        txtFileDTO.setTextId(txtId);
        boolean status = fileSaveServ.saveFileToLocal(txtFile);
        if (status == true) {
            txtFileDAO.saveNewDTO(txtFileDTO);
        }
        return status;
    }

    @Override
    public List<ITextFile> retrieveTextFiles(String wsId) throws QuadrigaStorageException {
        List<TextFileDTO> tfDTOList = txtFileDAO.getTextFileDTObyWsId(wsId);
        List<ITextFile> tfList = new ArrayList<>();
        for (TextFileDTO tfDTO : tfDTOList) {
            tfList.add(tfSMapper.getTextFile(tfDTO));
        }
        return tfList;

    }
    
    @Override
    public ITextFile getTextFileByUri(String uri) throws QuadrigaStorageException {
        int idxLastSegment = uri.lastIndexOf("/");
        String id = uri.substring(idxLastSegment + 1);
        TextFileDTO txtDto = txtFileDAO.getDTO(id);
        if (txtDto == null) {
            txtDto = txtFileDAO.getTextFileByUri(uri);
        }
        if (txtDto != null) {
            return tfSMapper.getTextFile(txtDto);
        }
        return null;
    }

    @Override
    public String retrieveTextFileContent(String txtId) throws FileStorageException {
        TextFileDTO tfDTO = txtFileDAO.getDTO(txtId);
        String fileName = tfDTO.getFilename();
        if (!fileName.contains("."))
            fileName += ".txt";
        return fileSaveServ.retrieveFileFromLocal(fileName, txtId);

    }

}
