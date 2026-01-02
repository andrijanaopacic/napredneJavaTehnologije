/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.repository.impl;

import com.mycompany.andrijana_automobili.entity.impl.Marka;
import com.mycompany.andrijana_automobili.entity.impl.Model;
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
public class ModelRepository implements MyAppRepository<Model, Long>{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<Model> findAll() {
        return entityManager.createQuery("SELECT m FROM Model m JOIN FETCH m.marka", Model.class).getResultList();
    }

    @Override
    public Model findById(Long id) throws Exception {
        Model model = entityManager.find(Model.class, id);
        if(model == null){
            throw new Exception("Model nije pronadjen.");
        }
        return model;
    }

    @Override
    @Transactional
    public void save(Model entity) {
        if(entity.getId() == null){
            entityManager.persist(entity);
        } else{
            entityManager.merge(entity);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Model model = entityManager.find(Model.class, id);
        if(model != null){
            entityManager.remove(model);
        }
    }
    
}
