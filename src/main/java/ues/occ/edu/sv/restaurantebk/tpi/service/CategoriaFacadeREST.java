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
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import ues.occ.edu.sv.restaurantebk.tpi.cors.verificacion;
import ues.occ.edu.sv.restaurantebk.tpi.entities.Categoria;
import ues.occ.edu.sv.restaurantebk.tpi.facades.CategoriaFacade;

/**
 * Categoria rest implementa los metodos rest exponiendo los recursos en objetos json
 * con la verificacion de JWT java web token para poder responder a la peticion http
 * los metodos rest son accesables mediante la exposicion de cors para configurar las peticiones
 * 
 * @author enrique
 */
@Stateless
@Path("categoria")
public class CategoriaFacadeREST implements Serializable {

    /*
    Se injecta el categoriaFacade para hacer uso de sus metodos que conectan con el entitie
    */
    @Inject
    CategoriaFacade categoriaFacade;
    /*
    Se injecta verificacion, contiene un metodo de verificacion del jwt
    */
    @Inject
    verificacion verificacion;

    /**
     * Metodo para crear una nueva categoria se recibe un json con los datos de
     * la nueva categoria
     *
     * @param jsonString
     * @param JWT
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearNuevo(String jsonString, @HeaderParam("JWT") String JWT) {
        try {
            if (JWT != null) {
                DecodedJWT token = verificacion.verificarJWT(JWT);
                if (token != null) {
                    JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
                    if (isNullOrEmpty(json.get("nombreCategoria").getAsString())) {
                        if (categoriaFacade.noNombresIguales(json.get("nombreCategoria").getAsString())) {
                            if (categoriaFacade.create(new Categoria(null, json.get("nombreCategoria").getAsString()))) {
                                return Response.status(Response.Status.OK).header("mensaje", "se creo con exito").build();
                            } else {
                                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("mensaje", "no se pudo crear").build();
                            }
                        } else {
                            return Response.status(Response.Status.CONFLICT).header("mensaje", "El nombre coincide con un dato existente").build();
                        }
                    } else {
                        return Response.status(Response.Status.BAD_REQUEST).header("mensaje", "los campos de nombre_categoria esta vacio").build();
                    }
                } else {
                    return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "JWT no valido").build();
                }
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "no autorizado sin JWT").build();
            }
        } catch (JsonSyntaxException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("mensaje", "No entro ni siquiera al try" + e).build();
        }
    }
    /**
     * Edita las categorias, para editar es necesario que todas las caracteristicas
     * vengan con datos no se permite que vengan vacias, y es necesario que un administrador las edite
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
                if(token != null){
                    JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
                    if(isNullOrEmpty(json.get("nombreCategoria").getAsString()) && isNullOrEmpty(json.get("idCategoria").getAsString())){
                        if(categoriaFacade.noNombresIguales(json.get("nombreCategoria").getAsString())){
                            if(("Administrador").equals(token.getClaim("categoria").asString())){
                                if(categoriaFacade.edit(new Categoria(json.get("idCategoria").getAsInt(), json.get("nombreCategoria").getAsString()))){
                                    return Response.status(Response.Status.OK).header("mensaje", "Se modifico la categoria").build();
                                }else{
                                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("mensaje", "No se pudo modificar la categoria").build();
                                }
                            }else{
                                return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "No esta autorizado para modificar").build();
                            }
                        }else{
                            return Response.status(Response.Status.CONFLICT).header("mensaje", "Ya existe una categoria con ese nombre").build();
                        }
                    }else{
                        return Response.status(Response.Status.BAD_REQUEST).header("mensaje", "los campos vinieron vacios").build();
                    }
                }else{
                    return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "token no valido").build();
                }
            }else{
                return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "No valido sin JWT").build();
            }
        } catch (JsonSyntaxException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("mensaje", "Error dentro del servidor "+e).build();
        }
    
    }
    
    /**
     * metodo borrar categoria se pide el id para buscar el objeto en cuestion y la autorizacion
     * por medio del jwt, para poder otorgar permisos de eliminacion
     * 
     * @param id
     * @param JWT
     * @return 
     */
    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") String id, @HeaderParam("JWT") String JWT){
        try {
            if(JWT != null){
                System.out.println("........................................"+id);
                DecodedJWT token = verificacion.verificarJWT(JWT);
                if(token != null){
                    if(categoriaFacade.remove(categoriaFacade.find((Integer) Integer.parseInt(id.trim())))){
                        return Response.status(Response.Status.OK).header("mensaje", "La categoria se borro con exito").build();
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
     * Metodo GET que devuelve una lista de las categorias en formato json, es necesario que el JWT vaya
     * en la peticion para autorizar la respuesta del metodo
     * 
     * @param JWT
     * @return 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Categoria> findAll(@HeaderParam("JWT") String JWT) {
        try {
            if (JWT != null) {
                DecodedJWT token = verificacion.verificarJWT(JWT);
                if (token != null) {
                    return categoriaFacade.findAll();
                } else {
                    return Collections.emptyList();
                }
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }

    }

    public static boolean isNullOrEmpty(String str) {
        return ((str != null) ? (!str.trim().isEmpty()) : (false));
    }
}
