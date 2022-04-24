package com.mateuszziomek.issuestracker.organizations.command.ui.http.rest.v1;

import com.mateuszziomek.rest.v1.RestErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrganizationRestConfiguration {
    @Bean
    public ErrorAttributes errorAttributes() {
        return new RestErrorAttributes();
    }
}
