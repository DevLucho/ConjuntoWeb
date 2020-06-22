/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.Comunicado;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Huertas
 */
@Stateless
public class ComunicadoFacade extends AbstractFacade<Comunicado> {

    @PersistenceContext(unitName = "ProyectoConjuntoWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComunicadoFacade() {
        super(Comunicado.class);
    }
    
}