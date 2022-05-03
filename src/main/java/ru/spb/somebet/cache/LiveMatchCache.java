package ru.spb.somebet.cache;

import ru.spb.somebet.dto.LiveMatchDto;

import java.util.Collection;

public interface LiveMatchCache {
    Collection<LiveMatchDto> getLiveMatches();
}
