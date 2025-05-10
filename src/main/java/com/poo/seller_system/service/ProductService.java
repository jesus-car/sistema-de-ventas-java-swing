package com.poo.seller_system.service;

import com.poo.seller_system.config.HibernateConfig;
import com.poo.seller_system.persistence.dao.impl.ProductDAOImpl;
import com.poo.seller_system.persistence.model.Product;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ProductService {
    
    private static final ProductService instance = new ProductService();
    private final ProductDAOImpl productRepository = ProductDAOImpl.getInstance();
    private final EntityManager em = HibernateConfig.getEntityManager();
    
    
    private ProductService(){}
    
    public static ProductService getInstance(){
        return instance;
    }
    
    public void addProduct(Product product){
        productRepository.addProduct(product, em);
    }
    
    public void updateProduct(Product product){
        productRepository.updateProduct(product, em);
    }
    
    public void deleteProduct(Long id){
        productRepository.deleteProduct(id, em);
    }
    
    public List<Product> allProducts(){
        return productRepository.allProducts(em);
    }
    
    public Product findById(Long id){
        return productRepository.findById(id, em);
    }
    
    public void updateStock(List<Product> prod){
        productRepository.updateStock(prod, em);
    }
    
}
