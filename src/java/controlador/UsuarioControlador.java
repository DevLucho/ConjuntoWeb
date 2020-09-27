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
    private CorreoControlador email;
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
        email = new CorreoControlador();
    }

    public void registrar() throws NoSuchProviderException, MessagingException {

        try {
            user = usuarioFacade.validarDocumento(nrodocumento = Integer.parseInt(nrodoc));
            useremail = usuarioFacade.validarEmail(correo);
            if (user.getDocumento() != 0 && useremail.getCorreo() != null) {
                mensaje.setMensaje("Mensajes('Error','El número de documento: " + this.nrodocumento + " y el correo: " + correo + " ya se encuentran registrados en el sistema.','error');");
            } else if (user.getDocumento() != 0) {
                mensaje.setMensaje("Mensajes('Error','El número de documento: " + this.nrodocumento + " ya se encuentra registrado en el sistema.','error');");
            } else if (useremail != null) {
                mensaje.setMensaje("Mensajes('Error','El correo electrónico: " + this.correo + " ya se encuentra registrado en el sistema.','error');");
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
                email.enviarEmail(correo, "Registro exitoso",
                        email.paginaCorreo("Bienvenido!",
                                "<p style='font-family: Arial, Helvetica, sans-serif;'><b>¡Hola, " + usuario.getNombre() + "!</b></p>\n"
                                + "<p style='font-family: Arial, Helvetica, sans-serif;'>Aquí tienes la información de tu <b>cuenta</b> para acceder al sistema de tu conjunto residencial:</p>\n"
                                + "<p style='font-family: Arial, Helvetica, sans-serif;'>Usuario: " + usuario.getDocumento() + "<br/>Contraseña: " + clavegenerada + "</p>",
                                "http://imgfz.com/i/7ocMf5s.jpeg")
                );

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
        } catch (NumberFormatException | NoSuchProviderException | MessagingException e) {
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
    
    public String obtenerUltimoDe(int idRol){
        List<Usuario> pepe = usuarioFacade.buscarUltimo(idRol);
        String nombresito = pepe.get(0).getNombre()+" "+pepe.get(0).getApellido();
        return nombresito;
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
    
}
