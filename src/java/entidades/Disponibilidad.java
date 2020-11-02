/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Huertas
 */
@Entity
@Table(name = "disponibilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Disponibilidad.findAll", query = "SELECT d FROM Disponibilidad d")
    , @NamedQuery(name = "Disponibilidad.findByIdDisponibilidad", query = "SELECT d FROM Disponibilidad d WHERE d.idDisponibilidad = :idDisponibilidad")})
public class Disponibilidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDisponibilidad")
    private Integer idDisponibilidad;
    @JoinColumn(name = "idZonaComunal", referencedColumnName = "idZonaComunal")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ZonaComunal idZonaComunal;
    @JoinColumn(name = "idDia", referencedColumnName = "idDia")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DisponibilidadDia idDia;
    @JoinColumn(name = "horaInicialReserva", referencedColumnName = "idHora")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private HoraInicial horaInicialReserva;
    @JoinColumn(name = "horaFinalReserva", referencedColumnName = "idHora")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private HoraFinal horaFinalReserva;

    public Disponibilidad() {
    }

    public Disponibilidad(Integer idDisponibilidad) {
        this.idDisponibilidad = idDisponibilidad;
    }

    public Integer getIdDisponibilidad() {
        return idDisponibilidad;
    }

    public void setIdDisponibilidad(Integer idDisponibilidad) {
        this.idDisponibilidad = idDisponibilidad;
    }

    public ZonaComunal getIdZonaComunal() {
        return idZonaComunal;
    }

    public void setIdZonaComunal(ZonaComunal idZonaComunal) {
        this.idZonaComunal = idZonaComunal;
    }

    public DisponibilidadDia getIdDia() {
        return idDia;
    }

    public void setIdDia(DisponibilidadDia idDia) {
        this.idDia = idDia;
    }

    public HoraInicial getHoraInicialReserva() {
        return horaInicialReserva;
    }

    public void setHoraInicialReserva(HoraInicial horaInicialReserva) {
        this.horaInicialReserva = horaInicialReserva;
    }

    public HoraFinal getHoraFinalReserva() {
        return horaFinalReserva;
    }

    public void setHoraFinalReserva(HoraFinal horaFinalReserva) {
        this.horaFinalReserva = horaFinalReserva;
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
