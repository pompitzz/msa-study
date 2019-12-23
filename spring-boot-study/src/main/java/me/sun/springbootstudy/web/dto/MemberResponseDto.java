package me.sun.springbootstudy.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sun.springbootstudy.member.Member;
import me.sun.springbootstudy.member.MemberRole;

@Getter
@NoArgsConstructor
public class MemberResponseDto {

    private Long id;
    private String email;
    private String name;
    private MemberRole role;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.role = member.getRole();
    }
}
