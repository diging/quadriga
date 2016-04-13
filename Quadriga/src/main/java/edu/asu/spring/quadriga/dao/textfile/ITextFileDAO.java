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



}
