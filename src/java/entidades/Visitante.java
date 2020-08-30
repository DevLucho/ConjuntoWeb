/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author User
 */
@Entity
@Table(name = "visitante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Visitante.findAll", query = "SELECT v FROM Visitante v")
    , @NamedQuery(name = "Visitante.findByIdVisitante", query = "SELECT v FROM Visitante v WHERE v.idVisitante = :idVisitante")
    , @NamedQuery(name = "Visitante.findByDocumento", query = "SELECT v FROM Visitante v WHERE v.documento = :documento")
    , @NamedQuery(name = "Visitante.findByVehiculo", query = "SELECT v FROM Visitante v WHERE v.vehiculo = :vehiculo")})
public class Visitante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idVisitante")
    private Integer idVisitante;
    @Basic(optional = false)
    @NotNull
    @Column(name = "documento")
    private int documento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "vehiculo")
    private String vehiculo;
    @OneToMany(mappedBy = "idVisitante", fetch = FetchType.LAZY)
    private List<Vehiculo> vehiculoList;
    @JoinColumn(name = "idFicha", referencedColumnName = "idFicha")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private FichaIngreso idFicha;

    public Visitante() {
    }

    public Visitante(Integer idVisitante) {
        this.idVisitante = idVisitante;
    }

    public Visitante(Integer idVisitante, int documento, String vehiculo) {
        this.idVisitante = idVisitante;
        this.documento = documento;
        this.vehiculo = vehiculo;
    }

    public Integer getIdVisitante() {
        return idVisitante;
    }

    public void setIdVisitante(Integer idVisitante) {
        this.idVisitante = idVisitante;
    }

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    @XmlTransient
    public List<Vehiculo> getVehiculoList() {
        return vehiculoList;
    }

    public void setVehiculoList(List<Vehiculo> vehiculoList) {
        this.vehiculoList = vehiculoList;
    }

    public FichaIngreso getIdFicha() {
        return idFicha;
    }

    public void setIdFicha(FichaIngreso idFicha) {
        this.idFicha = idFicha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVisitante != null ? idVisitante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Visitante)) {
            return false;
        }
        Visitante other = (Visitante) object;
        if ((this.idVisitante == null && other.idVisitante != null) || (this.idVisitante != null && !this.idVisitante.equals(other.idVisitante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Visitante[ idVisitante=" + idVisitante + " ]";
    }
    
}
