/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Pqrs;
import entidades.TipoPqrs;
import facade.PqrsFacade;
import facade.TipoPqrsFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
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

    @EJB
    PqrsFacade pqrsFacade;

    public TipoPqrsControlador() {
    }

    @PostConstruct
    public void init() {
        tipoPqrs = new TipoPqrs();
    }

    public List<TipoPqrs> consultarTodos() {
        return tipoPqrsFacade.findAll();
    }
    
    public List<String> consultarnombrePq(){
        List<String> nombres = new ArrayList<>();
        List<TipoPqrs> tipoList = tipoPqrsFacade.findAll();
        for(int i = 0;i<tipoList.size();i++){
            String nameTipo = tipoList.get(i).getTipo();
            nombres.add('"'+nameTipo+'"');
        }
        return nombres;
    }
    public List<Integer> contarTipoPqrs(){
        List<Integer> data = new ArrayList<>();
        List<TipoPqrs> pqrsList = tipoPqrsFacade.findAll();
        for(int i = 0;i < pqrsList.size();i++){
            int dataUsu =  pqrsFacade.contarPqrsT(pqrsList.get(i).getIdTipoPqrs());
            data.add(dataUsu);
        }
        return data;
    }
    public TipoPqrs getTipoPqrs() {
        return tipoPqrs;
    }

    public void setTipoPqrs(TipoPqrs tipoPqrs) {
        this.tipoPqrs = tipoPqrs;
    }

}
