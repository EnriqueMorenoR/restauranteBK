/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.restaurantebk.tpi.cors;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author enrique
 */
@Provider
public class crossOrigin implements ContainerResponseFilter {
    /**
     * Se permiten los accesos a los headers y a los path 
     * @param requestContext
     * @param response 
     */
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext response) {

        response.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
        response.getHeaders().putSingle("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
        response.getHeaders().putSingle("Access-Control-Allow-Headers", "Content-Type");
        response.getHeaders().putSingle("Access-Control-Expose-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, jwt, mensaje");
        
    }
    
 
}
