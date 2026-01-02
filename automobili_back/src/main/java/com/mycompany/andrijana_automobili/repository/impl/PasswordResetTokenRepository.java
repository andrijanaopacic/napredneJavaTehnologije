/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.repository.impl;

import com.mycompany.andrijana_automobili.entity.impl.PasswordResetToken;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class PasswordResetTokenRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(PasswordResetToken token) {
        em.persist(token);
    }

    public PasswordResetToken findByToken(String tokenValue) {
        return em.find(PasswordResetToken.class, tokenValue);
    }

    @Transactional
    public void delete(PasswordResetToken token) {
        if (em.contains(token)) {
            em.remove(token);
        } else {
            em.remove(em.merge(token));
        }
    }

    @Transactional
    public void update(PasswordResetToken token) {
        em.merge(token);
    }
}
