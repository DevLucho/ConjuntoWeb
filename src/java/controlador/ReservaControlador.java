/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Reserva;
import entidades.Residente;
import entidades.ZonaComunal;
import facade.ReservaFacade;
import facade.ResidenteFacade;
import facade.ZonaComunalFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author Huertas
 */
@Named(value = "reservaControlador")
@SessionScoped
public class ReservaControlador implements Serializable {

    /**
     * Creates a new instance of ReservaControlador
     */
    private Residente residente;
    private ZonaComunal zonaComunal;
    private Reserva reserva;

    @EJB
    ResidenteFacade residenteFacade;

    @EJB
    ZonaComunalFacade zonaComunalFacade;

    @EJB
    ReservaFacade reservaFacade;

    public ReservaControlador() {
    }

    @PostConstruct
    public void init() {
        residente = new Residente();
        zonaComunal = new ZonaComunal();
        reserva = new Reserva();
    }

    public int contarReservas() {
        return reservaFacade.count();
    }

    public Residente getResidente() {
        return residente;
    }

    public void setResidente(Residente residente) {
        this.residente = residente;
    }

    public ZonaComunal getZonaComunal() {
        return zonaComunal;
    }

    public void setZonaComunal(ZonaComunal zonaComunal) {
        this.zonaComunal = zonaComunal;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

}
