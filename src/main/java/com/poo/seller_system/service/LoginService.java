package com.poo.seller_system.service;

import com.poo.seller_system.persistence.model.User;
import com.poo.seller_system.persistence.model.dto.UserLoginDTO;


public class LoginService {
    
    private final UserService userService = UserService.getInstance();
    private final static LoginService instance = new LoginService();
    
    private LoginService(){}
    
    public static LoginService getInstance(){
        return instance;
    }
    
    public User login(UserLoginDTO userLogin){        
        User user = userService.findByUsername(userLogin.username());
       
        if(user == null) return null;
        
        return userLogin.password().equals(user.getPassword()) ? user : null;
        
    }
    
}
