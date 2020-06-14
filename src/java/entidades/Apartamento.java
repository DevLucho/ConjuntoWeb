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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Huertas
 */
@Entity
@Table(name = "apartamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Apartamento.findAll", query = "SELECT a FROM Apartamento a")
    , @NamedQuery(name = "Apartamento.findByIdApartamento", query = "SELECT a FROM Apartamento a WHERE a.idApartamento = :idApartamento")})
public class Apartamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idApartamento")
    private Integer idApartamento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idApartamento", fetch = FetchType.LAZY)
    private List<Inmueble> inmuebleList;

    public Apartamento() {
    }

    public Apartamento(Integer idApartamento) {
        this.idApartamento = idApartamento;
    }

    public Integer getIdApartamento() {
        return idApartamento;
    }

    public void setIdApartamento(Integer idApartamento) {
        this.idApartamento = idApartamento;
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
        hash += (idApartamento != null ? idApartamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Apartamento)) {
            return false;
        }
        Apartamento other = (Apartamento) object;
        if ((this.idApartamento == null && other.idApartamento != null) || (this.idApartamento != null && !this.idApartamento.equals(other.idApartamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Apartamento[ idApartamento=" + idApartamento + " ]";
    }
    
}
