package com.resmed.rpsgame;

import com.resmed.rpsgame.db.entity.Player;
import com.resmed.rpsgame.db.entity.Result;
import com.resmed.rpsgame.db.repository.PlayerRepository;
import com.resmed.rpsgame.db.repository.PlayerStatistics;
import com.resmed.rpsgame.db.repository.ResultRepository;
import com.resmed.rpsgame.domain.IPlayer;
import com.resmed.rpsgame.domain.Outcome;
import com.resmed.rpsgame.domain.Shape;
import com.resmed.rpsgame.exception.RpsGameException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RpsGameServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private ResultRepository resultRepository;

    @InjectMocks
    private RpsGameService gameService;

    @Test
    public void testPlay() {

        Player player1 = new Player();
        player1.setId("1");
        player1.setFirstName("User1");
        player1.setLastName("Surname1");
        player1.setUsername("User1Surname1");

        Result result = new Result();
        result.setId("1");
        result.setPlayer(player1);
        result.setMachineThrow(Shape.paper);
        result.setPlayerThrow(Shape.rock);
        result.setOutcome(Outcome.lost);

        given(playerRepository.findById("1")).willReturn(Optional.of(player1));
        given(resultRepository.save(Mockito.any())).willReturn(result);

        assertEquals(result.getMachineThrow(), gameService.play("1", Shape.rock.name()).getMachineThrow());
        assertEquals(result.getPlayerThrow(), gameService.play("1", Shape.rock.name()).getPlayerThrow());
        assertEquals(result.getOutcome(), gameService.play("1", Shape.rock.name()).getOutcome());

        assertThrows(RpsGameException.class, () ->  gameService.play("2", Shape.rock.name()), "Player if not valid");
        assertThrows(RpsGameException.class, () ->  gameService.play("1", "roc"), "Shape not valid");
    }

    @Test
    public void testGetStatistics() {

        Player player1 = new Player();
        player1.setId("1");
        player1.setFirstName("User1");
        player1.setLastName("Surname1");
        player1.setUsername("User1Surname1");

        List<PlayerStatistics> playerStatisticsList =  List.of(
                new PlayerStatistics(Outcome.won, 10L),
                new PlayerStatistics(Outcome.lost, 9L),
                new PlayerStatistics(Outcome.drawn, 8L));

        Result result = new Result();
        result.setId("1");
        result.setPlayer(player1);
        result.setMachineThrow(Shape.paper);
        result.setPlayerThrow(Shape.rock);
        result.setOutcome(Outcome.lost);

        Result result1 = new Result();
        result1.setId("1");
        result1.setPlayer(player1);
        result1.setMachineThrow(Shape.paper);
        result1.setPlayerThrow(Shape.rock);
        result1.setOutcome(Outcome.lost);

        Result result2 = new Result();
        result2.setId("2");
        result2.setPlayer(player1);
        result2.setMachineThrow(Shape.rock);
        result2.setPlayerThrow(Shape.paper);
        result2.setOutcome(Outcome.won);

        List<Result> results = List.of(result, result1, result2);
        given(resultRepository.countByOutcome("1")).willReturn(playerStatisticsList);
        given(playerRepository.findById("1")).willReturn(Optional.of(player1));
        given(resultRepository.findByPlayerId(Mockito.eq("1"), Mockito.any(PageRequest.class))).willReturn(results);

        assertEquals(27L, gameService.getStatistics("1").getPlayed());
        assertEquals(10L, gameService.getStatistics("1").getWon());
        assertEquals(9L, gameService.getStatistics("1").getLost());
        assertEquals(8L, gameService.getStatistics("1").getDrawn());
        assertEquals(List.of("lost","lost","won"), gameService.getStatistics("1").getForm());

        assertThrows(RpsGameException.class, () -> gameService.getStatistics("2"));
    }

    @Test
    public void testGetPlayers() {

        Player player1 = new Player();
        player1.setId("1");
        player1.setFirstName("User1");
        player1.setLastName("Surname1");
        player1.setUsername("User1Surname1");

        Player player2 = new Player();
        player2.setId("2");
        player2.setFirstName("User2");
        player2.setLastName("Surname2");
        player2.setUsername("User2Surname2");

        List<Player> players = List.of(player1, player2);
        given(playerRepository.findAll()).willReturn(players);

        assertEquals(2, gameService.getPlayers().size());
        assertEquals(player1.getId(), gameService.getPlayers().get(0).getId());
        assertEquals(player2.getId(), gameService.getPlayers().get(1).getId());
    }
}