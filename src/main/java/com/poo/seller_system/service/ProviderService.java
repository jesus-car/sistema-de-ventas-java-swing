package com.poo.seller_system.service;

import com.poo.seller_system.config.HibernateConfig;
import com.poo.seller_system.persistence.dao.impl.ProviderDAOImpl;
import com.poo.seller_system.persistence.model.Provider;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ProviderService {
    
    private final ProviderDAOImpl providerRepository = ProviderDAOImpl.getInstance();
    private final EntityManager em = HibernateConfig.getEntityManager();
    
    private static final ProviderService instance = new ProviderService();
    
    private ProviderService(){}
    
    public static ProviderService getInstance(){
        return instance;
    }
    
    public void addProvider(Provider provider){
        providerRepository.addProvider(provider, em);
    }
    
    public void deleteProvider(Long id){
        providerRepository.deleteProvider(id, em);
    }
    
    public List<Provider> allProviders(){
        return providerRepository.allProviders(em);
    }
    
    public void updateProvider(Provider c){
        providerRepository.updateProvider(c, em);
    }
    
}
