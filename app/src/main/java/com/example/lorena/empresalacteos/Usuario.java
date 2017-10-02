package com.example.lorena.empresalacteos;

/**
 * Created by JORGE_ALEJANDRO on 01/10/2017.
 */

public class Usuario {

    private int id;
    private String documento;
    private String nombre;
    private String direccion;
    private String telefono;

    public Usuario(int id, String documento, String nombre, String direccion, String telefono) {
        this.id = id;
        this.documento = documento;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getDocumento() {
        return documento;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
