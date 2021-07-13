package com.resmed.rpsgame.dto;

import com.resmed.rpsgame.db.entity.Result;
import com.resmed.rpsgame.domain.Outcome;
import com.resmed.rpsgame.domain.Shape;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResultDto {

    public ResultDto(Result result) {
        setPlayerThrow(result.getPlayerThrow());
        setMachineThrow(result.getMachineThrow());
        setOutcome(result.getOutcome());
    }

    private Shape playerThrow;

    private Shape machineThrow;

    private Outcome outcome;
}
