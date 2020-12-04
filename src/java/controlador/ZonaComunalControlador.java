/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.ZonaComunal;
import facade.ReservaFacade;
import facade.ZonaComunalFacade;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author HUERTAS
 */
@Named(value = "zonaComunalControlador")
@SessionScoped
public class ZonaComunalControlador implements Serializable {

    /**
     * Creates a new instance of ZonaComunalControlador
     */
    @Inject
    ImagenControlador imagen;
    @Inject
    MensajeControlador mensaje;
    private ZonaComunal zonaComunal;
    private String mayor;
    private String menor;

    @EJB
    ZonaComunalFacade zonaComunalFacade;

    @EJB
    ReservaFacade reservaFacade;

    public ZonaComunalControlador() {
    }

    @PostConstruct
    public void init() {
        zonaComunal = new ZonaComunal();
    }

    public void registrar() {
        zonaComunalFacade.create(zonaComunal);
    }

    public void eliminar(ZonaComunal zonaComunalEliminar){
        try {
            if (zonaComunalEliminar.getCantidadReservada() != 0) {
                mensaje.setMensaje("Mensajes('Atención','No puedes eliminar una zona que ya a sido reservada...','warning');");
            } else {
                mensaje.setMensaje("Confirmar('Estas seguro que deseas eliminar " + zonaComunalEliminar.getNombre() + "','No podras revertilo!','warning','Si, eliminar!','Eliminado!','Se ha eliminado exitosamente la zona común.','success');");
                Path p = Paths.get(imagen.getRuta() + zonaComunalEliminar.getImg());
                Files.delete(p);
                zonaComunalFacade.remove(zonaComunalEliminar);
            }
        } catch (Exception e) {
            System.out.println("Error"+e.getMessage());
        }
    }

    public List<ZonaComunal> consultarTodos() {
        return zonaComunalFacade.findAll();
    }

    public String consultarZona(int id) {
        zonaComunal = zonaComunalFacade.find(id);
        return "detalle-zona";
    }

    public String reservarZona(int id) {
        zonaComunal = zonaComunalFacade.find(id);
        return "generar-reserva";
    }

    public List<String> consultarNombreZona() {
        List<String> nombres = new ArrayList<>();
        try {
            for (ZonaComunal zona : zonaComunalFacade.findAll()) {
                String nameRol = zona.getNombre();
                nombres.add('"' + nameRol + '"');
            }
        } catch (Exception e) {
            System.out.println("Error consulta zona-name: " + e.getMessage());
        }
        return nombres;
    }

    public List<Integer> contarResZon() {
        List<Integer> data = new ArrayList<>();
        try {
            for (ZonaComunal zona : zonaComunalFacade.findAll()) {
                int dataZon = reservaFacade.ContarReserva(zona.getIdZonaComunal());
                data.add(dataZon);
                if (data.size() > 1) {
                    for (int i = 1; i < data.size(); i++) {
                        if (data.get(i) > data.get(i - 1)) {
                            mayor = zona.getNombre();
                        }
                        if (data.get(i) < data.get(i - 1)) {
                            menor = zona.getNombre();
                        }
                    }
                } else {
                    mayor = zona.getNombre();
                    menor = zona.getNombre();
                }
            }
        } catch (Exception e) {
            System.out.println("Error contar res-zon: " + e.getMessage());
        }
        return data;
    }

    public String maxx() {
        try {
            zonaComunal = zonaComunalFacade.find(1);
            ZonaComunal zonaComunal2 = zonaComunalFacade.find(2);
            ZonaComunal zonaComunal3 = zonaComunalFacade.find(3);
            if (zonaComunal.getCantidadReservada() > zonaComunal2.getCantidadReservada() && zonaComunal.getCantidadReservada() > zonaComunal3.getCantidadReservada()) {
                mayor = zonaComunal.getNombre();
            } else if (zonaComunal2.getCantidadReservada() > zonaComunal.getCantidadReservada() && zonaComunal2.getCantidadReservada() > zonaComunal3.getCantidadReservada()) {
                mayor = zonaComunal2.getNombre();
            } else if (zonaComunal3.getCantidadReservada() > zonaComunal.getCantidadReservada() && zonaComunal3.getCantidadReservada() > zonaComunal2.getCantidadReservada()) {
                mayor = zonaComunal3.getNombre();
            }
        } catch (Exception e) {
            System.out.println("Error MAX - Zona:" + e.getMessage());
        }
        return mayor;
    }

    public String minn() {
        try {
            zonaComunal = zonaComunalFacade.find(1);
            ZonaComunal zonaComunal2 = zonaComunalFacade.find(2);
            ZonaComunal zonaComunal3 = zonaComunalFacade.find(3);
            if (zonaComunal.getCantidadReservada() < zonaComunal2.getCantidadReservada() && zonaComunal.getCantidadReservada() < zonaComunal3.getCantidadReservada()) {
                menor = zonaComunal.getNombre();
            } else if (zonaComunal2.getCantidadReservada() < zonaComunal.getCantidadReservada() && zonaComunal2.getCantidadReservada() < zonaComunal3.getCantidadReservada()) {
                menor = zonaComunal2.getNombre();
            } else if (zonaComunal3.getCantidadReservada() < zonaComunal.getCantidadReservada() && zonaComunal3.getCantidadReservada() < zonaComunal2.getCantidadReservada()) {
                menor = zonaComunal3.getNombre();
            }
        } catch (Exception e) {
            System.out.println("Erro Min - Zona: " + e.getMessage());
        }
        return menor;
    }

    public String pepe() {
        return zonaComunalFacade.max();
    }

    public int contarZonas() {
        return zonaComunalFacade.count();
    }

    public ZonaComunal getZonaComunal() {
        return zonaComunal;
    }

    public void setZonaComunal(ZonaComunal zonaComunal) {
        this.zonaComunal = zonaComunal;
    }

    public String getMayor() {
        return mayor;
    }

    public void setMayor(String mayor) {
        this.mayor = mayor;
    }

    public String getMenor() {
        return menor;
    }

    public void setMenor(String menor) {
        this.menor = menor;
    }

}
