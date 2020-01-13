package me.sun.springbootstudy.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sun.springbootstudy.domain.board.BoardAndCommentService;
import me.sun.springbootstudy.domain.board.BoardSaveAndUpdateRequestDtoModel;
import me.sun.springbootstudy.domain.board.BoardService;
import me.sun.springbootstudy.domain.common.TokenMemberEmail;
import me.sun.springbootstudy.web.dto.board.BoardListResponseDto;
import me.sun.springbootstudy.web.dto.board.BoardResponseDtoModel;
import me.sun.springbootstudy.web.dto.board.BoardSaveRequestDto;
import me.sun.springbootstudy.web.dto.board.BoardUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/boards")
public class BoardApiController {

    private final BoardService boardService;
    private final ObjectMapper objectMapper;
    private final BoardAndCommentService boardAndCommentService;

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid BoardSaveRequestDto dto,
                               @TokenMemberEmail String email,
                               Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Long id = boardService.save(dto, email);
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

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBoard(@PathVariable Long id,
                                      @TokenMemberEmail String email) {
        boardService.deleteBoard(id, email);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity queryBoardsByTitle(@RequestParam(value = "title") String title,
                                             Pageable pageable,
                                             PagedResourcesAssembler<BoardListResponseDto> assembler) {

        Page<BoardListResponseDto> boards = boardService.findBoardsByTitle(title, pageable);

        PagedModel<BoardResponseDtoModel> entityModels = assembler.toModel(boards, BoardResponseDtoModel::new);

        return ResponseEntity.ok(entityModels);
    }

    @PostMapping("/modify/{id}")
    public ResponseEntity validateBoardMember(@PathVariable Long id,
                                              @TokenMemberEmail String email) {
        return ResponseEntity.ok(boardService.modifyBoardResponse(id, email));
    }

}
