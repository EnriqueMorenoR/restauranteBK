package ues.occ.edu.sv.restaurantebk.tpi.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
import javax.ws.rs.core.Response;
import ues.occ.edu.sv.restaurantebk.tpi.entities.Usuario;
import ues.occ.edu.sv.restaurantebk.tpi.facades.UsuarioFacade;

/**
 *
 * @author Cristian
 */
@Stateless
@Path("usuario")
public class UsuarioFacadeREST implements Serializable {

    @PersistenceContext(unitName = "my_persistence_unit")
    protected EntityManager em;
    @Inject
    UsuarioFacade usuarioFacade;

    //Este token es el mismo que en el frontend y es la firma personalizada del sistema y no se debería compartir con nadie
    protected String bitPassword256 = "ThWmZq3t6w9z$C&F)J@NcRfUjXn2r5u7";

    /**
     *
     * @param str Este string se recive como parámetro y se verificará que no
     * sea uno vacío o nulo
     * @return <b>True:</b> Si no es vacío o nulo <br><b>False:</b> Si es vacío
     * o nulo
     */
    public static boolean isNullOrEmpty(String str) {
        return ((str != null) ? (!str.trim().isEmpty()) : (false));
    }

    /**
     * Este método es para crear un nuevo usuario Recibe un json en el cuerpo de
     * la petición el cual contiene toda la información.
     *
     * @param jsonString El cuerpo de la petición.
     * @param JWT es el JWT que lleva en el header la petición enviada. Si el no
     * podrá hacer nada
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registrar(String jsonString, @HeaderParam("JWT") String JWT) {
        try {
            if (JWT != null) {
                DecodedJWT token = verificarJWT(JWT);
                if (token != null) {
                    if (("Administrador").equals(token.getClaim("categoria").asString())) {
                        JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
                        if (isNullOrEmpty(json.get("nombre").getAsString()) && isNullOrEmpty(json.get("apellido").getAsString()) && isNullOrEmpty(json.get("categoria").getAsString()) && isNullOrEmpty(json.get("password").getAsString())) {
                            if (usuarioFacade.noNombresIguales(json.get("nombre").getAsString())) {
                                if (usuarioFacade.create(new Usuario(null, json.get("nombre").getAsString(), json.get("apellido").getAsString(), json.get("categoria").getAsString(), json.get("password").getAsString()))) {
                                    return Response.status(Response.Status.OK).header("mensaje", "Se creo con exito").build();
                                } else {
                                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("mensaje", "No se pudo crear").build();
                                }
                            } else {
                                return Response.status(Response.Status.CONFLICT).header("mensaje", "Usuario existente, pruebe con otro.").build();
                            }
                        } else {
                            return Response.status(Response.Status.BAD_REQUEST).header("mensaje", "Los campos enviados estan vacios y no se puede crear el objeto").build();
                        }

                    } else {
                        return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "No tiene permisos de administrador").build();
                    }
                } else {
                    return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "JWT no valido").build();
                }
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "No autorizado, sin JWT").build();
            }
        } catch (JsonSyntaxException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("mensaje", e).build();
        }

    }

    /**
     * Puede modificarlo el admin o el mismo usuario. Tiene que enviar todos los
     * datos de la entidad para poder editarlo.
     *
     * @param jsonString Es el cuerpo de la solicitud
     * @param JWT Es el header que tiene el JWT
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
                    if (isNullOrEmpty(json.get("idUsuario").getAsString()) && isNullOrEmpty(json.get("nombre").getAsString()) && isNullOrEmpty(json.get("apellido").getAsString()) && isNullOrEmpty(json.get("categoria").getAsString()) && isNullOrEmpty(json.get("password").getAsString())) {
                        if (usuarioFacade.noNombresIguales(json.get("nombre").getAsString())) {
                            if (("Administrador").equals(token.getClaim("categoria").asString())) {
                                if (usuarioFacade.edit(new Usuario((Integer) json.get("idUsuario").getAsInt(), json.get("nombre").getAsString(), json.get("apellido").getAsString(), json.get("categoria").getAsString(), json.get("password").getAsString()))) {
                                    return Response.status(Response.Status.OK).header("mensaje", "Se edito con exito").build();
                                } else {
                                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("mensaje", "No se pudo editar").build();
                                }
                            } else {
                                return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "No tiene permisos para editar el usuario").build();
                            }
                        } else {
                            return Response.status(Response.Status.CONFLICT).header("mensaje", "Usuario existente, pruebe con otro.").build();
                        }
                    } else {
                        return Response.status(Response.Status.BAD_REQUEST).header("mensaje", "Los campos enviados estan vacios y no se puede editar el objeto").build();
                    }
                } else {
                    return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "JWT no valido").build();
                }
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "No autorizado, sin JWT").build();
            }
        } catch (JsonSyntaxException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("mensaje", e).build();
        }
    }

    /**
     * Este método elimina al usuario, sólo lo pueden hacer administradores. Se
     * necesita el id para eliminar al usuario.
     *
     * @param id Es el id del usuario a eliminar
     * @param JWT Es el header que tiene el JWT
     * @return
     */
    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") String id, @HeaderParam("JWT") String JWT) {
        try {
            if (JWT != null) {
                DecodedJWT token = verificarJWT(JWT);
                if (token != null) {
                    if (isNullOrEmpty(id)) {
                        if (("Administrador").equals(token.getClaim("categoria").asString())) {
                            if (usuarioFacade.remove(usuarioFacade.find((Integer) Integer.parseInt(id.trim())))) {
                                return Response.status(Response.Status.OK).header("mensaje", "Se elimino con exito").build();
                            } else {
                                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("mensaje", "No se pudo eliminar").build();
                            }
                        } else {
                            return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "No tiene permisos para eliminar el usuario").build();
                        }
                    } else {
                        return Response.status(Response.Status.BAD_REQUEST).header("mensaje", "El id del objeto es vacio, por lo tanto no se puede eliminar").build();
                    }
                } else {
                    return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "JWT no valido").build();
                }
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "No autorizado, sin JWT").build();
            }
        } catch (JsonSyntaxException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("mensaje", e).build();
        }
    }

    /**
     * Este método sirve para autenticar a los usuarios y les asigna un JWT con
     * el que podrán acceder a los métodos de esta API
     *
     * @param jsonString Es el cuerpo de la petición enviada. Contiene usuario y
     * contraseña
     * @return
     * @throws JsonParseException
     */
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(String jsonString) throws JsonParseException {
        try {
            JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
            String username = json.get("nombre").getAsString();
            String password = json.get("password").getAsString();
            if (isNullOrEmpty(username) && isNullOrEmpty(password)) {
                Object usuario = usuarioFacade.buscarPorNombreYContrasenia(username, password);
                if (usuario == null) {
                    return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "No autorizado").build();
                } else {
                    Usuario a = (Usuario) usuario;
                    return Response.status(Response.Status.OK).header("JWT", crearJWT(a.getNombre(), a.getCategoria())).entity(a.getIdUsuario()).build();
                }
            } else {
                return Response.status(Response.Status.BAD_REQUEST).header("mensaje", "Los campos enviados estan vacios y no se pueden verificar").build();
            }
        } catch (JsonSyntaxException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("mensaje", e).build();
        }
    }

    /**
     * Este método sirve para crear un JWT el cual puede ser usado durante todo
     * el tiempo que se desee, y este mismo puede dar acceso a todo el API.
     *
     * @param nombre Es el nombre del usuario
     * @param role La categoria del usuario (si es admin, chef, mesero, e.t.c.)
     * @return Un string que contiene el JWT
     */
    public String crearJWT(String nombre, String role) {
        try {
            return JWT.create()
                    .withIssuer("TPI")
                    .withClaim("nombre", nombre)
                    .withClaim("categoria", role)
                    .withClaim("fecha", new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                    .sign(Algorithm.HMAC256(bitPassword256));
        } catch (JWTCreationException e) {
            //Invalid Signing configuration / Couldn't convert Claims.
        }
        return null;
    }

    /**
     * Este método verifica la integridad del JWT, que no se haya cambiado
     * (modificado) y que tenga la firma secreta de este backend.
     *
     * @param token
     * @return
     */
    public DecodedJWT verificarJWT(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(bitPassword256))
                    .withIssuer("TPI")
                    .build()
                    .verify(token);
        } catch (JWTVerificationException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }

    /**
     * Este método sirver para traer todos los usuarios pero sin contraseña,
     * sólo administradores pueden acceder a él
     *
     * @param JWT Es el JSON Web Token del administrador
     * @return una lista de usuarios sin la contraseña
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(@HeaderParam("JWT") String JWT) {
        try {
            if (JWT != null) {
                DecodedJWT token = verificarJWT(JWT);
                if (token != null) {
                    if (("Administrador").equals(token.getClaim("categoria").asString())) {
                        List<Object> field1List = usuarioFacade.findAll().stream().map(usuario -> "{\"nombre\":\"" + usuario.getNombre() + "\",\"apellido\":\"" + usuario.getApellido() + "\",\"categoria\":\"" + usuario.getCategoria() + "\",\"idUsuario\":" + usuario.getIdUsuario() + "}").collect(Collectors.toList());
                        return Response.ok().header("mensaje", "Success").entity(field1List.toString()).type(MediaType.APPLICATION_JSON).build();
                    } else {
                        return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "No tiene permisos para ver todos los usuarios").build();
                    }
                } else {
                    return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "JWT no valido").build();
                }
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "No autorizado, sin JWT").build();
            }
        } catch (JsonSyntaxException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("mensaje", e).build();
        }
    }

    /**
     * Este método es para paginar datos, poder hacer llamadas al sistema sin
     * que traiga todos los datos
     *
     * @param from Desde que id de usuario se quiere
     * @param to Cuantos usuarios quiere de regreso
     * @param JWT Es el JSON Web Token
     * @return Una lista de usuarios en elñ rango seleccionado
     */
    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findRange(@PathParam("from") Integer from, @PathParam("to") Integer to, @HeaderParam("JWT") String JWT) {
        try {
            if (JWT != null) {
                DecodedJWT token = verificarJWT(JWT);
                if (token != null) {
                    if (("Administrador").equals(token.getClaim("categoria").asString())) {
                        List<Object> field1List = usuarioFacade.findRange(from, to).stream().map(usuario -> "{\"nombre\":" + usuario.getNombre() + ",\"apellido\":" + usuario.getApellido() + ",\"categoria\":" + usuario.getCategoria() + ",\"idUsuario\":" + usuario.getIdUsuario() + "}").collect(Collectors.toList());
                        return Response.ok().header("mensaje", "Success").entity(field1List.toString()).type(MediaType.APPLICATION_JSON).build();
                    } else {
                        return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "No tiene permisos para ver todos los usuarios").build();
                    }
                } else {
                    return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "JWT no valido").build();
                }
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "No autorizado, sin JWT").build();
            }
        } catch (JsonSyntaxException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("mensaje", e).build();
        }
    }

    @GET
    @Path("cuantosUsuarios")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(usuarioFacade.count());
    }

    @GET
    @Path("checkJWT/{jwt}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response checkJWT(@PathParam("jwt") String JWT) {
        try {
            if (JWT != null) {
                DecodedJWT token = verificarJWT(JWT);
                if (token != null) {
                    return Response.status(Response.Status.OK).header("mensaje", "Es un token valido").entity("valido").build();
                } else {
                    return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "JWT no valido").build();
                }
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).header("mensaje", "No autorizado, sin JWT").build();
            }
        } catch (JsonSyntaxException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("mensaje", e).build();
        }
    }
}
