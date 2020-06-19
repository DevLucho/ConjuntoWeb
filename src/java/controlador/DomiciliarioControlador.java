/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Domiciliario;
import entidades.Empresa;
import entidades.FichaIngreso;
import entidades.Inmueble;
import entidades.Paquete;
import entidades.Vigilante;
import facade.DomiciliarioFacade;
import facade.EmpresaFacade;
import facade.FichaIngresoFacade;
import facade.InmuebleFacade;
import facade.PaqueteFacade;
import facade.VigilanteFacade;
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
@Named(value = "domiciliarioControlador")
@SessionScoped
public class DomiciliarioControlador implements Serializable {

    /**
     * Creates a new instance of DomiciliarioControlador
     */
    public DomiciliarioControlador() {
    }

    private Domiciliario domiciliario;
    private FichaIngreso fichaIngreso;
    private Paquete paquete;
    private Empresa empresa;
    private Inmueble inmueble;
    private Vigilante vigilante;

    @EJB
    DomiciliarioFacade domiciliarioFacade;

    @EJB
    FichaIngresoFacade fichaIngresoFacade;

    @EJB
    PaqueteFacade paqueteFacade;

    @EJB
    EmpresaFacade empresaFacade;

    @EJB
    InmuebleFacade inmuebleFacade;

    @EJB
    VigilanteFacade vigilanteFacade;

    public Domiciliario getDomiciliario() {
        return domiciliario;
    }

    public void setDomiciliario(Domiciliario domiciliario) {
        this.domiciliario = domiciliario;
    }

    public FichaIngreso getFichaIngreso() {
        return fichaIngreso;
    }

    public void setFichaIngreso(FichaIngreso fichaIngreso) {
        this.fichaIngreso = fichaIngreso;
    }

    public Paquete getPaquete() {
        return paquete;
    }

    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public Vigilante getVigilante() {
        return vigilante;
    }

    public void setVigilante(Vigilante vigilante) {
        this.vigilante = vigilante;
    }

    @PostConstruct
    public void init() {
        domiciliario = new Domiciliario();
        fichaIngreso = new FichaIngreso();
        paquete = new Paquete();
        empresa = new Empresa();
        inmueble = new Inmueble();
        vigilante = new Vigilante();
    }

    public void registrar() {
        fichaIngreso.setIdInmueble(inmuebleFacade.find(inmueble.getIdInmueble()));
        fichaIngreso.setIdVigilante(vigilanteFacade.find(vigilante.getIdVigilante()));
        fichaIngresoFacade.create(fichaIngreso);

        domiciliario.setIdEmpresa(empresaFacade.find(empresa.getIdEmpresa()));
        domiciliario.setIdPaquete(paqueteFacade.find(paquete.getIdPaquete()));
        domiciliario.setIdFicha(fichaIngreso);
        domiciliarioFacade.create(domiciliario);
    }

    public List<Domiciliario> consultar() {
        return domiciliarioFacade.findAll();
    }

    public String preActualizar(Domiciliario domiciliarioActualizar) {
        domiciliario = domiciliarioActualizar;
        fichaIngreso = domiciliarioActualizar.getIdFicha();
        inmueble = domiciliarioActualizar.getIdFicha().getIdInmueble();
        vigilante = domiciliarioActualizar.getIdFicha().getIdVigilante();
        empresa = domiciliarioActualizar.getIdEmpresa();
        paquete = domiciliarioActualizar.getIdPaquete();
        return "editarDomiciliario";
    }

    public String actualizar() {
        fichaIngreso.setIdInmueble(inmuebleFacade.find(inmueble.getIdInmueble()));
        fichaIngreso.setIdVigilante(vigilanteFacade.find(vigilante.getIdVigilante()));
        fichaIngresoFacade.edit(fichaIngreso);

        domiciliario.setIdEmpresa(empresaFacade.find(empresa.getIdEmpresa()));
        domiciliario.setIdPaquete(paqueteFacade.find(paquete.getIdPaquete()));
        domiciliario.setIdFicha(fichaIngresoFacade.find(fichaIngreso.getIdFicha()));
        domiciliarioFacade.edit(domiciliario);
        return "buscarDomiciliario";
    }

    public void eliminar(Domiciliario domiciliarioEliminar) {
        domiciliarioFacade.remove(domiciliarioEliminar);
    }

    public String consultarID(int id) {
        domiciliario = domiciliarioFacade.find(id);
        return "buscarDomiciliario";
    }

}
