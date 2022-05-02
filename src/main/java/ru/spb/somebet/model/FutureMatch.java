package ru.spb.somebet.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FutureMatch {
    private Long id;
    private String description;
    private String team1;
    private String team2;
    private Region region;
    private Collection<Bet> bets;
    private LocalDateTime startTime;
}
