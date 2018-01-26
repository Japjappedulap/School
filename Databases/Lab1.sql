CREATE TABLE CarDealership.dbo.Manufacturers(
  ManufacturerID INT PRIMARY KEY NOT NULL IDENTITY,
  ManufacturerName NVARCHAR(32)
)

CREATE TABLE CarDealership.dbo.Models(
  ModelID INT PRIMARY KEY NOT NULL IDENTITY,
  ManufacturerID INT FOREIGN KEY REFERENCES CarDealership.dbo.Manufacturers(ManufacturerID)
)

CREATE TABLE CarDealership.dbo.CarFeatures(
  CarFeatureID INT PRIMARY KEY NOT NULL IDENTITY ,
  FeatureName NVARCHAR(64) /*clima bizonica and stuff*/
)

CREATE TABLE CarDealership.dbo.Cars(
  CarID INT PRIMARY KEY NOT NULL IDENTITY,
  ManufacturerID INT FOREIGN KEY REFERENCES CarDealership.dbo.Manufacturers(ManufacturerID),
  ModelID INT FOREIGN KEY REFERENCES CarDealership.dbo.Models(ModelID),
  AskingPrice INT,
  Mileage INT,
  AcquiringDate DATE,
  ManufacturingDate DATE
)

CREATE TABLE CarDealership.dbo.FeaturesOnCars(
  CarID INT FOREIGN KEY REFERENCES CarDealership.dbo.Cars(CarID),
  CarFeatureID INT FOREIGN KEY REFERENCES CarDealership.dbo.CarFeatures(CarFeatureID)
)

CREATE TABLE CarDealership.dbo.Address(
  AddressID INT PRIMARY KEY NOT NULL IDENTITY ,
  AddressLine1 NVARCHAR(64),
  AddressLine2 NVARCHAR(64),
  AddressLine3 NVARCHAR(64),
  CityName NVARCHAR(32),
  County NVARCHAR(32),
  Country NVARCHAR(32),
  PostalCode NCHAR(6)
)

CREATE TABLE CarDealership.dbo.IdentityCard(
  IdentityCardID INT PRIMARY KEY NOT NULL IDENTITY,
  FirstName NVARCHAR(32),
  LastName NVARCHAR(32),
  DateOfBirth DATE,
  AddressID INT FOREIGN KEY REFERENCES CarDealership.dbo.Address(AddressID)
)

CREATE TABLE CarDealership.dbo.Employees(
  EmployeeID INT PRIMARY KEY NOT NULL IDENTITY,
  IdentityCardID INT FOREIGN KEY REFERENCES CarDealership.dbo.IdentityCard(IdentityCardID),
  Salary INT,
  Perfomance INT
)

CREATE TABLE CarDealership.dbo.Customers(
  CustomerID INT PRIMARY KEY NOT NULL IDENTITY,
  IdentityCardID INT FOREIGN KEY REFERENCES CarDealership.dbo.IdentityCard(IdentityCardID),
)

CREATE TABLE CarDealership.dbo.Orders(
  OrderID INT PRIMARY KEY NOT NULL IDENTITY,
  CustomerID INT FOREIGN KEY REFERENCES CarDealership.dbo.Customers(CustomerID),
  CarID INT FOREIGN KEY REFERENCES CarDealership.dbo.Cars(CarID),
  SellingPrice INT,
  DateOfSold DATE
)