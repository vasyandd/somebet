package ru.spb.somebet.cache;

import ru.spb.somebet.model.Bet;
import ru.spb.somebet.model.LiveMatch;
import ru.spb.somebet.model.Result;
import ru.spb.somebet.service.payService.PayService;
import ru.spb.somebet.service.result.ResultService;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

public class ClearLiveMatchesListAndPayUsersWinBetsTask implements Runnable {
    private final List<LiveMatch> liveMatches;
    private final ReadWriteLock lock;
    private final ResultService resultService;
    private final PayService payService;

    public ClearLiveMatchesListAndPayUsersWinBetsTask(List<LiveMatch> liveMatches,
                                                      ReadWriteLock lock,
                                                      ResultService resultService,
                                                      PayService payService) {
        this.liveMatches = liveMatches;
        this.lock = lock;
        this.resultService = resultService;
        this.payService = payService;
    }

    @Override
    public void run() {
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
                            payService.payUsersByWinBet(bet);
                        }
                    }
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void checkSuccessBets(LiveMatch match) {
        int[] score = match.getScore();
        int goalsByFirstTeam = score[0];
        int goalsBySecondTeam = score[1];
        Map<Bet.Type, Bet> groupingBets = Bet.groupByType(match.getBets());
        if (goalsByFirstTeam > goalsBySecondTeam) {
            groupingBets.get(Bet.Type.WIN1TEAM).setSuccess(true);
        } else if (goalsByFirstTeam < goalsBySecondTeam) {
            groupingBets.get(Bet.Type.WIN2TEAM).setSuccess(true);
        } else {
            groupingBets.get(Bet.Type.DRAW).setSuccess(true);
        }
    }
}
