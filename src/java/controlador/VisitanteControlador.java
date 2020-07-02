/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.FichaIngreso;
import entidades.Inmueble;
import entidades.Parqueadero;
import entidades.Vehiculo;
import entidades.Vigilante;
import entidades.Visitante;
import facade.FichaIngresoFacade;
import facade.InmuebleFacade;
import facade.ParqueaderoFacade;
import facade.VehiculoFacade;
import facade.VigilanteFacade;
import facade.VisitanteFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author Sebastian
 */
@Named(value = "visitanteControlador")
@SessionScoped
public class VisitanteControlador implements Serializable {

    private String horaEntrada = "";
    private String horaSalida = "";

    /**
     * Creates a new instance of VisitanteControlador
     */
    public VisitanteControlador() {
    }
    Visitante visitante;
    FichaIngreso fichaIngreso;
    Inmueble inmueble;
    Vigilante vigilante;
    private Vehiculo vehiculo;
    private Parqueadero parqueadero;

    @PostConstruct
    public void init() {
        visitante = new Visitante();
        fichaIngreso = new FichaIngreso();
        inmueble = new Inmueble();
        vigilante = new Vigilante();
        vehiculo = new Vehiculo();
        parqueadero = new Parqueadero();
    }

    @EJB
    VisitanteFacade visitanteFacade;

    @EJB
    FichaIngresoFacade fichaIngresoFacade;

    @EJB
    InmuebleFacade inmuebleFacade;

    @EJB
    VigilanteFacade vigilanteFacade;
    
    @EJB
    VehiculoFacade vehiculoFacade;
    
    @EJB
    ParqueaderoFacade parqueaderoFacade;

    @Inject
    private MensajeControlador mensaje;

    public Visitante getVisitante() {
        return visitante;
    }

    public void setVisitante(Visitante visitante) {
        this.visitante = visitante;
    }

    public FichaIngreso getFichaIngreso() {
        return fichaIngreso;
    }

    public void setFichaIngreso(FichaIngreso fichaIngreso) {
        this.fichaIngreso = fichaIngreso;
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
    
    

    public void registrar() {
        fichaIngreso.setIdInmueble(inmuebleFacade.find(inmueble.getIdInmueble()));
        fichaIngreso.setIdVigilante(vigilanteFacade.find(vigilante.getIdVigilante()));
        fichaIngreso.setEstadoFicha("Activo");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        horaEntrada = dateFormat.format(date);
        fichaIngreso.setHoraEntrada(date);
        fichaIngresoFacade.create(fichaIngreso);
        visitante.setIdFicha(fichaIngreso);
        visitanteFacade.create(visitante);
        if ("Si".equals(visitante.getVehiculo())){
            vehiculo.setIdVisitante(visitante);
            vehiculo.setIdParqueadero(parqueaderoFacade.find(parqueadero.getIdParqueadero()));
            
        }
        fichaIngreso = new FichaIngreso();
        inmueble = new Inmueble();
        vigilante = new Vigilante();
        visitante = new Visitante();
        mensaje.setMensaje("RegistrarVisitante('success','Ficha de visitante creada','Para buscar datos, <br> modificar datos o agregar <br> datos, ingresar a visitantes <br><br>');");
    }
    
    public void salida(FichaIngreso fichaIngresoSalida){
        fichaIngreso = fichaIngresoSalida;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        horaSalida = dateFormat.format(date);
        fichaIngreso.setHoraSalida(date);
        fichaIngreso.setEstadoFicha("Inactivo");
        fichaIngresoFacade.edit(fichaIngresoSalida);  
        mensaje.setMensaje("ConfirmarSalida('info','Salida Registrada','El visitante ha abandonado, <br> el conjunto residencial <br><br>');");
    }
    
    public String preActulizr(Visitante visitanteActualizar) {
        visitante = visitanteActualizar;
        fichaIngreso = visitanteActualizar.getIdFicha();
        inmueble = visitanteActualizar.getIdFicha().getIdInmueble();
        vigilante = visitanteActualizar.getIdFicha().getIdVigilante();
        return "editarVisitante";
    }

    public void actualizar() {
        fichaIngreso.setIdInmueble(inmuebleFacade.find(inmueble.getIdInmueble()));
        fichaIngreso.setIdVigilante(vigilanteFacade.find(vigilante.getIdVigilante()));
        fichaIngresoFacade.edit(fichaIngreso);
        visitante.setIdFicha(fichaIngresoFacade.find(fichaIngreso.getIdFicha()));
        visitanteFacade.edit(visitante);
        mensaje.setMensaje("EdicionVisitante('buscarVisitante.xhtml','Ficha de visitante modificada','Para buscar datos, <br> modificar datos o agregar <br> datos, ingresar a visitantes <br><br>');");
        
    }

    public void eliminar(Visitante visitanteEliminar) {
        visitanteFacade.remove(visitanteEliminar);
        mensaje.setMensaje("EliminarVisitante();");
    }

    public List<Visitante> consultarVisitante() {
        return visitanteFacade.findAll();
    }

    public String consultarID(int id) {
        visitante = visitanteFacade.find(id);
        return "buscarVisitante";
    }
    
    public int contarVisitantes(){
        return visitanteFacade.count();
    }
    
     public List<Visitante> consultaSQL(){
        SimpleDateFormat hora = new SimpleDateFormat("YYYY-mm-dd HH:mm:ss");
        List<Object[]> listaTres = visitanteFacade.consultarVis();
        List<Visitante> listaVisitante = new ArrayList();
        try {
            for(Object[] iten : listaTres){
                Visitante visitante = new Visitante();
                FichaIngreso fichaIngreso = fichaIngresoFacade.find(Integer.parseInt(iten[3].toString()));
                visitante.setIdVisitante(Integer.parseInt(iten[0].toString()));
                visitante.setDocumento(Integer.parseInt(iten[1].toString()));
                visitante.setVehiculo(iten[2].toString());
                visitante.setIdFicha(fichaIngreso);
                listaVisitante.add(visitante);
            }
        } catch (Exception e) {
            System.out.println("errormmm: " + e.getMessage());
        }
        return listaVisitante;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Parqueadero getParqueadero() {
        return parqueadero;
    }

    public void setParqueadero(Parqueadero parqueadero) {
        this.parqueadero = parqueadero;
    }
}
