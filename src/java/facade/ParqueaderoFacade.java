/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.Parqueadero;
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
public class ParqueaderoFacade extends AbstractFacade<Parqueadero> {

    @PersistenceContext(unitName = "ProyectoConjuntoWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParqueaderoFacade() {
        super(Parqueadero.class);
    }

    public List<Parqueadero> consultarP(String estado) {
        Query query = em.createQuery("SELECT a FROM Parqueadero a WHERE a.estado=:estado");
        query.setParameter("estado", estado);
        return query.getResultList();
    }

}
