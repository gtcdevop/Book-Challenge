package com.paypal.challange.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@WebFilter("/*")
@Order(1) // Define order if you have multiple filters
public class ErrorHandlingFilter implements Filter {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception  e) {

            // custom error response class used across my project
            ErrorResponse errorResponse = new ErrorResponse();
            try {
                errorResponse.setMessage(((ServletException) e).getRootCause().getMessage());
            } catch(Exception exc) {
                errorResponse.setMessage(e.getMessage());
            }
            errorResponse.setException(e.getClass().getName());
            log.error("Spring Security Filter Chain Exception:", e);
            // set status response on ServletResponse
            ((HttpServletResponse)response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ((HttpServletResponse)response).setHeader("Content-Type", "application/json");
            (response).getWriter().write(convertObjectToJson(errorResponse));
        }
    }

    @Data
    public static class ErrorResponse {
        String message;
        String exception;
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
