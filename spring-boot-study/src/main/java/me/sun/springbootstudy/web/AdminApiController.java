package me.sun.springbootstudy.web;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.domain.member.MemberService;
import me.sun.springbootstudy.web.dto.MemberResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminApiController {

    private final MemberService memberService;

    @RequestMapping(value = "/validate", method = RequestMethod.HEAD)
    public ResponseEntity validateMember() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/members")
    public ResponseEntity queryMembers() {
        List<MemberResponseDto> members = memberService.findMembers();
        return ResponseEntity.ok(members);
    }
}
