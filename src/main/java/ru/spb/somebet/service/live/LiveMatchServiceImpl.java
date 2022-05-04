package ru.spb.somebet.service.live;

import org.springframework.stereotype.Component;
import ru.spb.somebet.cache.LiveMatchCache;
import ru.spb.somebet.dto.LiveMatchDto;

import java.util.Collection;

@Component
public class LiveMatchServiceImpl implements LiveMatchService {
    private final LiveMatchCache cache;

    public LiveMatchServiceImpl(LiveMatchCache cache) {
        this.cache = cache;
    }

    @Override
    public Collection<LiveMatchDto> getLiveMatches() {
        return cache.getLiveMatches();
    }

}
