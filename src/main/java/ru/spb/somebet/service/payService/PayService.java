package ru.spb.somebet.service.payService;

import ru.spb.somebet.model.Bet;

public interface PayService {
    void payUsersByWinBet(Bet bet);
}
