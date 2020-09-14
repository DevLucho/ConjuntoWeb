/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import controlador.HoraControlador;
import entidades.Comunicado;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author Huertas
 */
@Stateless
public class ComunicadoFacade extends AbstractFacade<Comunicado> {

    @Inject
    HoraControlador hora;
    
    @PersistenceContext(unitName = "ProyectoConjuntoWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComunicadoFacade() {
        super(Comunicado.class);
    }

    public List<Comunicado> consultarTodos(String tipo) {
        Query query = em.createQuery("SELECT u FROM Comunicado u WHERE u.tipo=:tipo");
        query.setParameter("tipo", tipo);
        return query.getResultList();
    }

    public List<Comunicado> procesoEliminar(Date fechaF) {
        Query query = em.createQuery("SELECT u FROM Comunicado u WHERE u.publicarHasta BETWEEN '2020-01-01' AND :fechaF");
        query.setParameter("fechaF", fechaF);
        return query.getResultList();
    }

    public int contarComunicados(String tipo) {
        Query query;
        query = em.createQuery("SELECT COUNT(u) FROM Comunicado u WHERE u.tipo=:tipo");
        query.setParameter("tipo", tipo);
        return ((Long) query.getSingleResult()).intValue();
    }

}
