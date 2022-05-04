package ru.spb.somebet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.spb.somebet.model.FutureMatch;
import ru.spb.somebet.model.Region;

import java.time.LocalDateTime;
import java.util.Collection;

public interface MatchRepository extends JpaRepository<FutureMatch, Long> {

    Collection<FutureMatch> findByRegion(Region region);

    Collection<FutureMatch> findByStartTimeEquals(LocalDateTime now);
}
