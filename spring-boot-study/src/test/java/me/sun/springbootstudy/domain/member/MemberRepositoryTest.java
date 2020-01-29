package me.sun.springbootstudy.domain.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void saveAndFindMember() throws Exception {
        //given
        Member member = Member.builder()
                .email("emqweasdq3213f1232dqweail@naver.com")
                .password("password")
                .name("James")
                .role(MemberRole.USER)
                .build();
        memberRepository.save(member);

        //when & then
        Member findMember = memberRepository.findById(member.getId()).get();
        assertThat(findMember.getEmail()).isEqualTo(member.getEmail());
        assertThat(findMember.getName()).isEqualTo(member.getName());
        assertThat(findMember.getRole()).isEqualTo(MemberRole.USER);
    }

    @Test
    void saveMemberWithNull() throws Exception {
        //when & then
        assertThatThrownBy(() -> memberRepository.save(new Member()))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void findByEmail() throws Exception {
        //given
        String email = "emai123asd123qwel@naver.com";
        Member member = Member.builder()
                .email(email)
                .password("password")
                .name("James")
                .build();
        memberRepository.save(member);

        //when
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        //then
        assertThat(findMember.getEmail()).isEqualTo(email);
    }
}