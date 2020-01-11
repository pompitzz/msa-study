package me.sun.springbootstudy.domain.member;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberRoleTest {

    @Test
    public void snumTest() throws Exception {
        //given
        String ADMIN = MemberRole.ADMIN.name();
        String USER = MemberRole.USER.name();

        assertThat(ADMIN).isEqualTo("ADMIN");
        assertThat(USER).isEqualTo("USER");
    }

}