DROP VIEW View_1
DROP VIEW View_2
DROP VIEW View_3

CREATE VIEW View_1 AS SELECT * FROM CarDealership.dbo.Table_1
CREATE VIEW View_2 AS SELECT * FROM CarDealership.dbo.Table_2
CREATE VIEW View_3 AS SELECT * FROM CarDealership.dbo.Table_3



DROP PROCEDURE Populate_Table_1
CREATE PROCEDURE Populate_Table_1
AS BEGIN
  DECLARE @NoOFRows int
	DECLARE @n int = 0

	SELECT TOP 1 @NoOFRows = NoOFRows FROM CarDealership.dbo.TestTables WHERE TestID = 1

	WHILE @n < @NoOFRows
	BEGIN
		INSERT INTO CarDealership.dbo.Table_1 DEFAULT VALUES
		SET @n=@n+1
	END
END
EXECUTE Populate_Table_1

DROP PROCEDURE Clear_Table_1
CREATE PROCEDURE Clear_Table_1
AS BEGIN
    DELETE FROM CarDealership.dbo.Table_1
  END
EXECUTE Clear_Table_1


DROP PROCEDURE Populate_Table_2
CREATE PROCEDURE Populate_Table_2
AS BEGIN
  DECLARE @NoOFRows int
	DECLARE @n int = 0

	SELECT TOP 1 @NoOFRows = NoOFRows FROM CarDealership.dbo.TestTables WHERE TestID = 3

	WHILE @n < @NoOFRows
	BEGIN
    DECLARE @fk INT
    SELECT @fk = ABS(Checksum(NewID()) % 100)
		INSERT INTO CarDealership.dbo.Table_2(FKID) VALUES (@fk)
		SET @n=@n+1
	END
  END
EXECUTE Populate_Table_2

DROP PROCEDURE Clear_Table_2
CREATE PROCEDURE Clear_Table_2
AS BEGIN
    DELETE FROM CarDealership.dbo.Table_2
  END
EXECUTE Clear_Table_2



DROP PROCEDURE Populate_Table_3
CREATE PROCEDURE Populate_Table_3
AS BEGIN
  DECLARE @NoOFRows FLOAT

  SELECT TOP 1 @NoOFRows = (NoOFRows + 0.0) FROM CarDealership.dbo.TestTables WHERE TestID = 5;

  SELECT @NoOFRows = sqrt(@NoOFRows) ;

  DECLARE @i INT = 0
  WHILE @i < @NoOFRows
  BEGIN
    DECLARE @j INT = 0
    WHILE @j < @NoOFRows BEGIN
      INSERT INTO CarDealership.dbo.Table_3(pk1, pk2) VALUES (@i, @j)
      SET @j=@j+1
    END
    SET @i=@i+1
  END
END
EXECUTE Populate_Table_3

DROP PROCEDURE Clear_Table_3
CREATE PROCEDURE Clear_Table_3
AS BEGIN
  DELETE FROM CarDealership.dbo.Table_3
END
EXECUTE Clear_Table_3

