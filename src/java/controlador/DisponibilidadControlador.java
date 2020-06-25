/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Disponibilidad;
import facade.DisponibilidadFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author Huertas
 */
@Named(value = "disponibilidadControlador")
@SessionScoped
public class DisponibilidadControlador implements Serializable {

    private String[] disponibilidadDias = {"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};

    private boolean domingo;
    private boolean lunes;
    private boolean martes;
    private boolean miercoles;
    private boolean jueves;
    private boolean viernes;
    private boolean sabado;

    private Disponibilidad disponibilidad;

    @EJB
    DisponibilidadFacade disponibilidadFacade;

    /**
     * Creates a new instance of DisponibilidadControlador
     */
    public DisponibilidadControlador() {
    }

    @PostConstruct
    public void init() {
        domingo = false;
        lunes = false;
        martes = false;
        miercoles = false;
        jueves = false;
        viernes = false;
        sabado = false;
        disponibilidad = new Disponibilidad();
    }

    public void registrar() {
        String diasDisponibilidad = "";

        if (domingo) {
            diasDisponibilidad += disponibilidadDias[0] + ",";
        }
        if (lunes) {
            diasDisponibilidad += disponibilidadDias[1] + ",";
        }
        if (martes) {
            diasDisponibilidad += disponibilidadDias[2] + ",";
        }
        if (miercoles) {
            diasDisponibilidad += disponibilidadDias[3] + ",";
        }
        if (jueves) {
            diasDisponibilidad += disponibilidadDias[4] + ",";
        }
        if (viernes) {
            diasDisponibilidad += disponibilidadDias[5] + ",";
        }
        if (sabado) {
            diasDisponibilidad += disponibilidadDias[6] + ",";
        }
        /*
        disponibilidad.setDias(diasDisponibilidad);
        disponibilidadFacade.create(disponibilidad);
        disponibilidad = new Disponibilidad();
    
        */
        }
    
    public String preActualizar(Disponibilidad disponibilidadActualizar){
        disponibilidad = disponibilidadActualizar;
        return "prueba-d";
    }
    
    public void actualizar(){
        disponibilidadFacade.edit(disponibilidad);
    }

    public String[] getDisponibilidadDias() {
        return disponibilidadDias;
    }

    public void setDisponibilidadDias(String[] disponibilidadDias) {
        this.disponibilidadDias = disponibilidadDias;
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
