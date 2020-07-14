/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.Domiciliario;
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
public class DomiciliarioFacade extends AbstractFacade<Domiciliario> {

    @PersistenceContext(unitName = "ProyectoConjuntoWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DomiciliarioFacade() {
        super(Domiciliario.class);
    }

    public List<Domiciliario> fichaBloqueadaD(String estadoFicha) {
        Query query;
        query = em.createQuery("SELECT u FROM Domiciliario u JOIN FichaIngreso F WHERE u.idFicha.idFicha=F.idFicha AND F.estadoFicha=:estadoFicha");
        query.setParameter("estadoFicha", estadoFicha);
        return query.getResultList();
    }
}
