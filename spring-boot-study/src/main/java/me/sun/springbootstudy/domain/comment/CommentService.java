package me.sun.springbootstudy.domain.comment;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.domain.board.Board;
import me.sun.springbootstudy.domain.board.repository.BoardRepository;
import me.sun.springbootstudy.domain.member.Member;
import me.sun.springbootstudy.domain.member.MemberRepository;
import me.sun.springbootstudy.web.dto.CommentResponseDto;
import me.sun.springbootstudy.web.dto.CommentSaveRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;


    @Transactional
    public Long save(CommentSaveRequestDto dto) {


        // member 찾기
        Member member = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        // board 찾기
        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 존재하지 않습니다."));

        // parent 찾기
        Comment parent = commentRepository.findById(dto.getParentId()).orElse(null);

        // comment 생성
        Comment comment = Comment.createComment(member, board, parent, dto.getContent());

        // comment 저장
        return commentRepository.save(comment).getId();
    }

    public CommentResponseDto findOne(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        return new CommentResponseDto(comment);
    }


    public List<CommentResponseDto> findCommentsOnlyDepth0(Long boardId) {
        return null;
    }
}
