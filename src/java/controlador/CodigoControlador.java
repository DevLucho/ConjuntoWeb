/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Codigo;
import entidades.Usuario;
import facade.CodigoFacade;
import facade.UsuarioFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.security.NoSuchProviderException;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.faces.context.FacesContext;

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
    private CorreoControlador email;
    private Codigo codigo;
    private String cod;
    private Codigo codr = null;
    private Usuario user = null;
    // Generar codigo alfanumerico
    Random rnd = new Random();
    String abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String codgenerado = "";
    int pos = 0, num;
    // campos form
    private String correo = "";
    private String recorreo;
    private String contrasenia = "";
    private String vcontrasenia;

    @EJB
    CodigoFacade codigoFacade;

    @EJB
    UsuarioFacade usuarioFacade;

    public CodigoControlador() {
    }

    @PostConstruct
    public void init() {
        email = new CorreoControlador();
        codigo = new Codigo();
    }

    public void generarCodigo() throws NoSuchProviderException, MessagingException {

        if (!correo.equals("")) {
            // generar codigo alfanumerico
            pos = (int) (rnd.nextDouble() * abecedario.length() - 1 + 0);
            num = (int) (rnd.nextDouble() * 9999 + 1000);
            codgenerado = codgenerado + abecedario.charAt(pos) + num + abecedario.charAt(pos); //Estructura codigo 6 caracteres
            // set y crear codigo
            codigo.setCodigo(codgenerado);
            codigo.setEstado("Valido");
            codigoFacade.create(codigo);

            // envio email
            email.enviarEmail(correo, "Código de registro",
                    email.paginaCorreo("Código de registro",
                            "<p style='font-family: Arial, Helvetica, sans-serif;'>Para continuar con el proceso de registro al sistema porfavor utilize el siguiente código de registro que ha sido generado por tu administrador.</p>\n"
                            + "<p style='font-family: Arial, Helvetica, sans-serif;'>Código: <b>" + codgenerado + "</b></p>\n"
                            + "<p style='font-family: Arial, Helvetica, sans-serif;'><b>Nota: </b>Una vez utilizado el código este expirará.</p>",
                            "http://imgfz.com/i/65vndEs.png")
            );

            codigo = new Codigo();
            mensaje.setMensaje("MensajeAlertify('Código enviado: " + codgenerado + "','success');");
            codgenerado = "";
            correo = "";
            email = new CorreoControlador();
        } else {
            mensaje.setMensaje("MensajeAlertify('Error de envio, digité un correo','error');");
        }

    }

    // condigo registro - contraseña
    public String validarCodigo() {
        codr = codigoFacade.validarCodigo(cod);
        if (codr.getCodigo() != null) {
            if ("Valido".equals(codr.getEstado())) {
                codr.setEstado("Invalido");
                codigoFacade.edit(codr);
                if (codr.getCodigo().length() >= 7) { // >=7 = recuperar clave
                    return "nueva-clave";
                } else {
                    Codigo c = new Codigo();
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogueado", c);
                    return "vista/registro/registro?faces-redirect=true";
                }
            } else {
                mensaje.setMensaje("MensajeAlertify('Lo sentimos el código ingresado ya no es válido.','error');");
                return "validar-cod";
            }
        } else {
            mensaje.setMensaje("MensajeAlertify('El código ingresado no existe o es incorrecto.','error');");
            return "validar-cod";
        }
    }

    // Metodos recuperar contraseña ↓↓
    public String recuperarPass() throws NoSuchProviderException, MessagingException {
        user = codigoFacade.recuperarPass(recorreo);
        if (user.getCorreo() != null) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogueado", user);
            // generar codigo alfanumerico
            pos = (int) (rnd.nextDouble() * abecedario.length() - 1 + 0);
            num = (int) (rnd.nextDouble() * 9999 + 1000);
            codgenerado = codgenerado + abecedario.charAt(pos) + num + abecedario.charAt(pos) + abecedario.charAt(pos); //Estructura codigo 7 caracteres
            // set y crear codigo
            codigo.setCodigo(codgenerado);
            codigo.setEstado("Valido");
            codigoFacade.create(codigo);

            // envio email
            email.enviarEmail(recorreo, "Restablecer contraseña",
                    email.paginaCorreo("Restablecer la contraseña",
                            "<p style='font-family: Arial, Helvetica, sans-serif;'>Hola " + user.getNombre() + ",</p>\n"
                            + "<p style='font-family: Arial, Helvetica, sans-serif;'>Has solicitado restablecer la contraseña de tu cuenta. Para continuar utilice el siguiente código de verificación:</p>\n"
                            + "<p style='font-family: Arial, Helvetica, sans-serif;'>Código: <b>" + codgenerado + "</b></p>\n"
                            + "<p style='font-family: Arial, Helvetica, sans-serif;'><b>Nota: </b>Si no solicitaste restablecer la contraseña, por favor ignorar este mensaje. </p>",
                            "'#'")
            );

            email = new CorreoControlador();
            codigo = new Codigo();
            mensaje.setMensaje("MensajeAlertify('Código generado','success');");
            codgenerado = "";
            recorreo = "";
            return "validar-cod";
        } else {
            mensaje.setMensaje("MensajeAlertify('El correo especificado no existe en el sistema.','error');");
            return "restablecer-clave";
        }
    }

    public void cambiarPass() {
        user.setContrasenia(contrasenia);
        if (contrasenia.length() < 8 || contrasenia.length() > 20) {
            mensaje.setMensaje("MensajeAlertify('Contrase&ntilde;a inv&aacute;lida','error');");
        } else if (contrasenia == null ? vcontrasenia != null : !contrasenia.equals(vcontrasenia)) {
            mensaje.setMensaje("MensajeAlertify('Contraseñas no coinciden','error');");
        } else {
            usuarioFacade.edit(user);
            mensaje.setMensaje("MensajeRedirect('login.xhtml','Su contraseña se ha registrado satisfactoriamente.','A continuación sera redireccionado al inicio de sesión del sistema','success');");
            try {
                FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
                this.user = null;
                this.recorreo = "";
                this.contrasenia = "";
            } catch (Exception e) {
                System.out.println("Error en logout revisar: " + e.getMessage());
            }
        }
    }
    
    // Metodos set y get ↓↓
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String getRecorreo() {
        return recorreo;
    }

    public void setRecorreo(String recorreo) {
        this.recorreo = recorreo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getVcontrasenia() {
        return vcontrasenia;
    }

    public void setVcontrasenia(String vcontrasenia) {
        this.vcontrasenia = vcontrasenia;
    }

}
