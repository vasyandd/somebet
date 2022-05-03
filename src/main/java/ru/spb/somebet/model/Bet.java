package ru.spb.somebet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Bet {
    private Long id;
    private float value;
    private Type type;
    private FutureMatch futureMatch;
    private boolean isSuccess;

    public enum Type {
        WIN1TEAM,
        WIN2TEAM,
        DRAW
    }

    public static Map<Type, Bet> groupByType(Collection<Bet> bets) {
        return bets.stream().collect(Collectors.toMap(b -> b.type, b -> b));
    }
}
