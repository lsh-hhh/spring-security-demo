package org.example.security.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.common.Result;
import org.example.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName AuthenticationEntryPointImpl
 * @Author lsh
 * @Date 2023/7/19 17:56
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Result<String> res = new Result<>(HttpStatus.UNAUTHORIZED.value(), "用户认证失败", null);
        WebUtils.renderString(response, objectMapper.writeValueAsString(res));
    }
}
