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

    @EJB
    TorreFacade torreFacade;

    public ParqueaderoControlador() {
    }

    @PostConstruct
    public void init() {
        parqueadero = new Parqueadero();
    }

    public void registrar() {
        parqueaderoFacade.create(parqueadero);
        parqueadero = new Parqueadero();
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
    }

    public List<Parqueadero> consultarTodos() {
        return parqueaderoFacade.findAll();
    }
    public List<String> consultarRolNombre() {
        List<String> nombres = new ArrayList<>();
        List<Torre> roleList = torreFacade.findAll();
        for (int i = 0; i < roleList.size(); i++) {
            String nameRol = roleList.get(i).getIdTorre().toString();
            nombres.add('"' + nameRol + '"');
        }

        return nombres;
    }
    
    public List<Integer> contarResZon(){
        List<Integer> data = new ArrayList<>();
        List<Torre> listaTorreRe = torreFacade.findAll();
        for (int i = 0; i < listaTorreRe.size(); i++) {
            System.out.println(listaTorreRe.get(i).getIdTorre());
            int dataZon = parqueaderoFacade.contarParqueaderoR(listaTorreRe.get(i).getIdTorre());
            data.add(dataZon);
        }
        return data;
    }
    
    public List<Parqueadero> consultarP(String estado){
        return parqueaderoFacade.consultarP(estado);
    }

    public Parqueadero getParqueadero() {
        return parqueadero;
    }

    public void setParqueadero(Parqueadero parqueadero) {
        this.parqueadero = parqueadero;
    }

}
