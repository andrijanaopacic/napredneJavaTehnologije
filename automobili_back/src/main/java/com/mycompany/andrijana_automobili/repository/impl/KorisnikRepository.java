/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.repository.impl;

import com.mycompany.andrijana_automobili.entity.impl.Korisnik;
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
public class KorisnikRepository implements MyAppRepository<Korisnik, Long>{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<Korisnik> findAll() {
        return entityManager.createQuery(
            "SELECT k FROM Korisnik k ", Korisnik.class).getResultList();
    }

    @Override
    public Korisnik findById(Long id) throws Exception {
        Korisnik k = entityManager.find(Korisnik.class, id);
        if(k == null){
            throw new Exception("Korisnik nije pronadjen.");
        }
        return k;
    }

    @Override
    public void save(Korisnik entity) {
        if(entity.getId() == null){
            entityManager.persist(entity);
        } else{
            entityManager.merge(entity);
        }
    }

    @Override
    public void deleteById(Long id) {
        Korisnik k = entityManager.find(Korisnik.class, id);
        if(k != null){
            entityManager.remove(k);
        }
    }
    
    public Korisnik findByUsername(String username){
        List<Korisnik> list = entityManager.createQuery("SELECT k FROM Korisnik k WHERE k.username = :un",Korisnik.class)
                .setParameter("un", username).getResultList();
        return list.isEmpty() ? null : list.get(0);
    }
    
    public boolean existsByUsername(String username) {
        Long c = entityManager.createQuery(
                "SELECT COUNT(k) FROM Korisnik k WHERE k.username = :un", Long.class)
                .setParameter("un", username).getSingleResult();
        return c > 0;
    }

    public boolean existsByEmail(String email) {
        Long c = entityManager.createQuery(
                "SELECT COUNT(k) FROM Korisnik k WHERE k.email = :em", Long.class)
                .setParameter("em", email).getSingleResult();
        return c > 0;
    }

    public Korisnik findByEmail(String email) {
        List<Korisnik> list = entityManager.createQuery(
                "SELECT k FROM Korisnik k WHERE k.email = :em", Korisnik.class)
                .setParameter("em", email).getResultList();
        return list.isEmpty() ? null : list.get(0);
    }
    
}
