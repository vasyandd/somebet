package ru.spb.somebet.service.live;

import ru.spb.somebet.model.LiveMatch;

import java.util.Collection;

public interface LiveMatchService {

    Collection<LiveMatch> getAllLiveMatches();

    Collection<LiveMatch> getAllLiveMatchesByRegion(String region);

}
