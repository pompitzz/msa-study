package me.sun.springbootstudy.domain.member;

import me.sun.springbootstudy.web.dto.member.MemberJoinRequestDto;
import me.sun.springbootstudy.web.dto.member.MemberResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void joinMemberAndFind() throws Exception {
        //given
        MemberJoinRequestDto joinDto = MemberJoinRequestDto.builder()
                .email("emaq123aszxcasdil@gmail.com")
                .password("password")
                .name("John")
                .role(MemberRole.USER)
                .build();
        //when
        Long savedId = memberService.save(joinDto);
        MemberResponseDto findMemberDto = memberService.findOne(savedId);

        //then
        assertThat(findMemberDto.getEmail()).isEqualTo(joinDto.getEmail());
        assertThat(findMemberDto.getName()).isEqualTo(joinDto.getName());
        assertThat(findMemberDto.getRole()).isEqualTo(joinDto.getRole());
        assertThat(findMemberDto.getId()).isEqualTo(savedId);
    }

    @Test
    void FindNonExistMember() throws Exception {
        //when & then
        assertThatThrownBy(() -> memberService.findOne(201212L))
                .isInstanceOf(IllegalArgumentException.class);
//                .hasMessage("해당 유저가 존재하지 않습니다.");
    }

    @Test
    void passwordEncode() throws Exception {
        //given
        MemberJoinRequestDto joinDto = MemberJoinRequestDto.builder()
                .email("emaasdqweil@gmail.com")
                .password("password")
                .name("John")
                .role(MemberRole.USER)
                .build();
        //when
        Long savedId = memberService.save(joinDto);
        Member member = memberRepository.findById(savedId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        //then
        assertThat(member.getPassword()).isNotEqualTo(joinDto.getPassword());
    }


}