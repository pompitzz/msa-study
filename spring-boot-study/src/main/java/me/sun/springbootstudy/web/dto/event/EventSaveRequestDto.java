package me.sun.springbootstudy.web.dto.event;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sun.springbootstudy.domain.event.Event;
import me.sun.springbootstudy.domain.member.Member;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
public class EventSaveRequestDto {


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;

    private String title;

    private String content;

    @Builder
    public EventSaveRequestDto(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, String title, String content) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.content = content;
    }


    public Event toEntity(Member member) {
        Event event = Event.builder()
                .startDate(startDate)
                .endDate(endDate)
                .startTime(startTime)
                .endTime(endTime)
                .title(title)
                .content(content)
                .build();

        event.setMember(member);
        return event;
    }

}
