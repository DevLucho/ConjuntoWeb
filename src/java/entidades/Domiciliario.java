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
 * @author User
 */
@Entity
@Table(name = "domiciliario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Domiciliario.findAll", query = "SELECT d FROM Domiciliario d")
    , @NamedQuery(name = "Domiciliario.findByIdDomiciliario", query = "SELECT d FROM Domiciliario d WHERE d.idDomiciliario = :idDomiciliario")})
public class Domiciliario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDomiciliario")
    private Integer idDomiciliario;
    @JoinColumn(name = "idEmpresa", referencedColumnName = "idEmpresa")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empresa idEmpresa;
    @JoinColumn(name = "idFicha", referencedColumnName = "idFicha")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private FichaIngreso idFicha;
    @JoinColumn(name = "idPaquete", referencedColumnName = "idPaquete")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Paquete idPaquete;

    public Domiciliario() {
    }

    public Domiciliario(Integer idDomiciliario) {
        this.idDomiciliario = idDomiciliario;
    }

    public Integer getIdDomiciliario() {
        return idDomiciliario;
    }

    public void setIdDomiciliario(Integer idDomiciliario) {
        this.idDomiciliario = idDomiciliario;
    }

    public Empresa getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Empresa idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public FichaIngreso getIdFicha() {
        return idFicha;
    }

    public void setIdFicha(FichaIngreso idFicha) {
        this.idFicha = idFicha;
    }

    public Paquete getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(Paquete idPaquete) {
        this.idPaquete = idPaquete;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDomiciliario != null ? idDomiciliario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Domiciliario)) {
            return false;
        }
        Domiciliario other = (Domiciliario) object;
        if ((this.idDomiciliario == null && other.idDomiciliario != null) || (this.idDomiciliario != null && !this.idDomiciliario.equals(other.idDomiciliario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Domiciliario[ idDomiciliario=" + idDomiciliario + " ]";
    }
    
}
