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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    private String fechaI = "";

    @Inject
    private MensajeControlador mensaje;

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

        DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        fechaI = fecha.format(date);

        inscripcion.setFechaInscripcion(date);
        inscripcion.setEstado("Inscrito");

        inscripcionFacade.create(inscripcion);
        mensaje.setMensaje("Mensajes('Exito!','Se ha inscrito satisfactoriamente al evento','success');");
    }

    public List<Inscripcion> consultarTodos() {
        return inscripcionFacade.findAll();
    }

    public void cancelar(Inscripcion inscripcionCancelar) {
        mensaje.setMensaje("Confirmar('Estas seguro que deseas cancelar la inscripción?','No podras revertilo!','warning','Si, cancelar!','Cancelado!','Se ha cancelado exitosamente la inscripción.','success');");
        inscripcion = inscripcionCancelar;
        inscripcion.setEstado("Cancelado");
        inscripcionFacade.edit(inscripcionCancelar);
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
