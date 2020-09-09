/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
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

    public ImagenControlador() {
    }

    public void subirImagen() {
        try {
            InputStream in = img.getInputStream();
            /*
            Pongan su ruta local:
            Lucho: C:\\Users\\CM1049LA\\Documents\\NetBeansProjects\\BackEnd-and-FrontEnd\\web\\SI\\img\\
            */ 
            File f = new File("C:\\Users\\CM1049LA\\Documents\\NetBeansProjects\\BackEnd-and-FrontEnd\\web\\SI\\img\\" + img.getSubmittedFileName());
            f.createNewFile();
            FileOutputStream nf = new FileOutputStream(f);

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

}
