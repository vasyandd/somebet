package ru.spb.somebet.service.analytic_department;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import org.springframework.stereotype.Component;
import ru.spb.somebet.dto.NewMatch;
import ru.spb.somebet.model.FutureMatch;

@Component
public class AnalyticDepartmentServiceImpl implements AnalyticDepartmentService {

    @Override
    public FutureMatch addBetsOnEventAndGet(NewMatch match) {
        return null;
    }

    @Override
    public FutureMatch checkBetsOnLiveEventAndGet(FutureMatch futureMatch) {
        return null;
    }
}
