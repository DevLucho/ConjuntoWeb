/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Parqueadero;
import facade.ParqueaderoFacade;
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
@Named(value = "parqueaderoControlador")
@SessionScoped
public class ParqueaderoControlador implements Serializable {

    /**
     * Creates a new instance of ParqueaderoControlador
     */
    private Parqueadero parqueadero;

    @EJB
    ParqueaderoFacade parqueaderoFacade;

    public ParqueaderoControlador() {
    }

    @PostConstruct
    public void init() {
        parqueadero = new Parqueadero();
    }

    public void registrar() {
        parqueaderoFacade.create(parqueadero);
        parqueadero = new Parqueadero();
    }

    public void eliminar(Parqueadero parqueaderoEliminar) {
        parqueaderoFacade.remove(parqueaderoEliminar);
    }

    public String preActulizar(Parqueadero parqueaderoActualizar) {
        parqueadero = parqueaderoActualizar;
        return "editar-parqueadero";
    }

    public void actualizar() {
        parqueaderoFacade.edit(parqueadero);
    }

    public List<Parqueadero> consultarTodos() {
        return parqueaderoFacade.findAll();
    }
    
    public List<Parqueadero> consultarP(String estado){
        return parqueaderoFacade.consultarP(estado);
    }

    public Parqueadero getParqueadero() {
        return parqueadero;
    }

    public void setParqueadero(Parqueadero parqueadero) {
        this.parqueadero = parqueadero;
    }

}
