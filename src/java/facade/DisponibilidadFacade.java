/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.Disponibilidad;
import entidades.ZonaComunal;
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
public class DisponibilidadFacade extends AbstractFacade<Disponibilidad> {

    @PersistenceContext(unitName = "ProyectoConjuntoWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DisponibilidadFacade() {
        super(Disponibilidad.class);
    }

    /*
    public List<Disponibilidad> consultarZonaComunal(ZonaComunal idZonaComunal) {
        Query query;
        query = em.createQuery("SELECT DISTINCT c.idZonaComunal FROM Disponibilidad c WHERE c.idZonaComunal=:idZonaComunal");
        query.setParameter("idZonaComunal", idZonaComunal);
        return query.getResultList();
    }*/
    public List<Disponibilidad> consultarHoraMaxMin(int idZonaComunal) {
        Query query;
        query = em.createQuery("SELECT u FROM Disponibilidad u WHERE u.idZonaComunal.idZonaComunal=:idZonaComunal");
        query.setParameter("idZonaComunal", idZonaComunal);
        return query.getResultList();
    }

    public List<Disponibilidad> consultarDisponibilidadZona(int idZonaComunal) {
        Query query;
        query = em.createQuery("SELECT u FROM Disponibilidad u WHERE u.idZonaComunal.idZonaComunal=:idZonaComunal");
        query.setParameter("idZonaComunal", idZonaComunal);
        return query.getResultList();
    }

    public Disponibilidad ctlaSingleDisponilidad(int idZonaComunal) {
        Query query = em.createQuery("SELECT u FROM Disponibilidad u WHERE u.idZonaComunal.idZonaComunal=:idZonaComunal");
        query.setParameter("idZonaComunal", idZonaComunal);
        query.setFirstResult(0);
        query.setMaxResults(1);
        return (Disponibilidad) query.getSingleResult();
    }

    public void eliminarZona(ZonaComunal idZonaComunal) {
        Query query;
        query = em.createQuery("DELETE FROM Disponibilidad u WHERE u.idZonaComunal=:idZonaComunal");
        query.setParameter("idZonaComunal", idZonaComunal);
        query.executeUpdate();
    }

}
