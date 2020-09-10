/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Evento;
import entidades.Inscripcion;
import entidades.Residente;
import facade.EventoFacade;
import facade.InscripcionFacade;
import facade.ResidenteFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

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
    @Inject
    private MensajeControlador mensaje;
    
    @Inject
    private HoraControlador hora;

    private Inscripcion inscripcion;

    private Residente residente;

    private Evento evento;

    @EJB
    InscripcionFacade inscripcionFacade;

    @EJB
    EventoFacade eventoFacade;

    @EJB
    ResidenteFacade residenteFacade;

    public InscripcionControlador() {
    }

    @PostConstruct
    public void init() {
        evento = new Evento();
        inscripcion = new Inscripcion();
        residente = new Residente();
    }

    public void registrar(int evento) {
        inscripcion.setIdEvento(eventoFacade.find(evento));
        inscripcion.setIdResidente(residenteFacade.find(residente.getIdResidente()));
        inscripcion.setFechaInscripcion(hora.now());
        inscripcion.setEstado("Inscrito");
        inscripcionFacade.create(inscripcion);
        mensaje.setMensaje("Mensajes('Exito!','Se ha inscrito satisfactoriamente al evento','success');");
    }

    public List<Inscripcion> consultarTodos() {
        return inscripcionFacade.findAll();
    }

    public List<Inscripcion> consultarInscripcionVigente() {
        return inscripcionFacade.inscripcionVigente("Inscrito");
    }

    public List<Inscripcion> consultarInscripcionCancelada() {
        return inscripcionFacade.inscripcionCancelada("Cancelado");
    }

    public void cancelar(Inscripcion inscripcionCancelar) {
        if ("Cancelado".equals(inscripcionCancelar.getEstado())) {
            mensaje.setMensaje("Mensajes('Advertencia!','Esta inscripción ya esta cancelada','warning');");
        } else {
            mensaje.setMensaje("Confirmar('Estas seguro que deseas cancelar la inscripción?','No podras revertilo!','warning','Si, cancelar!','Cancelado!','Se ha cancelado la inscripción.','success');");
            inscripcion = inscripcionCancelar;
            inscripcion.setEstado("Cancelado");
            inscripcionFacade.edit(inscripcionCancelar);
        }
    }

    public String consultarInscripcion(int id) {
        inscripcion = inscripcionFacade.find(id);
        return "detalle-inscripcion";
    }

    public List<Inscripcion> estadoInscripcionR(String estado, int idResidente) {
        return inscripcionFacade.estadoInscripcionR(estado, idResidente);
    }

    public List<Inscripcion> inscripcionResidente(int idResidente) {
        return inscripcionFacade.inscripcionResidente(idResidente);
    }

    public int contarInscripciones() {
        return inscripcionFacade.count();
    }

    public int contarInscripcionesVigentes() {
        return inscripcionFacade.contarVigentes("Inscrito");
    }

    public int contarInscripcionesCanceladas() {
        return inscripcionFacade.contarCanceladas("Cancelado");
    }

    public int contarEstadoR(String estado, int idResidente) {
        return inscripcionFacade.contarEstadoR(estado, idResidente);
    }

    public int contarR(int idResidente) {
        return inscripcionFacade.contarR(idResidente);
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

    public Residente getResidente() {
        return residente;
    }

    public void setResidente(Residente residente) {
        this.residente = residente;
    }

    public MensajeControlador getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajeControlador mensaje) {
        this.mensaje = mensaje;
    }

}
