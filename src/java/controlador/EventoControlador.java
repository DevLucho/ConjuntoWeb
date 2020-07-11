/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Evento;
import entidades.Rol;
import entidades.ZonaComunal;
import facade.EventoFacade;
import facade.RolFacade;
import facade.ZonaComunalFacade;
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
@Named(value = "eventoControlador")
@SessionScoped
public class EventoControlador implements Serializable {

    /**
     * Creates a new instance of EventoControlador
     */
    @Inject
    private MensajeControlador mensaje;

    private Evento evento;

    private ZonaComunal zonaComunal;

    private Rol rol;

    @EJB
    EventoFacade eventoFacade;

    @EJB
    ZonaComunalFacade zonaComunalFacade;

    @EJB
    RolFacade rolFacade;

    public EventoControlador() {
    }

    @PostConstruct
    public void init() {
        evento = new Evento();
        zonaComunal = new ZonaComunal();
        rol = new Rol();
    }

    public void registrar() {
        evento.setEstado("Vigente");
        evento.setIdZonaComunal(zonaComunalFacade.find(zonaComunal.getIdZonaComunal()));
        eventoFacade.create(evento);
        zonaComunal = new ZonaComunal();
        evento = new Evento();
        mensaje.setMensaje("Mensajes('Evento publicado!','Se ha publicado un nuevo evento.','success');");
    }

    public String preActualizar(Evento eventoActualizar) {
        if ("Cancelado".equals(eventoActualizar.getEstado()) || "Finalizado".equals(eventoActualizar.getEstado())) {
            mensaje.setMensaje("Mensajes('Atención!','No puedes editar un evento que no este vigente.','warning');");
        } else {
            zonaComunal = eventoActualizar.getIdZonaComunal();
            evento = eventoActualizar;
            return "editar-evento";
        }
        return "";
    }

    public void actualizar() {
        evento.setIdZonaComunal(zonaComunalFacade.find(zonaComunal.getIdZonaComunal()));
        eventoFacade.edit(evento);
        mensaje.setMensaje("Mensajes('Evento modificado!','Se ha modificado satisfactoriamente el evento.','success');");
    }

    public String consultarEvento(int id) {
        evento = eventoFacade.find(id);
        return "detalle-evento";
    }

    public List<Evento> consultarTodos() {
        return eventoFacade.findAll();
    }

    public int contarEventos() {
        return eventoFacade.count();
    }

    public int contarCancelados() {
        return eventoFacade.countEstado("Cancelado");
    }

    public int contarFinalizados() {
        return eventoFacade.countEstado("Finalizado");
    }

    public int contarVigentes() {
        return eventoFacade.countEstado("Vigente");
    }

    public List<Evento> consultarCancelados() {
        return eventoFacade.estadoEvento("Cancelado");
    }

    public List<Evento> consultarVigentes() {
        return eventoFacade.estadoEvento("Vigente");
    }

    public List<Evento> consultarFinalizados() {
        return eventoFacade.estadoEvento("Finalizado");
    }

    public void eliminar(Evento eventoEliminar) {
        eventoFacade.remove(eventoEliminar);
    }

    public void cancelar(Evento eventoCancelar) {
        if ("Cancelado".equals(eventoCancelar.getEstado()) || "Finalizado".equals(eventoCancelar.getEstado())) {
            mensaje.setMensaje("Mensajes('Atención!','Este evento ya ha sido cancelado.','warning');");
        } else {
            mensaje.setMensaje("Confirmar('Estas seguro que deseas cancelar este evento?','No podras revertilo!','warning','Si, cancelar!','Cancelado!','Se ha cancelado exitosamente el evento.','success');");
            evento = eventoCancelar;
            evento.setEstado("Cancelado");
            eventoFacade.edit(evento);
        }
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public ZonaComunal getZonaComunal() {
        return zonaComunal;
    }

    public void setZonaComunal(ZonaComunal zonaComunal) {
        this.zonaComunal = zonaComunal;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public MensajeControlador getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajeControlador mensaje) {
        this.mensaje = mensaje;
    }

}
