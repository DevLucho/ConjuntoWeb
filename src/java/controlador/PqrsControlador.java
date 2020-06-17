/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import entidades.Pqrs;
import entidades.Residente;
import entidades.TipoPqrs;
import facade.PqrsFacade;
import facade.ResidenteFacade;
import facade.TipoPqrsFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author Familia
 */
@Named(value = "pqrsControlador")
@SessionScoped
public class PqrsControlador implements Serializable {

    /**
     * Creates a new instance of PqrsControlador
     */
    public PqrsControlador() {
    }
    
    Pqrs pqrs;
    Residente residente;
    TipoPqrs tipoPqrs;
    
    @EJB
    PqrsFacade pqrsFacade;
    
    @EJB
    ResidenteFacade residenteFacade;
    
    @EJB
    TipoPqrsFacade tipoPqrsFacade;

    public Pqrs getPqrs() {
        return pqrs;
    }

    public void setPqrs(Pqrs pqrs) {
        this.pqrs = pqrs;
    }

    public Residente getResidente() {
        return residente;
    }

    public void setResidente(Residente residente) {
        this.residente = residente;
    }
    
    public TipoPqrs getTipoPqrs() {
        return tipoPqrs;
    }

    public void setTipoPqrs(TipoPqrs tipoPqrs) {
        this.tipoPqrs = tipoPqrs;
    }
    
    @PostConstruct
    public void init(){
        pqrs = new Pqrs();
        residente = new Residente();
        tipoPqrs = new TipoPqrs();
    }
    
    public void registar(){
        pqrs.setIdResidente(residenteFacade.find(residente.getIdResidente()));
        pqrs.setIdTipoPqrs(tipoPqrsFacade.find(tipoPqrs.getIdTipoPqrs()));
        pqrsFacade.create(pqrs);
    }
    
    public List <Pqrs> consultar(){
        return pqrsFacade.findAll();
    }
    
    public String preActualizar(Pqrs pqrsActualizar){
        residente = pqrsActualizar.getIdResidente();
        tipoPqrs = pqrsActualizar.getIdTipoPqrs();
        pqrs = pqrsActualizar;
        return "actualizar_pqrs";
    }
    
    public String actualizar(){
        pqrs.setIdResidente(residente);
        pqrs.setIdTipoPqrs(tipoPqrs);
        pqrsFacade.edit(pqrs);
        return "consultar_mis_solicitudes";
    }
    
    public void eliminar(Pqrs pqrsEliminar){
        pqrsFacade.remove(pqrsEliminar);
    }
    
    public int contarPqrs(){
        return pqrsFacade.count();
    }
}
