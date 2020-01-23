package me.sun.springbootstudy.web;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.domain.common.TokenMemberEmail;
import me.sun.springbootstudy.domain.event.EventService;
import me.sun.springbootstudy.web.dto.event.EventResponseDto;
import me.sun.springbootstudy.web.dto.event.EventSaveRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
