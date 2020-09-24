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
                    correo.paginaCorreo("Reserva pendiente de la zona: "+reserva.getIdZonaComunal().getNombre()+"",
                            " <p style='font-family: Arial, Helvetica, sans-serif;'>Estimado residente " + reserva.getIdResidente().getIdPerfil().getNombre() + ", la reserva de: "+reserva.getIdZonaComunal().getNombre()+" ha sido realizada exitosamente con el siguiente horario:</p>\n"
                            + "<p style='font-family: Arial, Helvetica, sans-serif;'><b>Fecha y hora inicio: " + hora.convertirfh(reserva.getFechaInicioReserva()) +"</b></p>\n"
                            + "<p style='font-family: Arial, Helvetica, sans-serif;'><b>Fecha y hora fin: " + hora.convertirfh(reserva.getFechaFinReserva()) +"</b></p>\n"
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

    public int contarReservas() {
        return reservaFacade.count();
    }
    public String obtenerUltimoR(int idReserva){
        List<Reserva> pepa = reservaFacade.buscarUltimaR(idReserva);
        String nombresaso = pepa.get(0).getMotivoReserva();
        return nombresaso;
    }
    
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
