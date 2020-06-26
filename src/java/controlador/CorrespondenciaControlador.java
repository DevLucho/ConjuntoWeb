/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Correspondencia;
import entidades.Empresa;
import entidades.Inmueble;
import entidades.Paquete;
import entidades.Vigilante;
import facade.CorrespondenciaFacade;
import facade.EmpresaFacade;
import facade.InmuebleFacade;
import facade.PaqueteFacade;
import facade.VigilanteFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author Sebastian
 */
@Named(value = "correspondenciaControlador")
@SessionScoped
public class CorrespondenciaControlador implements Serializable {

    /**
     * Creates a new instance of CorrespondenciaControlador
     */
    public CorrespondenciaControlador() {
    }

    Correspondencia correspondencia;
    Inmueble inmueble;
    Paquete paquete;
    Empresa empresa;
    Vigilante vigilante;

    @EJB
    CorrespondenciaFacade correspondenciaFacade;
    @EJB
    InmuebleFacade inmuebleFacade;
    @EJB
    PaqueteFacade paqueteFacade;
    @EJB
    EmpresaFacade empresaFacade;
    @EJB
    VigilanteFacade vigilanteFacade;
    
    
    @Inject
    private MensajeControlador mensaje;
    
    public Correspondencia getCorrespondencia() {
        return correspondencia;
    }

    public void setCorrespondencia(Correspondencia correspondencia) {
        this.correspondencia = correspondencia;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
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

    public Vigilante getVigilante() {
        return vigilante;
    }

    public void setVigilante(Vigilante vigilante) {
        this.vigilante = vigilante;
    }

    @PostConstruct
    public void init() {
        correspondencia = new Correspondencia();
        inmueble = new Inmueble();
        paquete = new Paquete();
        empresa = new Empresa();
        vigilante = new Vigilante();
    }

    public void registrar() {
        correspondencia.setIdEmpresa(empresaFacade.find(empresa.getIdEmpresa()));
        correspondencia.setIdInmueble(inmuebleFacade.find(inmueble.getIdInmueble()));
        correspondencia.setIdPaquete(paqueteFacade.find(paquete.getIdPaquete()));
        correspondencia.setIdVigilante(vigilanteFacade.find(vigilante.getIdVigilante()));
        correspondencia.setEstado("No reclamado");
        correspondenciaFacade.create(correspondencia);
        empresa = new Empresa();
        inmueble = new Inmueble();
        paquete = new Paquete();
        vigilante = new Vigilante();
        correspondencia = new Correspondencia();
        mensaje.setMensaje("RegistrarVisitante('Correspondencia Registrada','Para buscar datos, <br> modificar datos o agregar<br>datos, ingrese al menu de Correspondencia.<br><br>');");
    }

    public List<Correspondencia> consultar() {
        return correspondenciaFacade.findAll();
    }

    public String preActualizar(Correspondencia correspondenciaActualizar) {
        correspondencia = correspondenciaActualizar;
        inmueble = correspondenciaActualizar.getIdInmueble();
        paquete = correspondenciaActualizar.getIdPaquete();
        empresa = correspondenciaActualizar.getIdEmpresa();
        vigilante = correspondenciaActualizar.getIdVigilante();
        return "editarMensajeria";
    }

    public void actualizar() {
        correspondencia.setIdInmueble(inmuebleFacade.find(inmueble.getIdInmueble()));
        correspondencia.setIdPaquete(paqueteFacade.find(paquete.getIdPaquete()));
        correspondencia.setIdEmpresa(empresaFacade.find(empresa.getIdEmpresa()));
        correspondencia.setIdVigilante(vigilanteFacade.find(vigilante.getIdVigilante()));
        correspondenciaFacade.edit(correspondencia);
        mensaje.setMensaje("EdicionVisitante('buscarMensajeria.xhtml','Correspondencia Modificada','Para buscar datos, <br> modificar datos o agregar<br>datos, ingrese al menu de Correspondencia.<br><br>');");
    }

    public void eliminar(Correspondencia correspondenciaEliminar) {
        correspondenciaFacade.remove(correspondenciaEliminar);
    }

    public String consultarID(int id) {
        correspondencia = correspondenciaFacade.find(id);
        return "ListaPaquete";
    }
}
