/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.controller;

import com.mycompany.andrijana_automobili.dto.impl.AuthResponse;
import com.mycompany.andrijana_automobili.dto.impl.KorisnikDto;
import com.mycompany.andrijana_automobili.dto.impl.LoginRequest;
import com.mycompany.andrijana_automobili.dto.impl.RegisterRequest;
import com.mycompany.andrijana_automobili.entity.impl.Korisnik;
import com.mycompany.andrijana_automobili.entity.impl.VerificationToken;
import com.mycompany.andrijana_automobili.repository.impl.KorisnikRepository;
import com.mycompany.andrijana_automobili.repository.impl.VerificationTokenRepository;
import com.mycompany.andrijana_automobili.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HP
 */

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth")
public class AuthController {

    private final AuthService authService;
    private final VerificationTokenRepository tokens;
    private final KorisnikRepository korisnici;

    public AuthController(AuthService authService, VerificationTokenRepository tokens, KorisnikRepository korisnici) {
        this.authService = authService;
        this.tokens = tokens;
        this.korisnici = korisnici;
    }

    // Registracija korisnika
    @PostMapping("/register")
    public ResponseEntity<KorisnikDto> register(@Valid @RequestBody RegisterRequest req) throws Exception {
        KorisnikDto dto = authService.register(req); // AuthService šalje mejl sa tokenom
        return ResponseEntity.ok(dto);
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    // Logout
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        // JWT je stateless -> logout je samo na klijentu
        return ResponseEntity.ok().build();
    }

    // Podaci o trenutno ulogovanom korisniku
    @GetMapping("/me")
    public ResponseEntity<KorisnikDto> me(Authentication auth) throws Exception {
        Korisnik u = korisnici.findByUsername(auth.getName());
        KorisnikDto dto = new KorisnikDto(u.getId(), u.getUsername(), u.getEmail(), u.getUloga());
        return ResponseEntity.ok(dto);
    }

    // Verifikacija mejla
    @GetMapping("/verify")
    @Transactional
    public ResponseEntity<?> verify(@RequestParam String token) {
        VerificationToken vt = tokens.find(token);
        if (vt == null) return ResponseEntity.badRequest().body("Neispravan token.");
        if (vt.isExpired()) {
            tokens.delete(vt);
            return ResponseEntity.badRequest().body("Token je istekao.");
        }

        Korisnik u = vt.getKorisnik();
        u.setEnabled(true);
        korisnici.save(u);     // update korisnika
        tokens.delete(vt); // token je potrošen

        return ResponseEntity.ok("Nalog aktiviran. Sada se možete prijaviti.");
    }

    // Zaboravljena lozinka – šalje mejl sa tokenom
    @PostMapping("/forgot-password")
    @Transactional
    public ResponseEntity<Void> forgotPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        authService.requestPasswordReset(email);
        return ResponseEntity.ok().build();
    }

    // Reset lozinke
    @PostMapping("/reset-password")
    @Transactional
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String password = body.get("password");

        if (password == null || password.length() < 6) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Lozinka mora imati bar 6 karaktera.");
        }

        authService.resetPassword(token, password);
        return ResponseEntity.ok("Lozinka je promenjena. Sada se možete prijaviti.");
    }
}
