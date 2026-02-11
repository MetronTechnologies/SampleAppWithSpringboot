package com.fcmb.shared.exception;

import com.fcmb.shared.dto.ApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class ApiResponseWrapper implements ResponseBodyAdvice<Object> {

    // @Override
    // public boolean supports(MethodParameter returnType, Class converterType) {
    // // Wrap all responses except if already ApiResponse
    // String packageName = returnType.getContainingClass().getPackageName();
    // return packageName.startsWith("com.fcmb.mainapplication.controller");
    // }

    @Override
    public boolean supports(MethodParameter returnType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        // Only wrap your application controllers
        String packageName = returnType.getContainingClass().getPackageName();
        return packageName.startsWith("com.fcmb.mainapplication.controller");
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
            MediaType selectedContentType,
            Class selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {

        // Default status
        int status = 200;

        // Try to get actual HTTP status if possible
        if (response instanceof ServletServerHttpResponse servletResponse
                && servletResponse.getServletResponse() != null) {
            status = servletResponse.getServletResponse().getStatus();
        }

        String path = request.getURI().getPath();

        return ApiResponse.success(body, "Request successful", status, path);
    }
}
