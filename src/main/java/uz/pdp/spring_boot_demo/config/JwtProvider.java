package uz.pdp.spring_boot_demo.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtProvider {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    @Value("${jwt.secret.expired-date}")
    private Long EXPIRED_DATE;

    public String genereteToken(@NonNull String username) {
        System.out.println(EXPIRED_DATE);
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("online.pdp.g52.uz")
                .setIssuedAt(new Date())
                .claim("roles", List.of("ROLE_ADMIN", "ROLE_USER"))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRED_DATE))
                .signWith(signKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(@NonNull String token) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);

//            Jwts
//                    .parserBuilder()
//                    .setSigningKey(signKey())
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody();

            return true;
        } catch (ExpiredJwtException e){
            System.err.println("Token muddati o'tgan");
            return false;
        } catch (MalformedJwtException e){
            System.err.println("Token buzilgan");
            return false;
        } catch (SignatureException e){
            System.err.println("Token secret key xato");
            return false;
        } catch (UnsupportedJwtException e){
            System.err.println("Token format xato");
            return false;
        }catch (Exception e) {
            System.out.println("Token xato");
            return false;
        }

    }

    public Key signKey(){
        byte[] key = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }

    public String getUsernameFromToken(String token) {
        return  Jwts
                .parserBuilder()
                .setSigningKey(signKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
