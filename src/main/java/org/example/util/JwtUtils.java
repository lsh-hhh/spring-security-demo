package org.example.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.model.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 * @ClassName JwtUtils
 * @Author lsh
 * @Date 2023/7/17 10:51
 */
public class JwtUtils {

    private final static int EXPIRE_TIME_PERIOD = 60;
    private final static String SALT = "you belong to me";

    private JwtUtils() {

    }

    public static String generateToken(User user) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("ipAddress", user.getAddress());

        return Jwts.builder()
                .setSubject("login")
                .claim("id", user.getId())
                .claim("timeStamp", System.currentTimeMillis())
                .claim("username", user.getUsername())
                .claim("ipAddress", user.getIpAddress())
                .setExpiration(new Date(System.currentTimeMillis() + (60 * 1000 * EXPIRE_TIME_PERIOD)))
                .signWith(SignatureAlgorithm.HS256, SALT)
                .compact();
    }

    public static Claims parseToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(SALT)
                .parseClaimsJws(token);
        Claims body = claimsJws
                .getBody();
        if (Objects.isNull(body)) {
            throw new RuntimeException("请先登录");
        }

        return body;
    }

    public static void main(String[] args) {
        Claims user = parseToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsb2dpbiIsImlkIjo5NCwidXNlcm5hbWUiOiJjaGVuMmxpdTIiLCJpcEFkZHJlc3MiOiIxOTIuMTY4LjEuMTk0IiwiZXhwIjoxNjg5NjUyMjU2fQ.-tNwwJbiWGB-Xxu0VY-dtXqMK3BKKtDVzI4rgN6O4SE");
        System.out.println(user);
    }
}
