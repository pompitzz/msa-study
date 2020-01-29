package me.sun.springbootstudy.domain.event;

import me.sun.springbootstudy.common.BaseControllerTest;
import me.sun.springbootstudy.domain.event.repository.EventRepository;
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
import java.util.List;

import static java.time.LocalDate.now;
import static java.time.LocalDate.of;
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
                .startDate(of(2019, 11, 11))
                .endDate(now())
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
                .startDate(of(2019, 11, 11))
                .endDate(now())
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

    @Test
    @DisplayName("전달 매개변수의 날짜의 년도와 개월에 일치하는 이벤트들을 조회하는 테스트")
    void findEventsByDateAndMember() throws Exception {
        //given
        Member savedMember = saveMember("ema1212123il@naver.com", "password");
        EventResponseDto title1 =
                saveEvent(of(2020, 1, 2), of(2020, 1, 5), "title1", savedMember);
        EventResponseDto title2 =
                saveEvent(of(2019, 1, 2), of(2019, 1, 5), "title2", savedMember);
        EventResponseDto title3 =
                saveEvent(of(2020, 2, 2), of(2020, 2, 5), "title3", savedMember);
        EventResponseDto title4 =
                saveEvent(of(2020, 1, 2), of(2020, 2, 5), "title4", savedMember);
        EventResponseDto title5 =
                saveEvent(of(2020, 12, 2), of(2020, 1, 5), "title5", savedMember);

        //when
        List<EventResponseDto> events = eventService.findByMonthAndMember(of(2020, 1, 5), savedMember.getEmail());

        //then
        assertThat(events).
                extracting("title").containsExactly(title1.getTitle(), title4.getTitle(), title5.getTitle());
    }

    @Test
    @DisplayName("날짜가 일치하는 이벤트가 없어 조회가 되지 않는 테스트")
    void findEventsByDateAndMemberRetSize0() throws Exception {
        //given
        Member savedMember = saveMember("email@naver.com", "password");
        EventResponseDto title1 =
                saveEvent(of(2020, 1, 2), of(2020, 1, 5), "title1", savedMember);
        EventResponseDto title2 =
                saveEvent(of(2019, 1, 2), of(2019, 1, 5), "title2", savedMember);
        EventResponseDto title3 =
                saveEvent(of(2020, 2, 2), of(2020, 2, 5), "title3", savedMember);
        EventResponseDto title4 =
                saveEvent(of(2020, 1, 2), of(2020, 2, 5), "title4", savedMember);
        EventResponseDto title5 =
                saveEvent(of(2020, 12, 2), of(2020, 1, 5), "title5", savedMember);

        //when
        List<EventResponseDto> events = eventService.findByMonthAndMember(of(2013, 1, 5), savedMember.getEmail());

        //then
        assertThat(events.size()).isEqualTo(0);
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

    private EventResponseDto saveEvent(LocalDate startDate, LocalDate endDate, String title, Member savedMember) {
        EventSaveRequestDto dto = EventSaveRequestDto.builder()
                .title(title)
                .startDate(startDate)
                .endDate(endDate)
                .startTime(LocalTime.of(11, 11))
                .endTime(LocalTime.of(12, 0))
                .content("content")
                .build();
        return eventService.save(dto, savedMember.getEmail());
    }


}