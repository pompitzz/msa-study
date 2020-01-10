package me.sun.springbootstudy.domain.board;

import me.sun.springbootstudy.domain.member.Member;
import me.sun.springbootstudy.domain.member.MemberRepository;
import me.sun.springbootstudy.domain.member.MemberRole;
import me.sun.springbootstudy.web.dto.BoardListResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void findBoardsWithPageable() throws Exception {

        //given
        makeTestMemberAndBoards();

        PageRequest pageRequest = PageRequest.of(2, 3);

        //when
        Page<BoardListResponseDto> boards = boardService.findBoards(pageRequest);

        //then
        List<BoardListResponseDto> content = boards.getContent();
        long totalElements = boards.getTotalElements();

        assertThat(content.size()).isEqualTo(3);
        assertThat(totalElements).isEqualTo(10);
        assertThat(boards.getTotalPages()).isEqualTo(4);
        assertThat(boards.getNumber()).isEqualTo(2);
        assertThat(content.get(0).getViewsCount()).isEqualTo(7);
    }

    @Test
    void findBoardsWithExceedingPageable() throws Exception {

        //given
        makeTestMemberAndBoards();

        PageRequest pageRequest = PageRequest.of(4, 3);

        //when
        Page<BoardListResponseDto> boards = boardService.findBoards(pageRequest);

        //then
        List<BoardListResponseDto> content = boards.getContent();
        long totalElements = boards.getTotalElements();

        assertThat(content.size()).isEqualTo(0);
        assertThat(totalElements).isEqualTo(10);
        assertThat(boards.getTotalPages()).isEqualTo(4);

    }

    @Test
    void findBoardsWithSortPageable() throws Exception {

        //given
        makeTestMemberAndBoards();

        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "viewsCount"));

        //when
        Page<BoardListResponseDto> boards = boardService.findBoards(pageRequest);

        //then
        List<BoardListResponseDto> content = boards.getContent();
        long totalElements = boards.getTotalElements();

        assertThat(content.size()).isEqualTo(3);
        assertThat(totalElements).isEqualTo(10);
        assertThat(boards.getTotalPages()).isEqualTo(4);
        assertThat(content.get(0).getViewsCount()).isEqualTo(10);

    }

    private void makeTestMemberAndBoards() {
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
                .author("author" + i)
                .viewsCount(i)
                .boardType(BoardType.STUDY)
                .member(member)
                .build()
        ));
    }


}