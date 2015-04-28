package TAD.Proyecto.pelisInfo;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
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
 */
@Theme("valo")
@Title("pelisInfo")
public class Principal extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        List<Pelicula> listaPeliculas = new ArrayList();
        
        String login = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/pelisInfo";

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet res = stmt.executeQuery("SELECT * FROM peliculas");
                while (res.next()) {
                    Pelicula p = new Pelicula(Integer.parseInt(res.getString("idPelicula")), res.getString("titulo"), res.getString("informacion"), res.getString("generos"), res.getString("director"), res.getString("sinopsis"), res.getString("protagonistas"), res.getString("imagen"), res.getString("trailer"));
                    listaPeliculas.add(p);
                }
                res.close();
                stmt.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        final VerticalLayout layout1 = new VerticalLayout();
        layout1.setMargin(true);
        setContent(layout1);
        Table table = new Table();

        table.addContainerProperty("Titulo", String.class, null);
        table.addContainerProperty("Director", String.class, null);
        table.addContainerProperty("informacion", String.class, null);

        for (int i = 0; i < listaPeliculas.size(); i++) {
            Pelicula p = listaPeliculas.get(i);
            table.addItem(new Object[]{p.getTitulo(), p.getDirector(), p.getInformacion()},p.getIdPelicula());
        }
        
        table.setPageLength(table.size());
        table.setSelectable(true);

        layout1.addComponent(table);
    }

    @WebServlet(urlPatterns = {"/Principal/*", "/VAADIN/*"}, name = "Principal", asyncSupported = true)
    @VaadinServletConfiguration(ui = Principal.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
