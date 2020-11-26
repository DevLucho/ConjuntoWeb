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
import javax.faces.component.UIInput;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.swing.JOptionPane;

/**
 *
 * @author Sebastian
 */
@Named(value = "visitanteControlador")
@SessionScoped
public class VisitanteControlador implements Serializable {

    /**
     * Creates a new instance of VisitanteControlador
     */
    public VisitanteControlador() {
    }
    @Inject
    private HoraControlador hora;
    @Inject
    private MensajeControlador mensaje;
    Visitante visitante;
    FichaIngreso fichaIngreso;
    Inmueble inmueble;
    Vigilante vigilante;
    private Vehiculo vehiculo;
    private Parqueadero parqueadero;
    private List<Visitante> consultaVisitante;
    //variables del form
    private String nombre;
    private String apellido;
    private String placa;
    private int documento;
    private int inmuebl;
    private int vigilant;
    private int parquea;
    private String tipoVehicul;
    private String seleccionAuto;
    
    @PostConstruct
    public void init() {
        visitante = new Visitante();
        fichaIngreso = new FichaIngreso();
        inmueble = new Inmueble();
        vigilante = new Vigilante();
        vehiculo = new Vehiculo();
        parqueadero = new Parqueadero();
        consultaVisitante = new ArrayList();
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
        fichaIngreso.setIdInmueble(inmuebleFacade.find(inmuebl));
        fichaIngreso.setIdVigilante(vigilanteFacade.find(vigilant));
        fichaIngreso.setNombre(nombre);
        fichaIngreso.setApellido(apellido);
        fichaIngreso.setEstadoFicha("Activo");
        fichaIngreso.setHoraEntrada(hora.now());
        fichaIngresoFacade.create(fichaIngreso);
        visitante.setIdFicha(fichaIngreso);
        visitante.setDocumento(documento);
        visitante.setVehiculo(seleccionAuto);
        visitanteFacade.create(visitante);
        if ("Si".equals(visitante.getVehiculo())) {
            vehiculo.setIdVisitante(visitante);
            vehiculo.setPlaca(placa);
            vehiculo.setTipoVehiculo(tipoVehicul);
            vehiculo.setIdParqueadero(parqueaderoFacade.find(parquea));
            vehiculoFacade.create(vehiculo);
        }
        fichaIngreso = new FichaIngreso();
        inmueble = new Inmueble();
        vigilante = new Vigilante();
        visitante = new Visitante();
        nombre = null;
        apellido= null;
        placa= null;
        documento=0;
        inmuebl=0;
        vigilant=0;
        parquea=0;
        tipoVehicul= null;
        seleccionAuto= null;
        mensaje.setMensaje("RegistrarVisitante('success','Ficha de visitante creada','Para buscar datos, <br> modificar datos o agregar <br> datos, ingresar a visitantes <br><br>');");
    }

    public void salida(FichaIngreso fichaIngresoSalida) {
        fichaIngreso = fichaIngresoSalida;
        fichaIngreso.setHoraSalida(hora.now());
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

    public List<Visitante> consultarEstado(String estadoFicha) {
        return visitanteFacade.fichaBloqueada(estadoFicha);
    }

    public List<Visitante> fichaVisitante(int idInmueble) {
        return visitanteFacade.fichaVisitante(idInmueble);
    }

    public String consultarID(int id) {
        visitante = visitanteFacade.find(id);
        return "buscarVisitante";
    }

    public int contarVisitantes() {
        return visitanteFacade.count();
    }

    public int contarVisitantesR(int idInmueble) {
        return visitanteFacade.contarVisitanteR(idInmueble);
    }

    public List<Visitante> consultaSQL() {
        SimpleDateFormat hora = new SimpleDateFormat("YYYY-mm-dd HH:mm:ss");
        List<Object[]> listaTres = visitanteFacade.consultarVis();
        List<Visitante> listaVisitante = new ArrayList();
        try {
            for (Object[] iten : listaTres) {
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

    public List<Visitante> getConsultaVisitante() {
        return consultaVisitante;
    }

    public void setConsultaVisitante(List<Visitante> consultaVisitante) {
        this.consultaVisitante = consultaVisitante;
    }

    public void validarLong(FacesContext context, UIComponent comp, Object value) {
        context = FacesContext.getCurrentInstance();
        String texto = (String) value;

        if (texto.length() < 4 || texto.length() > 10) {
            ((UIInput) comp).setValid(false);
            context.addMessage(comp.getClientId(context), new FacesMessage("tamaño no valido"));
        }
    }

    public void validarplaca(FacesContext context, UIComponent comp, Object value) {
        context = FacesContext.getCurrentInstance();
        String text = (String) value;

        if (text.length() < 5 || text.length() > 8) {
            ((UIInput) comp).setValid(false);
            context.addMessage(comp.getClientId(context), new FacesMessage("tamaño no valido"));
        }
    }

    //public void validarC(FacesContext context, UIComponent comp, Object value){
    //    context = FacesContext.getCurrentInstance();
    //    String texto = (String)value;
    //    if(validarCa(nombre.getBytes(texto))){
    //        JOptionPane.showMessageDialog(rootPane, "correcto");
    //    } else {
    //    }
    //    if(texto.length() < 4 || texto.length() > 10){
    //        ((UIInput)comp).setValid(false);
    //        context.addMessage(comp.getClientId(context),new FacesMessage("tamaño no valido"));
    //    }
    //}
    public static boolean validarCa(String datos) {
        return datos.matches("[a-zA-Z]{1,10}*");
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getInmuebl() {
        return inmuebl;
    }

    public void setInmuebl(int inmuebl) {
        this.inmuebl = inmuebl;
    }

    public int getVigilant() {
        return vigilant;
    }

    public void setVigilant(int vigilant) {
        this.vigilant = vigilant;
    }

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
    }

    public int getParquea() {
        return parquea;
    }

    public void setParquea(int parquea) {
        this.parquea = parquea;
    }

    public String getTipoVehicul() {
        return tipoVehicul;
    }

    public void setTipoVehicul(String tipoVehicul) {
        this.tipoVehicul = tipoVehicul;
    }

    public String getSeleccionAuto() {
        return seleccionAuto;
    }

    public void setSeleccionAuto(String seleccionAuto) {
        this.seleccionAuto = seleccionAuto;
    }

    
}
