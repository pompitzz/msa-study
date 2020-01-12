package me.sun.springbootstudy.web.dto.board;

import me.sun.springbootstudy.web.BoardApiController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class BoardResponseDtoModel extends EntityModel<BoardListResponseDto> {

    public BoardResponseDtoModel(BoardListResponseDto content, Link... links) {
        super(content, links);
        add(linkTo(BoardApiController.class).slash(content.getId()).withSelfRel());
    }
}
