package ru.spb.somebet.cache;

import org.springframework.stereotype.Component;
import ru.spb.somebet.dto.LiveMatchDto;
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
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
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

    private final List<LiveMatch> liveMatches = new ArrayList<>();

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
            try {
                lock.writeLock().lock();
                Iterator<LiveMatch> iterator = liveMatches.iterator();
                while (iterator.hasNext()) {
                    LiveMatch match = iterator.next();
                    long currentMinute = match.getCurrentMinuteOfMatch();
                    if (currentMinute > 90) {
                        iterator.remove();
                        Result result = new Result(match, currentMinute);
                        resultService.saveResult(result);
                        checkSuccessBets(match);
                        for (Bet bet : match.getBets()) {
                            if (bet.isSuccess()) {
                                userService.payUsersByWinBet(bet);
                            }
                        }
                    }
                }
            } finally {
                lock.writeLock().unlock();
            }
        };
        goal = () -> {
            try {
                lock.writeLock().lock();
                int numberOfMatchWhereGoalWillBeScored = ThreadLocalRandom.current().nextInt(liveMatches.size());
                LiveMatch chosenMatch = liveMatches.get(numberOfMatchWhereGoalWillBeScored);
                int numberOfTeamThatWillScoreGoal = ThreadLocalRandom.current().nextInt(2);
                chosenMatch.getScore()[numberOfTeamThatWillScoreGoal]++;
                chosenMatch.addEvent("Gooooooal! " + chosenMatch.getTeams()[numberOfTeamThatWillScoreGoal]
                        + " has scored on " + chosenMatch.getCurrentMinuteOfMatch() + " minute");
                analyticDepartmentService.updateBetsOnLiveMatchAndGet(chosenMatch, numberOfTeamThatWillScoreGoal);
            } finally {
                lock.writeLock().unlock();
            }
        };
    }

    private void checkSuccessBets(LiveMatch match) {
        byte[] score = match.getScore();
        byte goalsByFirstTeam = score[0];
        byte goalsBySecondTeam = score[1];
        Map<Bet.Type, Bet> groupingBets = Bet.groupByType(match.getBets());
        if (goalsByFirstTeam > goalsBySecondTeam) {
            groupingBets.get(Bet.Type.WIN1TEAM).setSuccess(true);
        } else if (goalsByFirstTeam < goalsBySecondTeam) {
            groupingBets.get(Bet.Type.WIN2TEAM).setSuccess(true);
        } else {
            groupingBets.get(Bet.Type.DRAW).setSuccess(true);
        }
    }

    @PreDestroy
    public void closeExecutor() {
        executor.shutdown();
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
}
