INSERT IGNORE INTO Nations(ID, Name) VALUES (1, "Europe"), (2, "Asia"), (3, "America");

INSERT IGNORE INTO Towns(ID, Nation, Name, Balance, StationsTax, AuctionTax, TasksTax)
VALUES (1, 1, "Poland", 0, 1, 2, 3), (2, 1, "Germany", 1000.50, 10, 0, 0), (3, 2, "China", 0, 1, 1, 1),
(4, 2, "Japan", 500, 2, 3, 4), (5, 3, "Canada", 1.5, 99, 100, 200), (6, 3, "Brazil", 0, 0, 0, 0);

INSERT IGNORE INTO TownResources(ID, Town, Name, Amount) VALUES (1, 1, "FLINT", 48), (2, 1, "MI_SHARP_FLINT", 10),
(3, 1, "DIRT", 64), (4, 2, "STONE", 144), (5, 2, "MI_STEEL_SWORD", 1);

INSERT IGNORE INTO Players(ID, Name) VALUES (2, "Rubix327"), (3, "Foldner"), (4, "_Pa3eTka_");

INSERT IGNORE INTO TownHouses(ID, Town, Owner, Location) VALUES (1, 1, 1, "world,0,0,80,0,0"), (2, 1, 2, "world,200,200,80,0,0"),
(3, 2, 3, "world,100,100,80,0,0");

INSERT IGNORE INTO TownWorkers(ID, Player, Town, Profession, DisplayName, Salary) VALUES (1, 2, 1, 1, "Mayor", 100),
(2, 3, 1, 2, "Мэр", 50), (3, 4, 2, 1, "Helper", 200);

INSERT IGNORE INTO Farms(ID, Town, Name, Location, Level, LoadedResource, Amount) VALUES
(1, 1, "Farm", "world,300,300,80,0,0", 1, "CARROT", 64), (2, 2, "Farm", "world,400,400,80,0,0", 4, "POTATO", 21),
(3, 3, "Farm", "world,200,200,80,0,0", 2, "WHEAT_SEEDS", 40);

INSERT IGNORE INTO DefendTowers(ID, Town, Name, Location, Level, LoadedResource, Amount) VALUES
(1, 1, "DefendTower", "world,250,400,80,0,0", 3, "", 0), (2, 2, "DefendTower", "world,400,550,80,0,0", 1, "ARROW", 19),
(3, 3, "DefendTower", "world,3500,400,80,0,0", 5, "ARROW", 10);

INSERT IGNORE INTO Workshops(ID, Town, Name, Location, Level) VALUES (1, 1, "Workshop", "world,290,400,80,0,0", 2),
(2, 2, "Workshop", "world,0,400,80,0,0", 5), (3, 3, "Workshop", "world,250,0,80,0,0", 4);

INSERT IGNORE INTO Barracks(ID, Town, Name, Location, Level) VALUES (1, 1, "Barrack", "world,50,400,80,0,0", 1),
(2, 2, "Barrack", "world,110,400,80,0,0", 2), (3, 3, "Barrack", "world,70,400,80,0,0", 4);

INSERT IGNORE INTO Churches(ID, Town, Name, Location, Level) VALUES (1, 1, "Church", "world,70,900,80,0,0", 3),
(2, 2, "Church", "world,70,343,80,0,0", 5), (3, 3, "Church", "world,70,600,80,0,0", 2);

INSERT IGNORE INTO Reputations(ID, Player, Town, Amount) VALUES (1, 2, 1, 0), (2, 3, 1, 100), (3, 4, 2, 50);

INSERT IGNORE INTO Tasks(ID, Town, Player, Name, Description, TakeAmount, MinLevel, MaxLevel, MoneyReward,
ExpReward, RepReward, PlacementDatetime, TimeToComplete, Priority) VALUES
(1, 1, 2, "EasyTask", "Bring me some vegetables", 5, 1, 30, 100, 15, 5, "2022-04-22", 60, 2),
(2, 1, 3, "For Low Skills", "Army supply!", 1, 1, 30, 200, 30, 20, "2022-04-23 00:00:00", 5, 1),
(3, 2, 4, "Make it faster", "Harder, better, faster, stronger", 20, 10, 20, 500, 200, 150, "2022-04-22 21:15:11", 20, 1);

INSERT IGNORE INTO Objectives(ID, Task, Type, Target, Amount) VALUES
(1, 1, "Food", "POTATO", 5), (2, 1, "Food", "CARROT", 10), (3, 1, "Resource", "DIAMOND", 16),
(4, 2, "Crafting", "IRON_SWORD", 1), (5, 2, "Crafting", "MI_STEEL_PICKAXE", 1),
(6, 3, "MobKill", "ZOMBIE", 20), (7, 3, "MobKill", "MM_SKELETAL_KNIGHT", 5);

INSERT IGNORE INTO TakenTasks(ID, Player, Task) VALUES (1, 4, 1), (2, 2, 2), (3, 3, 3);

INSERT IGNORE INTO TaskProgresses(ID, Objective, TakenTask, Progress) VALUES (1, 1, 1, 3), (2, 2, 2, 0), (3, 3, 3, 20);