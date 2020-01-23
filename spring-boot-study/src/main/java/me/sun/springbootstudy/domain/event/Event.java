package me.sun.springbootstudy.domain.event;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sun.springbootstudy.domain.common.BaseTimeEntity;
import me.sun.springbootstudy.domain.member.Member;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Event extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private LocalTime startTime;

    private LocalTime endTime;

    @Column(nullable = false)
    private String title;

    private String content;


    @Builder
    public Event(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, String title, String content) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.content = content;
    }

    // 연관관계 편의 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getEvents().add(this);
    }

}
