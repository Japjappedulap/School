CREATE VIEW Cars_view AS
  SELECT C.CarID, C.ManufacturerID, Ma.ManufacturerName, C.ModelID, Mo.ModelName, C.AskingPrice, C.Mileage, C.AcquiringDate, C.ManufacturingDate
    FROM CarDealership.dbo.Cars C
    INNER JOIN CarDealership.dbo.Manufacturers Ma ON C.ManufacturerID = Ma.ManufacturerID
    INNER JOIN CarDealership.dbo.Models Mo ON C.ModelID =  Mo.ModelID

CREATE VIEW Manufacturers_view AS
  SELECT * FROM CarDealership.dbo.Manufacturers

CREATE VIEW Models_view AS
  SELECT Mo.ModelID, Ma.ManufacturerName ,Mo.ModelName
    FROM CarDealership.dbo.Models Mo
    INNER JOIN CarDealership.dbo.Manufacturers Ma ON Mo.ManufacturerID = Ma.ManufacturerID

CREATE VIEW Cars_Features_view AS SELECT C.CarID, Ma.ManufacturerName, Mo.ModelName,CF.FeatureName, CF.CarFeatureID
  FROM CarDealership.dbo.FeaturesOnCars FoC
  INNER JOIN CarDealership.dbo.CarFeatures CF ON CF.CarFeatureID = FoC.CarFeatureID
  INNER JOIN CarDealership.dbo.Cars C ON C.CarID = FoC.CarID
  INNER JOIN CarDealership.dbo.Manufacturers Ma ON C.ManufacturerID = Ma.ManufacturerID
  INNER JOIN CarDealership.dbo.Models Mo ON Mo.ModelID = C.ModelID

CREATE VIEW Features_view AS
  SELECT * FROM CarDealership.dbo.CarFeatures

CREATE FUNCTION Get_CarID(
  @ManufacturerName NVARCHAR(64),
  @ModelName NVARCHAR(64),
  @AskingPrice INT,
  @Mileage INT,
  @AcquiringDate DATE,
  @ManufacturingDate DATE
) RETURNS INT BEGIN
  DECLARE @CarID INT = NULL
  DECLARE @ManufacturerID INT = NULL
  DECLARE @ModelID INT = NULL
  SELECT TOP 1 @ManufacturerID = ManufacturerID FROM CarDealership.dbo.Manufacturers WHERE ManufacturerName = @ManufacturerName
  SELECT TOP 1 @ModelID = ModelID FROM CarDealership.dbo.Models WHERE ModelName = @ModelName
  SELECT TOP 1 @CarID = CarID FROM CarDealership.dbo.Cars WHERE
    AskingPrice = @AskingPrice AND
    Mileage = @Mileage AND
    AcquiringDate = @AcquiringDate AND
    ManufacturingDate = @ManufacturingDate AND
    ManufacturerID = @ManufacturerID AND
    ModelID = @ModelID
  return @CarID
END

CREATE PROCEDURE Cars_insert
  @Manufacturer_name NVARCHAR(64),
  @Model_name NVARCHAR(64),
  @AskingPrice INT,
  @Mileage INT,
  @AcquiringDate DATE,
  @ManufacturingDate Date
