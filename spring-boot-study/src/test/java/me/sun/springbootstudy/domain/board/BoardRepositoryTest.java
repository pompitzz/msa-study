package me.sun.springbootstudy.domain.board;

import me.sun.springbootstudy.domain.board.repository.BoardRepository;
import me.sun.springbootstudy.domain.member.Member;
import me.sun.springbootstudy.domain.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void init() {
        boardRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("조회수가 증가하는지 확인하는 테스트")
    void checkViewCount1() throws Exception {
        //given

        Member member = Member.builder()
                .email("email")
                .name("name")
                .password("paa")
                .build();
        memberRepository.save(member);

        Board board = Board.builder()
                .title("Web Server Developement Using SpringBoot And Vue")
                .content("So Hard...")
                .viewsCount(0)
                .boardType(BoardType.STUDY)
                .member(member)
                .build();
        Board savedBoard = boardRepository.save(board);


        //when
        boardRepository.countViewsCount(savedBoard.getId());

        em.flush();
        em.clear();

        //then
        Board findBoard = boardRepository.findById(savedBoard.getId()).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        assertThat(findBoard.getViewsCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("조회가 여러번 있을 때 정확이 조회수가 증가하는지 확인하는 테스트")
    void checkViewCount2() throws Exception {
        //given

        Member member = Member.builder()
                .email("email")
                .name("name")
                .password("paa")
                .build();
        memberRepository.save(member);

        Board board = Board.builder()
                .title("Web Server Developement Using SpringBoot And Vue")
                .content("So Hard...")
                .viewsCount(0)
                .boardType(BoardType.STUDY)
                .member(member)
                .build();
        Board savedBoard = boardRepository.save(board);


        //when
        IntStream.rangeClosed(1, 10).forEach(i -> boardRepository.countViewsCount(savedBoard.getId()));

        em.flush();
        em.clear();
        //then
        Board findBoard = boardRepository.findById(savedBoard.getId()).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        assertThat(findBoard.getViewsCount()).isEqualTo(10);
    }

    @Test
    @DisplayName("타이틀에 포함되는 제목들만 조회하는 쿼리 테스트")
    void findBoardsOnlyTitle() throws Exception {
        //given
        Member member = Member.builder()
                .email("email")
                .name("name")
                .password("paa")
                .build();
        memberRepository.save(member);

        String title1 = "123SpringBoot123";
        String title2 = "1SpringBoot123";
        String title3 = "123SpringBoot1";
        String title4 = "123Spring12Boot23";
        String title5 = "12Spring22Boot123";

        saveBoard(title1, member);
        saveBoard(title2, member);
        saveBoard(title3, member);
        saveBoard(title4, member);
        saveBoard(title5, member);

        //when
        String query = "SpringBoot";


        //then
        List<Board> boardsOnlyTitle = boardRepository.findBoardsTitle(query);
        assertThat(boardsOnlyTitle.size()).isEqualTo(3);
    }


    @Test
    @DisplayName("타이틀에 포함되는 제목들만 페이징하여 조회하는 쿼리 테스트")
    void findBoardsOnlyTitleWithPageable() throws Exception {
        //given
        Member member = Member.builder()
                .email("email")
                .name("name")
                .password("paa")
                .build();
        memberRepository.save(member);

        String title1 = "123SpringBoot123";
        String title2 = "1SpringBoot123";
        String title3 = "123SpringBoot1";
        String title4 = "123Spring12Boot23";
        String title5 = "12Spring22Boot123";

        saveBoard(title1, member);
        saveBoard(title2, member);
        saveBoard(title3, member);
        saveBoard(title4, member);
        saveBoard(title5, member);

        //when
        String query = "SpringBoot";
        PageRequest pageRequest = PageRequest.of(0, 2);

        //then
        Page<Board> boardsOnlyTitle = boardRepository.findBoardsTitleWithPageable(query, pageRequest);
        assertThat(boardsOnlyTitle.getTotalElements()).isEqualTo(5);
        assertThat(boardsOnlyTitle.getTotalPages()).isEqualTo(2);
        assertThat(boardsOnlyTitle.getNumber()).isEqualTo(0);
    }

    private void saveBoard(String title, Member member) {
        boardRepository.save(Board.builder()
                .title(title)
                .content("So Hard...")
                .viewsCount(0)
                .boardType(BoardType.STUDY)
                .member(member)
                .build());
    }

}