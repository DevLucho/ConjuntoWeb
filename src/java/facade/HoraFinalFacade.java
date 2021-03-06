/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.HoraFinal;
import java.util.Date;
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
public class HoraFinalFacade extends AbstractFacade<HoraFinal> {

    @PersistenceContext(unitName = "ProyectoConjuntoWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HoraFinalFacade() {
        super(HoraFinal.class);
    }

    public List<HoraFinal> horasNoReservadas(int id) {
        Query query = em.createQuery("SELECT h FROM HoraFinal h WHERE h.idHora <> :id");
        query.setParameter("id", id);
        return query.getResultList();
    }

    public List<HoraFinal> horasFinales(int num) {
        Query query = em.createQuery("SELECT h FROM HoraFinal h WHERE h.idHora >= :num");
        query.setParameter("num", num);
        return query.getResultList();
    }            

    public List<HoraFinal> horasSinReserva(int idHoraIni, int idHoraFin) {
        Query q = em.createQuery("SELECT h FROM HoraFinal h WHERE h.idHora BETWEEN  :idHoraIni AND :idHoraFin");
        q.setParameter("idHoraIni", idHoraIni);
        q.setParameter("idHoraFin", idHoraFin);
        return q.getResultList();
    }

}
