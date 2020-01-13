package me.sun.springbootstudy.config;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.config.oauth.MemberEmailDecodeResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final MemberEmailDecodeResolver memberEmailDecodeResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberEmailDecodeResolver);
    }
}
