/*
  Q1: display cars table but instead of manufacturerId, manufacturerName and instead of modelID, modelName
 */
SELECT Ma.ManufacturerName, Mo.ModelName, C.AskingPrice, C.AcquiringDate, C.ManufacturingDate, C.Mileage
FROM CarDealership.dbo.Cars C
INNER JOIN CarDealership.dbo.Manufacturers Ma ON C.ManufacturerID = Ma.ManufacturerID
INNER JOIN CarDealership.dbo.Models Mo ON C.ModelID =  Mo.ModelID

/*
  Q2: display how many different manufacturers there are and the count of every cars
 */
SELECT DISTINCT Ma.[ManufacturerName], COUNT(C.CarID) "Count"
FROM CarDealership.dbo.Cars C
INNER JOIN CarDealership.dbo.Manufacturers Ma on C.ManufacturerID = Ma.ManufacturerID
GROUP BY Ma.ManufacturerName

/*
  Q3: display all cars that have Heated seats
 */
SELECT C.CarID, Ma.ManufacturerName, Mo.ModelName
FROM CarDealership.dbo.Cars [C]
INNER JOIN CarDealership.dbo.Manufacturers AS Ma on C.ManufacturerID = Ma.ManufacturerID
INNER JOIN CarDealership.dbo.Models AS Mo on C.ModelID = Mo.ModelID
INNER JOIN CarDealership.dbo.FeaturesOnCars As FoC on C.CarID = FoC.CarID
INNER JOIN CarFeatures AS F on F.CarFeatureID = FoC.CarFeatureID
INNER JOIN CarFeatures AS F2 on F2.CarFeatureID = FoC.CarFeatureID
WHERE F.FeatureName = 'Heated seats'

/*
  Q4: display all cars that have Air conditioning and are cheaper than 20000
 */
SELECT C.CarID, Ma.ManufacturerName, Mo.ModelName, C.AskingPrice
FROM CarDealership.dbo.Cars [C]
INNER JOIN CarDealership.dbo.Manufacturers AS Ma on C.ManufacturerID = Ma.ManufacturerID
INNER JOIN CarDealership.dbo.Models AS Mo on C.ModelID = Mo.ModelID
INNER JOIN CarDealership.dbo.FeaturesOnCars As FoC on C.CarID = FoC.CarID
INNER JOIN CarFeatures AS F on F.CarFeatureID = FoC.CarFeatureID
WHERE F.FeatureName = 'Air conditioning' AND C.AskingPrice <= 20000

/*
Q5: display all cars that have at least 3 features and are cheaper than 30000
 */
SELECT C.CarID, Ma.ManufacturerName, Mo.ModelName, C.AskingPrice, COUNT(FoC.CarID) AS "Number of featuress"
FROM CarDealership.dbo.Cars [C]
INNER JOIN CarDealership.dbo.Manufacturers AS Ma on C.ManufacturerID = Ma.ManufacturerID
INNER JOIN CarDealership.dbo.Models AS Mo on C.ModelID = Mo.ModelID
INNER JOIN CarDealership.dbo.FeaturesOnCars As FoC on C.CarID = FoC.CarID
WHERE C.AskingPrice < 30000
GROUP BY C.CarID, Ma.ManufacturerName, Mo.ModelName, C.AskingPrice
HAVING COUNT(FoC.CarID) >= 3

/*
Q6: display all audi cars that are older than 2012 and cost less than 20000
 */
SELECT C.CarID, Ma.ManufacturerName, Mo.ModelName, C.AskingPrice, C.ManufacturingDate
FROM CarDealership.dbo.Cars [C]
INNER JOIN CarDealership.dbo.Manufacturers AS Ma on C.ManufacturerID = Ma.ManufacturerID
INNER JOIN CarDealership.dbo.Models AS Mo on C.ModelID = Mo.ModelID
WHERE Ma.ManufacturerName = 'Audi' AND C.ManufacturingDate < '2012-1-1' and C.AskingPrice <= 20000

/*
Q7: display all cars that have Heated seats and air conditioning
 */
SELECT C.CarID, Ma.ManufacturerName, Mo.ModelName, C.AskingPrice
FROM CarDealership.dbo.Cars [C]
INNER JOIN CarDealership.dbo.Manufacturers AS Ma on C.ManufacturerID = Ma.ManufacturerID
INNER JOIN CarDealership.dbo.Models AS Mo on C.ModelID = Mo.ModelID
INNER JOIN CarDealership.dbo.FeaturesOnCars As FoC1 on C.CarID = FoC1.CarID
INNER JOIN CarDealership.dbo.FeaturesOnCars AS FoC2 on C.CarID = FoC2.CarID
INNER JOIN CarDealership.dbo.CarFeatures AS CF1 ON CF1.CarFeatureID = FoC1.CarFeatureID
INNER JOIN CarDealership.dbo.CarFeatures AS CF2 ON CF2.CarFeatureID = FoC2.CarFeatureID
WHERE CF1.FeatureName = 'Heated seats' AND CF2.FeatureName = 'Air conditioning'

/*
Q8: display all cars that were manufactured between 2010 and 2012 and have less than 125k mileage
 */
SELECT C.CarID, Ma.ManufacturerName, Mo.[ModelName], C.ManufacturingDate, C.[Mileage]
FROM CarDealership.dbo.Cars [C]
INNER JOIN CarDealership.dbo.Manufacturers AS Ma on C.ManufacturerID = Ma.ManufacturerID
INNER JOIN CarDealership.dbo.Models AS Mo on C.ModelID = Mo.ModelID
WHERE C.ManufacturingDate >= '2010-1-1' AND C.ManufacturingDate <= '2012-12-31' AND C.Mileage <= 125000

/*
Q9: display all the cars sorted by the number of features, in case of equality, sorted by price
 */
SELECT C.CarID, Ma.ManufacturerName, Mo.ModelName, C.AskingPrice, COUNT(FoC.CarID) AS "Number of featuress"
FROM CarDealership.dbo.Cars [C]
INNER JOIN CarDealership.dbo.Manufacturers AS Ma on C.ManufacturerID = Ma.ManufacturerID
INNER JOIN CarDealership.dbo.Models AS Mo on C.ModelID = Mo.ModelID
INNER JOIN CarDealership.dbo.FeaturesOnCars As FoC on C.CarID = FoC.[CarID]
GROUP BY C.CarID, Ma.ManufacturerName, Mo.ModelName, C.AskingPrice
ORDER BY COUNT(FoC.CarID) DESC, C.AskingPrice ASC

/*
Q10: displays all the cars sorted by Manufacturing Date, in case of equality, by price
 */
SELECT C.CarID, Ma.ManufacturerName, Mo.ModelName, C.AskingPrice, C.[ManufacturingDate]
FROM CarDealership.dbo.Cars [C]
INNER JOIN CarDealership.dbo.Manufacturers AS Ma on C.ManufacturerID = Ma.ManufacturerID
INNER JOIN CarDealership.dbo.Models AS Mo on C.ModelID = Mo.ModelID
ORDER BY C.ManufacturingDate DESC, C.[AskingPrice]