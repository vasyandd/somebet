package ru.spb.somebet.service.match;

import ru.spb.somebet.dto.NewMatch;
import ru.spb.somebet.model.FutureMatch;

import java.util.Collection;

public interface MatchService {

    Collection<FutureMatch> getMatches();

    void saveMatch(NewMatch match);

    Collection<FutureMatch> getMatchesByRegion(String region);
}
