package com.poo.seller_system;

import com.poo.seller_system.config.HibernateConfig;
import com.poo.seller_system.persistence.model.User;
import com.poo.seller_system.view.Login;
import com.poo.seller_system.view.MainApp;
import jakarta.persistence.EntityManager;


public class Seller_system {
    
    public static User currentUser;

    public static void main(String[] args) {
        EntityManager em = HibernateConfig.getEntityManager();

        Login login = new Login();
        login.setVisible(true);
//        MainApp app = new MainApp();
//        app.setVisible(true);
    }
    
}
