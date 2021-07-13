package com.resmed.rpsgame.db.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Player {

    @Id
    private String id;

    @Column(columnDefinition = "TIMESTAMP")
    @Setter(value = AccessLevel.NONE)
    private LocalDateTime dateCreated;

    @Column(columnDefinition = "TIMESTAMP")
    @Setter(value = AccessLevel.NONE)
    private LocalDateTime dateUpdated;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    @PreUpdate
    public void preUpdate() {
        dateUpdated = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        dateCreated = LocalDateTime.now();
        dateUpdated = dateCreated;
    }
}
