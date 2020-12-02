/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.inject.Inject;
import javax.servlet.http.Part;

/**
 *
 * @author Huertas
 */
@Named(value = "imagenControlador")
@SessionScoped
public class ImagenControlador implements Serializable {

    /**
     * Creates a new instance of ImagenControlador
     */
    private Part img;
    private File f;
    /*
    *Pongan su ruta local:
    *Lucho: C:\\Users\\CM1049LA\\Documents\\NetBeansProjects\\ConjuntoWeb\\web\\SI\\img\\
    AVENDAÑO: C:\\Users\\User\\Documents\\NetBeansProjects\\ProyectoConjuntoWeb\\web\\SI\\img\\
    Camilo: C:\\Users\\Familia\\Desktop\\Proyecto\\Completo(no git)\\BackEnd-and-FrontEnd\\web\\SI\\img\\
    private String ruta = "C:\\Users\\CM1049LA\\Documents\\NetBeansProjects\\ConjuntoWeb\\web\\SI\\img\\"; // Ruta img interna
    private String ruta2 = "C:\\Users\\CM1049LA\\Documents\\NetBeansProjects\\ConjuntoWeb\\web\\img\\"; // Ruta img externa
     */

    private String ruta = "" + ExportUControlador.get() + "\\SI\\img\\"; // Ruta img interna
    private String ruta2 = "" + ExportUControlador.get() + "\\img\\"; // Ruta img externa

    public ImagenControlador() {
    }

    public void subirImagen(int ruta) {
        try {
            InputStream in = img.getInputStream();

            if (ruta == 1) { // Imagenes internas
                this.f = new File(this.ruta + img.getSubmittedFileName());
            } else { // Imagenes para index
                this.f = new File(this.ruta2 + img.getSubmittedFileName());
            }
            this.f.createNewFile();
            FileOutputStream nf = new FileOutputStream(this.f);

            byte[] buffer = new byte[1024];
            int tamaño;

            while ((tamaño = in.read(buffer)) > 0) {
                nf.write(buffer, 0, tamaño);
            }

            nf.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public Part getImg() {
        return img;
    }

    public void setImg(Part img) {
        this.img = img;
    }

    public File getF() {
        return f;
    }

    public void setF(File f) {
        this.f = f;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta2() {
        return ruta2;
    }

    public void setRuta2(String ruta2) {
        this.ruta2 = ruta2;
    }

}
