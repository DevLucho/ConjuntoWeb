/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Vigilante;
import facade.VigilanteFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author HUERTAS
 */
@Named(value = "vigilanteControlador")
@SessionScoped
public class VigilanteControlador implements Serializable {

    /**
     * Creates a new instance of VigilanteControlador
     */
    private Vigilante vigilante;

    @EJB
    VigilanteFacade vigilanteFacade;

    public VigilanteControlador() {
    }

    public List<Vigilante> consultarTodos() {
        return vigilanteFacade.findAll();
    }

    public int contarVigilantes() {
        return vigilanteFacade.count();
    }

    public Vigilante getVigilante() {
        return vigilante;
    }

    public void setVigilante(Vigilante vigilante) {
        this.vigilante = vigilante;
    }

}
