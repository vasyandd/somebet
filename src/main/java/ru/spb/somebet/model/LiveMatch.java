package ru.spb.somebet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@Getter
@Setter
public class LiveMatch {
    private String description;
    private String team1;
    private String team2;
    private Region region;
    private Collection<Bet> bets;
    private LocalDateTime startTime;
    private Collection<String> progressMatch = new ArrayList<>();
    private byte[] score = new byte[2];

    public LiveMatch(FutureMatch match) {
        this(match.getDescription(), match.getTeam1(), match.getTeam2(), match.getRegion(),
                match.getBets(), match.getStartTime());
    }

    public LiveMatch(String description, String team1, String team2, Region region,
                     Collection<Bet> bets, LocalDateTime startTime) {
        this.description = description;
        this.team1 = team1;
        this.team2 = team2;
        this.region = region;
        this.bets = bets;
        this.startTime = startTime;
    }

    public int getCurrentMinuteOfMatch() {
        return 0;
    }
}
