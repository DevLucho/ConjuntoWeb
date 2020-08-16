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

    private String fechaIngreso = "";
    private String fechaSalida = "";

    /**
     * Creates a new instance of CorrespondenciaControlador
     */
    public CorrespondenciaControlador() {
    }

    Correspondencia correspondencia;
    Inmueble inmueble;
    Paquete paquete;
    Empresa empresa;
    Vigilante vigilante;
    Residente residente;
    CodigoControlador codigoControlador;
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

    @Inject
    private MensajeControlador mensaje;

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
        codigoControlador = new CodigoControlador();
    }

    // consultar inmueble residente
    public List<Residente> inmuebleR(int idInmueble) {
        return correspondenciaFacade.inmuebleR(idInmueble);
    }

    public void registrar() throws NoSuchProviderException, MessagingException {
        correspondencia.setIdEmpresa(empresaFacade.find(empresa.getIdEmpresa()));
        correspondencia.setIdInmueble(inmuebleFacade.find(inmueble.getIdInmueble()));
        correspondencia.setIdPaquete(paqueteFacade.find(paquete.getIdPaquete()));
        correspondencia.setIdVigilante(vigilanteFacade.find(vigilante.getIdVigilante()));
        correspondencia.setEstado("No reclamado");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        fechaIngreso = dateFormat.format(date);
        correspondencia.setFechaIngreso(date);
        correspondenciaFacade.create(correspondencia);

        residenteList = inmuebleR(correspondencia.getIdInmueble().getIdInmueble());

        this.correo = residenteList.get(0).getIdPerfil().getCorreo();
        codigoControlador.enviarEmail(correo, "Correpondencia nueva",
                "<div style='margin: 0;padding: 0;box-sizing: border-box;font-family: Arial, Helvetica, sans-serif;font-size: 20px;'>\n"
                + "    <div style='width: 60;height: 1120px; margin: 3% 20% 5% 20%; overflow: hidden; -webkit-box-shadow: 0px 7px 12px 2px rgba(0, 0, 0, 0.53);-moz-box-shadow: 0px 7px 12px 2px rgba(0, 0, 0, 0.53);box-shadow: 0px 7px 12px 2px rgba(0, 0, 0, 0.53);'>\n"
                + "        <section style='padding: 3% 3%; background-color: #282828; border-bottom: solid rgba(212, 197, 197, 0.383) 2px;'>\n"
                + "            <img style='width: 99%;' src='http://imgfz.com/i/MBLyXhF.png'>\n"
                + "        </section>\n"
                + "        <section style='padding-left: 9%;padding-top: 4%;'>\n"
                + "            <img src='http://imgfz.com/i/xP0vmL4.jpeg' style='width: 70%; margin-left: 11%;'>\n"
                + "        </section>\n"
                + "        <section style='padding-left: 15%;padding-top: 2%;'>\n"
                + "            <strong>Hola!!</strong><br><br>\n"
                + "            <p>Te informamos que te ha llegado un nuevo paquete, recibo<br> o carta. Lo podras encontrar en tu portería mas cercana.</p><br>\n"
                + "        </section>\n"
                + "        <p style='text-align: center;'>Torre : 12</p><br>\n"
                + "        <p style='text-align: center;'>Apartamento : 204</p><br><br>\n"
                + "        <section style='text-align: center;padding-top: 3%;background-color: rgb(231, 231, 231);height: 120px;font-size: 10px !important;border-top: solid rgba(212, 197, 197, 0.383) 2px;'>\n"
                + "            <a style='font-size: 10px !important;' href='#'>Términos y condiciones</a> | <a href='#'>Políticas de Privacidad</a>\n"
                + "            <br><br>\n"
                + "            <p style='font-size: 10px !important;'>© 2020 ConjuntoWEB · Todos los derechos reservados.</p>\n"
                + "        </section>\n"
                + "    </div>\n"
                + "</div>"
        );

        empresa = new Empresa();
        inmueble = new Inmueble();
        paquete = new Paquete();
        vigilante = new Vigilante();
        correspondencia = new Correspondencia();
        mensaje.setMensaje("RegistrarVisitante('success','Correspondencia Registrada','Para buscar datos, <br> modificar datos o agregar<br>datos, ingrese al menu de Correspondencia.<br><br>');");
    }

    public void salida(Correspondencia correspondenciaSalida) {
        correspondencia = correspondenciaSalida;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        fechaSalida = dateFormat.format(date);
        correspondencia.setFechaSalida(date);
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

}
