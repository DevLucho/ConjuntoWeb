/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.FichaIngreso;
import entidades.Inmueble;
import entidades.Vigilante;
import facade.FichaIngresoFacade;
import facade.InmuebleFacade;
import facade.VigilanteFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author Sebastian
 */
@Named(value = "fichadeIngresoControlador")
@SessionScoped
public class FichadeIngresoControlador implements Serializable {

    /**
     * Creates a new instance of FichadeIngresoControlador
     */
    public FichadeIngresoControlador() {
    }

    FichaIngreso fichaIngreso;
    Vigilante vigilante;
    Inmueble inmueble;

    @EJB
    FichaIngresoFacade fichaIngresoFacade;
    @EJB
    VigilanteFacade vigilanteFacade;
    @EJB
    InmuebleFacade inmuebleFacade;

    public FichaIngreso getFichaIngreso() {
        return fichaIngreso;
    }

    public void setFichaIngreso(FichaIngreso fichaIngreso) {
        this.fichaIngreso = fichaIngreso;
    }

    public Vigilante getVigilante() {
        return vigilante;
    }

    public void setVigilante(Vigilante vigilante) {
        this.vigilante = vigilante;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    @PostConstruct
    public void init() {
        fichaIngreso = new FichaIngreso();
        vigilante = new Vigilante();
        inmueble = new Inmueble();
    }

    public void registrar() {
        fichaIngreso.setEstadoFicha("Activo");
        fichaIngreso.setIdVigilante(vigilanteFacade.find(vigilante.getIdVigilante()));
        fichaIngreso.setIdInmueble(inmuebleFacade.find(inmueble.getIdInmueble()));
        fichaIngresoFacade.create(fichaIngreso);
    }

    public List<FichaIngreso> consultar() {
        return fichaIngresoFacade.findAll();
    }

    public String preActualizar(FichaIngreso fichaIngresoActualizar) {
        fichaIngreso = fichaIngresoActualizar;
        vigilante = fichaIngresoActualizar.getIdVigilante();
        inmueble = fichaIngresoActualizar.getIdInmueble();
        return "editarVisitante";
    }

    public String actualizar() {
        fichaIngreso.setIdVigilante(vigilanteFacade.find(vigilante.getIdVigilante()));
        fichaIngreso.setIdInmueble(inmuebleFacade.find(inmueble.getIdInmueble()));
        fichaIngresoFacade.edit(fichaIngreso);
        return "buscarVisitante";
    }

    public void eliminar(FichaIngreso fichaIngresoEliminar) {
        fichaIngresoFacade.remove(fichaIngresoEliminar);
    }
    public List<FichaIngreso> consultarSQL(){
        SimpleDateFormat hora = new SimpleDateFormat("YYYY-mm-dd HH:mm:ss");
     List<Object[]> listaDos = fichaIngresoFacade.consultarFichas();
        List<FichaIngreso> listaFichaIngreso = new ArrayList();
        try {
        for(Object[] item : listaDos) {
             
            FichaIngreso fichaIngreso = new FichaIngreso();
            Inmueble inmueble = inmuebleFacade.find(Integer.parseInt(item [3].toString()));
            Vigilante vigilante = vigilanteFacade.find(Integer.parseInt(item [7].toString()));
            fichaIngreso.setIdFicha(Integer.parseInt(item[0].toString()));
            fichaIngreso.setNombre(item[1].toString());
            fichaIngreso.setApellido(item[2].toString());
            fichaIngreso.setIdInmueble(inmueble);
            fichaIngreso.setHoraEntrada(hora.parse(item[4].toString()));
            fichaIngreso.setEstadoFicha(item[6].toString());
            fichaIngreso.setIdVigilante(vigilante);
            
            listaFichaIngreso.add(fichaIngreso);
        }
        
            
        } catch (Exception e) {
            System.out.println("error:" + e.getMessage());
        }
        
        return listaFichaIngreso;
    }
}
