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
import javax.persistence.Lob;
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
@Table(name = "respuesta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Respuesta.findAll", query = "SELECT r FROM Respuesta r")
    , @NamedQuery(name = "Respuesta.findByIdRespuesta", query = "SELECT r FROM Respuesta r WHERE r.idRespuesta = :idRespuesta")
    , @NamedQuery(name = "Respuesta.findByFecha", query = "SELECT r FROM Respuesta r WHERE r.fecha = :fecha")
    , @NamedQuery(name = "Respuesta.findByHora", query = "SELECT r FROM Respuesta r WHERE r.hora = :hora")})
public class Respuesta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_respuesta")
    private Integer idRespuesta;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "respuesta")
    private String respuesta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @JoinColumn(name = "nroRadicado", referencedColumnName = "nroRadicado")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pqrs nroRadicado;
    @JoinColumn(name = "idPerfil", referencedColumnName = "idPerfil")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario idPerfil;

    public Respuesta() {
    }

    public Respuesta(Integer idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public Respuesta(Integer idRespuesta, String respuesta, Date fecha, Date hora) {
        this.idRespuesta = idRespuesta;
        this.respuesta = respuesta;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Integer getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(Integer idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
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

    public Pqrs getNroRadicado() {
        return nroRadicado;
    }

    public void setNroRadicado(Pqrs nroRadicado) {
        this.nroRadicado = nroRadicado;
    }

    public Usuario getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Usuario idPerfil) {
        this.idPerfil = idPerfil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRespuesta != null ? idRespuesta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Respuesta)) {
            return false;
        }
        Respuesta other = (Respuesta) object;
        if ((this.idRespuesta == null && other.idRespuesta != null) || (this.idRespuesta != null && !this.idRespuesta.equals(other.idRespuesta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Respuesta[ idRespuesta=" + idRespuesta + " ]";
    }
    
}
