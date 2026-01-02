package com.mycompany.andrijana_automobili.security;
import com.mycompany.andrijana_automobili.entity.impl.Korisnik;
import com.mycompany.andrijana_automobili.repository.impl.KorisnikRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final KorisnikRepository korisnikRepository;

    public AppUserDetailsService(KorisnikRepository korisnikRepository) {
        this.korisnikRepository = korisnikRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Korisnik korisnik = korisnikRepository.findByUsername(username);
        if (korisnik == null) {
            throw new UsernameNotFoundException("Korisnik sa korisničkim imenom '" + username + "' nije pronađen.");
        }

        return new org.springframework.security.core.userdetails.User(
                korisnik.getUsername(),
                korisnik.getPasswordHash(),
                korisnik.isEnabled(),
                true,                
                true,                 
                true,                
                List.of(new SimpleGrantedAuthority("ROLE_" + korisnik.getUloga().name()))
        );
    }
}