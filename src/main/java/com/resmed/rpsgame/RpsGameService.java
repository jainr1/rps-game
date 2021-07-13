package com.resmed.rpsgame;

import com.resmed.rpsgame.db.entity.Player;
import com.resmed.rpsgame.db.entity.Result;
import com.resmed.rpsgame.db.repository.PlayerRepository;
import com.resmed.rpsgame.db.repository.PlayerStatistics;
import com.resmed.rpsgame.db.repository.ResultRepository;
import com.resmed.rpsgame.domain.IPlayer;
import com.resmed.rpsgame.domain.Outcome;
import com.resmed.rpsgame.domain.RpsPlayer;
import com.resmed.rpsgame.domain.Shape;
import com.resmed.rpsgame.dto.PlayerDto;
import com.resmed.rpsgame.dto.PlayerStatisticDto;
import com.resmed.rpsgame.dto.ResultDto;
import com.resmed.rpsgame.exception.ErrorCode;
import com.resmed.rpsgame.exception.RpsGameException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RpsGameService implements IRpsGameService {

    private final PlayerRepository playerRepository;

    private final ResultRepository resultRepository;

    public ResultDto play(String playerId, String shapeThrownString)  {

        Optional<Player> optionalPlayer = playerRepository.findById(playerId);
        Player player = validatePlayer(optionalPlayer, playerId);
        Shape shapeThrown = validateShape(shapeThrownString);
        IPlayer humanPlayer = new RpsPlayer(shapeThrown);
        IPlayer machinePlayer = new RpsPlayer(Shape.randomShape());
        Outcome outcome = humanPlayer.play(machinePlayer);
        return new ResultDto(saveResult(player, humanPlayer.getThrow(), machinePlayer.getThrow(), outcome));
    }

    public PlayerStatisticDto getStatistics(String playerId) {

        Optional<Player> optionalPlayer = playerRepository.findById(playerId);
        validatePlayer(optionalPlayer, playerId);
        List<PlayerStatistics> playerStatistics = resultRepository.countByOutcome(playerId);
        List<Result> results = resultRepository.findByPlayerId(playerId, PageRequest.of(0, 5, Sort.Direction.DESC, "dateCreated"));
        List<String> latestOutcomes = results.stream().map(result -> result.getOutcome().name()).collect(Collectors.toList());
        return new PlayerStatisticDto(playerStatistics, latestOutcomes);
    }

    // TODO: add pagination support, currently fetching all records
    public List<PlayerDto> getPlayers()  {

        List<Player> players = playerRepository.findAll();
        return players.stream()
                .map(PlayerDto::new)
                .collect(Collectors.toList());
    }

    private Player validatePlayer(Optional<Player> player, String playerId) {
        if (player.isEmpty()) {
            throw new RpsGameException(ErrorCode.RPS_1, "Player with id does not exists:" + playerId);
        }
        return player.get();
    }

    private Shape validateShape(String shapeString) {

        Shape shape;
        try {
            shape = Shape.valueOf(shapeString);
        } catch (Exception ex) {
            throw new RpsGameException(ErrorCode.RPS_2, "Provide shape not valid, should be one of:" + List.of(Shape.values()));
        }
        return shape;
    }

    private Result saveResult(Player player, Shape playerThrow, Shape machineThrow, Outcome outcome) {

        Result result = new Result();
        result.setId(UUID.randomUUID().toString());
        result.setPlayerThrow(playerThrow);
        result.setMachineThrow(machineThrow);
        result.setOutcome(outcome);
        result.setPlayer(player);
        result = resultRepository.save(result);
        return result;
    }
}
