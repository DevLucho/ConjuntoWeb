/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Evento;
import entidades.HoraFinal;
import entidades.HoraInicial;
import entidades.Rol;
import entidades.Usuario;
import entidades.ZonaComunal;
import facade.EventoFacade;
import facade.HoraFinalFacade;
import facade.HoraInicialFacade;
import facade.RolFacade;
import facade.UsuarioFacade;
import facade.ZonaComunalFacade;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.security.NoSuchProviderException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.servlet.http.Part;

/**
 *
 * @author Huertas
 */
@Named(value = "eventoControlador")
@SessionScoped
public class EventoControlador implements Serializable {

    /**
     * Creates a new instance of EventoControlador
     */
    @Inject
    private MensajeControlador mensaje;
    private CorreoControlador email;
    private ImagenControlador imagen;
    private Evento evento;
    private ZonaComunal zonaComunal;
    private Rol rol;
    private HoraInicial horaInicial;
    private HoraFinal horaFinal;
    private int notificar;
    private String horaI = "";
    private String fechaI = "";
    private String horaF = "";
    private String fechaF = "";
    DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat hora = new SimpleDateFormat("HH:mm:ss");

    @EJB
    EventoFacade eventoFacade;

    @EJB
    ZonaComunalFacade zonaComunalFacade;

    @EJB
    RolFacade rolFacade;

    @EJB
    HoraInicialFacade horaInFacade;

    @EJB
    HoraFinalFacade horaFiFacade;

    public EventoControlador() {
    }

    @PostConstruct
    public void init() {
        imagen = new ImagenControlador();
        horaInicial = new HoraInicial();
        horaFinal = new HoraFinal();
        evento = new Evento();
        zonaComunal = new ZonaComunal();
        rol = new Rol();
        email = new CorreoControlador();
    }

    public void registrar() throws NoSuchProviderException, MessagingException {
        boolean notifico = false; // validar si notifico o no
        // --- Subir img --- 
        imagen.subirImagen();
        // ------------------
        evento.setIdZonaComunal(zonaComunalFacade.find(zonaComunal.getIdZonaComunal()));
        evento.setHoraInicio(horaInFacade.find(horaInicial.getIdHora()));
        evento.setImg("../../../img/" + imagen.getImg().getSubmittedFileName());
        evento.setHoraFin(horaFiFacade.find(horaFinal.getIdHora()));
        evento.setEstado("Vigente");
        eventoFacade.create(evento);
        // mostrar fechas - horas con formato
        fechaI = fecha.format(evento.getFechaInicio());
        horaI = hora.format(evento.getHoraInicio().getHora());
        fechaF = fecha.format(evento.getFechaFin());
        horaF = hora.format(evento.getHoraFin().getHora());
        if (notificar == 1000) { // 1000 notifica todos los usuarios
            allMails();
            // Correos masivos 
            for (int i = 0; i < allMails().size(); i++) {
                email.enviarEmail(allMails().get(i).getCorreo(), "Invitación a evento",
                        email.paginaCorreo("Tienes una invitación para el evento " + evento.getTitulo() + "",
                                "<p style='font-family: Arial, Helvetica, sans-serif;'>Cordial saludo " + correos().get(i).getNombre() + ", el administrador del conjunto te ha invitando al evento " + evento.getTitulo() + ", el cual ha sido programado con el siguiente horario:</p>\n"
                                + "<p style='font-family: Arial, Helvetica, sans-serif;'><b>Fecha y hora inicio: " + fechaI + " " + horaI + "</b></p>\n"
                                + "<p style='font-family: Arial, Helvetica, sans-serif;'><b>Fecha y hora fin: " + fechaF + " " + horaF + "</b></p>\n"
                                + "<p style='font-family: Arial, Helvetica, sans-serif;'><b>Nota: </b>Recuerda que para participar deberas <b>inscribirte</b> previamente al evento, este proceso lo podras realizar desde la pagina de tu conjunto donde encontraras información detallada del evento.</p>",
                                "http://imgfz.com/i/XqFi5Ir.png")
                );
            }
            notifico = true;
        } else if (notificar != 1100) { // 1100 = ningun usuario, notifica segun rol
            correos();
            for (int i = 0; i < correos().size(); i++) {
                email.enviarEmail(correos().get(i).getCorreo(), "Invitación a evento",
                        email.paginaCorreo("Tienes una invitación para el evento " + evento.getTitulo() + "",
                                " <p style='font-family: Arial, Helvetica, sans-serif;'>Cordial saludo " + correos().get(i).getNombre() + ", el administrador del conjunto te ha invitando al evento " + evento.getTitulo() + ", el cual ha sido programado con el siguiente horario:</p>\n"
                                + "<p style='font-family: Arial, Helvetica, sans-serif;'><b>Fecha y hora inicio: " + fechaI + " " + horaI + "</b></p>\n"
                                + "<p style='font-family: Arial, Helvetica, sans-serif;'><b>Fecha y hora fin: " + fechaF + " " + horaF + "</b></p>\n"
                                + "<p style='font-family: Arial, Helvetica, sans-serif;'><b>Nota: </b>Recuerda que para participar deberas <b>inscribirte</b> previamente al evento, este proceso lo podras realizar desde la pagina de tu conjunto donde encontraras información detallada del evento.</p>",
                                "http://imgfz.com/i/XqFi5Ir.png")
                );

            }
            notifico = true;
        }
        if (notifico) {
            mensaje.setMensaje("Mensajes('Evento publicado!','Se ha publicado un nuevo evento y ha sido notificado.','success');");
        } else {
            mensaje.setMensaje("Mensajes('Evento publicado!','Se ha publicado un nuevo evento.','success');");
        }
        email = new CorreoControlador();
        zonaComunal = new ZonaComunal();
        evento = new Evento();
        imagen = new ImagenControlador();
        notificar = 0;
    }

