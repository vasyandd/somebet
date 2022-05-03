package ru.spb.somebet.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.spb.somebet.dto.BetDto;
import ru.spb.somebet.dto.LiveMatchDto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@NoArgsConstructor
@Getter
@Setter
public class LiveMatch {
    private String description;
    private String[] teams = new String[2];
    private Region region;
    private Collection<Bet> bets;
    private LocalDateTime startTime;
    private Collection<String> matchProgress = new ArrayList<>();
    private byte[] score = new byte[2];

    public LiveMatch(FutureMatch match) {
        this(match.getDescription(), match.getTeams(), match.getRegion(),
                match.getBets(), match.getStartTime());
    }

    public LiveMatch(String description, String[] teams, Region region,
                     Collection<Bet> bets, LocalDateTime startTime) {
        this.description = description;
        this.teams = teams;
        this.region = region;
        this.bets = bets;
        this.startTime = startTime;
    }

    public static LiveMatchDto modelToDto(LiveMatch liveMatch) {
        List<BetDto> betsDto = new ArrayList<>();
        for (Bet oldBet : liveMatch.bets) {
            betsDto.add(new BetDto(oldBet.getValue(), oldBet.getType()));
        }
        byte[] scoreForDto = Arrays.copyOf(liveMatch.score, 2);
        Collection<String> progressForDto = Collections.unmodifiableCollection(liveMatch.matchProgress);
        return new LiveMatchDto(liveMatch.description, liveMatch.getTeams(),
                liveMatch.region, betsDto, liveMatch.getCurrentMinuteOfMatch(), progressForDto, scoreForDto);
    }

    public void addEvent(String event) {
        matchProgress.add(event);
    }

    public long getCurrentMinuteOfMatch() {
        return ChronoUnit.MINUTES.between(LocalDateTime.now(), startTime);
    }
}
