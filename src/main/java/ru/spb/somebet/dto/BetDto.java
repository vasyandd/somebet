package ru.spb.somebet.dto;

import lombok.AllArgsConstructor;
import ru.spb.somebet.model.Bet;
import ru.spb.somebet.model.FutureMatch;

@AllArgsConstructor
public class BetDto {
    private final float value;
    private final Bet.Type type;
}
