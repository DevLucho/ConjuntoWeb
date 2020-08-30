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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author User
 */
@Entity
@Table(name = "torre")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Torre.findAll", query = "SELECT t FROM Torre t")
    , @NamedQuery(name = "Torre.findByIdTorre", query = "SELECT t FROM Torre t WHERE t.idTorre = :idTorre")})
public class Torre implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTorre")
    private Integer idTorre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTorre", fetch = FetchType.LAZY)
    private List<Inmueble> inmuebleList;

    public Torre() {
    }

    public Torre(Integer idTorre) {
        this.idTorre = idTorre;
    }

    public Integer getIdTorre() {
        return idTorre;
    }

    public void setIdTorre(Integer idTorre) {
        this.idTorre = idTorre;
    }

    @XmlTransient
    public List<Inmueble> getInmuebleList() {
        return inmuebleList;
    }

    public void setInmuebleList(List<Inmueble> inmuebleList) {
        this.inmuebleList = inmuebleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTorre != null ? idTorre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Torre)) {
            return false;
        }
        Torre other = (Torre) object;
        if ((this.idTorre == null && other.idTorre != null) || (this.idTorre != null && !this.idTorre.equals(other.idTorre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Torre[ idTorre=" + idTorre + " ]";
    }
    
}
