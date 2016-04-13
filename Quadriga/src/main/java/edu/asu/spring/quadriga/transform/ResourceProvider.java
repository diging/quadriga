package edu.asu.spring.quadriga.transform;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.osgi.service.datalocation.Location;

public class ResourceProvider {

    public static String getWorkspacePath() {
        Location loc = Platform.getInstanceLocation();
        URL url = loc.getURL();
        if (url == null)
            // FIXME raise exception
            return null;
        String locPath = url.getPath();
        return locPath;
    }
    
    public static Resource getResource(String path, boolean create) {
        String workspace = getWorkspacePath();
        if (workspace == null)
            return null;
        
        String filePath = workspace.endsWith(File.separator) ? workspace + path : workspace + File.separator + path;
        File file = new File(filePath);
        if (file.exists() && !file.isFile())
            return null;
        
        boolean exists = file.exists();
        if (!file.exists()) {
            if (create) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    // FIXME Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else 
                return null;
        }
        
        ResourceSet resourceSet = EditingDomainManager.INSTANCE.getEditingDomain().getResourceSet();
        if (resourceSet == null)
            return null;
        
        URI fileURI = URI.createFileURI(file.getAbsolutePath());
        Resource resource = resourceSet.getResource(fileURI, false);
        if (resource == null) {
            resource = resourceSet.createResource(fileURI);
            resource.setTrackingModification(true);
            if (!exists) {
                try {
                    resource.save(Collections.EMPTY_MAP);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        if (!resource.isLoaded())
            try {
                resource.load(Collections.EMPTY_MAP);
            } catch (IOException e) {
                // FIXME Auto-generated catch block
                e.printStackTrace();
            }
        return resource;
    }
    
    /**
     * Method to create a new folder in the workspace. If you want to created nested folders,
     * you need to create every folder on its own.
     * @param path Path of folder relative to workspace folder.
     */
    public static File createFolder(String path) {
        String workspace = getWorkspacePath();
        if (workspace == null)
            return null;
        
        String filePath = workspace.endsWith(File.separator) ? workspace + path : workspace + File.separator + path;
        File file = new File(filePath);
        if (file.exists())
            return file;
        
        file.mkdir();
        return file;
    }
    
    public static void saveResources() {
        EditingDomain domain = EditingDomainManager.INSTANCE.getEditingDomain();
        List<Resource> resources = domain.getResourceSet().getResources();
        for (Resource r : resources) {
            try {
                r.save(Collections.EMPTY_MAP);
            } catch (IOException e) {
                // FIXME Auto-generated catch block
                e.printStackTrace();
            }
        }
    }   
}