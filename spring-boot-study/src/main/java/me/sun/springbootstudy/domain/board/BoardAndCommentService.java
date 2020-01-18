package me.sun.springbootstudy.domain.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sun.springbootstudy.domain.board.repository.BoardRepository;
import me.sun.springbootstudy.domain.comment.CommentRepositodyCustom;
import me.sun.springbootstudy.domain.comment.CommentRepository;
import me.sun.springbootstudy.web.dto.board.BoardAndCommentResponseDto;
import me.sun.springbootstudy.web.dto.comment.CommentResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

    public BoardAndCommentResponseDto findOneBoard(Long boardId, Pageable pageable) {

        long start = System.currentTimeMillis();

        // 게시글 작성한 멤버와 함께 조회 -> 쿼리 1번
        Board board = boardRepository.findWithMemberBy(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 존재하지 않습니다."));

        // dto로 변환
        BoardAndCommentResponseDto boardAndCommentResponseDto = new BoardAndCommentResponseDto(board);

        // 게시글의 최상위 부모 댓글들을 페이징하여 조회 -> 쿼리 1번
        Slice<CommentResponseDto> topParents = commentRepository.findTopPraent(boardId, pageable)
                .map(CommentResponseDto::new);

        /* 자식들을 가져오는걸 재귀로 구현, 각 자식들은 최대 2개만 가져오고 더보기로 더 가져오게 한다.
            -> 쿼리는 자식들이 아무리 많아도 댓글의 계층 + 1번 수행
            -> 즉 총 depth이 3개라면 위의 쿼리랑 합쳐 총 6번 수행
         */
        setChildren(topParents.getContent(), 0, 5);

        // dto로 변환한 인스턴스에 최상위 부모를 추가한다.
        boardAndCommentResponseDto.setCommentResponseDtoList(topParents);

        log.info("계층형 댓글 조회 시간 ::: " + (System.currentTimeMillis() - start));

        return boardAndCommentResponseDto;
    }

    /* 댓글 더보기를 눌렀을 때 추가 댓글 2개만 보여주고 그 댓글의 자식들이 있는지 확인한다.
       - 다 구현했는데.. 클라이언트 단에서 계층속에 있는 댓글을 수정 삭제했을 때 그 값을 처리 후 바로 뷰단에서 보여줄수가 없다..

     */
    public Slice<CommentResponseDto> findComments(Long parentId, Pageable pageable) {

        Slice<CommentResponseDto> commentsDto = commentRepository.findByParentId(parentId, pageable)
                .map(CommentResponseDto::new);

        setChildren(commentsDto.getContent());

        return commentsDto;
    }

    private void setChildren(List<CommentResponseDto> parentList) {

        List<Long> parentIds = mapToParentIds(parentList);
        List<CommentResponseDto> children = commentRepositodyCustom.findChildrenByparentIds(parentIds);
        Map<Long, List<CommentResponseDto>> groupByParentIdComments = groupByParentId(children);

        parentList.forEach(p -> {
            Long parendId = p.getCommentId();
            List<CommentResponseDto> childrenResponseDto = groupByParentIdComments.get(parendId);
            p.setIsMore(isMoreChildren(childrenResponseDto, 0));
        });
    }

    private void setChildren(List<CommentResponseDto> parentList, int from, int to) {

        if (parentList.size() == 0 || from >= to) return;

        List<Long> parentIds = mapToParentIds(parentList);

        List<CommentResponseDto> children = commentRepositodyCustom.findChildrenByparentIds(parentIds);
        Map<Long, List<CommentResponseDto>> groupByParentIdComments = groupByParentId(children);

        List<CommentResponseDto> totalChildrenList = addChildrenAndMakeTotalChildrenList
                (parentList, groupByParentIdComments);

        setChildren(totalChildrenList, from + 1, to);
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

//            p.setIsMore(isMoreChildren(childrenResponseDto, 2));
            p.setChildrenResponseDto(childrenResponseDto);

//            setOnlyTwoChildren(p, childrenResponseDto);
        });

        return totalChildrenList;
    }

//    private void setOnlyTwoChildren(CommentResponseDto parent, List<CommentResponseDto> childrenResponseDto) {
//        if (parent.getIsMore()) {
//            parent.setChildrenResponseDto(childrenResponseDto.subList(0, 2));
//        } else {
//            parent.setChildrenResponseDto(childrenResponseDto);
//        }
//    }

    private List<Long> mapToParentIds(List<CommentResponseDto> topParents) {
        return topParents.stream().map(CommentResponseDto::getCommentId).collect(Collectors.toList());
    }


    private Map<Long, List<CommentResponseDto>> groupByParentId(List<CommentResponseDto> children) {
        return children.stream().collect(Collectors.groupingBy(CommentResponseDto::getParentId));
    }

    private boolean isMoreChildren(List<CommentResponseDto> childrenResponseDto, int number) {
        return childrenResponseDto != null && childrenResponseDto.size() > number;
    }

}
