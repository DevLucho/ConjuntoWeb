/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.HoraFinal;
import entidades.HoraInicial;
import facade.HoraFinalFacade;
import facade.HoraInicialFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Huertas
 */
@Named(value = "horaControlador")
@SessionScoped
public class HoraControlador implements Serializable {

    /**
     * Creates a new instance of HoraControlador
     */
    private HoraInicial horaInicial;
    private HoraFinal horaFinal;

    @EJB
    HoraInicialFacade horaInicialFacade;
    @EJB
    HoraFinalFacade horaFinalFacade;

    public HoraControlador() {
        horaInicial = new HoraInicial();
        horaFinal = new HoraFinal();
    }

    // Consultar
    public List<HoraInicial> consultarHorasIniciales() {
        return horaInicialFacade.findAll();
    }

    public List<HoraFinal> consultarHorasFinales() {
        return horaFinalFacade.findAll();
    }

    // Generar fecha actual
    public Date now() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        return date;
    }

    // Dar formato a horas
    public String convertir(Date hora) {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(hora);
    }

    // Dar formato a fechas
    public String convertirf(Date fecha) {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.format(fecha);
    }

    public String convertirfh(Date hf) {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return df.format(hf);
    }

    // modificar dia +1 en fecha
    public Date fecha(Date fecha) {
        Calendar f = Calendar.getInstance();
        f.setTime(fecha);
        f.add(Calendar.DATE, 1);
        return f.getTime();
    }

    // Metodos get y set
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

}
