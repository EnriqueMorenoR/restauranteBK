/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.restaurantebk.tpi.facades;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ues.occ.edu.sv.restaurantebk.tpi.entities.DetalleOrden;

/**
 *
 * @author cristian
 */
@Stateless
@LocalBean
public class DetalleOrdenFacade extends AbstractFacade<DetalleOrden> implements GenericLocalInterface<DetalleOrden> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DetalleOrdenFacade() {
        super(DetalleOrden.class);
    }
    
}
