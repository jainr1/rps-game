package com.resmed.rpsgame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.resmed.rpsgame.dto.PlayerDto;
import com.resmed.rpsgame.dto.PlayerStatisticDto;
import com.resmed.rpsgame.dto.ResultDto;

import java.util.List;

public interface IRpsGameService {

    ResultDto play(String id, String playerThrow);

    PlayerStatisticDto getStatistics(String id) throws JsonProcessingException;

    List<PlayerDto> getPlayers();
}
