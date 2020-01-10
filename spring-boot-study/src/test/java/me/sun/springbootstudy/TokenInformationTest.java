package me.sun.springbootstudy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TokenInformationTest {

    @Autowired
    TokenInformation tokenInformation;

    @Test
    void TokenInformation() throws Exception {
        //given
        String clientId = tokenInformation.getClientId();
        String clientSecret = tokenInformation.getClientSecret();
        String jwtKey = tokenInformation.getJwtKey();
        int accessTokenValidTime = tokenInformation.getAccessTokenValidTime();
        int refreshTokenValidTime = tokenInformation.getRefreshTokenValidTime();
        String[] grantTypes = tokenInformation.getGrantTypes();
        String[] scopes = tokenInformation.getScopes();
        //when && then

        assertThat(clientId).isNotNull();
        assertThat(clientSecret).isNotNull();
        assertThat(jwtKey).isNotNull();
        assertThat(accessTokenValidTime).isNotNull();
        assertThat(refreshTokenValidTime).isNotNull();
        assertThat(grantTypes).isNotNull();
        assertThat(scopes).isNotNull();

        System.out.println("clientId = " + clientId);
        System.out.println("clientSecret = " + clientSecret);
        System.out.println("jwtKey = " + jwtKey);
        System.out.println("accessTokenValidTime = " + accessTokenValidTime);
        System.out.println("refreshTokenValidTime = " + refreshTokenValidTime);
        System.out.println("grantTypes = " + Arrays.toString(grantTypes));
        System.out.println("scopes = " + Arrays.toString(scopes));

    }


}