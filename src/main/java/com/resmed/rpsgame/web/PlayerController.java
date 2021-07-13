package com.resmed.rpsgame.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.resmed.rpsgame.IRpsGameService;
import com.resmed.rpsgame.dto.PlayerDto;
import com.resmed.rpsgame.dto.PlayerStatisticDto;
import com.resmed.rpsgame.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/players")
public class PlayerController {

    private final IRpsGameService game;

    @PostMapping("{id}/throws/{shape}")
    public ResultDto throwShape(@PathVariable String id, @PathVariable("shape") String playerThrow) {
        return game.play(id, playerThrow);
    }

    @GetMapping("{id}/statistics")
    @ResponseBody
    public PlayerStatisticDto getStatistics(@PathVariable String id) throws JsonProcessingException {
        return game.getStatistics(id);
    }

    @GetMapping
    @ResponseBody
    // TODO: handle pagination
    public Map<String, Object> getPlayers() {
        List<PlayerDto> players = game.getPlayers();
        return Map.of("results", players, "totalHits", players.size());
    }
}
