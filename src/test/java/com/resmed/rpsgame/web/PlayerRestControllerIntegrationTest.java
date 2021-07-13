package com.resmed.rpsgame.web;

import com.resmed.rpsgame.IRpsGameService;
import com.resmed.rpsgame.domain.Outcome;
import com.resmed.rpsgame.domain.Shape;
import com.resmed.rpsgame.dto.PlayerDto;
import com.resmed.rpsgame.dto.PlayerStatisticDto;
import com.resmed.rpsgame.dto.ResultDto;
import com.resmed.rpsgame.exception.ErrorCode;
import com.resmed.rpsgame.exception.RpsGameException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest // Not using @WebMvcTest due to security
@AutoConfigureMockMvc(addFilters = false) // Add filters to disable security
class PlayerRestControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IRpsGameService game;

    @Test
    public void whenThrowShape_returnResult() throws Exception {

        ResultDto resultDto = new ResultDto(Shape.paper, Shape.rock, Outcome.won);
        given(game.play(Mockito.any(), Mockito.any())).willReturn(resultDto);

        mvc.perform(post("/api/players/1234/throws/paper").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerThrow", is("paper")))
                .andExpect(jsonPath("$.machineThrow", is("rock")))
                .andExpect(jsonPath("$.outcome", is("won")));
        verify(game, VerificationModeFactory.times(1)).play(Mockito.any(), Mockito.any());
    }

    @Test
    public void whenThrowShape_InvalidPlayer() throws Exception {

        given(game.play(Mockito.any(), Mockito.any())).willThrow(new RpsGameException(ErrorCode.RPS_1, "Player with id not found"));
        mvc.perform(post("/api/players/1234/throws/paper").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode", is(404)))
                .andExpect(jsonPath("$.shortMessage", is(ErrorCode.RPS_1.shortMessage)))
                .andExpect(jsonPath("$.description", is("Player with id not found")));

        verify(game, VerificationModeFactory.times(1)).play(Mockito.any(), Mockito.any());
    }

    @Test
    public void whenThrowShape_InvalidShape() throws Exception {

        given(game.play(Mockito.any(), Mockito.any())).willThrow(new RpsGameException(ErrorCode.RPS_2, "Shape is not valid"));
        mvc.perform(post("/api/players/1234/throws/p").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode", is(400)))
                .andExpect(jsonPath("$.shortMessage", is(ErrorCode.RPS_2.shortMessage)))
                .andExpect(jsonPath("$.description", is("Shape is not valid")));

        verify(game, VerificationModeFactory.times(1)).play(Mockito.any(), Mockito.any());
    }

    @Test
    public void whenGetStatistics_returnStatistics() throws Exception {

        PlayerStatisticDto statisticDto = new PlayerStatisticDto(10, 3, 3, 4, Arrays.asList("won", "lost", "won", "drawn", "lost"));
        given(game.getStatistics(Mockito.any())).willReturn(statisticDto);

        mvc.perform(get("/api/players/1234/statistics").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.played", is(10)))
                .andExpect(jsonPath("$.won", is(3)))
                .andExpect(jsonPath("$.drawn", is(3)))
                .andExpect(jsonPath("$.lost", is(4)))
                .andExpect(jsonPath("$.form", is(Arrays.asList("won", "lost", "won", "drawn", "lost"))));
        verify(game, VerificationModeFactory.times(1)).getStatistics(Mockito.any());
    }

    @Test
    public void whenGetStatistics_InvalidPlayer() throws Exception {

        given(game.getStatistics(Mockito.any())).willThrow(new RpsGameException(ErrorCode.RPS_1, "Player with id not found"));
        mvc.perform(get("/api/players/1234/statistics").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode", is(404)))
                .andExpect(jsonPath("$.shortMessage", is(ErrorCode.RPS_1.shortMessage)))
                .andExpect(jsonPath("$.description", is("Player with id not found")));
        verify(game, VerificationModeFactory.times(1)).getStatistics(Mockito.any());
    }

    @Test
    public void whenGetPlayers_returnPlayers() throws Exception {

        List<PlayerDto> players = new ArrayList<>();
        players.add(new PlayerDto(
                "1",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "test1",
                "surname1",
                "test1username"));
        players.add(new PlayerDto(
                "2",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "test2",
                "surname2",
                "test2username"));
        given(game.getPlayers()).willReturn(players);

        mvc.perform(get("/api/players").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalHits", is(2)))
                .andExpect(jsonPath("$.results[0].id", is("1")))
                .andExpect(jsonPath("$.results[1].id", is("2")));
        verify(game, VerificationModeFactory.times(1)).getPlayers();
    }
}