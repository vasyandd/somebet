package ru.spb.somebet.service.live;

import ru.spb.somebet.model.LiveMatch;

import java.util.Collection;

public interface LiveService {
   Collection<LiveMatch> getAllLiveMatches();
}
