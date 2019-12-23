package me.sun.springbootstudy.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sun.springbootstudy.member.Member;
import me.sun.springbootstudy.member.MemberRole;

@NoArgsConstructor
@Getter
public class MemberJoinRequestDto {

    private String email;
    private String password;
    private String name;
    private MemberRole role;

    @Builder
    public MemberJoinRequestDto(String email, String password, String name, MemberRole role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .role(role)
                .build();
    }
}
