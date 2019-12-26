package me.sun.springbootstudy.domain.board;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.web.dto.BoardResponseDto;
import me.sun.springbootstudy.web.dto.BoardSaveRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long save(BoardSaveRequestDto dto) {
        return boardRepository.save(dto.toEntity()).getId();
    }

    public BoardResponseDto findById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시판이 존재하지 않습니다."));
        return new BoardResponseDto(board);
    }

    public List<BoardResponseDto> findAll() {
        return boardRepository.findAll().stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
    }
}
