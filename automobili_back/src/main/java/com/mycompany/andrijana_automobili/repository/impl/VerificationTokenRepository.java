/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.repository.impl;

import com.mycompany.andrijana_automobili.entity.impl.VerificationToken;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;


@Repository
public class VerificationTokenRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * Čuva ili ažurira verifikacioni token u bazi.
     */
    @Transactional
    public void save(VerificationToken token) {
        em.persist(token);
//        if (em.find(VerificationToken.class, token.getToken()) == null) {
//            em.persist(token);
//        } else {
//            em.merge(token);
//        }
    }

    /**
     * Pronalaženje tokena po njegovoj vrednosti.
     */
    public VerificationToken find(String token) {
        return em.find(VerificationToken.class, token);
    }

    /**
     * Brisanje tokena iz baze.
     */
    @Transactional
    public void delete(VerificationToken token) {
//        if (token == null) return;
        em.remove(em.contains(token) ? token : em.merge(token));
    }
}
