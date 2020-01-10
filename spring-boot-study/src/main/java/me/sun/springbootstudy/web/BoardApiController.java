package me.sun.springbootstudy.web;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.domain.board.BoardSaveAndUpdateRequestDtoModel;
import me.sun.springbootstudy.domain.board.BoardService;
import me.sun.springbootstudy.web.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        Long id = boardService.save(dto);

        return ResponseEntity.ok(new BoardSaveAndUpdateRequestDtoModel(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateBoard(@PathVariable Long id,
                                      @RequestBody BoardUpdateRequestDto dto,
                                      Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        boardService.update(id, dto);

        return ResponseEntity.ok(new BoardSaveAndUpdateRequestDtoModel(id));
    }

    @PutMapping("/count/{id}")
    public ResponseEntity countViews(@PathVariable Long id) {
        boardService.countBoardViews(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        BoardOneResponseDto responseDto = boardService.findBoard(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity queryBoardsByTitle(@RequestParam(value = "title") String title,
                                             Pageable pageable,
                                             PagedResourcesAssembler<BoardListResponseDto> assembler) {

        Page<BoardListResponseDto> boards = boardService.findBoardsByTitle(title, pageable);

        PagedModel<BoardResponseDtoModel> entityModels = assembler.toModel(boards, BoardResponseDtoModel::new);

        return ResponseEntity.ok(entityModels);
    }

    @GetMapping("/validate")
    public ResponseEntity validateBoardMember(@RequestParam("boardId") Long boardId,
                                              @RequestParam("email") String email) {
        if (!boardService.validateBoardMember(boardId, email)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
