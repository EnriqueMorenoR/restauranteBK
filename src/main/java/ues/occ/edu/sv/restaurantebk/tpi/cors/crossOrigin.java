/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.restaurantebk.tpi.cors;

import java.util.Arrays;
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

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext response) {

        response.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
        response.getHeaders().putSingle("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
        response.getHeaders().putSingle("Access-Control-Allow-Headers", "Content-Type");
        response.getHeaders().putSingle("Access-Control-Expose-Headers", "*");
//        configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
//            "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers"));

    }
    
    
//    @Bean
//CorsConfigurationSource corsConfigurationSource() {
//
//    CorsConfiguration configuration = new CorsConfiguration();
//    configuration.setAllowCredentials(true);
//    configuration.setAllowedOrigins(Arrays.asList(FRONT_END_SERVER));
//    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
//    configuration.setAllowedHeaders(Arrays.asList("X-Requested-With","Origin","Content-Type","Accept","Authorization"));
//
//    // This allow us to expose the headers
//    configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
//            "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers"));
//
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    source.registerCorsConfiguration("/**", configuration);
//    return source;
//}
    
}
