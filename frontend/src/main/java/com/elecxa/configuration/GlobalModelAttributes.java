package com.elecxa.configuration;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute("currentUri")
    public String addCurrentUri(HttpServletRequest request) {
        return request.getRequestURI();
    }
}
