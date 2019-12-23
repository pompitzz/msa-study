package me.sun.springbootstudy.member;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.web.dto.MemberJoinRequestDto;
import me.sun.springbootstudy.web.dto.MemberResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long save(MemberJoinRequestDto dto) {
        return memberRepository.save(dto.toEntity()).getId();
    }

    public MemberResponseDto findOne(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        return new MemberResponseDto(member);
    }

}
