package me.sun.springbootstudy;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
@Getter
@ConfigurationProperties(prefix = "telegram")
public class TelegramInfo {
    private String botUsername;
    private String botToken;
    private String chatId;

}
