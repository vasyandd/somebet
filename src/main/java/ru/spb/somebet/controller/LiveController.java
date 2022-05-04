package ru.spb.somebet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spb.somebet.dto.LiveMatchDto;
import ru.spb.somebet.service.live.LiveMatchService;

import java.util.Collection;


@RestController
@RequestMapping("/live")
public class LiveController {
    private final LiveMatchService service;

    public LiveController(LiveMatchService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Collection<LiveMatchDto>> getAllLiveMatches() {
        Collection<LiveMatchDto> matches = service.getLiveMatches();
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }

}
