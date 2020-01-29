package me.sun.springbootstudy.web;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.domain.common.TokenMemberEmail;
import me.sun.springbootstudy.domain.event.EventService;
import me.sun.springbootstudy.web.dto.event.EventOneResponseDto;
import me.sun.springbootstudy.web.dto.event.EventResponseDto;
import me.sun.springbootstudy.web.dto.event.EventSaveRequestDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/events")
public class EventApiController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity saveEvent(@RequestBody EventSaveRequestDto dto,
                                    @TokenMemberEmail String email) {

        EventResponseDto saveDto = eventService.save(dto, email);

        return ResponseEntity.ok(saveDto);
    }

    @GetMapping
    public ResponseEntity queryEvents(@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam LocalDate date,
                                      @TokenMemberEmail String email) {

        if (email == null) return ResponseEntity.ok().build();

        List<EventResponseDto> findDtos = eventService.findByMonthAndMember(date, email);

        return ResponseEntity.ok(findDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity findEvent(@PathVariable Long id) {
        EventOneResponseDto dto = eventService.findOne(id);
        return ResponseEntity.ok(dto);
    }

}