AS BEGIN
  DECLARE @ManufacturerID INT
  DECLARE @ManufacturerID_2 INT
  DECLARE @ModelID INT
  SELECT TOP 1 @ManufacturerID = ManufacturerID FROM CarDealership.dbo.Manufacturers WHERE ManufacturerName = @Manufacturer_name
  PRINT @ManufacturerID
  SELECT TOP 1 @ModelID = ModelID FROM CarDealership.dbo.Models WHERE ModelName = @Model_name
  SELECT TOP 2 @ManufacturerID_2 = ManufacturerID FROM CarDealership.dbo.Models WHERE ModelName = @Model_name
  PRINT @ModelID
  PRINT @ManufacturerID_2


  IF @ManufacturerID IS NULL BEGIN
    PRINT 'manufacturer non existent'
    INSERT INTO CarDealership.dbo.Manufacturers(ManufacturerName) VALUES (@Manufacturer_name)
    SELECT TOP 1 @ManufacturerID = ManufacturerID FROM CarDealership.dbo.Manufacturers WHERE ManufacturerName = @Manufacturer_name
    INSERT INTO CarDealership.dbo.Models(ManufacturerID, ModelName) VALUES (@ManufacturerID, @Model_name)
    SELECT TOP 1 @ModelID = ModelID FROM CarDealership.dbo.Models WHERE ModelName = @Model_name
  END
  IF @ModelID IS NULL BEGIN
    PRINT 'model non existent'
    SELECT TOP 1 @ManufacturerID = ManufacturerID FROM CarDealership.dbo.Manufacturers WHERE ManufacturerName = @Manufacturer_name
    INSERT INTO CarDealership.dbo.Models(ManufacturerID, ModelName) VALUES (@ManufacturerID, @Model_name)
    SELECT TOP 1 @ModelID = ModelID FROM CarDealership.dbo.Models WHERE ModelName = @Model_name
  END

  INSERT INTO CarDealership.dbo.Cars(ManufacturerID, ModelID, AskingPrice, Mileage, AcquiringDate, ManufacturingDate)
  VALUES (@ManufacturerID, @ModelID, @AskingPrice, @Mileage, @AcquiringDate, @ManufacturingDate)
END

DROP PROCEDURE Cars_delete
CREATE PROCEDURE Cars_delete
  @Manufacturer_name NVARCHAR(64),
  @Model_name NVARCHAR(64),
  @AskingPrice INT,
  @Mileage INT,
  @AcquiringDate DATE,
  @ManufacturingDate Date
AS BEGIN
  DECLARE @CarID INT
  DECLARE @ManufacturerID INT
  DECLARE @ManufacturerID_2 INT
  DECLARE @ModelID INT
  SELECT TOP 1 @ManufacturerID = ManufacturerID FROM CarDealership.dbo.Manufacturers WHERE ManufacturerName = @Manufacturer_name
  PRINT @ManufacturerID
  SELECT TOP 1 @ModelID = ModelID FROM CarDealership.dbo.Models WHERE ModelName = @Model_name
  SELECT TOP 2 @ManufacturerID_2 = ManufacturerID FROM CarDealership.dbo.Models WHERE ModelName = @Model_name
  PRINT @ModelID
  PRINT @ManufacturerID_2


  SELECT TOP 1 @CarID = CarID FROM CarDealership.dbo.Cars WHERE
    @ManufacturerID = ManufacturerID AND
    @ModelID = ModelID AND
    @AskingPrice = AskingPrice AND
    @Mileage = Mileage AND
    @AcquiringDate = AcquiringDate AND
    @ManufacturingDate = ManufacturingDate
  DELETE FROM CarDealership.dbo.FeaturesOnCars WHERE @CarID = CarDealership.dbo.FeaturesOnCars.CarID
  DELETE FROM CarDealership.dbo.Cars WHERE CarDealership.dbo.Cars.CarID = @CarID;
END

CREATE PROCEDURE Cars_update
  @Manufacturer_name NVARCHAR(64),
  @Update_Manufacturer_name NVARCHAR(64),
  @Model_name NVARCHAR(64),
  @Update_Model_name NVARCHAR(64),
  @AskingPrice INT,
  @Update_AskingPrice INT,
  @Mileage INT,
  @Update_Mileage INT,
  @AcquiringDate DATE,
  @Update_AcquiringDate DATE,
  @ManufacturingDate DATE,
  @Update_ManufacturingDate DATE
AS BEGIN
  DECLARE @CarID INT
  DECLARE @ManufacturerID INT
  DECLARE @Update_ManufacturerID INT
  DECLARE @ModelID INT
  DECLARE @Update_ModelID INT
  SELECT TOP 1 @ManufacturerID = ManufacturerID FROM CarDealership.dbo.Manufacturers WHERE ManufacturerName = @Manufacturer_name
  PRINT @ManufacturerID
  SELECT TOP 1 @ModelID = ModelID FROM CarDealership.dbo.Models WHERE ModelName = @Model_name
  PRINT @ModelID

  SELECT TOP 1 @CarID = CarID FROM CarDealership.dbo.Cars WHERE
  @ManufacturerID = ManufacturerID AND
  @ModelID = ModelID AND
  @AskingPrice = AskingPrice AND
  @Mileage = Mileage AND
  @AcquiringDate = AcquiringDate AND
  @ManufacturingDate = ManufacturingDate
