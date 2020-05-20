package ues.occ.edu.sv.restaurantebk.tpi.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.logging.Level;
import java.util.logging.Logger;

public class fatherClassVerify {

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

    /**
     * Se verifica si un String es nulo o esta vacio
     *
     * @param str
     * @return <b>True:</b> Si no es vacío o nulo <br><b>False:</b> Si es vacío
     * o nulo
     */
    public static boolean isNullOrEmpty(String str) {
        return ((str != null) ? (!str.trim().isEmpty()) : (false));
    }
}
