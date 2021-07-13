package com.resmed.rpsgame.domain;

import java.util.List;
import java.util.Random;

public enum Shape {

    rock,
    paper,
    scissor;

    private static final List<Shape> values = List.of(values());
    private static final Random RANDOM = new Random();

    public static Shape randomShape()  {
        return values.get(RANDOM.nextInt(values.size()));
    }

    public boolean isDefeats(Shape other) {

        boolean isDefeats = false;
        switch (this) {
            case rock:
                isDefeats = (other == scissor);
                break;
            case paper:
                isDefeats = (other == rock);
                break;
            case scissor:
                isDefeats = (other == paper);
                break;
        }
        return isDefeats;
    }
}
