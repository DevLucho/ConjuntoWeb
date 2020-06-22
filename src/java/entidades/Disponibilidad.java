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
@Table(name = "disponibilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Disponibilidad.findAll", query = "SELECT d FROM Disponibilidad d")
    , @NamedQuery(name = "Disponibilidad.findByIdDisponibilidad", query = "SELECT d FROM Disponibilidad d WHERE d.idDisponibilidad = :idDisponibilidad")
    , @NamedQuery(name = "Disponibilidad.findByHoraInicialReserva", query = "SELECT d FROM Disponibilidad d WHERE d.horaInicialReserva = :horaInicialReserva")
    , @NamedQuery(name = "Disponibilidad.findByHoraFinalReserva", query = "SELECT d FROM Disponibilidad d WHERE d.horaFinalReserva = :horaFinalReserva")
    , @NamedQuery(name = "Disponibilidad.findByTiempoMaximoReserva", query = "SELECT d FROM Disponibilidad d WHERE d.tiempoMaximoReserva = :tiempoMaximoReserva")})
public class Disponibilidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDisponibilidad")
    private Integer idDisponibilidad;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "dias")
    private String dias;
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
    private int tiempoMaximoReserva;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDisponibilidad", fetch = FetchType.LAZY)
    private List<ZonaComunal> zonaComunalList;

    public Disponibilidad() {
    }

    public Disponibilidad(Integer idDisponibilidad) {
        this.idDisponibilidad = idDisponibilidad;
    }

    public Disponibilidad(Integer idDisponibilidad, String dias, Date horaInicialReserva, Date horaFinalReserva, int tiempoMaximoReserva) {
        this.idDisponibilidad = idDisponibilidad;
        this.dias = dias;
        this.horaInicialReserva = horaInicialReserva;
        this.horaFinalReserva = horaFinalReserva;
        this.tiempoMaximoReserva = tiempoMaximoReserva;
    }

    public Integer getIdDisponibilidad() {
        return idDisponibilidad;
    }

    public void setIdDisponibilidad(Integer idDisponibilidad) {
        this.idDisponibilidad = idDisponibilidad;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
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

    public int getTiempoMaximoReserva() {
        return tiempoMaximoReserva;
    }

    public void setTiempoMaximoReserva(int tiempoMaximoReserva) {
        this.tiempoMaximoReserva = tiempoMaximoReserva;
    }

    @XmlTransient
    public List<ZonaComunal> getZonaComunalList() {
        return zonaComunalList;
    }

    public void setZonaComunalList(List<ZonaComunal> zonaComunalList) {
        this.zonaComunalList = zonaComunalList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDisponibilidad != null ? idDisponibilidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Disponibilidad)) {
            return false;
        }
        Disponibilidad other = (Disponibilidad) object;
        if ((this.idDisponibilidad == null && other.idDisponibilidad != null) || (this.idDisponibilidad != null && !this.idDisponibilidad.equals(other.idDisponibilidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Disponibilidad[ idDisponibilidad=" + idDisponibilidad + " ]";
    }
    
}
