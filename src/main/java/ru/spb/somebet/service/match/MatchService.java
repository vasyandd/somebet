package ru.spb.somebet.service.match;

import ru.spb.somebet.dto.FutureMatchDto;
import ru.spb.somebet.dto.NewMatch;
import ru.spb.somebet.model.FutureMatch;

import java.time.LocalDateTime;
import java.util.Collection;

public interface MatchService {

    Collection<FutureMatchDto> getMatches();

    Collection<FutureMatch> getMatchesThatStartsNow();

    void saveMatch(NewMatch match);

    void deleteMatches(Collection<FutureMatch> matches);
}
