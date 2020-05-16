/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.restaurantebk.tpi.facades;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author cristian
 * @param <T>
 */
public abstract class AbstractFacade<T> {

    private final Class<T> entityClass;

    /**
     * Este método obtiene la clase de la entidad
     *
     * @param entityClass
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    /**
     * Este método crea un objeto de la clase
     *
     * @param entity Es la entidad a crear
     * @return <b>True</b>: Si se pudo crear <br><b>False</b>: Si hubo algún
     * error o algo es nulo
     */
    public boolean create(T entity) {
        EntityManager em = getEntityManager();
        try {
            if (entity != null && em != null) {
                em.persist(entity);
                return true;
            } else {
                System.out.println("Entity manager es nulo o la entidad es nula");
                return false;
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return false;
        }

    }

    /**
     * Este método es para editar algún objeto
     *
     * @param entity es la entidad
     * @return <b>True</b>: Si se pudo crear <br><b>False</b>: Si hubo algún
     * error o algo es nulo
     */
    public boolean edit(T entity) {
        EntityManager em = getEntityManager();
        try {
            if (em != null && entity != null) {
                em.merge(entity);
                return true;
            } else {
                System.out.println("Entity manager es nulo o la entidad es nula");
                return false;
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Este método es para eliminar un objeto
     *
     * @param entity es la entidad
     * @return <b>True</b>: Si se pudo crear <br><b>False</b>: Si hubo algún
     * error o algo es nulo
     */
    public boolean remove(T entity) {
        EntityManager em = getEntityManager();
        try {
            if (em != null && entity != null) {
                em.remove(em.merge(entity));
                return true;
            } else {
                System.out.println("Entity manager es nulo o la entidad es nula");
                return false;
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Encuentra un objeto por su id (el de la clase)
     *
     * @param id es el id del objeto
     * @return El objeto si se encuentra. <br>Sino, devuelve null
     */
    public T find(Object id) {
        EntityManager em = getEntityManager();
        try {
            if (em != null && id != null) {
                return em.find(entityClass, id);
            } else {
                System.out.println("Entity manager es nulo o la entidad es nula");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }

    /**
     * Este método devuelve todos los objetos de una clase
     *
     * @return Una lista de objetos de la clase si todo va bien<br> Si no
     * devuelve una lista vacía
     */
    public List<T> findAll() {
        EntityManager em = getEntityManager();
        try {
            if (em != null) {
                javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
                cq.select(cq.from(entityClass));
                return em.createQuery(cq).getResultList();
            } else {
                System.out.println("Entity manager es nulo");
                return Collections.emptyList();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return Collections.emptyList();
        }

    }

    /**
     * Este método sirve para paginar y devuelve un rango de entidades
     *
     * @param desde entero que indica desde donde comenzará
     * @param cuantosReg La cantidad de registros que va a traer
     * @return una lista de entidades si todo va bien <br> sino devuelve una
     * lista vacía
     */
    public List<T> findRange(int desde, int cuantosReg) {
        EntityManager em = getEntityManager();
        try {
            if (em != null && String.valueOf(desde) != null && String.valueOf(cuantosReg) != null) {
                javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
                cq.select(cq.from(entityClass));
                javax.persistence.Query q = em.createQuery(cq);
                q.setMaxResults(cuantosReg);
                q.setFirstResult(desde);
                return q.getResultList();
            } else {
                System.out.println("ALGO ES NULO");
                return Collections.emptyList();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return Collections.emptyList();
        }

    }

    /**
     * Este método cuenta cuantas entidades hay en la bd
     *
     * @return un entero con la cantidad o 0
     */
    public int count() {
        EntityManager em = getEntityManager();
        try {
            if (em != null) {
                javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
                javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
                cq.select(em.getCriteriaBuilder().count(rt));
                javax.persistence.Query q = em.createQuery(cq);
                return ((Long) q.getSingleResult()).intValue();
            } else {
                System.out.println("ALGO ES NULO");
                return 0;
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return 0;
        }

    }

}
