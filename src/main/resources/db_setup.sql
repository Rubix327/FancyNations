CREATE DATABASE IF NOT EXISTS @db;
USE @db;

CREATE TABLE IF NOT EXISTS Professions (
    Id INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(50) NOT NULL,
    Salary DECIMAL NOT NULL,
    StationsTaxBonus DECIMAL NOT NULL,
    AuctionTaxBonus DECIMAL NOT NULL,
    TasksTaxBonus DECIMAL NOT NULL,
    PermissionOpenBank BIT NOT NULL,
    PermissionDeposit BIT NOT NULL,
    PermissionWithdraw BIT NOT NULL,
    PRIMARY KEY (Id),
    UNIQUE(Name)
);

CREATE TABLE IF NOT EXISTS Players (
    Id INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(50) NOT NULL,
    PRIMARY KEY (Id),
    UNIQUE(Name)
);

CREATE TABLE IF NOT EXISTS Nations (
    Id INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(50) NOT NULL,
    PRIMARY KEY (Id),
    UNIQUE(Name)
);

CREATE TABLE IF NOT EXISTS Towns (
    Id INT NOT NULL AUTO_INCREMENT,
    Nation INT NOT NULL,
    Name VARCHAR(50),
    Balance DECIMAL NOT NULL,
    StationsTax DECIMAL NOT NULL,
    AuctionTax DECIMAL NOT NULL,
    TasksTax DECIMAL NOT NULL,
    PRIMARY KEY (Id),
    FOREIGN KEY (Nation) REFERENCES Nations (Id),
    UNIQUE(Name)
);

CREATE TABLE IF NOT EXISTS Tasks (
    Id INT NOT NULL AUTO_INCREMENT,
    Town INT NOT NULL,
    Player INT NOT NULL,
    Name VARCHAR(100) NOT NULL,
    Description VARCHAR(255) NOT NULL,
    TakeAmount INT NOT NULL,
    MinLevel INT NOT NULL,
    MaxLevel INT NOT NULL,
    MoneyReward DECIMAL NOT NULL,
    ExpReward DECIMAL NOT NULL,
    RepReward INT NOT NULL,
    PlacementDatetime DATETIME NOT NULL,
    TimeToComplete INT,
    Priority INT NOT NULL,
    PRIMARY KEY (Id),
    FOREIGN KEY (Town) REFERENCES Towns (Id),
    FOREIGN KEY (Player) REFERENCES Players (Id)
);

CREATE TABLE IF NOT EXISTS TakenTasks (
    Id INT NOT NULL AUTO_INCREMENT,
    Player INT NOT NULL,
    Task INT NOT NULL,
    PRIMARY KEY (Id),
    FOREIGN KEY (Player) REFERENCES Players (Id),
    FOREIGN KEY (Task) REFERENCES Tasks (Id)
);

CREATE TABLE IF NOT EXISTS TownWorkers (
    Id INT NOT NULL AUTO_INCREMENT,
    Player INT NOT NULL,
    Town INT NOT NULL,
    Profession INT NOT NULL,
    DisplayName VARCHAR(50) NOT NULL,
    Salary DECIMAL NOT NULL,
    PRIMARY KEY (Id),
    FOREIGN KEY (Player) REFERENCES Players (Id),
    FOREIGN KEY (Town) REFERENCES Towns (Id),
    FOREIGN KEY (Profession) REFERENCES Professions (Id),
    UNIQUE(Player)
);

CREATE TABLE IF NOT EXISTS TownResources (
    Id INT NOT NULL AUTO_INCREMENT,
    Town INT NOT NULL,
    Name VARCHAR(50) NOT NULL,
    Amount INT NOT NULL,
    PRIMARY KEY (Id),
    FOREIGN KEY (Town) REFERENCES Towns (Id)
);

CREATE TABLE IF NOT EXISTS Objectives (
    Id INT NOT NULL AUTO_INCREMENT,
    Type VARCHAR(50) NOT NULL,
    Target VARCHAR(50) NOT NULL,
    Amount INT NOT NULL,
    Task INT NOT NULL,
    PRIMARY KEY (Id),
    FOREIGN KEY (Task) REFERENCES Tasks (Id)
);

CREATE TABLE IF NOT EXISTS TaskProgresses (
    Id INT NOT NULL AUTO_INCREMENT,
    Objective INT NOT NULL,
    TakenTask INT NOT NULL,
    Progress INT NOT NULL,
    PRIMARY KEY (Id),
    FOREIGN KEY (TakenTask) REFERENCES TakenTasks (Id),
    FOREIGN KEY (Objective) REFERENCES Objectives (Id)
);

CREATE TABLE IF NOT EXISTS TownHouses (
    Id INT NOT NULL AUTO_INCREMENT,
    Town INT NOT NULL,
    Owner INT NOT NULL,
    Location VARCHAR(100) NOT NULL,
    PRIMARY KEY (Id),
    FOREIGN KEY (Town) REFERENCES Towns (Id),
    FOREIGN KEY (Owner) REFERENCES Players (Id)
);

CREATE TABLE IF NOT EXISTS Farms (
    Id INT NOT NULL AUTO_INCREMENT,
    Town INT NOT NULL,
    Name VARCHAR(100) NOT NULL,
    Location VARCHAR(100) NOT NULL,
    Level INT NOT NULL,
    LoadedResource VARCHAR(50),
    Amount INT,
    PRIMARY KEY (Id),
    FOREIGN KEY (Town) REFERENCES Towns (Id)
);

CREATE TABLE IF NOT EXISTS DefendTowers (
    Id INT NOT NULL AUTO_INCREMENT,
    Town INT NOT NULL,
    Name VARCHAR(100) NOT NULL,
    Location VARCHAR(100) NOT NULL,
    Level INT NOT NULL,
    LoadedResource VARCHAR(50),
    Amount INT,
    PRIMARY KEY (Id),
    FOREIGN KEY (Town) REFERENCES Towns (Id)
);

CREATE TABLE IF NOT EXISTS Workshops (
    Id INT NOT NULL AUTO_INCREMENT,
    Town INT NOT NULL,
    Name VARCHAR(100) NOT NULL,
    Location VARCHAR(100) NOT NULL,
    Level INT NOT NULL,
    PRIMARY KEY (Id),
    FOREIGN KEY (Town) REFERENCES Towns (Id)
);

CREATE TABLE IF NOT EXISTS Barracks (
    Id INT NOT NULL AUTO_INCREMENT,
    Town INT NOT NULL,
    Name VARCHAR(100) NOT NULL,
    Location VARCHAR(100) NOT NULL,
    Level INT NOT NULL,
    PRIMARY KEY (Id),
    FOREIGN KEY (Town) REFERENCES Towns (Id)
);

CREATE TABLE IF NOT EXISTS Churches (
    Id INT NOT NULL AUTO_INCREMENT,
    Town INT NOT NULL,
    Name VARCHAR(100) NOT NULL,
    Location VARCHAR(100) NOT NULL,
    Level INT NOT NULL,
    PRIMARY KEY (Id),
    FOREIGN KEY (Town) REFERENCES Towns (Id)
);

CREATE TABLE IF NOT EXISTS Reputations (
    Id INT NOT NULL AUTO_INCREMENT,
    Player INT NOT NULL,
    Town INT NOT NULL,
    Amount INT NOT NULL,
    PRIMARY KEY (Id),
    FOREIGN KEY (Player) REFERENCES Players (Id),
    FOREIGN KEY (Town) REFERENCES Towns (Id)
);