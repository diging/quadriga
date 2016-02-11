package edu.asu.spring.quadriga.service.textfile.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.textfile.ITextFileDAO;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.dto.TextFileDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.textfile.ITextFileService;

@Service
public class TextFileService implements ITextFileService{

	@Autowired
	private TextFileDTO txtFileDTO;
	
	
	@Autowired
	private ITextFileDAO txtFileDAO;
	
    @Override
    public boolean saveTextFile(String prjId, String wsId, String fileName, String fileContent ) throws QuadrigaStorageException, IOException {
        
    	saveTextFileLocal();
               
        txtFileDTO.setFilename("testFile");
        txtFileDTO.setProjectId(prjId);
        txtFileDTO.setRefId("refid");
        txtFileDTO.setWorkspaceId("wsId");
        
        txtFileDAO.saveTextFileDTO(txtFileDTO);
        
        
        return true;
    }
    
    private boolean saveTextFileLocal() throws IOException{
    	UUID refId = UUID.randomUUID();
        String filePath =  "PathfromPOM" + refId;
        File dirFile = new File(filePath);
        dirFile.mkdir();
        File txtFile = new File(dirFile + "fileName" + ".txt");
        FileWriter fw =  new FileWriter(txtFile);
        fw.write("meow!!!");
        File propFile = new File(dirFile + "meta.properties");
        FileWriter propFw =  new FileWriter(propFile);
        fw.write("meow!!!");
        
        
        return true;
    	
    }
    
    
}
