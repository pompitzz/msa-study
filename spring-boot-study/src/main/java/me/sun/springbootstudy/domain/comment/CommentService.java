package me.sun.springbootstudy.domain.comment;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.domain.board.Board;
import me.sun.springbootstudy.domain.board.repository.BoardRepository;
import me.sun.springbootstudy.domain.member.Member;
import me.sun.springbootstudy.domain.member.MemberRepository;
import me.sun.springbootstudy.web.dto.comment.CommentCreateUpdateResponseDto;
import me.sun.springbootstudy.web.dto.comment.CommentSaveRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;


    @Transactional
    public CommentCreateUpdateResponseDto save(CommentSaveRequestDto dto, String email) {

        // member 찾기
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        // board 찾기
        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 존재하지 않습니다."));

        // parent 찾기
        Comment parent = commentRepository.findById(dto.getParentId()).orElse(null);

        // comment 생성
        Comment comment = Comment.createComment(member, board, parent, dto.getContent());

        // comment 저장
        Comment savedComment = commentRepository.save(comment);

        // commentCount 증가
        board.countOneComment();

        return new CommentCreateUpdateResponseDto(savedComment);
    }

    @Transactional
    public CommentCreateUpdateResponseDto update(Long id, String content, String email) {

        Comment comment = validateCommentMember(id, email);

        comment.update(content);

        return new CommentCreateUpdateResponseDto(comment);
    }

    @Transactional
    public void delete(Long id, String email) {
        Comment comment = validateCommentMember(id, email);

        commentRepository.delete(comment);
    }

    private Comment validateCommentMember(Long id, String email) {
        Comment comment = commentRepository.findByIdWithMember(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if (!comment.getMember().getEmail().equals(email)) {
            throw new IllegalArgumentException("작성한 유저만 수정이 가능합니다");
        }

        return comment;
    }
}


