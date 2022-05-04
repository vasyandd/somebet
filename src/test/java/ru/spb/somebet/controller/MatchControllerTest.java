package ru.spb.somebet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.spb.somebet.dto.BetDto;
import ru.spb.somebet.dto.FutureMatchDto;
import ru.spb.somebet.dto.NewMatch;
import ru.spb.somebet.model.Bet;
import ru.spb.somebet.model.FutureMatch;
import ru.spb.somebet.model.Region;
import ru.spb.somebet.model.User;
import ru.spb.somebet.service.match.MatchService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MatchController.class)
public class MatchControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private MatchService service;

    @Test
    public void saveMatch_successTest() throws Exception {
        NewMatch newMatch = new NewMatch("РПЛ", new String[]{"Зенит", "ЦСКА"}, "ru", LocalDateTime.now());

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/matches")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(newMatch));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated());
    }

    @Test
    public void getFutureMatches_successTest() throws Exception {
        List<BetDto> betDtos = new ArrayList<>() {{add( new BetDto(3L, 1.33f, Bet.Type.WIN1TEAM));}};
        FutureMatchDto futureMatchDto = new FutureMatchDto(null,"РПЛ", new String[]{"Зенит", "ЦСКА"},
                Region.RUSSIA, betDtos, LocalDateTime.now());
        List<FutureMatchDto> matches = new ArrayList<>(){{add(futureMatchDto);}};

        Mockito.when(service.getMatches()).thenReturn(matches);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/matches")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}
