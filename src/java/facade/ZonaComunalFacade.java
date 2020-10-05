/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.Pqrs;
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
public class ZonaComunalFacade extends AbstractFacade<ZonaComunal> {

    @PersistenceContext(unitName = "ProyectoConjuntoWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    public String max() {
        Query query;
        query = em.createQuery("SELECT X.zona FROM (SELECT z.nombre AS zona, COUNT(r.idReserva) AS cuenta FROM reserva r JOIN zona_comunal z ON z.idZonaComunal=r.idZonaComunal GROUP BY r.idZonaComunal)X HAVING X.cuenta=MAX(X.cuenta)");
        return query.getSingleResult().toString();
    }
    public List<ZonaComunal> min() {
        Query query;
        query = em.createQuery("SELECT MIN(z.cantidadReservada) FROM ZonaComunal z");
        return query.getResultList();
    }
    
    public ZonaComunalFacade() {
        super(ZonaComunal.class);
    }
    
}
