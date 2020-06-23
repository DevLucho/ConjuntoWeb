/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.FichaIngreso;
import entidades.Inmueble;
import entidades.Vigilante;
import facade.FichaIngresoFacade;
import facade.InmuebleFacade;
import facade.VigilanteFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author Sebastian
 */
@Named(value = "fichadeIngresoControlador")
@SessionScoped
public class FichadeIngresoControlador implements Serializable {

    /**
     * Creates a new instance of FichadeIngresoControlador
     */
    public FichadeIngresoControlador() {
    }

    FichaIngreso fichaIngreso;
    Vigilante vigilante;
    Inmueble inmueble;

    @EJB
    FichaIngresoFacade fichaIngresoFacade;
    @EJB
    VigilanteFacade vigilanteFacade;
    @EJB
    InmuebleFacade inmuebleFacade;

    public FichaIngreso getFichaIngreso() {
        return fichaIngreso;
    }

    public void setFichaIngreso(FichaIngreso fichaIngreso) {
        this.fichaIngreso = fichaIngreso;
    }

    public Vigilante getVigilante() {
        return vigilante;
    }

    public void setVigilante(Vigilante vigilante) {
        this.vigilante = vigilante;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    @PostConstruct
    public void init() {
        fichaIngreso = new FichaIngreso();
        vigilante = new Vigilante();
        inmueble = new Inmueble();
    }

    public void registrar() {
        fichaIngreso.setEstadoFicha("Activo");
        fichaIngreso.setIdVigilante(vigilanteFacade.find(vigilante.getIdVigilante()));
        fichaIngreso.setIdInmueble(inmuebleFacade.find(inmueble.getIdInmueble()));
        fichaIngresoFacade.create(fichaIngreso);
    }

    public List<FichaIngreso> consultar() {
        return fichaIngresoFacade.findAll();
    }

    public String preActualizar(FichaIngreso fichaIngresoActualizar) {
        fichaIngreso = fichaIngresoActualizar;
        vigilante = fichaIngresoActualizar.getIdVigilante();
        inmueble = fichaIngresoActualizar.getIdInmueble();
        return "editarVisitante";
    }

    public String actualizar() {
        fichaIngreso.setIdVigilante(vigilanteFacade.find(vigilante.getIdVigilante()));
        fichaIngreso.setIdInmueble(inmuebleFacade.find(inmueble.getIdInmueble()));
        fichaIngresoFacade.edit(fichaIngreso);
        return "buscarVisitante";
    }

    public void eliminar(FichaIngreso fichaIngresoEliminar) {
        fichaIngresoFacade.remove(fichaIngresoEliminar);
    }

}
