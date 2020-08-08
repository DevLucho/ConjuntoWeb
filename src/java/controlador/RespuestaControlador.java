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
    private String horaI = "";
    private String fechaI = "";
    private Respuesta respuesta;
    private Usuario usuario;
    private Residente residente;
    private TipoPqrs tipoPqrs;
    private Pqrs pqrs;

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

    @Inject
    private MensajeControlador mensaje;

    public RespuestaControlador() {
    }

    @PostConstruct
    public void init() {
        usuario = new Usuario();
        respuesta = new Respuesta();
        residente = new Residente();
        tipoPqrs = new TipoPqrs();
        pqrs = new Pqrs();

    }

    public void registrar(Pqrs pqrs) {
        respuesta.setIdPerfil(usuarioFacade.find(usuario.getIdPerfil()));
        respuesta.setIdPqrs(pqrsFacade.find(pqrs.getIdPqrs()));
        this.pqrs = pqrs;
        pqrs.setEstado("Resuelto");
        pqrsFacade.edit(pqrs);

        DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        fechaI = fecha.format(date);
        respuesta.setFecha(date);

        DateFormat hora = new SimpleDateFormat("HH:mm:ss");
        Calendar cale = Calendar.getInstance();
        Date dates = cale.getTime();
        horaI = hora.format(dates);
        respuesta.setHora(dates);

        respuestaFacade.create(respuesta);
        
        mensaje.setMensaje("MensajeRedirect('consultar-pqrs.xhtml','PQRS resuelta','El estado de la pqrs con el radicado: "+pqrs.getNroRadicado()+" ahora es resuelta!','success');");
        this.usuario = new Usuario();
        this.respuesta = new Respuesta();
        this.residente = new Residente();
        this.tipoPqrs = new TipoPqrs();
        this.pqrs = new Pqrs();
    }

    public List<Respuesta> consultar() {
        return respuestaFacade.findAll();
    }

    public String consultarRespuesta(Pqrs respuestaConsultar) {
        if ("Resuelto".equals(respuestaConsultar.getEstado()) || "Cancelado".equals(respuestaConsultar.getEstado())) {
            mensaje.setMensaje("ConfirmacionResidenteR('./consultar-pqrs.xhtml');");
        }
        residente = respuestaConsultar.getIdResidente();
        tipoPqrs = respuestaConsultar.getIdTipoPqrs();
        pqrs = respuestaConsultar;
        return "respuesta-pqrs";
    }

    public String consultarRespuestaR(Respuesta respuestaConsultar) {
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

}
