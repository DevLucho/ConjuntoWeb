/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.FichaIngreso;
import entidades.Inmueble;
import entidades.Vigilante;
import entidades.Visitante;
import facade.FichaIngresoFacade;
import facade.InmuebleFacade;
import facade.VigilanteFacade;
import facade.VisitanteFacade;
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
@Named(value = "visitanteControlador")
@SessionScoped
public class VisitanteControlador implements Serializable {

    /**
     * Creates a new instance of VisitanteControlador
     */
    public VisitanteControlador() {
    }
    Visitante visitante;
    FichaIngreso fichaIngreso;
    Inmueble inmueble;
    Vigilante vigilante;

    @PostConstruct
    public void init() {
        visitante = new Visitante();
        fichaIngreso = new FichaIngreso();
        inmueble = new Inmueble();
        vigilante = new Vigilante();
    }

    @EJB
    VisitanteFacade visitanteFacade;

    @EJB
    FichaIngresoFacade fichaIngresoFacade;

    @EJB
    InmuebleFacade inmuebleFacade;

    @EJB
    VigilanteFacade vigilanteFacade;

    public Visitante getVisitante() {
        return visitante;
    }

    public void setVisitante(Visitante visitante) {
        this.visitante = visitante;
    }

    public FichaIngreso getFichaIngreso() {
        return fichaIngreso;
    }

    public void setFichaIngreso(FichaIngreso fichaIngreso) {
        this.fichaIngreso = fichaIngreso;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public Vigilante getVigilante() {
        return vigilante;
    }

    public void setVigilante(Vigilante vigilante) {
        this.vigilante = vigilante;
    }

    public void registrar() {
        fichaIngreso.setIdInmueble(inmuebleFacade.find(inmueble.getIdInmueble()));
        fichaIngreso.setIdVigilante(vigilanteFacade.find(vigilante.getIdVigilante()));
        fichaIngresoFacade.create(fichaIngreso);
        visitante.setIdFicha(fichaIngreso);
        visitanteFacade.create(visitante);
    }

    public String preActulizr(Visitante visitanteActualizar) {
        visitante = visitanteActualizar;
        fichaIngreso = visitanteActualizar.getIdFicha();
        inmueble = visitanteActualizar.getIdFicha().getIdInmueble();
        vigilante = visitanteActualizar.getIdFicha().getIdVigilante();
        return "editarVisitante";
    }

    public String actualizar() {
        fichaIngreso.setIdInmueble(inmuebleFacade.find(inmueble.getIdInmueble()));
        fichaIngreso.setIdVigilante(vigilanteFacade.find(vigilante.getIdVigilante()));
        fichaIngresoFacade.edit(fichaIngreso);
        visitante.setIdFicha(fichaIngresoFacade.find(fichaIngreso.getIdFicha()));
        visitanteFacade.edit(visitante);
        return "buscarVisitante";
    }

    public void eliminar(Visitante visitanteEliminar) {
        visitanteFacade.remove(visitanteEliminar);
    }

    public List<Visitante> consultarVisitante() {
        return visitanteFacade.findAll();
    }

    public String consultarID(int id) {
        visitante = visitanteFacade.find(id);
        return "buscarVisitante";
    }
}
