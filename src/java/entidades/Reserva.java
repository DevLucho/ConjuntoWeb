/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Huertas
 */
@Entity
@Table(name = "reserva")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reserva.findAll", query = "SELECT r FROM Reserva r")
    , @NamedQuery(name = "Reserva.findByIdReserva", query = "SELECT r FROM Reserva r WHERE r.idReserva = :idReserva")
    , @NamedQuery(name = "Reserva.findByNombre", query = "SELECT r FROM Reserva r WHERE r.nombre = :nombre")
    , @NamedQuery(name = "Reserva.findByMotivoReserva", query = "SELECT r FROM Reserva r WHERE r.motivoReserva = :motivoReserva")
    , @NamedQuery(name = "Reserva.findByFechaInicioReserva", query = "SELECT r FROM Reserva r WHERE r.fechaInicioReserva = :fechaInicioReserva")
    , @NamedQuery(name = "Reserva.findByFechaFinReserva", query = "SELECT r FROM Reserva r WHERE r.fechaFinReserva = :fechaFinReserva")
    , @NamedQuery(name = "Reserva.findByEstado", query = "SELECT r FROM Reserva r WHERE r.estado = :estado")})
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idReserva")
    private Integer idReserva;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "motivoReserva")
    private String motivoReserva;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaInicioReserva")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioReserva;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaFinReserva")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinReserva;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "idResidente", referencedColumnName = "idResidente")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Residente idResidente;
    @JoinColumn(name = "idZonaComunal", referencedColumnName = "idZonaComunal")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ZonaComunal idZonaComunal;

    public Reserva() {
    }

    public Reserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public Reserva(Integer idReserva, String nombre, String motivoReserva, Date fechaInicioReserva, Date fechaFinReserva, String estado) {
        this.idReserva = idReserva;
        this.nombre = nombre;
        this.motivoReserva = motivoReserva;
        this.fechaInicioReserva = fechaInicioReserva;
        this.fechaFinReserva = fechaFinReserva;
        this.estado = estado;
    }

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMotivoReserva() {
        return motivoReserva;
    }

    public void setMotivoReserva(String motivoReserva) {
        this.motivoReserva = motivoReserva;
    }

    public Date getFechaInicioReserva() {
        return fechaInicioReserva;
    }

    public void setFechaInicioReserva(Date fechaInicioReserva) {
        this.fechaInicioReserva = fechaInicioReserva;
    }

    public Date getFechaFinReserva() {
        return fechaFinReserva;
    }

    public void setFechaFinReserva(Date fechaFinReserva) {
        this.fechaFinReserva = fechaFinReserva;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Residente getIdResidente() {
        return idResidente;
    }

    public void setIdResidente(Residente idResidente) {
        this.idResidente = idResidente;
    }

    public ZonaComunal getIdZonaComunal() {
        return idZonaComunal;
    }

    public void setIdZonaComunal(ZonaComunal idZonaComunal) {
        this.idZonaComunal = idZonaComunal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReserva != null ? idReserva.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reserva)) {
            return false;
        }
        Reserva other = (Reserva) object;
        if ((this.idReserva == null && other.idReserva != null) || (this.idReserva != null && !this.idReserva.equals(other.idReserva))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Reserva[ idReserva=" + idReserva + " ]";
    }
    
}
