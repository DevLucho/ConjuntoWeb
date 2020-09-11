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
    @Inject
    private MensajeControlador mensaje;
    @Inject
    private HoraControlador hora;
    private Comunicado comunicado;
    private Usuario usuario;
    private ImagenControlador imagen;

    String fechaPublicacion = "";

    @EJB
    ComunicadoFacade comunicadoFacade;

    @EJB
    UsuarioFacade usuarioFacade;

    public ComunicadoControlador() {
    }

    @PostConstruct
    public void init() {
        usuario = new Usuario();
        comunicado = new Comunicado();
        imagen = new ImagenControlador();
    }

    public void registrar(String tipo) {
        switch (tipo) {
            case "Interno":
                imagen.subirImagen(1);
                usuario.setIdPerfil(1);
                comunicado.setIdPerfil(usuario);
                comunicado.setFechaPublicacion(hora.now());
                comunicado.setTipo("Interno");
                comunicado.setImg("../../../img/" + imagen.getImg().getSubmittedFileName());
                comunicadoFacade.create(comunicado);
                usuario = new Usuario();
                comunicado = new Comunicado();
                mensaje.setMensaje("Mensaje('Exito','Se ha publicado el comunicado','success');");
                break;
            case "Externo":
                imagen.subirImagen(2);
                usuario.setIdPerfil(1);
                comunicado.setIdPerfil(usuario);
                comunicado.setTipo("Externo");
                comunicado.setFechaPublicacion(hora.now());
                comunicado.setImg("img/" + imagen.getImg().getSubmittedFileName());
                comunicadoFacade.create(comunicado);
                usuario = new Usuario();
                comunicado = new Comunicado();
                mensaje.setMensaje("Mensaje('Exito','Se ha publicado la noticia','success');");
                break;
            case "Galeria":
                imagen.subirImagen(2);
                usuario.setIdPerfil(1);
                comunicado.setIdPerfil(usuario);
                comunicado.setTitulo(".");
                comunicado.setDescripcion(".");
                comunicado.setTipo("Galeria");
                comunicado.setImg("img/" + imagen.getImg().getSubmittedFileName());
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

    public int contarComunicado(String tipo) {
        return comunicadoFacade.contarComunicados(tipo);
    }

    // Pendiente 
    public void asignarImg(Comunicado comunicadoImg) {
        comunicado = comunicadoImg;
    }

    // Metodos get y set
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

    public ImagenControlador getImagen() {
        return imagen;
    }

    public void setImagen(ImagenControlador imagen) {
        this.imagen = imagen;
    }

}
