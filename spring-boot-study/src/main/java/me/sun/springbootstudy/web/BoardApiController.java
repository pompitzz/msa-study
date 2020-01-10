package me.sun.springbootstudy.web;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.domain.board.BoardResponseDtoModel;
import me.sun.springbootstudy.domain.board.BoardSaveAndUpdateRequestDtoModel;
import me.sun.springbootstudy.domain.board.BoardService;
import me.sun.springbootstudy.web.dto.BoardListResponseDto;
import me.sun.springbootstudy.web.dto.BoardOneResponseDto;
import me.sun.springbootstudy.web.dto.BoardSaveRequestDto;
import me.sun.springbootstudy.web.dto.BoardUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
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
    public ResponseEntity queryBoards(Pageable pageable,
                                      PagedResourcesAssembler<BoardListResponseDto> assembler) {
        Page<BoardListResponseDto> boards = boardService.findBoards(pageable);

        PagedModel<EntityModel<BoardListResponseDto>> entityModels = assembler.toModel(boards, BoardResponseDtoModel::new);
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
