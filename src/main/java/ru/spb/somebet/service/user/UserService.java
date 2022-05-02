package ru.spb.somebet.service.user;

import ru.spb.somebet.model.Bet;

import java.util.Collection;

public interface UserService {
    Collection<Bet> findById(Long id);

    void payUsersByWinBet(Bet bet);
}
