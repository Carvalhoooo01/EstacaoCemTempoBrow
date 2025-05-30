package br.unipar.programacaoweb.estacaocemtempobrow.configuration;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${security.secret-key}")
    private String secret_key;

    @Value("${security.expiration-time}")
    private long expiration_time;

    public String gerarToken(UserDetails user) {
    Algorithm algorithm = Algorithm.HMAC256(secret_key);

    return com.auth0.jwt.JWT.create()
            .withSubject(user.getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis()+ expiration_time))
            .sign(algorithm);

    }
    public String getSubjectByToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret_key);
            return JWT.require(algorithm)
                    .build()
                    .verify(token).getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}