package edu.asu.spring.quadriga.transform;

public class MapFiles {
    
    private String origFile;
    private String mappingFile;
    private String mappingName;
    private String mappingKey;
    
    public MapFiles(String origFilePath,String mappingFilePath, String givenMappingName,String key){
        origFile=origFilePath;
        mappingFile=mappingFilePath;
        mappingName=givenMappingName;
        mappingKey=key;
    }
    /** sets the original File's path
     * @param path
     */
    public void setOrigFile(String path){
        origFile=path;
    }
    
    /** sets the Mapping File's path
     * @param path
     */
    public void setMappingFile(String path){
        mappingFile=path;
    }
    
    /**
     * @return path of the Original File
     */
    public String getOrigFile(){
        return origFile;
    }
    
    /**
     * @return path of the Mapping file
     */
    public String getMappingFile(){
        return mappingFile;
    }
    
    /** sets a name for the Mapping Pair
     * @param path
     */
    public void setMappingName(String name){
        mappingName=name;
    }
    
    /**
     * @return name of the Mapping Pair
     */
    public String getMappingName(){
        return mappingName;
    }
    
    /** sets a key for the Mapping Pair
     * @param path
     */
    public void setMappingKey(String key){
        mappingKey=key;
    }
    
    /**
     * @return Key of the Mapping Pair
     */
    public String getMappingKey(){
        return mappingKey;
    }
}