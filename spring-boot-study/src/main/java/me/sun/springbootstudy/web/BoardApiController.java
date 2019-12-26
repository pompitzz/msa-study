package me.sun.springbootstudy.web;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.domain.board.BoardService;
import me.sun.springbootstudy.web.dto.BoardResponseDto;
import me.sun.springbootstudy.web.dto.BoardSaveRequestDto;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/boards")
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid BoardSaveRequestDto dto,
                               Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Long savedId = boardService.save(dto);
        URI uri = WebMvcLinkBuilder.linkTo(BoardApiController.class).slash(savedId).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        BoardResponseDto responseDto = boardService.findById(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity findAll() {
        List<BoardResponseDto> boardList = boardService.findAll();
        return ResponseEntity.ok(boardList);
    }
}
