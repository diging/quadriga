package edu.asu.spring.quadriga.service.textfile.mapper;

import java.util.List;

import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.dto.TextFileDTO;

public interface ITextFileShallowMapper {
    /**
     * @param wsId
     * @return
     */
    public abstract List<ITextFile> getTextFileListbyWsId(String wsId);

    /**
     * @param textId
     * @return
     */
    public abstract ITextFile getTextFile(String textId);

    /**
     * @param projId
     * @return
     */
    public abstract List<ITextFile> getTextFileListbyProjId(String projId);
    
    public abstract TextFileDTO getTextFileDTO(ITextFile txtFile);
}
