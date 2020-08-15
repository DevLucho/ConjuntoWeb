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
import java.util.List;
import java.util.Properties;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        codigo = new Codigo();
    }

    // notificar via email
    public void enviarEmail(String destinatario, String asunto, String descripcion) throws NoSuchProviderException, MessagingException {
        try {
            // propiedades  
            Properties propiedad = new Properties();
            propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
            propiedad.setProperty("mail.smtp.starttls.enable", "true");
            propiedad.setProperty("mail.smtp.port", "587");
            propiedad.setProperty("mail.smtp.auth", "true");
            // otros correos = conjuntow@gmail.com, conjuntooweb@gmail.com - misma contraseña
            String correoEnvia = "noresponder.conjuntoweb@gmail.com";
            String contrasena = "conjunto123";

            Session sesion = Session.getDefaultInstance(propiedad);

            // envio email
            MimeMessage mail = new MimeMessage(sesion);

            try {
                mail.setFrom(new InternetAddress(correoEnvia));
                mail.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
                mail.setSubject(asunto);
                mail.setContent(descripcion, "text/html");

                Transport transporte = sesion.getTransport("smtp");
                transporte.connect(correoEnvia, contrasena);
                transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
                transporte.close();

            } catch (AddressException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);

            } catch (MessagingException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (Exception e) {
            System.out.println("Error en envio email revisar: " + e.getMessage());
        }
    }

    public void generarCodigo() throws NoSuchProviderException, MessagingException {

        if (!correo.equals("")) {
            // generar codigo alfanumerico
            pos = (int) (rnd.nextDouble() * abecedario.length() - 1 + 0);
            num = (int) (rnd.nextDouble() * 9999 + 1000);
            codgenerado = codgenerado + abecedario.charAt(pos) + num + abecedario.charAt(pos + 1); //Estructura codigo 6 caracteres
            // set y crear codigo
            codigo.setCodigo(codgenerado);
            codigo.setEstado("Valido");
            codigoFacade.create(codigo);

            // envio email
            enviarEmail(correo, "Código de registro",
                    "<link href='https://fonts.googleapis.com/css2?family=Righteous&display=swap' rel='stylesheet'>\n" +
"<div style='margin: 0;padding: 0;box-sizing: border-box;font-family: Arial, Helvetica, sans-serif;font-size: 20px;'>\n" +
"    <div\n" +
"        style='width: 60;height: 150; margin: 3% 20% 5% 20%; overflow: hidden; -webkit-box-shadow: 0px 7px 12px 2px rgba(0, 0, 0, 0.53);-moz-box-shadow: 0px 7px 12px 2px rgba(0, 0, 0, 0.53);box-shadow: 0px 7px 12px 2px rgba(0, 0, 0, 0.53);'>\n" +
"        <section\n" +
"            style='padding: 3% 3%; background-color: rgb(231, 231, 231); border-bottom: solid rgba(212, 197, 197, 0.383) 2px;'>\n" +
"            <img style='width: 99%;' src='http://imgfz.com/i/MBLyXhF.png'>\n" +
"        </section>\n" +
"        <section style='padding-left: 9%;padding-top: 4%;'>\n" +
"            <img src='http://imgfz.com/i/Gv0ib8s.jpeg' style='width:90%;'>\n" +
"            <h1\n" +
"                style='font-family: Righteous, cursive;font-size: 50px;color: #33406A;position: absolute;margin: -22% 25%;'>\n" +
"                ¡Registro <br> Exitoso!</h1>\n" +
"        </section>\n" +
"        <section style='padding-left: 15%;padding-top: 2%;'>\n" +
"            <strong>Hola!!</strong><br><br>\n" +
"            <p>Has ingresado satisfactoriamente a tu <strong><i>ConjuntoWEB </i></strong>, explora <br> con libertad\n" +
"                todos\n" +
"                nuestros beneficios y aplicaciones.</p><br>\n" +
"        </section>\n" +
"        <p style='text-align: center;'>Usuario : 1000383826</p><br>\n" +
"        <p style='text-align: center;'>Contraseña : H5375IGH</p><br><br>\n" +
"        <section\n" +
"            style='text-align: center;padding-top: 3%;background-color: rgb(231, 231, 231);height: 40vh;font-size: 10px !important;border-top: solid rgba(212, 197, 197, 0.383) 2px;'>\n" +
"            <a style='font-size: 10px !important;' href='http://'>Términos y condiciones</a> | <a\n" +
"                href='http://'>Políticas de Privacidad</a>\n" +
"            <br><br><br>\n" +
"            <p style=\"font-size: 10px !important;\">© 2020 ConjuntoWEB · Todos los derechos reservados.</p>\n" +
"        </section>\n" +
"    </div>\n" +
"</div>");

            codigo = new Codigo();
            mensaje.setMensaje("MensajeAlertify('Código generado: " + codgenerado + "','success');");
            codgenerado = "";
            correo = "";
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
                if (codr.getCodigo().length() >= 7) {
                    return "nueva-clave?faces-redirect=true";
                } else {
                    return "vista/registro/registro?faces-redirect=true";
                }
            } else {
                mensaje.setMensaje("MensajeAlertify('Lo sentimos el código ingresado ya no es válido.','error');");
                return "";
            }
        } else {
            mensaje.setMensaje("MensajeAlertify('El código ingresado no existe o es incorrecto.','error');");
            return "";
        }
    }

    // Metodos recuperar contraseña ↓↓
    public String recuperarPass() throws NoSuchProviderException, MessagingException {
        user = codigoFacade.recuperarPass(recorreo);
        if (user.getCorreo() != null) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userPass", user);
            // generar codigo alfanumerico
            pos = (int) (rnd.nextDouble() * abecedario.length() - 1 + 0);
            num = (int) (rnd.nextDouble() * 9999 + 1000);
            codgenerado = codgenerado + abecedario.charAt(pos) + num + abecedario.charAt(pos + 1) + abecedario.charAt(pos); //Estructura codigo 7 caracteres
            // set y crear codigo
            codigo.setCodigo(codgenerado);
            codigo.setEstado("Valido");
            codigoFacade.create(codigo);

            // envio email
            enviarEmail(recorreo, "Restablecer contraseña", "Este es tu código de verificación: <b>" + codgenerado + "</b>");

            codigo = new Codigo();
            mensaje.setMensaje("MensajeAlertify('Código generado','success');");
            codgenerado = "";
            recorreo = "";
            return "validar-cod";
        } else {
            mensaje.setMensaje("MensajeAlertify('El correo especificado no existe en el sistema.','error');");
            return "";
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
