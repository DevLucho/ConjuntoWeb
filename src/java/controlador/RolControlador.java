/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Permiso;
import entidades.Rol;
import entidades.RolPermiso;
import facade.PermisoFacade;
import facade.RolFacade;
import facade.RolPermisoFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author HUERTAS
 */
@Named(value = "rolControlador")
@SessionScoped
public class RolControlador implements Serializable {

    /**
     * Creates a new instance of RolControlador
     */
    @Inject
    private MensajeControlador mensaje;
    private Rol rol;
    private Rol rol2 = null; // validar nombre rol
    private RolPermiso rolPermiso;
    private Permiso permiso;
    private String nombre;
    private int cont;
    private int id;
    // permisos
    private int[] usuarios;
    private int[] comunicados;
    private int[] eventos;
    private int[] zonasComunes;
    private int[] servicioPqrs;
    private int[] reportes;
    private int[] documentacion;
    private int[] domiciliario;
    private int[] visitante;
    private int[] correspondencia;
    private int[] inicio;
    private int[] parqueadero;
    private int[] correspondenciaV;
    private List<RolPermiso> rolPermisoList;
    private List<Permiso> permisoPadreList;

    @EJB
    RolFacade rolFacade;

    @EJB
    RolPermisoFacade rolPermisoFacade;

    @EJB
    PermisoFacade permisoFacade;

    public RolControlador() {
    }

    @PostConstruct
    public void init() {
        rol = new Rol();
        rolPermiso = new RolPermiso();
        permiso = new Permiso();
    }

    public void registrar() {
        rol2 = rolFacade.nombreRol(nombre);
        if (rol2.getNombre() == null) {
            if (cont == 0) {
                rol.setNombre(nombre);
                rolFacade.create(rol);
                this.id = rol.getIdRol();
                mensaje.setMensaje("MensajeRedirect('asignar-permiso.xhtml','¡Se ha creado un nuevo rol!','A continuación sera redireccionado para asignar permisos al rol " + nombre + "','success');");
            } else {
                this.rolPermisoList = rolPermisoFacade.rolPermisos(cont);
                rol.setNombre(nombre);
                rolFacade.create(rol);
                for (int i = 0; i < rolPermisoList.size(); i++) {
                    rolPermiso.setEstado("Activo");
                    rolPermiso.setIdRol(rol);
                    permiso = permisoFacade.consultarPermiso(rolPermisoList.get(i).getIdPermiso().getIdPermiso());
                    rolPermiso.setIdPermiso(permiso);
                    rolPermisoFacade.create(rolPermiso);
                }
                mensaje.setMensaje("Mensajes('¡Rol creado y permisos asignados!','Has creado el rol " + nombre + " y asignado permisos del rol " + rolPermisoList.get(0).getIdRol().getNombre() + "','success');");
                rolPermiso = new RolPermiso();
                rol = new Rol();
                nombre = "";
            }
        } else {
            mensaje.setMensaje("MensajeAlertify('Ya existe un rol con el nombre: " + nombre + ", prueba con otro.','error');");
        }
    }

