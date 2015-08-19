package edu.asu.spring.quadriga.dto;

import java.util.List;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class CollaboratingDTO<U extends CollaboratorDTOPK, T extends CollaboratorDTO<U, T>> {

    public abstract List<T> getCollaboratorList();
    
    public abstract void setCollaboratorList(List<T> list);
    
    public abstract String getId();
}
