package com.zsq.spring;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author Aubergine(chinazhaoshuangquan@gmail.com)
 * Created on 2016/6/28 14:32
 */
public class SystemInitInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest arg0,
                                HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
                           Object arg2, ModelAndView arg3) throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object arg2) throws Exception {

        String reqUrl = request.getRequestURI().replace(request.getContextPath(), "");
        if (reqUrl.contains("/login.htm")) {
            return true;
        } else {
            HttpSession session = request.getSession();
            Object obj = session.getAttribute("user");
            if (obj == null || "".equals(obj.toString())) {
                response.sendRedirect("error.jsp");
            }
        }
        return true;
    }
}
