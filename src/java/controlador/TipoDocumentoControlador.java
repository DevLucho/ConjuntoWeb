/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.TipoDocumento;
import facade.TipoDocumentoFacade;
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
@Named(value = "tipoDocumentoControlador")
@SessionScoped
public class TipoDocumentoControlador implements Serializable {

    /**
     * Creates a new instance of TipoDocumentoControlador
     */
    
    private TipoDocumento tipoDocumento;
    
    @EJB
    TipoDocumentoFacade tipoDocumentoFacade;
    
    public TipoDocumentoControlador() {
    }
    
    @PostConstruct
    public void init(){
        tipoDocumento = new TipoDocumento();
    }
    
    public List<TipoDocumento> consultarTodos(){
        return tipoDocumentoFacade.findAll();
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    
}
