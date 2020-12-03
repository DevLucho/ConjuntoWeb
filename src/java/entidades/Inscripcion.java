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
@Table(name = "inscripcion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inscripcion.findAll", query = "SELECT i FROM Inscripcion i")
    , @NamedQuery(name = "Inscripcion.findByCodInscripcion", query = "SELECT i FROM Inscripcion i WHERE i.codInscripcion = :codInscripcion")
    , @NamedQuery(name = "Inscripcion.findByFechaInscripcion", query = "SELECT i FROM Inscripcion i WHERE i.fechaInscripcion = :fechaInscripcion")
    , @NamedQuery(name = "Inscripcion.findByEstado", query = "SELECT i FROM Inscripcion i WHERE i.estado = :estado")})
public class Inscripcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CodInscripcion")
    private Integer codInscripcion;
    @Column(name = "fechaInscripcion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInscripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "idResidente", referencedColumnName = "idResidente")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Residente idResidente;
    @JoinColumn(name = "idEvento", referencedColumnName = "idEvento")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Evento idEvento;

    public Inscripcion() {
    }

    public Inscripcion(Integer codInscripcion) {
        this.codInscripcion = codInscripcion;
    }

    public Inscripcion(Integer codInscripcion, String estado) {
        this.codInscripcion = codInscripcion;
        this.estado = estado;
    }

    public Integer getCodInscripcion() {
        return codInscripcion;
    }

    public void setCodInscripcion(Integer codInscripcion) {
        this.codInscripcion = codInscripcion;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Residente getIdResidente() {
        return idResidente;
    }

    public void setIdResidente(Residente idResidente) {
        this.idResidente = idResidente;
    }

    public Evento getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Evento idEvento) {
        this.idEvento = idEvento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codInscripcion != null ? codInscripcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inscripcion)) {
            return false;
        }
        Inscripcion other = (Inscripcion) object;
        if ((this.codInscripcion == null && other.codInscripcion != null) || (this.codInscripcion != null && !this.codInscripcion.equals(other.codInscripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Inscripcion[ codInscripcion=" + codInscripcion + " ]";
    }
    
}
