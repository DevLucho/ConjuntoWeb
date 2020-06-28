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
        Usuario usuario = null;
        try {
            Query query;
            query = em.createQuery("SELECT u FROM Usuario u WHERE u.documento=:documento and u.contrasenia=:contrasenia");
            query.setParameter("documento", documento);
            query.setParameter("contrasenia", contrasenia);
            usuario = (Usuario) query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error en login revisar: " + e.getMessage());
        }
        return usuario;
    }

    public List<Usuario> sesionUsuario(int idPerfil) {
        Query query;
        query = em.createQuery("SELECT u FROM Usuario u WHERE u.idPerfil=:idPerfil");
        query.setParameter("idPerfil", idPerfil);
        return query.getResultList();
    }
}
