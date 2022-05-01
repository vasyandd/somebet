package ru.spb.somebet.repository;

import org.springframework.data.repository.CrudRepository;
import ru.spb.somebet.model.Bet;

import java.util.Collection;

public interface UserRepository extends CrudRepository<Bet, Long> {

    Collection<Bet> findBetsById(Long id);
}
