package com.resmed.rpsgame.db.repository;

import com.resmed.rpsgame.domain.Outcome;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlayerStatistics {

    private Outcome outcome;
    private Long count;
}
