/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Evento;
import entidades.Rol;
import entidades.Usuario;
import entidades.ZonaComunal;
import facade.EventoFacade;
import facade.RolFacade;
import facade.UsuarioFacade;
import facade.ZonaComunalFacade;
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

    private CodigoControlador codigoControlador;

    private Evento evento;

    private ZonaComunal zonaComunal;

    private Rol rol;

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

    public EventoControlador() {
    }

    @PostConstruct
    public void init() {
        evento = new Evento();
        zonaComunal = new ZonaComunal();
        rol = new Rol();
        codigoControlador = new CodigoControlador();
    }

    public void registrar() throws NoSuchProviderException, MessagingException {
        boolean notifico = false; // validar si notifico o no
        evento.setEstado("Vigente");
        evento.setIdZonaComunal(zonaComunalFacade.find(zonaComunal.getIdZonaComunal()));
        eventoFacade.create(evento);
        // mostrar fechas con formato
        fechaI = fecha.format(evento.getFechaInicio());
        horaI = hora.format(evento.getHoraInicio());
        fechaF = fecha.format(evento.getFechaFin());
        horaF = hora.format(evento.getHoraFin());
        if (notificar == 1000) { // 1000 notifica todos los usuarios
            allMails();
            for (int i = 0; i < allMails().size(); i++) {
                codigoControlador.enviarEmail(allMails().get(i).getCorreo(), "Invitación a evento",
                        "<div style='\n"
                        + "    width:500px;\n"
                        + "    background-color:\n"
                        + "    #282828;margin-top: 50px !important;\n"
                        + "    min-height: 115px;\n"
                        + "    border-radius: 4px 4px 0 0 ;\n"
                        + "    box-shadow: 2px 0px 3px 0px rgba(0,0,0, 0.3);\n"
                        + "    position: relative;\n"
                        + "    min-height: 70px;\n"
                        + "    margin: 0 auto;\n"
                        + "    padding: 20px;\n"
                        + "    text-align: center;'>\n"
                        + "        <div>\n"
                        + "            <div>\n"
                        + "                <a href='#'>\n"
                        + "                    <img src='http://imgfz.com/i/9mEWVtU.png' style='width: 300px;'>\n"
                        + "                </a>\n"
                        + "            </div>\n"
                        + "        </div>\n"
                        + "    </div>\n"
                        + "    <hr style='\n"
                        + "    margin-top: 1rem;\n"
                        + "    margin-bottom: 1rem;\n"
                        + "    border: 0;\n"
                        + "    border-top: 1px solid rgba(0,0,0,.1);'>\n"
                        + "    <div style='padding:20px;width: 500px;margin-right: auto;margin-left: auto;border: 1px solid rgba(0,0,0,.1);'>\n"
                        + "        <div style='\n"
                        + "        margin:0 auto;\n"
                        + "        background: #ffffff;\n"
                        + "        box-shadow: 2px 0px 3px 0px rgba(0,0,0, 0.3);\n"
                        + "        border-radius: 0 0 4px 4px ;'>\n"
                        + "            <img src='http://imgfz.com/i/XqFi5Ir.png' style='width: 100% ;background-attachment: fixed'>\n"
                        + "        </div>\n"
                        + "            <!-- Titulo y texto informativo -->\n"
                        + "        <div>\n"
                        + "            <h2 style='font-family: Arial, Helvetica, sans-serif;color:#33406A;font-weight: bold;'>\n"
                        + "                Tienes una invitación para el evento " + evento.getTitulo() + "\n"
                        + "            </h2>\n"
                        + "            <hr>\n"
                        + "            <p style='font-family: Arial, Helvetica, sans-serif;'>Cordial saludo " + correos().get(i).getNombre() + ", el administrador del conjunto te ha invitando al evento " + evento.getTitulo() + ", el cual ha sido programado con el siguiente horario:</p>\n"
                        + "            <p style='font-family: Arial, Helvetica, sans-serif;'><b>Fecha y hora inicio: " + fechaI + " " + horaI + "</b></p>\n"
                        + "            <p style='font-family: Arial, Helvetica, sans-serif;'><b>Fecha y hora fin: " + fechaF + " " + horaF + "</b></p>\n"
                        + "            <p style='font-family: Arial, Helvetica, sans-serif;'><b>Nota: </b>Recuerda que para participar deberas <b>inscribirte</b> previamente al evento, este proceso lo podras realizar desde la pagina de tu conjunto donde encontraras información detallada del evento.</p>\n"
                        + "        </div>\n"
                        + "    </div>\n"
                        + "    <hr style='\n"
                        + "    margin-top: 1rem;\n"
                        + "    margin-bottom: 1rem;\n"
                        + "    border: 0;\n"
                        + "    border-top: 1px solid rgba(0,0,0,.1);'>\n"
                        + "    <div style='\n"
                        + "    width:500px;\n"
                        + "    background-color:#282828;\n"
                        + "    min-height: 115px;\n"
                        + "    border-radius: 0px 0px 4px 4px ;\n"
                        + "    box-shadow: 2px 0px 3px 0px rgba(0,0,0, 0.3);\n"
                        + "    position: relative;\n"
                        + "    min-height: 70px;\n"
                        + "    margin: 0 auto;\n"
                        + "    padding: 20px;\n"
                        + "    text-align: center;'>\n"
                        + "        <div>\n"
                        + "            <div style=\"color: white;font-family: Arial, Helvetica, sans-serif;\">\n"
                        + "                <small>\n"
                        + "                    <a href='#' style=\"color: white;font-family: Arial, Helvetica, sans-serif;\">\n"
                        + "                        Términos y condiciones\n"
                        + "                    </a>\n"
                        + "                    &nbsp;\n"
                        + "                    |\n"
                        + "                    &nbsp;\n"
                        + "                    <a href='#' style=\"color: white;font-family: Arial, Helvetica, sans-serif;\">\n"
                        + "                        Políticas de privacidad\n"
                        + "                    </a>\n"
                        + "                </small>\n"
                        + "                <br />\n"
                        + "                <br />\n"
                        + "                <div>\n"
                        + "                    <small>© 2020 Todos los derechos reservados.</small>\n"
                        + "                    <br>\n"
                        + "                    <a href='#'><img src='http://imgfz.com/i/HxYQDRd.png' /></a>\n"
                        + "                </div>\n"
                        + "            </div>\n"
                        + "        </div>\n"
                        + "    </div>");
            }
            notifico = true;
        } else if (notificar != 1100) { // 1100 = ningun usuario, notifica segun rol
            correos();
            for (int i = 0; i < correos().size(); i++) {
                codigoControlador.enviarEmail(correos().get(i).getCorreo(), "Invitación a evento",
                        "<div style='\n"
                        + "    width:500px;\n"
                        + "    background-color:\n"
                        + "    #282828;margin-top: 50px !important;\n"
                        + "    min-height: 115px;\n"
                        + "    border-radius: 4px 4px 0 0 ;\n"
                        + "    box-shadow: 2px 0px 3px 0px rgba(0,0,0, 0.3);\n"
                        + "    position: relative;\n"
                        + "    min-height: 70px;\n"
                        + "    margin: 0 auto;\n"
                        + "    padding: 20px;\n"
                        + "    text-align: center;'>\n"
                        + "        <div>\n"
                        + "            <div>\n"
                        + "                <a href='#'>\n"
                        + "                    <img src='http://imgfz.com/i/9mEWVtU.png' style='width: 300px;'>\n"
                        + "                </a>\n"
                        + "            </div>\n"
                        + "        </div>\n"
                        + "    </div>\n"
                        + "    <hr style='\n"
                        + "    margin-top: 1rem;\n"
                        + "    margin-bottom: 1rem;\n"
                        + "    border: 0;\n"
                        + "    border-top: 1px solid rgba(0,0,0,.1);'>\n"
                        + "    <div style='padding:20px;width: 500px;margin-right: auto;margin-left: auto;border: 1px solid rgba(0,0,0,.1);'>\n"
                        + "        <div style='\n"
                        + "        margin:0 auto;\n"
                        + "        background: #ffffff;\n"
                        + "        box-shadow: 2px 0px 3px 0px rgba(0,0,0, 0.3);\n"
                        + "        border-radius: 0 0 4px 4px ;'>\n"
                        + "            <img src='http://imgfz.com/i/XqFi5Ir.png' style='width: 100% ;background-attachment: fixed'>\n"
                        + "        </div>\n"
                        + "            <!-- Titulo y texto informativo -->\n"
                        + "        <div>\n"
                        + "            <h2 style='font-family: Arial, Helvetica, sans-serif;color:#33406A;font-weight: bold;'>\n"
                        + "                Tienes una invitación para el evento " + evento.getTitulo() + "\n"
                        + "            </h2>\n"
                        + "            <hr>\n"
                        + "            <p style='font-family: Arial, Helvetica, sans-serif;'>Cordial saludo " + correos().get(i).getNombre() + ", el administrador del conjunto te ha invitando al evento " + evento.getTitulo() + ", el cual ha sido programado con el siguiente horario:</p>\n"
                        + "            <p style='font-family: Arial, Helvetica, sans-serif;'><b>Fecha y hora inicio: " + fechaI + " " + horaI + "</b></p>\n"
                        + "            <p style='font-family: Arial, Helvetica, sans-serif;'><b>Fecha y hora fin: " + fechaF + " " + horaF + "</b></p>\n"
                        + "            <p style='font-family: Arial, Helvetica, sans-serif;'><b>Nota: </b>Recuerda que para participar deberas <b>inscribirte</b> previamente al evento, este proceso lo podras realizar desde la pagina de tu conjunto donde encontraras información detallada del evento.</p>\n"
                        + "        </div>\n"
                        + "    </div>\n"
                        + "    <hr style='\n"
                        + "    margin-top: 1rem;\n"
                        + "    margin-bottom: 1rem;\n"
                        + "    border: 0;\n"
                        + "    border-top: 1px solid rgba(0,0,0,.1);'>\n"
                        + "    <div style='\n"
                        + "    width:500px;\n"
                        + "    background-color:#282828;\n"
                        + "    min-height: 115px;\n"
                        + "    border-radius: 0px 0px 4px 4px ;\n"
                        + "    box-shadow: 2px 0px 3px 0px rgba(0,0,0, 0.3);\n"
                        + "    position: relative;\n"
                        + "    min-height: 70px;\n"
                        + "    margin: 0 auto;\n"
                        + "    padding: 20px;\n"
                        + "    text-align: center;'>\n"
                        + "        <div>\n"
                        + "            <div style=\"color: white;font-family: Arial, Helvetica, sans-serif;\">\n"
                        + "                <small>\n"
                        + "                    <a href='#' style=\"color: white;font-family: Arial, Helvetica, sans-serif;\">\n"
                        + "                        Términos y condiciones\n"
                        + "                    </a>\n"
                        + "                    &nbsp;\n"
                        + "                    |\n"
                        + "                    &nbsp;\n"
                        + "                    <a href='#' style=\"color: white;font-family: Arial, Helvetica, sans-serif;\">\n"
                        + "                        Políticas de privacidad\n"
                        + "                    </a>\n"
                        + "                </small>\n"
                        + "                <br />\n"
                        + "                <br />\n"
                        + "                <div>\n"
                        + "                    <small>© 2020 Todos los derechos reservados.</small>\n"
                        + "                    <br>\n"
                        + "                    <a href='#'><img src='http://imgfz.com/i/HxYQDRd.png' /></a>\n"
                        + "                </div>\n"
                        + "            </div>\n"
                        + "        </div>\n"
                        + "    </div>");
            }
            notifico = true;
        }
        if (notifico) {
            mensaje.setMensaje("Mensajes('Evento publicado!','Se ha publicado un nuevo evento y ha sido notificado.','success');");
        } else {
            mensaje.setMensaje("Mensajes('Evento publicado!','Se ha publicado un nuevo evento.','success');");
        }
        zonaComunal = new ZonaComunal();
        evento = new Evento();
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

    public CodigoControlador getCodigoControlador() {
        return codigoControlador;
    }

    public void setCodigoControlador(CodigoControlador codigoControlador) {
        this.codigoControlador = codigoControlador;
    }
}
