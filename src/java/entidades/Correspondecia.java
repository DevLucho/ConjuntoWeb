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
@Table(name = "correspondecia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Correspondecia.findAll", query = "SELECT c FROM Correspondecia c")
    , @NamedQuery(name = "Correspondecia.findByIdCorrespondencia", query = "SELECT c FROM Correspondecia c WHERE c.idCorrespondencia = :idCorrespondencia")
    , @NamedQuery(name = "Correspondecia.findByDescripcion", query = "SELECT c FROM Correspondecia c WHERE c.descripcion = :descripcion")
    , @NamedQuery(name = "Correspondecia.findByFechaIngreso", query = "SELECT c FROM Correspondecia c WHERE c.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "Correspondecia.findByFechaSalida", query = "SELECT c FROM Correspondecia c WHERE c.fechaSalida = :fechaSalida")})
public class Correspondecia implements Serializable {

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
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaSalida")
    @Temporal(TemporalType.DATE)
    private Date fechaSalida;
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

    public Correspondecia() {
    }

    public Correspondecia(Integer idCorrespondencia) {
        this.idCorrespondencia = idCorrespondencia;
    }

    public Correspondecia(Integer idCorrespondencia, String descripcion, Date fechaIngreso, Date fechaSalida) {
        this.idCorrespondencia = idCorrespondencia;
        this.descripcion = descripcion;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
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
        if (!(object instanceof Correspondecia)) {
            return false;
        }
        Correspondecia other = (Correspondecia) object;
        if ((this.idCorrespondencia == null && other.idCorrespondencia != null) || (this.idCorrespondencia != null && !this.idCorrespondencia.equals(other.idCorrespondencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Correspondecia[ idCorrespondencia=" + idCorrespondencia + " ]";
    }
    
}
