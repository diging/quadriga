package edu.asu.spring.quadriga.aspects.annotations;

/**
 * These are enumerators defined for the element types for identifying the type
 * of object being called to check the access permissions
 * 
 * @author Kiran Kumar
 */
public enum CheckedElementType {
    PROJECT,
    DICTIONARY,
    WORKSPACE,
    CONCEPTCOLLECTION,
    NETWORK,
    WORKSPACE_REST,
    CONCEPTCOLLECTION_REST;
}
