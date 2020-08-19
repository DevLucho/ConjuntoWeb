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
                    "<div style='\n"
                    + "    width:500px;\n"
                    + "    background-color:\n"
                    + "    #282828;margin-top: 50px !important;\n"
                    + "    min-height: 115px;\n"
                    + "    border-radius: 4px 4px 0 0 ;\n"
                    + "    box-shadow: 2px 0px 3px 0px rgba(0,0,0, 0.3);\n"
                    + "    position: relative;\n"
                    + "    min-height: 70px;\n"
                    + "    margin: 0 auto;\n"
                    + "    padding: 20px;\n"
                    + "    text-align: center;'>\n"
                    + "        <div>\n"
                    + "            <div>\n"
                    + "                <a href='#'>\n"
                    + "                    <img src='http://imgfz.com/i/9mEWVtU.png' style='width: 300px;'>\n"
                    + "                </a>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "    </div>\n"
                    + "    <hr style='\n"
                    + "    margin-top: 1rem;\n"
                    + "    margin-bottom: 1rem;\n"
                    + "    border: 0;\n"
                    + "    border-top: 1px solid rgba(0,0,0,.1);'>\n"
                    + "    <div style='padding:20px;width: 500px;margin-right: auto;margin-left: auto;border: 1px solid rgba(0,0,0,.1);'>\n"
                    + "        <div style='\n"
                    + "        margin:0 auto;\n"
                    + "        background: #ffffff;\n"
                    + "        box-shadow: 2px 0px 3px 0px rgba(0,0,0, 0.3);\n"
                    + "        border-radius: 0 0 4px 4px ;'>\n"
                    + "            <img src='http://imgfz.com/i/65vndEs.png' style='width: 100% ;background-attachment: fixed'>\n"
                    + "        </div>\n"
                    + "            <!-- Titulo y texto informativo -->\n"
                    + "        <div>\n"
                    + "            <h2 style='font-family: Arial, Helvetica, sans-serif;color:#33406A;font-weight: bold;'>\n"
                    + "                Código de registro\n"
                    + "            </h2>\n"
                    + "            <hr>\n"
                    + "            <p style='font-family: Arial, Helvetica, sans-serif;'>Para continuar con el proceso de registro al sistema porfavor utilize el siguiente código de registro:</p>\n"
                    + "            <p style='font-family: Arial, Helvetica, sans-serif;'>Código: <b>" + codgenerado + "</b></p>\n"
                    + "            <small style='font-family: Arial, Helvetica, sans-serif;'><b>Nota: </b>Una vez utilizado el código este expirará.</small>\n"
                    + "        </div>\n"
                    + "    </div>\n"
                    + "    <hr style='\n"
                    + "    margin-top: 1rem;\n"
                    + "    margin-bottom: 1rem;\n"
                    + "    border: 0;\n"
                    + "    border-top: 1px solid rgba(0,0,0,.1);'>\n"
                    + "    <div style='\n"
                    + "    width:500px;\n"
                    + "    background-color:#282828;\n"
                    + "    min-height: 115px;\n"
                    + "    border-radius: 0px 0px 4px 4px ;\n"
                    + "    box-shadow: 2px 0px 3px 0px rgba(0,0,0, 0.3);\n"
                    + "    position: relative;\n"
                    + "    min-height: 70px;\n"
                    + "    margin: 0 auto;\n"
                    + "    padding: 20px;\n"
                    + "    text-align: center;'>\n"
                    + "        <div>\n"
                    + "            <div style=\"color: white;font-family: Arial, Helvetica, sans-serif;\">\n"
                    + "                <small>\n"
                    + "                    <a href='#' style=\"color: white;font-family: Arial, Helvetica, sans-serif;\">\n"
                    + "                        Términos y condiciones\n"
                    + "                    </a>\n"
                    + "                    &nbsp;\n"
                    + "                    |\n"
                    + "                    &nbsp;\n"
                    + "                    <a href='#' style=\"color: white;font-family: Arial, Helvetica, sans-serif;\">\n"
                    + "                        Políticas de privacidad\n"
                    + "                    </a>\n"
                    + "                </small>\n"
                    + "                <br />\n"
                    + "                <br />\n"
                    + "                <div>\n"
                    + "                    <small>© 2020 Todos los derechos reservados.</small>\n"
                    + "                    <br>\n"
                    + "                    <a href='#'><img src='http://imgfz.com/i/HxYQDRd.png' /></a>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "    </div>"
            );

            codigo = new Codigo();
            mensaje.setMensaje("MensajeAlertify('Código enviado: " + codgenerado + "','success');");
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
                if (codr.getCodigo().length() >= 7) { // >=7 = recuperar clave
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
            enviarEmail(recorreo, "Restablecer contraseña",
                    "<div style='\n"
                    + "    width:500px;\n"
                    + "    background-color:\n"
                    + "    #282828;margin-top: 50px !important;\n"
                    + "    min-height: 115px;\n"
                    + "    border-radius: 4px 4px 0 0 ;\n"
                    + "    box-shadow: 2px 0px 3px 0px rgba(0,0,0, 0.3);\n"
                    + "    position: relative;\n"
                    + "    min-height: 70px;\n"
                    + "    margin: 0 auto;\n"
                    + "    padding: 20px;\n"
                    + "    text-align: center;'>\n"
                    + "        <div>\n"
                    + "            <div>\n"
                    + "                <a href='#'>\n"
                    + "                    <img src='http://imgfz.com/i/9mEWVtU.png' style='width: 300px;'>\n"
                    + "                </a>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "    </div>\n"
                    + "    <hr style='\n"
                    + "    margin-top: 1rem;\n"
                    + "    margin-bottom: 1rem;\n"
                    + "    border: 0;\n"
                    + "    border-top: 1px solid rgba(0,0,0,.1);'>\n"
                    + "    <div style='padding:20px;width: 500px;margin-right: auto;margin-left: auto;border: 1px solid rgba(0,0,0,.1);'>\n"
                    + "        <div style='\n"
                    + "        margin:0 auto;\n"
                    + "        background: #ffffff;\n"
                    + "        box-shadow: 2px 0px 3px 0px rgba(0,0,0, 0.3);\n"
                    + "        border-radius: 0 0 4px 4px ;'>\n"
                    + "            <!--<img src='#' style='width: 100% ;background-attachment: fixed'>-->\n"
                    + "        </div>\n"
                    + "            <!-- Titulo y texto informativo -->\n"
                    + "        <div>\n"
                    + "            <h2 style='font-family: Arial, Helvetica, sans-serif;color:#33406A;font-weight: bold;margin-top:0px;'>\n"
                    + "                Restablecer la contraseña\n"
                    + "            </h2>\n"
                    + "            <hr>\n"
                    + "            <p style='font-family: Arial, Helvetica, sans-serif;'>Hola "+user.getNombre()+","+"</p>\n"
                    + "            <p style='font-family: Arial, Helvetica, sans-serif;'>Has solicitado restablecer la contraseña de tu cuenta. Para continuar utilice el siguiente código de verificación:</p>\n"
                    + "            <p style='font-family: Arial, Helvetica, sans-serif;'>Código: <b>"+codgenerado+"</b></p>\n"
                    + "            <small style='font-family: Arial, Helvetica, sans-serif;'><b>Nota: </b>Si no solicitaste restablecer la contraseña, por favor ignorar este mensaje. </small>\n"
                    + "        </div>\n"
                    + "    </div>\n"
                    + "    <hr style='\n"
                    + "    margin-top: 1rem;\n"
                    + "    margin-bottom: 1rem;\n"
                    + "    border: 0;\n"
                    + "    border-top: 1px solid rgba(0,0,0,.1);'>\n"
                    + "    <div style='\n"
                    + "    width:500px;\n"
                    + "    background-color:#282828;\n"
                    + "    min-height: 115px;\n"
                    + "    border-radius: 0px 0px 4px 4px ;\n"
                    + "    box-shadow: 2px 0px 3px 0px rgba(0,0,0, 0.3);\n"
                    + "    position: relative;\n"
                    + "    min-height: 70px;\n"
                    + "    margin: 0 auto;\n"
                    + "    padding: 20px;\n"
                    + "    text-align: center;'>\n"
                    + "        <div>\n"
                    + "            <div style=\"color: white;font-family: Arial, Helvetica, sans-serif;\">\n"
                    + "                <small>\n"
                    + "                    <a href='#' style=\"color: white;font-family: Arial, Helvetica, sans-serif;\">\n"
                    + "                        Términos y condiciones\n"
                    + "                    </a>\n"
                    + "                    &nbsp;\n"
                    + "                    |\n"
                    + "                    &nbsp;\n"
                    + "                    <a href='#' style=\"color: white;font-family: Arial, Helvetica, sans-serif;\">\n"
                    + "                        Políticas de privacidad\n"
                    + "                    </a>\n"
                    + "                </small>\n"
                    + "                <br />\n"
                    + "                <br />\n"
                    + "                <div>\n"
                    + "                    <small>© 2020 Todos los derechos reservados.</small>\n"
                    + "                    <br>\n"
                    + "                    <a href='#'><img src='http://imgfz.com/i/HxYQDRd.png' /></a>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "    </div>"
            );

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
