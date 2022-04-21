package org.example.issuestracker.organizations.command.ui.http.rest.v1;

import org.example.rest.v1.RestErrorAttributes;
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
