/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Vehiculo;
import entidades.Parqueadero;
import entidades.Residente;
import entidades.Visitante;
import facade.ParqueaderoFacade;
import facade.ResidenteFacade;
import facade.VehiculoFacade;
import facade.VisitanteFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author oscar
 */
@Named(value = "vehiculoControlador")
@SessionScoped
public class VehiculoControlador implements Serializable {

    /**
     * Creates a new instance of vehiculoControlador
     */
    private Vehiculo vehiculo;    
    private Parqueadero parqueadero;
    private Visitante visitante;
    private Residente residente;
    

    @EJB
    VehiculoFacade vehiculoFacade;    
    
    @EJB
    VisitanteFacade visitanteFacade;
    
    @EJB
    ParqueaderoFacade parqueaderoFacade;
    
    @EJB
    ResidenteFacade residenteFacade;

    public VehiculoControlador() {
    }

    @PostConstruct
    public void init() {
        visitante = new Visitante();
        residente = new Residente();
        vehiculo = new Vehiculo();
        parqueadero = new Parqueadero();
    }

    public void registrar() {
        vehiculo.setIdParqueadero(parqueaderoFacade.find(parqueadero.getIdParqueadero()));
        vehiculo.setIdResidente(residenteFacade.find(residente.getIdResidente()));
        
        vehiculoFacade.create(vehiculo);
        vehiculo = new Vehiculo();
        residente = new Residente();
        visitante = new Visitante();
    }

    public void eliminar(Vehiculo vehiculoEliminar) {
        vehiculoFacade.remove(vehiculoEliminar);
    }

    public String preActulizar(Vehiculo vehiculoActualizar) {
        parqueadero = vehiculoActualizar.getIdParqueadero();
        visitante = vehiculoActualizar.getIdVisitante();
        residente = vehiculoActualizar.getIdResidente();
        vehiculo = vehiculoActualizar;
        return "editar-vehiculo";
    }

    public void actualizar() {
        vehiculo.setIdParqueadero(parqueaderoFacade.find(parqueadero.getIdParqueadero()));
        vehiculo.setIdVisitante(visitanteFacade.find(visitante.getIdVisitante()));
        vehiculo.setIdResidente(residenteFacade.find(residente.getIdResidente()));
        
        vehiculoFacade.edit(vehiculo);
    }

    public List<Vehiculo> consultarTodosResidente() {
        return vehiculoFacade.vehiculoResidente();
    }

    public List<Vehiculo> consultarTodosVisitante() {
        return vehiculoFacade.vehiculoVisitante();
    }

    public List<Vehiculo> consultarTodos() {
        return vehiculoFacade.findAll();
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Parqueadero getParqueadero() {
        return parqueadero;
    }

    public void setParqueadero(Parqueadero parqueadero) {
        this.parqueadero = parqueadero;
    }

    public Visitante getVisitante() {
        return visitante;
    }

    public void setVisitante(Visitante visitante) {
        this.visitante = visitante;
    }

    public Residente getResidente() {
        return residente;
    }

    public void setResidente(Residente residente) {
        this.residente = residente;
    }

    
}