--   if car doesn't exist, exit
  IF @CarID IS NULL BEGIN
    RETURN
  END

  SELECT TOP 1 @Update_ManufacturerID = ManufacturerID FROM CarDealership.dbo.Manufacturers WHERE ManufacturerName = @Update_Manufacturer_name
  SELECT TOP 1 @Update_ModelID = ModelID FROM CarDealership.dbo.Models WHERE ModelName = @Update_Model_name
  PRINT @ModelID
--   check if the updating is done right
  IF @Update_ManufacturerID IS NULL BEGIN
    PRINT 'manufacturer non existent'
    INSERT INTO CarDealership.dbo.Manufacturers(ManufacturerName) VALUES (@Update_Manufacturer_name)
    SELECT TOP 1 @Update_ManufacturerID = ManufacturerID FROM CarDealership.dbo.Manufacturers WHERE ManufacturerName = @Update_Manufacturer_name
    SELECT TOP 1 @Update_ModelID = ModelID FROM CarDealership.dbo.Models WHERE ModelName = @Update_Model_name
  END
  IF @Update_ModelID IS NULL BEGIN
    PRINT 'model non existent'
    SELECT TOP 1 @Update_ManufacturerID = ManufacturerID FROM CarDealership.dbo.Manufacturers WHERE ManufacturerName = @Update_Manufacturer_name
    INSERT INTO CarDealership.dbo.Models(ManufacturerID, ModelName) VALUES (@ManufacturerID, @Update_Model_name)
    SELECT TOP 1 @Update_ModelID = ModelID FROM CarDealership.dbo.Models WHERE ModelName = @Update_Model_name
  END

  UPDATE CarDealership.dbo.Cars SET
    ManufacturerID = @Update_ManufacturerID,
    ModelID = @Update_ModelID,
    AskingPrice = @Update_AskingPrice,
    Mileage = @Update_Mileage,
    AcquiringDate = @Update_AcquiringDate,
    ManufacturingDate = @Update_ManufacturingDate
  WHERE @CarID = CarID
END

CREATE PROCEDURE Cars_select
AS BEGIN
  SELECT * FROM Cars_view
END

CREATE FUNCTION Get_ManufacturerID(
  @ManufacturerName NVARCHAR(64)
)
RETURNS INT BEGIN
  DECLARE @ManufacturerID INT = NULL
  SELECT TOP 1 @ManufacturerID = ManufacturerID FROM CarDealership.dbo.Manufacturers WHERE ManufacturerName = @ManufacturerName
  RETURN @ManufacturerID
END

CREATE PROCEDURE Manufacturers_insert
  @ManufacturerName NVARCHAR(64)
AS BEGIN
  DECLARE @ManufacturerID INT
  SELECT TOP 1 @ManufacturerID = ManufacturerID FROM CarDealership.dbo.Manufacturers WHERE ManufacturerName = @ManufacturerName
  IF @ManufacturerID IS NULL BEGIN
    INSERT INTO CarDealership.dbo.Manufacturers(ManufacturerName) VALUES (@ManufacturerName)

  END
END

CREATE PROCEDURE Manufacturers_delete
  @ManufacturerName NVARCHAR(64)
AS BEGIN
  DECLARE @ManufacturerID INT
  SELECT TOP 1 @ManufacturerID = ManufacturerID FROm CarDealership.dbo.Manufacturers WHERE ManufacturerName = @ManufacturerName
  IF @ManufacturerID IS NOT NULL BEGIN
    DELETE FROM CarDealership.dbo.Manufacturers WHERE CarDealership.dbo.Manufacturers.ManufacturerID = @ManufacturerID
  END
END

CREATE PROCEDURE Manufacturers_update
  @ManufacturerName NVARCHAR(64),
  @Update_ManufacturerName NVARCHAR(64)
