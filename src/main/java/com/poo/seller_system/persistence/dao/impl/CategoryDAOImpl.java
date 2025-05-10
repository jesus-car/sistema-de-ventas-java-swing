/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poo.seller_system.persistence.dao.impl;

import com.poo.seller_system.persistence.model.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 *
 * @author Jesus
 */
public class CategoryDAOImpl {
    
    private CategoryDAOImpl(){}
    
    private static final CategoryDAOImpl instance = new CategoryDAOImpl();
    
    public static CategoryDAOImpl getInstance() { return instance; }
    
    public void addCategory(Category category, EntityManager em){
        em.getTransaction().begin();
        em.persist(category);
        em.getTransaction().commit();        
    }
    
    public void deleteCategory(Long id, EntityManager em) {
        em.getTransaction().begin();
        Category managed = em.find(Category.class, id);
        if (managed == null) {
            em.getTransaction().commit();
            throw new RuntimeException();            
        }
        em.remove(managed);
        em.getTransaction().commit();        
    }
    
    public void updateCategory(Category category, EntityManager em){
        em.getTransaction().begin();
        em.merge(category);
        em.getTransaction().commit();
    }
    
    public List<Category> allCategory(EntityManager em) {
        TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c", Category.class);
        return query.getResultList();
    }

    public Category findByDescription(String categoryName, EntityManager em) {
        TypedQuery<Category> query = em.createQuery(
            "SELECT c FROM Category c WHERE c.name = :categoryName", Category.class);
        query.setParameter("categoryName", categoryName);

        List<Category> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
    
    
}
