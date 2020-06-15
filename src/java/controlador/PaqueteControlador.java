/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Paquete;
import facade.PaqueteFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author Sebastian
 */
@Named(value = "paqueteControlador")
@SessionScoped
public class PaqueteControlador implements Serializable {

    /**
     * Creates a new instance of PaqueteControlador
     */
    public PaqueteControlador() {
    }
    
    Paquete paquete;
    
    @EJB
    PaqueteFacade paqueteFacade;

    @PostConstruct
    public void init(){
        paquete = new Paquete();
    }
    
    public Paquete getPaquete() {
        return paquete;
    }

    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
    }
    
    public void registrar(){
        paqueteFacade.create(paquete);
    }
    
    public String preActualizar(Paquete paqueteActualizar){
        paquete = paqueteActualizar;
        return "editarDomiciliario";
    }
    
    public String actualizar(){
        paqueteFacade.edit(paquete);
        return "buscarDomiciliario";
    }
    
    public void eliminar(Paquete paqueteEliminar){
        paqueteFacade.remove(paquete);
    }
    
    public List<Paquete> consultar(){
        return paqueteFacade.findAll();
    }
}
