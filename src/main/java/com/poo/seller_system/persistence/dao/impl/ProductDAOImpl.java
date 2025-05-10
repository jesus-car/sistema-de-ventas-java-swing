package com.poo.seller_system.persistence.dao.impl;

import com.poo.seller_system.persistence.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;


public class ProductDAOImpl {
    
    private static final ProductDAOImpl instance = new ProductDAOImpl();
    
    private ProductDAOImpl(){}
    
    public static ProductDAOImpl getInstance(){
        return instance;
    }
    
    public void addProduct(Product product, EntityManager em) {
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
    }

    public void updateProduct(Product product, EntityManager em) {
        em.getTransaction().begin();
        em.merge(product); // merge actualiza si ya existe
        em.getTransaction().commit();
    }

    public void deleteProduct(Long id, EntityManager em) {
        em.getTransaction().begin();
        Product managed = em.find(Product.class, id);
        if (managed == null) {
            em.getTransaction().commit();
            throw new RuntimeException();  
        }
        em.remove(managed);
        em.getTransaction().commit();
    }

    public List<Product> allProducts(EntityManager em) {
        TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p", Product.class);
        return query.getResultList();
    }

    public Product findByName(String username, EntityManager em) {
        TypedQuery<Product> query = em.createQuery(
            "SELECT p FROM Product p WHERE p.name = :username", Product.class);
        query.setParameter("username", username);

        List<Product> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
    
    public Product findById(Long id, EntityManager em){
        return em.find(Product.class, id);
    }
    
    public void updateStock(List<Product> products, EntityManager em){
        EntityTransaction tx = em.getTransaction();
        try{
            tx.begin();
            for(Product product : products){
                em.merge(product);
            }
            tx.commit();
        } catch(Exception e){
            if(tx.isActive()){
                tx.rollback();
                throw new RuntimeException();
            }
        }
        
    }
    
}
