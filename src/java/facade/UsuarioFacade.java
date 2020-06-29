/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Huertas
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "ProyectoConjuntoWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    public Usuario iniciarSesion(int documento, String contrasenia) {
        try {
            Query query;
            query = em.createQuery("SELECT u FROM Usuario u WHERE u.documento=:documento and u.contrasenia=:contrasenia");
            query.setParameter("documento", documento);
            query.setParameter("contrasenia", contrasenia);
            return (Usuario) query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error en login revisar: " + e.getMessage());
        }
        return null;
    }

    public List<Usuario> sesionUsuario(int idPerfil) {
        Query query;
        query = em.createQuery("SELECT u FROM Usuario u WHERE u.idPerfil=:idPerfil");
        query.setParameter("idPerfil", idPerfil);
        return query.getResultList();
    }

    public List<Usuario> usuarioBloquedo(String estado) {
        Query query;
        query = em.createQuery("SELECT u FROM Usuario u WHERE u.estado=:estado");
        query.setParameter("estado", estado);
        return query.getResultList();
    }

    public int contarBloqueados(String estado) {
        Query query;
        query = em.createQuery("SELECT COUNT(u) FROM Usuario u WHERE u.estado=:estado");
        query.setParameter("estado", estado);
        return ((Long) query.getSingleResult()).intValue();
    }

    public int contarAdministradores(int idRol) {
        Query query;
        query = em.createQuery("SELECT COUNT(u) FROM Usuario u WHERE u.idRol.idRol=:idRol");
        query.setParameter("idRol", idRol);
        return ((Long) query.getSingleResult()).intValue();
    }
    
    public List<Usuario> usuarioAdmin(int idRol){
        Query query;
        query = em.createQuery("SELECT u FROM Usuario u WHERE u.idRol.idRol=:idRol");
        query.setParameter("idRol", idRol);
        return query.getResultList();
    }
}