AS BEGIN
  DECLARE @ManufacturerID INT
  SELECT TOP 1 @ManufacturerID = ManufacturerID FROM CarDealership.dbo.Manufacturers WHERE ManufacturerName = @ManufacturerName
  IF @ManufacturerID IS NULL BEGIN
    RETURN
  END
  UPDATE CarDealership.dbo.Manufacturers SET
    ManufacturerName = @Update_ManufacturerName
  WHERE @ManufacturerID = ManufacturerID
END

CREATE PROCEDURE Manufacturers_select AS BEGIN
  SELECT * FROM Manufacturers_view
END

CREATE FUNCTION [Get_ModelID](
  @ManufacturerName NVARCHAR(64),
  @ModelName NVARCHAR(64)
)
RETURNS INT BEGIN
  DECLARE @ManufacturerID INT
  SELECT TOP 1 @ManufacturerID = ManufacturerID FROM CarDealership.dbo.Manufacturers WHERE ManufacturerName = @ManufacturerName
  IF @ManufacturerID IS NULL BEGIN
    RETURN NULL
  END
  DECLARE @ModelID INT
  SELECT TOP 1 @ModelID = ModelID FROM CarDealership.dbo.Models WHERE @ManufacturerID = ManufacturerID AND @ModelName = ModelName
  RETURN @ModelID
END

CREATE PROCEDURE Models_insert
  @ManufacturerName NVARCHAR(64),
  @ModelName NVARCHAR(64)
AS BEGIN
  DECLARE @ManufacturerID INT
  SELECT TOP 1 @ManufacturerID = ManufacturerID FROM CarDealership.dbo.Manufacturers WHERE ManufacturerName = @ManufacturerName
  IF @ManufacturerID IS NULL BEGIN
    RETURN
  END
  DECLARE @ModelID INT
  SELECT TOP 1 @ModelID = ModelID FROM CarDealership.dbo.Models WHERE @ManufacturerID = ManufacturerID AND @ModelName = ModelName
  IF @ModelID IS NOT NULL BEGIN
    RETURN
  END
  INSERT INTO CarDealership.dbo.Models(ManufacturerID, ModelName) VALUES (@ManufacturerID, @ModelName)
END

CREATE PROCEDURE Models_delete
  @ManufacturerName NVARCHAR(64),
  @ModelName NVARCHAR(64)
AS BEGIN
  DECLARE @ManufacturerID INT
  SELECT TOP 1 @ManufacturerID = ManufacturerID FROM CarDealership.dbo.Manufacturers WHERE ManufacturerName = @ManufacturerName
  IF @ManufacturerID IS NULL BEGIN
    RETURN
  END
  DECLARE @ModelID INT
  SELECT TOP 1 @ModelID = ModelID FROM CarDealership.dbo.Models WHERE @ManufacturerID = ManufacturerID AND @ModelName = ModelName
  IF @ModelID IS NULL BEGIN
    RETURN
  END
  DELETE FROM CarDealership.dbo.Models WHERE CarDealership.dbo.Models.ModelID = @ModelID
END

CREATE PROCEDURE Models_update
  @ManufacturerName NVARCHAR(64),
  @Update_ManufacturerName NVARCHAR(64),
  @ModelName NVARCHAR(64),
  @Update_ModelName NVARCHAR(64)
AS BEGIN
  DECLARE @ManufacturerID INT
  SELECT TOP 1 @ManufacturerID = ManufacturerID FROM CarDealership.dbo.Manufacturers WHERE ManufacturerName = @ManufacturerName
  IF @ManufacturerID IS NULL BEGIN
    RETURN
  END
  DECLARE @ModelID INT
  SELECT TOP 1 @ModelID = ModelID FROM CarDealership.dbo.Models WHERE @ManufacturerID = ManufacturerID AND @ModelName = ModelName
  IF @ModelID IS NULL BEGIN
    RETURN
  END

  DECLARE @Update_ManufacturerID INT
  SELECT TOP 1 @Update_ManufacturerID = ManufacturerID FROM CarDealership.dbo.Manufacturers WHERE ManufacturerName = @Update_ManufacturerName
  IF @Update_ManufacturerID IS NULL BEGIN
    INSERT INTO CarDealership.dbo.Manufacturers(ManufacturerName) VALUES (@Update_ManufacturerName)
    SET @Update_ManufacturerID = NULL
    SELECT TOP 1 @Update_ManufacturerID = ManufacturerID FROM CarDealership.dbo.Manufacturers WHERE ManufacturerName = @Update_ManufacturerName
  END
  UPDATE CarDealership.dbo.Models SET
    ManufacturerID = @Update_ManufacturerID,
    ModelName = @Update_ModelName
    WHERE ModelID = @ModelID
