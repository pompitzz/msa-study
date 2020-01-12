package me.sun.springbootstudy;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.domain.board.Board;
import me.sun.springbootstudy.domain.board.BoardType;
import me.sun.springbootstudy.domain.board.repository.BoardRepository;
import me.sun.springbootstudy.domain.comment.Comment;
import me.sun.springbootstudy.domain.comment.CommentRepository;
import me.sun.springbootstudy.domain.comment.CommentService;
import me.sun.springbootstudy.domain.member.Member;
import me.sun.springbootstudy.domain.member.MemberRepository;
import me.sun.springbootstudy.domain.member.MemberRole;
import me.sun.springbootstudy.domain.member.MemberService;
import me.sun.springbootstudy.web.dto.MemberJoinRequestDto;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Component
@Profile("local")
@Transactional
public class AppRunner implements ApplicationRunner {


    private final MemberService memberService;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    @PersistenceContext
    EntityManager em;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        IntStream.rangeClosed(1, 10).forEach(i -> memberService.save(MemberJoinRequestDto.builder()
//                .email("email@gmail.com" + i)
//                .password("password")
//                .name("John" + i)
//                .role(MemberRole.USER)
//                .build()));
//
        Long mem = memberService.save(MemberJoinRequestDto.builder()
                .email("123@123.com")
                .password("123")
                .name("John")
                .role(MemberRole.ADMIN)
                .build());

        Member member = memberRepository.findById(mem).get();

//        IntStream.rangeClosed(1, 10).forEach(i -> boardRepository.save(Board.builder()
//                .title("Web Server Developement Using SpringBoot" + i)
//                .content("So Hard..." + i)
//                .viewsCount(1)
//                .boardType(BoardType.STUDY)
//                .member(member)
//                .build()));

        Board board = Board.builder()
                .title("Web Server Developement Using SpringBoot")
                .content("So Hard...")
                .viewsCount(1)
                .boardType(BoardType.STUDY)
                .member(member)
                .build();

        boardRepository.save(board);
        Comment content1 = Comment.createComment(member, board,
                null, "content1");
        commentRepository.save(content1);

        Comment content2 = Comment.createComment(member, board,
                null, "content1");
        commentRepository.save(content2);

        Comment content3 = Comment.createComment(member, board,
                null, "content3");
        commentRepository.save(content3);

        IntStream.rangeClosed(4, 10).forEach(i -> commentRepository.save(Comment.createComment(member, board,
                null, "content" + i)));

        IntStream.rangeClosed(2, 3).forEach(i -> commentRepository.save(Comment.createComment(member, board,
                content1, "content1 " + i)));

        Comment contentcc1 = Comment.createComment(member, board,
                content1, "content1 1");
        commentRepository.save(contentcc1);
//
        IntStream.rangeClosed(1, 3).forEach(i -> commentRepository.save(Comment.createComment(member, board,
                contentcc1, "content1 1" + i)));


        IntStream.rangeClosed(1, 3).forEach(i -> commentRepository.save(Comment.createComment(member, board,
                content2, "content2 " + i)));
//
        IntStream.rangeClosed(2, 3).forEach(i -> commentRepository.save(Comment.createComment(member, board,
                content3, "content3 " + i)));

        Comment contentcc3 = Comment.createComment(member, board,
                content3, "content1 1");
        commentRepository.save(contentcc3);
        IntStream.rangeClosed(1, 3).forEach(i -> commentRepository.save(Comment.createComment(member, board,
                contentcc3, "content1 3" + i)));


//        IntStream.rangeClosed(1, 30).forEach(i -> boardRepository.save(Board.builder()
//                .title("Web Server Developement Using SpringBoot" + i)
//                .content("So Hard...")
//                .author("DongMyeongLee")
//                .viewsCount(i)
//                .boardType(BoardType.STUDY)
//                .member(member)
//                .build()));

    }
}
