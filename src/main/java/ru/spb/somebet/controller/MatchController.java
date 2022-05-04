package ru.spb.somebet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spb.somebet.dto.FutureMatchDto;
import ru.spb.somebet.dto.NewMatch;
import ru.spb.somebet.model.Result;
import ru.spb.somebet.service.match.MatchService;
import ru.spb.somebet.service.result.ResultService;

import java.util.Collection;

@RestController
@RequestMapping("/all-matches")
public class MatchController {
    private final MatchService matchService;
    private final ResultService resultService;

    public MatchController(MatchService matchService, ResultService resultService) {
        this.matchService = matchService;
        this.resultService = resultService;
    }

    @GetMapping
    public ResponseEntity<Collection<FutureMatchDto>> getFutureMatches() {
        Collection<FutureMatchDto> matches = matchService.getMatches();
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }

    @GetMapping("/result")
    public ResponseEntity<Collection<Result>> getResults() {
        Collection<Result> results = resultService.getAllResults();
        return new ResponseEntity<>(results, HttpStatus.OK);
    }




    @PostMapping
    public ResponseEntity<?> saveMatch(@RequestBody NewMatch match) {
        matchService.saveMatch(match);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
