package hust.tuanpq.finalproject.dronecontrol.jwt;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtility implements Serializable {
	
	@Value("${jwtSecret}")
    private String jwtSecret;
    
    public String generateJwtToken(String uername){
        return Jwts.builder()
                .setSubject(uername)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime()+(100 * 60 * 60 * 24)))
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }
    public boolean validateJwtToken(String authToken)
    {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }catch (SignatureException e) {
//            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
//            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
//            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
//            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
//            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }
}
