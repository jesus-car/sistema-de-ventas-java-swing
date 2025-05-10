/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poo.seller_system.persistence.dao;

import com.poo.seller_system.persistence.model.User;
import jakarta.persistence.EntityManager;
import java.util.List;

public interface UserDAO {
    
    public void addUser(User user, EntityManager em);
    
    public void updateUser(User user, EntityManager em);
    
    public void deleteUser(Long id, EntityManager em);
    
    public List<User> allUsers(EntityManager em);
    
    User findByUsername(String username, EntityManager em);
}
