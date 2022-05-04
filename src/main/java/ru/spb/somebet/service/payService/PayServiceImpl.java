package ru.spb.somebet.service.payService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.spb.somebet.model.Bet;
import ru.spb.somebet.model.User;
import ru.spb.somebet.service.user.UserService;

import java.util.Collection;
import java.util.Objects;

@Slf4j
@Component
public class PayServiceImpl implements PayService {
    private final UserService userService;

    public PayServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void payUsersByWinBet(Bet bet) {
        Collection<User> users = userService.findUsersByBet(bet);
        Long betId = bet.getId();
        float betValue = bet.getValue();
        if (Objects.nonNull(users)) {
            for (User user : users) {
                float balance = user.getBalance();
                float moneyOnBet = user.getBets().get(betId);
                float winSum = betValue * moneyOnBet;
                user.setBalance(balance + winSum);
                user.getBets().remove(betId);
                userService.saveUser(user);
                log.info("User " + user.getLogin() + " has won " + winSum);
            }
        }
    }
}
