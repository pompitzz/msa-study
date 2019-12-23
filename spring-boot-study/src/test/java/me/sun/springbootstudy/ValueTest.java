package me.sun.springbootstudy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ValueTest {

    @Value("${custom.clientId}")
    private String clientId;
    @Value("${custom.clientSecret}")
    private String clientSecret;
    @Value("${custom.jwtKey}")
    private String jwtKey;

    @Test
    void value() throws Exception {
        System.out.println(clientId);
        System.out.println(clientSecret);
        System.out.println(jwtKey);
    }

}
