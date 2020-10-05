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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "codigo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Codigo.findAll", query = "SELECT c FROM Codigo c")
    , @NamedQuery(name = "Codigo.findByIdCodigo", query = "SELECT c FROM Codigo c WHERE c.idCodigo = :idCodigo")
    , @NamedQuery(name = "Codigo.findByCodigo", query = "SELECT c FROM Codigo c WHERE c.codigo = :codigo")
    , @NamedQuery(name = "Codigo.findByEstado", query = "SELECT c FROM Codigo c WHERE c.estado = :estado")})
public class Codigo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_codigo")
    private Integer idCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "estado")
    private String estado;

    public Codigo() {
    }

    public Codigo(Integer idCodigo) {
        this.idCodigo = idCodigo;
    }

    public Codigo(Integer idCodigo, String codigo, String estado) {
        this.idCodigo = idCodigo;
        this.codigo = codigo;
        this.estado = estado;
    }

    public Integer getIdCodigo() {
        return idCodigo;
    }

    public void setIdCodigo(Integer idCodigo) {
        this.idCodigo = idCodigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCodigo != null ? idCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Codigo)) {
            return false;
        }
        Codigo other = (Codigo) object;
        if ((this.idCodigo == null && other.idCodigo != null) || (this.idCodigo != null && !this.idCodigo.equals(other.idCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Codigo[ idCodigo=" + idCodigo + " ]";
    }
    
}
