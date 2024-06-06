package com.project.Authentication;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.project.pojo.User;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFilter implements Filter {
    
    private static final List<String> publicPaths = List.of(
        "/", "/login", "/admin"
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI().replace(request.getContextPath(), ""); // Normalize URI
        String method = request.getMethod();

        User user = (User) request.getSession().getAttribute("user");

        // Allow POST requests to /login-user to proceed without authentication
        if (requestURI.equals("/login-user") && method.equalsIgnoreCase("POST")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        
     // Allow POST requests to /login-user to proceed without authentication
        if (requestURI.equals("/admin-user") && method.equalsIgnoreCase("POST")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // Redirect if trying to access a protected resource and not logged in
        if (!publicPaths.contains(requestURI) && user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
