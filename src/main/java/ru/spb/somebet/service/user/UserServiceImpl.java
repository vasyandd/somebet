package ru.spb.somebet.service.user;

import org.springframework.stereotype.Component;
import ru.spb.somebet.model.Bet;
import ru.spb.somebet.repository.UserRepository;

import java.util.Collection;

@Component
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<Bet> getBetsForUser(Long id) {
        return repository.findBetsById(id);
    }
}
