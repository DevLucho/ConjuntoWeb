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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Huertas
 */
@Entity
@Table(name = "vigilante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vigilante.findAll", query = "SELECT v FROM Vigilante v")
    , @NamedQuery(name = "Vigilante.findByIdVigilante", query = "SELECT v FROM Vigilante v WHERE v.idVigilante = :idVigilante")})
public class Vigilante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idVigilante")
    private Integer idVigilante;
    @JoinColumn(name = "idPerfil", referencedColumnName = "idPerfil")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario idPerfil;
    @JoinColumn(name = "idTurno", referencedColumnName = "idTurno")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TurnoVigilante idTurno;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVigilante", fetch = FetchType.LAZY)
    private List<FichaIngreso> fichaIngresoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVigilante", fetch = FetchType.LAZY)
    private List<Correspondecia> correspondeciaList;

    public Vigilante() {
    }

    public Vigilante(Integer idVigilante) {
        this.idVigilante = idVigilante;
    }

    public Integer getIdVigilante() {
        return idVigilante;
    }

    public void setIdVigilante(Integer idVigilante) {
        this.idVigilante = idVigilante;
    }

    public Usuario getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Usuario idPerfil) {
        this.idPerfil = idPerfil;
    }

    public TurnoVigilante getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(TurnoVigilante idTurno) {
        this.idTurno = idTurno;
    }

    @XmlTransient
    public List<FichaIngreso> getFichaIngresoList() {
        return fichaIngresoList;
    }

    public void setFichaIngresoList(List<FichaIngreso> fichaIngresoList) {
        this.fichaIngresoList = fichaIngresoList;
    }

    @XmlTransient
    public List<Correspondecia> getCorrespondeciaList() {
        return correspondeciaList;
    }

    public void setCorrespondeciaList(List<Correspondecia> correspondeciaList) {
        this.correspondeciaList = correspondeciaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVigilante != null ? idVigilante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vigilante)) {
            return false;
        }
        Vigilante other = (Vigilante) object;
        if ((this.idVigilante == null && other.idVigilante != null) || (this.idVigilante != null && !this.idVigilante.equals(other.idVigilante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Vigilante[ idVigilante=" + idVigilante + " ]";
    }
    
}
