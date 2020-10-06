/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Reserva;
import entidades.Residente;
import entidades.ZonaComunal;
import facade.ReservaFacade;
import facade.ResidenteFacade;
import facade.ZonaComunalFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.security.NoSuchProviderException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.mail.MessagingException;

/**
 *
 * @author Huertas
 */
@Named(value = "reservaControlador")
@SessionScoped
public class ReservaControlador implements Serializable {

    /**
     * Creates a new instance of ReservaControlador
     */
    @Inject
    CorreoControlador correo;
    @Inject
    HoraControlador hora;
    @Inject
    MensajeControlador mensaje;
    private Residente residente;
    private ZonaComunal zonaComunal;
    private Reserva reserva;
    private String fechaSeleccionada;
    private Date horaInicio;
    private Date horaFin;

    @EJB
    ResidenteFacade residenteFacade;

    @EJB
    ZonaComunalFacade zonaComunalFacade;

    @EJB
    ReservaFacade reservaFacade;

    public ReservaControlador() {
    }

    @PostConstruct
    public void init() {
        residente = new Residente();
        zonaComunal = new ZonaComunal();
        reserva = new Reserva();
    }

    public void registrar() throws NoSuchProviderException, MessagingException {
        try {
            reserva.setIdZonaComunal(zonaComunalFacade.find(zonaComunal.getIdZonaComunal()));
            reserva.setIdResidente(residenteFacade.find(residente.getIdResidente()));
            reserva.setEstado("Pendiente");

            // Extraer fecha de string
            int dia = Integer.parseInt(fechaSeleccionada.substring(0, 2));
            int mes = Integer.parseInt(fechaSeleccionada.substring(3, 5));
            int anio = Integer.parseInt(fechaSeleccionada.substring(6, 10));

            // Extraer fecha inicio
            Calendar time = GregorianCalendar.getInstance();
            time.setTime(this.horaInicio);

            int hour = time.get(Calendar.HOUR);
            int minute = time.get(Calendar.MINUTE);
            int second = time.get(Calendar.SECOND);

            // Extraer fecha fin
            Calendar time2 = GregorianCalendar.getInstance();
            time2.setTime(this.horaFin);

            int hour2 = time2.get(Calendar.HOUR);
            int minute2 = time2.get(Calendar.MINUTE);
            int second2 = time2.get(Calendar.SECOND);

            // Construir fecha-hora inicio reserva
            Calendar c = Calendar.getInstance();
            c.set(anio, mes, dia, hour, minute, second);
            c.add(Calendar.MONTH, -1);
            reserva.setFechaInicioReserva(c.getTime());

            // Construir fecha-hora fin de reserva
            Calendar c2 = Calendar.getInstance();
            c2.set(anio, mes, dia, hour2, minute2, second2);
            c2.add(Calendar.MONTH, -1);
            reserva.setFechaFinReserva(c2.getTime());

            reservaFacade.create(reserva);
            mensaje.setMensaje("EdicionVisitante('consultar-reserva.xhtml','Reserva generada satisfactoriamente','<b>*</b>Recuerde, tiene 3 horas antes para <br> cancelar sin causar bloqueo.<br><b>*</b>Su solicitud queda en estado pendiente<br> hasta que el administrador la apruebe.');");

            correo.enviarEmail(reserva.getIdResidente().getIdPerfil().getCorreo(), "Confirmacion de reserva común",
                    correo.paginaCorreo("Reserva pendiente de la zona: " + reserva.getIdZonaComunal().getNombre() + "",
                            " <p style='font-family: Arial, Helvetica, sans-serif;'>Estimado residente " + reserva.getIdResidente().getIdPerfil().getNombre() + ", la reserva de: " + reserva.getIdZonaComunal().getNombre() + " ha sido realizada exitosamente con el siguiente horario:</p>\n"
                            + "<p style='font-family: Arial, Helvetica, sans-serif;'><b>Fecha y hora inicio: " + hora.convertirfh(reserva.getFechaInicioReserva()) + "</b></p>\n"
                            + "<p style='font-family: Arial, Helvetica, sans-serif;'><b>Fecha y hora fin: " + hora.convertirfh(reserva.getFechaFinReserva()) + "</b></p>\n"
                            + "<p style='font-family: Arial, Helvetica, sans-serif;'><b>Nota: Recuerda, tu petición de reserva se encuentra <b> Pendiente </b>, por lo tanto, el Administrador aprobara o no dicha reserva.</p>",
                            "http://imgfz.com/i/1CrnTaz.jpeg")
            );

            residente = new Residente();
            zonaComunal = new ZonaComunal();
            reserva = new Reserva();
            horaInicio = new Date();
            horaFin = new Date();
            fechaSeleccionada = "";

        } catch (NumberFormatException e) {
            System.out.println("Error reserva" + e.getMessage());
        }

    }

    public List<Reserva> consultar() {
        return reservaFacade.findAll();
    }

    public String findReserva(Reserva reserva) {
        this.zonaComunal = reserva.getIdZonaComunal();
        this.residente = reserva.getIdResidente();
        this.reserva = reserva;
        return "detalle-reserva";
    }

