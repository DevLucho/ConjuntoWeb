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
@Table(name = "ficha_ingreso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FichaIngreso.findAll", query = "SELECT f FROM FichaIngreso f")
    , @NamedQuery(name = "FichaIngreso.findByIdFicha", query = "SELECT f FROM FichaIngreso f WHERE f.idFicha = :idFicha")
    , @NamedQuery(name = "FichaIngreso.findByNombre", query = "SELECT f FROM FichaIngreso f WHERE f.nombre = :nombre")
    , @NamedQuery(name = "FichaIngreso.findByApellido", query = "SELECT f FROM FichaIngreso f WHERE f.apellido = :apellido")
    , @NamedQuery(name = "FichaIngreso.findByHoraEntrada", query = "SELECT f FROM FichaIngreso f WHERE f.horaEntrada = :horaEntrada")
    , @NamedQuery(name = "FichaIngreso.findByHoraSalida", query = "SELECT f FROM FichaIngreso f WHERE f.horaSalida = :horaSalida")
    , @NamedQuery(name = "FichaIngreso.findByEstadoFicha", query = "SELECT f FROM FichaIngreso f WHERE f.estadoFicha = :estadoFicha")})
public class FichaIngreso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFicha")
    private Integer idFicha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "horaEntrada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaEntrada;
    @Column(name = "horaSalida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaSalida;
    @Size(max = 8)
    @Column(name = "estadoFicha")
    private String estadoFicha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFicha", fetch = FetchType.LAZY)
    private List<Domiciliario> domiciliarioList;
    @JoinColumn(name = "idInmueble", referencedColumnName = "idInmueble")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Inmueble idInmueble;
    @JoinColumn(name = "idVigilante", referencedColumnName = "idVigilante")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Vigilante idVigilante;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFicha", fetch = FetchType.LAZY)
    private List<Visitante> visitanteList;

    public FichaIngreso() {
    }

    public FichaIngreso(Integer idFicha) {
        this.idFicha = idFicha;
    }

    public FichaIngreso(Integer idFicha, String nombre, String apellido) {
        this.idFicha = idFicha;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Integer getIdFicha() {
        return idFicha;
    }

    public void setIdFicha(Integer idFicha) {
        this.idFicha = idFicha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Date horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Date getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Date horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getEstadoFicha() {
        return estadoFicha;
    }

    public void setEstadoFicha(String estadoFicha) {
        this.estadoFicha = estadoFicha;
    }

    @XmlTransient
    public List<Domiciliario> getDomiciliarioList() {
        return domiciliarioList;
    }

    public void setDomiciliarioList(List<Domiciliario> domiciliarioList) {
        this.domiciliarioList = domiciliarioList;
    }

    public Inmueble getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(Inmueble idInmueble) {
        this.idInmueble = idInmueble;
    }

    public Vigilante getIdVigilante() {
        return idVigilante;
    }

    public void setIdVigilante(Vigilante idVigilante) {
        this.idVigilante = idVigilante;
    }

    @XmlTransient
    public List<Visitante> getVisitanteList() {
        return visitanteList;
    }

    public void setVisitanteList(List<Visitante> visitanteList) {
        this.visitanteList = visitanteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFicha != null ? idFicha.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FichaIngreso)) {
            return false;
        }
        FichaIngreso other = (FichaIngreso) object;
        if ((this.idFicha == null && other.idFicha != null) || (this.idFicha != null && !this.idFicha.equals(other.idFicha))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.FichaIngreso[ idFicha=" + idFicha + " ]";
    }
    
}
