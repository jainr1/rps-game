package com.resmed.rpsgame.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShapeTest {

    @Test
    public void testIsDefeats() {

        assertTrue(Shape.paper.isDefeats(Shape.rock));
        assertFalse(Shape.paper.isDefeats(Shape.scissor));
        assertFalse(Shape.paper.isDefeats(Shape.paper));

        assertTrue(Shape.scissor.isDefeats(Shape.paper));
        assertFalse(Shape.scissor.isDefeats(Shape.rock));
        assertFalse(Shape.scissor.isDefeats(Shape.scissor));

        assertTrue(Shape.rock.isDefeats(Shape.scissor));
        assertFalse(Shape.rock.isDefeats(Shape.paper));
        assertFalse(Shape.rock.isDefeats(Shape.rock));
    }
}