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
import ru.spb.somebet.exception.UserNotFoundException;
import ru.spb.somebet.model.Bet;
import ru.spb.somebet.model.User;
import ru.spb.somebet.service.user.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void doBet_successTest() throws Exception{
        BetDto betDto = new BetDto(3L, 1.33f, Bet.Type.WIN1TEAM);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/users/bet?id=3&betValue=500")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(betDto));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }


    @Test
    public void findById_exceptionTest() throws Exception {
        Mockito.when(userService.findById(3L)).thenThrow(UserNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/3"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAll_successTest() throws Exception{
        User user = new User(null, "Vasya", "qwerty", 100f, null);
        List<User> users = new ArrayList<>() {{add(user);}};

        Mockito.when(userService.getUsers()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}
