package ru.spb.somebet.cache;

import org.springframework.beans.factory.annotation.Autowired;
import ru.spb.somebet.model.FutureMatch;
import ru.spb.somebet.model.LiveMatch;
import ru.spb.somebet.service.match.MatchService;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

public class FetchLiveMatchesFromRepositoryTask implements Runnable {
    private final List<LiveMatch> liveMatches;
    private final ReadWriteLock lock;
    @Autowired
    private MatchService matchService;

    public FetchLiveMatchesFromRepositoryTask(List<LiveMatch> liveMatches, ReadWriteLock lock) {
        this.liveMatches = liveMatches;
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.writeLock().lock();
        Collection<FutureMatch> matches = matchService.getMatches();
        for (FutureMatch futureMatch : matches) {
            liveMatches.add(new LiveMatch(futureMatch));
        }
        matchService.deleteMatches(matches);
        lock.writeLock().unlock();
    }
}
