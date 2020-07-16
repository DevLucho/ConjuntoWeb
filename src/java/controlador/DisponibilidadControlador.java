/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Disponibilidad;
import entidades.DisponibilidadDia;
import entidades.ZonaComunal;
import facade.DisponibilidadDiaFacade;
import facade.DisponibilidadFacade;
import facade.ZonaComunalFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author Huertas
 */
@Named(value = "disponibilidadControlador")
@SessionScoped
public class DisponibilidadControlador implements Serializable {

    @Inject
    private MensajeControlador mensaje;
    // Dias de la semana
    private boolean domingo;
    private boolean lunes;
    private boolean martes;
    private boolean miercoles;
    private boolean jueves;
    private boolean viernes;
    private boolean sabado;

    private ZonaComunal zonaComunal;

    private Disponibilidad disponibilidad;

    private DisponibilidadDia disponibilidadDia;

    private List<Disponibilidad> disponibilidadZona;

    @EJB
    ZonaComunalFacade zonaComunalFacade;

    @EJB
    DisponibilidadFacade disponibilidadFacade;

    @EJB
    DisponibilidadDiaFacade disponibilidadDiaFacade;

    /**
     * Creates a new instance of DisponibilidadControlador
     */
    public DisponibilidadControlador() {
    }

    @PostConstruct
    public void init() {
        zonaComunal = new ZonaComunal();
        domingo = false;
        lunes = false;
        martes = false;
        miercoles = false;
        jueves = false;
        viernes = false;
        sabado = false;
        disponibilidad = new Disponibilidad();
        disponibilidadDia = new DisponibilidadDia();
        disponibilidadZona = new ArrayList();
    }

    public void registrar() {
        zonaComunalFacade.create(zonaComunal);
        if (domingo) {
            disponibilidadDia.setIdDia(1);
            disponibilidad.setIdDia(disponibilidadDia);
            disponibilidad.setIdZonaComunal(zonaComunal);
            disponibilidadFacade.create(disponibilidad);
        }
        if (lunes) {
            disponibilidadDia.setIdDia(2);
            disponibilidad.setIdDia(disponibilidadDia);
            disponibilidad.setIdZonaComunal(zonaComunal);
            disponibilidadFacade.create(disponibilidad);
        }
        if (martes) {
            disponibilidadDia.setIdDia(3);
            disponibilidad.setIdDia(disponibilidadDia);
            disponibilidad.setIdZonaComunal(zonaComunal);
            disponibilidadFacade.create(disponibilidad);
        }
        if (miercoles) {
            disponibilidadDia.setIdDia(4);
            disponibilidad.setIdDia(disponibilidadDia);
            disponibilidad.setIdZonaComunal(zonaComunal);
            disponibilidadFacade.create(disponibilidad);
        }
        if (jueves) {
            disponibilidadDia.setIdDia(5);
            disponibilidad.setIdDia(disponibilidadDia);
            disponibilidad.setIdZonaComunal(zonaComunal);
            disponibilidadFacade.create(disponibilidad);
        }
        if (viernes) {
            disponibilidadDia.setIdDia(6);
            disponibilidad.setIdDia(disponibilidadDia);
            disponibilidad.setIdZonaComunal(zonaComunal);
            disponibilidadFacade.create(disponibilidad);
        }
        if (sabado) {
            disponibilidadDia.setIdDia(7);
            disponibilidad.setIdDia(disponibilidadDia);
            disponibilidad.setIdZonaComunal(zonaComunal);
            disponibilidadFacade.create(disponibilidad);
        }

        this.domingo = false;
        this.lunes = false;
        this.martes = false;
        this.miercoles = false;
        this.jueves = false;
        this.viernes = false;
        this.sabado = false;
        this.disponibilidad = new Disponibilidad();
        this.zonaComunal = new ZonaComunal();
        mensaje.setMensaje("Mensajes('Exito!','Zona común creada satisfactoriamente','success');");
    }

    public String preActualizar(ZonaComunal zonaActualizar) {
        disponibilidadZona = disponibilidadFacade.consultarDisponibilidadZona(zonaActualizar.getIdZonaComunal());
        for (Disponibilidad disponibilidad : disponibilidadZona) {
            if (disponibilidad.getIdDia().getIdDia() == 1) {
                domingo = true;
            }
            if (disponibilidad.getIdDia().getIdDia() == 2) {
                lunes = true;
            }
            if (disponibilidad.getIdDia().getIdDia() == 3) {
                martes = true;
            }
            if (disponibilidad.getIdDia().getIdDia() == 4) {
                miercoles = true;
            }
            if (disponibilidad.getIdDia().getIdDia() == 5) {
                jueves = true;
            }
            if (disponibilidad.getIdDia().getIdDia() == 6) {
                viernes = true;
            }
            if (disponibilidad.getIdDia().getIdDia() == 7) {
                sabado = true;
            }
        }
        disponibilidad = disponibilidadZona.get(0);
        zonaComunal = zonaActualizar;
        return "editar-zona";
    }

