/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

/**
 *
 * @author Sebastian
 */
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
@FacesValidator("valnu")
public class validacionVisitante implements Validator{
    @Override
    public void validate(FacesContext context, UIComponent comp, Object value){
        String texto = (String)value;
        
        if(texto.length() < 4 || texto.length() > 10){
            FacesMessage msg = new FacesMessage("tamaño no valido");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