DROP PROCEDURE initialize
CREATE PROCEDURE initialize AS BEGIN

  IF OBJECT_ID(N'CarDealership.dbo.Table_1', N'U') IS NOT NULL BEGIN
    DROP TABLE CarDealership.dbo.Table_1
  END

  IF OBJECT_ID(N'CarDealership.dbo.Table_2', N'U') IS NOT NULL BEGIN
    DROP TABLE CarDealership.dbo.Table_2
  END

  IF OBJECT_ID(N'CarDealership.dbo.Table_3', N'U') IS NOT NULL BEGIN
    DROP TABLE CarDealership.dbo.Table_3
  END

  IF OBJECT_ID(N'CarDealership.dbo.Table_aux', N'U') IS NOT NULL BEGIN
    DROP TABLE CarDealership.dbo.Table_aux
  END

  IF OBJECT_ID(N'CarDealership.dbo.Table_1', N'U') IS NULL
  BEGIN
    CREATE TABLE CarDealership.dbo.Table_1 (
      ID INT PRIMARY KEY IDENTITY
--       Name NVARCHAR(32)
    )
  END

  IF OBJECT_ID(N'CarDealership.dbo.Table_2', N'U') IS NULL
  BEGIN
    IF OBJECT_ID(N'CarDealership.dbo.Table_aux', N'U') IS NULL
    BEGIN
      CREATE TABLE CarDealership.dbo.Table_aux (
        ID INT PRIMARY KEY IDENTITY (0, 1)
      )
      DECLARE @n INT = 0
      WHILE @n < 100
      BEGIN
        INSERT INTO CarDealership.dbo.Table_aux DEFAULT VALUES
        SET @n=@n+1
      END
    END

    CREATE TABLE CarDealership.dbo.Table_2 (
      ID INT PRIMARY KEY IDENTITY (1, 1),
      FKID INT,
      FOREIGN KEY (FKID) REFERENCES CarDealership.dbo.Table_aux(ID)
    )
  END

  IF OBJECT_ID(N'CarDealership.dbo.Table_3', N'U') IS NULL
  BEGIN
    CREATE TABLE CarDealership.dbo.Table_3 (
      pk1 INT,
      pk2 INT,
      CONSTRAINT table_name_pk1_pk2_pk PRIMARY KEY (pk1, pk2)
    )
  END

  DELETE FROM CarDealership.dbo.TestTables
  DELETE FROM CarDealership.dbo.TestViews
  DELETE FROM CarDealership.dbo.Tables
  DELETE FROM CarDealership.dbo.Tests
  DELETE FROM CarDealership.dbo.Views

  DBCC CHECKIDENT ('CarDealership.dbo.Tables', RESEED, 0);
  INSERT INTO CarDealership.dbo.Tables(Name) VALUES ('Table_1')
  INSERT INTO CarDealership.dbo.Tables(Name) VALUES ('Table_2')
  INSERT INTO CarDealership.dbo.Tables(Name) VALUES ('Table_3')

  DBCC CHECKIDENT ('CarDealership.dbo.Tests', RESEED, 0);
  INSERT INTO CarDealership.dbo.Tests(Name) VALUES ('Populate_Table_1')
  INSERT INTO CarDealership.dbo.Tests(Name) VALUES ('Clear_Table_1')
  INSERT INTO CarDealership.dbo.Tests(Name) VALUES ('Populate_Table_2')
  INSERT INTO CarDealership.dbo.Tests(Name) VALUES ('Clear_Table_2')
  INSERT INTO CarDealership.dbo.Tests(Name) VALUES ('Populate_Table_3')
  INSERT INTO CarDealership.dbo.Tests(Name) VALUES ('Clear_Table_3')

  DBCC CHECKIDENT ('CarDealership.dbo.Views', RESEED, 0);
  INSERT INTO CarDealership.dbo.Views(Name) VALUES ('View_1')
  INSERT INTO CarDealership.dbo.Views(Name) VALUES ('View_2')
  INSERT INTO CarDealership.dbo.Views(Name) VALUES ('View_3')


  INSERT INTO CarDealership.dbo.TestViews(TestID, ViewID) VALUES (1, 1);
  INSERT INTO CarDealership.dbo.TestViews(TestID, ViewID) VALUES (3, 2);
  INSERT INTO CarDealership.dbo.TestViews(TestID, ViewID) VALUES (5, 3);

  INSERT INTO CarDealership.dbo.TestTables(TestID, TableID, NoOfRows, Position) VALUES (1, 1, 1000, 1)
  INSERT INTO CarDealership.dbo.TestTables(TestID, TableID, NoOfRows, Position) VALUES (2, 1, 1000, 2)
  INSERT INTO CarDealership.dbo.TestTables(TestID, TableID, NoOfRows, Position) VALUES (3, 2, 1000, 3)
  INSERT INTO CarDealership.dbo.TestTables(TestID, TableID, NoOfRows, Position) VALUES (4, 2, 1000, 4)
  INSERT INTO CarDealership.dbo.TestTables(TestID, TableID, NoOfRows, Position) VALUES (5, 3, 1000, 5)
  INSERT INTO CarDealership.dbo.TestTables(TestID, TableID, NoOfRows, Position) VALUES (6, 3, 1000, 6)

END

