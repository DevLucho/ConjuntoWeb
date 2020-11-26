/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Parqueadero;
import entidades.Torre;
import facade.ParqueaderoFacade;
import facade.TorreFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author oscar
 */
@Named(value = "parqueaderoControlador")
@SessionScoped
public class ParqueaderoControlador implements Serializable {

    /**
     * Creates a new instance of ParqueaderoControlador
     */
    private Parqueadero parqueadero;
    private Torre torre;

    @EJB
    ParqueaderoFacade parqueaderoFacade;
    
    @Inject
    private MensajeControlador mensaje;
    
    @EJB
    TorreFacade torreFacade;

    //variable form
    private String lugarr;
    private String estaddo;
    
    public ParqueaderoControlador() {
    }

    @PostConstruct
    public void init() {
        parqueadero = new Parqueadero();
    }

    public void registrar() {
        parqueadero.setLugar(lugarr);
        parqueadero.setEstado(estaddo);
        parqueaderoFacade.create(parqueadero);
        parqueadero = new Parqueadero();
        lugarr = null;
        estaddo = null;
        mensaje.setMensaje("Mensajes('Exito!','Parquedero agregado satisfactoriamente','success');");
    }

    public void eliminar(Parqueadero parqueaderoEliminar) {
        parqueaderoFacade.remove(parqueaderoEliminar);
    }

    public String preActulizar(Parqueadero parqueaderoActualizar) {
        parqueadero = parqueaderoActualizar;
        return "editar-parqueadero";
    }

    public void actualizar() {
        parqueaderoFacade.edit(parqueadero);
        mensaje.setMensaje("Mensajes('Exito!','Parquedero Editado satisfactoriamente','success');");
   
    }

    public List<Parqueadero> consultarTodos() {
        return parqueaderoFacade.findAll();
    }

    public List<String> consultarRolNombre() {
        List<String> nombres = new ArrayList<>();
        List<Torre> roleList = torreFacade.findAll();
        try {
            for (int i = 0; i < roleList.size(); i++) {
                String nameRol = roleList.get(i).getIdTorre().toString();
                nombres.add('"' + nameRol + '"');
            }
        } catch (Exception e) {
            System.out.println("Error consulta rol-name:" + e.getMessage());
        }
        return nombres;
    }

    public List<Integer> contarResZon() {
        List<Integer> data = new ArrayList<>();
        List<Torre> listaTorreRe = torreFacade.findAll();
        for (int i = 0; i < listaTorreRe.size(); i++) {
            int dataZon = parqueaderoFacade.contarVehiculos(listaTorreRe.get(i).getIdTorre());
            data.add(dataZon);
        }
        try {

        } catch (Exception e) {
            System.out.println("Error contar res - zona: " + e.getMessage());
        }
        return data;
    }

    public int contartipos(String tipoVehiculo) {
        return parqueaderoFacade.contarVehiculosAuto(tipoVehiculo);
    }

    public List<Parqueadero> consultarP(String estado) {
        return parqueaderoFacade.consultarP(estado);
    }

    public Parqueadero getParqueadero() {
        return parqueadero;
    }

    public void setParqueadero(Parqueadero parqueadero) {
        this.parqueadero = parqueadero;
    }

    public String getLugarr() {
        return lugarr;
    }

    public void setLugarr(String lugarr) {
        this.lugarr = lugarr;
    }

   
    public String getEstaddo() {
        return estaddo;
    }

    public void setEstaddo(String estaddo) {
        this.estaddo = estaddo;
    }

    public MensajeControlador getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajeControlador mensaje) {
        this.mensaje = mensaje;
    }
}
