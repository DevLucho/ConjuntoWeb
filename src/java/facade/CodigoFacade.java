/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.Codigo;
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
public class CodigoFacade extends AbstractFacade<Codigo> {

    @PersistenceContext(unitName = "ProyectoConjuntoWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CodigoFacade() {
        super(Codigo.class);
    }

    public Codigo validarCodigo(String codigo) {
        Codigo c = new Codigo();
        try {
            Query query;
            query = em.createQuery("SELECT u FROM Codigo u WHERE u.codigo=:codigo");
            query.setParameter("codigo", codigo);
            c = (Codigo) query.getResultList().get(0);
        } catch (Exception e) {
            System.out.println("Error en codigo revisar: " + e.getMessage());
        }
        return c;
    }

    public Usuario recuperarPass(String correo) {
        Usuario usuario = new Usuario();
        try {
            Query query;
            query = em.createQuery("SELECT u FROM Usuario u WHERE u.correo=:correo");
            query.setParameter("correo", correo);
            usuario = (Usuario) query.getResultList().get(0);
        } catch (Exception e) {
            System.out.println("Error en recuperar pass revisar: " + e.getMessage());
        }
        return usuario;
    }
    
    public List<Usuario> cambiarPass(int idPerfil) {
        Query query;
        query = em.createQuery("SELECT u FROM Usuario u WHERE u.idPerfil=:idPerfil");
        query.setParameter("idPerfil", idPerfil);
        return query.getResultList();
    }
}
