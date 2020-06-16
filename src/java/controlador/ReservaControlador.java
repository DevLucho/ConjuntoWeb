/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;


import entidades.Reserva;
import entidades.Residente;
import entidades.ZonaComunal;
import facade.ReservaFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author Familia
 */
@Named(value = "reservaControlador")
@SessionScoped
public class ReservaControlador implements Serializable {

    /**
     * Creates a new instance of ReservaControlador
     */
    public ReservaControlador() {
    }
    
    Reserva reserva;
    ZonaComunal zonaComunal;
    Residente residente;
    
    @EJB
    ReservaFacade reservaFacade;

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public ZonaComunal getZonaComunal() {
        return zonaComunal;
    }

    public void setZonaComunal(ZonaComunal zonaComunal) {
        this.zonaComunal = zonaComunal;
    }

    public Residente getResidente() {
        return residente;
    }

    public void setResidente(Residente residente) {
        this.residente = residente;
    }
    
    
    @PostConstruct
    public void init(){
        reserva = new Reserva();
        zonaComunal = new ZonaComunal();
        residente = new Residente();
    }
    
    public void registrar(){
        reserva.setIdZonaComunal(zonaComunal);
        reserva.setIdResidente(residente);
        reservaFacade.create(reserva);
    }
    
    public List <Reserva> consultar(){
        return reservaFacade.findAll();
    }
    
    public String preActualizar(Reserva reservaActualizar){
        reserva = reservaActualizar;
        zonaComunal = reservaActualizar.getIdZonaComunal();
        residente = reservaActualizar.getIdResidente();
        return "ActualizarReserva";
    }
    
    public String actualizar(){
        reserva.setIdZonaComunal(zonaComunal);
        reserva.setIdResidente(residente);
        reservaFacade.edit(reserva);
        return "ListaReserva";
    }
}
