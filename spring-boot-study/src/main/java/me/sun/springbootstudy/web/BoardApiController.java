package me.sun.springbootstudy.web;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.domain.board.BoardResponseDtoModel;
import me.sun.springbootstudy.domain.board.BoardSaveRequestDtoModel;
import me.sun.springbootstudy.domain.board.BoardService;
import me.sun.springbootstudy.web.dto.BoardListResponseDto;
import me.sun.springbootstudy.web.dto.BoardOneResponseDto;
import me.sun.springbootstudy.web.dto.BoardSaveRequestDto;
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
        Long savedId = boardService.save(dto);
        BoardSaveRequestDtoModel boardSaveRequestDtoModel = new BoardSaveRequestDtoModel(savedId);
        return ResponseEntity.ok(boardSaveRequestDtoModel);
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
}
