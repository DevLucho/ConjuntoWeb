/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Codigo;
import facade.CodigoFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author Huertas
 */
@Named(value = "codigoControlador")
@SessionScoped
public class CodigoControlador implements Serializable {

    /**
     * Creates a new instance of CodigoControlador
     */
    @Inject
    private MensajeControlador mensaje;
    private Codigo codigo;
    private String cod;
    private Codigo codr = null;
    // Generar codigo aleatorio
    Random rnd = new Random();
    String abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String codgenerado = "";
    int pos = 0, num;

    @EJB
    CodigoFacade codigoFacade;

    public CodigoControlador() {
    }

    @PostConstruct
    public void init() {
        codigo = new Codigo();
    }

    public void generarCodigo() {
        try {
            // generar codigo aleatorio
            pos = (int) (rnd.nextDouble() * abecedario.length() - 1 + 0);
            num = (int) (rnd.nextDouble() * 9999 + 1000);
            codgenerado = codgenerado + abecedario.charAt(pos) + num + abecedario.charAt(pos + 1); //Estructura codigo
            pos = (int) (rnd.nextDouble() * abecedario.length() - 1 + 0);
            codigo.setCodigo(codgenerado);
            codigo.setEstado("Valido");
            codigoFacade.create(codigo);
            codigo = new Codigo();
            mensaje.setMensaje("MensajeAlertify('Código generado: "+codgenerado+"','success');");
            codgenerado = "";
        } catch (Exception e) {
            System.out.println("Error en codigo generado revisar: " + e.getMessage());
        }

    }

    public String validarCodigo() {
        codr = codigoFacade.validarCodigo(cod);
        if (codr.getCodigo() != null) {
            if ("Valido".equals(codr.getEstado())) {
                codr.setEstado("Invalido");
                codigoFacade.edit(codr);
                return "vista/registro/registro?faces-redirect=true";
            } else {
                mensaje.setMensaje("MensajeAlertify('Lo sentimos el código ingresado ya no es válido.','error');");
                return "";
            }
        } else {
            mensaje.setMensaje("MensajeAlertify('El código ingresado no existe o es incorrecto.','error');");
            return "";
        }
    }

    public Codigo getCodigo() {
        return codigo;
    }

    public void setCodigo(Codigo codigo) {
        this.codigo = codigo;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Codigo getCodr() {
        return codr;
    }

    public void setCodr(Codigo codr) {
        this.codr = codr;
    }

}
