/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.Visitante;
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
public class VisitanteFacade extends AbstractFacade<Visitante> {

    @PersistenceContext(unitName = "ProyectoConjuntoWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VisitanteFacade() {
        super(Visitante.class);
    }

    public List<Object[]> consultarVis() {
        List<Object[]> listaVisitante = new ArrayList();
        Query query = em.createNativeQuery("select * from visitante");
        listaVisitante = query.getResultList();
        for (Object[] iten : listaVisitante) {
            System.out.println(iten[0]);
            System.out.println(iten[1]);
            System.out.println(iten[2]);
            System.out.println(iten[3]);
        }
        return listaVisitante;
    }

    public List<Visitante> fichaBloqueada(String estadoFicha) {
        Query query;
        query = em.createQuery("SELECT u FROM Visitante u JOIN FichaIngreso F WHERE u.idFicha.idFicha=F.idFicha AND F.estadoFicha=:estadoFicha");
        query.setParameter("estadoFicha", estadoFicha);
        return query.getResultList();
    }

}
