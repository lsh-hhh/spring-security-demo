package org.example.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName WebUtils
 * @Author lsh
 * @Date 2023/7/17 15:55
 */
public class WebUtils {

    public static String renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
