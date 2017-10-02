package com.example.lorena.empresalacteos;

/**
 * Created by JORGE_ALEJANDRO on 01/10/2017.
 */

public class Factura {

    private int valoru;
    private int valort;
    private String DocumentoUsuario;

    public Factura(int valoru, int valort, String DocumentoUsuario) {
        this.valoru = valoru;
        this.valort = valort;
        this.DocumentoUsuario = DocumentoUsuario;
    }

    public String getDocumentoUsuario() {
        return DocumentoUsuario;
    }


    public int getValort() {
        return valort;
    }

    public int getValoru() {
        return valoru;
    }

    public void setDocumentoUsuario(String DocumentoUsuario) {
        this.DocumentoUsuario = DocumentoUsuario;
    }

    public void setValort(int valort) {
        this.valort = valort;
    }

    public void setValoru(int valoru) {
        this.valoru = valoru;
    }
}
