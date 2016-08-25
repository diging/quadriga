package edu.asu.spring.quadriga.service.textfile.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.textfile.ITextFileDAO;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.dto.TextFileDTO;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.textfile.IFileSaveService;
import edu.asu.spring.quadriga.service.textfile.ITextFileManager;
import edu.asu.spring.quadriga.service.textfile.mapper.ITextFileMapper;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

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

    @Autowired
    private IRetrieveProjectManager projectManager;

    @Override
    public boolean saveTextFile(ITextFile txtFile) throws FileStorageException, QuadrigaStorageException {
        String txtId = txtFileDAO.generateUniqueID();
        txtFile.setTextId(txtId);

        boolean status = fileSaveServ.saveFileToLocal(txtFile);
        if (status == true) {
            storeTextFile(txtFile);
        }
        return status;
    }

    @Override
    public void storeTextFile(ITextFile txtFile) {
        TextFileDTO txtFileDTO = tfSMapper.getTextFileDTO(txtFile);
        txtFileDAO.saveOrUpdateDTO(txtFileDTO);
    }

    @Override
    public List<ITextFile> retrieveTextFiles(String wsId) throws QuadrigaStorageException {
        List<TextFileDTO> tfDTOList = txtFileDAO.getTextFileDTObyWsId(wsId);
        List<ITextFile> tfList = new ArrayList<>();
        for (TextFileDTO tfDTO : tfDTOList) {
            ITextFile textFile = tfSMapper.getTextFile(tfDTO);
            tfList.add(textFile);
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
            ITextFile textFile = tfSMapper.getTextFile(txtDto);
            String projectId = textFile.getProjectId();
            IProject project = projectManager.getProjectDetails(projectId);

            if (project.getResolver() != null && textFile.getRefId() != null) {
                String resolvedHandle = project.getResolver().buildResolvedHandle(textFile.getRefId());
                textFile.setPresentationUrl(resolvedHandle);
            }
            return textFile;
        }
        return null;
    }

    @Override
    public ITextFile getTextFile(String textId) throws QuadrigaStorageException {
        TextFileDTO txtDto = txtFileDAO.getDTO(textId);
        if (txtDto != null) {
            ITextFile textFile = tfSMapper.getTextFile(txtDto);
            String projectId = textFile.getProjectId();
            IProject project = projectManager.getProjectDetails(projectId);

            if (project.getResolver() != null && textFile.getRefId() != null) {
                String resolvedHandle = project.getResolver().buildResolvedHandle(textFile.getRefId());
                textFile.setPresentationUrl(resolvedHandle);
            }
            return textFile;
        }
        return null;
    }

    @Override
    public void loadFile(ITextFile txtFile) throws FileStorageException {
        txtFile.setFileContent(retrieveTextFileContent(txtFile.getTextId()));
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