    public void actualizar() {
        disponibilidadFacade.eliminarZona(zonaComunal);
        if (domingo) {
            disponibilidadDia.setIdDia(1);
            disponibilidad.setIdDia(disponibilidadDia);
            disponibilidad.setIdZonaComunal(zonaComunal);
            disponibilidadFacade.create(disponibilidad);
        }
        if (lunes) {
            disponibilidadDia.setIdDia(2);
            disponibilidad.setIdDia(disponibilidadDia);
            disponibilidad.setIdZonaComunal(zonaComunal);
            disponibilidadFacade.create(disponibilidad);
        }
        if (martes) {
            disponibilidadDia.setIdDia(3);
            disponibilidad.setIdDia(disponibilidadDia);
            disponibilidad.setIdZonaComunal(zonaComunal);
            disponibilidadFacade.create(disponibilidad);
        }
        if (miercoles) {
            disponibilidadDia.setIdDia(4);
            disponibilidad.setIdDia(disponibilidadDia);
            disponibilidad.setIdZonaComunal(zonaComunal);
            disponibilidadFacade.create(disponibilidad);
        }
        if (jueves) {
            disponibilidadDia.setIdDia(5);
            disponibilidad.setIdDia(disponibilidadDia);
            disponibilidad.setIdZonaComunal(zonaComunal);
            disponibilidadFacade.create(disponibilidad);
        }
        if (viernes) {
            disponibilidadDia.setIdDia(6);
            disponibilidad.setIdDia(disponibilidadDia);
            disponibilidad.setIdZonaComunal(zonaComunal);
            disponibilidadFacade.create(disponibilidad);
        }
        if (sabado) {
            disponibilidadDia.setIdDia(7);
            disponibilidad.setIdDia(disponibilidadDia);
            disponibilidad.setIdZonaComunal(zonaComunal);
            disponibilidadFacade.create(disponibilidad);
        }
        zonaComunalFacade.edit(zonaComunal);
        mensaje.setMensaje("Mensajes('Exito!','Zona común modificada satisfactoriamente','success');");
    }

    public List<Disponibilidad> consultarTodos() {
        return disponibilidadFacade.findAll();
    }

    public String consultarDisponibilidad(ZonaComunal zonaComunal) {
        this.zonaComunal = zonaComunal;
        disponibilidadZona = disponibilidadFacade.consultarDisponibilidadZona(zonaComunal.getIdZonaComunal());
        return "detalle-zona";
    }
    /*
    public List<Disponibilidad> consultaDisponiblidad() {
        for (Disponibilidad disponibilidad : disponibilidadFacade.consultarDisponibilidadZona(zonaComunal.getIdZonaComunal())) {
            System.out.println("Dia: " + disponibilidad.getIdDia().getIdDia());
        }
        return disponibilidadFacade.consultarDisponibilidadZona(zonaComunal.getIdZonaComunal());
    }*/

    public DisponibilidadDia getDisponibilidadDia() {
        return disponibilidadDia;
    }

    public void setDisponibilidadDia(DisponibilidadDia disponibilidadDia) {
        this.disponibilidadDia = disponibilidadDia;
    }

    public ZonaComunal getZonaComunal() {
        return zonaComunal;
    }

    public void setZonaComunal(ZonaComunal zonaComunal) {
        this.zonaComunal = zonaComunal;
    }

    public Disponibilidad getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Disponibilidad disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public boolean isDomingo() {
        return domingo;
    }

    public void setDomingo(boolean domingo) {
        this.domingo = domingo;
    }

    public boolean isLunes() {
        return lunes;
    }

    public void setLunes(boolean lunes) {
        this.lunes = lunes;
    }

    public boolean isMartes() {
        return martes;
    }

    public void setMartes(boolean martes) {
        this.martes = martes;
    }

    public boolean isMiercoles() {
        return miercoles;
    }

    public void setMiercoles(boolean miercoles) {
        this.miercoles = miercoles;
    }

    public boolean isJueves() {
        return jueves;
    }

    public void setJueves(boolean jueves) {
        this.jueves = jueves;
    }

    public boolean isViernes() {
        return viernes;
    }

    public void setViernes(boolean viernes) {
        this.viernes = viernes;
    }

    public boolean isSabado() {
        return sabado;
    }

    public void setSabado(boolean sabado) {
        this.sabado = sabado;
    }

    public MensajeControlador getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajeControlador mensaje) {
        this.mensaje = mensaje;
    }

    public List<Disponibilidad> getDisponibilidadZona() {
        return disponibilidadZona;
    }

    public void setDisponibilidadZona(List<Disponibilidad> disponibilidadZona) {
        this.disponibilidadZona = disponibilidadZona;
    }

}