    public List<Usuario> correos() {
        return eventoFacade.correos(notificar);
    }

    public List<Usuario> allMails() {
        return eventoFacade.allMails();
    }

    public String preActualizar(Evento eventoActualizar) {
        if ("Cancelado".equals(eventoActualizar.getEstado()) || "Finalizado".equals(eventoActualizar.getEstado())) {
            mensaje.setMensaje("Mensajes('Atención!','No puedes editar un evento que no este vigente.','warning');");
        } else {
            zonaComunal = eventoActualizar.getIdZonaComunal();
            evento = eventoActualizar;
            return "editar-evento";
        }
        return "";
    }

    public void actualizar() {
        evento.setIdZonaComunal(zonaComunalFacade.find(zonaComunal.getIdZonaComunal()));
        eventoFacade.edit(evento);
        mensaje.setMensaje("Mensajes('Evento modificado!','Se ha modificado satisfactoriamente el evento.','success');");
    }

    public String consultarEvento(int id) {
        evento = eventoFacade.find(id);
        return "detalle-evento";
    }

    public List<Evento> consultarTodos() {
        return eventoFacade.findAll();
    }

    public List<HoraInicial> consultarHorasI() {
        return horaInFacade.findAll();
    }

    public List<HoraFinal> consultarHorasF() {
        return horaFiFacade.findAll();
    }

    public int contarEventos() {
        return eventoFacade.count();
    }

    public int contarCancelados() {
        return eventoFacade.countEstado("Cancelado");
    }

    public int contarFinalizados() {
        return eventoFacade.countEstado("Finalizado");
    }

    public int contarVigentes() {
        return eventoFacade.countEstado("Vigente");
    }

    public List<Evento> consultarCancelados() {
        return eventoFacade.estadoEvento("Cancelado");
    }

    public List<Evento> consultarVigentes() {
        return eventoFacade.estadoEvento("Vigente");
    }

    public List<Evento> consultarFinalizados() {
        return eventoFacade.estadoEvento("Finalizado");
    }

    public void eliminar(Evento eventoEliminar) {
        eventoFacade.remove(eventoEliminar);
    }

    public void cancelar(Evento eventoCancelar) {
        if ("Cancelado".equals(eventoCancelar.getEstado()) || "Finalizado".equals(eventoCancelar.getEstado())) {
            mensaje.setMensaje("Mensajes('Atención!','Este evento ya ha sido cancelado.','warning');");
        } else {
            mensaje.setMensaje("Confirmar('Estas seguro que deseas cancelar este evento?','No podras revertilo!','warning','Si, cancelar!','Cancelado!','Se ha cancelado exitosamente el evento.','success');");
            evento = eventoCancelar;
            evento.setEstado("Cancelado");
            eventoFacade.edit(evento);
        }
    }

    // Metodos get y set
    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public ZonaComunal getZonaComunal() {
        return zonaComunal;
    }

    public void setZonaComunal(ZonaComunal zonaComunal) {
        this.zonaComunal = zonaComunal;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public MensajeControlador getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajeControlador mensaje) {
        this.mensaje = mensaje;
    }

    public int getNotificar() {
        return notificar;
    }

    public void setNotificar(int notificar) {
        this.notificar = notificar;
    }

    public HoraInicial getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(HoraInicial horaInicial) {
        this.horaInicial = horaInicial;
    }

    public HoraFinal getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(HoraFinal horaFinal) {
        this.horaFinal = horaFinal;
    }

    public ImagenControlador getImagen() {
        return imagen;
    }

    public void setImagen(ImagenControlador imagen) {
        this.imagen = imagen;
    }

}
