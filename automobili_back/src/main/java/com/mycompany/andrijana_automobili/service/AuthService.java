package com.mycompany.andrijana_automobili.service;

import com.mycompany.andrijana_automobili.dto.impl.AuthResponse;
import com.mycompany.andrijana_automobili.dto.impl.KorisnikDto;
import com.mycompany.andrijana_automobili.dto.impl.LoginRequest;
import com.mycompany.andrijana_automobili.dto.impl.RegisterRequest;
import com.mycompany.andrijana_automobili.entity.impl.Korisnik;
import com.mycompany.andrijana_automobili.entity.impl.PasswordResetToken;
import com.mycompany.andrijana_automobili.entity.impl.Uloga;
import com.mycompany.andrijana_automobili.entity.impl.VerificationToken;
import com.mycompany.andrijana_automobili.mapper.impl.KorisnikMapper;
import com.mycompany.andrijana_automobili.repository.impl.KorisnikRepository;
import com.mycompany.andrijana_automobili.repository.impl.PasswordResetTokenRepository;
import com.mycompany.andrijana_automobili.repository.impl.VerificationTokenRepository;
import com.mycompany.andrijana_automobili.security.JwtService;
import com.mycompany.andrijana_automobili.service.MailService;
import java.time.LocalDateTime;
//import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtService jwt;
    private final KorisnikRepository korisnici;
    private final VerificationTokenRepository tokens;
    private final PasswordEncoder encoder;
    private final KorisnikMapper korisnikMapper;
    private final PasswordResetTokenRepository resetTokens;
    private final MailService mail;
    private final KorisnikRepository korisnikRepository;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public AuthService(
            AuthenticationManager authManager,
            JwtService jwt,
            KorisnikRepository korisnici,
            VerificationTokenRepository tokens,
            PasswordEncoder encoder,
            KorisnikMapper korisnikMapper,
            PasswordResetTokenRepository resetTokens,
            MailService mail, KorisnikRepository korisnikRepository) {
        this.authManager = authManager;
        this.jwt = jwt;
        this.korisnici = korisnici;
        this.tokens = tokens;
        this.encoder = encoder;
        this.korisnikMapper = korisnikMapper;
        this.resetTokens = resetTokens;
        this.mail = mail;
        this.korisnikRepository = korisnikRepository;
    }
    
    public Korisnik getTrenutniKorisnik() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName().equals("anonymousUser")) {
            return null; // ili baci exception ako mora biti ulogovan
        }
        return korisnikRepository.findByUsername(auth.getName());
    }

    @Transactional
    public KorisnikDto register(RegisterRequest req) throws Exception {
        if (korisnici.existsByUsername(req.getUsername()))
            throw new Exception("Korisničko ime je zauzeto");
        if (korisnici.existsByEmail(req.getEmail()))
            throw new Exception("Email adresa je zauzeta");

        Korisnik k = new Korisnik();
        k.setUsername(req.getUsername());
        k.setEmail(req.getEmail());
        k.setPasswordHash(encoder.encode(req.getPassword()));
        k.setUloga(Uloga.KORISNIK);

        korisnici.save(k);

        // kreiranje verifikacionog tokena (važi 24h = 86400s)
        var vt = VerificationToken.of(k, 86400);
        tokens.save(vt);

        String verifyUrl = "http://localhost:8080/api/auth/verify?token=" + vt.getToken();
        String html = """
            <div style="font-family: Inter,Segoe UI,Arial,sans-serif; max-width: 560px; margin: 0 auto; padding: 24px; background:#f7f8fb;">
              <div style="background:#fff; border:1px solid #e6e8ef; border-radius:14px; padding:24px;">
                <h2 style="margin:0 0 8px; color:#0f172a;">Zdravo %s 👋</h2>
                <p style="margin:0 0 16px; color:#475569;">Hvala ti na registraciji na <strong>Andrijana Automobili</strong>! Da bi aktivirala svoj nalog, klikni na dugme ispod:</p>
                <div style="text-align:center; margin:24px 0;">
                  <a href="%s" style="display:inline-block; padding:12px 18px; background:#2563eb; color:#fff; text-decoration:none; border-radius:10px; font-weight:700;">
                    Potvrdi nalog
                  </a>
                </div>
                <p style="margin:0 0 6px; color:#64748b; font-size:14px;">Ako dugme ne radi, možeš otvoriti ovaj link direktno u pregledaču:</p>
                <p style="margin:0; word-break:break-all; color:#0f172a; font-size:13px;">%s</p>
                <hr style="border:none; border-top:1px solid #e6e8ef; margin:20px 0;">
                <p style="margin:0; color:#94a3b8; font-size:12px;">Link važi 24h. Ako se nisi registrovala, ignoriši ovaj mejl.</p>
              </div>
            </div>
            """.formatted(k.getUsername(), verifyUrl, verifyUrl);

        mail.sendHtml(k.getEmail(), "Potvrda naloga", html);

        return korisnikMapper.toDto(k);
    }

    
    public AuthResponse login(LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

        Korisnik me = korisnici.findByUsername(req.getUsername());
        if (me == null) {
            throw new RuntimeException("Korisnik ne postoji");
        }
        if (!me.isEnabled()) {
            throw new RuntimeException("Nalog nije aktiviran");
        }
        
        Map<String, Object> extra = Map.of(
                "role", java.util.List.of(me.getUloga().name())
        );
        

        String token = jwt.generate(
                new org.springframework.security.core.userdetails.User(
                        me.getUsername(),
                        me.getPasswordHash(),
                        java.util.List.of(new SimpleGrantedAuthority("ROLE_" + me.getUloga().name()))
                ),
                extra
        );

        return new AuthResponse(token, korisnikMapper.toDto(me));
    }

    @Transactional
    public void requestPasswordReset(String email) {
        Korisnik k = korisnici.findByEmail(email);
        if (k == null) {
            // bez otkrivanja da li korisnik postoji
            return;
        }

        PasswordResetToken t = PasswordResetToken.of(k, 1800); // 30 min
        resetTokens.save(t);

        String link = frontendUrl + "/reset?token=" + t.getToken();
        String html = buildResetEmailHtml(k.getUsername(), link);

        mail.sendHtml(k.getEmail(), "Reset lozinke", html);
    }

    private String buildResetEmailHtml(String username, String link) {
        return """
        <div style="font-family: Inter,Segoe UI,Arial,sans-serif; max-width: 560px; margin: 0 auto; padding: 24px; background:#f7f8fb;">
          <div style="background:#fff; border:1px solid #e6e8ef; border-radius:14px; padding:24px;">
            <h2 style="margin:0 0 8px; color:#0f172a;">Pozdrav %s,</h2>
            <p style="margin:0 0 16px; color:#475569;">Dobili smo zahtev za reset lozinke. Klikni na dugme ispod da postaviš novu lozinku.</p>
            <div style="text-align:center; margin:24px 0;">
              <a href="%s" style="display:inline-block; padding:12px 18px; background:#2563eb; color:#fff; text-decoration:none; border-radius:10px; font-weight:700;">
                Postavi novu lozinku
              </a>
            </div>
            <p style="margin:0 0 6px; color:#64748b; font-size:14px;">Ako dugme ne radi, otvori ovaj link u pregledaču:</p>
            <p style="margin:0; word-break:break-all; color:#0f172a; font-size:13px;">%s</p>
            <hr style="border:none; border-top:1px solid #e6e8ef; margin:20px 0;">
            <p style="margin:0; color:#94a3b8; font-size:12px;">Link važi 30 minuta. Ako nisi tražio reset, ignoriši ovaj mejl.</p>
          </div>
        </div>
        """.formatted(username, link, link);
    }

    public void resetPassword(String token, String password) {
        PasswordResetToken t = resetTokens.findByToken(token);
//                if (t != null) {
//            System.out.println("Token pronađen. Vrednost 'isUsed()': " + t.isUsed());
//            System.out.println("Token ističe na: " + t.getExpiresAt());
//            System.out.println("Trenutno vreme: " + LocalDateTime.now());
//        } else {
//            System.out.println("Token NIJE pronađen.");
//        }
        if (t == null || t.isUsed() || t.isExpired()) {
            throw new RuntimeException("Neispravan ili istekao token.");
        }

        Korisnik k = t.getKorisnik();
        k.setPasswordHash(encoder.encode(password));
        korisnici.save(k);

        t.setUsed(true);
        resetTokens.save(t);
        System.out.println("Token saved: "+t.getToken());
    }
}