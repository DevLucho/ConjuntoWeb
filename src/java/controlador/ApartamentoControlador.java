/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Apartamento;
import facade.ApartamentoFacade;
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
@Named(value = "apartamentoControlador")
@SessionScoped
public class ApartamentoControlador implements Serializable {

    /**
     * Creates a new instance of ApartamentoControlador
     */
    
    private Apartamento apartamento;
    
    @EJB
    ApartamentoFacade apartamentoFacade;
    
    public ApartamentoControlador() {
    }
    
    @PostConstruct
    public void init(){
        apartamento = new Apartamento();
    }
    
    public List<Apartamento> consultarTodos(){
        return apartamentoFacade.findAll();
    }

    public Apartamento getApartamento() {
        return apartamento;
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }
        
}
