package ru.spb.somebet.service.analytic_department;

import org.springframework.stereotype.Component;
import ru.spb.somebet.dto.NewMatch;
import ru.spb.somebet.model.Bet;
import ru.spb.somebet.model.LiveMatch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class AnalyticDepartmentServiceImpl implements AnalyticDepartmentService {

    @Override
    public Collection<Bet> getBetsOnNewMatch(NewMatch match) {
        int fistTeamRating = getFootballClubRating(match.getTeam1());
        int secondTeamRating = getFootballClubRating(match.getTeam2());
        return getAllBetsOfThisMatch(fistTeamRating, secondTeamRating);
    }

    private Collection<Bet> getAllBetsOfThisMatch(int fistTeamRating, int secondTeamRating) {
        List<Bet> list = new ArrayList<>();
        float[] coefficients = getCoefficients(fistTeamRating, secondTeamRating);
        list.add(new Bet(null, coefficients[0], Bet.Type.WIN1TEAM, null, true));
        list.add(new Bet(null, coefficients[1], Bet.Type.DRAW, null, true));
        list.add(new Bet(null, coefficients[2], Bet.Type.WIN2TEAM, null, true));
        return list;
    }

    private float[] getCoefficients(int fistTeamRating, int secondTeamRating) {
        // 33% - win firstTeam, 34% - draw, 33% - win secondTeam
        int[] basePercent = new int[]{33, 34, 33};
        if (secondTeamRating != fistTeamRating) {
            int module = Math.abs(fistTeamRating - secondTeamRating);
            basePercent[0] += module;
            basePercent[1] -= module / 2;
            basePercent[2] = 100 - basePercent[0] - basePercent[1];
            if (secondTeamRating > fistTeamRating) {
                int temp = basePercent[0];
                basePercent[0] = basePercent[2];
                basePercent[2] = temp;
            }
        }
        float[] result = new float[3];
        for (int i = 0; i < result.length; i++) {
            result[i] = 1 / (float) basePercent[i];
        }
        return result;
    }

    private int getFootballClubRating(String club) {
        return ThreadLocalRandom.current().nextInt(1, 50);
    }

    @Override
    public LiveMatch checkBetsOnLiveMatchAndGet(LiveMatch futureMatch) {
        return null;
    }
}
