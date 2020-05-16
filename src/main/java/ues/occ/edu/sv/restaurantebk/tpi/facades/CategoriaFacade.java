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
import ues.occ.edu.sv.restaurantebk.tpi.entities.Categoria;

/**
 *
 * @author cristian
 */
@Stateless
@LocalBean
public class CategoriaFacade extends AbstractFacade<Categoria> implements GenericLocalInterface<Categoria> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoriaFacade() {
        super(Categoria.class);
    }
    /**
     * El metodo buscara entre todas las categorias y buscara coincidencias con el nombreCategoria proporcionado
     * si no ecuentra entonces el metodo devolvera false, que indica que no hay una categoria con el mismo nombre
     * proporcionado
     * 
     * @param nombreCategoria el nombre de la categoria que se desea buscar conicidencias
     * @return 
     */
    public boolean noNombresIguales(String nombreCategoria){
        try {
            return getEntityManager().createQuery("SELECT n FROM Categoria n WHERE n.nombreCategoria=:nombre").setParameter("nombre", nombreCategoria).getResultList().isEmpty();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return true;
        }
    }
    
    
    
}
