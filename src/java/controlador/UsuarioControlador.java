/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Apartamento;
import entidades.Inmueble;
import entidades.Residente;
import entidades.Rol;
import entidades.TipoDocumento;
import entidades.Torre;
import entidades.TurnoVigilante;
import entidades.Usuario;
import entidades.Vigilante;
import facade.ApartamentoFacade;
import facade.InmuebleFacade;
import facade.ResidenteFacade;
import facade.RolFacade;
import facade.TipoDocumentoFacade;
import facade.TorreFacade;
import facade.TurnoVigilanteFacade;
import facade.UsuarioFacade;
import facade.VigilanteFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author HUERTAS
 */
@Named(value = "usuarioControlador")
@SessionScoped
public class UsuarioControlador implements Serializable {

    /**
     * Creates a new instance of UsuarioControlador
     */
    @Inject
    private LenguajeControlador lenguaje; // internacionalizacion
    @Inject
    private MensajeControlador mensaje;
    private Usuario usuario;
    private Usuario user = null;
    private Usuario useremail = null;
    private TipoDocumento tipoDocumento;
    private Rol rol;
    private Residente residente;
    private Vigilante vigilante;
    private TurnoVigilante turnoVigilante;
    private Torre torre;
    private Apartamento apartamento;
    private Inmueble inmueble;
    private CodigoControlador codigoControlador;
    // campos form
    private String nrodoc;
    private int nrodocumento;
    private String nrocel;
    private long nrocelular;
    private String correo;
    // Generar contraseña aleatoria
    Random rnd = new Random();
    String abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String clavegenerada = "";
    int pos = 0, num;

    @EJB
    UsuarioFacade usuarioFacade;

    @EJB
    ResidenteFacade residenteFacade;

    @EJB
    TorreFacade torreFacade;

    @EJB
    ApartamentoFacade apartamentoFacade;

    @EJB
    InmuebleFacade inmuebleFacade;

    @EJB
    VigilanteFacade vigilanteFacade;

    @EJB
    TurnoVigilanteFacade turnoVigilanteFacade;

    @EJB
    RolFacade rolFacade;

    @EJB
    TipoDocumentoFacade tipoDocumentoFacade;

    public UsuarioControlador() {
    }

    @PostConstruct
    public void init() {
        rol = new Rol();
        tipoDocumento = new TipoDocumento();
        residente = new Residente();
        vigilante = new Vigilante();
        turnoVigilante = new TurnoVigilante();
        inmueble = new Inmueble();
        apartamento = new Apartamento();
        torre = new Torre();
        usuario = new Usuario();
        codigoControlador = new CodigoControlador();
    }

    public void registrar() throws NoSuchProviderException, MessagingException {

        try {
            user = usuarioFacade.validarDocumento(nrodocumento = Integer.parseInt(nrodoc));
            useremail = usuarioFacade.validarEmail(correo);
            if (user.getDocumento() != 0 && useremail.getCorreo() != null) {
                mensaje.setMensaje("Mensajes('Error','El número de documento: <b>" + nrodocumento + "</b> y el correo: <b>" + correo + "</b> ya se encuentran registrados en el sistema.','error');");
            } else if (user.getDocumento() != 0) {
                mensaje.setMensaje("Mensajes('Error','El número de documento: " + nrodocumento + " ya se encuentra registrado en el sistema.','error');");
            } else if (useremail.getCorreo() != null) {
                mensaje.setMensaje("Mensajes('Error','El correo electrónico: " + correo + " ya se encuentra registrado en el sistema.','error');");
            } else {
                usuario.setIdRol(rolFacade.find(rol.getIdRol()));
                usuario.setTipoDocumento(tipoDocumentoFacade.find(tipoDocumento.getId()));
                usuario.setCelular(nrocelular = Long.parseLong(nrocel));
                usuario.setDocumento(nrodocumento = Integer.parseInt(nrodoc));
                usuario.setEstado("Activo");
                // generar contraseña
                pos = (int) (rnd.nextDouble() * abecedario.length() - 1 + 0);
                num = (int) (rnd.nextDouble() * 9999 + 1000);
                clavegenerada = clavegenerada + abecedario.charAt(pos) + num + abecedario.charAt(pos + 1) + abecedario.charAt(pos - 1) + abecedario.charAt(pos); //Estructura codigo 8 caracteres
                usuario.setContrasenia(clavegenerada);
                usuario.setCorreo(correo);
                usuarioFacade.create(usuario);
                // if is residente
                if (rol.getIdRol() == 2) {
                    residente.setIdPerfil(usuario);
                    inmueble.setIdTorre(torreFacade.find(torre.getIdTorre()));
                    inmueble.setIdApartamento(apartamentoFacade.find(apartamento.getIdApartamento()));
                    inmuebleFacade.create(inmueble);
                    residente.setIdInmueble(inmueble);
                    residenteFacade.create(residente);
                    torre = new Torre();
                    apartamento = new Apartamento();
                    residente = new Residente();
                } // if is vigilante
                else if (rol.getIdRol() == 3) {
                    vigilante.setIdPerfil(usuario);
                    turnoVigilanteFacade.create(turnoVigilante);
                    vigilante.setIdTurno(turnoVigilante);
                    vigilanteFacade.create(vigilante);
                    vigilante = new Vigilante();
                    turnoVigilante = new TurnoVigilante();
                }

                // enviar email con usuario y contraseña          
                codigoControlador.enviarEmail(correo, "Registro exitoso",
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
                        + "            <img src='http://imgfz.com/i/7ocMf5s.jpeg' style='width: 100% ;background-attachment: fixed'>\n"
                        + "        </div>\n"
                        + "            <!-- Titulo y texto informativo -->\n"
                        + "        <div>\n"
                        + "            <h2 style='font-family: Arial, Helvetica, sans-serif;color:#33406A;font-weight: bold;'>\n"
                        + "                Bienvenido!\n"
                        + "            </h2>\n"
                        + "            <hr>\n"
                        + "            <p style='font-family: Arial, Helvetica, sans-serif;'><b>¡Hola, " + usuario.getNombre() +"!</b></p>\n"
                        + "            <p style='font-family: Arial, Helvetica, sans-serif;'>Aquí tienes la información de tu <b>cuenta</b> para acceder al sistema de tu conjunto residencial:</p>\n"
                        + "            <p style='font-family: Arial, Helvetica, sans-serif;'>Usuario: "+usuario.getDocumento()+"<br/>Contraseña: "+clavegenerada+"</p>\n"
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
                        + "    </div>");

                usuario = new Usuario();
                rol = new Rol();
                tipoDocumento = new TipoDocumento();
                nrodoc = "";
                clavegenerada = "";
                nrocel = "";
                correo = "";
                if ("es".equals(lenguaje.getLenguaje())) {
                    mensaje.setMensaje("MensajeAlertify('Usuario creado satisfactoriamente','success');");
                } else {
                    mensaje.setMensaje("MensajeAlertify('user created successfully','success');");
                }
            }
        } catch (Exception e) {
            System.out.println("Error registro de usuario: " + e.getMessage());
        }
    }

