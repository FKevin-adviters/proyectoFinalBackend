package com.adviters.app.Bootcamp.Security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "security.config")
public class SecurityConfig {

    public static String PREFIX;
    public static String KEY;
    public static Long EXPIRATION;

    public SecurityConfig() {
        PREFIX = "Bearer ";
        KEY = "LA_KEY_SECRETA";
        EXPIRATION = 36000000L;
    }
}
