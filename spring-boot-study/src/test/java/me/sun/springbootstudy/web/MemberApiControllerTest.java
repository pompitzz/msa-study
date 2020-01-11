package me.sun.springbootstudy.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.sun.springbootstudy.TokenInformation;
import me.sun.springbootstudy.domain.member.MemberRole;
import me.sun.springbootstudy.domain.member.MemberService;
import me.sun.springbootstudy.web.dto.MemberJoinRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MemberApiControllerTest {

    @Autowired
    MemberApiController memberApiController;

    @Autowired
    MemberService memberService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TokenInformation tokenInformation;
    @Test
    void joinMember() throws Exception {
        //given
        MemberJoinRequestDto joinDto = MemberJoinRequestDto.builder()
                .email("emaasdil@gmail.com")
                .password("password")
                .name("John")
                .role(MemberRole.USER)
                .build();

        //when & then
        this.mockMvc.perform(post("/api/members/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(joinDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getJwtToken() throws Exception {
        //given
        String password = "qwe123";
        String email = "email@gmail.com";
        MemberJoinRequestDto joinDto = MemberJoinRequestDto.builder()
                .email(email)
                .password(password)
                .name("John")
                .role(MemberRole.USER)
                .build();
        memberService.save(joinDto);

        //when & then
        this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(tokenInformation.getClientId(), tokenInformation.getClientSecret()))
                .param("username", email)
                .param("password", password)
                .param("grant_type", "password")
        ).andDo(print());
    }


}