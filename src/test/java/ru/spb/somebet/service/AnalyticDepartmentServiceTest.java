package ru.spb.somebet.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.spb.somebet.dto.NewMatch;
import ru.spb.somebet.model.Bet;
import ru.spb.somebet.service.analytic_department.AnalyticDepartmentService;
import ru.spb.somebet.service.analytic_department.AnalyticDepartmentServiceImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;


public class AnalyticDepartmentServiceTest {

    private final AnalyticDepartmentService service = new AnalyticDepartmentServiceImpl();
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
        System.out.println(actualBets);
    }

    static Stream<NewMatch> generateNewMatches() {
        return Stream.of(new NewMatch("РПЛ", new String[]{"Зенит","ЦСКА"}, "ru", START_DATE_FOR_ALL_TEST_MATCHES),
                new NewMatch("Ла Лига", new String[]{"Барселона", "Реал Мадрид"}, "sp", START_DATE_FOR_ALL_TEST_MATCHES),
                new NewMatch("Серия А", new String[]{"Милан", "Интер"}, "it", START_DATE_FOR_ALL_TEST_MATCHES),
                new NewMatch("Бундеслига", new String[]{"Бавария", "Боруссия Д"}, "gr", START_DATE_FOR_ALL_TEST_MATCHES));
    }

}
