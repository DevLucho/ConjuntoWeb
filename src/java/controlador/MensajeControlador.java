/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Huertas
 */
@Named(value = "mensajeControlador")
@SessionScoped
public class MensajeControlador implements Serializable{

    /**
     * Creates a new instance of MensajeControlador
     */
    private String mensajes;

    public MensajeControlador() {
    }

    public String getMensajes() {
        return mensajes;
    }

    public void setMensajes(String mensajes) {
        this.mensajes = mensajes;
    }

}
