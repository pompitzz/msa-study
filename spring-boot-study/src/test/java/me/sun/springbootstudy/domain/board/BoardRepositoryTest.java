package me.sun.springbootstudy.domain.board;

import me.sun.springbootstudy.domain.member.Member;
import me.sun.springbootstudy.domain.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
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
                .author("DongMyeongLee")
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
                .author("DongMyeongLee")
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

}