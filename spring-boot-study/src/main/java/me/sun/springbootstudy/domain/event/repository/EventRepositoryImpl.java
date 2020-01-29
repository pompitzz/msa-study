package me.sun.springbootstudy.domain.event.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.domain.event.Event;

import java.time.LocalDate;
import java.util.List;

import static me.sun.springbootstudy.domain.event.QEvent.event;
import static me.sun.springbootstudy.domain.member.QMember.member;

@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Event> findByMonthAndMemberId(LocalDate date, Long memberId) {
        return queryFactory
                .selectFrom(event)
                .join(event.member, member)
                .where(
                        member.id.eq(memberId),
                        startDateEq(date).or(endDateEq(date))
                )
                .fetch();
    }

    private BooleanExpression startDateEq(LocalDate date) {
        return event.startDate.year().eq(date.getYear())
                .and(event.startDate.month().eq(date.getMonthValue()));
    }

    private BooleanExpression endDateEq(LocalDate date) {
        return event.endDate.year().eq(date.getYear())
                .and(event.endDate.month().eq(date.getMonthValue()));
    }

}
