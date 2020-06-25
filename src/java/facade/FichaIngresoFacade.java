/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.FichaIngreso;
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
    public List<Object[]> consultarFichas(){
        List<Object[]> listaFicha = new ArrayList();  
        Query query = em.createNativeQuery("select * from ficha_ingreso");
        listaFicha = query.getResultList();
        for(Object[] item: listaFicha){
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
}
