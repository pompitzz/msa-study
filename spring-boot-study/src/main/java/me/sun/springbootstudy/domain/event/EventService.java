package me.sun.springbootstudy.domain.event;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.domain.member.Member;
import me.sun.springbootstudy.domain.member.MemberRepository;
import me.sun.springbootstudy.web.dto.event.EventOneResponseDto;
import me.sun.springbootstudy.web.dto.event.EventResponseDto;
import me.sun.springbootstudy.web.dto.event.EventSaveRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventService {

    private final EventRepository eventRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public EventResponseDto save(EventSaveRequestDto dto, String email) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        Event event = dto.toEntity(member);

        eventRepository.save(event);

        return new EventResponseDto(event);
    }

    public EventOneResponseDto findOne(Long eventId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("해당 이벤트가 존재하지 않습니다."));

        return new EventOneResponseDto(event);
    }

    public List<EventResponseDto> findByMonth(String month, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        return null;
    }
}
