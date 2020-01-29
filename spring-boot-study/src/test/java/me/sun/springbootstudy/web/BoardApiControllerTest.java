package me.sun.springbootstudy.web;

import me.sun.springbootstudy.common.BaseControllerTest;
import me.sun.springbootstudy.domain.board.Board;
import me.sun.springbootstudy.domain.board.BoardService;
import me.sun.springbootstudy.domain.board.BoardType;
import me.sun.springbootstudy.domain.board.repository.BoardRepository;
import me.sun.springbootstudy.domain.event.repository.EventRepository;
import me.sun.springbootstudy.domain.member.Member;
import me.sun.springbootstudy.domain.member.MemberRepository;
import me.sun.springbootstudy.domain.member.MemberRole;
import me.sun.springbootstudy.domain.member.MemberService;
import me.sun.springbootstudy.web.dto.board.BoardSaveRequestDto;
import me.sun.springbootstudy.web.dto.board.BoardUpdateRequestDto;
import me.sun.springbootstudy.web.dto.member.MemberJoinRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.stream.IntStream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BoardApiControllerTest extends BaseControllerTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardService boardService;

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EventRepository eventRepository;


    @BeforeEach
    void clearAll() {
        boardRepository.deleteAll();
        eventRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("AccessToken이 없이 게시판을 저장하는 테스트")
    void saveBoardWithoutAccessToken() throws Exception {
        //given
        BoardSaveRequestDto dto = BoardSaveRequestDto.builder()
                .title("title")
                .content("content")
                .boardType(BoardType.STUDY)
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
    @DisplayName("AccessToken과 함께 게시판을 저장하는 테스트")
    void saveBoardWithAccessToken() throws Exception {
        //given

        String email = "qwe123@gmail.com";
        BoardSaveRequestDto dto = BoardSaveRequestDto.builder()
                .title("title")
                .content("content")
                .boardType(BoardType.STUDY)
                .build();

        // when && then
        this.mockMvc.perform(post("/api/boards")
                .header(HttpHeaders.AUTHORIZATION, getJwtToken(email))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("content").exists())
                .andExpect(jsonPath("_links.self.href").exists())
        ;
    }

    @Test
    @DisplayName("AccessToken과 함께 게시판을 저장하지만 유효하지 않는 토큰일때의 테스트")
    void saveBoardWithAccessTokenButInvalidToken() throws Exception {
        //given
        String email = "qwe123@gmail.com";

        BoardSaveRequestDto dto = BoardSaveRequestDto.builder()
                .title("title")
                .content("content")
                .boardType(BoardType.STUDY)
                .build();


        // when && then
        this.mockMvc.perform(post("/api/boards")
                .header(HttpHeaders.AUTHORIZATION, getJwtToken(email) + "ASD")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }


    @Test
    @DisplayName("게시판 저장 후 수정하는 테스트")
    void updateBoard() throws Exception {
        //given

        MemberJoinRequestDto joinDto = MemberJoinRequestDto.builder()
                .email("email@gmail.com")
                .password("password")
                .name("John")
                .role(MemberRole.USER)
                .build();
        //when
        Long savedId = memberService.save(joinDto);
        Member member = memberRepository.findById(savedId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        Board board = Board.builder()
                .title("Web Server Developement Using SpringBoot And Vue")
                .content("So Hard...")
                .viewsCount(0)
                .boardType(BoardType.STUDY)
                .member(member)
                .build();

        boardRepository.save(board);

        BoardUpdateRequestDto dto = BoardUpdateRequestDto.builder()
                .title("title")
                .content("hoho")
                .boardType(BoardType.FREE)
                .build();

        // when && then
        this.mockMvc.perform(put("/api/boards/{id}", board.getId())
                .header(HttpHeaders.AUTHORIZATION, getJwtToken(joinDto))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("content").exists())
                .andExpect(jsonPath("_links.self.href").exists())
        ;
    }


    @Test
    @DisplayName("게시판을 제목별 페이징 처리하여 조회하는 테스트")
    void findBoardsByTitleWithPageable() throws Exception {
        //given
        saveTestMemberAndBoards();

        //when && then
        this.mockMvc.perform(get("/api/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .param("title", "title1")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id,DESC")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.boardListResponseDtoList[0].id").exists())
                .andExpect(jsonPath("_embedded.boardListResponseDtoList[0].title").exists())
                .andExpect(jsonPath("_embedded.boardListResponseDtoList[0].author").exists())
                .andExpect(jsonPath("_embedded.boardListResponseDtoList[0].createDate").exists())
                .andExpect(jsonPath("_embedded.boardListResponseDtoList[0].viewsCount").exists())
                .andExpect(jsonPath("_embedded.boardListResponseDtoList[0]._links.self.href").exists())
                .andExpect(jsonPath("_links.self.href").exists())
                .andExpect(jsonPath("page.size").exists())
                .andExpect(jsonPath("page.totalElements").exists())
                .andExpect(jsonPath("page.totalPages").exists())
                .andExpect(jsonPath("page.number").exists())
        ;
    }

    @Test
    @DisplayName("게시판을 제목이 빈칸일 때 페이징 처리하여 조회하는 테스트")
    void findBoardsByEmptyTitleWithPageable() throws Exception {
        //given
        saveTestMemberAndBoards();

        //when && then
        this.mockMvc.perform(get("/api/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .param("title", "")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id,DESC")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.boardListResponseDtoList[0].id").exists())
                .andExpect(jsonPath("_embedded.boardListResponseDtoList[0].title").exists())
                .andExpect(jsonPath("_embedded.boardListResponseDtoList[0].author").exists())
                .andExpect(jsonPath("_embedded.boardListResponseDtoList[0].createDate").exists())
                .andExpect(jsonPath("_embedded.boardListResponseDtoList[0].viewsCount").exists())
                .andExpect(jsonPath("_embedded.boardListResponseDtoList[0]._links.self.href").exists())
                .andExpect(jsonPath("_links.self.href").exists())
                .andExpect(jsonPath("page.size").exists())
                .andExpect(jsonPath("page.totalElements").exists())
                .andExpect(jsonPath("page.totalPages").exists())
                .andExpect(jsonPath("page.number").exists());
    }

    @Test
    @DisplayName("게시판을 제목이 존재하지않는 값일때 페이징 처리하여 조회하는 테스트")
    void findBoardsByInVaildTextTitleWithPageable() throws Exception {
        //given
        saveTestMemberAndBoards();

        //when && then
        this.mockMvc.perform(get("/api/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .param("title", "qwdqwsadascascqwe")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id,DESC")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page.size").value(10))
                .andExpect(jsonPath("page.totalElements").value(0))
                .andExpect(jsonPath("page.totalPages").value(0))
                .andExpect(jsonPath("page.number").value(0))
        ;
    }


    private void saveTestMemberAndBoards() {
        Member member = Member.builder()
                .email("email")
                .password("pass")
                .name("name")
                .role(MemberRole.USER)
                .build();
        memberRepository.save(member);


        IntStream.rangeClosed(1, 10).forEach(i -> boardRepository.save(Board.builder()
                .title("title" + i)
                .content("content" + i)
                .viewsCount(i)
                .boardType(BoardType.STUDY)
                .member(member)
                .build()
        ));
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