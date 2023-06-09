package com.adviters.app.Bootcamp.Security;

import io.jsonwebtoken.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

public class JWTCreator {
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String ROLES_AUTHORITIES = "authorities";

    public static String createToken(String prefix,String key, JWTObject jwtObject) {
        String token = Jwts.builder()
                .setSubject(jwtObject.getSubject())
                .setIssuedAt(jwtObject.getIssuedAt())
                .setExpiration(jwtObject.getExpiration())
                .claim(ROLES_AUTHORITIES, checkRoles(jwtObject.getRoles()))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return prefix + token;
    }
    public static JWTObject create(String token,String prefix,String key)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException {

            JWTObject object = new JWTObject();
            token = token.replace(prefix, "");
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
            object.setSubject(claims.getSubject());
            object.setExpiration(claims.getExpiration());
            object.setIssuedAt(claims.getIssuedAt());
            object.setRoles((List<String>) claims.get(ROLES_AUTHORITIES));
            return object;
    }

    private static List<String> checkRoles(List<String> roles) {

        List<String> lista = roles.stream().map(s -> "ROLE_".concat(s.replaceAll("ROLE_",""))).collect(Collectors.toList());
        System.out.println(lista);
        return lista;
    }
}
