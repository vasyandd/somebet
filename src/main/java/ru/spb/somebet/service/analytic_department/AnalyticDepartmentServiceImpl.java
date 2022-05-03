package ru.spb.somebet.service.analytic_department;

import org.springframework.stereotype.Component;
import ru.spb.somebet.dto.NewMatch;
import ru.spb.somebet.model.Bet;
import ru.spb.somebet.model.LiveMatch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class AnalyticDepartmentServiceImpl implements AnalyticDepartmentService {

    @Override
    public Collection<Bet> getBetsOnNewMatch(NewMatch match) {
        int fistTeamRating = getFootballClubRating(match.getTeams()[0]);
        int secondTeamRating = getFootballClubRating(match.getTeams()[1]);
        return getAllBetsOfThisMatch(fistTeamRating, secondTeamRating);
    }

    private Collection<Bet> getAllBetsOfThisMatch(int fistTeamRating, int secondTeamRating) {
        List<Bet> list = new ArrayList<>();
        float[] coefficients = getInitialCoefficients(fistTeamRating, secondTeamRating);
        list.add(new Bet(null, coefficients[0], Bet.Type.WIN1TEAM, null, false));
        list.add(new Bet(null, coefficients[1], Bet.Type.DRAW, null, false));
        list.add(new Bet(null, coefficients[2], Bet.Type.WIN2TEAM, null, false));
        return list;
    }

    private float[] getInitialCoefficients(int fistTeamRating, int secondTeamRating) {
        // 33% - win firstTeam, 34% - draw, 33% - win secondTeam
        int[] basePercent = new int[]{30,40,30};
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
        return mapPercentsToCoefficients(basePercent);
    }

    private int getFootballClubRating(String club) {
        return ThreadLocalRandom.current().nextInt(1, 50);
    }

    @Override
    public LiveMatch updateBetsOnLiveMatchAndGet(LiveMatch liveMatch, int numberOfTeamThatHasScored) {
        Map<Bet.Type, Bet> helpMap =  Bet.groupByType(liveMatch.getBets());
        int[] percents = getPercentsFromBets(helpMap);
        updatePercents(percents, numberOfTeamThatHasScored);
        float[] newCoefficients = mapPercentsToCoefficients(percents);
        helpMap.get(Bet.Type.WIN1TEAM).setValue(newCoefficients[0]);
        helpMap.get(Bet.Type.DRAW).setValue(newCoefficients[1]);
        helpMap.get(Bet.Type.WIN2TEAM).setValue(newCoefficients[2]);
        return liveMatch;
    }

    private void updatePercents(int[] percents, int numberOfTeamThatHasScored) {
        if (numberOfTeamThatHasScored == 0) {
            percents[0] += 10;
            if (percents[0] >= 98) {
                percents[1] = 1;
                percents[2] = 1;
            }
            else {
                percents[1] -= 5;
                percents[2] = 100 - percents[0] - percents[1];
            }
        } else {
            percents[2] += 10;
            if (percents[2] >= 98) {
                percents[1] = 1;
                percents[0] = 1;
            }
            else {
                percents[1] -= 5;
                percents[0] = 100 - percents[2] - percents[1];
            }
        }
    }

    private int[] getPercentsFromBets(Map<Bet.Type, Bet> bets) {
        int[] percents = new int[3];
        percents[0] = (int) (1 * 100.1 / bets.get(Bet.Type.WIN1TEAM).getValue());
        percents[1] = (int) (1 * 100.1 / bets.get(Bet.Type.DRAW).getValue());
        percents[2] = 100 - percents[0] - percents[1];
        return percents;
    }

    private float[] mapPercentsToCoefficients(int[] percents) {
        float[] result = new float[3];
        for (int i = 0; i < result.length; i++) {
            result[i] = 100 / (float) percents[i];
        }
        return result;
    }
}
