package me.sun.springbootstudy.domain.board;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.domain.board.repository.BoardRepository;
import me.sun.springbootstudy.domain.member.Member;
import me.sun.springbootstudy.domain.member.MemberRepository;
import me.sun.springbootstudy.web.dto.board.BoardListResponseDto;
import me.sun.springbootstudy.web.dto.board.BoardOneResponseDto;
import me.sun.springbootstudy.web.dto.board.BoardSaveRequestDto;
import me.sun.springbootstudy.web.dto.board.BoardUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(BoardSaveRequestDto dto, String email) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        return boardRepository.save(dto.toEntity(member)).getId();
    }

    public Page<BoardListResponseDto> findBoards(Pageable pageable) {
        return boardRepository.findAll(pageable).map(BoardListResponseDto::new);
    }

    @Transactional
    public Long update(Long id, BoardUpdateRequestDto dto, String email) {
        Board findBoard = boardRepository.findWithMemberBy(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 존재하지 않습니다."));

        isAuthor(findBoard, email);

        findBoard.update(dto.getTitle(), dto.getContent(), dto.getBoardType());

        return id;
    }

    private void isAuthor(Board findBoard, String email) {
        if (!findBoard.getMember().getEmail().equals(email)) {
            throw new IllegalArgumentException("작성자가 아닙니다.");
        }
    }

    @Transactional
    public void countBoardViews(Long id) {
        boardRepository.countViewsCount(id);
    }

    public Page<BoardListResponseDto> findBoardsByTitle(String title, Pageable pageable) {
        return boardRepository.findBoardsTitleWithPageable(title, pageable).map(BoardListResponseDto::new);
    }

    private Board validateBoardMember(Long boardId, String email) {
        Board board = boardRepository.findWithMemberBy(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시판이 존재하지 않습니다."));
        if (!board.getMember().getEmail().equals(email)) {
            throw new IllegalArgumentException("게시글 작성자만 수정이 가능합니다.");
        }
        return board;
    }

    @Transactional
    public void deleteBoard(Long id, String email) {
        Board board = boardRepository.findWithMemberBy(id)
                .orElseThrow(() -> new IllegalArgumentException("게시판이 존재하지 않습니다."));

        isAuthor(board, email);

        boardRepository.delete(board);
    }

    public BoardOneResponseDto modifyBoardResponse(Long id, String email) {
        Board board = this.validateBoardMember(id, email);
        return new BoardOneResponseDto(board);
    }
}
