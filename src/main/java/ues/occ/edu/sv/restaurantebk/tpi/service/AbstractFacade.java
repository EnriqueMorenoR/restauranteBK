/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.restaurantebk.tpi.service;

import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author enrique
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    /**
     * Se llama al entitie de la clase abstracto
     * @param entityClass 
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    /**
     * Se pide el entitie manager para manejar en Entitie
     * @return 
     */
    protected abstract EntityManager getEntityManager();
    
    /**
     * Metodo abstracto para crear a traves del persistence con una entitie de parametro
     * 
     * @param entity 
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }
    
    /**
     * Metodo abstracto para editar con entitie manager
     * 
     * @param entity 
     */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }
    
    /**
     * Metodo Abstracto para eliminar con un tipo de object abstracto y un entitie
     * 
     * @param entity 
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }
    
    /**
     * Metodo abstracto que busca un objeto con el id
     * 
     * @param id
     * @return 
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
    
    /**
     * Metodo abstracto que busca todos los objetos de un tipo T haciendo uso del entitie
     * manager para hacer la peticion en jpql
     * 
     * @return 
     */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    /**
     * Metodo abstracto para hacer una busqueda por rango devolviendo una lista de tipo T
     * 
     * @param range
     * @return 
     */
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    
    /**
     * Metodo abstracto para contar todos los objetos que estan de esta entitie 
     * 
     * @return 
     */
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
