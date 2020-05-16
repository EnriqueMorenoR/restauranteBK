/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.restaurantebk.tpi.facades;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cristian
 * @param <T>
 */
@Local
public interface GenericLocalInterface<T> {

    boolean create(T clase);

    boolean edit(T clase);

    boolean remove(T clase);

    T find(Object id);

    List<T> findAll();

    List<T> findRange(int desde, int cuantos);

    int count();
    
}
