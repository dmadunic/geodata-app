package com.ag04.geodata.web.rest.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Records duration of each HTTP request along with the information of user it was made
 * for and type of the request.
 *
 * @author dmadunic
 */
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private final Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);
    private static final String UNKNOWN_USER = "UNKNOWN";
    private static final String UNKWNOWN_TYPE = "UNKWNOWN_TYPE";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String user = getUsername(principal);
            MDC.put("username", user);
        } else {
            MDC.put("username", UNKNOWN_USER);
        }
        //if returned false, we need to make sure 'response' is sent
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long duration = System.currentTimeMillis() - (Long) request.getAttribute("startTime");

        Object[] logArguments = new Object[] { request.getMethod(), request.getRequestURI(), duration, response.getStatus() };

        if (duration > 1000) {
            log.warn(">> {} '{}' processed in {}ms [{}]", logArguments);
        } else {
            log.info(">> {} '{}' processed in {}ms [{}]", logArguments);
        }
    }

    private String getUsername(Object principal) {
        if (principal == null) {
            return UNKNOWN_USER;
        }

        if (principal instanceof User) {
            return ((User) principal).getUsername();
        } else if (principal instanceof String) {
            return (String) principal;
        } else {
            return UNKWNOWN_TYPE;
        }
    }
}
