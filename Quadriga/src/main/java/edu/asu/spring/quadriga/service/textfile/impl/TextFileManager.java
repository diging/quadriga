package edu.asu.spring.quadriga.service.textfile.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import edu.asu.spring.quadriga.utilities.IFileSaveUtility;

/**
 * @author Nischal Samji
 *
 */
@Service
public class TextFileManager implements ITextFileManager {

    @Autowired
    private ITextFileDAO txtFileDAO;

    @Autowired
    private IFileSaveService fileSaveServ;

    @Qualifier("txtfileSaveUtil")
    @Autowired
    private IFileSaveUtility fileManager;

    @Autowired
    private ITextFileMapper tfSMapper;

    
    @Override
    public boolean saveTextFile(ITextFile txtFile) throws FileStorageException, QuadrigaStorageException {
        String txtId = txtFileDAO.generateUniqueID();
        txtFile.setTextId(txtId);
        TextFileDTO txtFileDTO = tfSMapper.getTextFileDTO(txtFile);
        txtFileDTO.setTextId(txtId);
        txtFileDAO.saveNewDTO(txtFileDTO);
        return fileSaveServ.saveFileToLocal(txtFile);
    }

    @Override
    public List<ITextFile> retrieveTextFiles(String wsId) {
        List<TextFileDTO> tfDTOList = txtFileDAO.getTextFileDTObyWsId(wsId);
        List<ITextFile> tfList = new ArrayList<>();
        for(TextFileDTO tfDTO : tfDTOList)
        {
            tfList.add(tfSMapper.getTextFile(tfDTO));
        }
        return tfList;

    }

    @Override
    public String retrieveTextFileContent(String txtId) throws FileStorageException {

        TextFileDTO tfDTO = txtFileDAO.getDTO(txtId);
        String fileName= tfDTO.getFilename();
        if(!fileName.contains("."))
            fileName+=".txt";
        return fileManager.readFileContent(fileName, tfDTO.getTextId());

        
    }

}
