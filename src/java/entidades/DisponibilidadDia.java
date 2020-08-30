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
 * @author User
 */
@Entity
@Table(name = "disponibilidad_dia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DisponibilidadDia.findAll", query = "SELECT d FROM DisponibilidadDia d")
    , @NamedQuery(name = "DisponibilidadDia.findByIdDia", query = "SELECT d FROM DisponibilidadDia d WHERE d.idDia = :idDia")
    , @NamedQuery(name = "DisponibilidadDia.findByNombre", query = "SELECT d FROM DisponibilidadDia d WHERE d.nombre = :nombre")})
public class DisponibilidadDia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDia")
    private Integer idDia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDia", fetch = FetchType.LAZY)
    private List<Disponibilidad> disponibilidadList;

    public DisponibilidadDia() {
    }

    public DisponibilidadDia(Integer idDia) {
        this.idDia = idDia;
    }

    public DisponibilidadDia(Integer idDia, String nombre) {
        this.idDia = idDia;
        this.nombre = nombre;
    }

    public Integer getIdDia() {
        return idDia;
    }

    public void setIdDia(Integer idDia) {
        this.idDia = idDia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Disponibilidad> getDisponibilidadList() {
        return disponibilidadList;
    }

    public void setDisponibilidadList(List<Disponibilidad> disponibilidadList) {
        this.disponibilidadList = disponibilidadList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDia != null ? idDia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DisponibilidadDia)) {
            return false;
        }
        DisponibilidadDia other = (DisponibilidadDia) object;
        if ((this.idDia == null && other.idDia != null) || (this.idDia != null && !this.idDia.equals(other.idDia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.DisponibilidadDia[ idDia=" + idDia + " ]";
    }
    
}
