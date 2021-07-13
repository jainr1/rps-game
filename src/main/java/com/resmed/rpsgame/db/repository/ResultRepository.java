package com.resmed.rpsgame.db.repository;

import com.resmed.rpsgame.db.entity.Result;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, String> {

    @Query("SELECT new com.resmed.rpsgame.db.repository.PlayerStatistics(r.outcome, COUNT(r.outcome)) FROM Result AS r WHERE r.player.id = ?1 GROUP BY r.outcome ")
    List<PlayerStatistics> countByOutcome(String playerId);

    List<Result> findByPlayerId(String playerId, Pageable pageable);
}
