package com.poo.seller_system.persistence.dao.impl;

import com.poo.seller_system.persistence.model.Provider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ProviderDAOImpl {
    
    private static final ProviderDAOImpl instance = new ProviderDAOImpl();
    private ProviderDAOImpl(){}
    
    public static ProviderDAOImpl getInstance(){
        return instance;
    }
    
    public void addProvider(Provider provider, EntityManager em) {
        em.getTransaction().begin();
        em.persist(provider);
        em.getTransaction().commit();
    }

    public void updateProvider(Provider provider, EntityManager em) {
        em.getTransaction().begin();
        em.merge(provider); // merge actualiza si ya existe
        em.getTransaction().commit();
    }

    public void deleteProvider(Long id, EntityManager em) {
        em.getTransaction().begin();
        Provider managed = em.find(Provider.class, id);
        if (managed == null) {
            em.getTransaction().commit();
            throw new RuntimeException();            
        }
        em.remove(managed);
        em.getTransaction().commit();        
    }

    public List<Provider> allProviders(EntityManager em) {
        TypedQuery<Provider> query = em.createQuery("SELECT u FROM Provider u", Provider.class);
        return query.getResultList();
    }

    public Provider findByProvidername(String providername, EntityManager em) {
        TypedQuery<Provider> query = em.createQuery(
            "SELECT u FROM Provider u WHERE u.name = :providername", Provider.class);
        query.setParameter("providername", providername);

        List<Provider> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
