package ru.spb.somebet.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.spb.somebet.exception.NotEnoughMoney;
import ru.spb.somebet.exception.UserNotFoundException;
import ru.spb.somebet.model.Bet;
import ru.spb.somebet.model.User;
import ru.spb.somebet.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " doesn't exist"));
    }

    @Override
    public Collection<User> getUsers() {
        return repository.findAll();
    }

    @Override
    public void addBetToUser(Long userId, Bet bet, float value) {
        User user = findById(userId);
        float balance = user.getBalance();
        if (balance < value) {
            throw new NotEnoughMoney("User with id " + userId + " have not enough money");
        }
        user.setBalance(balance - value);
        user.addActiveBet(bet, value);
        log.info("User " + user.getLogin() + " has put " + value + " on" + bet.getFutureMatch().getDescription());
        saveUser(user);
    }

    @Override
    public void saveUser(User user) {
        repository.save(user);
    }

    @Override
    public Collection<User> findUsersByBet(Bet bet) {
        List<User> users = new ArrayList<>();
        for (User user : repository.findAll()) {
            if (user.getBets().containsKey(bet.getId())) {
                users.add(user);
            }
        }
        return users;
    }
}
