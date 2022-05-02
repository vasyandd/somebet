package ru.spb.somebet.service.live;

import org.springframework.stereotype.Component;
import ru.spb.somebet.cache.LiveMatchCache;
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
    public Collection<LiveMatch> getAllLiveMatches() {
        return cache.getLiveMatches();
    }

    @Override
    public Collection<LiveMatch> getAllLiveMatchesByRegion(String region) {
        Collection<LiveMatch> matches = getAllLiveMatches();
        return matches.stream()
                .filter(m -> m.getRegion() == Region.of(region))
                .collect(Collectors.toList());
    }
}
