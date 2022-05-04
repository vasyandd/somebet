package ru.spb.somebet.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.spb.somebet.model.Bet;
import ru.spb.somebet.model.User;
import ru.spb.somebet.service.payService.PayService;
import ru.spb.somebet.service.user.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class PayServiceTest {

    private static final Map<Long, Float> DEFAULT_USER_BETS = new HashMap<>() {{
        put(37L, 70f);
        put(24L, 10f);
    }};
    private static final User DEFAULT_USER = new User(1L, "Vasya", "qwerty", 100f, DEFAULT_USER_BETS);
    private static final Bet WIN_BET = new Bet(37L, 2.0f, Bet.Type.WIN1TEAM, null, true);

    @MockBean
    private UserService userService;
    @Autowired
    private PayService payService;


    @Test
    @DisplayName(value = "payUsersByWinBet")
    public void payUsersByWinBet_successTest() {
        List<User> users = new ArrayList<>() {{
            add(DEFAULT_USER);
        }};
        float balanceBeforeWin = DEFAULT_USER.getBalance();
        float expectedBalance = balanceBeforeWin + DEFAULT_USER.getBets().get(37L) * WIN_BET.getValue();

        Mockito.when(userService.findUsersByBet(WIN_BET)).thenReturn(users);
        payService.payUsersByWinBet(WIN_BET);

        float balanceAfterWin = DEFAULT_USER.getBalance();
        Assertions.assertEquals(expectedBalance, balanceAfterWin);
    }
}
