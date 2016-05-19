package edu.asu.spring.quadriga.service.textfile.mapper;

import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.dto.TextFileDTO;

/**
 * Interface for Mapping DTO Objects and Domain Objects back and forth.
 * 
 * @author Nischal Samji
 *
 */
public interface ITextFileMapper {

    /**
     * @param textId
     *            TextFile ID for the TextFiles to be retrieved.
     * @return Returns a TextFile Object.
     */
    public abstract ITextFile getTextFile(TextFileDTO tfDTO);

    /**
     * @param txtFile
     *            Domain Object to be mapped to a DTO.
     * @return Returns the mapped DTO object for a textfile domain object.
     */
    public abstract TextFileDTO getTextFileDTO(ITextFile txtFile);

}
