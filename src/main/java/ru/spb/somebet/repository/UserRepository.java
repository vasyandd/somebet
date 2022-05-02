package ru.spb.somebet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spb.somebet.model.Bet;
import ru.spb.somebet.model.User;

import java.util.Collection;
import java.util.Collections;

public interface UserRepository extends JpaRepository<User, Long> {
Collection<User> findByBet(Bet bet);
}
