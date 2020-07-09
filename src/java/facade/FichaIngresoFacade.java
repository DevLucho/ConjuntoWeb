/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.FichaIngreso;
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
public class FichaIngresoFacade extends AbstractFacade<FichaIngreso> {

    @PersistenceContext(unitName = "ProyectoConjuntoWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FichaIngresoFacade() {
        super(FichaIngreso.class);
    }

    public List<Object[]> consultarFichas() {
        List<Object[]> listaFicha = new ArrayList();
        Query query = em.createNativeQuery("select * from ficha_ingreso");
        listaFicha = query.getResultList();
        for (Object[] item : listaFicha) {
            System.out.println(item[0]);
            System.out.println(item[1]);
            System.out.println(item[2]);
            System.out.println(item[3]);
            System.out.println(item[4]);
            System.out.println(item[6]);
            System.out.println(item[7]);
        }
        return listaFicha;
    }
 public List<FichaIngreso> fichaBloqueada(String estadoFicha) {
        Query query;
        query = em.createQuery("SELECT u FROM FichaIngreso u WHERE u.estadoFicha=:estadoFicha");
        query.setParameter("estadoFicha", estadoFicha);
        return query.getResultList();
    }
    public List<Visitante> consultaBlo(String estadoFicha, FichaIngreso idFicha){
        Query query;
        query = em.createQuery("SELECT u FROM Visitante u WHERE u.idFicha=:idFicha AND u.idFicha.estadoFicha=:estadoFicha");
        query.setParameter("idFicha", idFicha);
        query.setParameter("estadoFicha", estadoFicha);
        return query.getResultList();
    }
}
