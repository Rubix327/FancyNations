INSERT IGNORE INTO Nations(ID, Name) VALUES (1, "Europe"), (2, "Asia"), (3, "America");

INSERT IGNORE INTO Towns(ID, Nation, Name, Balance, StationsTax, AuctionTax, TasksTax)
VALUES (1, 1, "Poland", 0, 1, 2, 3), (2, 1, "Germany", 1000.50, 10, 0, 0), (3, 2, "China", 0, 1, 1, 1),
(4, 2, "Japan", 500, 2, 3, 4), (5, 3, "Canada", 1.5, 99, 100, 200), (6, 3, "Brazil", 0, 0, 0, 0);

INSERT IGNORE INTO TownResources(ID, Town, Name, Amount) VALUES (1, 1, "FLINT", 48), (2, 1, "MI_SHARP_FLINT", 10),
(3, 1, "DIRT", 64), (4, 2, "STONE", 144), (5, 2, "MI_STEEL_SWORD", 1);

INSERT IGNORE INTO Players(ID, Name) VALUES (1, '%server%'), (2, "Rubix327"), (3, "Foldner"), (4, "_Pa3eTka_");

INSERT IGNORE INTO TownHouses(ID, Town, Owner, Location) VALUES (1, 1, 1, "world,0,0,80,0,0"), (2, 1, 2, "world,200,200,80,0,0"),
(3, 2, 3, "world,100,100,80,0,0");

INSERT IGNORE INTO Professions(ID, Name, Salary, StationsTaxBonus, AuctionTaxBonus, TasksTaxBonus, PermissionOpenBank, PermissionDeposit, PermissionWithdraw)
VALUES (1, 'Mayor', 12000, 0.1, 0.2, 0.2, 1, 1, 1), (2, 'Helper', 6000, 0.1, 0.2, 0.1, 1, 1, 0);

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

INSERT IGNORE INTO Tasks(ID, Town, Name, CreatorType, Creator, Description, CompletionsLeft, MinLevel, MaxLevel,
MoneyReward, ExpReward, RepReward, PlacementDateTime, TerminationDateTime, TimeToComplete, Priority) VALUES
(1, 1, "We need food", "Town", 2, "Bring me some vegetables", 5, 1, 30, 100, 15, 5, "2022-04-22", "2022-05-23", 60, 2),
(2, 1, "We need more", "Player", 3, "Army supply!",  1, 1, 30, 200, 30, 20, "2022-04-23 00:00:00", "2022-05-24", 30, 1),
(3, 2, "Make it faster", "Town", 4, "Harder, better, faster, stronger",  20, 10, 20, 500, 200, 150, "2022-05-22 21:15:11", "2022-04-23 12:00:00", 43800, 1),
(4, 2, "We need resources", "Nation", 1, "Server task",  20, 10, 20, 500, 200, 150, "2022-05-22 21:15:11", "2022-04-23 12:00:00", 43200, 1),
(5, 2, "We need grass and chainmail", "Town", 2, "Server task",  20, 10, 20, 500, 200, 150, "2022-05-22 21:15:11", "2022-04-23 12:00:00", 43200, 1),
(6, 2, "Hello world", "Nation", 1, "Server task",  20, 10, 20, 500, 200, 150, "2022-05-22 21:15:11", "2022-04-23 12:00:00", 43600, 1);

INSERT IGNORE INTO Objectives(ID, Task, Type, Target, Amount) VALUES
(1, 1, "Food", "POTATO", 5), (2, 1, "Food", "CARROT", 10), (3, 1, "Resource", "DIAMOND", 16),
(4, 2, "Crafting", "IRON_SWORD", 1), (5, 2, "Crafting", "MI_STEEL_PICKAXE", 1),
(6, 3, "MobKill", "ZOMBIE", 20), (7, 3, "MobKill", "MM_SKELETAL_KNIGHT", 5),
(8, 4, "Resource", "MI_UNCOMMON_WEAPON_ESSENCE", 2), (9, 4, "Resource", "STONE", 16),
(10, 5, "Resource", "GRASS", 20), (11, 5, "Crafting", "MI_MYTHRIL_CHAINMAIL", 2);

INSERT IGNORE INTO TakenTasks(ID, Player, Task, TakingDateTime) VALUES
(1, 4, 1, "2022-05-18"), (2, 2, 2, "2022-05-20 20:30:40"), (3, 3, 3, "2022-05-22 11:11:11");

INSERT IGNORE INTO TaskProgresses(ID, Objective, TakenTask, Progress) VALUES (1, 1, 1, 3), (2, 2, 2, 0), (3, 3, 3, 20);