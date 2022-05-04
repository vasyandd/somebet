package ru.spb.somebet.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "BETS")
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private float value;

    private Type type;

    @ManyToOne
    @JoinColumn(name = "match_id")
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
