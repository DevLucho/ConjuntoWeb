/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.TipoPqrs;
import facade.TipoPqrsFacade;
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
@Named(value = "tipoPqrsControlador")
@SessionScoped
public class TipoPqrsControlador implements Serializable {

    /**
     * Creates a new instance of TipoPqrControlador
     */
    private TipoPqrs tipoPqrs;

    @EJB
    TipoPqrsFacade tipoPqrsFacade;

    public TipoPqrsControlador() {
    }

    @PostConstruct
    public void init() {
        tipoPqrs = new TipoPqrs();
    }

    public List<TipoPqrs> consultarTodos() {
        return tipoPqrsFacade.findAll();
    }

    public TipoPqrs getTipoPqrs() {
        return tipoPqrs;
    }

    public void setTipoPqrs(TipoPqrs tipoPqrs) {
        this.tipoPqrs = tipoPqrs;
    }

}
