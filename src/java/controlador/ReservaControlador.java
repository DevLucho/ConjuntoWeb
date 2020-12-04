/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Disponibilidad;
import entidades.HoraFinal;
import entidades.HoraInicial;
import entidades.Reserva;
import entidades.Residente;
import entidades.ZonaComunal;
import facade.DisponibilidadFacade;
import facade.HoraFinalFacade;
import facade.HoraInicialFacade;
import facade.ReservaFacade;
import facade.ResidenteFacade;
import facade.ZonaComunalFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.security.NoSuchProviderException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.mail.MessagingException;
import org.primefaces.event.SelectEvent;

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
    private Date fechaSelected;
    private Date horaInicio;
    private Date horaFin;
    private HoraInicial horaInicial;
    private HoraFinal horaFinal;
    private boolean verifica = true;
    private boolean error = false;
    // listas
    private List<HoraFinal> horasDisponibles; // Horas mayores a la hora inicial
    private List<HoraInicial> horasIni = null;
    private List<Reserva> reservas = null;

    // atributos form
    private int horaI;
    private int horaF;
    private String motivo;
    private int idResidente;

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

    @EJB
    DisponibilidadFacade disponibilidad;

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

    public void limpiar() {
        fechaSelected = null;
        residente = new Residente();
        reserva = new Reserva();
        horaFinal = new HoraFinal();
        horaInicial = new HoraInicial();
        horaI = 0;
        reservaFacade.findAll();
    }

    public String filtrar() {
        if (error) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Atención!", "Debes reservar con un día de anticipación"));
        }
        return null;
    }

    public void registrar() throws NoSuchProviderException, MessagingException {
        try {
            reserva.setHoraInicioReserva(horaInicialFacade.find(horaI));
            reserva.setHoraFinReserva(horaFinalFacade.find(horaF));
            reserva.setIdZonaComunal(zonaComunalFacade.find(zonaComunal.getIdZonaComunal()));
            reserva.setIdResidente(residenteFacade.find(idResidente));
            reserva.setMotivoReserva(motivo);
            reserva.setEstado("Pendiente");
            reserva.setFechaReserva(this.fechaSelected);
            reservaFacade.create(reserva);
            mensaje.setMensaje("EdicionVisitante('#','Reserva generada satisfactoriamente','<b>*</b>Recuerde, tiene 3 horas antes para <br> cancelar sin causar bloqueo.<br><br><b>*</b>Su solicitud queda en estado pendiente<br> hasta que el administrador la apruebe.<br><br><b>*</b>Se a notificado vía email su reserva.');");

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
            fechaSelected = null;
            horaI = 0;
            horaF = 0;
            motivo = "";
            idResidente = 0;
        } catch (NumberFormatException e) {
            mensaje.setMensaje("Mensaje('Error','Vuelve a intentarlo...','error');");
            System.out.println("Error reserva" + e.getMessage());
        }

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

    public void onDateSelect(SelectEvent event) {
        error = false;
        try {
            if (hora.now().after(this.fechaSelected)) { // Si es una fecha posterior
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Atención!", "Debes reservar con un día de anticipación"));
                this.error = true;
            } else if (hora.now().equals(this.fechaSelected)) { // Si son iguales
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se pueden reservar fechas a la actual"));
                this.error = true;
            } else {
                horasIniSinReserva(this.fechaSelected);
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! " + e.getMessage() + "", ""));
        }
    }

    public void cargaHoras(AjaxBehaviorEvent event) {
        horasIniSinReserva(this.fechaSelected);
    }

    public Date extraerFecha(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        } catch (ParseException e) {
            mensaje.setMensaje("Mensaje('Error','Vuelve a intentarlo...','error');");
            System.out.println("Error en extraer fecha: " + e.getMessage());
            verifica = false;
        }
        return fechaDate;
    }

    public void horasIniSinReserva(Date fechaSelected) {
        reservas = reservaFacade.fechasReservadas(fechaSelected, this.zona.getZonaComunal().getIdZonaComunal()); // Busca reservas con la fecha seleccionada
        Disponibilidad zonaDisponibilidad = disponibilidad.ctlaSingleDisponilidad(this.zona.getZonaComunal().getIdZonaComunal());
        System.out.println("H." + zonaDisponibilidad.getHoraInicialReserva().getIdHora() + " "+(zonaDisponibilidad.getHoraFinalReserva().getIdHora() + 1));

        if (verifica) {
            try {
                if (reservas != null && this.fechaSelected != null) {
                    // Reservaciones desde - hasta
                    //Disponibilidad zona = disponibilidad.ctlaSingleDisponilidad(this.zona.getZonaComunal().getIdZonaComunal());
                    //List<HoraInicial> notBeetwen = new ArrayList<>();
                    //horasIni = horaInicialFacade.horaReservaMaxMin(zonaDisponibilidad.getHoraInicialReserva().getIdHora(), zonaDisponibilidad.getHoraFinalReserva().getIdHora() + 1);
                    //System.out.println("H." + zonaDisponibilidad.getHoraInicialReserva().getIdHora() + zonaDisponibilidad.getHoraFinalReserva().getIdHora() + 1);
                    horasIni = horaInicialFacade.findAll();
                    for (Reserva reservado : reservas) {
                        // Consulta las horas reservadas
                        List<HoraInicial> horasIni2 = horaInicialFacade.horasSinReserva(reservado.getHoraInicioReserva().getIdHora(), reservado.getHoraFinReserva().getIdHora() + 1);
                        horasIni.removeAll(horasIni2); // Remueve todas las horas reservadas de la lista
                    }
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! " + e.getMessage() + "", ""));
                System.out.println("Error remove horas: " + e.getMessage());
            }
        }
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
        String nombresaso = "";
        try {
            List<Reserva> pepa = reservaFacade.buscarUltimaR(idReserva);
            nombresaso = pepa.get(0).getMotivoReserva();
        } catch (Exception e) {
            System.out.println("Error obtener ultimo r: " + e.getMessage());
        }
        return nombresaso;
    }

    /*
    public void validarFecha(FacesContext context, UIComponent comp, Object value) {
        context = FacesContext.getCurrentInstance();
        String fechaValue = (String) value;

        try {
            if (hora.now().after(extraerFecha(fechaValue))) { // Si es una fecha posterior
                ((UIInput) comp).setValid(false);
                context.addMessage(comp.getClientId(context), new FacesMessage("No se pueden reservar fechas pasadas, a la actual"));
                mensaje.setMensaje("MensajeAlertify('No se pueden reservar fechas pasadas, selecciona otra','error');");
                this.error = true;
            } else if (hora.now().equals(extraerFecha(fechaValue))) { // Si son iguales
                ((UIInput) comp).setValid(false);
                context.addMessage(comp.getClientId(context), new FacesMessage("No se pueden reservar fechas a la actual"));
                mensaje.setMensaje("MensajeAlertify('No se pueden reservar fechas a la actual, selecciona otra','error');");
                this.error = true;
            }
        } catch (Exception e) {
            System.out.println("Error validacion fecha r: " + e.getMessage());
            mensaje.setMensaje("MensajeAlertify('Error, selecciona otra fecha','error');");
        }

    }
     */
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

    public int getHoraF() {
        return horaF;
    }

    public void setHoraF(int horaF) {
        this.horaF = horaF;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getIdResidente() {
        return idResidente;
    }

    public void setIdResidente(int idResidente) {
        this.idResidente = idResidente;
    }

    public Date getFechaSelected() {
        return fechaSelected;
    }

    public void setFechaSelected(Date fechaSelected) {
        this.fechaSelected = fechaSelected;
    }

    public List<HoraInicial> getHorasIni() {
        return horasIni;
    }

    public void setHorasIni(List<HoraInicial> horasIni) {
        this.horasIni = horasIni;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

}
