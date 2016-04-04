package edu.asu.spring.quadriga.dao.textfile;

import java.util.List;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.TextFileDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * @author Nischal Samji
 * 
 *         Interface for TextFileDAO Operations
 *
 */
public interface ITextFileDAO extends IBaseDAO<TextFileDTO> {

    /**
     * @param wsId
     *            Workspace Id to retrieve textfiles in the related workspace.
     * @return Returns a list of TextFiles related to the workspace
     */
    public abstract List<TextFileDTO> getTextFileDTObyWsId(String wsId);

    /**
     * @param textId
     *            Text Id for the corresponding text file.
     * @return Returns a Text file object
     */
    public abstract TextFileDTO getTextFileDTO(String textId);

    /**
     * @param txtFileDTO
     *            TextFile DTO to be stored in the database.
     * @return Returns true if text file is saved successfully else false.
     * @throws QuadrigaStorageException
     */
    public abstract boolean saveTextFileDTO(TextFileDTO txtFileDTO) throws QuadrigaStorageException;

}
