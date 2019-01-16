package apii.application_practice_2;

import apii.application_practice_2.utility.SessionSchema;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 全局过滤器，用于鉴别登录与否
 */
@WebFilter(filterName = "MyFilter", urlPatterns = {"/information/*","/data_alter/*"})
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("----------------------->过滤器被创建");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("----------------------->过滤器执行");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        System.out.println("filter url: " + uri);

        if (session != null && session.getAttribute(SessionSchema.USER_ID) != null){
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            System.out.println("*****请求已拦截*****");
            JSONObject responseJson = new JSONObject();
            response.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
            responseJson.put("status", "failed");
            responseJson.put("message", "未登录");
            response.getWriter().write(responseJson.toJSONString());
        }
    }

    @Override
    public void destroy() {
        System.out.println("----------------------->过滤器被销毁");
    }
}
