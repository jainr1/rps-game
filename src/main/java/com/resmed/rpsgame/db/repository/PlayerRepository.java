package com.resmed.rpsgame.db.repository;

import com.resmed.rpsgame.db.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {

    Player findByUsername(String username);
}