END

CREATE PROCEDURE Models_select
AS BEGIN
  SELECT * FROM Models_view
END

CREATE FUNCTION [Get_CarFeatureID] (@FeatureName NVARCHAR(64)) RETURNS INT BEGIN
  DECLARE @CarFeatureID INT = NULL
  SELECT TOP 1 @CarFeatureID = CarFeatureID FROM CarDealership.dbo.CarFeatures WHERE FeatureName = @FeatureName
  RETURN @CarFeatureID
END

CREATE PROCEDURE Cars_Features_insert
  @ManufacturerName NVARCHAR(64),
  @ModelName NVARCHAR(64),
  @AskingPrice INT,
  @Mileage INT,
  @AcquiringDate DATE,
  @ManufacturingDate DATE,
  @FeatureName NVARCHAR(64)
AS BEGIN
  DECLARE @CarID INT = NULL
  DECLARE @ManufacturerID INT = NULL
  DECLARE @ModelID INT = NULL
  SELECT TOP 1 @ManufacturerID = ManufacturerID FROM CarDealership.dbo.Manufacturers WHERE ManufacturerName = @ManufacturerName
  SELECT TOP 1 @ModelID = ModelID FROM CarDealership.dbo.Models WHERE ModelName = @ModelName
  SELECT TOP 1 @CarID = CarID FROM CarDealership.dbo.Cars WHERE
    AskingPrice = @AskingPrice AND
    Mileage = @Mileage AND
    AcquiringDate = @AcquiringDate AND
    ManufacturingDate = @ManufacturingDate AND
    ManufacturerID = @ManufacturerID AND
    ModelID = @ModelID
  DECLARE @CarFeatureID INT = NULL
  SELECT TOP 1 @CarFeatureID = CarFeatureID FROM CarDealership.dbo.CarFeatures WHERE FeatureName = @FeatureName
  IF @CarID IS NULL BEGIN
    PRINT 'Cannot find car!'
    RETURN
  END
  PRINT @CarID
  IF @CarFeatureID IS NULL BEGIN
    PRINT 'Cannot find feature!'
    RETURN
  END
  PRINT @CarFeatureID
  BEGIN TRY
    INSERT INTO CarDealership.dbo.FeaturesOnCars(CarID, CarFeatureID) VALUES (@CarID, @CarFeatureID)
  END TRY
  BEGIN CATCH
    PRINT 'Feature already added!'
  END CATCH
END

CREATE PROCEDURE Cars_Features_delete
  @ManufacturerName NVARCHAR(64),
  @ModelName NVARCHAR(64),
  @AskingPrice INT,
  @Mileage INT,
  @AcquiringDate DATE,
  @ManufacturingDate DATE,
  @FeatureName NVARCHAR(64)
AS BEGIN
  DECLARE @CarID INT = NULL
  DECLARE @ManufacturerID INT = NULL
  DECLARE @ModelID INT = NULL
  SELECT TOP 1 @ManufacturerID = ManufacturerID FROM CarDealership.dbo.Manufacturers WHERE ManufacturerName = @ManufacturerName
  SELECT TOP 1 @ModelID = ModelID FROM CarDealership.dbo.Models WHERE ModelName = @ModelName
  SELECT TOP 1 @CarID = CarID FROM CarDealership.dbo.Cars WHERE
    AskingPrice = @AskingPrice AND
    Mileage = @Mileage AND
    AcquiringDate = @AcquiringDate AND
    ManufacturingDate = @ManufacturingDate AND
    ManufacturerID = @ManufacturerID AND
    ModelID = @ModelID
  DECLARE @CarFeatureID INT = NULL
  SELECT TOP 1 @CarFeatureID = CarFeatureID FROM CarDealership.dbo.CarFeatures WHERE FeatureName = @FeatureName
  IF @CarID IS NULL BEGIN
    PRINT 'null'
  END
  PRINT @CarID
  IF @CarFeatureID IS NULL BEGIN
    PRINT 'null'
  END
  BEGIN TRY
    DELETE FROM CarDealership.dbo.FeaturesOnCars WHERE
      CarDealership.dbo.FeaturesOnCars.CarFeatureID = @CarFeatureID AND
      CarDealership.dbo.FeaturesOnCars.CarID = @CarID
  END TRY
  BEGIN CATCH
    PRINT 'Feature not found!'
  END CATCH

