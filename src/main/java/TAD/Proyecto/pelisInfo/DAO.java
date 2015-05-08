/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TAD.Proyecto.pelisInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Patricia
 */
public class DAO {

    private Connection conn;

    public DAO() {
        this.conn = null;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void abrirConexion() {
        String login = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/pelisInfo";

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.setConn(DriverManager.getConnection(url, login, password));
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cerrarConexion() throws SQLException {
        this.getConn().close();
    }

    public List<Pelicula> consultarPeliculas() throws SQLException {
        List<Pelicula> listaPeliculas = new ArrayList();
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM pelicula");
        while (res.next()) {
            Pelicula p = new Pelicula(Integer.parseInt(res.getString("idPelicula")), Integer.parseInt(res.getString("idDirector")), res.getString("titulo"), Integer.parseInt(res.getString("anio")), res.getString("pais"), res.getString("genero"), res.getString("sinopsis"), Integer.parseInt(res.getString("duracion")), res.getString("imagen"), res.getString("trailer"));
            listaPeliculas.add(p);
        }
        res.close();
        stmt.close();
        return listaPeliculas;
    }

    public List<Director> consultarDirectores() throws SQLException {
        final List<Director> listaDirectores = new ArrayList();
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM director");
        while (res.next()) {
            Director d = new Director(Integer.parseInt(res.getString("idDirector")), res.getString("nombre"), res.getString("apellidos"));
            listaDirectores.add(d);
        }
        res.close();
        stmt.close();
        return listaDirectores;
    }

    public List<Actor> consultarActores() throws SQLException {
        List<Actor> listaActores = new ArrayList();
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM actor");
        while (res.next()) {
            Actor a = new Actor(Integer.parseInt(res.getString("idActor")), res.getString("nombre"), res.getString("apellidos"));
            listaActores.add(a);
        }
        res.close();
        stmt.close();
        return listaActores;
    }

    public Director devolverDirector(Integer idDirector) throws SQLException {
        Director d = null;
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM director WHERE idDirector='" + idDirector + "'");
        while (res.next()) {
            d = new Director(Integer.parseInt(res.getString("idDirector")),
                    res.getString("nombre"),
                    res.getString("apellidos"));
        }
        res.close();
        stmt.close();
        return d;
    }

    public List<Actor> devolverActores(Integer idPelicula) throws SQLException {
        List<Actor> listaActores = new ArrayList();
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM actorpelicula ap INNER JOIN actor a WHERE ap.idPelicula='" + idPelicula + "' and a.idActor='ap.idActor'");
        while (res.next()) {            
            Actor a = new Actor(Integer.parseInt(res.getString("idActor")), res.getString("nombre"), res.getString("apellidos"));
            listaActores.add(a);
        }
        res.close();
        stmt.close();
        return listaActores;
    }
}
