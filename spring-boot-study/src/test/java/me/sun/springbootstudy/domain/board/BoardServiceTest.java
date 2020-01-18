package me.sun.springbootstudy.domain.board;

import me.sun.springbootstudy.domain.board.repository.BoardRepository;
import me.sun.springbootstudy.domain.member.Member;
import me.sun.springbootstudy.domain.member.MemberRepository;
import me.sun.springbootstudy.domain.member.MemberRole;
import me.sun.springbootstudy.web.dto.board.BoardListResponseDto;
import me.sun.springbootstudy.web.dto.board.BoardUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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

    @Autowired
    EntityManager em;

    @BeforeEach
    public void init() {
        boardRepository.deleteAll();
        memberRepository.deleteAll();
    }

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
    @DisplayName("게시판을 수정하는 테스트")
    void updateBoard() throws Exception {
        //given
        Member member = Member.builder()
                .email("email")
                .password("pass")
                .name("name")
                .role(MemberRole.USER)
                .build();
        memberRepository.save(member);


        Board board = Board.builder()
                .title("hello")
                .content("content")
                .viewsCount(0)
                .boardType(BoardType.STUDY)
                .member(member)
                .build();
        boardRepository.save(board);


        //when
        String updatedTitle = "title";
        String updatedContent = "hoho";
        BoardType updatedBoardType = BoardType.FREE;
        BoardUpdateRequestDto dto = BoardUpdateRequestDto.builder()
                .title(updatedTitle)
                .content(updatedContent)
                .boardType(updatedBoardType)
                .build();

        Long updatedId = boardService.update(board.getId(), dto, member.getEmail());
        em.flush();
        em.clear();

        Board findBoard = boardRepository.findById(updatedId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        //then
        assertThat(findBoard.getTitle()).isEqualTo(updatedTitle);
        assertThat(findBoard.getContent()).isEqualTo(updatedContent);
        assertThat(findBoard.getBoardType()).isEqualTo(updatedBoardType);
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
                .viewsCount(i)
                .boardType(BoardType.STUDY)
                .member(member)
                .build()
        ));
    }


}