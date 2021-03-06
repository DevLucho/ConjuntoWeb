/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.Vehiculo;
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
public class VehiculoFacade extends AbstractFacade<Vehiculo> {

    @PersistenceContext(unitName = "ProyectoConjuntoWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VehiculoFacade() {
        super(Vehiculo.class);
    }

    public List<Vehiculo> vehiculoVisitante() {
        Query query;
        query = em.createQuery("SELECT a FROM Vehiculo a WHERE a.idResidente is null");
        return query.getResultList();
    }

    public List<Vehiculo> vehiculoResidente(int idResidente) {
        Query query;
        query = em.createQuery("SELECT u FROM Vehiculo u WHERE u.idResidente.idResidente=:idResidente");
        query.setParameter("idResidente", idResidente);
        return query.getResultList();
    }

    public List<Vehiculo> vehiculoResidente() {
        Query query;
        query = em.createQuery("SELECT a FROM Vehiculo a WHERE a.idVisitante is null");
        return query.getResultList();
    }

    // count

    public int contarVehiculoR(int idResidente) {
        Query query;
        query = em.createQuery("SELECT COUNT(u) FROM Vehiculo u WHERE u.idResidente.idResidente=:idResidente");
        query.setParameter("idResidente", idResidente);
        return ((Long) query.getSingleResult()).intValue();
    }

    public int contarVehiculoV() {
        Query query;
        query = em.createQuery("SELECT COUNT(u) FROM Vehiculo u WHERE u.idResidente is null");
        return ((Long) query.getSingleResult()).intValue();
    }

    public int contarVehiculoR() {
        Query query;
        query = em.createQuery("SELECT COUNT(u) FROM Vehiculo u WHERE u.idVisitante is null");
        return ((Long) query.getSingleResult()).intValue();
    }
}
