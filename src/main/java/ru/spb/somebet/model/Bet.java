package ru.spb.somebet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
