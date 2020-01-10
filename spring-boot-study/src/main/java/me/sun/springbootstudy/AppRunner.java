package me.sun.springbootstudy;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.domain.board.Board;
import me.sun.springbootstudy.domain.board.BoardType;
import me.sun.springbootstudy.domain.board.repository.BoardRepository;
import me.sun.springbootstudy.domain.member.Member;
import me.sun.springbootstudy.domain.member.MemberRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@RequiredArgsConstructor
@Component
public class AppRunner implements ApplicationRunner {

    private final MemberRepository memberRepository;

    private final BoardRepository boardRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        Member member = Member.builder()
                .email("email")
                .name("name")
                .password("paa")
                .build();
        memberRepository.save(member);

        IntStream.rangeClosed(1, 50).forEach(i -> {
            boardRepository.save(
                    Board.builder()
                            .title("Web Server Developement Using SpringBoot And Vue" + i)
                            .content("So Hard...")
                            .author("DongMyeongLee")
                            .viewsCount(0)
                            .boardType(BoardType.STUDY)
                            .member(member)
                            .build()
            );
        });

    }
}
