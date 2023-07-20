package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.cache.MemoryCache;
import org.example.model.User;
import org.example.security.UserDetailsImpl;
import org.example.util.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @ClassName LoginService
 * @Author lsh
 * @Date 2023/7/18 9:41
 */
@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemoryCache memoryCache;
    private final AuthenticationManager authenticationManager;

    public String login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();
        String token = JwtUtils.generateToken(userDetails.getUser());
        memoryCache.put("login:"+ userDetails.getUser().getId(), userDetails);
        return token;
    }

    public void logout() {
        // 获取SecurityContextHolder中的用户id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        if (Objects.isNull(principal)) {
            return;
        }
        memoryCache.remove("login:" + principal.getUser().getId());
    }
}
