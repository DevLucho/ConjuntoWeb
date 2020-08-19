/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.Rol;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Huertas
 */
@Stateless
public class RolFacade extends AbstractFacade<Rol> {

    @PersistenceContext(unitName = "ProyectoConjuntoWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolFacade() {
        super(Rol.class);
    }

    public Rol consultarRol(int idRol) {
        Rol rol = null;
        try {
            Query q = em.createQuery("SELECT r FROM Rol r WHERE r.idRol=:idRol ");
            q.setParameter("idRol", idRol);
            rol = (Rol) q.getSingleResult();

        } catch (Exception e) {
            System.out.println("Error rol: " + e.getMessage());
        }
        return rol;
    }

    public Rol nombreRol(String nombre) {
        Rol rol = new Rol();
        try {
            Query q = em.createQuery("SELECT r FROM Rol r WHERE r.nombre=:nombre ");
            q.setParameter("nombre", nombre);
            rol = (Rol) q.getResultList().get(0);
        } catch (Exception e) {
            System.out.println("Error rol - nombre: " + e.getMessage());
        }
        return rol;
    }

}
