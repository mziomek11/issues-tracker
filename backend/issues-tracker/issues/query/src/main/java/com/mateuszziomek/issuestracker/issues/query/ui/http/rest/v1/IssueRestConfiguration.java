package com.mateuszziomek.issuestracker.issues.query.ui.http.rest.v1;

import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.error.RestErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IssueRestConfiguration {
    @Bean
    public ErrorAttributes errorAttributes() {
        return new RestErrorAttributes();
    }
}
