/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.Reserva;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Huertas
 */
@Stateless
public class ReservaFacade extends AbstractFacade<Reserva> {

    @PersistenceContext(unitName = "ProyectoConjuntoWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReservaFacade() {
        super(Reserva.class);
    }
    public int ContarReserva(int id){
        Query q = em.createQuery("SELECT COUNT(r) FROM Reserva r WHERE r.estado='Reservado' AND r.idZonaComunal.idZonaComunal=:id");
        q.setParameter("id", id);
        return ((Long) q.getSingleResult()).intValue();
    }
    //public List<Object[]> ContarReservaMes(){
        //Query q = em.createNativeQuery("SELECT Date_Format(fechaInicioReserva,\"%M %Y\") AS Fecha, COUNT(idReserva) AS Cuenta, idZonaComunal FROM Reserva GROUP BY Fecha , idZonaComunal");
      //  return q.getResultList();
    //}
    
}
