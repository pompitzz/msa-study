package me.sun.springbootstudy.apprunner;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.domain.board.Board;
import me.sun.springbootstudy.domain.board.BoardType;
import me.sun.springbootstudy.domain.board.repository.BoardRepository;
import me.sun.springbootstudy.domain.comment.Comment;
import me.sun.springbootstudy.domain.comment.CommentRepository;
import me.sun.springbootstudy.domain.event.Event;
import me.sun.springbootstudy.domain.event.repository.EventRepository;
import me.sun.springbootstudy.domain.member.Member;
import me.sun.springbootstudy.domain.member.MemberRepository;
import me.sun.springbootstudy.domain.member.MemberRole;
import me.sun.springbootstudy.domain.member.MemberService;
import me.sun.springbootstudy.web.dto.member.MemberJoinRequestDto;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.stream.IntStream;

import static java.time.LocalDate.of;

@RequiredArgsConstructor
@Component
@Profile("local")
@Transactional
public class AppRunner implements ApplicationRunner {


    private final MemberService memberService;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Long mem = memberService.save(MemberJoinRequestDto.builder()
                .email("123@123.com")
                .password("123")
                .name("John")
                .role(MemberRole.ADMIN)
                .build());

        Member member = memberRepository.findById(mem).get();

        Board board = Board.builder()
                .title("Web Server Developement Using SpringBoot")
                .content("So Hard...")
                .viewsCount(1)
                .boardType(BoardType.STUDY)
                .member(member)
                .build();
        boardRepository.save(board);


        // 최상위 부모
        Comment content1 = saveComment(member, board, null, "content1");
        Comment content2 = saveComment(member, board, null, "content2");


        // 1 첫번째 자식
        Comment ch1 = saveThreeChildrenAndOneReturn(member, board, content1, 1);
        Comment ch3 = saveThreeChildrenAndOneReturn(member, board, content1, 2);
        Comment ch2 = saveThreeChildrenAndOneReturn(member, board, content2, 1);

        // 2
        Comment chch1 = saveThreeChildrenAndOneReturn(member, board, ch1, 1);
        Comment chch2 = saveThreeChildrenAndOneReturn(member, board, ch2, 1);

        // 3
        Comment chchch1 = saveThreeChildrenAndOneReturn(member, board, chch1, 1);
        Comment chchch2 = saveThreeChildrenAndOneReturn(member, board, chch2, 1);

        Comment content3 = saveComment(member, board, null, "content3");
        Comment content4 = saveComment(member, board, null, "content4");
        Comment content5 = saveComment(member, board, null, "content5");


        saveEvents("블로그 작성", of(2020, 1, 28), of(2020, 1, 30), member);
        saveEvents("회의", of(2019, 12, 20), of(2019, 12, 25), member);
        saveEvents("2월 면접", of(2020, 2, 10), of(2020, 2, 11), member);
        saveEvents("면접 공부", of(2020, 1, 25), of(2020, 2, 2), member);
        saveEvents("책 읽기", of(2020, 1, 2), of(2020, 1, 5), member);
    }

    private Comment saveThreeChildrenAndOneReturn(Member member, Board board, Comment content1, int num) {
        String string = content1.getContent() + " -> " + num;
        Comment comment = Comment.createComment(member, board, content1, string + " -> " + 0);
        commentRepository.save(comment);
        IntStream.rangeClosed(1, 3).forEach(i -> commentRepository.save(Comment.createComment(member, board,
                content1, string + " -> " + i)));

        return comment;
    }

    private Comment saveComment(Member member, Board board, Comment o, String content12) {
        Comment content1 = Comment.createComment(member, board,
                o, content12);
        return commentRepository.save(content1);
    }

    private void saveEvents(String title, LocalDate start, LocalDate end, Member member) {
        Event event = Event.builder()
                .startDate(start)
                .endDate(end)
                .title(title)
                .content("content")
                .build();
        event.setMember(member);
        eventRepository.save(event);
    }
}
