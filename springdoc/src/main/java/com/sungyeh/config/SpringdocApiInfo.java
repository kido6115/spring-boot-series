package com.sungyeh.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Swagger OAS 設定
 *
 * @author sungyeh
 */
@Component
@Data
public class SpringdocApiInfo {

    @Resource
    private OasConfig oasConfig;

    @Bean
    public OpenAPI openApi() {
        SecurityRequirement securityItem = new SecurityRequirement();
        securityItem.put("Client Credentials", new ArrayList<>());
        return new OpenAPI().servers(
                        Collections.singletonList(
                                new Server().url(oasConfig.getUrl())
                        )
                )
                .info(new Info().title("Sungyeh Tech Note")
                        .description("範例Swagger")
                        .version("0.0.1")
                        .contact(new Contact().name(oasConfig.getOwner()).email(oasConfig.getMail()).url(""))
                ).schemaRequirement("Client Credentials", new SecurityScheme()
                        .description("請先透過認證伺服器取得 access token").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))
                .addSecurityItem(securityItem);
    }
}
