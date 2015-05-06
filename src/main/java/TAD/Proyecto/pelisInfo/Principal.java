package TAD.Proyecto.pelisInfo;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Flash;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
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
public class Principal extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        HorizontalLayout h1 = new HorizontalLayout();
        h1.setMargin(true);

        final HorizontalLayout h2 = new HorizontalLayout();
        h2.setMargin(true);

        VerticalLayout v1 = new VerticalLayout();
        v1.setMargin(true);

        VerticalSplitPanel v2 = new VerticalSplitPanel();
        v2.addComponent(h1);
        v2.addComponent(h2);
        v2.setSplitPosition(20, Sizeable.UNITS_PERCENTAGE);

        HorizontalSplitPanel layout = new HorizontalSplitPanel();
        layout.addComponent(v1);
        layout.addComponent(v2);
        layout.setSplitPosition(20, Sizeable.UNITS_PERCENTAGE);

        setContent(layout);

        List<Pelicula> listaPeliculas = new ArrayList();
        List<Director> listaDirectores = new ArrayList();
        List<Actor> listaActores = new ArrayList();

        Connection conn = DAO.abrirConexion();
        try {
            if (conn != null) {                
                listaPeliculas = DAO.consultarPeliculas(conn);
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
                
                res2.close();
                stmt2.close();
                res3.close();
                stmt3.close();                
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }finally{
            try {
                DAO.cerrarConexion(conn);
            } catch (SQLException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Table table = new Table();

        table.addContainerProperty("Portada", Image.class, null);
        table.addContainerProperty("Titulo", String.class, null);
        table.addContainerProperty("Año", Integer.class, null);
        table.addContainerProperty("Pais", String.class, null);
        table.addContainerProperty("Duracion", Integer.class, null);
        table.addContainerProperty("Trailer", Flash.class, null);

        for (int i = 0; i < listaPeliculas.size(); i++) {
            Pelicula p = listaPeliculas.get(i);
            /*Label sample = new Label(
             "This is an example of a"
             + "\n<br>"
             + "\n<img src='"+p.getImagen()+"'>"
             + "Label</a> \ncomponent.");
             sample.setContentMode(com.vaadin.shared.ui.label.ContentMode.HTML);
             h2.addComponent(sample);*/
            Image portada = new Image();
            final ExternalResource externalResource = new ExternalResource(
                    p.getImagen());
            portada.setSource(externalResource);
            Flash trailer = new Flash(null, new ExternalResource(
                    p.getTrailer()));
            trailer.setParameter("allowFullScreen", "true");
            trailer.setWidth(280.0f, Unit.PIXELS);
            trailer.setHeight(235.0f, Unit.PIXELS);
            table.addItem(new Object[]{portada, p.getTitulo(), p.getAnio(), p.getPais(), p.getDuracion(), trailer}, p.getIdPelicula());
        }

        table.setPageLength(table.size());
        table.setSelectable(true);

        h1.addComponent(new TextField());
        Button button1 = new Button("Buscar");
        button1.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {

            }
        });
        h1.addComponent(button1);
        h2.addComponent(table);
        v1.addComponent(new Label("Filtros:"));
        
        BeanItemContainer<Director> bdir = new BeanItemContainer(Director.class,listaDirectores);
        final ComboBox cd = new ComboBox("Directores",bdir);
        cd.setItemCaptionPropertyId("nombreCompleto");
        
        v1.addComponent(cd);
        v1.addComponent(new ComboBox("Actores", listaActores));
        Button button2 = new Button("Filtrar");
        button2.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                h2.removeAllComponents();
                h2.addComponent(new Label(""+cd.getValue()));                
            }
        });
        v1.addComponent(button2);
    }

    @WebServlet(urlPatterns = {"/Principal/*", "/VAADIN/*"}, name = "Principal", asyncSupported = true)
    @VaadinServletConfiguration(ui = Principal.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
