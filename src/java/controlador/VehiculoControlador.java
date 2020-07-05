/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Vehiculo;
import facade.VehiculoFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
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

    @EJB
    VehiculoFacade vehiculoFacade;

    public VehiculoControlador() {
    }

    @PostConstruct
    public void init() {
        vehiculo = new Vehiculo();
    }

    public void registrar() {
        vehiculoFacade.create(vehiculo);
        vehiculo = new Vehiculo();
    }

    public void eliminar(Vehiculo vehiculoEliminar) {
        vehiculoFacade.remove(vehiculoEliminar);
    }

    public String preActulizar(Vehiculo vehiculoActualizar) {
        vehiculo = vehiculoActualizar;
        return "editar-vehiculo";
    }

    public void actualizar() {
        vehiculoFacade.edit(vehiculo);
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

}
