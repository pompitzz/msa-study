package me.sun.springbootstudy.domain.event.repository;

import me.sun.springbootstudy.domain.event.Event;
import me.sun.springbootstudy.domain.member.Member;
import me.sun.springbootstudy.domain.member.MemberRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class EventRepositoryTest {


    @Autowired
    EventRepository eventRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("시작일자 혹은 종료일자가 해당 개월에 포함되면서 멤버아이디 또한 포함되는 이벤트를 조히하는 테스트")
    void findEventsByDate() throws Exception {
        //given
        Member member = Member.builder()
                .email("asd@asd.com")
                .password("password")
                .name("name")
                .role(MemberRole.USER)
                .build();
        em.persist(member);


        Event event1 = getEvent(member, "title1", "content", 1, 1);
        Event event2 = getEvent(member, "title2", "content", 12, 1);
        Event event3 = getEvent(member, "title3", "content", 1, 2);
        Event event4 = getEvent(member, "title4", "content", 2, 2);
        Event event5 = getEvent(member, "title5", "content", 12, 12);
        em.persist(event1);
        em.persist(event2);
        em.persist(event3);
        em.persist(event4);
        em.persist(event5);

        em.flush();
        em.clear();

        //when
        List<Event> findEvent = eventRepository.findByMonthAndMemberId(of(2020, 1, 20), member.getId());

        //then
        assertThat(findEvent).extracting("title").containsExactly("title1", "title2", "title3");
    }


    @Test
    @DisplayName("시작일자 혹은 종료일자가 해당 개월에 포함되면서 멤버아이디 또한 포함되는 이벤트를 조히하는 테스트")
    void findByMonthTest2() throws Exception {
        //given
        Member member = Member.builder()
                .email("asd@asd.com")
                .password("password")
                .name("name")
                .role(MemberRole.USER)
                .build();
        em.persist(member);


        Event event1 = getEvent(member, "title1", "content", 1, 1);
        Event event2 = getEvent(member, "title2", "content", 12, 1);
        Event event3 = getEvent(member, "title3", "content", 1, 2);
        Event event4 = getEvent(member, "title4", "content", 2, 2);
        Event event5 = getEvent(member, "title5", "content", 12, 12);
        em.persist(event1);
        em.persist(event2);
        em.persist(event3);
        em.persist(event4);
        em.persist(event5);

        em.flush();
        em.clear();

        //when
        List<Event> findEvent = eventRepository.findByMonthAndMemberId(of(2020, 12, 20), member.getId());

        //then
        assertThat(findEvent).extracting("title").containsExactly("title2", "title5");
    }


    @Test
    void localDateTest() throws Exception {
        LocalDate date = of(2019, 1, 12);

        assertThat(date.getYear()).isEqualTo(2019);
        assertThat(date.getMonthValue()).isEqualTo(1);
    }


    private Event getEvent(Member member, String title, String content, int startMonth, int endMonth) {
        Event build = Event.builder()
                .startDate(of(2020, startMonth, 1))
                .endDate(of(2020, endMonth, 10))
                .startTime(LocalTime.of(10, 10, 10))
                .endTime(LocalTime.of(15, 10, 10))
                .title(title)
                .content(content)
                .build();
        build.setMember(member);
        return build;
    }


}