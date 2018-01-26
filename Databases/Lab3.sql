-- Modify the type of a column
ALTER TABLE CarDealership.dbo.Employees ALTER COLUMN Perfomance SMALLINT

-- Add a default constraint
ALTER TABLE CarDealership.dbo.Employees ADD CONSTRAINT Employees_Salary_default DEFAULT 2500 FOR Salary

-- Create a new table
CREATE TABLE CarDealership.dbo.Test (
  Column1 INT PRIMARY KEY NOT NULL,
  Column2 NVARCHAR(32),
  Column3 FLOAT DEFAULT 3.141596
)

-- Remove a table
DROP TABLE CarDealership.dbo.Test

-- Add a column
ALTER TABLE CarDealership.dbo.Test ADD ColumnX FLOAT DEFAULT 2.71828

-- Create a foreign key constraint
ALTER TABLE CarDealership.dbo.Test
ADD CONSTRAINT Test_IdentityCard_IdentityCardID_fk
FOREIGN KEY (Column1) REFERENCES IdentityCard (IdentityCardID)

-- Remove a foreign key constraint
ALTER TABLE CarDealership.dbo.Test DROP CONSTRAINT Test_IdentityCard_IdentityCardID_fk

-- PROCEDURES

CREATE PROCEDURE UpToVersion1 AS
  BEGIN
    BEGIN TRY
      CREATE TABLE CarDealership.dbo.Test (
        Column1 INT PRIMARY KEY NOT NULL,
        Column2 NVARCHAR(32),
        Column3 FLOAT
      )
      PRINT 'Created table'
    END TRY
    BEGIN CATCH
    END CATCH
  END

CREATE PROCEDURE DownToVersion0 AS
  BEGIN
    BEGIN TRY
      DROP TABLE CarDealership.dbo.Test
      PRINT 'Removed table'
    END TRY
    BEGIN CATCH
    END CATCH
  END


CREATE PROCEDURE UpToVersion2 AS
  BEGIN
    BEGIN TRY
      ALTER TABLE CarDealership.dbo.Test ALTER COLUMN Column3 DOUBLE PRECISION
      PRINT 'Modified column'
    END TRY
    BEGIN CATCH
    END CATCH
  END

CREATE PROCEDURE DownToVersion1 AS
  BEGIN
    BEGIN TRY
      ALTER TABLE CarDealership.dbo.Test ALTER COLUMN Column3 FLOAT
      PRINT 'Undo column modification'
    END TRY
    BEGIN CATCH
    END CATCH
  END


CREATE PROCEDURE UpToVersion3 AS
  BEGIN
    BEGIN TRY
      ALTER TABLE CarDealership.dbo.Test ADD CONSTRAINT Column2_default DEFAULT 'neinspirat...' FOR Column2
      PRINT 'Added default constraint'
    END TRY
    BEGIN CATCH
    END CATCH
  END

CREATE PROCEDURE DownToVersion2 AS
  BEGIN
    BEGIN TRY
      ALTER TABLE CarDealership.dbo.Test DROP CONSTRAINT Column2_default
      PRINT 'Removed default constraint'
    END TRY
    BEGIN CATCH
    END CATCH
  END


CREATE PROCEDURE UpToVersion4 AS
  BEGIN
    BEGIN TRY
      ALTER TABLE CarDealership.dbo.Test
      ADD CONSTRAINT Test_IdentityCard_IdentityCardID_fk
      FOREIGN KEY (Column1) REFERENCES IdentityCard (IdentityCardID)
      PRINT 'Added FK constraint'
    END TRY
    BEGIN CATCH
    END CATCH
  END

CREATE PROCEDURE DownToVersion3 AS
  BEGIN
    BEGIN TRY
      ALTER TABLE CarDealership.dbo.Test DROP CONSTRAINT Test_IdentityCard_IdentityCardID_fk
      PRINT 'Removed FK constraint'
    END TRY
    BEGIN CATCH
    END CATCH
  END

CREATE PROCEDURE UpToVersion5 AS
  BEGIN
    BEGIN TRY
      ALTER TABLE CarDealership.dbo.Test ADD Column10 DOUBLE PRECISION
      PRINT 'Added column'
    END TRY
    BEGIN CATCH
    END CATCH
  END


CREATE PROCEDURE DownToVersion4 AS
  BEGIN
    BEGIN TRY
      ALTER TABLE CarDealership.dbo.Test DROP COLUMN Column10
      PRINT 'Removed column'
    END TRY
    BEGIN CATCH
    END CATCH
  END


DROP PROCEDURE Main
CREATE PROCEDURE Main(
  @arg NVARCHAR(8)
  ) AS
  BEGIN
    DECLARE @currentVersion INT
    SET @currentVersion = (SELECT VersionID FROM CarDealership.dbo.Version)

    IF isnumeric(@currentVersion) != 1 BEGIN
      PRINT 'NOT A NUMBER!'
      RETURN 1
    END



    DECLARE @requestedVersion INT
    SET @requestedVersion = cast(@arg AS INT)
    IF NOT (0 <= @requestedVersion AND @requestedVersion <= 5) BEGIN
      PRINT 'Invalid version'
      RETURN 1
    END


    DECLARE @nextStep NVARCHAR(30)
    WHILE @currentVersion < @requestedVersion BEGIN
      SET @currentVersion = @currentVersion+1
      SET @nextStep = 'UpToVersion' + convert(NVARCHAR(3), @currentVersion)
      EXECUTE @nextStep
    END
    WHILE @currentVersion > @requestedVersion BEGIN
      SET @currentVersion = @currentVersion-1
      SET @nextStep = 'DownToVersion' + convert(NVARCHAR(3), @currentVersion)
      EXECUTE @nextStep
    END

    TRUNCATE TABLE Version
    INSERT INTO Version VALUES (@requestedVersion)
    PRINT 'ok... done'
  END

-- EXECUTING PROCEDURES

-- EXECUTE CreateTable
-- EXECUTE RemoveTable
-- EXECUTE ModifyTableColumn
-- EXECUTE AddDefaultConstraint
-- EXECUTE RemoveDefaultConstraint
-- EXECUTE AddForeignKeyConstraint
-- EXECUTE RemoveForeignKeyConstraint
-- EXECUTE AddColumn
-- EXECUTE RemoveColumn

EXECUTE Main 0


