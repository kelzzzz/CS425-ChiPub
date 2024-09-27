CREATE DATABASE IF NOT EXISTS hw_1_2;

USE hw_1_2;

CREATE TABLE IF NOT EXISTS Team (
    TmID CHAR(3) PRIMARY KEY, -- inline pk constraint appears to infer not null
    ConfID CHAR(2),
    Ranking TINYINT NOT NULL, -- can this be computed from won/lost/games?
    Playoff VARCHAR(3),
    Name VARCHAR(100) NOT NULL,
    Won SMALLINT DEFAULT 0,
    Lost SMALLINT DEFAULT 0,
    Games SMALLINT DEFAULT 0 -- can this be computed from Won & Lost?
);

CREATE TABLE IF NOT EXISTS Person (
    BioID VARCHAR(20) PRIMARY KEY,
    FirstName VARCHAR(127),
    LastName VARCHAR(127) NOT NULL,
    BirthDate DATE,
    BirthCity VARCHAR(127),
    BirthCountry CHAR(3)
);

CREATE TABLE IF NOT EXISTS Player (
    BioID VARCHAR(20) PRIMARY KEY,
    TmID CHAR(3) NOT NULL,
    -- this is an ISA relationship, not a M:M relationship, composite key would
    -- be incorrect since trades can be done and player could change teams
    Played SMALLINT DEFAULT 0,
    Started SMALLINT DEFAULT 0,
    Minutes SMALLINT DEFAULT 0,
    Points SMALLINT DEFAULT 0,
    Attempts SMALLINT DEFAULT 0,
    Made SMALLINT DEFAULT 0,
    FOREIGN KEY (BioID)
        REFERENCES Person(BioID)
        ON DELETE CASCADE,
    FOREIGN KEY (TmID)
        REFERENCES Team(TmID)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Coach (
    BioID VARCHAR(20) PRIMARY KEY,
    TmID CHAR(3) NOT NULL,
    -- this is an ISA relationship, not a M:M relationship, composite key would
    -- be incorrect since trades can be done and player could change teams
    Won SMALLINT DEFAULT 0,
    Lost SMALLINT DEFAULT 0,
    Games SMALLINT DEFAULT 0,
    Stint TINYINT DEFAULT 0,
    FOREIGN KEY (BioID)
        REFERENCES Person(BioID)
        ON DELETE CASCADE,
    FOREIGN KEY (TmID)
        REFERENCES Team(TmID)
        ON DELETE CASCADE
);
