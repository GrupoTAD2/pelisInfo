package TAD.Proyecto.pelisInfo;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
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

        List<Pelicula> listaPeliculas = new ArrayList();
        List<Director> listaDirectores = new ArrayList();
        List<Actor> listaActores = new ArrayList();

        DAO dao = new DAO();
        dao.abrirConexion();

        try {
            listaPeliculas = dao.consultarPeliculas();
            listaDirectores = dao.consultarDirectores();
            listaActores = dao.consultarActores();
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                dao.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        final BeanItemContainer<Director> bdir = new BeanItemContainer(Director.class, listaDirectores);

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
                TextField t0 = new TextField("Duracion", Integer.toString(p.getDuracion()));
                v2.addComponent(t0);
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
                final ComboBox t7 = new ComboBox("Directores", bdir);
                t7.setItemCaptionPropertyId("nombreCompleto");
                v2.addComponent(t7);
                TextField t8 = new TextField("Director", Integer.toString(p.getIdDirector()));
                v2.addComponent(t8);
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
