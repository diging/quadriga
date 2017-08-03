package edu.asu.spring.quadriga.utilities.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.utilities.IFileSaveUtility;

public class FileSaveUtility implements IFileSaveUtility {

    private String textFileLocation;

    public String getTextFileLocation() {
        return textFileLocation;
    }

    public void setTextFileLocation(String textFileLocation) {
        this.textFileLocation = textFileLocation;
    }

    private boolean createDirectoryIfNotExists(String dirPath) {

        File dirFile = new File(dirPath);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        return true;
    }

    @Override
    public boolean saveFiletoDir(String dirName, String fileName, String fileContent) throws FileStorageException {

        try {
            createDirectoryIfNotExists(textFileLocation + File.separator + dirName);
            File f = new File(textFileLocation + File.separator + dirName + File.separator + fileName);
            if (!f.exists()) {
                f.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new StringReader(fileContent));
            PrintWriter writer = new PrintWriter(new FileWriter(f));
            String line;
            while ((line = reader.readLine()) != null) {
                writer.println(line);
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            throw new FileStorageException(e);
        }

        return true;
    }

    @Override
    public String readFileContent(String fileName, String dirName) throws FileStorageException {

        File file = null;
        if (dirName != null) {
            file = new File(textFileLocation + File.separator + dirName + File.separator + fileName);
        } else {
            file = new File(textFileLocation + File.separator + fileName);
        }
        
        if (!file.exists()) {
            return null;
        }
        
        byte fileBytes[] = new byte[(int) file.length()];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            fis.read(fileBytes);

        } catch (IOException e) {
            throw new FileStorageException(e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                throw new FileStorageException(e);
            }

        }
        try {
            return new String(fileBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new FileStorageException(e);
        }
    }

    @Override
    public String getFileLocation() {
        return textFileLocation;
    }
    
    @Override
    public String saveFileInRoot(String fileName, String content) throws IOException {
        File file = new File(textFileLocation + File.separator + fileName);
        FileUtils.write(file, content, Charset.forName("UTF-8"));
        return file.getAbsolutePath();
    }

}
