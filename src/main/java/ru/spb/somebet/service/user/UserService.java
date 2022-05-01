package ru.spb.somebet.service.user;

import ru.spb.somebet.model.Bet;

import java.util.Collection;

public interface UserService {
    Collection<Bet> getBetsForUser(Long id);
}
