/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.ZonaComunal;
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
    private String[] disponiblidad = new String[7];
    
    String [] disponibilidad ={"Domingo","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
    
    private boolean domingo;
    private boolean lunes;
    private boolean martes;
    private boolean miercoles;
    private boolean jueves;
    private boolean viernes;
    private boolean sabado;
    
    private ZonaComunal zonaComunal;

    @EJB
    ZonaComunalFacade zonaComunalFacade;

    public ZonaComunalControlador() {
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
        zonaComunal = new ZonaComunal();
    }

    public void registrar() {
        
        String diasDisponibilidad = "";
        
        if(domingo){
            diasDisponibilidad+=disponibilidad[0]+",";
        }if(lunes){
            diasDisponibilidad+=disponibilidad[1]+",";
        }if(martes){
            diasDisponibilidad+=disponibilidad[2]+",";
        }if(miercoles){
            diasDisponibilidad+=disponibilidad[3]+",";
        }if(jueves){
            diasDisponibilidad+=disponibilidad[4]+",";
        }if(viernes){
            diasDisponibilidad+=disponibilidad[5]+",";
        }if(sabado){
            diasDisponibilidad+=disponibilidad[6]+",";
        }
        zonaComunal.setDisponibilidad(diasDisponibilidad);
        zonaComunalFacade.create(zonaComunal);
        zonaComunal = new ZonaComunal();
    }

    public void eliminar(ZonaComunal zonaComunalEliminar) {
        zonaComunalFacade.remove(zonaComunal);
    }

    public String preActulizar(ZonaComunal zonaComunalActualizar) {
        zonaComunal = zonaComunalActualizar;
        return "editar-zona";
    }

    public void actualizar() {
        zonaComunalFacade.findAll();
    }

    public List<ZonaComunal> consultarTodos() {
        return zonaComunalFacade.findAll();
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

    public String[] getDisponiblidad() {
        return disponiblidad;
    }

    public void setDisponiblidad(String[] disponiblidad) {
        this.disponiblidad = disponiblidad;
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
