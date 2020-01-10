package me.sun.springbootstudy.domain.board;

import me.sun.springbootstudy.web.BoardApiController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class BoardSaveAndUpdateRequestDtoModel extends EntityModel<Long> {

    public BoardSaveAndUpdateRequestDtoModel(Long content, Link... links) {
        super(content, links);
        add(linkTo(BoardApiController.class).slash(content).withSelfRel());
    }
}
