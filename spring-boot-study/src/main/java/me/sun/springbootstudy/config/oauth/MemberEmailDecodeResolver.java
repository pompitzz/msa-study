package me.sun.springbootstudy.config.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sun.springbootstudy.domain.common.TokenMemberEmail;
import me.sun.springbootstudy.exception.NotHaveAccessTokenException;
import org.springframework.core.MethodParameter;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class MemberEmailDecodeResolver implements HandlerMethodArgumentResolver {

    private final ObjectMapper objectMapper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isTokenUserEmail = parameter.getParameterAnnotation(TokenMemberEmail.class) != null;
        boolean isString = String.class.equals(parameter.getParameterType());

        return isTokenUserEmail && isString;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authorizationHeader = webRequest.getHeader("Authorization");
        log.info("Authorization Header ::: " + authorizationHeader);

        if (authorizationHeader == null) {
            throw new NotHaveAccessTokenException("Access Token이 존재하지 않습니다.");
        }

        String jwtToken = authorizationHeader.substring(7);
        Jwt decodedToken = JwtHelper.decode(jwtToken);

        Map<String, String> claims = objectMapper.readValue(decodedToken.getClaims(), Map.class);
        String email = claims.get("user_name");

        log.info("Decoded email is ::: " + email);
        return email;
    }
}
