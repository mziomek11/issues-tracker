package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error;

import com.mateuszziomek.issuestracker.shared.ui.ApplicationErrorCode;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

public class RestErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        final Map<String, Object> defaultErrorAttributes = super.getErrorAttributes(webRequest, options);

        return new RestErrorResponse(
            ApplicationErrorCode.GENERIC_UNNAMED_ERROR,
            HttpStatus.valueOf((Integer) defaultErrorAttributes.get("status")),
            (String) defaultErrorAttributes.get("error")
        ).toAttributeMap();
    }
}
