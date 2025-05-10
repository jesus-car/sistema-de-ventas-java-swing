package com.poo.seller_system.persistence.dao.impl;

import com.poo.seller_system.persistence.model.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ClientDAOImpl {
    
    private static final ClientDAOImpl instance = new ClientDAOImpl();
    
    private ClientDAOImpl(){}
    
    public static ClientDAOImpl getInstance(){
        return instance;
    }
    
    public void addClient(Client client, EntityManager em) {
        em.getTransaction().begin();
        em.persist(client);
        em.getTransaction().commit();
    }

    public void updateClient(Client client, EntityManager em) {
        em.getTransaction().begin();
        em.merge(client); // merge actualiza si ya existe
        em.getTransaction().commit();
    }

    public void deleteClient(Long id, EntityManager em) {
        em.getTransaction().begin();
        Client managed = em.find(Client.class, id);
        if (managed == null) {
            em.getTransaction().commit();
            throw new RuntimeException();            
        }
        em.remove(managed);
        em.getTransaction().commit();        
    }

    public List<Client> allClient(EntityManager em) {
        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c", Client.class);
        return query.getResultList();
    }

    public Client findByUsername(String username, EntityManager em) {
        TypedQuery<Client> query = em.createQuery(
            "SELECT c FROM Client c WHERE c.name = :username", Client.class);
        query.setParameter("username", username);

        List<Client> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
    
}
