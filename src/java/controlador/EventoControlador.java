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
    public void init(){
        evento = new Evento();
        zonaComunal = new ZonaComunal();
        rol = new Rol();
    }
    
    public void registrar(){
        evento.setIdZonaComunal(zonaComunalFacade.find(zonaComunal.getIdZonaComunal()));
        eventoFacade.create(evento);
        zonaComunal = new ZonaComunal();
        evento = new Evento();
    }
    
    public String preActualizar(Evento eventoActualizar){
        zonaComunal = eventoActualizar.getIdZonaComunal();
        evento = eventoActualizar;
        return "editar-evento";
    }
    
    public void actualizar(){
        evento.setIdZonaComunal(zonaComunalFacade.find(zonaComunal.getIdZonaComunal()));
        eventoFacade.edit(evento);
    }
    
    public List<Evento> consultarTodos(){
        return eventoFacade.findAll();
    }
    
    public int contarEventos(){
        return eventoFacade.count();
    }
    
    public void eliminar(Evento eventoEliminar){
        eventoFacade.remove(eventoEliminar);
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
    
}
