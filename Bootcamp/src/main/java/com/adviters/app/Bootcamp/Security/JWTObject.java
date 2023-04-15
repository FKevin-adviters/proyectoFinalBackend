package com.adviters.app.Bootcamp.Security;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class JWTObject {
    private String subject; //email del usuario
    private Date issuedAt; //info. de creacion de token
    private Date expiration; // info. de exp. de token
    private List<String> roles; //perfiles de acceso
}
