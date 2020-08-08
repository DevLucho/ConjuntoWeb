/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.Pqrs;
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
public class PqrsFacade extends AbstractFacade<Pqrs> {

    @PersistenceContext(unitName = "ProyectoConjuntoWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PqrsFacade() {
        super(Pqrs.class);
    }

    public List<Pqrs> estadoPqrs(String estado) {
        Query query;
        query = em.createQuery("SELECT u FROM Pqrs u WHERE u.estado=:estado");
        query.setParameter("estado", estado);
        return query.getResultList();
    }

    public List<Pqrs> estadoPqrsR(String estado, int idResidente) {
        Query query;
        query = em.createQuery("SELECT u FROM Pqrs u WHERE u.estado=:estado AND u.idResidente.idResidente=:idResidente");
        query.setParameter("estado", estado);
        query.setParameter("idResidente", idResidente);
        return query.getResultList();
    }

    public List<Pqrs> pqrsResidente(int idResidente) {
        Query query;
        query = em.createQuery("SELECT u FROM Pqrs u WHERE u.idResidente.idResidente=:idResidente");
        query.setParameter("idResidente", idResidente);
        return query.getResultList();
    }

    // count
    
    public int countEstadoR(String estado, int idResidente) {
        Query query;
        query = em.createQuery("SELECT COUNT(u) FROM Pqrs u WHERE u.estado=:estado AND u.idResidente.idResidente=:idResidente");
        query.setParameter("estado", estado);
        query.setParameter("idResidente", idResidente);
        return ((Long) query.getSingleResult()).intValue();
    }

    public int countEstado(String estado) {
        Query query;
        query = em.createQuery("SELECT COUNT(u) FROM Pqrs u WHERE u.estado=:estado");
        query.setParameter("estado", estado);
        return ((Long) query.getSingleResult()).intValue();
    }
    
    public int countR(int idResidente) {
        Query query;
        query = em.createQuery("SELECT COUNT(u) FROM Pqrs u WHERE u.idResidente.idResidente=:idResidente");
        query.setParameter("idResidente", idResidente);
        return ((Long) query.getSingleResult()).intValue();
    }

}
