/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Evento;
import entidades.Inscripcion;
import facade.EventoFacade;
import facade.InscripcionFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author Huertas
 */
@Named(value = "inscripcionControlador")
@SessionScoped
public class InscripcionControlador implements Serializable {

    /**
     * Creates a new instance of InscripcionControlador
     */
    private Inscripcion inscripcion;

    private Evento evento;

    @EJB
    InscripcionFacade inscripcionFacade;

    @EJB
    EventoFacade eventoFacade;

    public InscripcionControlador() {
    }

    @PostConstruct
    public void init() {
        evento = new Evento();
        inscripcion = new Inscripcion();
    }

    public int contarInscripciones() {
        return inscripcionFacade.count();
    }

    public Inscripcion getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(Inscripcion inscripcion) {
        this.inscripcion = inscripcion;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

}
