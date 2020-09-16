/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.ZonaComunal;
import facade.ZonaComunalFacade;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author HUERTAS
 */
@Named(value = "zonaComunalControlador")
@SessionScoped
public class ZonaComunalControlador implements Serializable {

    /**
     * Creates a new instance of ZonaComunalControlador
     */
    @Inject
    ImagenControlador imagen;
    @Inject
    MensajeControlador mensaje;
    private ZonaComunal zonaComunal;

    @EJB
    ZonaComunalFacade zonaComunalFacade;

    public ZonaComunalControlador() {
    }

    @PostConstruct
    public void init() {
        zonaComunal = new ZonaComunal();
    }

    public void registrar() {
        zonaComunalFacade.create(zonaComunal);
    }

    public void eliminar(ZonaComunal zonaComunalEliminar) throws IOException {
        mensaje.setMensaje("Confirmar('Estas seguro que deseas eliminar " + zonaComunalEliminar.getNombre() + "','No podras revertilo!','warning','Si, eliminar!','Eliminado!','Se ha eliminado exitosamente la zona común.','success');");
        Path p = Paths.get(imagen.getRuta() + zonaComunalEliminar.getImg());
        Files.delete(p);
        zonaComunalFacade.remove(zonaComunalEliminar);
    }

    public List<ZonaComunal> consultarTodos() {
        return zonaComunalFacade.findAll();
    }

    public String consultarZona(int id) {
        zonaComunal = zonaComunalFacade.find(id);
        return "detalle-zona";
    }

    public String reservarZona(int id) {
        zonaComunal = zonaComunalFacade.find(id);
        return "generar-reserva";
    }

    public int contarZonas() {
        return zonaComunalFacade.count();
    }

    public ZonaComunal getZonaComunal() {
        return zonaComunal;
    }

    public void setZonaComunal(ZonaComunal zonaComunal) {
        this.zonaComunal = zonaComunal;
    }

}
