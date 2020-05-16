/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.restaurantebk.tpi.facades;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ues.occ.edu.sv.restaurantebk.tpi.entities.Usuario;

/**
 *
 * @author cristian
 */
@Stateless
@LocalBean
public class UsuarioFacade extends AbstractFacade<Usuario> implements GenericLocalInterface<Usuario> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    /**
     * Este método busca que el nombre y la contraseña coincidan con los datos
     * de la bd (que exista).
     *
     * @param nombre El usuario de la persona
     * @param Contrasenia La contraseña de el usuario
     * @return <b> La entidad: Si coinciden</b> <br> <b>Null:</b> Si no existen
     * coincidencias
     */
    public Usuario buscarPorNombreYContrasenia(String nombre, String Contrasenia) {
        try {
            List<Usuario> listUsarios = getEntityManager().createQuery("SELECT n from Usuario n WHERE n.nombre=:nombre AND n.password=:password").setParameter("nombre", nombre).setParameter("password", Contrasenia).getResultList();
            return listUsarios.size() > 0 ? listUsarios.get(0) : null;
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    /**
     * Este método busca que no existan usuarios iguales, ya que se genera un
     * JWT con el nombre del usuario y sería una vulnerabilidad el hacer 2
     * tokens igualess.
     *
     * @param nombre El usuario a verificar.
     * @return <b>True: </b>Si el usuario está disponible.<br><b>False: </b>Si
     * se encontraron coincidencias. Por lo tanto no puede ser usado el usuario.
     */
    public boolean noNombresIguales(String nombre) {
        try {
            return getEntityManager().createQuery("SELECT n from Usuario n WHERE n.nombre=:nombre").setParameter("nombre", nombre).getResultList().isEmpty();
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }
}
