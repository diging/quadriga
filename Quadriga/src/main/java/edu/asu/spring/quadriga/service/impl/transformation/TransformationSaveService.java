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

        String directoryName = getTransformationDirectoryName(transformationFile);
        String metaDataContent = getMetaDataContent(transformationFile);

        return (transformationFileManager.saveFiletoDir(directoryName, "metadata.txt", metaDataContent.getBytes())
                && transformationFileManager.saveFiletoDir(directoryName,
                        "Pattern_" + transformationFile.getPatternFileName(),
                        transformationFile.getPatternFileContent())
                && transformationFileManager.saveFiletoDir(directoryName,
                        "Mapping_" + transformationFile.getMappingFileName(),
                        transformationFile.getMappingFileContent()));
    }

    /**
     * forms the directory name by appending username followed by
     * "Transformation" followed by mapping file name and transformation file
     * names, each separated by underscores.
     * 
     * @param transformationFile
     * @return String which contains the directory name
     */
    private String getTransformationDirectoryName(ITransformationFile transformationFile) {

        StringBuilder directoryName = new StringBuilder();

        directoryName.append(transformationFile.getUserName());
        directoryName.append("_Transformation_");
        directoryName.append(transformationFile.getMappingTitle());
        directoryName.append("_");
        directoryName.append(transformationFile.getPatternTitle());

        return directoryName.toString();
    }

    /**
     * takes properties namely nameOfUser, title, description, patternfilename,
     * mappingfilename and their values each in a separate line.
     * 
     * @param transformationFile
     * @return String which contains nameofUser, title, description,
     *         patternfilename, mappingfilename each in a separate line
     * 
     */
    private String getMetaDataContent(ITransformationFile transformationFile) {

        StringBuilder content = new StringBuilder();
        content.append("Name of the User - ");
        content.append(transformationFile.getUserName());
        content.append(System.lineSeparator());
        content.append("Title - ");
        content.append(transformationFile.getTitle());
        content.append(System.lineSeparator());
        content.append("Description - ");
        content.append(transformationFile.getDescription());
        content.append(System.lineSeparator());
        content.append("PatternFileName - ");
        content.append(transformationFile.getPatternFileName());
        content.append(System.lineSeparator());
        content.append("MappingFileName - ");
        content.append(transformationFile.getMappingFileName());

        return content.toString();
    }
}
