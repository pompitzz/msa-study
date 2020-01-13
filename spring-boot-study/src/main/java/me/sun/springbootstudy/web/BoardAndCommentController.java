package me.sun.springbootstudy.web;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.domain.board.BoardAndCommentService;
import me.sun.springbootstudy.domain.comment.CommentService;
import me.sun.springbootstudy.domain.common.TokenMemberEmail;
import me.sun.springbootstudy.web.dto.board.BoardAndCommentResponseDto;
import me.sun.springbootstudy.web.dto.comment.CommentCreateUpdateResponseDto;
import me.sun.springbootstudy.web.dto.comment.CommentResponseDto;
import me.sun.springbootstudy.web.dto.comment.CommentSaveRequestDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
public class BoardAndCommentController {

    private final BoardAndCommentService boardAndCommentService;
    private final CommentService commentService;

    @GetMapping("/api/boards/{id}")
    public ResponseEntity findBoardWithComments(@PathVariable Long id, Pageable pageable) {
        BoardAndCommentResponseDto dto = boardAndCommentService.findOneBoard(id, pageable);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/api/comments/{id}")
    public Slice<CommentResponseDto> findComments(@PathVariable Long id, Pageable pageable) {
        return boardAndCommentService.findComments(id, pageable);
    }

    @PostMapping("/api/comments")
    public ResponseEntity createComment(@Valid @RequestBody CommentSaveRequestDto dto,
                                        @TokenMemberEmail String email,
                                        Errors error) {

        if (error.hasErrors()) {
            return ResponseEntity.badRequest().body(error);
        }

        CommentCreateUpdateResponseDto responseDto = commentService.save(dto, email);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/api/comments/{id}")
    public ResponseEntity updateComment(@PathVariable Long id,
                                        @RequestParam String content,
                                        @TokenMemberEmail String email) {

        return ResponseEntity.ok(commentService.update(id, content, email));
    }

    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id,
                                        @TokenMemberEmail String email
    ) {

        commentService.delete(id, email);

        return ResponseEntity.ok().build();
    }
}
