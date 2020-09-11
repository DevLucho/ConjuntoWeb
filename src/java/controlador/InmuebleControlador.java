/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Apartamento;
import entidades.Inmueble;
import entidades.Torre;
import facade.ApartamentoFacade;
import facade.InmuebleFacade;
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
@Named(value = "inmuebleControlador")
@SessionScoped
public class InmuebleControlador implements Serializable {

    /**
     * Creates a new instance of InmuebleControlador
     */
    
    private Torre torre;
    private Apartamento apartamento;
    private Inmueble inmueble;
    
    @EJB
    TorreFacade torreFacade;
    
    @EJB
    ApartamentoFacade apartamentoFacade;
    
    @EJB
    InmuebleFacade inmuebleFacade;
    
    public InmuebleControlador() {
    }
    
    @PostConstruct
    public void init(){
        torre = new Torre();
        apartamento = new Apartamento();
        inmueble = new Inmueble();
    }
    
    public void registrar(){
        inmueble.setIdTorre(torreFacade.find(torre.getIdTorre()));
        inmueble.setIdApartamento(apartamentoFacade.find(apartamento.getIdApartamento()));
        inmuebleFacade.create(inmueble);
        inmueble = new Inmueble();
    }
    
    public List<Inmueble> consultarTodos(){
        return inmuebleFacade.findAll();
    }
    // Consultar ordenadamente
    public List<Inmueble> findInmueble(){
        return inmuebleFacade.findInmueble();
    }

    public Torre getTorre() {
        return torre;
    }

    public void setTorre(Torre torre) {
        this.torre = torre;
    }

    public Apartamento getApartamento() {
        return apartamento;
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }
    
}