END

DROP PROCEDURE Cars_Features_update
CREATE PROCEDURE Cars_Features_update
  @ManufacturerName NVARCHAR(64),
  @ModelName NVARCHAR(64),
  @AskingPrice INT,
  @Mileage INT,
  @AcquiringDate DATE,
  @ManufacturingDate DATE,
  @FeatureName NVARCHAR(64),
  @UpdateFeatureName NVARCHAR(64)
AS BEGIN
  DECLARE @CarID INT = NULL
  DECLARE @ManufacturerID INT = NULL
  DECLARE @ModelID INT = NULL
  SELECT TOP 1 @ManufacturerID = ManufacturerID FROM CarDealership.dbo.Manufacturers WHERE ManufacturerName = @ManufacturerName
  SELECT TOP 1 @ModelID = ModelID FROM CarDealership.dbo.Models WHERE ModelName = @ModelName
  SELECT TOP 1 @CarID = CarID FROM CarDealership.dbo.Cars WHERE
    AskingPrice = @AskingPrice AND
    Mileage = @Mileage AND
    AcquiringDate = @AcquiringDate AND
    ManufacturingDate = @ManufacturingDate AND
    ManufacturerID = @ManufacturerID AND
    ModelID = @ModelID
  DECLARE @CarFeatureID INT = NULL
  SELECT TOP 1 @CarFeatureID = CarFeatureID FROM CarDealership.dbo.CarFeatures WHERE FeatureName = @FeatureName
  IF @CarID IS NULL BEGIN
    PRINT 'null'
  END
  PRINT @CarID
  IF @CarFeatureID IS NULL BEGIN
    PRINT 'null'
  END
  BEGIN TRY
    DELETE FROM CarDealership.dbo.FeaturesOnCars WHERE
      CarDealership.dbo.FeaturesOnCars.CarFeatureID = @CarFeatureID AND
      CarDealership.dbo.FeaturesOnCars.CarID = @CarID
    DECLARE @UpdateFeatureID INT = NULL
    SELECT TOP 1 @UpdateFeatureID = CarFeatureID FROM CarDealership.dbo.CarFeatures WHERE FeatureName = @UpdateFeatureName
    IF @UpdateFeatureID IS NULL BEGIN
      INSERT INTO CarDealership.dbo.CarFeatures(FeatureName) VALUES (@UpdateFeatureName)
    END
    SELECT TOP 1 @UpdateFeatureID = CarFeatureID FROM CarDealership.dbo.CarFeatures WHERE FeatureName = @UpdateFeatureName
    INSERT INTO CarDealership.dbo.FeaturesOnCars(CarID, CarFeatureID) VALUES (@CarID, @UpdateFeatureID)
  END TRY
  BEGIN CATCH
    PRINT 'Feature not found!'
  END CATCH

END

CREATE PROCEDURE Cars_Features_select AS BEGIN
  SELECT * FROM Cars_Features_view
END

CREATE PROCEDURE Features_insert
  @FeatureName NVARCHAR(64)
AS BEGIN
  DECLARE @CarFeatureID INT = NULL
  SELECT TOP 1 @CarFeatureID = CarFeatureID FROM CarDealership.dbo.CarFeatures WHERE @FeatureName = FeatureName
  IF @CarFeatureID IS NULL BEGIN
    INSERT INTO CarDealership.dbo.CarFeatures(FeatureName) VALUES (@FeatureName)
  END
END

CREATE PROCEDURE Features_delete
  @FeatureName NVARCHAR(64)
