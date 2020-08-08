/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.Residente;
import entidades.Usuario;
import entidades.Vigilante;
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

    public Usuario validarDocumento(int documento) {
        Usuario usuario = new Usuario();
        try {
            Query query;
            query = em.createQuery("SELECT u FROM Usuario u WHERE u.documento=:documento");
            query.setParameter("documento", documento);
            usuario = (Usuario) query.getResultList().get(0);
        } catch (Exception e) {
            System.out.println("Error en documento revisar: " + e.getMessage());
        }
        return usuario;
    }

    public Usuario iniciarSesion(int documento, String contrasenia) {
        Usuario usuario = new Usuario();
        try {
            Query query;
            query = em.createQuery("SELECT u FROM Usuario u WHERE u.documento=:documento and u.contrasenia=:contrasenia");
            query.setParameter("documento", documento);
            query.setParameter("contrasenia", contrasenia);
            usuario = (Usuario) query.getResultList().get(0);
        } catch (Exception e) {
            System.out.println("Error en login revisar: " + e.getMessage());
        }
        return usuario;
    }

    public Usuario cambiarContrasenia(String contrasenia) {
        Usuario usuario = new Usuario();
        try {
            Query query;
            query = em.createQuery("SELECT u FROM Usuario u WHERE u.contrasenia=:contrasenia");
            query.setParameter("contrasenia", contrasenia);
            usuario = (Usuario) query.getResultList().get(0);
        } catch (Exception e) {
            System.out.println("Error en cambiar contrasenia revisar: " + e.getMessage());
        }
        return usuario;
    }

    public List<Usuario> sesionUsuario(int idPerfil) {
        Query query;
        query = em.createQuery("SELECT u FROM Usuario u WHERE u.idPerfil=:idPerfil");
        query.setParameter("idPerfil", idPerfil);
        return query.getResultList();
    }

    public List<Residente> sesionUsuarioR(int idPerfil) {
        Query query;
        query = em.createQuery("SELECT u FROM Residente u WHERE u.idPerfil.idPerfil=:idPerfil");
        query.setParameter("idPerfil", idPerfil);
        return query.getResultList();
    }

    public List<Vigilante> sesionUsuarioV(int idPerfil) {
        Query query;
        query = em.createQuery("SELECT u FROM Vigilante u WHERE u.idPerfil.idPerfil=:idPerfil");
        query.setParameter("idPerfil", idPerfil);
        return query.getResultList();
    }

    public List<Usuario> usuarioBloquedo(String estado) {
        Query query;
        query = em.createQuery("SELECT u FROM Usuario u WHERE u.estado=:estado");
        query.setParameter("estado", estado);
        return query.getResultList();
    }
    
    // count

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

    public List<Usuario> usuarioAdmin(int idRol) {
        Query query;
        query = em.createQuery("SELECT u FROM Usuario u WHERE u.idRol.idRol=:idRol");
        query.setParameter("idRol", idRol);
        return query.getResultList();
    }
}
