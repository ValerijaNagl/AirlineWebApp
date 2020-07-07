package mvcrest.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import mvcrest.util.Util;
import mvcrest.user.User;

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    static Key key = MacProvider.generateKey();

    public static String generateJWT(User user){
        // JWT-om mozete bezbedono poslati informacije na FE
        // Tako s to sve sto zelite da posaljete zapakujete u claims mapu
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("password", user.getPassword());
        claims.put("user", user);

        return Jwts.builder().setSubject(user.getUsername())
//                .setClaims(claims)
                .setExpiration(new Date(new Date().getTime() + 1000*60*60L))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public static boolean isAuthorized(String token) throws IOException {
        if(!Util.isEmpty(token) && token.contains("Bearer ")){
            String jwt = token.substring(token.indexOf("Bearer ") + 7);
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);

            if(mvcrest.user.UserRepository.findUserByUsername(claims.getBody().getSubject()) != null){
                return true;
            }
        }
        return false;
    }
}
