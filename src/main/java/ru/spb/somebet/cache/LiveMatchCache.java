package ru.spb.somebet.cache;

import ru.spb.somebet.model.LiveMatch;

import java.util.Collection;

public interface LiveMatchCache {
    Collection<LiveMatch> getLiveMatches();
}
