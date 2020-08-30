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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Huertas
 */
@Entity
@Table(name = "turno_vigilante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TurnoVigilante.findAll", query = "SELECT t FROM TurnoVigilante t")
    , @NamedQuery(name = "TurnoVigilante.findByIdTurno", query = "SELECT t FROM TurnoVigilante t WHERE t.idTurno = :idTurno")
    , @NamedQuery(name = "TurnoVigilante.findByInicioTurno", query = "SELECT t FROM TurnoVigilante t WHERE t.inicioTurno = :inicioTurno")
    , @NamedQuery(name = "TurnoVigilante.findByFinTurno", query = "SELECT t FROM TurnoVigilante t WHERE t.finTurno = :finTurno")})
public class TurnoVigilante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTurno")
    private Integer idTurno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "inicioTurno")
    @Temporal(TemporalType.TIME)
    private Date inicioTurno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "finTurno")
    @Temporal(TemporalType.TIME)
    private Date finTurno;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTurno", fetch = FetchType.LAZY)
    private List<Vigilante> vigilanteList;

    public TurnoVigilante() {
    }

    public TurnoVigilante(Integer idTurno) {
        this.idTurno = idTurno;
    }

    public TurnoVigilante(Integer idTurno, Date inicioTurno, Date finTurno) {
        this.idTurno = idTurno;
        this.inicioTurno = inicioTurno;
        this.finTurno = finTurno;
    }

    public Integer getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(Integer idTurno) {
        this.idTurno = idTurno;
    }

    public Date getInicioTurno() {
        return inicioTurno;
    }

    public void setInicioTurno(Date inicioTurno) {
        this.inicioTurno = inicioTurno;
    }

    public Date getFinTurno() {
        return finTurno;
    }

    public void setFinTurno(Date finTurno) {
        this.finTurno = finTurno;
    }

    @XmlTransient
    public List<Vigilante> getVigilanteList() {
        return vigilanteList;
    }

    public void setVigilanteList(List<Vigilante> vigilanteList) {
        this.vigilanteList = vigilanteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTurno != null ? idTurno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TurnoVigilante)) {
            return false;
        }
        TurnoVigilante other = (TurnoVigilante) object;
        if ((this.idTurno == null && other.idTurno != null) || (this.idTurno != null && !this.idTurno.equals(other.idTurno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.TurnoVigilante[ idTurno=" + idTurno + " ]";
    }
    
}
