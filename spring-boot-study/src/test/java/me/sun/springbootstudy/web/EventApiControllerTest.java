package me.sun.springbootstudy.web;

import me.sun.springbootstudy.common.BaseControllerTest;
import me.sun.springbootstudy.domain.event.EventRepository;
import me.sun.springbootstudy.domain.event.EventService;
import me.sun.springbootstudy.domain.member.MemberRepository;
import me.sun.springbootstudy.domain.member.MemberRole;
import me.sun.springbootstudy.domain.member.MemberService;
import me.sun.springbootstudy.web.dto.event.EventSaveRequestDto;
import me.sun.springbootstudy.web.dto.member.MemberJoinRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EventApiControllerTest extends BaseControllerTest {

    @Autowired
    MemberService memberService;

    @Autowired
    EventService eventService;


    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EventRepository eventRepository;

    @AfterEach
    void clearAll() {
        eventRepository.deleteAll();
        memberRepository.deleteAll();
    }


    @Test
    @DisplayName("이벤트를 저장하는 테스트")
    void eventSave() throws Exception {
        //given
        String email = "ema121212il@gmail.com";
        EventSaveRequestDto dto = EventSaveRequestDto.builder()
                .startDate(LocalDate.of(2019, 11, 11))
                .endDate(LocalDate.now())
                .startTime(LocalTime.of(11, 11))
                .endTime(LocalTime.of(11, 11))
                .title("title1")
                .content("content1")
                .build();


        //when && then
        this.mockMvc.perform(post("/api/events")
                .header(HttpHeaders.AUTHORIZATION, getJwtToken(email))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("startDate").exists())
                .andExpect(jsonPath("endDate").exists())
                .andExpect(jsonPath("startTime").exists())
                .andExpect(jsonPath("endTime").exists())
                .andExpect(jsonPath("title").exists())
        ;
    }


    @Test
    @DisplayName("시작, 종료 시간이 없는 이벤트를 저장하는 테스트")
    void eventSaveWithoutTime() throws Exception {
        //given
        String email = "email12@gmail.com";
        EventSaveRequestDto dto = EventSaveRequestDto.builder()
                .startDate(LocalDate.of(2019, 11, 11))
                .endDate(LocalDate.now())
                .title("title1")
                .content("content1")
                .build();


        //when && then
        this.mockMvc.perform(post("/api/events")
                .header(HttpHeaders.AUTHORIZATION, getJwtToken(email))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("startDate").exists())
                .andExpect(jsonPath("endDate").exists())
                .andExpect(jsonPath("startTime").doesNotExist())
                .andExpect(jsonPath("endTime").doesNotExist())
                .andExpect(jsonPath("title").exists())
        ;
    }

    String getJwtToken(String email) throws Exception {
        //given
        String password = "qwe123";
        MemberJoinRequestDto joinDto = MemberJoinRequestDto.builder()
                .email(email)
                .password(password)
                .name("John")
                .role(MemberRole.USER)
                .build();
        memberService.save(joinDto);

        //when & then
        ResultActions result = this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(tokenInformation.getClientId(), tokenInformation.getClientSecret()))
                .param("username", email)
                .param("password", password)
                .param("grant_type", "password"));

        String responseBody = result.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        String access_token = parser.parseMap(responseBody).get("access_token").toString();
        return "Bearer " + access_token;
    }


}