AS BEGIN
  DECLARE @CarFeatureID INT = NULL
  SELECT TOP 1 @CarFeatureID = CarFeatureID FROM CarDealership.dbo.CarFeatures WHERE @FeatureName = FeatureName
  IF @CarFeatureID IS NOT NULL BEGIN
    DELETE FROM CarDealership.dbo.CarFeatures WHERE @FeatureName = CarDealership.dbo.CarFeatures.FeatureName
  END
END

CREATE PROCEDURE Features_update
  @FeatureName NVARCHAR(64),
  @UpdateFeatureName NVARCHAR(64)
AS BEGIN
  DECLARE @CarFeatureID INT = NULL
  SELECT TOP 1 @CarFeatureID = CarFeatureID FROM CarDealership.dbo.CarFeatures WHERE @FeatureName = FeatureName
  DECLARE @UpdateCarFeatureID INT = NULL
  SELECT TOP 1 @UpdateCarFeatureID = CarFeatureID FROM CarDealership.dbo.CarFeatures WHERE @UpdateFeatureName = FeatureName
  IF @CarFeatureID IS NULL BEGIN
    PRINT 'Feature not found!'
    RETURN
  END
  IF @UpdateCarFeatureID IS NULL BEGIN
    UPDATE CarDealership.dbo.CarFeatures SET FeatureName = @UpdateFeatureName
      WHERE CarFeatureID = @CarFeatureID
    RETURN
  END
  PRINT 'Feature already existent'
END

CREATE PROCEDURE Features_select
AS BEGIN
  SELECT * FROM Features_view
END


EXECUTE Cars_select
EXECUTE Cars_insert 'Dacia', 'Logan', 3000, 3000, '2010-2-2', '2009-2-2'
EXECUTE Cars_update 'Dacia', 'Dacia', 'Logan', 'Sandero', 3000, 4000, 3000, 4000, '2010-2-2', '2010-1-1', '2009-2-2', '2009-1-1'
EXECUTE Cars_delete 'Dacia', 'Sandero', 4000, 4000, '2010-1-1', '2009-1-1'
EXECUTE Cars_delete 'Dacia', 'Logan', 4000, 4000, '2010-1-1', '2009-1-1'
EXECUTE Cars_delete 'Dacia', 'Logan', 3000, 3000, '2010-2-2', '2009-2-2'

EXECUTE Manufacturers_select
EXECUTE Manufacturers_insert 'Volvo'
EXECUTE Manufacturers_update 'Audi', 'Ferrari'
EXECUTE Manufacturers_update 'Ferrari', 'Audi'
EXECUTE Manufacturers_delete 'Renault'
EXECUTE Manufacturers_delete 'Volvo'

EXECUTE Models_select
EXECUTE Models_insert 'Audi', 'A3'
EXECUTE Models_update 'Audi', 'Aston Martin', 'A3', 'DBS'
EXECUTE Models_insert 'Aston Martin', 'DBS'
EXECUTE Models_delete 'Aston Martin', 'DBS'

EXECUTE Cars_Features_select
EXECUTE Cars_Features_insert 'Dacia', 'Logan', 3000, 3000, '2010-2-2', '2009-2-2', 'Heated seats'
EXECUTE Cars_Features_insert 'Dacia', 'Logan', 3000, 3000, '2010-2-2', '2009-2-2', 'Autopilot'
EXECUTE Cars_Features_update 'Dacia', 'Logan', 3000, 3000, '2010-2-2', '2009-2-2', 'Autopilot', 'ABS'
EXECUTE Cars_Features_delete 'Dacia', 'Logan', 3000, 3000, '2010-2-2', '2009-2-2', 'Heated seats'
EXECUTE Cars_Features_delete 'Dacia', 'Logan', 3000, 3000, '2010-2-2', '2009-2-2', 'ABS'

EXECUTE Features_select
EXECUTE Features_insert 'ABS'
EXECUTE Features_update 'ABS', 'ESP'
EXECUTE Features_delete 'ABS'
EXECUTE Features_delete 'ESP'

SELECT [dbo].[Get_CarID]('Audi', 'A4', 15000, 120000, '2015-1-1', '2012-1-1')
SELECT [dbo].[Get_ManufacturerID]('Aston Martin')
SELECT [dbo].[Get_ModelID]('Audi', 'A4')

SELECT * FROM Cars_Features_view


