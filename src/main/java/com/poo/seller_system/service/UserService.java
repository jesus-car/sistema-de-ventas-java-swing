package com.poo.seller_system.service;

import com.poo.seller_system.config.HibernateConfig;
import com.poo.seller_system.persistence.dao.impl.UserDAOImpl;
import com.poo.seller_system.persistence.model.User;
import jakarta.persistence.EntityManager;
import java.util.List;

public class UserService {
    
    private final UserDAOImpl userRepository = UserDAOImpl.getInstance();
    private EntityManager em = HibernateConfig.getEntityManager();
    
    private static final UserService instance = new UserService();
    
    private UserService(){}
    
    public static UserService getInstance(){
        return instance;
    }
    
    public void createUser(User user){
        userRepository.addUser(user, em);
    }
    
    public User findByUsername(String username){
        return userRepository.findByUsername(username, em);              
    }
    
    public List<User> getAllUsers(){
        return userRepository.allUsers(em);
    }
    
    public void deleteUser(Long id){
        userRepository.deleteUser(id, em);
    }
    
    public void updateUser(User user){
        if(user.getPassword() == null){
            User updateUser = this.findByUsername(user.getName());
            user.setPassword(updateUser.getPassword());
        }
        userRepository.updateUser(user, em);
        
    }
    
}
