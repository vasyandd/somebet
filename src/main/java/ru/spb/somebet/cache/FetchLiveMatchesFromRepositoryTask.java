package ru.spb.somebet.cache;

import ru.spb.somebet.model.FutureMatch;
import ru.spb.somebet.model.LiveMatch;
import ru.spb.somebet.service.match.MatchService;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

public class FetchLiveMatchesFromRepositoryTask implements Runnable {
    private final List<LiveMatch> liveMatches;
    private final ReadWriteLock lock;
    private final MatchService matchService;

    public FetchLiveMatchesFromRepositoryTask(List<LiveMatch> liveMatches, ReadWriteLock lock, MatchService matchService) {
        this.liveMatches = liveMatches;
        this.lock = lock;
        this.matchService = matchService;
    }

    @Override
    public void run() {
        lock.writeLock().lock();
        Collection<FutureMatch> matches = matchService.getMatchesThatStartsNow();
        for (FutureMatch futureMatch : matches) {
            liveMatches.add(new LiveMatch(futureMatch));
        }
        matchService.deleteMatches(matches);
        lock.writeLock().unlock();
    }
}
