package org.tomodachi.authetication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.io.PrintWriter;

public class AuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestUri = httpRequest.getRequestURI();

        // Bypass authentication for any requests to the web pages or static resources
        if (requestUri.startsWith("/home") ||
                requestUri.startsWith("/events") ||
                requestUri.startsWith("/css") ||
                requestUri.startsWith("/js") ||
                requestUri.startsWith("/images") ||
                requestUri.equals("/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Apply authentication only for API paths
        if (requestUri.startsWith("/api/")) {
            try {
                Authentication authentication = AuthenticationService.getAuthentication(httpRequest);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception exp) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = httpResponse.getWriter();
                writer.print(exp.getMessage());
                writer.flush();
                writer.close();
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
