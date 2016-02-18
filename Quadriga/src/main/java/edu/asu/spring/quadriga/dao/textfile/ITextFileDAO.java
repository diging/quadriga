package edu.asu.spring.quadriga.dao.textfile;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.TextFileDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface ITextFileDAO extends IBaseDAO<TextFileDTO>{

    public abstract TextFileDTO getTextFileDTO(String wsId);

    public abstract TextFileDTO getTextFileDTObyProjId(String projId);

    public abstract boolean saveTextFileDTO(TextFileDTO txtFileDTO) throws QuadrigaStorageException;

}
