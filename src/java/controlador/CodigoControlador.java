/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Codigo;
import facade.CodigoFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author Huertas
 */
@Named(value = "codigoControlador")
@SessionScoped
public class CodigoControlador implements Serializable {

    /**
     * Creates a new instance of CodigoControlador
     */
    private Codigo codigo;
    private String cod;
    private Codigo codr = null;

    @EJB
    CodigoFacade codigoFacade;

    public CodigoControlador() {
    }

    @PostConstruct
    public void init() {
        codigo = new Codigo();
    }

    public void generarCodigo() {
        codigoFacade.create(codigo);
    }

    public String validarCodigo() {
        codr = codigoFacade.validarCodigo(cod);
        if (codr.getCodigo() != null) {
            return "vista/registro/registro?faces-redirect=true";
        } else {
            return "";
        }
    }

    public Codigo getCodigo() {
        return codigo;
    }

    public void setCodigo(Codigo codigo) {
        this.codigo = codigo;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Codigo getCodr() {
        return codr;
    }

    public void setCodr(Codigo codr) {
        this.codr = codr;
    }

}
