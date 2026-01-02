/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.repository.impl;

import com.mycompany.andrijana_automobili.entity.impl.Automobil;
import com.mycompany.andrijana_automobili.entity.impl.Model;
import com.mycompany.andrijana_automobili.repository.MyAppRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author HP
 */
@Repository
public class AutomobilRepository implements MyAppRepository<Automobil, Long>{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<Automobil> findAll() {
        return entityManager.createQuery("SELECT a FROM Automobil a "
                + "JOIN FETCH a.model m "
                + "JOIN FETCH m.marka", Automobil.class).getResultList();
    }

    @Override
    public Automobil findById(Long id) throws Exception {
        Automobil automobil = entityManager.createQuery(
        "SELECT a FROM Automobil a " +
        "JOIN FETCH a.model m " +
        "JOIN FETCH m.marka " +
        "WHERE a.id = :id", Automobil.class)
        .setParameter("id", id)
        .getSingleResult();
        if(automobil == null){
            throw new Exception("Automobil nije pronadjen.");
        }
        return automobil;
    }

    @Override
    public void save(Automobil entity) {
        if(entity.getId() == null){
            entityManager.persist(entity);
        } else{
            entityManager.merge(entity);
        }
    }

    @Override
    public void deleteById(Long id) {
        Automobil automobil = entityManager.find(Automobil.class, id);
        if(automobil != null){
            entityManager.remove(automobil);
        }
    }
    
}
