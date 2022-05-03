package ru.spb.somebet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;
import ru.spb.somebet.dto.LiveMatchDto;
import ru.spb.somebet.service.live.LiveMatchService;

import java.util.Collection;


@RestController(value = "/live")
public class LiveController {
    private final LiveMatchService service;

    public LiveController(LiveMatchService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Collection<LiveMatchDto>> getAllLiveMatches() {
        Collection<LiveMatchDto> matches = service.getAllLiveMatches();
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<LiveMatchDto>> getAllLiveMatchesByRegion(@RequestAttribute String region) {
        Collection<LiveMatchDto> matches = service.getAllLiveMatchesByRegion(region);
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }
}
