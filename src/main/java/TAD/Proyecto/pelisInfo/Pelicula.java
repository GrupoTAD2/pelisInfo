/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TAD.Proyecto.pelisInfo;

/**
 *
 * @author Grupo 02
 */
public class Pelicula {

    private int idPelicula;
    private String titulo;
    private String informacion;
    private String generos;
    private String director;
    private String sinopsis;
    private String protagonistas;
    private String imagen;
    private String trailer;

    public Pelicula(int idPelicula, String titulo, String informacion, String generos, String director, String sinopsis, String protagonistas, String imagen, String trailer) {
        this.idPelicula = idPelicula;
        this.titulo = titulo;
        this.informacion = informacion;
        this.generos = generos;
        this.director = director;
        this.sinopsis = sinopsis;
        this.protagonistas = protagonistas;
        this.imagen = imagen;
        this.trailer = trailer;
    }

    public int getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public String getGeneros() {
        return generos;
    }

    public void setGeneros(String generos) {
        this.generos = generos;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getProtagonistas() {
        return protagonistas;
    }

    public void setProtagonistas(String protagonistas) {
        this.protagonistas = protagonistas;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    @Override
    public String toString() {
        return "Pelicula{" + "idPelicula=" + idPelicula + ", titulo=" + titulo + ", informacion=" + informacion + ", generos=" + generos + ", director=" + director + ", sinopsis=" + sinopsis + ", protagonistas=" + protagonistas + ", imagen=" + imagen + ", trailer=" + trailer + '}';
    }

}
