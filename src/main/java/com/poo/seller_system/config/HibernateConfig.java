package com.poo.seller_system.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class HibernateConfig {

    private static final EntityManagerFactory factory = 
        Persistence.createEntityManagerFactory("sellerSystemHibernate");

    private HibernateConfig() {
        // Constructor privado para evitar instanciación
    }

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    public static void close() {
        factory.close();
    }
}
