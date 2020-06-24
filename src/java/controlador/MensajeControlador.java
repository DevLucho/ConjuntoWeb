/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Huertas
 */
@Named(value = "mensajeControlador")
@RequestScoped
public class MensajeControlador{

    /**
     * Creates a new instance of MensajeControlador
     */
    private String mensaje;

    public MensajeControlador() {
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
