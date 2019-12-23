package me.sun.springbootstudy;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.domain.member.MemberRole;
import me.sun.springbootstudy.domain.member.MemberService;
import me.sun.springbootstudy.web.dto.MemberJoinRequestDto;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AppRunner implements ApplicationRunner {

    private final MemberService memberService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        MemberJoinRequestDto dto = MemberJoinRequestDto.builder()
                .email("test@gmail.com")
                .password("qwe123")
                .role(MemberRole.USER)
                .name("홍길동")
                .build();
        memberService.save(dto);
    }
}
