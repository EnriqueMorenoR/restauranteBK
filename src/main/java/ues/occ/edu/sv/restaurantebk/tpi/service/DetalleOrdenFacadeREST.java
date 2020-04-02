/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.restaurantebk.tpi.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import ues.occ.edu.sv.restaurantebk.tpi.entities.DetalleOrden;
import ues.occ.edu.sv.restaurantebk.tpi.entities.DetalleOrdenPK;

/**
 *
 * @author enrique
 */
@Stateless
@Path("ues.occ.edu.sv.restaurantebk.tpi.entities.detalleorden")
public class DetalleOrdenFacadeREST extends AbstractFacade<DetalleOrden> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    private DetalleOrdenPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;idOrden=idOrdenValue;idProducto=idProductoValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        ues.occ.edu.sv.restaurantebk.tpi.entities.DetalleOrdenPK key = new ues.occ.edu.sv.restaurantebk.tpi.entities.DetalleOrdenPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> idOrden = map.get("idOrden");
        if (idOrden != null && !idOrden.isEmpty()) {
            key.setIdOrden(new java.lang.Integer(idOrden.get(0)));
        }
        java.util.List<String> idProducto = map.get("idProducto");
        if (idProducto != null && !idProducto.isEmpty()) {
            key.setIdProducto(new java.lang.Integer(idProducto.get(0)));
        }
        return key;
    }

    public DetalleOrdenFacadeREST() {
        super(DetalleOrden.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(DetalleOrden entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, DetalleOrden entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        ues.occ.edu.sv.restaurantebk.tpi.entities.DetalleOrdenPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public DetalleOrden find(@PathParam("id") PathSegment id) {
        ues.occ.edu.sv.restaurantebk.tpi.entities.DetalleOrdenPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<DetalleOrden> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<DetalleOrden> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
