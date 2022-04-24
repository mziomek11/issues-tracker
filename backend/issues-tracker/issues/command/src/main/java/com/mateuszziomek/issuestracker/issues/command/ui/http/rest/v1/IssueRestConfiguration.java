package com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1;

import com.mateuszziomek.rest.v1.RestErrorAttributes;
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
