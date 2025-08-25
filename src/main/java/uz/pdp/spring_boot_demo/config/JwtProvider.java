package uz.pdp.spring_boot_demo.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    @Value("${jwt.secret.expired-date}")
    private Long EXPIRED_DATE;

    public String genereteToken(@NonNull String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("online.pdp.g52.uz")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRED_DATE))
                .signWith(signKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Key signKey(){
        byte[] key = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }

}
