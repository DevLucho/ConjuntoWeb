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
    
    private Rol rol;
    
    @EJB
    RolFacade rolFacade;
    
    public RolControlador() {
    }
    
    @PostConstruct
    public void init(){
        rol = new Rol();
    }
    
    public void registrar(){
        rolFacade.create(rol);
        rol = new Rol();
    }
    
    public void eliminar(Rol rolEliminar){
        rolFacade.remove(rolEliminar);
    }
    
    public String preActulizar(Rol rolActualizar){
        rol = rolActualizar;
        return "editar-rol";
    }
    
    public void actualizar(){
        rolFacade.edit(rol);
    }
    
    public List<Rol> consultarTodos(){
        return rolFacade.findAll();
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }        
    
}
