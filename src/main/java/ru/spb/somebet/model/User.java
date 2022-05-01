package ru.spb.somebet.model;

import java.util.Collection;

public class User {
    private Long id;
    private String name;
    private Integer balance;
    private Collection<Bet> bets;
}
