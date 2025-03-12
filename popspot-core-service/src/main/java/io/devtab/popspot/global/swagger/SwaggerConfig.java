package io.devtab.popspot.global.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.ObjectUtils;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private static final String JWT = "JWT";
    private final Environment environment;

    @Bean
    public OpenAPI openAPI() {
        String activeProfile = "";
        if (!ObjectUtils.isEmpty(environment.getActiveProfiles()) && environment.getActiveProfiles().length >= 1) {
            activeProfile = environment.getActiveProfiles()[0];
        }

        SecurityRequirement securityRequirement = new SecurityRequirement().addList(JWT);

        return new OpenAPI()
            .info(apiInfo(activeProfile))
            .addSecurityItem(securityRequirement)
            .components(securitySchemes());
    }

    private Components securitySchemes() {
        final var securitySchemeAccessToken = new SecurityScheme()
            .name(JWT)
            .type(SecurityScheme.Type.HTTP)
            .scheme("Bearer")
            .bearerFormat("JWT")
            .in(SecurityScheme.In.HEADER)
            .name("Authorization");

        return new Components()
            .addSecuritySchemes(JWT, securitySchemeAccessToken);
    }

    private Info apiInfo(String activeProfile) {
        return new Info()
            .title("PopSpot API (" + activeProfile + ")")
            .version("v1.0.0");
    }
}
