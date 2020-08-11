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
    private String nombre;

    @EJB
    RolFacade rolFacade;

    public RolControlador() {
    }

    @PostConstruct
    public void init() {
        rol = new Rol();
    }

    public void registrar() {
        rol.setNombre(nombre);
        rolFacade.create(rol);
        rol = new Rol();
        mensaje.setMensaje("Mensaje('Exito!','Rol "+nombre+" creado satisfactoriamente','success');");
        nombre="";
    }

    public void eliminar(Rol rolEliminar) {
        if (rolEliminar.getIdRol() == 1 || rolEliminar.getIdRol() == 2 || rolEliminar.getIdRol() == 3) {
            mensaje.setMensaje("Mensajes('Error!','No puedes eliminar el rol "+rolEliminar.getNombre()+"','error');");
        } else {
            rolFacade.remove(rolEliminar);
            mensaje.setMensaje("Mensajes('Exito!','El rol "+rolEliminar.getNombre()+" fue eliminado satistactoriamente','success');");
        }
    }

    public String preActualizar(Rol rolActualizar) {
        rol = rolActualizar;
        return "editar-rol";
    }

    public void actualizar() {
        rolFacade.edit(rol);
        mensaje.setMensaje("Mensaje('Exito!','Rol "+rol.getNombre()+" modificado satisfactoriamente','success');");
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
