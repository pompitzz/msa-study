package me.sun.springbootstudy.domain.comment;

import me.sun.springbootstudy.domain.board.Board;
import me.sun.springbootstudy.domain.board.repository.BoardRepository;
import me.sun.springbootstudy.domain.member.Member;
import me.sun.springbootstudy.domain.member.MemberRepository;
import me.sun.springbootstudy.domain.member.MemberRole;
import me.sun.springbootstudy.web.dto.comment.CommentCreateUpdateResponseDto;
import me.sun.springbootstudy.web.dto.comment.CommentSaveRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("댓글을 저장하는 테스트")
    void saveComment() throws Exception {
        //given
        Member savedMember = saveMember();

        Board savedBoard = savedBoard(savedMember);

        //when
        IntStream.rangeClosed(1, 5).forEach(i -> commentService.save(CommentSaveRequestDto.builder()
                .boardId(savedBoard.getId())
                .parentId(0L)
                .content("comment" + (i - 1))
                .email(savedMember.getEmail())
                .build()));

        //then
        List<Comment> comments = commentRepository.findAll();

        IntStream.rangeClosed(0, 4).forEach(i -> {
            Comment comment = comments.get(i);
            assertThat(comment.getDepth()).isEqualTo(0);
            assertThat(comment.getContent()).isEqualTo("comment" + i);
            assertThat(comment.getMember().getId()).isEqualTo(savedMember.getId());
            assertThat(comment.getBoard().getId()).isEqualTo(savedBoard.getId());
        });
    }


    @Test
    @DisplayName("댓글의 계층구조를 확인하는 테스트")
    void checkHierarchy() throws Exception {
        //given
        Member member = saveMember();
        Board board = savedBoard(member);
        Comment parent = saveComment(member, board);

        //when
        CommentCreateUpdateResponseDto savedComment = commentService.save(CommentSaveRequestDto.builder()
                .boardId(board.getId())
                .parentId(parent.getId())
                .content("child 1")
                .email(member.getEmail())
                .build());
        //then
        em.flush();
        em.clear();
        Comment childComment = commentRepository.findById(savedComment.getCommentId())
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        Comment findParent = commentRepository.findById(parent.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        assertThat(childComment.getParent().getId()).isEqualTo(parent.getId());
        assertThat(childComment.getDepth()).isEqualTo(1);

        assertThat(findParent.getChild().get(0).getId()).isEqualTo(childComment.getId());
    }


    @Test
    @DisplayName("댓글을 수정하는 이벤트")
    void updateComment() throws Exception {
        //given
        Member savedMember = saveMember();

        Board savedBoard = savedBoard(savedMember);

        CommentCreateUpdateResponseDto savedComment = commentService.save(CommentSaveRequestDto.builder()
                .boardId(savedBoard.getId())
                .parentId(0L)
                .content("comment")
                .email(savedMember.getEmail()).build());

        //when
        String updateStr = "test1";
        CommentUpdateRequestDto dto = CommentUpdateRequestDto.builder()
                .email(savedMember.getEmail())
                .content(updateStr).build();

        //then
        CommentCreateUpdateResponseDto update = commentService.update(savedComment.getCommentId(), dto);
        em.flush();
        em.clear();

        Comment updateComment = commentRepository.findById(update.getCommentId())
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        assertThat(updateComment.getContent()).isEqualTo(updateStr);
        assertThat(updateComment.getId()).isEqualTo(savedComment.getCommentId());
    }

    @Test
    @DisplayName("댓글을 삭제하는 이벤트")
    void deleteComment() throws Exception {
        //given
        Member savedMember = saveMember();

        Board savedBoard = savedBoard(savedMember);

        CommentCreateUpdateResponseDto savedComment = commentService.save(CommentSaveRequestDto.builder()
                .boardId(savedBoard.getId())
                .parentId(0L)
                .content("comment")
                .email(savedMember.getEmail()).build());

        // when && then
        commentService.delete(savedComment.getCommentId(), savedMember.getEmail());
        em.flush();
        em.clear();

        Optional<Comment> findDeletedComment = commentRepository.findById(savedComment.getCommentId());

        assertThat(findDeletedComment.isPresent()).isEqualTo(false);
    }

    @Test
    @DisplayName("댓글을 수정하는데 저장한 사용자가 아닐 때")
    void updateCommentWithInvalidateMember() throws Exception {
        //given
        Member savedMember = saveMember();

        Board savedBoard = savedBoard(savedMember);

        CommentCreateUpdateResponseDto savedComment = commentService.save(CommentSaveRequestDto.builder()
                .boardId(savedBoard.getId())
                .parentId(0L)
                .content("comment")
                .email(savedMember.getEmail()).build());

        //when
        String updateStr = "test1";
        CommentUpdateRequestDto dto = CommentUpdateRequestDto.builder()
                .email("123")
                .content(updateStr).build();

        //then
        assertThatThrownBy(() -> commentService.update(savedComment.getCommentId(), dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("작성한 유저만 수정이 가능합니다");
    }


    @Test
    @DisplayName("댓글을 삭제하는데 저장한 사용자가 아닐 때")
    void deleteCommentWithInvalidateMember() throws Exception {
        //given
        Member savedMember = saveMember();

        Board savedBoard = savedBoard(savedMember);

        CommentCreateUpdateResponseDto savedComment = commentService.save(CommentSaveRequestDto.builder()
                .boardId(savedBoard.getId())
                .parentId(0L)
                .content("comment")
                .email(savedMember.getEmail()).build());

        //when
        String updateStr = "test1";

        //then
        assertThatThrownBy(() -> commentService.delete(savedComment.getCommentId(), "123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("작성한 유저만 수정이 가능합니다");
    }

    private Comment saveComment(Member member, Board board) {
        Comment comment = Comment.createComment(member, board, null, "conent");
        return commentRepository.save(comment);
    }

    private Board savedBoard(Member savedMember) {
        Board board = Board.builder()
                .title("title")
                .content("content")
                .member(savedMember)
                .viewsCount(1)
                .build();

        return boardRepository.save(board);
    }

    private Member saveMember() {
        Member member = Member.builder()
                .name("name")
                .email("email@email.com")
                .password("password")
                .role(MemberRole.USER)
                .build();
        return memberRepository.save(member);
    }

}