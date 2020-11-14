/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.security.NoSuchProviderException;
import javax.inject.Inject;
import javax.mail.MessagingException;

/**
 *
 * @author Huertas
 */
@Named(value = "contactoControlador")
@SessionScoped
public class ContactoControlador implements Serializable {

    /**
     * Creates a new instance of ContactoControlador
     */
    @Inject
    MensajeControlador mensajes;

    public ContactoControlador() {
        correo = new CorreoControlador();
    }

    private CorreoControlador correo;

    // ====== atributos mensaje
    private String nombre;
    private String email;
    private int torre;
    private int apto;
    private String asunto;
    private String mensaje;

    // ====== enviar msj
    public void enviarMsj() throws NoSuchProviderException, MessagingException {
        correo.enviarEmail("ldhuertas56@misena.edu.co", "Contacto residente",
                correo.paginaCorreo(asunto,
                        "<p style='font-family: Arial, Helvetica, sans-serif;'>De: " + nombre + ",</p>\n"
                        + "<p style='font-family: Arial, Helvetica, sans-serif;'>Inmueble: <b>" + torre + " - " + apto + "</b></p>\n"
                        + "<p style='font-family: Arial, Helvetica, sans-serif;'>Email de contacto: " + email + "</p>"
                        + "<p style='font-family: Arial, Helvetica, sans-serif;'>Mensaje: " + mensaje + "</p>",
                        "'#'"));
        mensajes.setMensaje("MensajeAlertify('Mensaje enviado','success');");
        this.nombre = "";
        this.email = "";
        this.torre = 0;
        this.apto = 0;
        this.asunto = "";
        this.mensaje = "";
    }

    // ====== Get y set
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CorreoControlador getCorreo() {
        return correo;
    }

    public void setCorreo(CorreoControlador correo) {
        this.correo = correo;
    }

    public int getTorre() {
        return torre;
    }

    public void setTorre(int torre) {
        this.torre = torre;
    }

    public int getApto() {
        return apto;
    }

    public void setApto(int apto) {
        this.apto = apto;
    }


}
