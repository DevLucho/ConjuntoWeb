/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Pqrs;
import entidades.Residente;
import entidades.TipoPqrs;
import facade.PqrsFacade;
import facade.ResidenteFacade;
import facade.TipoPqrsFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author Huertas
 */
@Named(value = "pqrsControlador")
@SessionScoped
public class PqrsControlador implements Serializable {

    /**
     * Creates a new instance of PqrsControlador
     */
    private String horaI = "";
    private String fechaI = "";
    private Pqrs pqrs;
    private Residente residente;
    private TipoPqrs tipoPqrs;

    @EJB
    PqrsFacade pqrsFacade;

    @EJB
    ResidenteFacade residenteFacade;

    @EJB
    TipoPqrsFacade tipoPqrsFacade;
        
    @Inject
    private MensajeControlador mensaje;

    public PqrsControlador() {
    }

    @PostConstruct
    public void init() {
        residente = new Residente();
        tipoPqrs = new TipoPqrs();
        pqrs = new Pqrs();
    }

    public void registar() {
        pqrs.setIdResidente(residente);
        pqrs.setIdTipoPqrs(tipoPqrs);
        pqrs.setEstado("Pendiente");

        DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        fechaI = fecha.format(date);
        pqrs.setFecha(date);

        DateFormat hora = new SimpleDateFormat("HH:mm:ss");
        Calendar cale = Calendar.getInstance();
        Date dates = cale.getTime();
        horaI = hora.format(dates);
        pqrs.setHora(dates);

        pqrsFacade.create(pqrs);
        residente = new Residente();
        tipoPqrs = new TipoPqrs();
        pqrs = new Pqrs();
        mensaje.setMensaje("Mensaje('Exito','Se ha generado la PQRS.','success');");

    }

    public List<Pqrs> consultar() {
        return pqrsFacade.findAll();
    }

    public String preActualizar(Pqrs pqrsActualizar) {
        residente = pqrsActualizar.getIdResidente();
        tipoPqrs = pqrsActualizar.getIdTipoPqrs();
        pqrs = pqrsActualizar;
        return "actualizar_pqrs";
    }

    public String actualizar() {
        pqrs.setIdResidente(residente);
        pqrs.setIdTipoPqrs(tipoPqrs);
        pqrsFacade.edit(pqrs);
        return "consultar_mis_solicitudes";
    }

    public void eliminar(Pqrs pqrsEliminar) {
        pqrsFacade.remove(pqrsEliminar);
    }

    public int contarPqrs() {
        return pqrsFacade.count();
    }

    public List<Pqrs> consultarCancelados() {
        return pqrsFacade.estadoPqrs("Cancelado");
    }

    public List<Pqrs> consultarResueltos() {
        return pqrsFacade.estadoPqrs("Resuelto");
    }

    public List<Pqrs> consultarPendientes() {
        return pqrsFacade.estadoPqrs("Pendiente");
    }

    public List<Pqrs> consultarAbiertos() {
        return pqrsFacade.estadoPqrs("Abierto");
    }
    
    public List<Pqrs> estadoPqrsR(String estado, int idResidente){
        return pqrsFacade.estadoPqrsR(estado, idResidente);
    }
    
    public List<Pqrs> pqrsResidente(int idResidente){
        return pqrsFacade.pqrsResidente(idResidente);
    }

    public int contarCancelados() {
        return pqrsFacade.countEstado("Cancelado");
    }

    public int contarResueltos() {
        return pqrsFacade.countEstado("Resuelto");
    }

    public int contarAbierto() {
        return pqrsFacade.countEstado("Abierto");
    }

    public int contarPendiente() {
        return pqrsFacade.countEstado("Pendiente");
    }
    
    public int contarEstadoR(String estado, int idResidente) {
        return pqrsFacade.countEstadoR(estado, idResidente);
    }
    
    public int contarR(int idResidente) {
        return pqrsFacade.countR(idResidente);
    }

    public Pqrs getPqrs() {
        return pqrs;
    }

    public void setPqrs(Pqrs pqrs) {
        this.pqrs = pqrs;
    }

    public Residente getResidente() {
        return residente;
    }

    public void setResidente(Residente residente) {
        this.residente = residente;
    }

    public TipoPqrs getTipoPqrs() {
        return tipoPqrs;
    }

    public void setTipoPqrs(TipoPqrs tipoPqrs) {
        this.tipoPqrs = tipoPqrs;
    }

    public MensajeControlador getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajeControlador mensaje) {
        this.mensaje = mensaje;
    }

}
