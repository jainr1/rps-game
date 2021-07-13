package com.resmed.rpsgame.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RpsPlayerTest {

    @Test
    public void testPlay() {

        assertEquals(Outcome.won, new RpsPlayer(Shape.rock).play(new RpsPlayer(Shape.scissor)));
        assertEquals(Outcome.lost, new RpsPlayer(Shape.rock).play(new RpsPlayer(Shape.paper)));
        assertEquals(Outcome.drawn, new RpsPlayer(Shape.rock).play(new RpsPlayer(Shape.rock)));

        assertEquals(Outcome.won, new RpsPlayer(Shape.scissor).play(new RpsPlayer(Shape.paper)));
        assertEquals(Outcome.lost, new RpsPlayer(Shape.scissor).play(new RpsPlayer(Shape.rock)));
        assertEquals(Outcome.drawn, new RpsPlayer(Shape.scissor).play(new RpsPlayer(Shape.scissor)));

        assertEquals(Outcome.won, new RpsPlayer(Shape.paper).play(new RpsPlayer(Shape.rock)));
        assertEquals(Outcome.lost, new RpsPlayer(Shape.paper).play(new RpsPlayer(Shape.scissor)));
        assertEquals(Outcome.drawn, new RpsPlayer(Shape.paper).play(new RpsPlayer(Shape.paper)));
    }
}