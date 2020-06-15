/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Permiso;
import facade.PermisoFacade;
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
@Named(value = "permisoControlador")
@SessionScoped
public class PermisoControlador implements Serializable {

    /**
     * Creates a new instance of PermisoControlador
     */
    private Permiso permiso;
    
    @EJB
    PermisoFacade permisoFacade;

    public PermisoControlador() {
    }

    @PostConstruct
    public void init() {
        permiso = new Permiso();
    }

    public List<Permiso> consultarTodos() {
        return permisoFacade.findAll();
    }

    public int contarPermisos() {
        return permisoFacade.count();
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

}
