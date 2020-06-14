/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Huertas
 */
@Entity
@Table(name = "zona_comunal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ZonaComunal.findAll", query = "SELECT z FROM ZonaComunal z")
    , @NamedQuery(name = "ZonaComunal.findByIdZonaComunal", query = "SELECT z FROM ZonaComunal z WHERE z.idZonaComunal = :idZonaComunal")
    , @NamedQuery(name = "ZonaComunal.findByNombre", query = "SELECT z FROM ZonaComunal z WHERE z.nombre = :nombre")
    , @NamedQuery(name = "ZonaComunal.findByHoraInicialReserva", query = "SELECT z FROM ZonaComunal z WHERE z.horaInicialReserva = :horaInicialReserva")
    , @NamedQuery(name = "ZonaComunal.findByHoraFinalReserva", query = "SELECT z FROM ZonaComunal z WHERE z.horaFinalReserva = :horaFinalReserva")
    , @NamedQuery(name = "ZonaComunal.findByTiempoMaximoReserva", query = "SELECT z FROM ZonaComunal z WHERE z.tiempoMaximoReserva = :tiempoMaximoReserva")
    , @NamedQuery(name = "ZonaComunal.findByDescripcion", query = "SELECT z FROM ZonaComunal z WHERE z.descripcion = :descripcion")})
public class ZonaComunal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idZonaComunal")
    private Integer idZonaComunal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "disponibilidad")
    private String disponibilidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "horaInicialReserva")
    @Temporal(TemporalType.TIME)
    private Date horaInicialReserva;
    @Basic(optional = false)
    @NotNull
    @Column(name = "horaFinalReserva")
    @Temporal(TemporalType.TIME)
    private Date horaFinalReserva;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tiempoMaximoReserva")
    @Temporal(TemporalType.TIME)
    private Date tiempoMaximoReserva;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idZonaComunal", fetch = FetchType.LAZY)
    private List<Evento> eventoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idZonaComunal", fetch = FetchType.LAZY)
    private List<Reserva> reservaList;

    public ZonaComunal() {
    }

    public ZonaComunal(Integer idZonaComunal) {
        this.idZonaComunal = idZonaComunal;
    }

    public ZonaComunal(Integer idZonaComunal, String nombre, String disponibilidad, Date horaInicialReserva, Date horaFinalReserva, Date tiempoMaximoReserva, String descripcion) {
        this.idZonaComunal = idZonaComunal;
        this.nombre = nombre;
        this.disponibilidad = disponibilidad;
        this.horaInicialReserva = horaInicialReserva;
        this.horaFinalReserva = horaFinalReserva;
        this.tiempoMaximoReserva = tiempoMaximoReserva;
        this.descripcion = descripcion;
    }

    public Integer getIdZonaComunal() {
        return idZonaComunal;
    }

    public void setIdZonaComunal(Integer idZonaComunal) {
        this.idZonaComunal = idZonaComunal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Date getHoraInicialReserva() {
        return horaInicialReserva;
    }

    public void setHoraInicialReserva(Date horaInicialReserva) {
        this.horaInicialReserva = horaInicialReserva;
    }

    public Date getHoraFinalReserva() {
        return horaFinalReserva;
    }

    public void setHoraFinalReserva(Date horaFinalReserva) {
        this.horaFinalReserva = horaFinalReserva;
    }

    public Date getTiempoMaximoReserva() {
        return tiempoMaximoReserva;
    }

    public void setTiempoMaximoReserva(Date tiempoMaximoReserva) {
        this.tiempoMaximoReserva = tiempoMaximoReserva;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Evento> getEventoList() {
        return eventoList;
    }

    public void setEventoList(List<Evento> eventoList) {
        this.eventoList = eventoList;
    }

    @XmlTransient
    public List<Reserva> getReservaList() {
        return reservaList;
    }

    public void setReservaList(List<Reserva> reservaList) {
        this.reservaList = reservaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idZonaComunal != null ? idZonaComunal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ZonaComunal)) {
            return false;
        }
        ZonaComunal other = (ZonaComunal) object;
        if ((this.idZonaComunal == null && other.idZonaComunal != null) || (this.idZonaComunal != null && !this.idZonaComunal.equals(other.idZonaComunal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.ZonaComunal[ idZonaComunal=" + idZonaComunal + " ]";
    }
    
}
