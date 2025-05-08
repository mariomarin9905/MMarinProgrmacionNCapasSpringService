
package com.digis01.MMarinProgrmacionNCapasSpring.JPA;


public class ErrorFile {
    private int Fila;
    private String Mensaje;
    private String Descripcion;

    public ErrorFile() {
    }

    public ErrorFile(int Fila, String Mensaje, String Descripcion) {
        this.Fila = Fila;
        this.Mensaje = Mensaje;
        this.Descripcion =Descripcion;
    }

    public void setFila(int Fila) {
        this.Fila = Fila;
    }

    public int getFila() {
        return this.Fila;
    }

    public void setMensaje(String Mensaje) {
        this.Mensaje = Mensaje;
    }

    public String getMensaje() {
        return this.Mensaje;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }
}
