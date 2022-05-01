package ru.spb.somebet.repository;

import org.springframework.data.repository.CrudRepository;
import ru.spb.somebet.model.FutureMatch;

import java.util.Collection;

public interface MatchRepository extends CrudRepository<FutureMatch, Long> {

    Collection<FutureMatch> findAll();

}
