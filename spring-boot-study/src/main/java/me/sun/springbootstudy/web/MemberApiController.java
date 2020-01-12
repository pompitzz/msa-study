package me.sun.springbootstudy.web;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.domain.member.MemberService;
import me.sun.springbootstudy.web.dto.member.MemberJoinRequestDto;
import me.sun.springbootstudy.web.dto.member.MemberResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity errorHandler(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e);
    }

    @PostMapping("/join")
    public ResponseEntity joinMember(@RequestBody @Valid MemberJoinRequestDto dto,
                                     Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        memberService.save(dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity queryMemberRole(@RequestParam("email") String email) {
        MemberResponseDto dto = memberService.findByEmail(email);
        return ResponseEntity.ok(dto);
    }

}
