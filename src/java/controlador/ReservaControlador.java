/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.HoraFinal;
import entidades.HoraInicial;
import entidades.Reserva;
import entidades.Residente;
import entidades.ZonaComunal;
import facade.HoraFinalFacade;
import facade.HoraInicialFacade;
import facade.ReservaFacade;
import facade.ResidenteFacade;
import facade.ZonaComunalFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
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
    @Inject
    ZonaComunalControlador zona;

    private Residente residente;
    private ZonaComunal zonaComunal;
    private Reserva reserva;
    private String fechaSeleccionada;
    private Date horaInicio;
    private Date horaFin;
    private HoraInicial horaInicial;
    private HoraFinal horaFinal;
    private int horaI;
    private boolean verifica = true;

    // listas
    private List<HoraFinal> horasDisponibles; // Horas mayores a la hora inicial
    private List<HoraInicial> horasIni = null;
    private List<Reserva> reservas = null;

    @EJB
    ResidenteFacade residenteFacade;

    @EJB
    ZonaComunalFacade zonaComunalFacade;

    @EJB
    ReservaFacade reservaFacade;

    @EJB
    HoraInicialFacade horaInicialFacade;

    @EJB
    HoraFinalFacade horaFinalFacade;

    public ReservaControlador() {
    }

    @PostConstruct
    public void init() {
        residente = new Residente();
        zonaComunal = new ZonaComunal();
        reserva = new Reserva();
        horaInicial = new HoraInicial();
        horaFinal = new HoraFinal();
    }

    public String limpiar() {
        fechaSeleccionada = null;
        return null;
    }

    public String filtrar() {
        return null;
    }

    public Date extraerFecha(String fecha) {
        Calendar c = Calendar.getInstance();
        try {
            int dia = Integer.parseInt(fecha.substring(0, 2));
            int mes = Integer.parseInt(fecha.substring(3, 5));
            int anio = Integer.parseInt(fecha.substring(6, 10));
            c.set(anio, mes, dia);
            c.add(Calendar.MONTH, -1);
        } catch (Exception e) {
            mensaje.setMensaje("Mensaje('Error','Vuelve a intentarlo...','error');");
            System.out.println("Error en extraer fecha: " + e.getMessage());
            verifica = false;
        }
        return c.getTime();
    }

    public void registrar() throws NoSuchProviderException, MessagingException {
        try {
            reserva.setHoraInicioReserva(horaInicialFacade.find(horaI));
            reserva.setHoraFinReserva(horaFinalFacade.find(horaFinal.getIdHora()));
            reserva.setIdZonaComunal(zonaComunalFacade.find(zonaComunal.getIdZonaComunal()));
            reserva.setIdResidente(residenteFacade.find(residente.getIdResidente()));
            reserva.setEstado("Pendiente");

            reserva.setFechaReserva(extraerFecha(this.fechaSeleccionada));

            reservaFacade.create(reserva);
            mensaje.setMensaje("EdicionVisitante('consultar-reserva.xhtml','Reserva generada satisfactoriamente','<b>*</b>Recuerde, tiene 3 horas antes para <br> cancelar sin causar bloqueo.<br><br><b>*</b>Su solicitud queda en estado pendiente<br> hasta que el administrador la apruebe.<br><br><b>*</b>Se a notificado vía email su reserva.');");

            correo.enviarEmail(reserva.getIdResidente().getIdPerfil().getCorreo(), "Confirmacion de reserva común",
                    correo.paginaCorreo("Reserva pendiente de la zona: " + reserva.getIdZonaComunal().getNombre() + "",
                            " <p style='font-family: Arial, Helvetica, sans-serif;'>Estimado residente " + reserva.getIdResidente().getIdPerfil().getNombre() + ", la reserva de: " + reserva.getIdZonaComunal().getNombre() + " ha sido realizada exitosamente con el siguiente horario:</p>\n"
                            + "<p style='font-family: Arial, Helvetica, sans-serif;'><b>Fecha y hora inicio: " + hora.convertirf(reserva.getFechaReserva()) + " " + hora.convertir(reserva.getHoraInicioReserva().getHora()) + "</b></p>\n"
                            + "<p style='font-family: Arial, Helvetica, sans-serif;'><b>Fecha y hora fin: " + hora.convertirf(reserva.getFechaReserva()) + " " + hora.convertir(reserva.getHoraFinReserva().getHora()) + "</b></p>\n"
                            + "<p style='font-family: Arial, Helvetica, sans-serif;'><b>Nota: Recuerda, tu petición de reserva se encuentra <b> Pendiente </b>, por lo tanto, el Administrador aprobara o no dicha reserva.</p>",
                            "http://imgfz.com/i/1CrnTaz.jpeg")
            );

            residente = new Residente();
            //zonaComunal = new ZonaComunal();
            reserva = new Reserva();
            horaFinal = new HoraFinal();
            horaInicial = new HoraInicial();
            fechaSeleccionada = null;
            horaI = 0;

        } catch (NumberFormatException e) {
            mensaje.setMensaje("Mensaje('Error','Vuelve a intentarlo...','error');");
            System.out.println("Error reserva" + e.getMessage());
        }

    }

    /*
       public void handleClose(CloseEvent event) {
        addMessage(event.getComponent().getId() + " closed", "So you don't like nature?");
    }
     
    public void handleMove(MoveEvent event) {
        addMessage(event.getComponent().getId() + " moved", "Left: " + event.getLeft() + ", Top: " + event.getTop());
    }
     
    public void destroyWorld() {
        addMessage("System Error", "Please try again later.");
    }
     
    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }*/

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
                        + "<p style='font-family: Arial, Helvetica, sans-serif;'><b>Fecha y hora inicio: " + hora.convertirf(reserva.getFechaReserva()) + " " + hora.convertir(reserva.getHoraInicioReserva().getHora()) + "</b></p>\n"
                        + "<p style='font-family: Arial, Helvetica, sans-serif;'><b>Fecha y hora fin: " + hora.convertirf(reserva.getFechaReserva()) + " " + hora.convertir(reserva.getHoraFinReserva().getHora()) + "</b></p>\n"
                        + "<p style='font-family: Arial, Helvetica, sans-serif;'><b>Nota: Recuerda, en llegado caso de cancelar tu reserva cuentas con 3h antes para no causar bloqueo</p>",
                        "http://imgfz.com/i/1CrnTaz.jpeg")
        );
        mensaje.setMensaje("EdicionVisitante('consultar-reserva.xhtml','Reserva aprobada satisfactoriamente','<b>*</b>La reserva se a notificado <br> vía email.<br><b>*</b>El estado de la reserva ahora<br> se encuentra en estado Reservado.');");

        zonaComunal = reserva.getIdZonaComunal();
        zonaComunal.setCantidadReservada(reserva.getIdZonaComunal().getCantidadReservada() + 1);
        zonaComunalFacade.edit(zonaComunal);
    }

    public void validarFecha(FacesContext context, UIComponent comp, Object value) {
        context = FacesContext.getCurrentInstance();
        String fechaValue = (String) value;

        try {
            if (hora.now().after(extraerFecha(fechaValue))) { // Si es una fecha posterior
                ((UIInput) comp).setValid(false);
                context.addMessage(comp.getClientId(context), new FacesMessage("No se pueden reservar fechas pasadas, a la actual"));
                mensaje.setMensaje("MensajeAlertify('No se pueden reservar fechas pasadas, selecciona otra','error');");
            } else if (hora.now().equals(extraerFecha(fechaValue))) { // Si son iguales
                ((UIInput) comp).setValid(false);
                context.addMessage(comp.getClientId(context), new FacesMessage("No se pueden reservar fechas a la actual"));
                mensaje.setMensaje("MensajeAlertify('No se pueden reservar fechas a la actual, selecciona otra','error');");
            }
        } catch (Exception e) {
            System.out.println("Error validacion fecha r: " + e.getMessage());
            mensaje.setMensaje("MensajeAlertify('Error, selecciona otra fecha','error');");
        }

    }

    public List<HoraInicial> horasIniSinReserva() {
        reservas = reservaFacade.fechasReservadas(extraerFecha(this.fechaSeleccionada), this.zona.getZonaComunal().getIdZonaComunal()); // Busca reservas con la fecha seleccionada
        if (verifica) {
            try {
                if (reservas != null && this.fechaSeleccionada != null) {
                    horasIni = horaInicialFacade.findAll();
                    for (Reserva reservado : reservas) {
                        // Consulta las horas reservadas
                        List<HoraInicial> horasIni2 = horaInicialFacade.horasSinReserva(reservado.getHoraInicioReserva().getIdHora(), reservado.getHoraFinReserva().getIdHora() + 1);
                        horasIni.removeAll(horasIni2); // Remueve todas las horas reservadas de la lista
                    }
                }
            } catch (Exception e) {
                System.out.println("Error remove horas: " + e.getMessage());
            }
        }

        return horasIni;
    }

    public void horasFinales(AjaxBehaviorEvent event) {
        List<HoraFinal> horasFinales = horaFinalFacade.horasFinales(this.horaI); // Consulta horas mayores a la hora i. seleccionada
        HoraInicial objHora = horaInicialFacade.find(this.horaI); // Obj hora inicio seleccionada
        ZonaComunal zona2 = zona.getZonaComunal(); // Obj con la zona actual
        horasDisponibles = new ArrayList<>(); // Almacena horas dependiendo que cumplan con el t. max. de reserva
        try {

            if (horasFinales != null) {
                for (HoraFinal horaF : horasFinales) {
                    long diferencia;
                    // calcula diferencia en minutos
                    if (objHora.getHora().getTime() > horaF.getHora().getTime()) {
                        diferencia = objHora.getHora().getTime() - horaF.getHora().getTime();
                    } else {
                        diferencia = horaF.getHora().getTime() - objHora.getHora().getTime();
                    }
                    long horasDiff = (TimeUnit.MILLISECONDS.toMinutes(diferencia)) / 60; // Operacion para pasar de minutos a horas.

                    if (horasDiff <= zona2.getTiempoMaximoReserva()) { // Agregar horas que cumplan con el t. max de reserva
                        horasDisponibles.add(new HoraFinal(horaF.getIdHora(), horaF.getHora()));
                    }
                }
                // elimina horas reservadas
                for (Reserva reservado : reservas) {
                    List<HoraFinal> horasFin = horaFinalFacade.horasSinReserva(reservado.getHoraInicioReserva().getIdHora() - 1, reservado.getHoraFinReserva().getIdHora());
                    horasDisponibles.removeAll(horasFin);
                }
            }

        } catch (Exception e) {
            System.out.println("Error horas finales: " + e.getMessage());
        }
    }

    // Metodos de consulta
    public String obtenerUltimoR(int idReserva) {
        List<Reserva> pepa = reservaFacade.buscarUltimaR(idReserva);
        String nombresaso = pepa.get(0).getMotivoReserva();
        return nombresaso;
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

    public List<Reserva> consultar() {
        return reservaFacade.findAll();
    }

    public List<Reserva> consultarDesc() {
        return reservaFacade.findDesc();
    }

    public String findReserva(Reserva reserva) {
        this.zonaComunal = reserva.getIdZonaComunal();
        this.residente = reserva.getIdResidente();
        this.reserva = reserva;
        return "detalle-reserva";
    }

    // Metodos count
    public int contarReservas() {
        return reservaFacade.count();
    }

    public int contarReservas(int idZona) {
        return reservaFacade.ContarReserva(idZona);
    }

    public int countEstadoR(String estado, int idResidente) {
        return reservaFacade.countEstadoR(estado, idResidente);
    }

    public int countEstado(String estado) {
        return reservaFacade.countEstado(estado);
    }

    public int countR(int idResidente) {
        return reservaFacade.countR(idResidente);
    }

    // Metodos Get y Set
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

    public int getHoraI() {
        return horaI;
    }

    public void setHoraI(int horaI) {
        this.horaI = horaI;
    }

    public List<HoraFinal> getHorasDisponibles() {
        return horasDisponibles;
    }

    public void setHorasDisponibles(List<HoraFinal> horasDisponibles) {
        this.horasDisponibles = horasDisponibles;
    }

    public boolean isVerifica() {
        return verifica;
    }

    public void setVerifica(boolean verifica) {
        this.verifica = verifica;
    }

}
