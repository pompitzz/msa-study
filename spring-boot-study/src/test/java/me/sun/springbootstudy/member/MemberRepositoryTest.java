package me.sun.springbootstudy.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void saveAndFindMember() throws Exception {
        //given
        Member member = Member.builder()
                .email("email@naver.com")
                .password("password")
                .name("James")
                .role(MemberRole.USER)
                .build();
        memberRepository.save(member);

        //when & then
        Member findMember = memberRepository.findAll().get(0);
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
        String email = "email@naver.com";
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