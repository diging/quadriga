package edu.asu.spring.quadriga.dao.publicwebsite;

public interface IAboutTextDAO<T> {

    public abstract void updateDTO(T dto);

    public abstract void saveNewDTO(T dto);

}
