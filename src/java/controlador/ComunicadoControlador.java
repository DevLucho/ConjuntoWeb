/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Comunicado;
import entidades.Usuario;
import facade.ComunicadoFacade;
import facade.UsuarioFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author Huertas
 */
@Named(value = "comunicadoControlador")
@SessionScoped
public class ComunicadoControlador implements Serializable {

    /**
     * Creates a new instance of ComunicadoControlador
     */
    
    private Comunicado comunicado;
    
    private Usuario usuario;
    
    @EJB
    ComunicadoFacade comunicadoFacade;
    
    @EJB
    UsuarioFacade usuarioFacade;
    
    public ComunicadoControlador() {
    }
    
    @PostConstruct
    public void init(){
        usuario = new Usuario();
        comunicado = new Comunicado();
    }
    
    public void registrar(){
        usuario.setIdPerfil(1);
        comunicado.setIdPerfil(usuario);
        comunicadoFacade.create(comunicado);
        usuario = new Usuario();
        comunicado = new Comunicado();
    }
    
    public List<Comunicado> consultarTodos(){
        return comunicadoFacade.findAll();
    }
    
    public String preActualizar(Comunicado comunicadoActualizar){
        usuario = comunicadoActualizar.getIdPerfil();
        comunicado = comunicadoActualizar;
        return "";
    }
    
    public void actualizar(){
        comunicado.setIdPerfil(usuarioFacade.find(usuario.getIdPerfil()));
        comunicadoFacade.edit(comunicado);
    }
    
    public void eliminar(Comunicado comunicadoEliminar){
        comunicadoFacade.remove(comunicadoEliminar);
    }

    public Comunicado getComunicado() {
        return comunicado;
    }

    public void setComunicado(Comunicado comunicado) {
        this.comunicado = comunicado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
