/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Permiso;
import entidades.Rol;
import entidades.RolPermiso;
import entidades.Usuario;
import facade.PermisoFacade;
import facade.RolFacade;
import facade.UsuarioFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;

/**
 *
 * @author Huertas
 */
@Named(value = "sesionControlador")
@SessionScoped
public class SesionControlador implements Serializable {

    /**
     * Creates a new instance of SesionControlador
     */
    private int documento;
    private String contrasenia;
    private Rol rolSeleccionado;
    private Usuario usuario;

    @EJB
    UsuarioFacade usuarioFacade;

    @EJB
    RolFacade rolFacade;

    @EJB
    PermisoFacade permisoFacade;

    public SesionControlador() {
        FacesContext fc = FacesContext.getCurrentInstance();
        rolFacade = new RolFacade();
        usuarioFacade = new UsuarioFacade();
    }

    public String iniciarSesion() {
        Usuario user;
        user = usuarioFacade.iniciarSesion(documento, contrasenia);
        if (user.getDocumento() != 0) {
            rolSeleccionado = user.getIdRol();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogueado", user);
            if (rolSeleccionado.getIdRol() == 1) {
                return "/SI/vista/pef-usuario/administrador/inicio-admin?faces-redirect=true";
            }
            if (rolSeleccionado.getIdRol() == 2) {
                return "/SI/vista/pef-usuario/residente/inicio_residente?faces-redirect=true";
            }
            return "/SI/vista/pef-usuario/vigilante/inicioSeguridad?faces-redirect=true";
        } else {
            return "login?faces-redirect=true";
        }
    }

    public String cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        this.usuario = null;
        this.documento = 0;
        this.contrasenia = "";
        return "/index.xhtml?faces-redirect=true";
    }

    public Boolean validarPermiso() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String urlRecurso = fc.getExternalContext().getRequestPathInfo();
        for (RolPermiso p : rolSeleccionado.getRolPermisoList()) {
            if (p.getIdPermiso().getUrl() != null && (p.getIdPermiso().getUrl().equals(urlRecurso)
                    || p.getIdPermiso().getUrl().equals("index.xhtml"))) {
                return true;
            }
        }
        return false;
    }

    public List<Permiso> hijos(int id) {
        return permisoFacade.consultarHijos(id);
    }

    public Rol getRolSeleccionado() {
        return rolSeleccionado;
    }

    public void setRolSeleccionado(Rol rolSeleccionado) {
        System.out.println("Rol - " + rolSeleccionado.getNombre());
        this.rolSeleccionado = rolSeleccionado;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getDocumento() {
        return documento;
    }

}
