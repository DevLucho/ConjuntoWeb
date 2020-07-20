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
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

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
    private MensajeControlador mensaje;
    private Usuario usuario;
    private TipoDocumento tipoDocumento;
    private Rol rol;
    private Residente residente;
    private Vigilante vigilante;
    private TurnoVigilante turnoVigilante;
    private Torre torre;
    private Apartamento apartamento;
    private Inmueble inmueble;

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

    // Incremental para menu dinamico
    /*private int valor = 0;

    public void incrementar() {
        valor++;
    }

    public int getValor() {
        return valor;
    }*/
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
    }

    public void registrar() {
        usuario.setIdRol(rolFacade.find(rol.getIdRol()));
        usuario.setTipoDocumento(tipoDocumentoFacade.find(tipoDocumento.getId()));
        usuario.setEstado("Activo");
        usuarioFacade.create(usuario);
        if (rol.getIdRol() == 2) {
            residente.setIdPerfil(usuario);
            inmueble.setIdTorre(torreFacade.find(torre.getIdTorre()));
            inmueble.setIdApartamento(apartamentoFacade.find(apartamento.getIdApartamento()));
            inmuebleFacade.create(inmueble);
            residente.setIdInmueble(inmueble);
            residenteFacade.create(residente);
            usuario = new Usuario();
            rol = new Rol();
            tipoDocumento = new TipoDocumento();
            torre = new Torre();
            apartamento = new Apartamento();
            residente = new Residente();
            mensaje.setMensaje("Mensajes('Exito!','Usuario creado satisfactoriamente','success');");
        } else if (rol.getIdRol() == 3) {
            vigilante.setIdPerfil(usuario);
            turnoVigilanteFacade.create(turnoVigilante);
            vigilante.setIdTurno(turnoVigilante);
            vigilanteFacade.create(vigilante);
            usuario = new Usuario();
            rol = new Rol();
            tipoDocumento = new TipoDocumento();
            vigilante = new Vigilante();
            turnoVigilante = new TurnoVigilante();
            mensaje.setMensaje("Mensajes('Exito!','Usuario creado satisfactoriamente','success');");
        }
        usuario = new Usuario();
        rol = new Rol();
        tipoDocumento = new TipoDocumento();
        mensaje.setMensaje("Mensajes('Exito!','Usuario creado satisfactoriamente','success');");
    }

    public void cancelar(Usuario usuarioCancelar) {
        if (usuarioCancelar.getIdRol().getIdRol() == 1) {
            mensaje.setMensaje("Mensajes('Error!','No puedes bloquear usuarios con rol Administrador','error');");
        } else if ("Bloqueado".equals(usuarioCancelar.getEstado())) {
            mensaje.setMensaje("Mensajes('Advertencia!','Este usuario ya esta bloqueado','warning');");
        } else {
            mensaje.setMensaje("Confirmar('Estas seguro que deseas bloquear este usuario?','Podras revertilo!','warning','Si, bloquear!','Bloqueado!','Se ha bloqueado exitosamente el usuario.','success');");
            usuario = usuarioCancelar;
            usuario.setEstado("Bloqueado");
            usuarioFacade.edit(usuarioCancelar);
        }
    }

    public void desbloquear(Usuario usuarioDesbloquear) {
        usuario = usuarioDesbloquear;
        usuario.setEstado("Activo");
        usuarioFacade.edit(usuarioDesbloquear);
        mensaje.setMensaje("Mensaje('Desbloqueado!','Se ha desbloqueado satisfactoriamente el usuario','success');");
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
            residente.setIdPerfil(usuarioFacade.find(usuario.getIdPerfil()));
            residenteFacade.edit(residente);
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

    public int contarUsuarios() {
        return usuarioFacade.count();
    }

    public int contarAdministradores() {
        return usuarioFacade.contarAdministradores(1);
    }

    public int contarBloqueados() {
        return usuarioFacade.contarBloqueados("Bloqueado");
    }

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

}
