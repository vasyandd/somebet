package ru.spb.somebet;

import ru.spb.somebet.model.Bet;
import ru.spb.somebet.model.FutureMatch;
import ru.spb.somebet.model.LiveMatch;
import ru.spb.somebet.model.Region;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TestData {
    private final static LocalDateTime RANDOM_START_DATE = LocalDateTime.of(2022, Month.MAY, 15, 14, 0);
    private final static LocalDateTime NOW_MINUS_92_MINUTES = LocalDateTime.now().minusMinutes(92);
    private final static LocalDateTime NOW = LocalDateTime.now();
    public static List<LiveMatch> getLiveMatchesWithZeroScore() {
        List<LiveMatch> list = new ArrayList<>();
        Collection<Bet> bets1 = createBets().get(0);
        Collection<Bet> bets2 = createBets().get(1);
        Collection<Bet> bets3 = createBets().get(2);;
        LiveMatch liveMatch1 = new LiveMatch("РПЛ", new String[]{"Зенит","ЦСКА"},
                Region.RUSSIA, bets1, RANDOM_START_DATE);
        LiveMatch liveMatch2 = new LiveMatch("Серия А", new String[]{"Милан","Интер"},
                Region.ITALY, bets2, RANDOM_START_DATE);
        LiveMatch liveMatch3 = new LiveMatch("Ла Лига", new String[]{"Барселона","Реал Мадрид"},
                Region.SPAIN, bets3, RANDOM_START_DATE);
        list.add(liveMatch1);
        list.add(liveMatch2);
        list.add(liveMatch3);
        return list;
    }

    public static List<LiveMatch> getLiveMatchesWithEndedMatchesWithZeroScore() {
        List<LiveMatch> list = new ArrayList<>();
        Collection<Bet> bets1 = createBets().get(0);
        Collection<Bet> bets2 = createBets().get(1);
        Collection<Bet> bets3 = createBets().get(2);;
        LiveMatch liveMatch1 = new LiveMatch("РПЛ", new String[]{"Зенит","ЦСКА"},
                Region.RUSSIA, bets1, RANDOM_START_DATE);
        LiveMatch liveMatch2 = new LiveMatch("Серия А", new String[]{"Милан","Интер"},
                Region.ITALY, bets2, NOW_MINUS_92_MINUTES);
        LiveMatch liveMatch3 = new LiveMatch("Ла Лига", new String[]{"Барселона","Реал Мадрид"},
                Region.SPAIN, bets3, NOW_MINUS_92_MINUTES);
        list.add(liveMatch1);
        list.add(liveMatch2);
        list.add(liveMatch3);
        return list;
    }

    public static List<FutureMatch> getFutureMatchesThatAreStartingNow() {
        List<FutureMatch> list = new ArrayList<>();
        Collection<Bet> bets1 = createBets().get(0);
        Collection<Bet> bets2 = createBets().get(1);
        Collection<Bet> bets3 = createBets().get(2);;
        FutureMatch liveMatch1 = new FutureMatch(null,"РПЛ", new String[]{"Зенит","ЦСКА"},
                Region.RUSSIA, bets1, NOW);
        FutureMatch liveMatch2 = new FutureMatch(null, "Серия А", new String[]{"Милан","Интер"},
                Region.ITALY, bets2, NOW);
        FutureMatch liveMatch3 = new FutureMatch(null, "Ла Лига", new String[]{"Барселона","Реал Мадрид"}
        , Region.SPAIN, bets3, NOW);
        list.add(liveMatch1);
        list.add(liveMatch2);
        list.add(liveMatch3);
        return list;
    }

    private static List<List<Bet>> createBets() {
        List<Bet> bets1 = new ArrayList<>() {{
            add(new Bet(null, 3.3f, Bet.Type.WIN1TEAM, null, false));
            add(new Bet(null, 2.5f, Bet.Type.DRAW, null, false));
            add(new Bet(null, 3.3f, Bet.Type.WIN2TEAM, null, false));
        }};
        List<Bet> bets2 = new ArrayList<>() {{
            add(new Bet(null, 16.6f, Bet.Type.WIN1TEAM, null, false));
            add(new Bet(null, 6.25f, Bet.Type.DRAW, null, false));
            add(new Bet(null, 1.28f, Bet.Type.WIN2TEAM, null, false));
        }};
        List<Bet> bets3 = new ArrayList<>() {{
            add(new Bet(null, 1.28f, Bet.Type.WIN1TEAM, null, false));
            add(new Bet(null, 6.25f, Bet.Type.DRAW, null, false));
            add(new Bet(null, 16.6f, Bet.Type.WIN2TEAM, null, false));
        }};
        return new ArrayList<>() {{add(bets1); add(bets2); add(bets3);}};
    }

}
