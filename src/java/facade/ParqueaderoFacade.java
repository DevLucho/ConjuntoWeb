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

    public int contarParqueaderoR(int id) {

        int Carros = em.createNativeQuery("SELECT COUNT(r.automovil) FROM Residente r, Torre t,Inmueble i WHERE r.automovil='Si' AND r.idInmueble=:i AND i.idTorre=:id").getFirstResult();
        //Query q = em.createQuery("SELECT COUNT(v.placa) FROM Vehiculo v JOIN Residente r on v.idResidente = r.idResidente JOIN Inmueble i on r.idInmueble = i.idInmueble WHERE r.idResidente IS NOT NULL AND i.idTorre=:id GROUP BY i.idTorre ORDER BY i.idTorre");
        //q.setParameter("id", id);
        //return ((Long) q.getSingleResult()).intValue();
        return Carros;
    }

    public int contarVehiculos(int idTorre) {
        Query q = em.createQuery("SELECT COUNT(u) FROM Residente u WHERE u.automovil='Si' AND u.idInmueble.idTorre.idTorre=:idTorre");
        q.setParameter("idTorre", idTorre);
        return ((Long) q.getSingleResult()).intValue();
    }

}
