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
public class ExportControlador {
    Part excel;

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
            /*
            Pongan sus conexiones locales:
            Avendaño: jdbc:mysql://localhost:3306/conjuntoweb?user=root&password=&useSSL=false
            Lucho: jdbc:mysql://localhost:3308/conjuntoweb?user=Huertas&password=1979&useSSL=false
            */
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3308/conjuntoweb?user=Huertas&password=1979&useSSL=false");
            
            Workbook libro = WorkbookFactory.create(excel.getInputStream());
            XSSFSheet hoja = (XSSFSheet) libro.getSheetAt(0);
           
            Iterator <Row> itr = hoja.rowIterator();
            itr.next();
            boolean crear;
            while(itr.hasNext()){
                crear = true;
                Row fila = itr.next();
                int ncamp = 1;
                String query = "INSERT INTO codigo VALUES(";
                Iterator<Cell> itrCelda = fila.cellIterator();
                
                while(itrCelda.hasNext()){
                    Cell celda = itrCelda.next();
                    
                    if(celda.getCellTypeEnum()== CellType.STRING){
                        if(ncamp == 2 || ncamp == 3){
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
        }catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Aviso","Error abriendo archivo"));        
        }catch (InvalidFormatException e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Aviso","Error en formato"));                
        }catch (SQLException e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Aviso","Error en formato"));                
        }
    }
}
