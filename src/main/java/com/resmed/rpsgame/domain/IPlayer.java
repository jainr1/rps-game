package com.resmed.rpsgame.domain;

public interface IPlayer {

    Shape getThrow();

    Outcome play(IPlayer other);
}