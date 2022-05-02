package ru.spb.somebet.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Long id;
    private String description;
    private String team1;
    private String team2;
    private Region region;
    private LocalDateTime endTime;

    public Result(LiveMatch match, int currentMinute) {
        this(null, match.getDescription(), match.getTeam1(), match.getTeam2(),
                match.getRegion(), match.getStartTime().plusMinutes(currentMinute));
    }
}
