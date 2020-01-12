package me.sun.springbootstudy.web;

import me.sun.springbootstudy.common.BaseControllerTest;
import me.sun.springbootstudy.domain.member.MemberRole;
import me.sun.springbootstudy.domain.member.MemberService;
import me.sun.springbootstudy.web.dto.member.MemberJoinRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminApiControllerTest extends BaseControllerTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("USER ROLE을 가진 사용자가 ADMIN 권한이 필요한 곳을 조회할 때")
    void loginUser() throws Exception {
        //given
        MemberJoinRequestDto joinDto = MemberJoinRequestDto.builder()
                .email("emauikyiiil@gmail.com")
                .password("password")
                .name("John")
                .role(MemberRole.USER)
                .build();
        memberService.save(joinDto);

        //when && then
        this.mockMvc.perform(head("/api/admin/validate")
                .header(HttpHeaders.AUTHORIZATION, getJwtToken(joinDto))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("USER ROLE을 가진 사용자가 ADMIN 권한이 필요한 곳을 GET 요청으로 조회할 때")
    void loginUserWithGet() throws Exception {
        //given
        MemberJoinRequestDto joinDto = MemberJoinRequestDto.builder()
                .email("emai12asdl@gmail.com")
                .password("password")
                .name("John")
                .role(MemberRole.USER)
                .build();
        memberService.save(joinDto);

        //when && then
        this.mockMvc.perform(get("/api/admin/members")
                .header(HttpHeaders.AUTHORIZATION, getJwtToken(joinDto))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test
    @DisplayName("ADMIN ROLE을 가진 사용자가 ADMIN 권한이 필요한 곳을 조회할 때")
    void loginAdmin() throws Exception {
        //given
        MemberJoinRequestDto joinDto = MemberJoinRequestDto.builder()
                .email("emyuiyuiyuiai123l@gmail.com")
                .password("password")
                .name("John")
                .role(MemberRole.ADMIN)
                .build();
        memberService.save(joinDto);

        //when && then
        this.mockMvc.perform(head("/api/admin/validate")
                .header(HttpHeaders.AUTHORIZATION, getJwtToken(joinDto))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("ADMIN이 멤버들 목록을 조회하는 테스트")
    void queryMembers() throws Exception {
        //given
        MemberJoinRequestDto joinDto = MemberJoinRequestDto.builder()
                .email("emai123l@gmail.com")
                .password("password")
                .name("John")
                .role(MemberRole.ADMIN)
                .build();
        memberService.save(joinDto);

        //when && then
        this.mockMvc.perform(head("/api/admin/members")
                .header(HttpHeaders.AUTHORIZATION, getJwtToken(joinDto))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    String getJwtToken(MemberJoinRequestDto joinDto) throws Exception {
        //when & then
        ResultActions result = this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(tokenInformation.getClientId(), tokenInformation.getClientSecret()))
                .param("username", joinDto.getEmail())
                .param("password", joinDto.getPassword())
                .param("grant_type", "password"));

        String responseBody = result.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        String access_token = parser.parseMap(responseBody).get("access_token").toString();
        return "Bearer " + access_token;
    }
}