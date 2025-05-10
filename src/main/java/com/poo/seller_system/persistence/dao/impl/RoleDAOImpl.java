package com.poo.seller_system.persistence.dao.impl;

import com.poo.seller_system.persistence.dao.RoleDAO;
import com.poo.seller_system.persistence.model.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class RoleDAOImpl implements RoleDAO{
    
    private static RoleDAOImpl instance = new RoleDAOImpl();
    private RoleDAOImpl(){}
    
    public static RoleDAOImpl getInstance(){
        return instance;
    }

    @Override
    public List<Role> allRoles(EntityManager em) {
        TypedQuery<Role> query = em.createQuery("SELECT r FROM Role r", Role.class);
        return query.getResultList();
    }
    
}
