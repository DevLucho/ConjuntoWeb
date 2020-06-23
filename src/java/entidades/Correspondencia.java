/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Huertas
 */
@Entity
@Table(name = "correspondencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Correspondencia.findAll", query = "SELECT c FROM Correspondencia c")
    , @NamedQuery(name = "Correspondencia.findByIdCorrespondencia", query = "SELECT c FROM Correspondencia c WHERE c.idCorrespondencia = :idCorrespondencia")
    , @NamedQuery(name = "Correspondencia.findByDescripcion", query = "SELECT c FROM Correspondencia c WHERE c.descripcion = :descripcion")
    , @NamedQuery(name = "Correspondencia.findByFechaIngreso", query = "SELECT c FROM Correspondencia c WHERE c.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "Correspondencia.findByFechaSalida", query = "SELECT c FROM Correspondencia c WHERE c.fechaSalida = :fechaSalida")
    , @NamedQuery(name = "Correspondencia.findByEstado", query = "SELECT c FROM Correspondencia c WHERE c.estado = :estado")})
public class Correspondencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCorrespondencia")
    private Integer idCorrespondencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaIngreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    @Column(name = "fechaSalida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSalida;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "idInmueble", referencedColumnName = "idInmueble")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Inmueble idInmueble;
    @JoinColumn(name = "idEmpresa", referencedColumnName = "idEmpresa")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empresa idEmpresa;
    @JoinColumn(name = "idPaquete", referencedColumnName = "idPaquete")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Paquete idPaquete;
    @JoinColumn(name = "idVigilante", referencedColumnName = "idVigilante")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Vigilante idVigilante;

    public Correspondencia() {
    }

    public Correspondencia(Integer idCorrespondencia) {
        this.idCorrespondencia = idCorrespondencia;
    }

    public Correspondencia(Integer idCorrespondencia, String descripcion, Date fechaIngreso, String estado) {
        this.idCorrespondencia = idCorrespondencia;
        this.descripcion = descripcion;
        this.fechaIngreso = fechaIngreso;
        this.estado = estado;
    }

    public Integer getIdCorrespondencia() {
        return idCorrespondencia;
    }

    public void setIdCorrespondencia(Integer idCorrespondencia) {
        this.idCorrespondencia = idCorrespondencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Inmueble getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(Inmueble idInmueble) {
        this.idInmueble = idInmueble;
    }

    public Empresa getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Empresa idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Paquete getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(Paquete idPaquete) {
        this.idPaquete = idPaquete;
    }

    public Vigilante getIdVigilante() {
        return idVigilante;
    }

    public void setIdVigilante(Vigilante idVigilante) {
        this.idVigilante = idVigilante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCorrespondencia != null ? idCorrespondencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Correspondencia)) {
            return false;
        }
        Correspondencia other = (Correspondencia) object;
        if ((this.idCorrespondencia == null && other.idCorrespondencia != null) || (this.idCorrespondencia != null && !this.idCorrespondencia.equals(other.idCorrespondencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Correspondencia[ idCorrespondencia=" + idCorrespondencia + " ]";
    }
    
}
