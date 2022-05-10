general_exists_id: SELECT Id FROM @Table WHERE ID = @ID;
general_exists_name: SELECT Id FROM @Table WHERE Name = '@Name';
general_get_id: SELECT * FROM @Table WHERE ID = @ID;
general_get_name: SELECT * FROM @Table WHERE Name = '@Name';
general_update: UPDATE @Table SET @Var = '@Value' WHERE ID = @ID;
general_remove: DELETE FROM @Table WHERE ID = @ID;
general_get_all: SELECT * FROM @Table;
general_get_max_id: SELECT Id FROM @Table ORDER BY Id DESC LIMIT 1;
general_get_names: SELECT Name FROM @Table;
barracks_add: INSERT INTO @Table (Town, Name, Location, Level) VALUES(@TownId, '@Name', '@Location', @Level);
churches_add: INSERT INTO @Table (Town, Name, Location, Level) VALUES(@TownId, '@Name', '@Location', @Level);
defend_towers_add: INSERT INTO @Table (Town, Name, Location, Level, LoadedResource, Amount) VALUES (@TownId, '@Name', '@Location', @Level, '@LoadedResource', @Amount);
farms_add: INSERT INTO @Table (Town, Name, Location, Level, LoadedResource, Amount) VALUES (@TownId, '@Name', '@Location', @Level, '@LoadedResource', @Amount);
fn_players_add: INSERT INTO @Table (Name) VALUES ('@Name');
nations_add: INSERT INTO @Table (Name) VALUES ('@Name');
objectives_add: INSERT INTO @Table (Type, Target, Amount, Task) VALUES ('@Type', '@Target', @Amount, @Task);
objectives_add_all_for: SELECT * FROM @Table WHERE Task = @TaskID;
reputations_exists: SELECT Id FROM @Table WHERE Player = @Player AND Town = @Town;
reputations_add: INSERT INTO @Table (Player, Town, Amount) VALUES (@Player, @Town, @Amount);
reputations_get: SELECT * FROM @Table WHERE Player = @Player AND Town = @Town;
taken_tasks_exists: SELECT Id FROM @Table WHERE Player = @PlayerID AND Task = @TaskID;
taken_tasks_add: INSERT INTO @Table (Player, Task) VALUES (@Player, @Task);
taken_tasks_get: SELECT * FROM @Table WHERE Player = @Player AND Task = @Task;
task_progresses_add: INSERT INTO @Table (Objective, Progress) VALUES (@Objective, @Progress);
task_progresses_get: SELECT * FROM @Table WHERE Objective = @Objective AND TakenTask = @TakenTask;
task_progresses_get_all_by_taken_task: SELECT * FROM @Table WHERE TakenTask = @TakenTask;
tasks_add: INSERT INTO @Table (Town, Player, Name, Description, TakeAmount, MinLevel,
                            MaxLevel, MoneyReward, ExpReward, RepReward, PlacementDateTime, TimeToComplete, Priority)
                            VALUES (@TownId, @Player, '@TaskName', '@Description', @TakeAmount, @MinLevel, @MaxLevel,
                            @MoneyReward, @ExpReward, @RepReward, '@PlacementDateTime', @TimeToComplete, @Priority);
town_houses_add: INSERT INTO @Table (Town, Owner, Location) VALUES (@Town, @Owner, '@Location');
town_resources_add: INSERT INTO @Table (Town, Name, Amount) VALUES (@Town, '@Name', @Amount);
towns_add: INSERT INTO @Table (Nation, Name, Balance, StationsTax, AuctionTax, TasksTax) VALUES (@Nation, '@Name', @Balance, @StationsTax, @AuctionTax, @TasksTax);
town_workers_add: INSERT INTO @Table (Player, Town, WorkerType, DisplayName, Salary) VALUES (@PlayerID, @TownID, @WorkerTypeID, '@DisplayName', @Salary);
town_workers_is_worker: SELECT Id FROM @Table WHERE Player = @PlayerID;
town_workers_get_type: SELECT @WorkerTypesTable.Id FROM @Table JOIN (@WorkerTypesTable) ON (@Table.WorkerType = @WorkerTypesTable.Id) WHERE @Table.Player = @PlayerID;
worker_types_add: INSERT INTO @Table (Name, DisplayName) VALUES ('@Name', '@DisplayName');
workshops_add: INSERT INTO @Table (Town, Name, Location, Level, LoadedResource, Amount) VALUES (@TownId, '@Name', '@Location', @Level, '@LoadedResource', @Amount);