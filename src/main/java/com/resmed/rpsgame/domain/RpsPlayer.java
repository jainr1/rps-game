package com.resmed.rpsgame.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RpsPlayer implements IPlayer {

    private final Shape shapeThrown;

    @Override
    public Shape getThrow() {
        return shapeThrown;
    }

    public Outcome play(IPlayer other) {

        Outcome outcome;
        if (this.getThrow() == other.getThrow()) {
            outcome = Outcome.drawn;
        } else if (this.getThrow().isDefeats(other.getThrow())) {
            outcome = Outcome.won;
        } else {
            outcome = Outcome.lost;
        }
        return outcome;
    }
}
