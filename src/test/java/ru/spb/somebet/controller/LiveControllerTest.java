package ru.spb.somebet.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.spb.somebet.TestData;
import ru.spb.somebet.dto.LiveMatchDto;
import ru.spb.somebet.model.LiveMatch;
import ru.spb.somebet.service.live.LiveMatchService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LiveController.class)
public class LiveControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LiveMatchService liveMatchService;

    @Test
    public void getAllLiveMatches_successTest() throws Exception {
        List<LiveMatchDto> liveMatches = new ArrayList<>();
        for (LiveMatch liveMatch : TestData.getLiveMatchesWithZeroScore()) {
            liveMatches.add(LiveMatch.modelToDto(liveMatch));
        }
        Mockito.when(liveMatchService.getLiveMatches()).thenReturn(liveMatches);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/live")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }
}
