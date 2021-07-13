package com.resmed.rpsgame.db.entity;

import com.resmed.rpsgame.domain.Outcome;
import com.resmed.rpsgame.domain.Shape;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Result {

    @Id
    private String id;

    @Column(columnDefinition = "TIMESTAMP")
    @Setter(value = AccessLevel.NONE)
    private LocalDateTime dateCreated;

    @Enumerated(EnumType.STRING)
    private Shape playerThrow;

    @Enumerated(EnumType.STRING)
    private Shape machineThrow;

    @Enumerated(EnumType.STRING)
    private Outcome outcome;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id", nullable=false)
    private Player player;

    @PrePersist
    public void prePersist() {
        dateCreated = LocalDateTime.now();
    }
}
