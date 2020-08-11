/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Comunicado;
import entidades.Usuario;
import facade.ComunicadoFacade;
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
 * @author Huertas
 */
@Named(value = "comunicadoControlador")
@SessionScoped
public class ComunicadoControlador implements Serializable {

    /**
     * Creates a new instance of ComunicadoControlador
     */
    private Comunicado comunicado;

    private Usuario usuario;

    String fechaPublicacion = "";

    @EJB
    ComunicadoFacade comunicadoFacade;

    @EJB
    UsuarioFacade usuarioFacade;

    @Inject
    private MensajeControlador mensaje;

    public ComunicadoControlador() {
    }

    @PostConstruct
    public void init() {
        usuario = new Usuario();
        comunicado = new Comunicado();
    }

    public void registrar(String tipo) {
        switch (tipo) {
            case "Interno":
                usuario.setIdPerfil(1);
                comunicado.setIdPerfil(usuario);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                Date date = cal.getTime();
                fechaPublicacion = dateFormat.format(date);
                comunicado.setFechaPublicacion(date);
                comunicado.setTipo("Interno");
                comunicadoFacade.create(comunicado);
                usuario = new Usuario();
                comunicado = new Comunicado();
                mensaje.setMensaje("Mensaje('Exito','Se ha publicado el comunicado','success');");
                break;
            case "Externo":
                usuario.setIdPerfil(1);
                comunicado.setIdPerfil(usuario);
                comunicado.setTipo("Externo");
                DateFormat dateFormatt = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cale = Calendar.getInstance();
                Date dates = cale.getTime();
                fechaPublicacion = dateFormatt.format(dates);
                comunicado.setFechaPublicacion(dates);
                comunicadoFacade.create(comunicado);
                usuario = new Usuario();
                comunicado = new Comunicado();
                mensaje.setMensaje("Mensaje('Exito','Se ha publicado la noticia','success');");
                break;
            case "Galeria":
                usuario.setIdPerfil(1);
                comunicado.setIdPerfil(usuario);
                comunicado.setTitulo(".");
                comunicado.setDescripcion(".");
                comunicado.setTipo("Galeria");
                comunicadoFacade.create(comunicado);
                usuario = new Usuario();
                comunicado = new Comunicado();
                mensaje.setMensaje("Mensaje('Exito','Se ha publicado la foto','success');");
                break;
            default:
                mensaje.setMensaje("Mensaje('Error','No se ha podido publicar el contenido.','error');");
                break;
        }

    }

    public List<Comunicado> consultarTodos() {
        return comunicadoFacade.findAll();
    }

    public List<Comunicado> consultar(String tipo) {
        return comunicadoFacade.consultarTodos(tipo);
    }

    public String preActualizar(Comunicado comunicadoActualizar) {
        usuario = comunicadoActualizar.getIdPerfil();
        comunicado = comunicadoActualizar;
        return "";
    }

    public void actualizar() {
        comunicado.setIdPerfil(usuarioFacade.find(usuario.getIdPerfil()));
        comunicadoFacade.edit(comunicado);
    }

    public void eliminar(Comunicado comunicadoEliminar) {
        comunicadoFacade.remove(comunicadoEliminar);
    }
    
    public int contarComunicado(String tipo){
        return comunicadoFacade.contarComunicados(tipo);
    }

    // Pendiente 
    public void asignarImg(Comunicado comunicadoImg) {
        comunicado = comunicadoImg;
    }

    public Comunicado getComunicado() {
        return comunicado;
    }

    public void setComunicado(Comunicado comunicado) {
        this.comunicado = comunicado;
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

    public void setMensaje(MensajeControlador mensaje) {
        this.mensaje = mensaje;
    }

}
