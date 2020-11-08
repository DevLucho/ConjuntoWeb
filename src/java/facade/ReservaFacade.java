/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.Reserva;
import java.util.List;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Huertas
 */
@Stateless
public class ReservaFacade extends AbstractFacade<Reserva> {

    @PersistenceContext(unitName = "ProyectoConjuntoWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReservaFacade() {
        super(Reserva.class);
    }

    public List<Reserva> findDesc() {
        Query query = em.createQuery("SELECT r FROM Reserva r ORDER BY r.idReserva DESC");
        return query.getResultList();
    }

    public List<Reserva> fechasReservadas(Date fecha, int id) {
        Query query = null;
        try {
            query = em.createQuery("SELECT r FROM Reserva r WHERE r.fechaReserva = :fecha AND r.idZonaComunal.idZonaComunal=:id");
            query.setParameter("fecha", fecha);
            query.setParameter("id", id);

        } catch (Exception e) {
            System.out.println("Error consulta fecha r. " + e.getMessage());
        }

        return query.getResultList();
    }

    public int ContarReserva(int id) {
        Query q = em.createQuery("SELECT COUNT(r) FROM Reserva r WHERE r.estado='Reservado' AND r.idZonaComunal.idZonaComunal=:id");
        q.setParameter("id", id);
        return ((Long) q.getSingleResult()).intValue();
    }

    public List<Reserva> buscarUltimaR(int idZonaComunal) {
        Query query = em.createQuery("SELECT r FROM Reserva r WHERE r.idZonaComunal.idZonaComunal=:idZonaComunal AND r.estado='Reservado' ORDER BY r.idReserva DESC");
        query.setParameter("idZonaComunal", idZonaComunal);
        return query.getResultList();
    }

    public List<Reserva> findState(String estado) {
        Query query = em.createQuery("SELECT r FROM Reserva r WHERE r.estado=:estado ORDER BY r.idReserva DESC");
        query.setParameter("estado", estado);
        return query.getResultList();
    }

    public List<Reserva> findStateR(String estado, int idResidente) {
        Query query = em.createQuery("SELECT r FROM Reserva r WHERE r.estado=:estado AND r.idResidente.idResidente=:idResidente ORDER BY r.idReserva DESC");
        query.setParameter("estado", estado);
        query.setParameter("idResidente", idResidente);
        return query.getResultList();
    }

    public List<Reserva> findR(int idResidente) {
        Query query;
        query = em.createQuery("SELECT r FROM Reserva r WHERE r.idResidente.idResidente=:idResidente ORDER BY r.idReserva DESC");
        query.setParameter("idResidente", idResidente);
        return query.getResultList();
    }

    // count
    public int countEstadoR(String estado, int idResidente) {
        Query query = em.createQuery("SELECT COUNT(r) FROM Reserva r WHERE r.estado=:estado AND r.idResidente.idResidente=:idResidente");
        query.setParameter("estado", estado);
        query.setParameter("idResidente", idResidente);
        return ((Long) query.getSingleResult()).intValue();
    }

    public int countEstado(String estado) {
        Query query = em.createQuery("SELECT COUNT(r) FROM Reserva r WHERE r.estado=:estado");
        query.setParameter("estado", estado);
        return ((Long) query.getSingleResult()).intValue();
    }

    public int countR(int idResidente) {
        Query query = em.createQuery("SELECT COUNT(r) FROM Reserva r WHERE r.idResidente.idResidente=:idResidente");
        query.setParameter("idResidente", idResidente);
        return ((Long) query.getSingleResult()).intValue();
    }

}
