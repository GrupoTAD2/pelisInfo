package TAD.Proyecto.pelisInfo;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Flash;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
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
public class Admin extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        VerticalLayout v1 = new VerticalLayout();
        v1.setMargin(true);

        final VerticalLayout v2 = new VerticalLayout();
        v2.setMargin(true);

        HorizontalSplitPanel layout = new HorizontalSplitPanel();
        layout.addComponent(v1);
        layout.addComponent(v2);
        layout.setSplitPosition(30, Sizeable.UNITS_PERCENTAGE);

        setContent(layout);

        final List<Pelicula> listaPeliculas = new ArrayList();
        final List<Director> listaDirectores = new ArrayList();
        final List<Actor> listaActores = new ArrayList();

        String login = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/pelisInfo";

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                Statement stmt1 = conn.createStatement();
                ResultSet res1 = stmt1.executeQuery("SELECT * FROM pelicula");
                while (res1.next()) {
                    Pelicula p = new Pelicula(Integer.parseInt(res1.getString("idPelicula")), Integer.parseInt(res1.getString("idDirector")), res1.getString("titulo"), Integer.parseInt(res1.getString("anio")), res1.getString("pais"), res1.getString("genero"), res1.getString("sinopsis"), Integer.parseInt(res1.getString("duracion")), res1.getString("imagen"), res1.getString("trailer"));
                    listaPeliculas.add(p);
                }
                Statement stmt2 = conn.createStatement();
                ResultSet res2 = stmt2.executeQuery("SELECT * FROM director");
                while (res2.next()) {
                    Director d = new Director(Integer.parseInt(res2.getString("idDirector")), res2.getString("nombre"), res2.getString("apellidos"));
                    listaDirectores.add(d);
                }
                Statement stmt3 = conn.createStatement();
                ResultSet res3 = stmt3.executeQuery("SELECT * FROM actor");
                while (res3.next()) {
                    Actor a = new Actor(Integer.parseInt(res3.getString("idActor")), res3.getString("nombre"), res3.getString("apellidos"));
                    listaActores.add(a);
                }
                res1.close();
                stmt1.close();
                res2.close();
                stmt2.close();
                res3.close();
                stmt3.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }

        Tree tree = new Tree("Administracion");
        String pel = "Peliculas";
        tree.addItem(pel);
        for (int i = 0; i < listaPeliculas.size(); i++) {
            Pelicula p = listaPeliculas.get(i);
            tree.addItem(p);
            tree.setParent(p, pel);
            tree.setChildrenAllowed(p, false);
        }
        String act = "Actores";
        tree.addItem(act);
        for (int i = 0; i < listaPeliculas.size(); i++) {
            Actor a = listaActores.get(i);
            tree.addItem(a.getNombre() + " " + a.getApellidos());
            tree.setParent(a.getNombre() + " " + a.getApellidos(), act);
            tree.setChildrenAllowed(a.getNombre() + " " + a.getApellidos(), false);
        }
        String dic = "Directores";
        tree.addItem(dic);
        for (int i = 0; i < listaDirectores.size(); i++) {
            Director d = listaDirectores.get(i);
            tree.addItem(d.getNombre() + " " + d.getApellidos());
            tree.setParent(d.getNombre() + " " + d.getApellidos(), dic);
            tree.setChildrenAllowed(d.getNombre() + " " + d.getApellidos(), false);
        }
        tree.setSelectable(true);
        tree.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                v2.removeAllComponents();
                Pelicula p = (Pelicula) event.getProperty().getValue();
                TextField t1 = new TextField("Titulo", p.getTitulo());
                t1.setColumns(25);
                v2.addComponent(t1);
                TextField t2 = new TextField("Pais", p.getPais());
                v2.addComponent(t2);
                TextField t3 = new TextField("Genero", p.getGenero());
                v2.addComponent(t3);
                TextArea t4 = new TextArea("Sinopsis", p.getSinopsis());
                t4.setColumns(30);
                v2.addComponent(t4);
                TextField t5 = new TextField("Portada", p.getImagen());
                t5.setColumns(30);
                v2.addComponent(t5);
                TextField t6 = new TextField("Trailer", p.getTrailer());
                t6.setColumns(30);
                v2.addComponent(t6);
                Button button = new Button("Editar");
                button.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {

                    }
                });
                v2.addComponent(button);
            }
        });

        v1.addComponent(tree);
    }

    @WebServlet(urlPatterns = "/Admin/*", name = "Admin", asyncSupported = true)
    @VaadinServletConfiguration(ui = Admin.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
