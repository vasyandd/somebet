package ru.spb.somebet.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.spb.somebet.TestData;
import ru.spb.somebet.dto.NewMatch;
import ru.spb.somebet.model.Bet;
import ru.spb.somebet.model.LiveMatch;
import ru.spb.somebet.model.Region;
import ru.spb.somebet.service.analytic_department.AnalyticDepartmentService;
import ru.spb.somebet.service.analytic_department.AnalyticDepartmentServiceImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Stream;

@SpringBootTest
class AnalyticDepartmentServiceTest {

    @Autowired
    private AnalyticDepartmentService service;
    private final static LocalDateTime START_DATE_FOR_ALL_TEST_MATCHES = LocalDateTime.of(2022, Month.MAY, 15, 14, 0);

    @ParameterizedTest
    @CsvSource(value = {
            "30, 30",
            "1, 49",
            "49, 1"
    })
    @DisplayName(value = "check if getInitialCoefficients method works")
    public void getInitialCoefficientsTest(int fistTeamRating, int secondTeamRating) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = AnalyticDepartmentServiceImpl.class.getDeclaredMethod("getInitialCoefficients", int.class, int.class);
        method.setAccessible(true);
        float[] result = (float[]) method.invoke(service, fistTeamRating, secondTeamRating);
        System.out.println(Arrays.toString(result));
    }

    @ParameterizedTest
    @MethodSource(value = "generateNewMatches")
    @DisplayName(value = "test getBetsOnNewMatch method")
    public void getBetsOnNewMatchTest(NewMatch match) {
        Collection<Bet> actualBets = service.getBetsOnNewMatch(match);
        Assertions.assertEquals(3, actualBets.size());
        float betSum = 0f;
        for (Bet bet : actualBets) {
            betSum = betSum + 1 / bet.getValue();
        }
        Assertions.assertEquals(0f, Float.compare(betSum, 1f));
    }

    @ParameterizedTest
    @ArgumentsSource(value = LiveMatchArguments.class)
    @DisplayName(value = "test updateBetsOnLiveMatchAndGet method")
    public void updateBetsOnLiveMatchAndGetTest(LiveMatch liveMatch, int number) {
        float currentBetValue;
        Map<Bet.Type, Bet> helper = Bet.groupByType(liveMatch.getBets());
        if (number == 0) {
            currentBetValue = helper.get(Bet.Type.WIN1TEAM).getValue();
        } else {
            currentBetValue = helper.get(Bet.Type.WIN2TEAM).getValue();
        }
        service.updateBetsOnLiveMatchAndGet(liveMatch, number);
        float valueAfterUpdate;
        if (number == 0) {
           valueAfterUpdate = helper.get(Bet.Type.WIN1TEAM).getValue();
        } else {
            valueAfterUpdate = helper.get(Bet.Type.WIN2TEAM).getValue();
        }
        Assertions.assertTrue(Float.compare(currentBetValue, valueAfterUpdate) > 0);
        float betSum = 0f;
        for (Bet bet : liveMatch.getBets()) {
            betSum = betSum + 1 / bet.getValue();
        }
        Assertions.assertEquals(0f, Float.compare(betSum, 1f));
    }

    static Stream<NewMatch> generateNewMatches() {
        return Stream.of(new NewMatch("РПЛ", new String[]{"Зенит","ЦСКА"}, "ru", START_DATE_FOR_ALL_TEST_MATCHES),
                new NewMatch("Ла Лига", new String[]{"Барселона", "Реал Мадрид"}, "sp", START_DATE_FOR_ALL_TEST_MATCHES),
                new NewMatch("Серия А", new String[]{"Милан", "Интер"}, "it", START_DATE_FOR_ALL_TEST_MATCHES),
                new NewMatch("Бундеслига", new String[]{"Бавария", "Боруссия Д"}, "gr", START_DATE_FOR_ALL_TEST_MATCHES));
    }

    static class LiveMatchArguments implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            List<LiveMatch> liveMatches = TestData.getLiveMatchesWithZeroScore();
            return Stream.of(
                    Arguments.of(liveMatches.get(0), 0), Arguments.of(liveMatches.get(0), 1),
                    Arguments.of(liveMatches.get(1), 0), Arguments.of(liveMatches.get(1), 1),
                    Arguments.of(liveMatches.get(2), 0), Arguments.of(liveMatches.get(2), 1)
            );
        }
    }

}
