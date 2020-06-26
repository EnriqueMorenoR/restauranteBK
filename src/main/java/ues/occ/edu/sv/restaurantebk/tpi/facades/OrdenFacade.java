/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.restaurantebk.tpi.facades;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ues.occ.edu.sv.restaurantebk.tpi.entities.Orden;

/**
 *
 * @author cristian
 */
@Stateless
@LocalBean
public class OrdenFacade extends AbstractFacade<Orden> implements GenericLocalInterface<Orden> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrdenFacade() {
        super(Orden.class);
    }
    
    /**
     * Metodo que busca que no existan dos objetos iguales mediante una query
     * 
     * @param idOrden
     * @return 
     */
    public boolean noIdIguales(Integer idOrden){
        try {
            return getEntityManager().createQuery("SELECT n FROM Orden n WHERE n.idOrden=:id").setParameter("id", idOrden).getResultList().isEmpty();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return true;
        }
    }
    
}
