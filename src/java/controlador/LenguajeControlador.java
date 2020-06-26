/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

@Named(value = "lenguajeControlador")
@SessionScoped
public class LenguajeControlador implements Serializable {

    public LenguajeControlador(){ 
    }
    
    private Locale languageSelected;
    private Locale espaniol;
    private Locale ingles;

    public Locale getEspaniol() {
        return espaniol;
    }

    public void setEspaniol(Locale espaniol) {
        this.espaniol = espaniol;
    }

    public Locale getIngles() {
        return ingles;
    }

    public void setIngles(Locale ingles) {
        this.ingles = ingles;
    }

    @PostConstruct
    public void init(){
        espaniol=new Locale("es");
        ingles=new Locale("en");
        languageSelected=new Locale("es");
    }

    public Locale getLanguageSelected() {
        return languageSelected;
    }

    public void setLanguageSelected(Locale languageSelected) {
        this.languageSelected = languageSelected;
    }
    
    public String cambiarIdioma(String idiomas){
        Locale idioma = new Locale(idiomas);
        if (idioma != null){
            this.languageSelected = idioma;
            FacesContext.getCurrentInstance().getViewRoot().setLocale(languageSelected);
        }
        return "";
    }
    
}