DROP PROCEDURE Testing
CREATE PROCEDURE Testing AS BEGIN
  DECLARE @id1 INT
  DECLARE @StartTime DATETIME
  DECLARE @EndTime DATETIME

  -- first insert
  SET @StartTime = GETDATE()
  EXECUTE Populate_Table_1
  SET @EndTime = GETDATE()
  INSERT INTO CarDealership.dbo.TestRuns(Description, StartAt, EndAt)
              VALUES ('inserting in simple table', @StartTime, @EndTime)
  SET @id1 = @@IDENTITY
  INSERT INTO CarDealership.dbo.TestRunTables(TestRunID, TableID, StartAt, EndAt)
              VALUES (@id1, 1, @StartTime, @EndTime)

  -- second insert
  SET @StartTime = GETDATE()
  EXECUTE Populate_Table_2
  SET @EndTime = GETDATE()
  INSERT INTO CarDealership.dbo.TestRuns(Description, StartAt, EndAt)
              VALUES ('inserting in table with foreign key', @StartTime, @EndTime)
  SET @id1 = @@IDENTITY
  INSERT INTO CarDealership.dbo.TestRunTables(TestRunID, TableID, StartAt, EndAt)
              VALUES (@id1, 2, @StartTime, @EndTime)

  -- third insert
  SET @StartTime = GETDATE()
  EXECUTE Populate_Table_3
  SET @EndTime = GETDATE()
  INSERT INTO CarDealership.dbo.TestRuns(Description, StartAt, EndAt)
              VALUES ('inserting in table with PK as pair', @StartTime, @EndTime)
  SET @id1 = @@IDENTITY
  INSERT INTO CarDealership.dbo.TestRunTables(TestRunID, TableID, StartAt, EndAt)
  VALUES (@id1, 3, @StartTime, @EndTime)

  -- view 1
  SET @StartTime = GETDATE()
  SELECT * FROM View_1
  SET @EndTime = GETDATE()
  PRINT @id1
  INSERT INTO CarDealership.dbo.TestRunViews(TestRunID, ViewID, StartAt, EndAt) VALUES
    (@id1, 1, @StartTime, @EndTime)

  -- view 2
  SET @StartTime = GETDATE()
  SELECT * FROM View_2
  SET @EndTime = GETDATE()
  PRINT @id1
  INSERT INTO CarDealership.dbo.TestRunViews(TestRunID, ViewID, StartAt, EndAt) VALUES
    (@id1, 2, @StartTime, @EndTime)

  -- view 3
  SET @StartTime = GETDATE()
  SELECT * FROM View_3
  SET @EndTime = GETDATE()
  PRINT @id1
  INSERT INTO CarDealership.dbo.TestRunViews(TestRunID, ViewID, StartAt, EndAt) VALUES
    (@id1, 3, @StartTime, @EndTime)

  -- first delete
  SET @StartTime = GETDATE()
  EXECUTE Clear_Table_1
  SET @EndTime = GETDATE()
  INSERT INTO CarDealership.dbo.TestRuns(Description, StartAt, EndAt)
  VALUES ('clearing simple table', @StartTime, @EndTime)
  SET @id1 = @@IDENTITY
  PRINT @id1
  INSERT INTO CarDealership.dbo.TestRunTables(TestRunID, TableID, StartAt, EndAt)
  VALUES (@id1, 1, @StartTime, @EndTime)

  -- second delete
  SET @StartTime = GETDATE()
  EXECUTE Clear_Table_2
  SET @EndTime = GETDATE()
  INSERT INTO CarDealership.dbo.TestRuns(Description, StartAt, EndAt)
  VALUES ('clearing table with FK', @StartTime, @EndTime)
  SET @id1 = @@IDENTITY
  PRINT @id1
  INSERT INTO CarDealership.dbo.TestRunTables(TestRunID, TableID, StartAt, EndAt)
  VALUES (@id1, 2, @StartTime, @EndTime)
  -- third delete
  SET @StartTime = GETDATE()
  EXECUTE Clear_Table_3
  SET @EndTime = GETDATE()
  INSERT INTO CarDealership.dbo.TestRuns(Description, StartAt, EndAt)
  VALUES ('clearing table with PK as pair', @StartTime, @EndTime)
  SET @id1 = @@IDENTITY
  PRINT @id1
  INSERT INTO CarDealership.dbo.TestRunTables(TestRunID, TableID, StartAt, EndAt)
  VALUES (@id1, 3, @StartTime, @EndTime)


  UPDATE CarDealership.dbo.TestRuns SET EndAt = @EndTime WHERE TestRunID = @id1
END


DELETE FROM CarDealership.dbo.Table_1

EXECUTE [initialize]
EXECUTE Testing

SELECT * FROM CarDealership.dbo.TestRunTables
SELECT * FROM CarDealership.dbo.TestRunViews
SELECT * FROM CarDealership.dbo.TestRuns