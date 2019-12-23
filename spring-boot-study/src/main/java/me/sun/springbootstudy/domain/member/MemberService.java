package me.sun.springbootstudy.domain.member;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.web.dto.MemberJoinRequestDto;
import me.sun.springbootstudy.web.dto.MemberResponseDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long save(MemberJoinRequestDto dto) {
        Member member = dto.toEntity();
        member.encodingPassword(passwordEncoder.encode(member.getPassword()));
        return memberRepository.save(member).getId();
    }

    public MemberResponseDto findOne(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        return new MemberResponseDto(member);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return new User(member.getEmail(), member.getPassword(), authorities(member.getRole()));
    }

    private Collection<? extends GrantedAuthority> authorities(MemberRole role) {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.toString()));
    }
}
