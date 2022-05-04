package ru.spb.somebet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.spb.somebet.model.Bet;
import ru.spb.somebet.model.FutureMatch;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class BetDto {
    private final Long id;
    private final float value;
    private final Bet.Type type;
}
