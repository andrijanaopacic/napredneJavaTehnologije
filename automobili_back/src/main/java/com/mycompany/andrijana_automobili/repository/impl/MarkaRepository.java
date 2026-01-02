/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.repository.impl;

import com.mycompany.andrijana_automobili.entity.impl.Marka;
import com.mycompany.andrijana_automobili.repository.MyAppRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author HP
 */
@Repository
public class MarkaRepository implements MyAppRepository<Marka, Long>{
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Marka> findAll() {
        return entityManager.createQuery("SELECT m FROM Marka m", Marka.class).getResultList();
    }

    @Override
    public Marka findById(Long id) throws Exception {
        Marka marka = entityManager.find(Marka.class, id);
        if(marka == null){
            throw new Exception("Marka nije pronadjena.");
        }
        return marka;
    }

    @Override
    @Transactional
    public void save(Marka entity) {
        if(entity.getId() == null){
            entityManager.persist(entity);
        } else{
            entityManager.merge(entity);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Marka marka = entityManager.find(Marka.class, id);
        if(marka != null){
            entityManager.remove(marka);
        }
    }
    
}
