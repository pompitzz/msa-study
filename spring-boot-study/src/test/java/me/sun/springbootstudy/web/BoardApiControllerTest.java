package me.sun.springbootstudy.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.sun.springbootstudy.domain.board.BoardRepository;
import me.sun.springbootstudy.domain.board.BoardService;
import me.sun.springbootstudy.domain.member.MemberRepository;
import me.sun.springbootstudy.domain.member.MemberRole;
import me.sun.springbootstudy.domain.member.MemberService;
import me.sun.springbootstudy.web.dto.BoardSaveRequestDto;
import me.sun.springbootstudy.web.dto.MemberJoinRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.stream.IntStream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BoardApiControllerTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardService boardService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MockMvc mockMvc;

    @Value("${custom.clientId}")
    String clientId;

    @Value("${custom.clientSecret}")
    String clientSecret;

    @BeforeEach
    void clearAll() {
        boardRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void saveBoardWithoutAccessToken() throws Exception {
        //given
        BoardSaveRequestDto dto = BoardSaveRequestDto.builder()
                .title("타이틀")
                .content("내용")
                .author("작성자")
                .build();
        // when && then
        this.mockMvc.perform(post("/api/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void saveBoardWithAccessToken() throws Exception {
        //given
        BoardSaveRequestDto dto = BoardSaveRequestDto.builder()
                .title("타이틀")
                .content("내용")
                .author("작성자")
                .build();
        // when && then
        this.mockMvc.perform(post("/api/boards")
                .header(HttpHeaders.AUTHORIZATION, getJwtToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern("http://localhost/api/boards/{id}"));
    }

    @Test
    void saveBoardWithAccessTokenButInvalidToken() throws Exception {
        //given
        BoardSaveRequestDto dto = BoardSaveRequestDto.builder()
                .title("타이틀")
                .content("내용")
                .author("작성자")
                .build();
        // when && then
        this.mockMvc.perform(post("/api/boards")
                .header(HttpHeaders.AUTHORIZATION, getJwtToken() + "ASD")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }


    String getJwtToken() throws Exception {
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
        ResultActions result = this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(clientId, clientSecret))
                .param("username", email)
                .param("password", password)
                .param("grant_type", "password"));

        String responseBody = result.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        String access_token = parser.parseMap(responseBody).get("access_token").toString();
        return "Bearer " + access_token;
    }

    @Test
    void findBoardAll() throws Exception {
        //given
        IntStream.range(0, 10).forEach(this::saveBoard);

        //when && then
        this.mockMvc.perform(get("/api/boards"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id").exists())
                .andExpect(jsonPath("[0].title").exists())
                .andExpect(jsonPath("[0].content").exists())
                .andExpect(jsonPath("[0].author").exists());
    }


    void saveBoard(int i) {
        BoardSaveRequestDto dto = BoardSaveRequestDto.builder()
                .title("Title" + i)
                .content("Content" + i)
                .author("Author" + i)
                .build();

        boardService.save(dto);
    }
}