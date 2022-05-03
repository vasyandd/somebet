package ru.spb.somebet.service.live;

import org.springframework.stereotype.Component;
import ru.spb.somebet.cache.LiveMatchCache;
import ru.spb.somebet.dto.LiveMatchDto;
import ru.spb.somebet.model.LiveMatch;
import ru.spb.somebet.model.Region;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class LiveMatchServiceImpl implements LiveMatchService {
    private final LiveMatchCache cache;

    public LiveMatchServiceImpl(LiveMatchCache cache) {
        this.cache = cache;
    }

    @Override
    public Collection<LiveMatchDto> getAllLiveMatches() {
        return cache.getLiveMatches();
    }

    @Override
    public Collection<LiveMatchDto> getAllLiveMatchesByRegion(String region) {
        Collection<LiveMatchDto> matches = getAllLiveMatches();
        return matches.stream()
                .filter(m -> m.getRegion() == Region.of(region))
                .collect(Collectors.toList());
    }
}
