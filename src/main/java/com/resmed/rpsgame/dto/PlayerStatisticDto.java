package com.resmed.rpsgame.dto;

import com.resmed.rpsgame.db.repository.PlayerStatistics;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PlayerStatisticDto {

    public PlayerStatisticDto (List<PlayerStatistics> statisticsList, List<String> latestOutcomes) {

        long played = 0;
        for (PlayerStatistics statistics : statisticsList) {
            played = played + statistics.getCount();
            switch (statistics.getOutcome()) {
                case drawn:
                    setDrawn(statistics.getCount());
                    break;
                case won:
                    setWon(statistics.getCount());
                    break;
                case lost:
                    setLost(statistics.getCount());
                    break;
            }
        }
        setPlayed(played);
        setForm(latestOutcomes);
    }

    private long played;

    private long won;

    private long drawn;

    private long lost;

    private List<String> form = new ArrayList<>();
}
