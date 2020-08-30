/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author User
 */
@Entity
@Table(name = "pqrs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pqrs.findAll", query = "SELECT p FROM Pqrs p")
    , @NamedQuery(name = "Pqrs.findByIdPqrs", query = "SELECT p FROM Pqrs p WHERE p.idPqrs = :idPqrs")
    , @NamedQuery(name = "Pqrs.findByNroRadicado", query = "SELECT p FROM Pqrs p WHERE p.nroRadicado = :nroRadicado")
    , @NamedQuery(name = "Pqrs.findByAsunto", query = "SELECT p FROM Pqrs p WHERE p.asunto = :asunto")
    , @NamedQuery(name = "Pqrs.findByFecha", query = "SELECT p FROM Pqrs p WHERE p.fecha = :fecha")
    , @NamedQuery(name = "Pqrs.findByHora", query = "SELECT p FROM Pqrs p WHERE p.hora = :hora")
    , @NamedQuery(name = "Pqrs.findByDescripcion", query = "SELECT p FROM Pqrs p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Pqrs.findByAdjunto", query = "SELECT p FROM Pqrs p WHERE p.adjunto = :adjunto")
    , @NamedQuery(name = "Pqrs.findByEstado", query = "SELECT p FROM Pqrs p WHERE p.estado = :estado")})
public class Pqrs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pqrs")
    private Integer idPqrs;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nroRadicado")
    private String nroRadicado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "asunto")
    private String asunto;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 100)
    @Column(name = "adjunto")
    private String adjunto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "idResidente", referencedColumnName = "idResidente")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Residente idResidente;
    @JoinColumn(name = "idTipoPqrs", referencedColumnName = "idTipoPqrs")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoPqrs idTipoPqrs;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPqrs", fetch = FetchType.LAZY)
    private List<Respuesta> respuestaList;

    public Pqrs() {
    }

    public Pqrs(Integer idPqrs) {
        this.idPqrs = idPqrs;
    }

    public Pqrs(Integer idPqrs, String nroRadicado, String asunto, String descripcion, String estado) {
        this.idPqrs = idPqrs;
        this.nroRadicado = nroRadicado;
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Integer getIdPqrs() {
        return idPqrs;
    }

    public void setIdPqrs(Integer idPqrs) {
        this.idPqrs = idPqrs;
    }

    public String getNroRadicado() {
        return nroRadicado;
    }

    public void setNroRadicado(String nroRadicado) {
        this.nroRadicado = nroRadicado;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(String adjunto) {
        this.adjunto = adjunto;
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

    public TipoPqrs getIdTipoPqrs() {
        return idTipoPqrs;
    }

    public void setIdTipoPqrs(TipoPqrs idTipoPqrs) {
        this.idTipoPqrs = idTipoPqrs;
    }

    @XmlTransient
    public List<Respuesta> getRespuestaList() {
        return respuestaList;
    }

    public void setRespuestaList(List<Respuesta> respuestaList) {
        this.respuestaList = respuestaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPqrs != null ? idPqrs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pqrs)) {
            return false;
        }
        Pqrs other = (Pqrs) object;
        if ((this.idPqrs == null && other.idPqrs != null) || (this.idPqrs != null && !this.idPqrs.equals(other.idPqrs))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Pqrs[ idPqrs=" + idPqrs + " ]";
    }
    
}
