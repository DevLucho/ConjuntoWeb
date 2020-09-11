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
import entidades.Usuario;
import facade.ApartamentoFacade;
import facade.InmuebleFacade;
import facade.ResidenteFacade;
import facade.RolFacade;
import facade.TipoDocumentoFacade;
import facade.TorreFacade;
import facade.UsuarioFacade;
import facade.VigilanteFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author HUERTAS
 */
@Named(value = "residenteControlador")
@SessionScoped
public class ResidenteControlador implements Serializable {

    /**
     * Creates a new instance of ResidenteControlador
     */
    private Usuario usuario;
    private Usuario user = null;
    private Usuario useremail = null;
    private TipoDocumento tipoDocumento;
    private Rol rol;
    private Residente residente;
    private Torre torre;
    private Apartamento apartamento;
    private Inmueble inmueble;
    // validar campos
    private String documentoc;
    private int documento;
    private String nrocel;
    private long nrocelular;
    private String correo;
    private String clave;

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
    RolFacade rolFacade;

    @EJB
    TipoDocumentoFacade tipoDocumentoFacade;

    @Inject
    private MensajeControlador mensaje;

    public ResidenteControlador() {
    }

    @PostConstruct
    public void init() {
        usuario = new Usuario();
        rol = new Rol();
        tipoDocumento = new TipoDocumento();
        residente = new Residente();
        inmueble = new Inmueble();
        apartamento = new Apartamento();
        torre = new Torre();
    }

    public void registrar() {
        user = usuarioFacade.validarDocumento(documento = Integer.parseInt(documentoc));
        useremail = usuarioFacade.validarEmail(correo);
        if (user.getDocumento() != 0) {
            mensaje.setMensaje("Mensajes('Error','El número de documento: " + this.documento + " ya se encuentra registrado en el sistema.','error');");
        } else if (useremail != null) {
            mensaje.setMensaje("Mensajes('Error','El correo electrónico: " + this.correo + " ya se encuentra registrado en el sistema.','error');");
        } else {
            usuario.setIdRol(rolFacade.find(2));
            usuario.setTipoDocumento(tipoDocumentoFacade.find(tipoDocumento.getId()));
            usuario.setCelular(nrocelular = Long.parseLong(nrocel));
            usuario.setDocumento(documento = Integer.parseInt(documentoc));
            usuario.setCorreo(correo);
            usuario.setEstado("Activo");
            usuarioFacade.create(usuario);
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
            documentoc = "";
            nrocel = "";
            mensaje.setMensaje("ConfirmacionResidente('../login/login.xhtml','success','Registro exitoso','Iniciar sesi&oacute;n');");
        }
    }

    // validaciones

    public void validarClave(FacesContext context, UIComponent comp, Object value) {
        context = FacesContext.getCurrentInstance();
        this.clave = (String) value;

        if (this.clave.length() < 8 || this.clave.length() > 20) {
            ((UIInput) comp).setValid(false);
            context.addMessage(comp.getClientId(context), new FacesMessage("Contraseña invalida"));
            mensaje.setMensaje("MensajeAlertify('Contraseña invalida','error');");
        }
    }

    public void confirmarClave(FacesContext context, UIComponent comp, Object value) {
        context = FacesContext.getCurrentInstance();
        String confirm = (String) value;

        if (confirm == null ? this.clave != null : !confirm.equals(this.clave)) {
            ((UIInput) comp).setValid(false);
            context.addMessage(comp.getClientId(context), new FacesMessage("Contraseña no coincide"));
            mensaje.setMensaje("MensajeAlertify('Contraseñas no coinciden','error');");
        }
    }

    public void validarDoc(FacesContext context, UIComponent comp, Object value) {
        context = FacesContext.getCurrentInstance();
        String doc = (String) value;

        if (doc.length() <8 || doc.length()>10) {
            ((UIInput) comp).setValid(false);
            context.addMessage(comp.getClientId(context), new FacesMessage("Documento incorrecto"));
            mensaje.setMensaje("MensajeAlertify('Nº de documento incorrecto','error');");
        }
    }

    public List<Residente> consultarTodos() {
        return residenteFacade.findAll();
    }

    public int contarResidentes() {
        return residenteFacade.count();
    }
    
    // Get's y Set's ↓

    public Residente getResidente() {
        return residente;
    }

    public void setResidente(Residente residente) {
        this.residente = residente;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public MensajeControlador getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajeControlador mensaje) {
        this.mensaje = mensaje;
    }

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
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

    public String getNrocel() {
        return nrocel;
    }

    public void setNrocel(String nrocel) {
        this.nrocel = nrocel;
    }

    public long getNrocelular() {
        return nrocelular;
    }

    public void setNrocelular(long nrocelular) {
        this.nrocelular = nrocelular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

}
