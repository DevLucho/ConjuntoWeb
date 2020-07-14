/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.Correspondencia;
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
public class CorrespondenciaFacade extends AbstractFacade<Correspondencia> {

    @PersistenceContext(unitName = "ProyectoConjuntoWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CorrespondenciaFacade() {
        super(Correspondencia.class);
    }

    public List<Correspondencia> paqueteEntregado(String estado) {
        Query query;
        query = em.createQuery("SELECT u FROM Correspondencia u WHERE u.estado=:estado");
        query.setParameter("estado", estado);
        return query.getResultList();
    }

}
