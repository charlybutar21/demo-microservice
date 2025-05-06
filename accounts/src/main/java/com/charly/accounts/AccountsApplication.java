package com.charly.accounts;

import com.charly.accounts.dto.AccountsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {AccountsContactInfoDto.class})
@OpenAPIDefinition(info = @Info(title = "Accounts microservice REST API Documentation", description = "EazyBank Accounts microservice REST API Documentation", version = "v1", contact = @Contact(name = "Charly Micolas", email = "charlymicolas21@gmail.com")), externalDocs = @ExternalDocumentation(description = "EazyBank Accounts microservice REST API Documentation", url = "https://www.eazybytes.com/swagger-ui.html"))
class AccountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }

}
