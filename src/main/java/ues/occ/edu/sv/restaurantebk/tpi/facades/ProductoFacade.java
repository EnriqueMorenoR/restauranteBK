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
import ues.occ.edu.sv.restaurantebk.tpi.entities.Producto;

/**
 *
 * @author cristian
 */
@Stateless
@LocalBean
public class ProductoFacade extends AbstractFacade<Producto> implements GenericLocalInterface<Producto> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductoFacade() {
        super(Producto.class);
    }
    
    /**
     * Metodo que busca la similitud de los nombres de producto devuelve un booleano, true para similitudes y false 
     * para nombres no iguales
     * 
     * @param nombreProducto
     * @return 
     */
    public boolean noNombresIguales(String nombreProducto){
        try {
            return getEntityManager().createQuery("SELECT n FROM Producto n WHERE n.nombreProducto=:nombre").setParameter("nombre", nombreProducto).getResultList().isEmpty();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return true;
        }
    }
    
}
