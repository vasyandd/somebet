package ru.spb.somebet.service.match;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.spb.somebet.dto.FutureMatchDto;
import ru.spb.somebet.dto.NewMatch;
import ru.spb.somebet.model.Bet;
import ru.spb.somebet.model.FutureMatch;
import ru.spb.somebet.model.Region;
import ru.spb.somebet.repository.MatchRepository;
import ru.spb.somebet.service.analytic_department.AnalyticDepartmentService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class MatchServiceImpl implements MatchService {
    private final MatchRepository repository;
    private final AnalyticDepartmentService analyticDepartment;

    public MatchServiceImpl(MatchRepository repository, AnalyticDepartmentService analyticDepartment) {
        this.repository = repository;
        this.analyticDepartment = analyticDepartment;
    }

    @Override
    public Collection<FutureMatchDto> getMatches() {
        List<FutureMatchDto> matches = new ArrayList<>();
        for (FutureMatch futureMatch : repository.findAll()) {
            matches.add(FutureMatch.modelToDto(futureMatch));
        }
        return matches;
    }

    @Override
    public void saveMatch(NewMatch match) {
        Collection<Bet> bets = analyticDepartment.getBetsOnNewMatch(match);
        FutureMatch futureMatch = new FutureMatch(null, match.getDescription(), match.getTeams(),
                Region.of(match.getRegion()), bets, match.getStartDate());
        bets.forEach(b -> b.setFutureMatch(futureMatch));
        log.info("NewMatch between " + match.getTeams()[0] + " and " + match.getTeams()[1] + " is added");
        repository.save(futureMatch);
    }

    @Override
    public Collection<FutureMatch> getMatchesThatStartsNow() {
        return repository.findByStartTimeEquals(LocalDateTime.now());
    }

    @Override
    public void deleteMatches(Collection<FutureMatch> matches) {
        repository.deleteAll(matches);
    }
}
