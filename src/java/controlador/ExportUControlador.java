/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Inmueble;
import entidades.Residente;
import entidades.Rol;
import entidades.TipoDocumento;
import entidades.Usuario;
import facade.ApartamentoFacade;
import facade.InmuebleFacade;
import facade.ResidenteFacade;
import facade.RolFacade;
import facade.TipoDocumentoFacade;
import facade.TorreFacade;
import facade.UsuarioFacade;
import java.io.IOException;
import java.security.NoSuchProviderException;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.mail.MessagingException;
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

    // ========== dependencias
    @Inject
    UsuarioControlador usuarioControlador;
    @Inject
    private MensajeControlador mensaje;
    @Inject
    CorreoControlador email;

    // ========== entidades
    Usuario user = null; // valida doc
    Usuario useremail = null; // valida email
    Usuario usuario;
    Residente residente;
    Inmueble inmueble;
    Rol rol;
    TipoDocumento tipo;

    // ========== campos excel
    String nombre;
    String apellido;
    String tipoDoc;
    int documento;
    String direccion;
    long celular;
    String correo;
    int torre;
    int apto;
    String automovil;

    // ========== excel a importar
    Part excel;

    // ========== contadores
    int cantDuplicados;
    int cantImportados;
    int cantVacios;

    // ========== EJB
    @EJB
    UsuarioFacade usuarioFacade;
    @EJB
    RolFacade rolFacade;
    @EJB
    TipoDocumentoFacade tipoFacade;
    @EJB
    TorreFacade torreFacade;
    @EJB
    ApartamentoFacade aptoFacade;
    @EJB
    InmuebleFacade inmuebleFacade;
    @EJB
    ResidenteFacade residenteFacade;

    public ExportUControlador() {
    }

    @PostConstruct
    public void init() {
        usuario = new Usuario();
        rol = new Rol();
        tipo = new TipoDocumento();
        residente = new Residente();
        inmueble = new Inmueble();
    }

    public String redirect() {
        return "importar";
    }

    public void migrar() throws NoSuchProviderException, MessagingException {
        try {
            cantDuplicados = 0;
            cantImportados = 0;
            cantVacios = 0;
            Workbook libro = WorkbookFactory.create(excel.getInputStream());
            XSSFSheet hoja = (XSSFSheet) libro.getSheetAt(0);

            Iterator<Row> itr = hoja.rowIterator();
            itr.next();
            boolean crear = true;
            while (itr.hasNext()) {
                Row fila = itr.next();
                int ncamp = 1;
                Iterator<Cell> itrCelda = fila.cellIterator();

                while (itrCelda.hasNext()) {
                    Cell celda = itrCelda.next();

                    if (celda.getCellTypeEnum() == CellType.STRING) {
                        if (ncamp == 1) {
                            nombre = celda.getRichStringCellValue().getString();
                        }
                        if (ncamp == 2) {
                            apellido = celda.getRichStringCellValue().getString();
                        }
                        if (ncamp == 3) {
                            tipoDoc = celda.getRichStringCellValue().getString();
                        }
                        if (ncamp == 5) {
                            direccion = celda.getRichStringCellValue().getString();
                        }
                        if (ncamp == 7) {
                            correo = celda.getRichStringCellValue().getString();
                        }
                        if (ncamp == 10) {
                            automovil = celda.getRichStringCellValue().getString();
                            importarResidente(nombre, apellido, tipoDoc, documento, direccion, celular, correo, torre, apto, automovil);
                        }
                    } else {
                        if (ncamp == 4) {
                            documento = (int) celda.getNumericCellValue();
                        }
                        if (ncamp == 6) {
                            celular = (long) celda.getNumericCellValue();
                        }
                        if (ncamp == 8) {
                            torre = (int) celda.getNumericCellValue();
                        }
                        if (ncamp == 9) {
                            apto = (int) celda.getNumericCellValue();
                        }
                    }
                    ncamp++;
                }
            }
            if (cantDuplicados != 0 && cantVacios!=0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO, "Aviso", 
                        "Migracion Realizada, se interrupieron "
                        + cantDuplicados + " filas ya que el nro. documento o email ya se encuentran registrados. "
                        + cantVacios + " filas se interrumpieron por campos vacios. "
                        + "Se han registrado satisfactoriamente " + cantImportados + " residentes."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Migracion Exitosa, se han registrado satisfactoriamente " + cantImportados + " residentes."));
            }
            mensaje.setMensaje("Mensaje('Importacion Exitosa','Se a notificado vía email el usuario y contraseña.','success');");
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Error abriendo archivo"));
            mensaje.setMensaje("Mensaje('Error en Importacion','El archivo no se puede abrir, revisar la extension requerida','warning');");
        } catch (InvalidFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Error en formato"));
            mensaje.setMensaje("Mensaje('Error en Importacion','El formato en el archivo es erroneo, por favor verificar las instrucciones.','error');");
        }
    }

    public void importarResidente(String nombre, String apellido, String tipoDoc,
            int documento, String direccion, long celular, String correo, int torre,
            int apto, String automovil) throws NoSuchProviderException, MessagingException {
        try {
            // ========== validar campos repetidos
            user = usuarioFacade.validarDocumento(documento);
            useremail = usuarioFacade.validarEmail(correo);
            if (user.getDocumento() != 0) {
                cantDuplicados++;
            } else if (useremail.getCorreo() != null) {
                cantDuplicados++;
            } else {
                // ========== crear usuario
                usuario.setIdRol(rolFacade.find(2));
                usuario.setNombre(nombre);
                usuario.setApellido(apellido);
                int id;
                if (tipoDoc.equals("Cedula de Ciudadania")) {
                    id = 1;
                } else {
                    id = 2;
                }
                usuario.setTipoDocumento(tipoFacade.find(id));
                usuario.setDocumento(documento);
                usuario.setDireccion(direccion);
                usuario.setCelular(celular);
                usuario.setCorreo(correo);
                usuario.setContrasenia(usuarioControlador.generarContraseña());
                usuario.setEstado("Activo");
                usuarioFacade.create(usuario);
                // ========== crear inmueble y residente
                residente.setIdPerfil(usuario);
                inmueble.setIdTorre(torreFacade.find(torre));
                inmueble.setIdApartamento(aptoFacade.find(apto));
                inmuebleFacade.create(inmueble);
                residente.setIdInmueble(inmueble);
                residente.setAutomovil(automovil);
                residenteFacade.create(residente);
                // ========== enviar email con usuario y contraseña          
                email.enviarEmail(correo, "Registro exitoso",
                        email.paginaCorreo("Bienvenido!",
                                "<p style='font-family: Arial, Helvetica, sans-serif;'><b>¡Hola, " + usuario.getNombre() + "!</b></p>\n"
                                + "<p style='font-family: Arial, Helvetica, sans-serif;'>Aquí tienes la información de tu <b>cuenta</b> para acceder al sistema de tu conjunto residencial:</p>\n"
                                + "<p style='font-family: Arial, Helvetica, sans-serif;'>Usuario: " + usuario.getDocumento() + "<br/>Contraseña: " + usuario.getContrasenia() + "</p>",
                                "http://imgfz.com/i/7ocMf5s.jpeg")
                );
                cantImportados++;
                residente = new Residente();
                inmueble = new Inmueble();
                usuario = new Usuario();
            }
        } catch (NoSuchProviderException | MessagingException e) {
            cantVacios++;
            System.out.println("Error migracion:" + e.getMessage());
        }
    }

    public Part getExcel() {
        return excel;
    }

    public void setExcel(Part excel) {
        this.excel = excel;
    }

}
