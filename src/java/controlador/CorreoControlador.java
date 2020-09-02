/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.security.NoSuchProviderException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Huertas
 */
@Named(value = "correoControlador")
@SessionScoped
public class CorreoControlador implements Serializable {

    /**
     * Creates a new instance of CorreoControlador
     */
    public CorreoControlador() {
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

    public String paginaCorreo(String asunto, String cuerpo, String img) {
        String pagina
                = "<div style='\n"
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
                + "            <img src='"+img+"' style='width: 100% ;background-attachment: fixed'>\n"
                + "        </div>\n"
                + "            <!-- Titulo y texto informativo -->\n"
                + "        <div>\n"
                + "            <h2 style='font-family: Arial, Helvetica, sans-serif;color:#33406A;font-weight: bold;'>\n"
                + "            "+asunto+"\n"
                + "            </h2>\n"
                + "            <hr>\n"
                + "            "+cuerpo+"\n"
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
                + "    </div>";

        return pagina;
    }

}
