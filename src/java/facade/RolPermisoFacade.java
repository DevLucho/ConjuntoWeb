/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.RolPermiso;
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
public class RolPermisoFacade extends AbstractFacade<RolPermiso> {

    @PersistenceContext(unitName = "ProyectoConjuntoWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolPermisoFacade() {
        super(RolPermiso.class);
    }

    public List<RolPermiso> rolPermisos(int idRol) {
        Query q = em.createQuery("SELECT r FROM RolPermiso r WHERE r.estado='Activo' AND r.idRol.idRol=:idRol");
        q.setParameter("idRol", idRol);
        return q.getResultList();
    }

}
