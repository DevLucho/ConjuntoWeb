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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Huertas
 */
@Entity
@Table(name = "inmueble")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inmueble.findAll", query = "SELECT i FROM Inmueble i")
    , @NamedQuery(name = "Inmueble.findByIdInmueble", query = "SELECT i FROM Inmueble i WHERE i.idInmueble = :idInmueble")})
public class Inmueble implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idInmueble")
    private Integer idInmueble;
    @JoinColumn(name = "idApartamento", referencedColumnName = "idApartamento")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Apartamento idApartamento;
    @JoinColumn(name = "idTorre", referencedColumnName = "idTorre")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Torre idTorre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idInmueble", fetch = FetchType.LAZY)
    private List<Residente> residenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idInmueble", fetch = FetchType.LAZY)
    private List<Correspondencia> correspondenciaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idInmueble", fetch = FetchType.LAZY)
    private List<FichaIngreso> fichaIngresoList;

    public Inmueble() {
    }

    public Inmueble(Integer idInmueble) {
        this.idInmueble = idInmueble;
    }

    public Integer getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(Integer idInmueble) {
        this.idInmueble = idInmueble;
    }

    public Apartamento getIdApartamento() {
        return idApartamento;
    }

    public void setIdApartamento(Apartamento idApartamento) {
        this.idApartamento = idApartamento;
    }

    public Torre getIdTorre() {
        return idTorre;
    }

    public void setIdTorre(Torre idTorre) {
        this.idTorre = idTorre;
    }

    @XmlTransient
    public List<Residente> getResidenteList() {
        return residenteList;
    }

    public void setResidenteList(List<Residente> residenteList) {
        this.residenteList = residenteList;
    }

    @XmlTransient
    public List<Correspondencia> getCorrespondenciaList() {
        return correspondenciaList;
    }

    public void setCorrespondenciaList(List<Correspondencia> correspondenciaList) {
        this.correspondenciaList = correspondenciaList;
    }

    @XmlTransient
    public List<FichaIngreso> getFichaIngresoList() {
        return fichaIngresoList;
    }

    public void setFichaIngresoList(List<FichaIngreso> fichaIngresoList) {
        this.fichaIngresoList = fichaIngresoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInmueble != null ? idInmueble.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inmueble)) {
            return false;
        }
        Inmueble other = (Inmueble) object;
        if ((this.idInmueble == null && other.idInmueble != null) || (this.idInmueble != null && !this.idInmueble.equals(other.idInmueble))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Inmueble[ idInmueble=" + idInmueble + " ]";
    }
    
}
