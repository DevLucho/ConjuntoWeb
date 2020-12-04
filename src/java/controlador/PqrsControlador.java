/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Pqrs;
import entidades.Residente;
import entidades.Respuesta;
import entidades.TipoPqrs;
import facade.PqrsFacade;
import facade.ResidenteFacade;
import facade.TipoPqrsFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author Huertas
 */
@Named(value = "pqrsControlador")
@SessionScoped
public class PqrsControlador implements Serializable {

    /**
     * Creates a new instance of PqrsControlador
     */
    @Inject
    private MensajeControlador mensaje;
    @Inject
    private HoraControlador hora;
    private ImagenControlador imagen;
    private Pqrs pqrs;
    private Residente residente;
    private TipoPqrs tipoPqrs;
    // Generar numero radicado
    Random rnd = new Random();
    String abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String radicado = "";
    int pos = 0, num;

    @EJB
    PqrsFacade pqrsFacade;

    @EJB
    ResidenteFacade residenteFacade;

    @EJB
    TipoPqrsFacade tipoPqrsFacade;

    public PqrsControlador() {
    }

    @PostConstruct
    public void init() {
        residente = new Residente();
        tipoPqrs = new TipoPqrs();
        imagen = new ImagenControlador();
        pqrs = new Pqrs();
    }

    public void registar() {
        try {
            // generar numero radicado
            pos = (int) (rnd.nextDouble() * abecedario.length() - 1 + 0);
            num = (int) (rnd.nextDouble() * 9999 + 1000);
            radicado = radicado + abecedario.charAt(pos) + abecedario.charAt(pos) + num + abecedario.charAt(pos); //Estructura codigo
            pqrs.setNroRadicado(radicado);
            pqrs.setIdResidente(residenteFacade.find(residente.getIdResidente()));
            pqrs.setIdTipoPqrs(tipoPqrsFacade.find(tipoPqrs.getIdTipoPqrs()));
            pqrs.setFecha(hora.now());
            pqrs.setHora(hora.now());
            pqrs.setEstado("Pendiente");
            if (imagen.getImg() != null) {
                // --- Subir img --- 
                imagen.subirImagen(1);
                pqrs.setAdjunto("../../../img/" + imagen.getImg().getSubmittedFileName());
            }
            pqrsFacade.create(pqrs);
            residente = new Residente();
            tipoPqrs = new TipoPqrs();
            imagen = new ImagenControlador();
            pqrs = new Pqrs();
            mensaje.setMensaje("Mensajes('Se ha generado la PQRS','Su número de radicado es: " + radicado + "','success');");
            radicado = "";
        } catch (Exception e) {
            System.out.println("error en pqrs revisar: " + e.getMessage());
        }

    }

    public List<Pqrs> consultar() {
        return pqrsFacade.findAll();
    }

    public String consultarPqrs(Pqrs pqrsConsultar) {
        residente = pqrsConsultar.getIdResidente();
        tipoPqrs = pqrsConsultar.getIdTipoPqrs();
        pqrs = pqrsConsultar;
        return "detalle-pqrs";
    }

    public String consultarRespuesta(Respuesta respuestaConsultar) {
        pqrs = respuestaConsultar.getIdPqrs();
        tipoPqrs = respuestaConsultar.getIdPqrs().getIdTipoPqrs();
        return "respuesta-pqrs";
    }

    public String preActualizar(Pqrs pqrsActualizar) {
        residente = pqrsActualizar.getIdResidente();
        tipoPqrs = pqrsActualizar.getIdTipoPqrs();
        pqrs = pqrsActualizar;
        return "actualizar_pqrs";
    }

    public String actualizar() {
        pqrs.setIdResidente(residente);
        pqrs.setIdTipoPqrs(tipoPqrs);
        pqrsFacade.edit(pqrs);
        return "consultar_mis_solicitudes";
    }

    public void cancelar(Pqrs pqrsCancelar) {
        if ("Cancelado".equals(pqrsCancelar.getEstado())) {
            mensaje.setMensaje("Mensajes('Advertencia!','Esta pqrs ya esta cancelada','warning');");
        } else {
            mensaje.setMensaje("Confirmar('Estas seguro que deseas cancelar la pqrs?','No podras revertilo!','warning','Si, cancelar!','Cancelado!','Se ha cancelado la pqrs.','success');");
            pqrs = pqrsCancelar;
            pqrs.setEstado("Cancelado");
            pqrsFacade.edit(pqrsCancelar);
        }
    }

    public void eliminar(Pqrs pqrsEliminar) {
        pqrsFacade.remove(pqrsEliminar);
    }

    public int contarPqrs() {
        return pqrsFacade.count();
    }

    public List<Pqrs> consultarCancelados() {
        return pqrsFacade.estadoPqrs("Cancelado");
    }

    public List<Pqrs> consultarResueltos() {
        return pqrsFacade.estadoPqrs("Resuelto");
    }

    public List<Pqrs> consultarPendientes() {
        return pqrsFacade.estadoPqrs("Pendiente");
    }

    public List<Pqrs> consultarAbiertos() {
        return pqrsFacade.estadoPqrs("Abierto");
    }

    public List<Pqrs> estadoPqrsR(String estado, int idResidente) {
        return pqrsFacade.estadoPqrsR(estado, idResidente);
    }

    public List<Pqrs> pqrsResidente(int idResidente) {
        return pqrsFacade.pqrsResidente(idResidente);
    }

    public String obtenerUltimoP(int idPqrs) {
        String nombresito = "";
        try {
            List<Pqrs> pepaa = pqrsFacade.buscarUltimoP(idPqrs);
            nombresito = pepaa.get(0).getAsunto();
        } catch (Exception e) {
            System.out.println("Error ultimo p: " + e.getMessage());
        }
        return nombresito;
    }

    public int contarporTipos(String tipoPqrs) {
        return pqrsFacade.contarPqrsTipos(tipoPqrs);
    }

    public int countState(String estado) {
        return pqrsFacade.countEstado(estado);
    }

    public int contarEstadoR(String estado, int idResidente) {
        return pqrsFacade.countEstadoR(estado, idResidente);
    }

    public int contarR(int idResidente) {
        return pqrsFacade.countR(idResidente);
    }

    public Pqrs getPqrs() {
        return pqrs;
    }

    public void setPqrs(Pqrs pqrs) {
        this.pqrs = pqrs;
    }

    public Residente getResidente() {
        return residente;
    }

    public void setResidente(Residente residente) {
        this.residente = residente;
    }

    public TipoPqrs getTipoPqrs() {
        return tipoPqrs;
    }

    public void setTipoPqrs(TipoPqrs tipoPqrs) {
        this.tipoPqrs = tipoPqrs;
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
