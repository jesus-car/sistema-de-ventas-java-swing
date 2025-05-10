package com.poo.seller_system.service;

import com.poo.seller_system.config.HibernateConfig;
import com.poo.seller_system.persistence.dao.impl.SaleDAOImpl;
import com.poo.seller_system.persistence.model.Product;
import com.poo.seller_system.persistence.model.Sales;
import jakarta.persistence.EntityManager;
import java.util.List;

public class SaleService {
    
    private static final SaleService instance = new SaleService();
    
    private final SaleDAOImpl saleRepository = SaleDAOImpl.getInstance();
    private final ProductService productService = ProductService.getInstance();
    private final EntityManager em = HibernateConfig.getEntityManager();
    
    private SaleService(){}
    
    public static SaleService getInstance(){
        return instance;
    }
    
    public void addSale(Sales sale){
        
        List<Product> products = sale.getSaleDetail().stream()
                .map(sd -> {
                    int quantity = sd.getQuantity();
                    Product p = sd.getProduct();
                    int updateStock = p.getProductDetail().getStock() - quantity;
                    p.getProductDetail().setStock(updateStock);
                    
                    return p;                   
                })
                .toList();
        productService.updateStock(products);

        saleRepository.addSale(sale, em);
    }
    
    public Sales findById(Long id){
        return saleRepository.findById(id, em);
    }
    
    public List<Sales> allSales(){
        return saleRepository.allSales(em);
    }
    
}
