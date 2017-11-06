package com.example.lorena.empresalacteos;

/**
 * Created by JORGE_ALEJANDRO on 01/10/2017.
 */

public class Usuario {

    private int idUsuario;
    private String correoUsuario;
    private String passwordUsuario;

    public Usuario(int idUsuario, String correoUsuario, String passwordUsuario)
    {
        this.idUsuario=idUsuario;
        this.correoUsuario=correoUsuario;
        this.passwordUsuario=passwordUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public String getPasswordUsuario() {
        return passwordUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public void setPasswordUsuario(String passwordUsuario) {
        this.passwordUsuario = passwordUsuario;
    }
}

