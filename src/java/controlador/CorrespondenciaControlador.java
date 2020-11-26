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
import entidades.Residente;
import entidades.Vigilante;
import facade.CorrespondenciaFacade;
import facade.EmpresaFacade;
import facade.InmuebleFacade;
import facade.PaqueteFacade;
import facade.ResidenteFacade;
import facade.VigilanteFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.security.NoSuchProviderException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.mail.MessagingException;

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
    @Inject
    private HoraControlador hora;
    @Inject
    private MensajeControlador mensaje;
    Correspondencia correspondencia;
    Inmueble inmueble;
    Paquete paquete;
    Empresa empresa;
    Vigilante vigilante;
    Residente residente;
    CorreoControlador email;

    List<Residente> residenteList;
    String correo;

    @EJB
    ResidenteFacade residenteFacade;
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
    
    //variables form
    private int inmuebl;
    private String descripcio;
    private int paque;
    private int vigilant;
    private int empre;
    
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
        residente = new Residente();
        correspondencia = new Correspondencia();
        inmueble = new Inmueble();
        paquete = new Paquete();
        empresa = new Empresa();
        vigilante = new Vigilante();
        residenteList = new ArrayList();
        email = new CorreoControlador();
    }

    // consultar inmueble residente
    public List<Residente> inmuebleR(int idInmueble) {
        return correspondenciaFacade.inmuebleR(idInmueble);
    }

    public void registrar() throws NoSuchProviderException, MessagingException {
        correspondencia.setIdEmpresa(empresaFacade.find(empre));
        correspondencia.setIdInmueble(inmuebleFacade.find(inmuebl));
        correspondencia.setIdPaquete(paqueteFacade.find(paque));
        correspondencia.setIdVigilante(vigilanteFacade.find(vigilant));
        correspondencia.setDescripcion(descripcio);
        correspondencia.setEstado("No reclamado");
        correspondencia.setFechaIngreso(hora.now());
        correspondenciaFacade.create(correspondencia);
        // Notificar correspondencia 
        residenteList = inmuebleR(correspondencia.getIdInmueble().getIdInmueble());
        this.correo = residenteList.get(0).getIdPerfil().getCorreo();
        email.enviarEmail(correo, "Nueva correspondencia",
                email.paginaCorreo("Nueva correspondencia al inmueble "+residenteList.get(0).getIdInmueble().getIdTorre().getIdTorre()+" - "+residenteList.get(0).getIdInmueble().getIdApartamento().getIdApartamento()+"",
                        "<p><strong>Estimado residente " + residenteList.get(0).getIdPerfil().getNombre() + "!!</strong></p>\n"
                        + "<p>Te informamos que te ha llegado un(a) nuevo(a) "+correspondencia.getIdPaquete().getTipo()+". Lo podras reclamar en la portería del conjunto residencial.</p>\n",
                        "http://imgfz.com/i/xP0vmL4.jpeg")
        );
        empresa = new Empresa();
        inmueble = new Inmueble();
        paquete = new Paquete();
        vigilante = new Vigilante();
        correspondencia = new Correspondencia();
        inmuebl =0;
        descripcio = null;
        paque=0;
        vigilant=0;
        empre=0;
        mensaje.setMensaje("RegistrarVisitante('success','Correspondencia Registrada','Para buscar datos, <br> modificar datos o agregar<br>datos, ingrese al menu de Correspondencia.<br><br>');");
    }

    public void salida(Correspondencia correspondenciaSalida) {
        correspondencia = correspondenciaSalida;
        correspondencia.setFechaSalida(hora.now());
        correspondencia.setEstado("Reclamado");
        correspondenciaFacade.edit(correspondencia);
        mensaje.setMensaje("ConfirmarSalida('info','Entrega Exitosa','El paquete se ha <br> entregado al residente... <br><br>');");
    }

    public List<Correspondencia> consultar() {
        return correspondenciaFacade.findAll();
    }

    public List<Correspondencia> consultarEntrega(String estado) {
        return correspondenciaFacade.paqueteEntregado(estado);
    }

    public List<Correspondencia> correspondenciaResidente(int idInmueble) {
        return correspondenciaFacade.correspondenciaResidente(idInmueble);
    }

    public int contarConrrespondenciaR(int idInmueble) {
        return correspondenciaFacade.contarCorrespondenciaR(idInmueble);
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

    public int getInmuebl() {
        return inmuebl;
    }

    public void setInmuebl(int inmuebl) {
        this.inmuebl = inmuebl;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public int getPaque() {
        return paque;
    }

    public void setPaque(int paque) {
        this.paque = paque;
    }

    public int getVigilant() {
        return vigilant;
    }

    public void setVigilant(int vigilant) {
        this.vigilant = vigilant;
    }

    public int getEmpre() {
        return empre;
    }

    public void setEmpre(int empre) {
        this.empre = empre;
    }
    
}
