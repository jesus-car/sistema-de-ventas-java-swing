package com.poo.seller_system.service;

import com.poo.seller_system.config.HibernateConfig;
import com.poo.seller_system.persistence.dao.impl.CategoryDAOImpl;
import com.poo.seller_system.persistence.model.Category;
import jakarta.persistence.EntityManager;
import java.util.List;

public class CategoryService {
    
    private final CategoryDAOImpl categoryRepository = CategoryDAOImpl.getInstance();
    private final EntityManager em = HibernateConfig.getEntityManager();
    
    private static final CategoryService instance = new CategoryService();
    
    private CategoryService(){}
    
    public static CategoryService getInstance(){
        return instance;
    }
    
    public void addCategory(Category category){
        categoryRepository.addCategory(category, em);
    }
    
    public void deleteCategory(Long id){
        categoryRepository.deleteCategory(id, em);
    }
    
    public void updateCategory(Category category){
        categoryRepository.updateCategory(category, em);
    }
    
    public List<Category> allCategory(){
        return categoryRepository.allCategory(em);
    }
    
}
