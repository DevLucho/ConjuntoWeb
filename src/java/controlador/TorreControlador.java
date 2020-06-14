/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Torre;
import facade.TorreFacade;
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
@Named(value = "torreControlador")
@SessionScoped
public class TorreControlador implements Serializable {

    /**
     * Creates a new instance of TorreControlador
     */
    
    private Torre torre;
    
    @EJB
    TorreFacade torreFacade;
    
    public TorreControlador() {
    }
    
    @PostConstruct
    public void init(){
        torre = new Torre();
    }
    
    public List<Torre> cosultarTodos(){
        return torreFacade.findAll();
    }

    public Torre getTorre() {
        return torre;
    }

    public void setTorre(Torre torre) {
        this.torre = torre;
    }
    
}
