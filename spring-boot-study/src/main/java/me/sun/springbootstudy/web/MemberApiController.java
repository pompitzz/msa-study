package me.sun.springbootstudy.web;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.domain.member.MemberService;
import me.sun.springbootstudy.web.dto.MemberJoinRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<Object> joinMember(@RequestBody @Valid MemberJoinRequestDto dto,
                                             Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        memberService.save(dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/join")
    public String memeber() {
        return "HELLO";
    }
}
