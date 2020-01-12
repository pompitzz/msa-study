package me.sun.springbootstudy.domain.board;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.domain.board.repository.BoardRepository;
import me.sun.springbootstudy.domain.comment.CommentService;
import me.sun.springbootstudy.domain.member.Member;
import me.sun.springbootstudy.domain.member.MemberRepository;
import me.sun.springbootstudy.web.dto.board.BoardListResponseDto;
import me.sun.springbootstudy.web.dto.board.BoardOneResponseDto;
import me.sun.springbootstudy.web.dto.board.BoardSaveRequestDto;
import me.sun.springbootstudy.web.dto.board.BoardUpdateRequestDto;
import me.sun.springbootstudy.web.dto.comment.CommentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CommentService commentService;

    @Transactional
    public Long save(BoardSaveRequestDto dto) {
        Member member = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        return boardRepository.save(dto.toEntity(member)).getId();
    }

    public BoardOneResponseDto findBoard(Long id) {
        Board board = boardRepository.findWithMemberBy(id)
                .orElseThrow(() -> new IllegalArgumentException("게시판이 존재하지 않습니다."));

        List<CommentResponseDto> commentResponseDtos = commentService.findCommentsOnlyDepth0(id);
        return new BoardOneResponseDto(board);
    }

    public Page<BoardListResponseDto> findBoards(Pageable pageable) {
        return boardRepository.findAll(pageable).map(BoardListResponseDto::new);
    }

    @Transactional
    public Long update(Long id, BoardUpdateRequestDto dto) {
        Board findBoard = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 존재하지 않습니다."));

        findBoard.update(dto.getTitle(), dto.getContent(), dto.getBoardType());

        return id;
    }

    @Transactional
    public void countBoardViews(Long id) {
        boardRepository.countViewsCount(id);
    }

    public Page<BoardListResponseDto> findBoardsByTitle(String title, Pageable pageable) {
        return boardRepository.findBoardsTitleWithPageable(title, pageable).map(BoardListResponseDto::new);
    }

    public boolean validateBoardMember(Long boardId, String email) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시판이 존재하지 않습니다."));

        return board.getMember().getEmail().equals(email);
    }
}
