package com.poo.seller_system.persistence.dao.impl;

import com.poo.seller_system.persistence.model.Orders;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class OrderDAOImpl {
    private static final OrderDAOImpl instance = new OrderDAOImpl();
    
    private OrderDAOImpl(){}
    
    public static OrderDAOImpl getInstance(){
        return instance;
    }
    
    public void addOrder(Orders order, EntityManager em) {
        em.getTransaction().begin();
        em.persist(order);
        em.getTransaction().commit();
    }

    public List<Orders> allOrders(EntityManager em) {
        TypedQuery<Orders> query = em.createQuery("SELECT o FROM Orders o", Orders.class);
        return query.getResultList();
    }
    
    public Orders findById(Long id, EntityManager em){
        return em.find(Orders.class, id);
    }
}
