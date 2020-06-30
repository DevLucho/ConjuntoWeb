/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Disponibilidad;
import entidades.DisponibilidadDia;
import entidades.ZonaComunal;
import facade.DisponibilidadDiaFacade;
import facade.DisponibilidadFacade;
import facade.ZonaComunalFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

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
    //private String[] disponibilidadDias = {"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
    private boolean domingo;
    private boolean lunes;
    private boolean martes;
    private boolean miercoles;
    private boolean jueves;
    private boolean viernes;
    private boolean sabado;

    private ZonaComunal zonaComunal;

    private Disponibilidad disponibilidad;

    DisponibilidadDia disponibilidadDia;

    @EJB
    ZonaComunalFacade zonaComunalFacade;

    @EJB
    DisponibilidadFacade disponibilidadFacade;

    @EJB
    DisponibilidadDiaFacade disponibilidadDiaFacade;

    public ZonaComunalControlador() {
    }

    @PostConstruct
    public void init() {
        zonaComunal = new ZonaComunal();
        domingo = false;
        lunes = false;
        martes = false;
        miercoles = false;
        jueves = false;
        viernes = false;
        sabado = false;
        disponibilidad = new Disponibilidad();
        disponibilidadDia = new DisponibilidadDia();
    }

    public void registrar() {
        zonaComunalFacade.create(zonaComunal);
        if (domingo) {
            disponibilidadDia.setIdDia(1);
            disponibilidad.setIdDia(disponibilidadDia);
            disponibilidad.setIdZonaComunal(zonaComunal);
            disponibilidadFacade.create(disponibilidad);
        }
        if (lunes) {
            disponibilidadDia.setIdDia(2);
            disponibilidad.setIdDia(disponibilidadDia);
            disponibilidad.setIdZonaComunal(zonaComunal);
            disponibilidadFacade.create(disponibilidad);
        }
        if (martes) {
            disponibilidadDia.setIdDia(3);
            disponibilidad.setIdDia(disponibilidadDia);
            disponibilidad.setIdZonaComunal(zonaComunal);
            disponibilidadFacade.create(disponibilidad);
        }
        if (miercoles) {
            disponibilidadDia.setIdDia(4);
            disponibilidad.setIdDia(disponibilidadDia);
            disponibilidad.setIdZonaComunal(zonaComunal);
            disponibilidadFacade.create(disponibilidad);
        }
        if (jueves) {
            disponibilidadDia.setIdDia(5);
            disponibilidad.setIdDia(disponibilidadDia);
            disponibilidad.setIdZonaComunal(zonaComunal);
            disponibilidadFacade.create(disponibilidad);
        }
        if (viernes) {
            disponibilidadDia.setIdDia(6);
            disponibilidad.setIdDia(disponibilidadDia);
            disponibilidad.setIdZonaComunal(zonaComunal);
            disponibilidadFacade.create(disponibilidad);
        }
        if (sabado) {
            disponibilidadDia.setIdDia(7);
            disponibilidad.setIdDia(disponibilidadDia);
            disponibilidad.setIdZonaComunal(zonaComunal);
            disponibilidadFacade.create(disponibilidad);
        }

        domingo = false;
        lunes = false;
        martes = false;
        miercoles = false;
        jueves = false;
        viernes = false;
        sabado = false;
        disponibilidad = new Disponibilidad();
        zonaComunal = new ZonaComunal();
    }

    public void eliminar(ZonaComunal zonaComunalEliminar) {
        zonaComunalFacade.remove(zonaComunalEliminar);
    }

    /*
    public String preActualizar(ZonaComunal zonaComunalActualizar) {
        disponibilidad = zonaComunalActualizar.getIdDisponibilidad();
        zonaComunal = zonaComunalActualizar;
        return "editar-zona";
    }
    public void actualizar() {
        zonaComunal.setIdDisponibilidad(disponibilidadFacade.find(disponibilidad.getIdDisponibilidad()));
        zonaComunalFacade.edit(zonaComunal);
    }*/
    public List<Disponibilidad> consultarTodos() {
        return disponibilidadFacade.findAll();
    }

    public String consultarZona(int id) {
        zonaComunal = zonaComunalFacade.find(id);
        return "detalle-zona";
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

    public Disponibilidad getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Disponibilidad disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public boolean isDomingo() {
        return domingo;
    }

    public void setDomingo(boolean domingo) {
        this.domingo = domingo;
    }

    public boolean isLunes() {
        return lunes;
    }

    public void setLunes(boolean lunes) {
        this.lunes = lunes;
    }

    public boolean isMartes() {
        return martes;
    }

    public void setMartes(boolean martes) {
        this.martes = martes;
    }

    public boolean isMiercoles() {
        return miercoles;
    }

    public void setMiercoles(boolean miercoles) {
        this.miercoles = miercoles;
    }

    public boolean isJueves() {
        return jueves;
    }

    public void setJueves(boolean jueves) {
        this.jueves = jueves;
    }

    public boolean isViernes() {
        return viernes;
    }

    public void setViernes(boolean viernes) {
        this.viernes = viernes;
    }

    public boolean isSabado() {
        return sabado;
    }

    public void setSabado(boolean sabado) {
        this.sabado = sabado;
    }

}
