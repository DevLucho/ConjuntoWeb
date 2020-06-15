/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Rol;
import entidades.TipoDocumento;
import entidades.TurnoVigilante;
import entidades.Usuario;
import entidades.Vigilante;
import facade.RolFacade;
import facade.TipoDocumentoFacade;
import facade.TurnoVigilanteFacade;
import facade.UsuarioFacade;
import facade.VigilanteFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author HUERTAS
 */
@Named(value = "vigilanteControlador")
@SessionScoped
public class VigilanteControlador implements Serializable {

    /**
     * Creates a new instance of VigilanteControlador
     */
    private Usuario usuario;
    private TipoDocumento tipoDocumento;
    private Rol rol;
    private Vigilante vigilante;
    private TurnoVigilante turnoVigilante;

    @EJB
    UsuarioFacade usuarioFacade;

    @EJB
    TipoDocumentoFacade tipoDocumentoFacade;

    @EJB
    RolFacade rolFacade;

    @EJB
    VigilanteFacade vigilanteFacade;

    @EJB
    TurnoVigilanteFacade turnoVigilanteFacade;

    public VigilanteControlador() {
    }

    @PostConstruct
    public void init() {
        usuario = new Usuario();
        rol = new Rol();
        tipoDocumento = new TipoDocumento();
        vigilante = new Vigilante();
        turnoVigilante = new TurnoVigilante();
    }

    public void registrar() {
        usuario.setIdRol(rolFacade.find(3));
        usuario.setTipoDocumento(tipoDocumentoFacade.find(tipoDocumento.getId()));
        usuarioFacade.create(usuario);
        vigilante.setIdPerfil(usuario);
        turnoVigilanteFacade.create(turnoVigilante);
        vigilante.setIdTurno(turnoVigilante);
        vigilanteFacade.create(vigilante);
        usuario = new Usuario();
        rol = new Rol();
        tipoDocumento = new TipoDocumento();
        vigilante = new Vigilante();
        turnoVigilante = new TurnoVigilante();
    }

    public List<Vigilante> consultarTodos() {
        return vigilanteFacade.findAll();
    }

    public int contarVigilantes() {
        return vigilanteFacade.count();
    }

    public Vigilante getVigilante() {
        return vigilante;
    }

    public void setVigilante(Vigilante vigilante) {
        this.vigilante = vigilante;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public TurnoVigilante getTurnoVigilante() {
        return turnoVigilante;
    }

    public void setTurnoVigilante(TurnoVigilante turnoVigilante) {
        this.turnoVigilante = turnoVigilante;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

}
