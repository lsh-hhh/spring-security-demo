package org.example.filter;

import io.jsonwebtoken.Claims;
import org.example.cache.MemoryCache;
import org.example.security.UserDetailsImpl;
import org.example.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

/**
 * @ClassName JwtAuthenticationTokenFilter
 * @Author lsh
 * @Date 2023/7/18 11:23
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private MemoryCache memoryCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        long userId;
        try {
            Claims claims = JwtUtils.parseToken(token);
            if (System.currentTimeMillis() / 1000 - Long.parseLong(claims.get("exp").toString()) > 0) {
                throw new RemoteException("登录已过期，请重新登录");
            }
            userId = Long.parseLong(claims.get("id").toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        String cacheKey = "login:" + userId;
        UserDetailsImpl o = (UserDetailsImpl) memoryCache.get(cacheKey);
        if (Objects.isNull(o)) {
            throw new RuntimeException("用户未登录");
        }

        // 存入securityContextHolder
        /*
        * 传入三个参数，会将认证状态设置为true
        * o ：认证信息
        * authorities: 权限
        * */
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(o, null, o.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
