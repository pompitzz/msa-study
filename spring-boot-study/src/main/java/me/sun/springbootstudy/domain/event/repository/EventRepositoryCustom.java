package me.sun.springbootstudy.domain.event.repository;

import me.sun.springbootstudy.domain.event.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventRepositoryCustom {

    List<Event> findByMonthAndMemberId(LocalDate date, Long memberId);

}
