package ru.spb.somebet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spb.somebet.dto.NewMatch;
import ru.spb.somebet.model.FutureMatch;
import ru.spb.somebet.service.match.MatchService;

import java.util.Collection;

@RestController(value = "/all-matches")
public class MatchController {
    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public ResponseEntity<Collection<FutureMatch>> getAllFutureMatches() {
        Collection<FutureMatch> result = matchService.getMatches();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<FutureMatch>> getFutureMatchesByRegion(@RequestAttribute String region) {
        Collection<FutureMatch> result = matchService.getMatchesByRegion(region);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveMatch(@RequestBody NewMatch match) {
        matchService.saveMatch(match);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
