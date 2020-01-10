package me.sun.springbootstudy;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
@Getter
@ConfigurationProperties(prefix = "custom")
public class TokenInformation {

    String clientId;

    String clientSecret;

    String jwtKey;

    int accessTokenValidTime;

    int refreshTokenValidTime;

    String[] scopes;

    String[] grantTypes;

}
