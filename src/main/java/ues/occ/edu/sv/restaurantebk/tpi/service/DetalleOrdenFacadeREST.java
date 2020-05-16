/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.restaurantebk.tpi.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.sun.source.doctree.SerialDataTree;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import ues.occ.edu.sv.restaurantebk.tpi.cors.verificacion;
import ues.occ.edu.sv.restaurantebk.tpi.entities.DetalleOrden;
import ues.occ.edu.sv.restaurantebk.tpi.entities.DetalleOrdenPK;
import ues.occ.edu.sv.restaurantebk.tpi.entities.Orden;
import ues.occ.edu.sv.restaurantebk.tpi.entities.Usuario;
import ues.occ.edu.sv.restaurantebk.tpi.facades.DetalleOrdenFacade;
import ues.occ.edu.sv.restaurantebk.tpi.facades.OrdenFacade;
import ues.occ.edu.sv.restaurantebk.tpi.facades.ProductoFacade;
import static ues.occ.edu.sv.restaurantebk.tpi.service.OrdenFacadeREST.isNullOrEmpty;

/**
 *
 * @author enrique
 */
@Stateless
@Path("detalleorden")
public class DetalleOrdenFacadeREST implements Serializable{
    
    @Inject
    DetalleOrdenFacade detalleOrdenFacade;
    @Inject
    OrdenFacade ordenFacade;
    @Inject
    ProductoFacade productoFacade;
    @Inject
    verificacion verificacion;
    /**
     * Metodo Para Obtener La lista completa de los detalleOrdenes
     * 
     * @param JWT
     * @return 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<DetalleOrden> findAll(@HeaderParam("JWT") String JWT){
        try {
            if (JWT != null) {
                DecodedJWT token = verificacion.verificarJWT(JWT);
                if (token != null) {
                    return detalleOrdenFacade.findAll();
                } else {
                    return Collections.EMPTY_LIST;
                }
            } else {
                return Collections.EMPTY_LIST;
            }
        } catch (Exception e) {
            return Collections.EMPTY_LIST;
        }
    }
    /**
     * Metodo Para crear un detalleOrden Solo se pide los id de la orden y de producto
     * y la cantidad y su precio unitario
     * 
     * @param jsonString
     * @param JWT
     * @return 
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearNuevo(String jsonString, @HeaderParam("JWT") String JWT){
        try {
            if (JWT != null) {
                DecodedJWT token = verificacion.verificarJWT(JWT);
                if (token != null) {
                    JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
                    if (isNullOrEmpty(json.get("cantidad").getAsString())
                            && isNullOrEmpty(json.get("idProducto").getAsString())
                            && isNullOrEmpty(json.get("idOrden").getAsString())
                            && isNullOrEmpty(json.get("precioUnitario").getAsString())) {
                            DetalleOrdenPK detalleOrdenPK = new DetalleOrdenPK(json.get("idOrden").getAsInt(), json.get("idProducto").getAsInt());
                            DetalleOrden detalleOrden = new DetalleOrden(detalleOrdenPK, json.get("cantidad").getAsInt(), json.get("precioUnitario").getAsBigDecimal());
                        if (detalleOrdenFacade.create(detalleOrden)) {
                            return Response.status(Response.Status.CREATED).header("mensaje", "orden creada con exito").build();
                        } else {
                            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("mensaje", "no se pudo crear la orden").build();
                        }
                    } else {
                        return Response.status(Response.Status.BAD_REQUEST).header("mensaje", "json vienen campos vacios que son requeridos").build();
                    }
                } else {
                    return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "Token no valido").build();
                }
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "No valido sin JWT").build();
            }
        } catch (JsonSyntaxException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("mensaje", "Error del server " + e).build();
        }
    }
    
    /**
     * Metodo Para editar por el momento no tiene restriccion de edicion
     * es decir que El PK es el unico dato que no es identico pero como las 
     * ordenes puden cambiar, si se encuentran errores se modifica
     * 
     * @param jsonString
     * @param JWT
     * @return 
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response edit(String jsonString, @HeaderParam("JWT") String JWT){
        try {
            if (JWT != null) {
                DecodedJWT token = verificacion.verificarJWT(JWT);
                if (token != null) {
                    JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
                    if (isNullOrEmpty(json.get("cantidad").getAsString())
                            && isNullOrEmpty(json.get("idProducto").getAsString())
                            && isNullOrEmpty(json.get("idOrden").getAsString())
                            && isNullOrEmpty(json.get("precioUnitario").getAsString())) {
                            DetalleOrdenPK detalleOrdenPK = new DetalleOrdenPK(json.get("idOrden").getAsInt(), json.get("idProducto").getAsInt());
                            DetalleOrden detalleOrden = new DetalleOrden(detalleOrdenPK, json.get("cantidad").getAsInt(), json.get("precioUnitario").getAsBigDecimal());
                        if (detalleOrdenFacade.edit(detalleOrden)) {
                            return Response.status(Response.Status.CREATED).header("mensaje", "orden creada con exito").build();
                        } else {
                            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("mensaje", "no se pudo crear la orden").build();
                        }
                    } else {
                        return Response.status(Response.Status.BAD_REQUEST).header("mensaje", "json vienen campos vacios que son requeridos").build();
                    }
                } else {
                    return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "Token no valido").build();
                }
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "No valido sin JWT").build();
            }
        } catch (JsonSyntaxException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("mensaje", "Error del server " + e).build();
        }
    }
    /**
     * Se verifica si un string esta vacio
     * 
     * @param str
     * @return 
     */
    public static boolean isNullOrEmpty(String str) {
        return ((str != null) ? (!str.trim().isEmpty()) : (false));
    }
   
}
