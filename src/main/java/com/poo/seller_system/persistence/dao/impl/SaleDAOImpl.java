package com.poo.seller_system.persistence.dao.impl;

import com.poo.seller_system.persistence.model.Sales;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;


public class SaleDAOImpl {
    
    private final static SaleDAOImpl instance = new SaleDAOImpl();
    
    private SaleDAOImpl(){}
    
    public static SaleDAOImpl getInstance(){
        return instance;
    }
    
    public void addSale(Sales sale, EntityManager em) {
        em.getTransaction().begin();
        em.persist(sale);
        em.getTransaction().commit();
    }

    public List<Sales> allSales(EntityManager em) {
        TypedQuery<Sales> query = em.createQuery("SELECT s FROM Sales s", Sales.class);
        return query.getResultList();
    }
    
    public Sales findById(Long id, EntityManager em){
        return em.find(Sales.class, id);
    }
}
