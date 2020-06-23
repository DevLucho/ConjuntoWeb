/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Pqrs;
import entidades.Residente;
import entidades.TipoPqrs;
import facade.PqrsFacade;
import facade.ResidenteFacade;
import facade.TipoPqrsFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author Huertas
 */
@Named(value = "pqrsControlador")
@SessionScoped
public class PqrsControlador implements Serializable {

    /**
     * Creates a new instance of PqrsControlador
     */
    private Pqrs pqrs;
    private Residente residente;
    private TipoPqrs tipoPqrs;

    @EJB
    PqrsFacade pqrsFacade;

    @EJB
    ResidenteFacade residenteFacade;

    @EJB
    TipoPqrsFacade tipoPqrsFacade;

    public PqrsControlador() {
    }

    @PostConstruct
    public void init() {
        residente = new Residente();
        tipoPqrs = new TipoPqrs();
        pqrs = new Pqrs();
    }

    public int contarPqrs() {
        return pqrsFacade.count();
    }

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

}
