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
@Table(name = "tipo_pqrs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoPqrs.findAll", query = "SELECT t FROM TipoPqrs t")
    , @NamedQuery(name = "TipoPqrs.findByIdTipoPqrs", query = "SELECT t FROM TipoPqrs t WHERE t.idTipoPqrs = :idTipoPqrs")
    , @NamedQuery(name = "TipoPqrs.findByTipo", query = "SELECT t FROM TipoPqrs t WHERE t.tipo = :tipo")})
public class TipoPqrs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipoPqrs")
    private Integer idTipoPqrs;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "tipo")
    private String tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoPqrs", fetch = FetchType.LAZY)
    private List<Pqrs> pqrsList;

    public TipoPqrs() {
    }

    public TipoPqrs(Integer idTipoPqrs) {
        this.idTipoPqrs = idTipoPqrs;
    }

    public TipoPqrs(Integer idTipoPqrs, String tipo) {
        this.idTipoPqrs = idTipoPqrs;
        this.tipo = tipo;
    }

    public Integer getIdTipoPqrs() {
        return idTipoPqrs;
    }

    public void setIdTipoPqrs(Integer idTipoPqrs) {
        this.idTipoPqrs = idTipoPqrs;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
        hash += (idTipoPqrs != null ? idTipoPqrs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPqrs)) {
            return false;
        }
        TipoPqrs other = (TipoPqrs) object;
        if ((this.idTipoPqrs == null && other.idTipoPqrs != null) || (this.idTipoPqrs != null && !this.idTipoPqrs.equals(other.idTipoPqrs))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.TipoPqrs[ idTipoPqrs=" + idTipoPqrs + " ]";
    }
    
}
