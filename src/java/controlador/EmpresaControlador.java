/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Empresa;
import facade.EmpresaFacade;
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
@Named(value = "empresaControlador")
@SessionScoped
public class EmpresaControlador implements Serializable {

    /**
     * Creates a new instance of EmpresaControlador
     */
    public EmpresaControlador() {
    }

    Empresa empresa;

    @PostConstruct
    public void init() {
        empresa = new Empresa();
    }

    @EJB
    EmpresaFacade empresaFacade;

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<Empresa> consultar() {
        return empresaFacade.findAll();
    }

    public void registrar() {
        empresaFacade.create(empresa);
    }

    public String preActualizar(Empresa empresaActualizar) {
        empresa = empresaActualizar;
        return "editarDomiciliario";
    }

    public String actualizar() {
        empresaFacade.edit(empresa);
        return "buscarDomiciliario";
    }

    public void eliminar(Empresa empresaEliminar) {
        empresaFacade.remove(empresa);
    }
}
