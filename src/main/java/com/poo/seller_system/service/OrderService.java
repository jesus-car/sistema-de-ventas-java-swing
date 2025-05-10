package com.poo.seller_system.service;

import com.poo.seller_system.config.HibernateConfig;
import com.poo.seller_system.persistence.dao.impl.OrderDAOImpl;
import com.poo.seller_system.persistence.model.Orders;
import com.poo.seller_system.persistence.model.Product;
import jakarta.persistence.EntityManager;
import java.util.List;

public class OrderService {
    
    private static final OrderService instance = new OrderService();
    private final OrderDAOImpl orderRepository = OrderDAOImpl.getInstance();
    private final ProductService productService= ProductService.getInstance();
    private final EntityManager em = HibernateConfig.getEntityManager();
    
    private OrderService(){}
    
    public static OrderService getInstance(){
        return instance;
    }
    
    public void addOrder(Orders order){
        List<Product> products = order.getOrderDetail().stream()
                .map(od -> {
                    int quantity = od.getQuantity();
                    Product p = od.getProduct();
                    int updateStock = p.getProductDetail().getStock() + quantity;
                    p.getProductDetail().setStock(updateStock);
                    
                    return p;                   
                })
                .toList();
        
        productService.updateStock(products);
        
        orderRepository.addOrder(order, em);
    }
    
    public Orders findById(Long id){
        return orderRepository.findById(id, em);
    }
    
    public List<Orders> allOrders(){
        return orderRepository.allOrders(em);
    }
}
