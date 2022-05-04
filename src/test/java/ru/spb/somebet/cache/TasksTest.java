package ru.spb.somebet.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.spb.somebet.TestData;
import ru.spb.somebet.model.LiveMatch;
import ru.spb.somebet.service.analytic_department.AnalyticDepartmentService;
import ru.spb.somebet.service.match.MatchService;
import ru.spb.somebet.service.payService.PayService;
import ru.spb.somebet.service.result.ResultService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;


@SpringBootTest
public class TasksTest {
    @Mock
    private MatchService matchService;
    @Mock
    private PayService payService;
    @Mock
    private ResultService resultService;
    @Autowired
    private AnalyticDepartmentService analyticDepartmentService;
    private final static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Test
    @DisplayName(value = "GoalTask")
    public void Should_GoalTask_Write_One_Goal_In_Some_LiveMatch_From_List() {
        List<LiveMatch> liveMatches = TestData.getLiveMatchesWithZeroScore();
        GoalTask goalTask = new GoalTask(liveMatches, lock, analyticDepartmentService);
        goalTask.run();
        int countScoredGoals = 0;
        for (LiveMatch liveMatch : liveMatches) {
            int[] resultScore = liveMatch.getScore();
            if (resultScore[0] != 0 || resultScore[1] != 0) {
                countScoredGoals++;
            }
        }
        Assertions.assertEquals(1, countScoredGoals);
    }

    @Test
    @DisplayName(value = "ClearLiveMatchesListAndPayUsersWinBetsTask")
    public void Should_ClearLiveMatchesListAndPayUsersWinBetsTask_Remove_Ended_Matches_From_List() {
        List<LiveMatch> liveMatches = TestData.getLiveMatchesWithEndedMatchesWithZeroScore();
        Assertions.assertEquals(3, liveMatches.size());
        ClearLiveMatchesListAndPayUsersWinBetsTask task = new ClearLiveMatchesListAndPayUsersWinBetsTask(liveMatches,
                lock, resultService, payService);
        task.run();
        Assertions.assertEquals(1, liveMatches.size());
    }

    @Test
    @DisplayName(value = "FetchLiveMatchesFromRepositoryTask")
    public void Should_FetchLiveMatchesFromRepositoryTask_Fill_LiveMatchesList() {
        List<LiveMatch> liveMatches = new ArrayList<>();
        Mockito.when(matchService.getMatchesThatStartsNow()).thenReturn(TestData.getFutureMatchesThatAreStartingNow());
        FetchLiveMatchesFromRepositoryTask task = new FetchLiveMatchesFromRepositoryTask(liveMatches, lock, matchService);
        task.run();
        Assertions.assertEquals(3, liveMatches.size());
    }
}
