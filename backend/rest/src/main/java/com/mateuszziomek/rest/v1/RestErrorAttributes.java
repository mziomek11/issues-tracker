package com.mateuszziomek.rest.v1;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

public class RestErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        final Map<String, Object> defaultErrorAttributes = super.getErrorAttributes(webRequest, options);

        return new RestErrorResponse((String) defaultErrorAttributes.get("error")).toAttributeMap();
    }
}
