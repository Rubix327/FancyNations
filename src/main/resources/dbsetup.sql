CREATE DATABASE IF NOT EXISTS @db;
USE @db;

CREATE TABLE TaskType (
    Id INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(50) NOT NULL,
    CONSTRAINT id_pk PRIMARY KEY (Id)
);

CREATE TABLE WorkerType (
    Id INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(50) NOT NULL,
    PRIMARY KEY (Id)
);

CREATE TABLE Player (
    Id INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(50) NOT NULL,
    PRIMARY KEY (Id),
    UNIQUE(Name)
);

CREATE TABLE Nation (
    Id INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL,
    PRIMARY KEY (Id),
    UNIQUE(Name)
);

CREATE TABLE Town (
    Id INT NOT NULL AUTO_INCREMENT,
    Nation INT NOT NULL,
    Name VARCHAR(100),
    PRIMARY KEY (Id),
    FOREIGN KEY (Nation) REFERENCES Nation (Id),
    UNIQUE(Name)
);

CREATE TABLE Task (
    Id INT NOT NULL AUTO_INCREMENT,
    TaskType INT NOT NULL,
    Name VARCHAR(100) NOT NULL,
    Player INT NOT NULL,
    Description VARCHAR(255) NOT NULL,
    TakeAmount INT NOT NULL,
    MinLevel INT NOT NULL,
    MaxLevel INT NOT NULL,
    MoneyReward DECIMAL NOT NULL,
    ExpReward DECIMAL NOT NULL,
    RepReward INT NOT NULL,
    PlacementDatetime INT NOT NULL,
    TimeToComplete INT,
    Priority INT NOT NULL,
    Objectives JSON NOT NULL,
    PRIMARY KEY (Id),
    FOREIGN KEY (TaskType) REFERENCES TaskType (Id),
    FOREIGN KEY (Player) REFERENCES Player (Id)
);

CREATE TABLE TownBoard (
    Id INT NOT NULL AUTO_INCREMENT,
    Town INT NOT NULL,
    Task INT NOT NULL,
    PRIMARY KEY (Id),
    FOREIGN KEY (Town) REFERENCES Town (Id),
    FOREIGN KEY (Task) REFERENCES Task (Id)
);


CREATE TABLE TakenTasks (
    Id INT NOT NULL AUTO_INCREMENT,
    Task INT NOT NULL,
    Player INT NOT NULL,
    Progress JSON,
    PRIMARY KEY (Id),
    FOREIGN KEY (Task) REFERENCES Task (Id),
    FOREIGN KEY (Player) REFERENCES Player (Id)
);

CREATE TABLE TownWorker (
    Id INT NOT NULL AUTO_INCREMENT,
    Player INT NOT NULL,
    Town INT NOT NULL,
    WorkerType INT NOT NULL,
    Salary DECIMAL,
    PRIMARY KEY (Id),
    FOREIGN KEY (Player) REFERENCES Player (Id),
    FOREIGN KEY (Town) REFERENCES Town (Id),
    FOREIGN KEY (WorkerType) REFERENCES WorkerType (Id)
);


INSERT INTO TaskType(Name) VALUES ('Food'), ('Resource'), ('Crafting'), ('Mobkill');
INSERT INTO WorkerType(Name) VALUES ('Mayor'), ('Helper'), ('Judge'), ('Other');
