package com.poo.seller_system.persistence.dao;

import com.poo.seller_system.persistence.model.Role;
import jakarta.persistence.EntityManager;
import java.util.List;

public interface RoleDAO {   
    
    public List<Role> allRoles(EntityManager em);
    
}
