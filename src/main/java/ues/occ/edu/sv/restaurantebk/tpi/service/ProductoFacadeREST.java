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
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ues.occ.edu.sv.restaurantebk.tpi.entities.Producto;
import ues.occ.edu.sv.restaurantebk.tpi.facades.CategoriaFacade;
import ues.occ.edu.sv.restaurantebk.tpi.facades.ProductoFacade;
import ues.occ.edu.sv.restaurantebk.tpi.services.fatherClassVerify;

/**
 *
 * @author enrique
 */
@Stateless
@Path("producto")
public class ProductoFacadeREST extends fatherClassVerify implements Serializable {

    @Inject
    ProductoFacade productoFacade;
    @Inject
    CategoriaFacade categoriaFacade;
    
    /**
     * Metodo para obtener todos los productos
     * 
     * @param JWT
     * @return 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Producto> findAll(@HeaderParam("JWT") String JWT) {
//        try {
//            if (JWT != null) {
//                DecodedJWT token = verificarJWT(JWT);
//                if (token != null) {
//                    return productoFacade.findAll();
//                } else {
//                    return Collections.EMPTY_LIST;
//                }
//            } else {
//                return Collections.EMPTY_LIST;
//            }
//        } catch (Exception e) {
//            return Collections.EMPTY_LIST;
//        }
return productoFacade.findAll();
    }
    
    /**
     * Con este metodo se podran crear productos 
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
                DecodedJWT token = verificarJWT(JWT);
                if (token != null) {
                    JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
                    if (isNullOrEmpty(json.get("idCategoria").getAsString())
                            && isNullOrEmpty(json.get("nombreProducto").getAsString())
                            && isNullOrEmpty(json.get("precio").getAsString())
                            && isNullOrEmpty(json.get("esPreparado").getAsString())) {
                        if (productoFacade.noNombresIguales(json.get("nombreProducto").getAsString())) {
                            if (productoFacade.create(new Producto(null, (categoriaFacade.find(json.get("idCategoria").getAsInt())),
                                    json.get("nombreProducto").getAsString(), json.get("precio").getAsDouble(),
                                    json.get("esPreparado").getAsBoolean()))) {
                                return Response.status(Response.Status.CREATED).header("mensaje", "producto creado con exito").build();
                            } else {
                                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("mensaje", "no se pudo crear el producto").build();
                            }
                        } else {
                            return Response.status(Response.Status.CONFLICT).header("mensaje", "Nombre del producto ya existe").build();
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
     * Metodo para editar los productos no se pueden repetir nombres
     * 
     * @param jsonString
     * @param JWT
     * @return 
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response edit(String jsonString, @HeaderParam("JWT") String JWT) {
        try {
            if (JWT != null) {
                DecodedJWT token = verificarJWT(JWT);
                if (token != null) {
                    JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
                    if (isNullOrEmpty(json.get("idProducto").getAsString())
                            && isNullOrEmpty(json.get("idCategoria").getAsString())
                            && isNullOrEmpty(json.get("nombreProducto").getAsString())
                            && isNullOrEmpty(json.get("precio").getAsString())
                            && isNullOrEmpty(json.get("esPreparado").getAsString())) {
                        if (productoFacade.noNombresIguales(json.get("nombreProducto").getAsString())) {
                            if (productoFacade.edit(new Producto(json.get("idProducto").getAsInt(),
                                    (categoriaFacade.find(json.get("id_categoria").getAsInt())),
                                    json.get("nombreProducto").getAsString(), json.get("precio").getAsDouble(),
                                    json.get("esPreparado").getAsBoolean()))) {
                                return Response.status(Response.Status.OK).header("mensaje", "Se modifico con exito").build();
                            } else {
                                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("mensaje", "No se pudo modificar el producto").build();
                            }
                        } else {
                            return Response.status(Response.Status.CONFLICT).header("mensaje", "Ya existe una categoria con ese nombre").build();
                        }
                    } else {
                        return Response.status(Response.Status.BAD_REQUEST).header("mensaje", "los campos vinieron vacios").build();
                    }
                } else {
                    return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "token no valido").build();
                }
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "No valido sin JWT").build();
            }
        } catch (JsonSyntaxException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("mensaje", "Error dentro del servidor "+e).build();
        }
    }
    
}
