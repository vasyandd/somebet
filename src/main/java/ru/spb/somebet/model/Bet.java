package ru.spb.somebet.model;

public class Bet {
    private Long id;
    private Float value;
    private Type type;
    private FutureMatch futureMatch;
    private boolean isSuccess;

    public enum Type {
        WIN1TEAM,
        WIN2TEAM,
        DRAW
    }
}
