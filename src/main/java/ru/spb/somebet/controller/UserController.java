package ru.spb.somebet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spb.somebet.model.Bet;
import ru.spb.somebet.model.FutureMatch;
import ru.spb.somebet.service.user.UserService;

import java.util.Collection;

@RestController
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Collection<Bet>> getAllBetsByUser(@PathVariable(name = "id") Long id) {
        Collection<Bet> result = service.getBetsForUser(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> doBet(@PathVariable(name = "id") Long id,
                                   @RequestBody Bet bet, @RequestBody Integer value) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
