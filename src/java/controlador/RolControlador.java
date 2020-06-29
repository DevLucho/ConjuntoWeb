/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Rol;
import facade.RolFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author HUERTAS
 */
@Named(value = "rolControlador")
@SessionScoped
public class RolControlador implements Serializable {

    /**
     * Creates a new instance of RolControlador
     */
    @Inject
    private MensajeControlador mensaje;
    private Rol rol;

    @EJB
    RolFacade rolFacade;

    public RolControlador() {
    }

    @PostConstruct
    public void init() {
        rol = new Rol();
    }

    public void registrar() {
        rolFacade.create(rol);
        rol = new Rol();
        mensaje.setMensaje("Mensaje('Exito!','Rol creado satisfactoriamente','success');");
    }

    public void eliminar(Rol rolEliminar) {
        if (rolEliminar.getIdRol() == 1 || rolEliminar.getIdRol() == 2 || rolEliminar.getIdRol() == 3) {
            mensaje.setMensaje("Mensajes('Error!','No puedes eliminar este rol','error');");
        } else {
            rolFacade.remove(rolEliminar);
            mensaje.setMensaje("Mensajes('Exito!','Rol eliminado satistactoriamente','success');");
        }
    }

    public String preActualizar(Rol rolActualizar) {
        rol = rolActualizar;
        return "editar-rol";
    }

    public void actualizar() {
        rolFacade.edit(rol);
        mensaje.setMensaje("Mensaje('Exito!','Rol modificado satisfactoriamente','success');");
    }

    public List<Rol> consultarTodos() {
        return rolFacade.findAll();
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

}
