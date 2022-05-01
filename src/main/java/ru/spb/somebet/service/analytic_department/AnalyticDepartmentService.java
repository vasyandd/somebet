package ru.spb.somebet.service.analytic_department;

import ru.spb.somebet.dto.NewMatch;
import ru.spb.somebet.model.FutureMatch;

public interface AnalyticDepartmentService {

    FutureMatch addBetsOnEventAndGet(NewMatch match);

    FutureMatch checkBetsOnLiveEventAndGet(FutureMatch futureMatch);
}
