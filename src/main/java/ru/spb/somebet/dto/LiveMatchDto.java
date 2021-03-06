package ru.spb.somebet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.spb.somebet.model.Region;

import java.util.Collection;

@AllArgsConstructor
@Getter
public class LiveMatchDto {
    private final String description;
    private final String[] teams;
    private final Region region;
    private final Collection<BetDto> bets;
    private final long currentMinute;
    private final Collection<String> matchProgress;
    private final int[] score;
}
