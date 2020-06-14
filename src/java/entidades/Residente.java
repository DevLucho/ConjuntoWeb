/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Huertas
 */
@Entity
@Table(name = "residente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Residente.findAll", query = "SELECT r FROM Residente r")
    , @NamedQuery(name = "Residente.findByIdResidente", query = "SELECT r FROM Residente r WHERE r.idResidente = :idResidente")
    , @NamedQuery(name = "Residente.findByAutomovil", query = "SELECT r FROM Residente r WHERE r.automovil = :automovil")})
public class Residente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idResidente")
    private Integer idResidente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "automovil")
    private String automovil;
    @JoinColumn(name = "idPerfil", referencedColumnName = "idPerfil")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario idPerfil;
    @JoinColumn(name = "idInmueble", referencedColumnName = "idInmueble")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Inmueble idInmueble;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idResidente", fetch = FetchType.LAZY)
    private List<Reserva> reservaList;
    @OneToMany(mappedBy = "idResidente", fetch = FetchType.LAZY)
    private List<Vehiculo> vehiculoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idResidente", fetch = FetchType.LAZY)
    private List<Inscripcion> inscripcionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idResidente", fetch = FetchType.LAZY)
    private List<Pqrs> pqrsList;

    public Residente() {
    }

    public Residente(Integer idResidente) {
        this.idResidente = idResidente;
    }

    public Residente(Integer idResidente, String automovil) {
        this.idResidente = idResidente;
        this.automovil = automovil;
    }

    public Integer getIdResidente() {
        return idResidente;
    }

    public void setIdResidente(Integer idResidente) {
        this.idResidente = idResidente;
    }

    public String getAutomovil() {
        return automovil;
    }

    public void setAutomovil(String automovil) {
        this.automovil = automovil;
    }

    public Usuario getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Usuario idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Inmueble getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(Inmueble idInmueble) {
        this.idInmueble = idInmueble;
    }

    @XmlTransient
    public List<Reserva> getReservaList() {
        return reservaList;
    }

    public void setReservaList(List<Reserva> reservaList) {
        this.reservaList = reservaList;
    }

    @XmlTransient
    public List<Vehiculo> getVehiculoList() {
        return vehiculoList;
    }

    public void setVehiculoList(List<Vehiculo> vehiculoList) {
        this.vehiculoList = vehiculoList;
    }

    @XmlTransient
    public List<Inscripcion> getInscripcionList() {
        return inscripcionList;
    }

    public void setInscripcionList(List<Inscripcion> inscripcionList) {
        this.inscripcionList = inscripcionList;
    }

    @XmlTransient
    public List<Pqrs> getPqrsList() {
        return pqrsList;
    }

    public void setPqrsList(List<Pqrs> pqrsList) {
        this.pqrsList = pqrsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idResidente != null ? idResidente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Residente)) {
            return false;
        }
        Residente other = (Residente) object;
        if ((this.idResidente == null && other.idResidente != null) || (this.idResidente != null && !this.idResidente.equals(other.idResidente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Residente[ idResidente=" + idResidente + " ]";
    }
    
}
