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
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
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
import javax.ws.rs.core.Response;
import ues.occ.edu.sv.restaurantebk.tpi.entities.Orden;
import ues.occ.edu.sv.restaurantebk.tpi.entities.Usuario;
import ues.occ.edu.sv.restaurantebk.tpi.facades.OrdenFacade;
import ues.occ.edu.sv.restaurantebk.tpi.facades.UsuarioFacade;
import ues.occ.edu.sv.restaurantebk.tpi.services.fatherClassVerify;

/**
 *
 * @author enrique
 */
@Stateless
@Path("orden")
public class OrdenFacadeREST extends fatherClassVerify implements Serializable {

    @Inject
    OrdenFacade ordenFacade;
    @Inject
    UsuarioFacade usuarioFacade;
    @Inject
    Orden orden;
    
    /**
     * Metodo para obtener todas las ordenes generadas
     * 
     * @param JWT
     * @return 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Orden> findAll(@HeaderParam("JWT") String JWT) {
        try {
            if (JWT != null) {
                DecodedJWT token = verificarJWT(JWT);
                if (token != null) {
                    return ordenFacade.findAll();
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
    
    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") String id, @HeaderParam("JWT") String JWT){
        try {
            if(JWT != null){
                System.out.println("........................................"+id);
                DecodedJWT token = verificarJWT(JWT);
                if(token != null){
                    if(ordenFacade.remove(ordenFacade.find((Integer) Integer.parseInt(id.trim())))){
                        return Response.status(Response.Status.OK).header("mensaje", "la orden se borro con exito").build();
                    }else{
                        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("mensaje", "no se pudo borrar la categoria").build();
                    }
                }else{
                    return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "token no autorizado").build();
                }
            }else{
                return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "No es posible sin token").build();
            }
        } catch (JsonSyntaxException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("mensaje", "Error dentro del servidor "+e).build();
        }
    }
    
    /**
     * Metodo para crear una orden no es necesario especificar el id
     * 
     * @param jsonString
     * @param JWT
     * @return
     * @throws ParseException 
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearNuevo(String jsonString, @HeaderParam("JWT") String JWT) throws ParseException {
        try {
            if (JWT != null) {
                DecodedJWT token = verificarJWT(JWT);
                if (token != null) {
                    JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
                    if (isNullOrEmpty(json.get("fecha").getAsString())
                            && isNullOrEmpty(json.get("mesa").getAsString())
                            && isNullOrEmpty(json.get("cliente").getAsString())
                            && isNullOrEmpty(json.get("total").getAsString())
                            && isNullOrEmpty(json.get("estado").getAsString())
                            && isNullOrEmpty(json.get("idUsuario").getAsString())) {
                        Usuario usuario = usuarioFacade.find(json.get("idUsuario").getAsInt());
                        orden = new Orden(null, new java.sql.Date(new java.util.Date().getTime()), json.get("mesa").getAsString(), json.get("cliente").getAsString(), json.get("estado").getAsString(), json.get("total").getAsDouble(), usuario, json.get("observacion").getAsString());
                        System.out.println(orden);
                        if (ordenFacade.create(orden)) {
                            return Response.status(Response.Status.CREATED).header("mensaje", "orden creada con exito").entity(orden.getIdOrden()).build();
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
     * Metodo para editar las ordenes el unico parametro es que no se repitan los id
     * 
     * @param jsonString
     * @param JWT
     * @return
     * @throws ParseException 
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editar(String jsonString, @HeaderParam("JWT") String JWT) throws ParseException {
        try {
            if (JWT != null) {
                DecodedJWT token = verificarJWT(JWT);
                if (token != null) {
                    JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
                    if (isNullOrEmpty(json.get("fecha").getAsString())
                            && isNullOrEmpty(json.get("idOrden").getAsString())
                            && isNullOrEmpty(json.get("mesa").getAsString())
                            && isNullOrEmpty(json.get("cliente").getAsString())
                            && isNullOrEmpty(json.get("total").getAsString())
                            && isNullOrEmpty(json.get("estado").getAsString())
                            && isNullOrEmpty(json.get("idUsuario").getAsString())) {
                        if (ordenFacade.noIdIguales(json.get("idOrden").getAsInt())) {

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = sdf.parse(json.get("fecha").getAsString());
                            System.out.println("............................................." + json.get("fecha").getAsString());
                            System.out.println("............................................." + date);
                            Usuario usuario = usuarioFacade.find(json.get("idUsuario").getAsInt());
                            orden = new Orden(json.get("idOrden").getAsInt(), date, json.get("mesa").getAsString(), json.get("cliente").getAsString(), json.get("estado").getAsString(), json.get("total").getAsDouble(), usuario, json.get("observacion").getAsString());
                            if (ordenFacade.edit(orden)) {
                                return Response.status(Response.Status.CREATED).header("mensaje", "orden creada con exito").build();
                            } else {
                                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("mensaje", "no se pudo crear la orden").build();
                            }
                        }else{
                            return Response.status(Response.Status.CONFLICT).header("mensaje", "Ya existe una orden con el mismo id").build();
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
   
}
