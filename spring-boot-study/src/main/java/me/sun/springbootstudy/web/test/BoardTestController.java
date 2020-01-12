package me.sun.springbootstudy.web.test;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.domain.board.Board;
import me.sun.springbootstudy.domain.board.repository.BoardRepository;
import me.sun.springbootstudy.domain.comment.CommentRepository;
import me.sun.springbootstudy.web.dto.CommentResponseDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class BoardTestController {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    @PersistenceContext
    EntityManager em;

    @PostMapping("/test/v1/{id}")
    public Board test(@PathVariable("id") Long id) {
        return boardRepository.findWithCommentById(id).get();
    }

    @PostMapping("/test/v2/{id}")
    public BoardResponseTestDto testv2(@PathVariable("id") Long id) {
        Board board = boardRepository.findWithFetchById(id).get();

        BoardResponseTestDto boardResponseTestDto = new BoardResponseTestDto(board);

        PageRequest of = PageRequest.of(0, 5);

        Slice<CommentResponseDto> comments2 = commentRepository.findDepth0ByBoardId(board.getId(), of).map(CommentResponseDto::new);
        // 첫번째 자식 넣어줌


//        npul1(board, comments2);

        List<Long> parentIds = getCollecIdList(comments2);
        Map<Long, List<CommentResponseDto>> parentIdsMap = getLongListMap(parentIds);

        for (CommentResponseDto commentResponseDto : comments2) {
            Long commentId = commentResponseDto.getCommentId();
            List<CommentResponseDto> commentResponseDtos = parentIdsMap.get(commentId);
            boolean moreChild = isMoreChild(commentResponseDtos);
            commentResponseDto.setIsMore(moreChild);
            commentResponseDto.setCommentResponseDtos(commentResponseDtos);
        }

        comments2.forEach(c -> c.setCommentResponseDtos(parentIdsMap.get(c.getCommentId())));


        List<CommentResponseDto> childOfChildList = isChildOfChildList(comments2);
        List<Long> collecIdList = getCollecIdList(childOfChildList);
        Map<Long, List<CommentResponseDto>> longListMap = getLongListMap(collecIdList);

        for (CommentResponseDto commentResponseDto : childOfChildList) {
            Long commentId = commentResponseDto.getCommentId();
            List<CommentResponseDto> commentResponseDtos = longListMap.get(commentId);
            boolean moreChild = isMoreChild(commentResponseDtos);
            commentResponseDto.setIsMore(moreChild);
            commentResponseDto.setCommentResponseDtos(commentResponseDtos);
        }

        boardResponseTestDto.setCommentResponseDtoList(comments2);
        return boardResponseTestDto;
    }

    private boolean isMoreChild(List<CommentResponseDto> commentResponseDtos) {
        return commentResponseDtos != null && commentResponseDtos.size() > 0;
    }

    private boolean isMoreChildTest(List<CommentResponseDto> commentResponseDtos) {
        if (commentResponseDtos == null) {
            return false;
        }


        List<Long> parentIds = getCollecIdList(commentResponseDtos);
        Map<Long, List<CommentResponseDto>> parentIdsMap = getLongListMap(parentIds);
        for (CommentResponseDto commentResponseDto : commentResponseDtos) {
            Long commentId = commentResponseDto.getCommentId();
            List<CommentResponseDto> child = parentIdsMap.get(commentId);
            boolean moreChild = isMoreChild(child);
            commentResponseDto.setIsMore(moreChild);
            commentResponseDto.setCommentResponseDtos(child);
        }

        return commentResponseDtos.size() > 0;
    }

    private List<CommentResponseDto> isChildOfChildList(Slice<CommentResponseDto> comments2) {
        List<CommentResponseDto> tempDtos = new ArrayList<>();
        comments2.forEach(i -> {
            List<CommentResponseDto> dtos = i.getCommentResponseDtos();
            if (dtos != null) tempDtos.addAll(dtos);

        });
        return tempDtos;
    }

    private Map<Long, List<CommentResponseDto>> getLongListMap(List<Long> parentIds) {
        List<CommentResponseDto> commentS = em.createQuery(
                "select new me.sun.springbootstudy.web.dto.CommentResponseDto" +
                        "(c.id, c.content, c.createDate, c.depth, c.parent.id)" +
                        " from Comment c" +
                        " where c.parent.id in :parentIds", CommentResponseDto.class)
                .setParameter("parentIds", parentIds)
                .getResultList();

        return commentS
                .stream()
                .collect(Collectors.groupingBy(CommentResponseDto::getParentId));
    }

    private List<Long> getCollecIdList(Slice<CommentResponseDto> comments2) {
        return comments2.stream().map(CommentResponseDto::getCommentId).collect(Collectors.toList());
    }

    private List<Long> getCollecIdList(List<CommentResponseDto> comments2) {
        return comments2.stream().map(CommentResponseDto::getCommentId).collect(Collectors.toList());
    }

    private void npul1(Board board, Slice<CommentResponseDto> comments2) {
//        PageRequest of2 = PageRequest.of(0, 2);
//        for (CommentResponseDto dto : comments2) {
//            // 첫번째 자식에 자식을 넣는다. 지 부모 찾아가지고
//            Slice<CommentResponseDto> comment3 = commentRepository.findDepth0ByPranID(board.getId(), of2).map(CommentResponseDto::new);
//            dto.setCommentResponseDtos(comment3);
//        }
    }
}

@Data
class BoardResponseTestDto {
    private String title;
    private String content;
    private Slice<CommentResponseDto> commentResponseDtoList;
    private String author;

    public BoardResponseTestDto(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.author = board.getMember().getName();
    }

}
