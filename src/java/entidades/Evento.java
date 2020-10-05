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
@Table(name = "evento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evento.findAll", query = "SELECT e FROM Evento e")
    , @NamedQuery(name = "Evento.findByIdEvento", query = "SELECT e FROM Evento e WHERE e.idEvento = :idEvento")
    , @NamedQuery(name = "Evento.findByTitulo", query = "SELECT e FROM Evento e WHERE e.titulo = :titulo")
    , @NamedQuery(name = "Evento.findByFechaInicio", query = "SELECT e FROM Evento e WHERE e.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Evento.findByFechaFin", query = "SELECT e FROM Evento e WHERE e.fechaFin = :fechaFin")
    , @NamedQuery(name = "Evento.findByDetalle", query = "SELECT e FROM Evento e WHERE e.detalle = :detalle")
    , @NamedQuery(name = "Evento.findByOrganizador", query = "SELECT e FROM Evento e WHERE e.organizador = :organizador")
    , @NamedQuery(name = "Evento.findByEstado", query = "SELECT e FROM Evento e WHERE e.estado = :estado")
    , @NamedQuery(name = "Evento.findByImg", query = "SELECT e FROM Evento e WHERE e.img = :img")})
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEvento")
    private Integer idEvento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fechaFin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "detalle")
    private String detalle;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "organizador")
    private String organizador;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "estado")
    private String estado;
    @Size(max = 100)
    @Column(name = "img")
    private String img;
    @JoinColumn(name = "horaInicio", referencedColumnName = "idHora")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private HoraInicial horaInicio;
    @JoinColumn(name = "horaFin", referencedColumnName = "idHora")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private HoraFinal horaFin;
    @JoinColumn(name = "idZonaComunal", referencedColumnName = "idZonaComunal")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ZonaComunal idZonaComunal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEvento", fetch = FetchType.LAZY)
    private List<Inscripcion> inscripcionList;

    public Evento() {
    }

    public Evento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Evento(Integer idEvento, String titulo, Date fechaInicio, String detalle, String organizador, String estado) {
        this.idEvento = idEvento;
        this.titulo = titulo;
        this.fechaInicio = fechaInicio;
        this.detalle = detalle;
        this.organizador = organizador;
        this.estado = estado;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getOrganizador() {
        return organizador;
    }

    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public HoraInicial getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(HoraInicial horaInicio) {
        this.horaInicio = horaInicio;
    }

    public HoraFinal getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(HoraFinal horaFin) {
        this.horaFin = horaFin;
    }

    public ZonaComunal getIdZonaComunal() {
        return idZonaComunal;
    }

    public void setIdZonaComunal(ZonaComunal idZonaComunal) {
        this.idZonaComunal = idZonaComunal;
    }

    @XmlTransient
    public List<Inscripcion> getInscripcionList() {
        return inscripcionList;
    }

    public void setInscripcionList(List<Inscripcion> inscripcionList) {
        this.inscripcionList = inscripcionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvento != null ? idEvento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evento)) {
            return false;
        }
        Evento other = (Evento) object;
        if ((this.idEvento == null && other.idEvento != null) || (this.idEvento != null && !this.idEvento.equals(other.idEvento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Evento[ idEvento=" + idEvento + " ]";
    }
    
}
