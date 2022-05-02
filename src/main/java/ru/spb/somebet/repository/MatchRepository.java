package ru.spb.somebet.repository;

import org.springframework.data.repository.CrudRepository;
import ru.spb.somebet.model.FutureMatch;
import ru.spb.somebet.model.Region;

import java.util.Collection;

public interface MatchRepository extends CrudRepository<FutureMatch, Long> {

    Collection<FutureMatch> findAll();

    Collection<FutureMatch> findByRegion(Region region);
}
