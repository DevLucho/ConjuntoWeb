/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.Inscripcion;
import java.util.ArrayList;
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
public class InscripcionFacade extends AbstractFacade<Inscripcion> {

    @PersistenceContext(unitName = "ProyectoConjuntoWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InscripcionFacade() {
        super(Inscripcion.class);
    }

    public List<Inscripcion> inscrito(int idEvento, int idResidente, String estado) {
        System.out.println(idEvento + " " + idResidente + " " + estado);
        Query query = em.createQuery("SELECT u FROM Inscripcion u WHERE u.idEvento.idEvento= :idEvento AND u.idResidente.idResidente= :idResidente AND u.estado=:estado");
        query.setParameter("idEvento", idEvento);
        query.setParameter("idResidente", idResidente);
        query.setParameter("estado", estado);
        return query.getResultList();
    }

    public List<Inscripcion> inscripcionVigente(String estado) {
        Query query;
        query = em.createQuery("SELECT u FROM Inscripcion u WHERE u.estado=:estado");
        query.setParameter("estado", estado);
        return query.getResultList();
    }

    public List<Inscripcion> estadoInscripcionR(String estado, int idResidente) {
        Query query;
        query = em.createQuery("SELECT u FROM Inscripcion u WHERE u.estado=:estado AND u.idResidente.idResidente=:idResidente");
        query.setParameter("estado", estado);
        query.setParameter("idResidente", idResidente);
        return query.getResultList();
    }

    public List<Inscripcion> inscripcionCancelada(String estado) {
        Query query;
        query = em.createQuery("SELECT u FROM Inscripcion u WHERE u.estado=:estado");
        query.setParameter("estado", estado);
        return query.getResultList();
    }

    public List<Inscripcion> inscripcionResidente(int idResidente) {
        Query query;
        query = em.createQuery("SELECT u FROM Inscripcion u WHERE u.idResidente.idResidente=:idResidente");
        query.setParameter("idResidente", idResidente);
        return query.getResultList();
    }

    public int contarEstadoR(String estado, int idResidente) {
        Query query;
        query = em.createQuery("SELECT COUNT(u) FROM Inscripcion u WHERE u.estado=:estado AND u.idResidente.idResidente=:idResidente");
        query.setParameter("estado", estado);
        query.setParameter("idResidente", idResidente);
        return ((Long) query.getSingleResult()).intValue();
    }

    public int contarR(int idResidente) {
        Query query;
        query = em.createQuery("SELECT COUNT(u) FROM Inscripcion u WHERE u.idResidente.idResidente=:idResidente");
        query.setParameter("idResidente", idResidente);
        return ((Long) query.getSingleResult()).intValue();
    }

    public int contarVigentes(String estado) {
        Query query;
        query = em.createQuery("SELECT COUNT(u) FROM Inscripcion u WHERE u.estado=:estado");
        query.setParameter("estado", estado);
        return ((Long) query.getSingleResult()).intValue();
    }

    public int contarCanceladas(String estado) {
        Query query;
        query = em.createQuery("SELECT COUNT(u) FROM Inscripcion u WHERE u.estado=:estado");
        query.setParameter("estado", estado);
        return ((Long) query.getSingleResult()).intValue();
    }
}
