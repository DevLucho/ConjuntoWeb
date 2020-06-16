/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Correspondecia;
import entidades.Empresa;
import entidades.Inmueble;
import entidades.Paquete;
import entidades.Vigilante;
import facade.CorrespondeciaFacade;
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
    
    Correspondecia correspondencia;
    Inmueble inmueble;
    Paquete paquete;
    Empresa empresa;
    Vigilante vigilante;
    
    @EJB
    CorrespondeciaFacade correspondenciaFacade;
    @EJB
    InmuebleFacade inmuebleFacade;
    @EJB
    PaqueteFacade paqueteFacade;
    @EJB
    EmpresaFacade empresaFacade;
    @EJB
    VigilanteFacade vigilanteFacade;

    public Correspondecia getCorrespondencia() {
        return correspondencia;
    }

    public void setCorrespondencia(Correspondecia correspondencia) {
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
    public void init(){
        correspondencia = new Correspondecia();
        inmueble = new Inmueble();
        paquete = new Paquete();
        empresa = new Empresa();
        vigilante = new Vigilante();
    }
    
    public void registrar(){
        correspondencia.setIdEmpresa(empresaFacade.find(empresa.getIdEmpresa()));
        correspondencia.setIdInmueble(inmuebleFacade.find(inmueble.getIdInmueble()));
        correspondencia.setIdPaquete(paqueteFacade.find(paquete.getIdPaquete()));
        correspondencia.setIdVigilante(vigilanteFacade.find(vigilante.getIdVigilante()));
        correspondenciaFacade.create(correspondencia);
    }

    public List<Correspondecia> consultar(){
        return correspondenciaFacade.findAll();
    }

    public String preActualizar(Correspondecia correspondenciaActualizar){
        correspondencia = correspondenciaActualizar;
        inmueble = correspondenciaActualizar.getIdInmueble();
        paquete = correspondenciaActualizar.getIdPaquete();
        empresa = correspondenciaActualizar.getIdEmpresa();
        vigilante = correspondenciaActualizar.getIdVigilante();
        return "editarMensajeria";
    }
    
    public String actualizar(){
        correspondencia.setIdInmueble(inmuebleFacade.find(inmueble.getIdInmueble()));
        correspondencia.setIdPaquete(paqueteFacade.find(paquete.getIdPaquete()));
        correspondencia.setIdEmpresa(empresaFacade.find(empresa.getIdEmpresa()));
        correspondencia.setIdVigilante(vigilanteFacade.find(vigilante.getIdVigilante()));
        correspondenciaFacade.edit(correspondencia);
        return "buscarMensajeria";
    }
    
    public void eliminar(Correspondecia correspondenciaEliminar){
        correspondenciaFacade.remove(correspondenciaEliminar);
    }
    
    public String consultarID(int id){
        correspondencia = correspondenciaFacade.find(id);
        return "ListaPaquete";
    }
    
    public int contarCorrespondencia(){
        return correspondenciaFacade.count();
    }
}
