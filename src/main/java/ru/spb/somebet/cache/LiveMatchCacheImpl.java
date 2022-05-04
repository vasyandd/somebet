package ru.spb.somebet.cache;

import org.springframework.stereotype.Component;
import ru.spb.somebet.dto.LiveMatchDto;
import ru.spb.somebet.model.LiveMatch;
import ru.spb.somebet.service.analytic_department.AnalyticDepartmentService;
import ru.spb.somebet.service.match.MatchService;
import ru.spb.somebet.service.payService.PayService;
import ru.spb.somebet.service.result.ResultService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class LiveMatchCacheImpl implements LiveMatchCache {
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final List<LiveMatch> liveMatches = new ArrayList<>();
    private final AnalyticDepartmentService analyticDepartmentService;
    private final MatchService matchService;
    private final ResultService resultService;
    private final PayService payService;

    public LiveMatchCacheImpl(AnalyticDepartmentService analyticDepartmentService, MatchService matchService,
                              ResultService resultService, PayService payService) {
        this.analyticDepartmentService = analyticDepartmentService;
        this.matchService = matchService;
        this.resultService = resultService;
        this.payService = payService;
    }

    @PostConstruct
    public void initTasksAndAddToScheduledExecutor() {
        Runnable fetchLiveMatchesFromRepository = new FetchLiveMatchesFromRepositoryTask(liveMatches, lock, matchService);
        Runnable clearLiveMatchesListAndPutFinishedMatchesToRepository =
                new ClearLiveMatchesListAndPayUsersWinBetsTask(liveMatches, lock, resultService, payService);
        Runnable goal = new GoalTask(liveMatches, lock, analyticDepartmentService);
        // someone scores a goal every 15 seconds
        executor.scheduleWithFixedDelay(goal, 15, 15, TimeUnit.SECONDS);
        // data is fetched from repository every minute
        executor.scheduleWithFixedDelay(fetchLiveMatchesFromRepository, 0, 1, TimeUnit.MINUTES);
        // list of live matches is cleared every minute
        executor.scheduleWithFixedDelay(clearLiveMatchesListAndPutFinishedMatchesToRepository, 1, 1, TimeUnit.MINUTES);
    }

    @Override
    public Collection<LiveMatchDto> getLiveMatches() {
        List<LiveMatchDto> result = new ArrayList<>();
        try {
            lock.readLock().lock();
            for (LiveMatch match : liveMatches) {
                LiveMatchDto liveMatchForResultList = LiveMatch.modelToDto(match);
                result.add(liveMatchForResultList);
            }
        } finally {
            lock.readLock().unlock();
        }
        return result;
    }

    @PreDestroy
    public void closeExecutor() {
        executor.shutdown();
    }
}
