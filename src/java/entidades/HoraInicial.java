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
 * @author User
 */
@Entity
@Table(name = "hora_inicial")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HoraInicial.findAll", query = "SELECT h FROM HoraInicial h")
    , @NamedQuery(name = "HoraInicial.findByIdHora", query = "SELECT h FROM HoraInicial h WHERE h.idHora = :idHora")
    , @NamedQuery(name = "HoraInicial.findByHora", query = "SELECT h FROM HoraInicial h WHERE h.hora = :hora")})
public class HoraInicial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idHora")
    private Integer idHora;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "horaInicio", fetch = FetchType.LAZY)
    private List<Evento> eventoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "horaInicialReserva", fetch = FetchType.LAZY)
    private List<Disponibilidad> disponibilidadList;

    public HoraInicial() {
    }

    public HoraInicial(Integer idHora) {
        this.idHora = idHora;
    }

    public HoraInicial(Integer idHora, Date hora) {
        this.idHora = idHora;
        this.hora = hora;
    }

    public Integer getIdHora() {
        return idHora;
    }

    public void setIdHora(Integer idHora) {
        this.idHora = idHora;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    @XmlTransient
    public List<Evento> getEventoList() {
        return eventoList;
    }

    public void setEventoList(List<Evento> eventoList) {
        this.eventoList = eventoList;
    }

    @XmlTransient
    public List<Disponibilidad> getDisponibilidadList() {
        return disponibilidadList;
    }

    public void setDisponibilidadList(List<Disponibilidad> disponibilidadList) {
        this.disponibilidadList = disponibilidadList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHora != null ? idHora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HoraInicial)) {
            return false;
        }
        HoraInicial other = (HoraInicial) object;
        if ((this.idHora == null && other.idHora != null) || (this.idHora != null && !this.idHora.equals(other.idHora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.HoraInicial[ idHora=" + idHora + " ]";
    }
    
}
