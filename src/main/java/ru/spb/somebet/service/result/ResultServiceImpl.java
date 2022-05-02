package ru.spb.somebet.service.result;

import org.springframework.stereotype.Component;
import ru.spb.somebet.model.Result;
import ru.spb.somebet.model.User;
import ru.spb.somebet.repository.ResultRepository;

import java.util.Collection;

@Component
public class ResultServiceImpl implements ResultService {
    private final ResultRepository resultRepository;

    public ResultServiceImpl(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @Override
    public void saveResult(Result result) {
        resultRepository.save(result);
    }


    @Override
    public Collection<Result> getAllResults() {
        return resultRepository.findAll();
    }
}
