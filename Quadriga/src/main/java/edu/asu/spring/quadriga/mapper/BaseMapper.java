package edu.asu.spring.quadriga.mapper;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;

public class BaseMapper {
    
    @Autowired
    protected SessionFactory sessionFactory;
    
    public QuadrigaUserDTO getUserDTO(String userName) {
        return (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class, userName);
    }
}
