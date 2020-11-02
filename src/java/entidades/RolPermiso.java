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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Huertas
 */
@Entity
@Table(name = "rol_permiso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RolPermiso.findAll", query = "SELECT r FROM RolPermiso r")
    , @NamedQuery(name = "RolPermiso.findByIdRolPermiso", query = "SELECT r FROM RolPermiso r WHERE r.idRolPermiso = :idRolPermiso")
    , @NamedQuery(name = "RolPermiso.findByEstado", query = "SELECT r FROM RolPermiso r WHERE r.estado = :estado")})
public class RolPermiso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRolPermiso")
    private Integer idRolPermiso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "idRol", referencedColumnName = "idRol")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Rol idRol;
    @JoinColumn(name = "idPermiso", referencedColumnName = "idPermiso")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Permiso idPermiso;

    public RolPermiso() {
    }

    public RolPermiso(Integer idRolPermiso) {
        this.idRolPermiso = idRolPermiso;
    }

    public RolPermiso(Integer idRolPermiso, String estado) {
        this.idRolPermiso = idRolPermiso;
        this.estado = estado;
    }

    public Integer getIdRolPermiso() {
        return idRolPermiso;
    }

    public void setIdRolPermiso(Integer idRolPermiso) {
        this.idRolPermiso = idRolPermiso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Rol getIdRol() {
        return idRol;
    }

    public void setIdRol(Rol idRol) {
        this.idRol = idRol;
    }

    public Permiso getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Permiso idPermiso) {
        this.idPermiso = idPermiso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRolPermiso != null ? idRolPermiso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolPermiso)) {
            return false;
        }
        RolPermiso other = (RolPermiso) object;
        if ((this.idRolPermiso == null && other.idRolPermiso != null) || (this.idRolPermiso != null && !this.idRolPermiso.equals(other.idRolPermiso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.RolPermiso[ idRolPermiso=" + idRolPermiso + " ]";
    }
    
}
