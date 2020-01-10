package me.sun.springbootstudy.domain.board;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.domain.member.Member;
import me.sun.springbootstudy.domain.member.MemberRepository;
import me.sun.springbootstudy.web.dto.BoardListResponseDto;
import me.sun.springbootstudy.web.dto.BoardOneResponseDto;
import me.sun.springbootstudy.web.dto.BoardSaveRequestDto;
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
    public Long save(BoardSaveRequestDto dto) {
        Member member = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        return boardRepository.save(dto.toEntity(member)).getId();
    }

    public BoardOneResponseDto findBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시판이 존재하지 않습니다."));
        return new BoardOneResponseDto(board);
    }

    public Page<BoardListResponseDto> findBoards(Pageable pageable) {
        return boardRepository.findAll(pageable).map(BoardListResponseDto::new);
    }
}
