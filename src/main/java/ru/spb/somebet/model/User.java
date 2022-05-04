package ru.spb.somebet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    private Float balance;
    @ElementCollection
    @CollectionTable(name = "USER_ACVTIVE_BETS", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyJoinColumn(name = "bet_id")
    @Column(name = "moneyOnBet")
    private Map<Long, Float> bets = new HashMap<>();

    public void addActiveBet(Bet bet, float value) {
        bets.put(bet.getId(), value);
    }
}
