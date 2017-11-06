package com.example.lorena.empresalacteos;

/**
 * Created by JORGE_ALEJANDRO on 01/10/2017.
 */

public class Pedido {

    private int id;
    private String codigo;
    private String nombre;
    private int cantidad;

    public Pedido()
    {

    }

    public Pedido(int id, String codigo, String nombre, int cantidad) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }
    public int getCantidad() {
        return cantidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }


    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return codigo+" - "+nombre+" - "+String.valueOf(cantidad);
    }
}
