package com.resmed.rpsgame.dto;

import com.resmed.rpsgame.db.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PlayerDto {

    public PlayerDto(Player player) {

        setDateCreated(player.getDateCreated());
        setDateUpdated(player.getDateUpdated());
        setId(player.getId());
        setUsername(player.getUsername());
        setFirstName(player.getFirstName());
        setLastName(player.getLastName());
    }

    private String id;

    private LocalDateTime dateCreated;

    private LocalDateTime dateUpdated;

    private String firstName;

    private String lastName;

    private String username;
}
