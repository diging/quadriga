package edu.asu.spring.quadriga.service.textfile;

import java.io.IOException;

import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.exceptions.FileStorageException;

public interface IFileSaveService {

    boolean saveFileToLocal(ITextFile txtFile) throws IOException, FileStorageException;
}
