/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.restaurantebk.tpi.service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author cristian
 */
@javax.ws.rs.ApplicationPath("tpi_restaurante")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ues.occ.edu.sv.restaurantebk.tpi.service.CategoriaFacadeREST.class);
        resources.add(ues.occ.edu.sv.restaurantebk.tpi.service.DetalleOrdenFacadeREST.class);
        resources.add(ues.occ.edu.sv.restaurantebk.tpi.service.OrdenFacadeREST.class);
        resources.add(ues.occ.edu.sv.restaurantebk.tpi.service.ProductoFacadeREST.class);
        resources.add(ues.occ.edu.sv.restaurantebk.tpi.service.UsuarioFacadeREST.class);
    }
    
}
