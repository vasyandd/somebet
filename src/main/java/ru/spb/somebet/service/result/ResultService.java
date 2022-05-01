package ru.spb.somebet.service.result;

import ru.spb.somebet.model.Result;
import ru.spb.somebet.model.User;

import java.util.Collection;

public interface ResultService {

    Collection<Result> getResultsByUser(User user);
    Collection<Result> getAllResultsB();
}
