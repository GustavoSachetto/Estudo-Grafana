package com.sachetto.grafana2.filter;

import java.io.IOException;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class RequestIdFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestId = httpRequest.getHeader("X-Request-ID");

        if (requestId != null && !requestId.isEmpty()) {
            MDC.put("x_request_id", requestId);
        }

        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove("x_request_id");
        }
    }
}