    // Registrar permisos
    public void registrarP() {
        if (this.id != 0) {
            int permisos[] = new int[this.usuarios.length + this.comunicados.length + this.eventos.length
                    + this.zonasComunes.length + this.servicioPqrs.length + this.reportes.length + this.documentacion.length
                    + this.domiciliario.length + this.visitante.length + this.correspondencia.length + this.inicio.length
                    + this.parqueadero.length + this.correspondenciaV.length];
            System.arraycopy(usuarios, 0, permisos, 0, usuarios.length);
            System.arraycopy(comunicados, 0, permisos, usuarios.length, comunicados.length);
            System.arraycopy(eventos, 0, permisos, usuarios.length + comunicados.length, eventos.length);
            System.arraycopy(zonasComunes, 0, permisos, comunicados.length + usuarios.length + eventos.length, zonasComunes.length);
            System.arraycopy(servicioPqrs, 0, permisos, comunicados.length + usuarios.length + eventos.length + zonasComunes.length, servicioPqrs.length);
            System.arraycopy(reportes, 0, permisos, comunicados.length + usuarios.length + eventos.length + zonasComunes.length + servicioPqrs.length, reportes.length);
            System.arraycopy(documentacion, 0, permisos, comunicados.length + usuarios.length + eventos.length + zonasComunes.length + servicioPqrs.length + reportes.length, documentacion.length);
            System.arraycopy(domiciliario, 0, permisos, comunicados.length + usuarios.length + eventos.length + zonasComunes.length + servicioPqrs.length + reportes.length + documentacion.length, domiciliario.length);
            System.arraycopy(visitante, 0, permisos, comunicados.length + usuarios.length + eventos.length + zonasComunes.length + servicioPqrs.length + reportes.length + documentacion.length + domiciliario.length, visitante.length);
            System.arraycopy(correspondencia, 0, permisos, comunicados.length + usuarios.length + eventos.length + zonasComunes.length + servicioPqrs.length + reportes.length + documentacion.length + domiciliario.length + visitante.length, correspondencia.length);
            System.arraycopy(inicio, 0, permisos, comunicados.length + usuarios.length + eventos.length + zonasComunes.length + servicioPqrs.length + reportes.length + documentacion.length + domiciliario.length + visitante.length + correspondencia.length, inicio.length);
            System.arraycopy(parqueadero, 0, permisos, comunicados.length + usuarios.length + eventos.length + zonasComunes.length + servicioPqrs.length + reportes.length + documentacion.length + domiciliario.length + visitante.length + correspondencia.length + inicio.length, parqueadero.length);
            System.arraycopy(correspondenciaV, 0, permisos, comunicados.length + usuarios.length + eventos.length + zonasComunes.length + servicioPqrs.length + reportes.length + documentacion.length + domiciliario.length + visitante.length + correspondencia.length + inicio.length + parqueadero.length, correspondenciaV.length);

            if (permisos != null) {
                rol = rolFacade.consultarRol(this.id);
                permisoPadreList = new ArrayList<>();
                // obtener permisos padre
                for (int i = 0; i < permisos.length; i++) { // recorrer permisos elejidos (hijos)
                    Permiso permiso2;
                    permiso2 = permisoFacade.consultarPermiso(permisos[i]);
                    permisoPadreList.add(permiso2);
                    for (int j = 1; j < permisoPadreList.size(); j++) { // eliminar permisos padre repeat
                        if (Objects.equals(permisoPadreList.get(j).getPermisoPadre().getIdPermiso(), permisoPadreList.get(j - 1).getPermisoPadre().getIdPermiso())) {
                            permisoPadreList.remove(j);
                        }
                    }
                }
                // registrar permisos hijos elejidos
                for (int i = 0; i < permisos.length; i++) {
                    permiso = permisoFacade.consultarPermiso(permisos[i]);
                    rolPermiso.setEstado("Activo");
                    rolPermiso.setIdPermiso(permiso);
                    rolPermiso.setIdRol(rol);
                    rolPermisoFacade.create(rolPermiso);
                }
                // registrar permisos padre
                for (int i = 0; i < permisoPadreList.size(); i++) {
                    permiso = permisoFacade.consultarPermiso(permisoPadreList.get(i).getPermisoPadre().getIdPermiso());
                    rolPermiso.setEstado("Activo");
                    rolPermiso.setIdPermiso(permiso);
                    rolPermiso.setIdRol(rol);
                    rolPermisoFacade.create(rolPermiso);
                }
                mensaje.setMensaje("MensajeRedirect('roles-permisos.xhtml','¡Permisos asignados!','Has asignado satisfactoriamente permisos al rol " + nombre + "','success');");
                rol = new Rol();
                nombre = "";
                rolPermiso = new RolPermiso();
                permiso = new Permiso();
            }
        }
    }

