/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Huertas
 */
@Entity
@Table(name = "comunicado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comunicado.findAll", query = "SELECT c FROM Comunicado c")
    , @NamedQuery(name = "Comunicado.findByIdComunicado", query = "SELECT c FROM Comunicado c WHERE c.idComunicado = :idComunicado")
    , @NamedQuery(name = "Comunicado.findByTitulo", query = "SELECT c FROM Comunicado c WHERE c.titulo = :titulo")
    , @NamedQuery(name = "Comunicado.findByDescripcion", query = "SELECT c FROM Comunicado c WHERE c.descripcion = :descripcion")
    , @NamedQuery(name = "Comunicado.findByImg", query = "SELECT c FROM Comunicado c WHERE c.img = :img")})
public class Comunicado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_comunicado")
    private Integer idComunicado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 100)
    @Column(name = "img")
    private String img;
    @JoinColumn(name = "idPerfil", referencedColumnName = "idPerfil")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario idPerfil;

    public Comunicado() {
    }

    public Comunicado(Integer idComunicado) {
        this.idComunicado = idComunicado;
    }

    public Comunicado(Integer idComunicado, String titulo, String descripcion) {
        this.idComunicado = idComunicado;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public Integer getIdComunicado() {
        return idComunicado;
    }

    public void setIdComunicado(Integer idComunicado) {
        this.idComunicado = idComunicado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
        hash += (idComunicado != null ? idComunicado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comunicado)) {
            return false;
        }
        Comunicado other = (Comunicado) object;
        if ((this.idComunicado == null && other.idComunicado != null) || (this.idComunicado != null && !this.idComunicado.equals(other.idComunicado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Comunicado[ idComunicado=" + idComunicado + " ]";
    }
    
}
