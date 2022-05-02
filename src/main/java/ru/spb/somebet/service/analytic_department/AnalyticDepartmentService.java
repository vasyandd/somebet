package ru.spb.somebet.service.analytic_department;

import ru.spb.somebet.dto.NewMatch;
import ru.spb.somebet.model.Bet;
import ru.spb.somebet.model.FutureMatch;
import ru.spb.somebet.model.LiveMatch;

import java.util.Collection;

public interface AnalyticDepartmentService {

    Collection<Bet> getBetsOnNewMatch(NewMatch match);

    LiveMatch checkBetsOnLiveMatchAndGet(LiveMatch match);
}
