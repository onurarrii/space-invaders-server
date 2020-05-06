package com.group14.termproject.server.service;

import com.group14.termproject.server.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Service
class JwtServiceImpl implements JwtService {
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    @Value("${custom.security.jwt.token.secret-key}")
    private String secretKey;
    @Value("${custom.security.jwt.token.secret-expire-length}")
    private long expirationTimeInMs;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    @Override
    public String createToken(User user) {
        Claims claims = Jwts.claims().setSubject(Long.toString(user.getId()));
        Date now = new Date();
        Date validity = new Date(now.getTime() + expirationTimeInMs);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    @Override
    public long getUserId(String token) throws JwtException {
        Claims claims = parseJwsBody(token);
        String userIdAsString = claims.getSubject();
        return Long.parseLong(userIdAsString);
    }

    @Override
    public boolean validateHttpRequestAuthentication(HttpServletRequest req) throws JwtException, IllegalArgumentException {
        String token = extractToken(req);
        Claims claims = parseJwsBody(token);
        return !claims.getExpiration().before(new Date());
    }

    @Override
    public String extractToken(String header) throws IllegalArgumentException {
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            return header.substring(TOKEN_PREFIX.length());
        }
        throw new IllegalArgumentException(String.format("Either token is not supplied or not in form " +
                "%s <token>", TOKEN_PREFIX));
    }

    private String extractToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(HEADER_STRING);
        return extractToken(bearerToken);
    }

    private Claims parseJwsBody(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}
