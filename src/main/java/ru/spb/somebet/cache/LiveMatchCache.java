package ru.spb.somebet.cache;

import ru.spb.somebet.dto.LiveMatchDto;
import ru.spb.somebet.model.LiveMatch;

import java.util.Collection;

public interface LiveMatchCache {
    Collection<LiveMatchDto> getLiveMatches();
}
