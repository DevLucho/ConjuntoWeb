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
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private Date fecha = new Date();

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

    public void registrar(String tipo, int idPerfil) {
        switch (tipo) {
            case "Interno":
                imagen.subirImagen(1);
                comunicado.setIdPerfil(usuarioFacade.find(idPerfil));
                comunicado.setFechaPublicacion(hora.now());
                comunicado.setTipo("Interno");
                comunicado.setImg("../../../img/" + imagen.getImg().getSubmittedFileName());
                comunicadoFacade.create(comunicado);
                comunicado.setPublicarHasta(hora.fecha(comunicado.getPublicarHasta()));
                comunicadoFacade.edit(comunicado);
                mensaje.setMensaje("MensajeRedirect('comunicados.xhtml', 'Exito', 'Se ha publicado el comunicado', 'success');");
                break;
            case "Externo":
                imagen.subirImagen(2);
                comunicado.setIdPerfil(usuarioFacade.find(idPerfil));
                comunicado.setTipo("Externo");
                comunicado.setFechaPublicacion(hora.now());
                comunicado.setImg("../../../../img/" + imagen.getImg().getSubmittedFileName());
                comunicadoFacade.create(comunicado);
                mensaje.setMensaje("MensajeRedirect('comunicados.xhtml', 'Exito', 'Se ha publicado la noticia', 'success');");
                break;
            case "Galeria":
                imagen.subirImagen(2);
                comunicado.setIdPerfil(usuarioFacade.find(idPerfil));
                comunicado.setTitulo(".");
                comunicado.setDescripcion(".");
                comunicado.setTipo("Galeria");
                comunicado.setImg("../../../../img/" + imagen.getImg().getSubmittedFileName());
                comunicadoFacade.create(comunicado);
                mensaje.setMensaje("MensajeRedirect('comunicados.xhtml', 'Exito', 'Se ha añadido la imagen', 'success');");
                break;
            default:
                mensaje.setMensaje("Mensaje('Error','No se ha podido publicar el contenido.','error');");
                break;
        }
        usuario = new Usuario();
        comunicado = new Comunicado();
        imagen = new ImagenControlador();
        hora = new HoraControlador();
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

    // Metodo para eliminar comunicados expirados
    public void eliminarExpirados() {
        try {
            List<Comunicado> expirados = comunicadoFacade.procesoEliminar(fecha);
            if (expirados != null) {
                for (int i = 0; i < expirados.size(); i++) {
                    eliminar(expirados.get(i));
                }
            }
        } catch (IOException e) {
            System.out.println("Error eliminar expirados:" + e.getMessage());
        }
    }

    public void eliminar(Comunicado comunicadoEliminar) throws IOException {
        // Eliminar imagen de directorio
        if ("Interno".equals(comunicadoEliminar.getTipo())) {
            String rutaImg = comunicadoEliminar.getImg().substring(13, comunicadoEliminar.getImg().length());
            Path p = Paths.get(imagen.getRuta() + rutaImg);
            Files.deleteIfExists(p);
        } else {
            Path p = Paths.get(imagen.getRuta2() + subString(comunicadoEliminar.getImg()));
            Files.deleteIfExists(p);
        }
        // Eliminar comunicado
        comunicadoFacade.remove(comunicadoEliminar);
    }

    // Para imagenes externas (recorta ruta ../../)
    public String subString(String img) {
        return img.substring(16, img.length());
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
