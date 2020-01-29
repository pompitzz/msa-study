package me.sun.springbootstudy.domain.event.repository;

import me.sun.springbootstudy.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {

}
