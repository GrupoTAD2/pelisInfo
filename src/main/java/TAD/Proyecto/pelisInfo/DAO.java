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

    public static Connection abrirConexion() {
        String login = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/pelisInfo";

        Connection conn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

        return conn;
    }

    public static void cerrarConexion(Connection conn) throws SQLException {
        conn.close();
    }

    public static List consultarPeliculas(Connection conn) throws SQLException {
        List<Pelicula> listaPeliculas = new ArrayList();
        Statement stmt1 = conn.createStatement();
        ResultSet res1 = stmt1.executeQuery("SELECT * FROM pelicula");
        while (res1.next()) {
            Pelicula p = new Pelicula(Integer.parseInt(res1.getString("idPelicula")), Integer.parseInt(res1.getString("idDirector")), res1.getString("titulo"), Integer.parseInt(res1.getString("anio")), res1.getString("pais"), res1.getString("genero"), res1.getString("sinopsis"), Integer.parseInt(res1.getString("duracion")), res1.getString("imagen"), res1.getString("trailer"));
            listaPeliculas.add(p);
        }
        res1.close();
        stmt1.close();
        return listaPeliculas;
    }

}
