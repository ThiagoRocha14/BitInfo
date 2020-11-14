package com.example.testefinal.ui.noticias;

public class Noticia {
    public String titulo;
    public String link;
    public String id;

    public Noticia() {
    }

    public Noticia(String titulo, String link, String id) {
        this.titulo = titulo;
        this.link = link;
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
