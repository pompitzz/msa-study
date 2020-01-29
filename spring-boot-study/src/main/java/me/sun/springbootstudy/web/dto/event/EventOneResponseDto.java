package me.sun.springbootstudy.web.dto.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.sun.springbootstudy.domain.event.Event;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@ToString
public class EventOneResponseDto {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String title;
    private String content;


    public EventOneResponseDto(Event event) {
        this.id = event.getId();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
        this.startTime = event.getStartTime();
        this.endTime = event.getEndTime();
        this.title = event.getTitle();
        this.content = event.getContent();
    }
}
