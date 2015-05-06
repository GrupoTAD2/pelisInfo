/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TAD.Proyecto.pelisInfo;

/**
 *
 * @author Patricia
 */
public class Actor {
    private int idActor;
    private String nombre;
    private String apellidos;

    public Actor(int idActor, String nombre, String apellidos) {
        this.idActor = idActor;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public int getIdActor() {
        return idActor;
    }

    public void setIdActor(int idActor) {
        this.idActor = idActor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Override
    public String toString() {
        return nombre + " " + apellidos;
    }
}
