/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.mysql.cj.jdbc.Driver;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.Part;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 *
 * @author User
 */
@ManagedBean
public class ExportUControlador {
     Part excel;
    @Inject
    private MensajeControlador mensaje;

    public Part getExcel() {
        return excel;
    }

    public void setExcel(Part excel) {
        this.excel = excel;
    }
    
    public void migrar() throws SQLException{
        try {
            Driver dvr = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(dvr);
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/conjuntoweb?user=root&password=&useSSL=false");
            
            Workbook libro = WorkbookFactory.create(excel.getInputStream());
            XSSFSheet hoja = (XSSFSheet) libro.getSheetAt(0);
           
            Iterator <Row> itr = hoja.rowIterator();
            itr.next();
            boolean crear;
            while(itr.hasNext()){
                crear = true;
                Row fila = itr.next();
                int ncamp = 1;
                String query = "INSERT INTO usuario VALUES(";
                Iterator<Cell> itrCelda = fila.cellIterator();
                
                while(itrCelda.hasNext()){
                    Cell celda = itrCelda.next();
                    
                    if(celda.getCellTypeEnum()== CellType.STRING){
                        if(ncamp == 3 || ncamp == 4 || ncamp == 7 || ncamp == 9 || ncamp == 10 || ncamp == 11){
                            query = query + ",'" + celda.getRichStringCellValue() + "'";
                        }
                        //System.out.println(" " + celda.getRichStringCellValue());
                    }else{
                        if(ncamp == 1){
                            query = query + (int)celda.getNumericCellValue();
                            if ((int) celda.getNumericCellValue() == 0) {
                                crear = false;
                            }
                        }
                        if (ncamp == 2) {
                            query = query + ", " + (int) celda.getNumericCellValue();
                        }
                        if (ncamp == 5) {
                            query = query + ", " + (int) celda.getNumericCellValue();
                        }
                        if (ncamp == 6) {
                            query = query + ", " + (int) celda.getNumericCellValue();
                        }
                        if (ncamp == 8) {
                            query = query + ", " + (long) celda.getNumericCellValue();
                        }
                        //System.out.println(" " + celda.getNumericCellValue());
                    }
                    ncamp++;
                }
                query = query + ");";
                //System.out.println("");
                //System.out.println(query);
                if (crear != false) {
                    PreparedStatement ps;
                    ps = con.prepareStatement(query);
                    ps.executeUpdate();
                }          
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Aviso","Migracion Exitosa"));
            mensaje.setMensaje("Mensaje('Importacion Exitosa','Datos registrados en la Bd','success');");
        }catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Aviso","Error abriendo archivo"));  
            mensaje.setMensaje("Mensaje('Error en Importacion','El archivo no se puede abrir, revisar la extension requerida','warning');");
        }catch (InvalidFormatException e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Aviso","Error en formato1"));  
            mensaje.setMensaje("Mensaje('Error en Importacion','El formato en el archivo es erroneo, por favor verificar las instrucciones.','error');");
        }catch (SQLException e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Aviso","Error en formato"));                
            mensaje.setMensaje("Mensaje('Error en Importacion','El formato en el archivo es erroneo, por favor verificar las instrucciones.','error');");
        }
    }
}
