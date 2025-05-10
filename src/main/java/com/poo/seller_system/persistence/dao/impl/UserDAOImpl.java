package com.poo.seller_system.persistence.dao.impl;

import com.poo.seller_system.persistence.dao.UserDAO;
import com.poo.seller_system.persistence.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    
    private static final UserDAOImpl instance = new UserDAOImpl();
    
    private UserDAOImpl(){}
    
    public static UserDAOImpl getInstance(){
        return instance;
    }

    @Override
    public void addUser(User user, EntityManager em) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    @Override
    public void updateUser(User user, EntityManager em) {
        em.getTransaction().begin();
        em.merge(user); // merge actualiza si ya existe
        em.getTransaction().commit();
    }

    @Override
    public void deleteUser(Long id, EntityManager em) {
        em.getTransaction().begin();
        User managed = em.find(User.class, id);
        if (managed == null) {
            em.getTransaction().commit();
            throw new RuntimeException();            
        }
        em.remove(managed);
        em.getTransaction().commit();        
    }

    @Override
    public List<User> allUsers(EntityManager em) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    @Override
    public User findByUsername(String username, EntityManager em) {
        TypedQuery<User> query = em.createQuery(
            "SELECT u FROM User u WHERE u.name = :username", User.class);
        query.setParameter("username", username);

        List<User> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
