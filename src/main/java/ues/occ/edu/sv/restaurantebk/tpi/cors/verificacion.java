/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.restaurantebk.tpi.cors;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author root
 */
@Stateless
public class verificacion implements Serializable{
    
    protected String bitPassword256 = "ThWmZq3t6w9z$C&F)J@NcRfUjXn2r5u7";
    
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
    
}
