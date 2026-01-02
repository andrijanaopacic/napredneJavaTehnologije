/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.repository.impl;

import com.mycompany.andrijana_automobili.entity.impl.Automobil;
import com.mycompany.andrijana_automobili.entity.impl.Oglas;
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
public class OglasRepository implements MyAppRepository<Oglas, Long> {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Oglas> findAll() {
        return entityManager.createQuery(
            "SELECT o FROM Oglas o " +
            "JOIN FETCH o.automobil a " +
            "JOIN FETCH a.model m " +
            "JOIN FETCH m.marka " +
            "JOIN FETCH o.korisnik k", Oglas.class)
            .getResultList();
    }

    @Override
    public Oglas findById(Long id) throws Exception {
        Oglas oglas = entityManager.createQuery(
            "SELECT o FROM Oglas o " +
            "JOIN FETCH o.automobil a " +
            "JOIN FETCH a.model m " +
            "JOIN FETCH m.marka " +
            "JOIN FETCH o.korisnik k " +
            "WHERE o.id = :id", Oglas.class)
            .setParameter("id", id)
            .getSingleResult();

        if (oglas == null) {
            throw new Exception("Oglas nije pronadjen.");
        }
        return oglas;
    }

    @Override
    public void save(Oglas entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    @Override
    public void deleteById(Long id) {
        Oglas oglas = entityManager.find(Oglas.class, id);
        if (oglas != null) {
            entityManager.remove(oglas);
        }
    }
}

