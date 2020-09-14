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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

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
    @Inject
    HoraControlador hora;
    @Inject
    ComunicadoControlador comunicado;
    @Inject
    private MensajeControlador mensaje;
    private String documentoc;
    private int documento;
    private String contrasenia;
    private Rol rolSeleccionado;
    private Usuario usuario; // logout
    private Usuario user = null; // login

    @EJB
    UsuarioFacade usuarioFacade;

    @EJB
    RolFacade rolFacade;

    @EJB
    PermisoFacade permisoFacade;

    public SesionControlador() {
        rolFacade = new RolFacade();
        usuarioFacade = new UsuarioFacade();
    }

    public String iniciarSesion() {
        try {
            user = usuarioFacade.iniciarSesion(documento = Integer.parseInt(documentoc), contrasenia);
        } catch (NumberFormatException e) {
            System.out.println("Revisar login: " + e.getMessage());
        }
        if (user != null) {
            if (user.getDocumento() != 0 && user.getDocumento() > 0 && contrasenia != null && !contrasenia.equals("")) {
                rolSeleccionado = user.getIdRol();
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogueado", user);
                if ("Activo".equals(user.getEstado())) {
                    comunicado.eliminarExpirados(); // Elimina comunicados expirados
                    if (rolSeleccionado.getIdRol() == 1) {
                        return "/SI/vista/pef-usuario/administrador/inicio-admin?faces-redirect=true";
                    }
                    if (rolSeleccionado.getIdRol() == 2) {
                        return "/SI/vista/pef-usuario/residente/inicio_residente?faces-redirect=true";
                    }
                    return "/SI/vista/pef-usuario/vigilante/inicioSeguridad?faces-redirect=true";
                } else {
                    mensaje.setMensaje("MensajeAlertify('Usuario bloqueado. Contacte al administrador para solucionar el inconveniente.','error');");
                    return "";
                }

            } else {
                mensaje.setMensaje("MensajeAlertify('Documento y/o clave inválidos','error');");
                return "";
            }
        } else {
            mensaje.setMensaje("MensajeAlertify('Documento y/o clave inválidos','error');");
            return "";
        }
    }

    public String cerrarSesion() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            this.usuario = null;
            this.documento = 0;
            this.contrasenia = "";
            //mensaje.setMensaje("MensajeAlertify('Logout exitoso','success');");
            //Thread.sleep(5 * 1000);
        } catch (Exception e) {
            System.out.println("Error en logout revisar: " + e.getMessage());
        }
        return "/index.xhtml?faces-redirect=true";
    }

    public void verifSesion(String ruta) {
        try {
            Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogueado");
            if (us == null) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("" + ruta + "not-found/mensaje.xhtml");
            }
        } catch (Exception e) {
            System.out.println("Error verifS" + e.getMessage());
        }
    }

    public Boolean validarPermiso() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String urlRecurso = fc.getExternalContext().getRequestPathInfo();
        for (RolPermiso p : rolSeleccionado.getRolPermisoList()) {
            if (p.getIdPermiso().getUrl() != null && (p.getIdPermiso().getUrl().equals(urlRecurso) || p.getIdPermiso().getUrl().equals("index.xhtml"))) {
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

    public int getDocumento() {
        return documento;
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

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String getDocumentoc() {
        return documentoc;
    }

    public void setDocumentoc(String documentoc) {
        this.documentoc = documentoc;
    }

}
