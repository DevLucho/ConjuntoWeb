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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    private Date fechaSeleccionada;

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

    public void registrar() {
        reserva.setIdZonaComunal(zonaComunalFacade.find(zonaComunal.getIdZonaComunal()));
        reserva.setIdResidente(residenteFacade.find(residente.getIdResidente()));
        reserva.setEstado("Pendiente");

        DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        
        reserva.setFechaInicioReserva(date);
        reserva.setFechaFinReserva(date);
        

        reservaFacade.create(reserva);
    }

    public List<Reserva> consultar() {
        return reservaFacade.findAll();
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

    public Date getFechaSeleccionada() {
        return fechaSeleccionada;
    }

    public void setFechaSeleccionada(Date fechaSeleccionada) {
        this.fechaSeleccionada = fechaSeleccionada;
    }

}
