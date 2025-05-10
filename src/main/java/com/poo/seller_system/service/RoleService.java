package com.poo.seller_system.service;

import com.poo.seller_system.config.HibernateConfig;
import com.poo.seller_system.persistence.dao.impl.RoleDAOImpl;
import com.poo.seller_system.persistence.model.Role;
import jakarta.persistence.EntityManager;
import java.util.List;

public class RoleService {
    
    private final RoleDAOImpl roleRepository = RoleDAOImpl.getInstance();
    
    private final EntityManager hc = HibernateConfig.getEntityManager();
    
    private static RoleService instance = new RoleService();
    
    private RoleService(){}
    
    public static RoleService getInstance(){
        return instance;
    }
    
    public List<Role> getAllRoles(){
        return roleRepository.allRoles(hc);
    }
    
}
