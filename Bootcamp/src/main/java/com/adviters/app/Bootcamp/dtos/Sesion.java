package com.adviters.app.Bootcamp.dtos;

import com.adviters.app.Bootcamp.Models.Usuario;

public class Sesion {
    private String login;
    private String token;

    private Usuario user;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}
