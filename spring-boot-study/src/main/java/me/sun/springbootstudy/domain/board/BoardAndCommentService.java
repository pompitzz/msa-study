package me.sun.springbootstudy.domain.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sun.springbootstudy.domain.board.repository.BoardRepository;
import me.sun.springbootstudy.domain.comment.CommentRepositodyCustom;
import me.sun.springbootstudy.domain.comment.CommentRepository;
import me.sun.springbootstudy.web.dto.board.BoardAndCommentResponseDto;
import me.sun.springbootstudy.web.dto.comment.CommentResponseDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class BoardAndCommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final CommentRepositodyCustom commentRepositodyCustom;

    public BoardAndCommentResponseDto findOneBoard(Long boardId) {
        long start = System.currentTimeMillis();

        Board board = boardRepository.findWithMemberBy(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 존재하지 않습니다."));

        BoardAndCommentResponseDto boardAndCommentResponseDto = new BoardAndCommentResponseDto(board);

        Slice<CommentResponseDto> topParents = getTopParents(boardId);

        setChildren(topParents.getContent());

        boardAndCommentResponseDto.setCommentResponseDtoList(topParents);

        log.info("계층형 댓글 조회 시간 ::: " + (System.currentTimeMillis() - start));
        return boardAndCommentResponseDto;

    }

    private Slice<CommentResponseDto> getTopParents(Long boardId) {
        PageRequest pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "createDate"));

        return commentRepository.findTopPraent(boardId, pageable).map(CommentResponseDto::new);
    }

    private void setChildren(List<CommentResponseDto> parentList) {

        if (parentList.size() == 0) return;

        List<Long> parentIds = mapToParentIds(parentList);

        List<CommentResponseDto> children = commentRepositodyCustom.findChildrenByparentIds(parentIds);
        Map<Long, List<CommentResponseDto>> groupByParentIdComments = groupByParentId(children);

        List<CommentResponseDto> totalChildrenList = addChildrenAndMakeTotalChildrenList
                (parentList, groupByParentIdComments);

        setChildren(totalChildrenList);
    }

    private List<CommentResponseDto> addChildrenAndMakeTotalChildrenList
            (List<CommentResponseDto> parentList, Map<Long, List<CommentResponseDto>> groupByParentIdComments) {

        List<CommentResponseDto> totalChildrenList = new ArrayList<>();

        parentList.forEach(p -> {
            Long parendId = p.getCommentId();
            List<CommentResponseDto> childrenResponseDto = groupByParentIdComments.get(parendId);

            if (childrenResponseDto != null) {
                totalChildrenList.addAll(childrenResponseDto);
            }

            p.setIsMore(isMoreChildren(childrenResponseDto));
            p.setChildrenResponseDto(childrenResponseDto);
        });

        return totalChildrenList;
    }

    private List<Long> mapToParentIds(List<CommentResponseDto> topParents) {
        return topParents.stream().map(CommentResponseDto::getCommentId).collect(Collectors.toList());
    }

    private Map<Long, List<CommentResponseDto>> groupByParentId(List<CommentResponseDto> children) {
        return children.stream().collect(Collectors.groupingBy(CommentResponseDto::getParentId));
    }

    private boolean isMoreChildren(List<CommentResponseDto> childrenResponseDto) {
        return childrenResponseDto != null && childrenResponseDto.size() > 2;
    }

}
