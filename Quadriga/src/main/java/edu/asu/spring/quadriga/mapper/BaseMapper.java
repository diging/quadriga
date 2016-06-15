package edu.asu.spring.quadriga.mapper;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;

public class BaseMapper {
    
    @Autowired
    protected SessionFactory sessionFactory;
    
    @Transactional
    public QuadrigaUserDTO getUserDTO(String userName) {
        return (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class, userName);
    }
}
