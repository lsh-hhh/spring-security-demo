package org.example.security.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.common.Result;
import org.example.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Result<String> res = new Result<>(HttpStatus.FORBIDDEN.value(), "用户权限不足", null);
        WebUtils.renderString(response, objectMapper.writeValueAsString(res));
    }
}