    public void eliminar(Rol rolEliminar) {
        if (rolEliminar.getIdRol() == 1 || rolEliminar.getIdRol() == 2 || rolEliminar.getIdRol() == 3) {
            mensaje.setMensaje("Mensajes('Error!','No puedes eliminar el rol " + rolEliminar.getNombre() + "','error');");
        } else {
            rolFacade.remove(rolEliminar);
            mensaje.setMensaje("Mensajes('Exito!','El rol " + rolEliminar.getNombre() + " fue eliminado satistactoriamente','success');");
        }
    }

    public String preActualizar(Rol rolActualizar) {
        rol = rolActualizar;
        return "editar-rol";
    }

    public void actualizar() {
        rolFacade.edit(rol);
        mensaje.setMensaje("Mensaje('Exito!','Rol " + rol.getNombre() + " modificado satisfactoriamente','success');");
    }

    public List<Rol> consultarTodos() {
        return rolFacade.findAll();
    }

    public List<Permiso> consultarPermisos(int idPermiso) {
        return permisoFacade.consultarHijos(idPermiso);
    }

    public int contarRol() {
        return rolFacade.contarRol();
    }

    // Get's y Set's ↓
    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCont() {
        return cont;
    }

    public void setCont(int cont) {
        this.cont = cont;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RolPermiso getRolPermiso() {
        return rolPermiso;
    }

    public void setRolPermiso(RolPermiso rolPermiso) {
        this.rolPermiso = rolPermiso;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public int[] getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(int[] usuarios) {
        this.usuarios = usuarios;
    }

    public int[] getComunicados() {
        return comunicados;
    }

    public void setComunicados(int[] comunicados) {
        this.comunicados = comunicados;
    }

    public int[] getEventos() {
        return eventos;
    }

    public void setEventos(int[] eventos) {
        this.eventos = eventos;
    }

    public int[] getZonasComunes() {
        return zonasComunes;
    }

    public void setZonasComunes(int[] zonasComunes) {
        this.zonasComunes = zonasComunes;
    }

    public int[] getServicioPqrs() {
        return servicioPqrs;
    }

    public void setServicioPqrs(int[] servicioPqrs) {
        this.servicioPqrs = servicioPqrs;
    }

    public int[] getReportes() {
        return reportes;
    }

    public void setReportes(int[] reportes) {
        this.reportes = reportes;
    }

    public int[] getDocumentacion() {
        return documentacion;
    }

    public void setDocumentacion(int[] documentacion) {
        this.documentacion = documentacion;
    }

    public int[] getDomiciliario() {
        return domiciliario;
    }

    public void setDomiciliario(int[] domiciliario) {
        this.domiciliario = domiciliario;
    }

    public int[] getVisitante() {
        return visitante;
    }

    public void setVisitante(int[] visitante) {
        this.visitante = visitante;
    }

    public int[] getCorrespondencia() {
        return correspondencia;
    }

    public void setCorrespondencia(int[] correspondencia) {
        this.correspondencia = correspondencia;
    }

    public int[] getInicio() {
        return inicio;
    }

    public void setInicio(int[] inicio) {
        this.inicio = inicio;
    }

    public int[] getParqueadero() {
        return parqueadero;
    }

    public void setParqueadero(int[] parqueadero) {
        this.parqueadero = parqueadero;
    }

    public int[] getCorrespondenciaV() {
        return correspondenciaV;
    }

    public void setCorrespondenciaV(int[] correspondenciaV) {
        this.correspondenciaV = correspondenciaV;
    }

    public List<RolPermiso> getRolPermisoList() {
        return rolPermisoList;
    }

    public void setRolPermisoList(List<RolPermiso> rolPermisoList) {
        this.rolPermisoList = rolPermisoList;
    }

    public List<Permiso> getPermisoPadreList() {
        return permisoPadreList;
    }

    public void setPermisoPadreList(List<Permiso> permisoPadreList) {
        this.permisoPadreList = permisoPadreList;
    }

    public Rol getRol2() {
        return rol2;
    }

    public void setRol2(Rol rol2) {
        this.rol2 = rol2;
    }

}