    public void cancelar(Usuario usuarioCancelar) {
        if (usuarioCancelar.getIdRol().getIdRol() == 1) {
            mensaje.setMensaje("Mensajes('Error!','No puedes bloquear usuarios con rol Administrador','error');");
        } else if ("Bloqueado".equals(usuarioCancelar.getEstado())) {
            mensaje.setMensaje("Mensajes('Advertencia!','El usuario " + usuarioCancelar.getNombre() + " " + usuarioCancelar.getApellido() + " ya esta bloqueado','warning');");
        } else {
            mensaje.setMensaje("Confirmar('Estas seguro que deseas bloquear el usuario " + usuarioCancelar.getNombre() + " " + usuarioCancelar.getApellido() + "?','Podras revertilo!','warning','Si, bloquear!','Bloqueado!','Se ha bloqueado exitosamente el usuario.','success');");
            usuario = usuarioCancelar;
            usuario.setEstado("Bloqueado");
            usuarioFacade.edit(usuarioCancelar);
        }
    }

    public void desbloquear(Usuario usuarioDesbloquear) {
        usuario = usuarioDesbloquear;
        usuario.setEstado("Activo");
        usuarioFacade.edit(usuarioDesbloquear);
        mensaje.setMensaje("Mensaje('Desbloqueado!','Se ha desbloqueado satisfactoriamente el usuario " + usuarioDesbloquear.getNombre() + " " + usuarioDesbloquear.getApellido() + "','success');");
    }

    public String preActualizar(Usuario usuarioActualizar) {
        rol = usuarioActualizar.getIdRol();
        tipoDocumento = usuarioActualizar.getTipoDocumento();
        usuario = usuarioActualizar;
        return "editar-usuario";
    }

    public String preActualizarR(Residente residenteActualizar) {
        rol = residenteActualizar.getIdPerfil().getIdRol();
        tipoDocumento = residenteActualizar.getIdPerfil().getTipoDocumento();
        torre = residenteActualizar.getIdInmueble().getIdTorre();
        usuario = residenteActualizar.getIdPerfil();
        apartamento = residenteActualizar.getIdInmueble().getIdApartamento();
        residente = residenteActualizar;
        return "editar-usuario";
    }

    public String preActualizarV(Vigilante vigilanteActualizar) {
        rol = vigilanteActualizar.getIdPerfil().getIdRol();
        tipoDocumento = vigilanteActualizar.getIdPerfil().getTipoDocumento();
        usuario = vigilanteActualizar.getIdPerfil();
        turnoVigilante = vigilanteActualizar.getIdTurno();
        vigilante = vigilanteActualizar;
        return "editar-usuario";
    }

