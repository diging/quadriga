package edu.asu.spring.quadriga.service.passthroughproject;

public interface IPassThroughProjectManager {
    
    void createWorkspaceForExternalProject();
    void addPassThroughProject();
    void getPassThroughProjectDTO();
    void callQStore();
}