    public void cancelar(Reserva reserva) {
        if ("Cancelado".equals(reserva.getEstado())) {
            mensaje.setMensaje("Mensajes('Advertencia!','Esta reserva ya esta cancelada','warning');");
        } else {
            mensaje.setMensaje("Confirmar('Estas seguro que deseas cancelar la reserva?','No podras revertilo!','warning','Si, cancelar!','Cancelado!','Se ha cancelado la reserva.','success');");
            this.reserva = reserva;
            this.reserva.setEstado("Cancelado");
            reservaFacade.edit(this.reserva);
        }
    }

    public void aprobarReserva(Reserva reserva) throws NoSuchProviderException, MessagingException {
        this.reserva = reserva;
        this.reserva.setEstado("Reservado");
        reservaFacade.edit(this.reserva);
        // notificar aprobación
        correo.enviarEmail(this.reserva.getIdResidente().getIdPerfil().getCorreo(), "Reserva aprobada",
                correo.paginaCorreo("Reserva aprobada de: " + reserva.getIdZonaComunal().getNombre() + "",
                        " <p style='font-family: Arial, Helvetica, sans-serif;'>Estimado residente " + reserva.getIdResidente().getIdPerfil().getNombre() + ", la reserva de: " + reserva.getIdZonaComunal().getNombre() + " ha sido aprobada por el administrador del conjunto con el siguiente horario:</p>\n"
                        + "<p style='font-family: Arial, Helvetica, sans-serif;'><b>Fecha y hora inicio: " + hora.convertirfh(reserva.getFechaInicioReserva()) + "</b></p>\n"
                        + "<p style='font-family: Arial, Helvetica, sans-serif;'><b>Fecha y hora fin: " + hora.convertirfh(reserva.getFechaFinReserva()) + "</b></p>\n"
                        + "<p style='font-family: Arial, Helvetica, sans-serif;'><b>Nota: Recuerda, en llegado caso de cancelar tu reserva cuentas con 3h antes para no causar bloqueo</p>",
                        "http://imgfz.com/i/1CrnTaz.jpeg")
        );
        mensaje.setMensaje("EdicionVisitante('consultar-reserva.xhtml','Reserva aprobada satisfactoriamente','<b>*</b>La reserva se a notificado <br> vía email.<br><b>*</b>El estado de la reserva ahora<br> se encuentra en estado Reservado.');");

        int cantidadreserva = reserva.getIdZonaComunal().getCantidadReservada();
        zonaComunal = reserva.getIdZonaComunal();
        zonaComunal.setCantidadReservada(cantidadreserva = +1);
        zonaComunalFacade.edit(zonaComunal);
    }

    public String obtenerUltimoR(int idReserva) {
        List<Reserva> pepa = reservaFacade.buscarUltimaR(idReserva);
        String nombresaso = pepa.get(0).getMotivoReserva();
        return nombresaso;
    }

    public int contarReservas() {
        return reservaFacade.count();
    }

    public int contarReservas(int idZona) {
        return reservaFacade.ContarReserva(idZona);
    }

    public List<Reserva> findState(String estado) {
        return reservaFacade.findState(estado);
    }

    public List<Reserva> findStateR(String estado, int idResidente) {
        return reservaFacade.findStateR(estado, idResidente);
    }

    public List<Reserva> findR(int idResidente) {
        return reservaFacade.findR(idResidente);
    }

    // count
    public int countEstadoR(String estado, int idResidente) {
        return reservaFacade.countEstadoR(estado, idResidente);
    }

    public int countEstado(String estado) {
        return reservaFacade.countEstado(estado);
    }

    public int countR(int idResidente) {
        return reservaFacade.countR(idResidente);
    }

    //
    public void validarFecha(FacesContext context, UIComponent comp, Object value) {
        context = FacesContext.getCurrentInstance();
        String fechaSeleccionadas = (String) value;

        // Extraer fecha de string
        int dia = Integer.parseInt(fechaSeleccionadas.substring(0, 2));
        int mes = Integer.parseInt(fechaSeleccionadas.substring(3, 5));
        int anio = Integer.parseInt(fechaSeleccionadas.substring(6, 10));

        Calendar c = Calendar.getInstance();
        c.set(anio, mes, dia);
        c.add(Calendar.MONTH, -1);
        
        System.out.println("La seleccionada:"+hora.convertirf(c.getTime()));
        System.out.println("La actual: "+hora.convertirf(hora.now()));

        try {
            if (hora.convertirf(c.getTime()).compareTo(hora.convertirf(hora.now())) < 0) {
                ((UIInput) comp).setValid(false);
                context.addMessage(comp.getClientId(context), new FacesMessage("No se pueden reservar fechas pasadas, a la actual"));
                mensaje.setMensaje("MensajeAlertify('No se pueden reservar fechas pasadas, selecciona otra','error');");
            }
        } catch (Exception e) {
            System.out.println("Error validacion fecha r: "+e.getMessage());
            mensaje.setMensaje("MensajeAlertify('Error, selecciona otra fecha','error');");
        }

    }

    // Metodo Get y Set
    public Residente getResidente() {
        return residente;
    }

    public void setResidente(Residente residente) {
        this.residente = residente;
    }

    public ZonaComunal getZonaComunal() {
        return zonaComunal;
    }

    public void setZonaComunal(ZonaComunal zonaComunal) {
        this.zonaComunal = zonaComunal;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public String getFechaSeleccionada() {
        return fechaSeleccionada;
    }

    public void setFechaSeleccionada(String fechaSeleccionada) {
        this.fechaSeleccionada = fechaSeleccionada;
    }

}
