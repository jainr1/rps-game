-- player table
CREATE TABLE player
(
    id           varchar(255) NOT NULL,
    date_created timestamp NULL,
    date_updated timestamp NULL,
    first_name   varchar(100) NULL,
    last_name    varchar(100) NULL,
    password     varchar(100) NULL,
    username     varchar(100) NOT NULL,
    CONSTRAINT player_un UNIQUE (username),
    CONSTRAINT player_pkey PRIMARY KEY (id)
);

-- player_statistics table
CREATE TABLE result
(
    id            varchar(255) NOT NULL,
    date_created  timestamp NULL,
    machine_throw varchar(10) NULL,
    player_throw  varchar(10) NULL,
    outcome       varchar(10) NULL,
    player_id     varchar(255) NOT NULL,
    CONSTRAINT player_statistics_pkey PRIMARY KEY (id),
    CONSTRAINT fk_player FOREIGN KEY (player_id) REFERENCES player (id)
);
