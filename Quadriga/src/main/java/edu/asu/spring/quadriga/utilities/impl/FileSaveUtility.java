package edu.asu.spring.quadriga.utilities.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
    public boolean saveFiletoDir(String dirName, String fileName, byte[] fileContent) throws FileStorageException {

        try {
            createDirectoryIfNotExists(textFileLocation + "/" + dirName);
            File f = new File(textFileLocation + "/" + dirName + fileName);
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(fileContent);
            fos.close();
        } catch (IOException e) {
            throw new FileStorageException(e);
        }

        return true;

    }

    @Override
    public String readFileContent(String fileName, String dirName) throws FileStorageException {

        File f = new File(textFileLocation + "/" + dirName + "/" + fileName);
        byte fileBytes[] = new byte[(int) f.length()];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
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
            return new String(fileBytes,"ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new FileStorageException(e);
        }
    }

}
