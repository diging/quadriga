package edu.asu.spring.quadriga.service.impl.transformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.workspace.ITransformationFile;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.service.transformation.ITransformationSaveService;
import edu.asu.spring.quadriga.utilities.IFileSaveUtility;

/**
 * This service contains method to save contents of transformation files to
 * local file storage
 * 
 * @author yoganandakishore
 *
 */

@Service
public class TransformationSaveService implements ITransformationSaveService {

    @Qualifier("transformfileSaveUtil")
    @Autowired
    private IFileSaveUtility transformationFileManager;

    @Override
    public boolean saveFileToLocal(ITransformationFile transformationFile) throws FileStorageException {

        String directoryName = transformationFile.getId();
        String metaDataContent = getMetaDataContent(transformationFile);

        boolean isMetaDataFileSaved = transformationFileManager.saveFiletoDir(directoryName, "metadata.properties",
                metaDataContent.getBytes());
        boolean isPatternFileSaved = transformationFileManager.saveFiletoDir(directoryName,
                "Pattern_" + transformationFile.getPatternFileName(), transformationFile.getPatternFileContent());
        boolean isMappingFileSaved = transformationFileManager.saveFiletoDir(directoryName,
                "Mapping_" + transformationFile.getMappingFileName(), transformationFile.getMappingFileContent());

        return isMetaDataFileSaved && isPatternFileSaved && isMappingFileSaved;
    }

    /**
     * properties of transformationFile namely nameOfUser, title, description,
     * patternfilename, mappingfilename and their values each in a separate
     * line.
     * 
     * @param transformationFile
     * @return String which contains nameofUser, title, description,
     *         patternfilename, mappingfilename each in a separate line
     * 
     */
    private String getMetaDataContent(ITransformationFile transformationFile) {

        StringBuilder content = new StringBuilder();
        content.append("Name of the User=");
        content.append(transformationFile.getUserName());
        content.append(System.lineSeparator());
        content.append("Title=");
        content.append(transformationFile.getTitle());
        content.append(System.lineSeparator());
        content.append("Description=");
        content.append(transformationFile.getDescription());
        content.append(System.lineSeparator());
        content.append("PatternFileName=");
        content.append(transformationFile.getPatternFileName());
        content.append(System.lineSeparator());
        content.append("MappingFileName=");
        content.append(transformationFile.getMappingFileName());

        return content.toString();
    }
}
