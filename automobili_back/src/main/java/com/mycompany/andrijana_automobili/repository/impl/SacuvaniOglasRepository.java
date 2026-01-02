package com.mycompany.andrijana_automobili.repository.impl;

import com.mycompany.andrijana_automobili.entity.impl.SacuvaniOglas;
import com.mycompany.andrijana_automobili.entity.impl.Korisnik;
import com.mycompany.andrijana_automobili.entity.impl.Oglas;
import com.mycompany.andrijana_automobili.repository.MyAppRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class SacuvaniOglasRepository implements MyAppRepository<SacuvaniOglas, Long> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<SacuvaniOglas> findAll() {
        TypedQuery<SacuvaniOglas> query = em.createQuery("SELECT s FROM SacuvaniOglas s", SacuvaniOglas.class);
        return query.getResultList();
    }

    @Override
    public SacuvaniOglas findById(Long id) throws Exception {
        SacuvaniOglas so = em.find(SacuvaniOglas.class, id);
        if (so == null) throw new Exception("SacuvaniOglas sa ID " + id + " ne postoji.");
        return so;
    }

    @Override
    public void save(SacuvaniOglas entity) {
        if (entity.getId() == null) {
            em.persist(entity);
        } else {
            em.merge(entity);
        }
    }

    @Override
    public void deleteById(Long id) {
        SacuvaniOglas so = em.find(SacuvaniOglas.class, id);
        if (so != null) {
            em.remove(so);
        }
    }

    // ---------------- Ispravljene metode ----------------

    public Optional<SacuvaniOglas> findByKorisnikAndOglas(Korisnik korisnik, Oglas oglas) {
        TypedQuery<SacuvaniOglas> query = em.createQuery(
            "SELECT s FROM SacuvaniOglas s WHERE s.korisnik.id = :korisnikId AND s.oglas.id = :oglasId", 
            SacuvaniOglas.class);
        query.setParameter("korisnikId", korisnik.getId());
        query.setParameter("oglasId", oglas.getId());
        List<SacuvaniOglas> result = query.getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
//        TypedQuery<SacuvaniOglas> query = em.createQuery(
//                "SELECT s FROM SacuvaniOglas s WHERE s.korisnik = :korisnik AND s.oglas = :oglas", 
//                SacuvaniOglas.class);
//        query.setParameter("korisnik", korisnik);
//        query.setParameter("oglas", oglas);
//        List<SacuvaniOglas> result = query.getResultList();
//        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public List<SacuvaniOglas> findByKorisnik(Korisnik korisnik) {
        TypedQuery<SacuvaniOglas> query = em.createQuery(
                "SELECT s FROM SacuvaniOglas s WHERE s.korisnik = :korisnik", 
                SacuvaniOglas.class);
        query.setParameter("korisnik", korisnik);
        return query.getResultList();
    }

    public List<SacuvaniOglas> findByKorisnikAndOglasAktivan(Korisnik korisnik, boolean aktivan) {
        
        TypedQuery<SacuvaniOglas> query = em.createQuery(
            "SELECT s FROM SacuvaniOglas s WHERE s.korisnik.id = :korisnikId AND s.oglas.aktivan = :aktivan", 
            SacuvaniOglas.class);
        query.setParameter("korisnikId", korisnik.getId());
        query.setParameter("aktivan", aktivan);
        return query.getResultList();
//        TypedQuery<SacuvaniOglas> query = em.createQuery(
//                "SELECT s FROM SacuvaniOglas s WHERE s.korisnik = :korisnik AND s.oglas.aktivan = :aktivan", 
//                SacuvaniOglas.class);
//        query.setParameter("korisnik", korisnik);
//        query.setParameter("aktivan", aktivan);
//        return query.getResultList();
    }
    
    public void deleteByKorisnikAndOglas(Korisnik korisnik, Oglas oglas) {
        Optional<SacuvaniOglas> soOpt = findByKorisnikAndOglas(korisnik, oglas);
        soOpt.ifPresent(em::remove);
    }
}