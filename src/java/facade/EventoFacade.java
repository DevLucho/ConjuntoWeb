/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.Evento;
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
public class EventoFacade extends AbstractFacade<Evento> {

    @PersistenceContext(unitName = "ProyectoConjuntoWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventoFacade() {
        super(Evento.class);
    }

    public List<Evento> eventos(String estado) {
        Query query;
        query = em.createQuery("SELECT u FROM Evento u WHERE u.estado=:estado ORDER BY u.idEvento DESC");
        query.setParameter("estado", estado);
        query.setMaxResults(3);
        return query.getResultList();
    }

    public List<Evento> estadoEvento(String estado) {
        Query query;
        query = em.createQuery("SELECT u FROM Evento u WHERE u.estado=:estado ORDER BY u.idEvento DESC");
        query.setParameter("estado", estado);
        return query.getResultList();
    }

    public int countEstado(String estado) {
        Query query;
        query = em.createQuery("SELECT COUNT(u) FROM Evento u WHERE u.estado=:estado");
        query.setParameter("estado", estado);
        return ((Long) query.getSingleResult()).intValue();
    }

    // coreos masivos
    public List<Usuario> correos(int idRol) {
        Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.idRol.idRol=:idRol");
        query.setParameter("idRol", idRol);
        return query.getResultList();
    }

    public List<Usuario> allMails() {
        Query query = em.createQuery("SELECT u FROM Usuario u");
        return query.getResultList();
    }
}
