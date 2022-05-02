package ru.spb.somebet.service.live;

import org.springframework.stereotype.Component;
import ru.spb.somebet.model.LiveMatch;

import java.util.Collection;

@Component
public class LiveMatchServiceImpl implements LiveMatchService {


    @Override
    public Collection<LiveMatch> getAllLiveMatches() {
        return null;
    }

    @Override
    public Collection<LiveMatch> getAllLiveMatchesByRegion(String region) {
        return null;
    }
}
