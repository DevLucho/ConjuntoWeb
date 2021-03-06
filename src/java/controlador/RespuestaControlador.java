/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Pqrs;
import entidades.Residente;
import entidades.Respuesta;
import entidades.TipoPqrs;
import entidades.Usuario;
import facade.PqrsFacade;
import facade.ResidenteFacade;
import facade.RespuestaFacade;
import facade.TipoPqrsFacade;
import facade.UsuarioFacade;
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
 * @author Familia
 */
@Named(value = "respuestaControlador")
@SessionScoped
public class RespuestaControlador implements Serializable {

    /**
     * Creates a new instance of RespuestaControlador
     */
    @Inject
    HoraControlador hora;
    @Inject
    MensajeControlador mensaje;
    private Respuesta respuesta;
    private Usuario usuario;
    private Residente residente;
    private TipoPqrs tipoPqrs;
    private Pqrs pqrs;
    private ImagenControlador imagen;

    @EJB
    RespuestaFacade respuestaFacade;

    @EJB
    UsuarioFacade usuarioFacade;

    @EJB
    PqrsFacade pqrsFacade;

    @EJB
    ResidenteFacade residenteFacade;

    @EJB
    TipoPqrsFacade tipoPqrsFacade;

    public RespuestaControlador() {
    }

    @PostConstruct
    public void init() {
        usuario = new Usuario();
        respuesta = new Respuesta();
        residente = new Residente();
        tipoPqrs = new TipoPqrs();
        pqrs = new Pqrs();
        imagen = new ImagenControlador();
    }

    public void registrar(Pqrs pqrs) {
        try {
            imagen.subirImagen(1);
            this.pqrs = pqrs;
            pqrs.setEstado("Resuelto");
            pqrsFacade.edit(pqrs);
            respuesta.setIdPerfil(usuarioFacade.find(usuario.getIdPerfil()));
            respuesta.setIdPqrs(pqrsFacade.find(pqrs.getIdPqrs()));
            respuesta.setFecha(hora.now());
            respuesta.setHora(hora.now());
            respuesta.setAdjunto("../../../img/" + imagen.getImg().getSubmittedFileName());
            respuestaFacade.create(respuesta);
            respuesta.setFecha(hora.fecha(respuesta.getFecha()));
            respuesta.setHora(hora.hora(respuesta.getHora()));
            respuestaFacade.edit(respuesta);
            mensaje.setMensaje("MensajeRedirect('consultar-pqrs.xhtml','PQRS resuelta','El estado de la pqrs con el radicado: " + pqrs.getNroRadicado() + " ahora es resuelta!','success');");
            this.usuario = new Usuario();
            this.respuesta = new Respuesta();
            this.pqrs = new Pqrs();
        } catch (Exception e) {
            System.out.println("Error respuesta: " + e.getMessage());
        }
    }

    public List<Respuesta> consultar() {
        return respuestaFacade.findAll();
    }

    public String consultarRespuesta(Pqrs respuestaConsultar) {
        if ("Resuelto".equals(respuestaConsultar.getEstado()) || "Cancelado".equals(respuestaConsultar.getEstado())) {
            mensaje.setMensaje("ConfirmacionResidente('./consultar-pqrs.xhtml','warning','Esta pqrs ya esta resuelta','Volver');");
        }
        residente = respuestaConsultar.getIdResidente();
        tipoPqrs = respuestaConsultar.getIdTipoPqrs();
        pqrs = respuestaConsultar;
        return "respuesta-pqrs";
    }

    public String consultarRespuestaR(Pqrs respuestaConsultar) {
        if ("Resuelto".equals(respuestaConsultar.getEstado()) || "Cancelado".equals(respuestaConsultar.getEstado())) {
            mensaje.setMensaje("ConfirmacionResidente('./consultar-pqrs.xhtml','warning','Esta pqrs ya esta resuelta','Volver');");
        }
        residente = respuestaConsultar.getIdResidente();
        tipoPqrs = respuestaConsultar.getIdTipoPqrs();
        pqrs = respuestaConsultar;
        return "detalle-pqrs";
    }

    public Respuesta getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Respuesta respuesta) {
        this.respuesta = respuesta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public MensajeControlador getMensaje() {
        return mensaje;
    }

    public Pqrs getPqrs() {
        return pqrs;
    }

    public void setPqrs(Pqrs pqrs) {
        this.pqrs = pqrs;
    }

    public void setMensaje(MensajeControlador mensaje) {
        this.mensaje = mensaje;
    }

    public ImagenControlador getImagen() {
        return imagen;
    }

    public void setImagen(ImagenControlador imagen) {
        this.imagen = imagen;
    }

}
