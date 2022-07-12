general_exists_id: SELECT Id FROM @Table WHERE Id = @Id;
general_exists_name: SELECT Id FROM @Table WHERE Name = '@Name';
general_get_id: SELECT * FROM @Table WHERE Id = @Id;
general_get_name: SELECT * FROM @Table WHERE Name = '@Name';
general_update: UPDATE @Table SET @Var = '@Value' WHERE Id = @Id;
general_remove: DELETE FROM @Table WHERE Id = @Id;
general_get_all: SELECT * FROM @Table;
general_get_next_id: SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = '@Table';
general_get_names: SELECT Name FROM @Table;
barracks_add: INSERT INTO @Table (Town, Name, Location, Level) VALUES(@TownId, '@Name', '@Location', @Level);
churches_add: INSERT INTO @Table (Town, Name, Location, Level) VALUES(@TownId, '@Name', '@Location', @Level);
defend_towers_add: INSERT INTO @Table (Town, Name, Location, Level, LoadedResource, Amount) VALUES (@TownId, '@Name', '@Location', @Level, '@LoadedResource', @Amount);
farms_add: INSERT INTO @Table (Town, Name, Location, Level, LoadedResource, Amount) VALUES (@TownId, '@Name', '@Location', @Level, '@LoadedResource', @Amount);
fn_players_add: INSERT INTO @Table (Name) VALUES ('@Name');
nations_add: INSERT INTO @Table (Name) VALUES ('@Name');
objectives_add: INSERT INTO @Table (Type, Target, Amount, Task) VALUES ('@Type', '@Target', @Amount, @Task);
objectives_get_all_for: SELECT * FROM @Table WHERE Task = @TaskId;
reputations_exists: SELECT Id FROM @Table WHERE Player = @Player AND Town = @Town;
reputations_add: INSERT INTO @Table (Player, Town, Amount) VALUES (@Player, @Town, @Amount);
reputations_get: SELECT * FROM @Table WHERE Player = @Player AND Town = @Town;
taken_tasks_exists: SELECT Id FROM @Table WHERE Player = @PlayerId AND Task = @TaskId;
taken_tasks_add: INSERT INTO @Table (Player, Task, TakingDatetime) VALUES (@Player, @Task, '@TakingDateTime');
taken_tasks_get: SELECT * FROM @Table WHERE Player = @PlayerId AND Task = @TaskId;
taken_tasks_get_count: SELECT COUNT(*) FROM @Table WHERE Task = @TaskId;
task_progress_exists: SELECT Id FROM @Table WHERE Objective = @ObjectiveId AND TakenTask = @TakenTaskId;
task_progresses_add: INSERT INTO @Table (Objective, TakenTask, Progress) VALUES (@ObjectiveId, @TakenTaskId, @Progress);
task_progresses_get: SELECT * FROM @Table WHERE Objective = @ObjectiveId AND TakenTask = @TakenTaskId;
task_progresses_get_all_by_taken_task: SELECT * FROM @Table WHERE TakenTask = @TakenTaskId;
tasks_add: INSERT INTO @Table (Town, Creator, CreatorType, Name, Description, CompletionsLeft, MinLevel,
                            MaxLevel, MoneyReward, ExpReward, RepReward, PlacementDateTime, TerminationDateTime, TimeToComplete, Priority)
                            VALUES (@TownId, @Creator, '@CreatorType', '@TaskName', '@Description', @CompletionsLeft, @MinLevel, @MaxLevel,
                            @MoneyReward, @ExpReward, @RepReward, '@PlacementDateTime', '@TerminationDateTime', @TimeToComplete, @Priority);
tasks_get_all_for: SELECT * FROM @Table WHERE Town = @TownId;
town_houses_add: INSERT INTO @Table (Town, Owner, Location) VALUES (@TownId, @OwnerId, '@Location');
town_resources_add: INSERT INTO @Table (Town, Name, Amount) VALUES (@TownId, '@ResourceName', @Amount);
town_resources_exists: SELECT Id FROM @Table WHERE Town = @TownId AND Name = '@ResourceName';
town_resources_get: SELECT * FROM @Table WHERE Town = @TownId AND Name = '@ResourceName';
towns_add: INSERT INTO @Table (Nation, Name, Balance, StationsTax, AuctionTax, TasksTax) VALUES (@Nation, '@Name', @Balance, @StationsTax, @AuctionTax, @TasksTax);
town_workers_add: INSERT INTO @Table (Player, Town, Profession, DisplayName, Salary) VALUES (@PlayerId, @TownId, @ProfessionId, '@DisplayName', @Salary);
town_workers_is_worker: SELECT Id FROM @Table WHERE Player = @PlayerId;
town_workers_is_worker_in_town: SELECT Id FROM @Table WHERE Player = @PlayerId AND Town = @TownId;
professions_add: INSERT INTO @Table (Name, Salary, StationsTaxBonus, AuctionTaxBonus, TasksTaxBonus,
                            PermissionOpenBank, PermissionDeposit, PermissionWithdraw)
                            VALUES ('@Name', @Salary, @StationsTaxBonus, @AuctionTaxBonus, @TasksTaxBonus,
                            @PermissionOpenBank, @PermissionDeposit, @PermissionWithdraw);
workshops_add: INSERT INTO @Table (Town, Name, Location, Level, LoadedResource, Amount) VALUES (@TownId, '@Name', '@Location', @Level, '@LoadedResource', @Amount);