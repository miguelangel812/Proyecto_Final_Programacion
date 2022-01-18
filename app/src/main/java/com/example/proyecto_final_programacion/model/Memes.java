package com.example.proyecto_final_programacion.model;

/* Modelo para nuestro array*/

public class Memes {


    private String url_img;
    private String equipo;


    public Memes(String descripcion, String url_img) {
        this.url_img = url_img;
        this.equipo = descripcion;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String titulo) {
        this.url_img = titulo;
    }

    public String getDescripcion() {
        return equipo;
    }

    public void setDescripcion(String descripcion) {
        this.equipo = descripcion;
    }

}
