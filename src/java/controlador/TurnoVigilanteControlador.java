/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.TurnoVigilante;
import facade.TurnoVigilanteFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author Huertas
 */
@Named(value = "turnoVigilanteControlador")
@SessionScoped
public class TurnoVigilanteControlador implements Serializable {

    /**
     * Creates a new instance of TurnoVigilanteControlador
     */
    
    private TurnoVigilante turnoVigilante ;
    
    @EJB
    TurnoVigilanteFacade turnoVigilanteFacade;
    
    public TurnoVigilanteControlador() {
    }
    
    @PostConstruct
    public void init(){
        turnoVigilante = new TurnoVigilante();
    }
    
    public void registrar(){
        turnoVigilanteFacade.create(turnoVigilante);
    }

    public TurnoVigilante getTurnoVigilante() {
        return turnoVigilante;
    }

    public void setTurnoVigilante(TurnoVigilante turnoVigilante) {
        this.turnoVigilante = turnoVigilante;
    }
    
}
