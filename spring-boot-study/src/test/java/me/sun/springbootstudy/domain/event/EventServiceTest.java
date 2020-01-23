package me.sun.springbootstudy.domain.event;

import me.sun.springbootstudy.common.BaseControllerTest;
import me.sun.springbootstudy.domain.member.Member;
import me.sun.springbootstudy.domain.member.MemberRepository;
import me.sun.springbootstudy.domain.member.MemberRole;
import me.sun.springbootstudy.web.dto.event.EventResponseDto;
import me.sun.springbootstudy.web.dto.event.EventSaveRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class EventServiceTest extends BaseControllerTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EventService eventService;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    EntityManager em;


    @Test
    @DisplayName("이벤트를 저장하는 테스트")
    void saveAndFindEvent() throws Exception {
        //given
        Member savedMember = saveMember("emaiL@naver.com", "password");

        EventSaveRequestDto dto = EventSaveRequestDto.builder()
                .title("event1")
                .startDate(LocalDate.of(2019, 11, 11))
                .endDate(LocalDate.now())
                .startTime(LocalTime.of(11, 11))
                .endTime(LocalTime.of(12, 00))
                .content("content")
                .build();
        //when
        EventResponseDto responseDto = eventService.save(dto, savedMember.getEmail());

        //then

        System.out.println("responseDto.toString() = " + responseDto.toString());

        assertThat(responseDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(responseDto.getStartDate()).isEqualTo(dto.getStartDate());
        assertThat(responseDto.getEndDate()).isEqualTo(dto.getEndDate());
    }

    @Test
    @DisplayName("이벤트저 권한없는 회원이 저장하는 테스트")
    void saveEventWithOutMember() throws Exception {
        //given

        EventSaveRequestDto dto = EventSaveRequestDto.builder()
                .title("event1")
                .startDate(LocalDate.of(2019, 11, 11))
                .endDate(LocalDate.now())
                .content("content")
                .build();
        //when
        assertThatThrownBy(() -> eventService.save(dto, "temp@naver.com"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("회원이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("없는 이벤트를 조회하는 테스트")
    void findNoExistEvent() throws Exception {
        //when && then
        assertThatThrownBy(() -> eventService.findOne(132L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 이벤트가 존재하지 않습니다.");
    }

    private Member saveMember(String email, String password) {
        Member member = Member.builder()
                .email(email)
                .password(password)
                .name("멤버" + email)
                .role(MemberRole.ADMIN)
                .build();

        return memberRepository.save(member);
    }

}