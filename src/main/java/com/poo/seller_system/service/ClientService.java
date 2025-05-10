package com.poo.seller_system.service;

import com.poo.seller_system.config.HibernateConfig;
import com.poo.seller_system.persistence.dao.impl.ClientDAOImpl;
import com.poo.seller_system.persistence.model.Client;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ClientService {
    
    private static final ClientService instance = new ClientService();
    private final ClientDAOImpl clientRepository = ClientDAOImpl.getInstance();
    private final EntityManager em = HibernateConfig.getEntityManager();
    
    private ClientService(){}
    
    public static ClientService getInstance(){
        return instance;
    }
    
    public void addClient(Client client){
        clientRepository.addClient(client, em);
    }
    
    public void deleteClient(Long id){
        clientRepository.deleteClient(id, em);
    }
    
    public List<Client> allClients(){
        return clientRepository.allClient(em);
    }
    
    public void updateClient(Client c){
        clientRepository.updateClient(c, em);
    }
    
    
}
