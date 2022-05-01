package ru.spb.somebet.service.match;

import org.springframework.stereotype.Component;
import ru.spb.somebet.dto.NewMatch;
import ru.spb.somebet.model.FutureMatch;
import ru.spb.somebet.repository.MatchRepository;
import ru.spb.somebet.service.analytic_department.AnalyticDepartmentService;

import java.util.Collection;

@Component
public class MatchServiceImpl implements MatchService {
    private final MatchRepository repository;
    private final AnalyticDepartmentService analyticDepartment;

    public MatchServiceImpl(MatchRepository repository, AnalyticDepartmentService analyticDepartment) {
        this.repository = repository;
        this.analyticDepartment = analyticDepartment;
    }

    @Override
    public Collection<FutureMatch> getMatches() {
        return repository.findAll();
    }

    @Override
    public void saveMatch(NewMatch match) {
        FutureMatch futureMatch = analyticDepartment.addBetsOnEventAndGet(match);
        repository.save(futureMatch);
    }

    @Override
    public Collection<FutureMatch> getMatchesByUser(Long id) {
        return null;
    }
}
