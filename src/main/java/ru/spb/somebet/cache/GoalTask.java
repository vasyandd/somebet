package ru.spb.somebet.cache;

import org.springframework.beans.factory.annotation.Autowired;
import ru.spb.somebet.model.LiveMatch;
import ru.spb.somebet.service.analytic_department.AnalyticDepartmentService;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReadWriteLock;

class GoalTask implements Runnable {
    private final List<LiveMatch> liveMatches;
    private final ReadWriteLock lock;
    @Autowired
    private AnalyticDepartmentService analyticDepartmentService;

    public GoalTask(List<LiveMatch> liveMatches, ReadWriteLock lock) {
        this.liveMatches = liveMatches;
        this.lock = lock;
    }

    @Override
    public void run() {
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
    }
}
