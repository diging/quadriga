package edu.asu.spring.quadriga.service.textfile.mapper;

import java.util.List;

import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.dto.TextFileDTO;

public interface ITextFileShallowMapper {
    /**
     * @param wsId
     *      Workspace ID for the TextFiles to be retrieved.
     * @return
     *      Returns a list of TextFile Objects.
     */
    public abstract List<ITextFile> getTextFileListbyWsId(String wsId);

    /**
     * @param textId
     *      TextFile ID for the TextFiles to be retrieved.
     * @return
     *      Returns a TextFile Object.
     */
    public abstract ITextFile getTextFile(String textId);

    /**
     * @param txtFile
     *         Domain Object to be mapped to a DTO.
     * @return
     *         Returns the mapped DTO object for a textfile domain object.
     */
    public abstract TextFileDTO getTextFileDTO(ITextFile txtFile);
}