    public void actualizar() {
        usuario.setIdRol(rolFacade.find(rol.getIdRol()));
        usuario.setTipoDocumento(tipoDocumentoFacade.find(tipoDocumento.getId()));
        usuarioFacade.edit(usuario);
        if (rol.getIdRol() == 2) {
            residente.getIdInmueble().setIdTorre(torreFacade.find(torre.getIdTorre()));
            residente.getIdInmueble().setIdApartamento(apartamentoFacade.find(apartamento.getIdApartamento()));
            inmuebleFacade.edit(residente.getIdInmueble());
            residente.setIdPerfil(usuarioFacade.find(usuario.getIdPerfil()));
            residenteFacade.edit(residente);
        }
        if (rol.getIdRol() == 3) {
            vigilante.setIdTurno(turnoVigilanteFacade.find(turnoVigilante.getIdTurno()));
            turnoVigilanteFacade.edit(turnoVigilante);
            vigilante.setIdPerfil(usuarioFacade.find(usuario.getIdPerfil()));
            vigilanteFacade.edit(vigilante);
        }
        mensaje.setMensaje("Mensajes('Exito!','Usuario modificado satisfactoriamente','success');");
    }

    public List<Usuario> consultarTodos() {
        return usuarioFacade.findAll();
    }

    public List<Usuario> consultarBloqueados() {
        return usuarioFacade.usuarioBloquedo("Bloqueado");
    }

    public List<Usuario> consultarAdmin() {
        return usuarioFacade.usuarioAdmin(1);
    }

    public String consultarUsuario(Usuario usuarioConsultar) {
        rol = usuarioConsultar.getIdRol();
        tipoDocumento = usuarioConsultar.getTipoDocumento();
        usuario = usuarioConsultar;
        return "detalle-usuario";
    }

    public String consultarResidente(Residente residenteConsultar) {
        rol = residenteConsultar.getIdPerfil().getIdRol();
        tipoDocumento = residenteConsultar.getIdPerfil().getTipoDocumento();
        usuario = residenteConsultar.getIdPerfil();
        torre = residenteConsultar.getIdInmueble().getIdTorre();
        apartamento = residenteConsultar.getIdInmueble().getIdApartamento();
        residente = residenteConsultar;
        return "detalle-residente";
    }

    public String consultarVigilante(Vigilante vigilanteConsultar) {
        rol = vigilanteConsultar.getIdPerfil().getIdRol();
        tipoDocumento = vigilanteConsultar.getIdPerfil().getTipoDocumento();
        usuario = vigilanteConsultar.getIdPerfil();
        turnoVigilante = vigilanteConsultar.getIdTurno();
        vigilante = vigilanteConsultar;
        return "detalle-usuario";
    }

    public String asignarVehiculoR(Residente residenteConsultar) {
        rol = residenteConsultar.getIdPerfil().getIdRol();
        tipoDocumento = residenteConsultar.getIdPerfil().getTipoDocumento();
        usuario = residenteConsultar.getIdPerfil();
        torre = residenteConsultar.getIdInmueble().getIdTorre();
        apartamento = residenteConsultar.getIdInmueble().getIdApartamento();
        residente = residenteConsultar;
        return "agregar-vehiculo";
    }

    public List<Usuario> sesionUsuario(int idPerfil) {
        return usuarioFacade.sesionUsuario(idPerfil);
    }

    public List<Residente> sesionUsuarioR(int idPerfil) {
        return usuarioFacade.sesionUsuarioR(idPerfil);
    }

    public List<Vigilante> sesionUsuarioV(int idPerfil) {
        return usuarioFacade.sesionUsuarioV(idPerfil);
    }

    // Metodos count ↓
    public int contarUsuarios() {
        return usuarioFacade.count();
    }

    public int contarAdministradores() {
        return usuarioFacade.contarAdministradores(1);
    }

    public int contarBloqueados() {
        return usuarioFacade.contarBloqueados("Bloqueado");
    }

    // Get's y Set's ↓
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Residente getResidente() {
        return residente;
    }

    public void setResidente(Residente residente) {
        this.residente = residente;
    }

    public Vigilante getVigilante() {
        return vigilante;
    }

    public void setVigilante(Vigilante vigilante) {
        this.vigilante = vigilante;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public Torre getTorre() {
        return torre;
    }

    public void setTorre(Torre torre) {
        this.torre = torre;
    }

    public Apartamento getApartamento() {
        return apartamento;
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }

    public TurnoVigilante getTurnoVigilante() {
        return turnoVigilante;
    }

    public void setTurnoVigilante(TurnoVigilante turnoVigilante) {
        this.turnoVigilante = turnoVigilante;
    }

    public MensajeControlador getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajeControlador mensaje) {
        this.mensaje = mensaje;
    }

    public String getNrodoc() {
        return nrodoc;
    }

    public void setNrodoc(String nrodoc) {
        this.nrodoc = nrodoc;
    }

    public String getNrocel() {
        return nrocel;
    }

    public void setNrocel(String nrocel) {
        this.nrocel = nrocel;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public CodigoControlador getCodigoControlador() {
        return codigoControlador;
    }

    public void setCodigoControlador(CodigoControlador codigoControlador) {
        this.codigoControlador = codigoControlador;
    }

}
