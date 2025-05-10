package com.poo.seller_system.persistence.dao.impl;

import com.poo.seller_system.persistence.model.ProductDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ProductDetailDAOImpl{
    
    private static final ProductDetailDAOImpl instance = new ProductDetailDAOImpl();
    
    private ProductDetailDAOImpl(){}
    
    public static ProductDetailDAOImpl getInstance(){
        return instance;
    }  
    
    
    public void addProductDetail(ProductDetail productDetail, EntityManager em) {
        em.getTransaction().begin();
        em.persist(productDetail);
        em.getTransaction().commit();
    }

    public void updateProductDetail(ProductDetail productDetail, EntityManager em) {
        em.getTransaction().begin();
        em.merge(productDetail); // merge actualiza si ya existe
        em.getTransaction().commit();
    }

    public void deleteProductDetail(Long id, EntityManager em) {
        em.getTransaction().begin();
        ProductDetail managed = em.find(ProductDetail.class, id);
        if (managed == null) {
            em.getTransaction().commit();
            throw new RuntimeException();  
        }
        em.remove(managed);
        em.getTransaction().commit();
    }

    public List<ProductDetail> allProductDetails(EntityManager em) {
        TypedQuery<ProductDetail> query = em.createQuery("SELECT pd FROM ProductDetail pd", ProductDetail.class);
        return query.getResultList();
    }

//    public ProductDetail findByProductId(Long productId, EntityManager em) {
//        TypedQuery<ProductDetail> query = em.createQuery(
//            "SELECT p FROM ProductDetail p WHERE p.name = :username", ProductDetail.class);
//        query.setParameter("username", username);
//
//        List<ProductDetail> results = query.getResultList();
//        return results.isEmpty() ? null : results.get(0);
//    }
    
}
