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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

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
    @Inject
    private HoraControlador hora;
    @Inject
    private MensajeControlador mensaje;
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
    
    //variables form
    private String nombre;
    private String apellido;
    private int inmuebl;
    private int vigilant;
    private int paque;
    private int empres;
    
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
        fichaIngreso.setIdInmueble(inmuebleFacade.find(inmuebl));
        fichaIngreso.setIdVigilante(vigilanteFacade.find(vigilant));
        fichaIngreso.setNombre(nombre);
        fichaIngreso.setApellido(apellido);
        fichaIngreso.setEstadoFicha("Activo");
        fichaIngreso.setHoraEntrada(hora.now());
        fichaIngresoFacade.create(fichaIngreso);
        domiciliario.setIdEmpresa(empresaFacade.find(empres));
        domiciliario.setIdPaquete(paqueteFacade.find(paque));
        domiciliario.setIdFicha(fichaIngreso);
        domiciliarioFacade.create(domiciliario);
        fichaIngreso = new FichaIngreso();
        inmueble = new Inmueble();
        vigilante = new Vigilante();
        empresa = new Empresa();
        paquete = new Paquete();
        domiciliario = new Domiciliario();
        nombre = null;
        apellido = null;
        inmuebl=0;
        vigilant=0;
        paque=0;
        empres=0;
        mensaje.setMensaje("RegistrarVisitante('success','Ficha de domiciliario creada','Tiempo disponible para entregar el, <br> domicilio maximo de 10 minutos,<br>para cualquier modificacion ingresar al menu<br> de Domiciliario.<br><br>');");
    }

    /*public static void delaySegundos(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }
    public static void pepe{
    for(int min=0;min < 2; min++){
            for(int seg=0;seg<60;seg++){
                //System.out.println(min+"+"+seg);
                delaySegundos();
                if (min == 1) {
                    mensaje.setMensaje("RegistrarVisitante('success','Ficha de domiciliario creada','Tiempo disponible para entregar el, <br> domicilio maximo de 10 minutos,<br>para cualquier modificacion ingresar al menu<br> de Domiciliario.<br><br>');");
                }
            }
        }
    }*/
    public void salida(FichaIngreso fichaIngresoSalida) {
        fichaIngreso = fichaIngresoSalida;
        fichaIngreso.setHoraSalida(hora.now());
        fichaIngreso.setEstadoFicha("Inactivo");
        fichaIngresoFacade.edit(fichaIngresoSalida);
        mensaje.setMensaje("ConfirmarSalida('info','Salida Registrada','El domiciliario ha abandonado, <br> el conjunto residencial <br><br>');");
    }

    public List<Domiciliario> consultar() {
        return domiciliarioFacade.findAll();
    }

    public List<Domiciliario> consultarEstadoD(String estadoFicha) {
        return domiciliarioFacade.fichaBloqueadaD(estadoFicha);
    }

    public List<Domiciliario> fichaDomiciliario(int idInmueble) {
        return domiciliarioFacade.fichaDomiciliario(idInmueble);
    }

    public int contarDomiciliarioR(int idInmueble) {
        return domiciliarioFacade.contarDomiclioR(idInmueble);
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

    public void actualizar() {
        fichaIngreso.setIdInmueble(inmuebleFacade.find(inmueble.getIdInmueble()));
        fichaIngreso.setIdVigilante(vigilanteFacade.find(vigilante.getIdVigilante()));
        fichaIngresoFacade.edit(fichaIngreso);

        domiciliario.setIdEmpresa(empresaFacade.find(empresa.getIdEmpresa()));
        domiciliario.setIdPaquete(paqueteFacade.find(paquete.getIdPaquete()));
        domiciliario.setIdFicha(fichaIngresoFacade.find(fichaIngreso.getIdFicha()));
        domiciliarioFacade.edit(domiciliario);
        mensaje.setMensaje("EdicionVisitante('buscarDomiciliario.xhtml','Ficha de domiciliario modificada','Tiempo disponible para entregar el, <br> domicilio maximo de 10 minutos,<br>para cualquier modificacion ingresar al menu<br> de Domiciliario.<br><br>');");
    }

    public void eliminar(Domiciliario domiciliarioEliminar) {
        domiciliarioFacade.remove(domiciliarioEliminar);
    }

    public String consultarID(int id) {
        domiciliario = domiciliarioFacade.find(id);
        return "buscarDomiciliario";
    }

    public void validarLong(FacesContext context, UIComponent comp, Object value) {
        context = FacesContext.getCurrentInstance();
        String texto = (String) value;

        if (texto.length() < 4 || texto.length() > 10) {
            ((UIInput) comp).setValid(false);
            context.addMessage(comp.getClientId(context), new FacesMessage("tamaño no valido"));
        }
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

    public int getPaque() {
        return paque;
    }

    public void setPaque(int paque) {
        this.paque = paque;
    }

    public int getEmpres() {
        return empres;
    }

    public void setEmpres(int empres) {
        this.empres = empres;
    }
}
