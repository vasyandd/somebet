package ru.spb.somebet.cache;

import org.springframework.stereotype.Component;
import ru.spb.somebet.model.Bet;
import ru.spb.somebet.model.FutureMatch;
import ru.spb.somebet.model.LiveMatch;
import ru.spb.somebet.model.Result;
import ru.spb.somebet.service.analytic_department.AnalyticDepartmentService;
import ru.spb.somebet.service.match.MatchService;
import ru.spb.somebet.service.result.ResultService;
import ru.spb.somebet.service.user.UserService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class LiveMatchCacheImpl implements LiveMatchCache {
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final MatchService matchService;
    private final UserService userService;
    private final ResultService resultService;
    private final AnalyticDepartmentService analyticDepartmentService;
    private Runnable fetchLiveMatchesFromRepository;
    private Runnable clearLiveMatchesListAndPutFinishedMatchesToRepository;
    private Runnable goal;

    private final Collection<LiveMatch> liveMatches = new LinkedList<>();

    public LiveMatchCacheImpl(MatchService matchService, UserService userService,
                              ResultService resultService, AnalyticDepartmentService analyticDepartmentService) {
        this.matchService = matchService;
        this.userService = userService;
        this.resultService = resultService;
        this.analyticDepartmentService = analyticDepartmentService;
    }

    @PostConstruct
    public void initTasks() {
        fetchLiveMatchesFromRepository = () -> {
            lock.writeLock().lock();
            Collection<FutureMatch> matches = matchService.getMatches();
            for (FutureMatch futureMatch : matches) {
                liveMatches.add(new LiveMatch(futureMatch));
            }
            matchService.deleteMatches(matches);
            lock.writeLock().unlock();
        };
        clearLiveMatchesListAndPutFinishedMatchesToRepository = () -> {
            lock.writeLock().lock();
            Iterator<LiveMatch> iterator = liveMatches.iterator();
            while (iterator.hasNext()) {
                LiveMatch match = iterator.next();
                int currentMinute = match.getCurrentMinuteOfMatch();
                if (currentMinute > 90) {
                    iterator.remove();
                    Result result = new Result(match, currentMinute);
                    resultService.saveResult(result);
                    for(Bet bet : match.getBets()) {
                        if (bet.isSuccess()) {
                            userService.payUsersByWinBet(bet);
                        }
                    }
                }
            }
            liveMatches.removeIf(match -> match.getCurrentMinuteOfMatch() >= 90);
            lock.writeLock().unlock();
        };
        goal = () -> {
            lock.writeLock().lock();

            lock.writeLock().unlock();
        };
    }

    @PreDestroy
    public void closeExecutor() {
        executor.shutdown();
    }

    @Override
    public Collection<LiveMatch> getLiveMatches() {

        return null;
    }
}
