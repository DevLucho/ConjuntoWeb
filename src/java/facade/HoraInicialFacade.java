/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.HoraInicial;
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
public class HoraInicialFacade extends AbstractFacade<HoraInicial> {

    @PersistenceContext(unitName = "ProyectoConjuntoWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HoraInicialFacade() {
        super(HoraInicial.class);
    }

    public List<HoraInicial> horasNoReservadas(int id) {
        Query query = em.createQuery("SELECT h FROM HoraInicial h WHERE h.idHora <> :id");
        query.setParameter("id", id);
        return query.getResultList();
    }

    public List<HoraInicial> horasSinReserva(int idHoraIni, int idHoraFin) {
        Query q = em.createQuery("SELECT h FROM HoraInicial h WHERE h.idHora BETWEEN  :idHoraIni AND :idHoraFin");
        q.setParameter("idHoraIni", idHoraIni);
        q.setParameter("idHoraFin", idHoraFin);
        return q.getResultList();
    }

    // Metodo que muestra las horas disponibles de reserva desde-hasta definidas
    public List<HoraInicial> horaReservaMaxMin(int desde, int hasta) {
        Query q = em.createQuery("SELECT h FROM HoraInicial h WHERE h.idHora BETWEEN  :idHoraIni AND :idHoraFin");
        q.setParameter("desde", desde);
        q.setParameter("hasta", hasta);
        return q.getResultList();
    }

}
