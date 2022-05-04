package ru.spb.somebet.service.user;

import ru.spb.somebet.model.Bet;
import ru.spb.somebet.model.User;

import java.util.Collection;

public interface UserService {
    User findById(Long id);

    Collection<User> getUsers();

    void addBetToUser(Long userId, Bet bet, float value);

    void saveUser(User user);

    Collection<User> findUsersByBet(Bet bet);
}
