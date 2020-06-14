/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.ZonaComunal;
import facade.ZonaComunalFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author HUERTAS
 */
@Named(value = "zonaComunalControlador")
@SessionScoped
public class ZonaComunalControlador implements Serializable {

    /**
     * Creates a new instance of ZonaComunalControlador
     */
    
    private ZonaComunal zonaComunal;
    
    @EJB
    ZonaComunalFacade zonaComunalFacade;
    
    public ZonaComunalControlador() {
    }
    
    @PostConstruct
    public void init(){
        zonaComunal = new ZonaComunal();
    }
    
    public void registrar(){
        zonaComunalFacade.create(zonaComunal);
        zonaComunal = new ZonaComunal();
    }

    public ZonaComunal getZonaComunal() {
        return zonaComunal;
    }

    public void setZonaComunal(ZonaComunal zonaComunal) {
        this.zonaComunal = zonaComunal;
    }
    
}
