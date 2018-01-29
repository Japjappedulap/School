CREATE TABLE testDB.dbo.Companies (
  CompanyID INT PRIMARY KEY IDENTITY,
  Name NVARCHAR(64),
  Description NVARCHAR(64) DEFAULT NULL,
  Website NVARCHAR(64) DEFAULT NULL,
  NumberOfEmployees INT DEFAULT 15
);

CREATE TABLE testDB.dbo.ConferenceTypes (
  ConferenceTypeID INT PRIMARY KEY IDENTITY,
  Name NVARCHAR(64) DEFAULT NULL,
  Popularity INT DEFAULT 5
);

CREATE TABLE testDB.dbo.Speakers (
  SpeakerID INT PRIMARY KEY IDENTITY,
  Name NVARCHAR(64),
  EmailAddress NVARCHAR(64) DEFAULT NULL,
  BirthDate DATE,
  CompanyID INT,
  CONSTRAINT SpeakerToCompanyFK FOREIGN KEY (CompanyID) REFERENCES testDB.dbo.Companies (CompanyID)
);

CREATE TABLE testDB.dbo.Conferences (
  ConferenceID INT PRIMARY KEY IDENTITY,
  ConferenceTypeID INT,
  Name NVARCHAR(64),
  Location NVARCHAR(64) DEFAULT NULL,
  StartDate DATE DEFAULT '2010-1-1',
  EndDate DATE DEFAULT '2011-1-1',
  CONSTRAINT ConferenceToConferenceTypeFK FOREIGN KEY (ConferenceTypeID) REFERENCES testDB.dbo.ConferenceTypes (ConferenceTypeID)
);

CREATE TABLE testDB.dbo.SpeakersToConferences (
  SpeakerID INT,
  ConferenceID INT,
  Length INT DEFAULT 60,
  CONSTRAINT SpeakersFK FOREIGN KEY (SpeakerID) REFERENCES testDB.dbo.Speakers (SpeakerID),
  CONSTRAINT ConferenceFK FOREIGN KEY (ConferenceID) REFERENCES testDB.dbo.Companies (CompanyID),
  CONSTRAINT SpeakerID_ConferenceID_PK PRIMARY KEY (SpeakerID, ConferenceID)
);

CREATE PROCEDURE Insert_Speaker_conference
  @SpeakerID INT,
  @ConferenceID INT,
  @Length INT
AS BEGIN
  DECLARE @Speaker_if_found INT = NULL;
  DECLARE @Conference_if_found INT = NULL;
  SELECT TOP 1 @Speaker_if_found = SpeakerID FROM testDB.dbo.Speakers WHERE SpeakerID = @SpeakerID;
  SELECT TOP 1 @Conference_if_found = ConferenceID FROM testDB.dbo.Conferences WHERE ConferenceID = @ConferenceID;

  IF ((@Speaker_if_found IS NOT NULL) AND (@Conference_if_found IS NOT NULL )) BEGIN
    -- if we're here, we must insert or update
    DECLARE @check INT = NULL
    SELECT TOP 1 @check = SpeakerID FROM testDB.dbo.SpeakersToConferences
        WHERE SpeakerID = @SpeakerID AND ConferenceID = @ConferenceID;
    IF @check IS NULL BEGIN
      -- we insert
      INSERT INTO testDB.dbo.SpeakersToConferences(SpeakerID, ConferenceID, Length) VALUES (@SpeakerID, @ConferenceID, @Length)
      PRINT 'inserted'
    END
    ELSE BEGIN
      -- we update
      PRINT 'updated'
      UPDATE testDB.dbo.SpeakersToConferences SET
        Length = @Length
      WHERE SpeakerID = @SpeakerID AND ConferenceID = @ConferenceID
    END
  END
  ELSE BEGIN
    PRINT 'invalid speaker/conference'
  END
END;

CREATE VIEW ShowConferencesWithAtLeast3Speakers AS
  SELECT SC.ConferenceID, C.Name, C.Location  FROM testDB.dbo.SpeakersToConferences SC INNER JOIN testDB.dbo.Conferences C ON SC.ConferenceID = C.ConferenceID
    GROUP BY SC.ConferenceID, C.Name, C.Location HAVING COUNT(SC.ConferenceID) >= 3

CREATE FUNCTION ShowAbsentSpeakers() RETURNS TABLE AS
  RETURN
      SELECT C.name'Company Name', S.Name'Speaker Name', S.SpeakerID, S.EmailAddress'Email Address'
        FROM testDB.dbo.Speakers S INNER JOIN testDB.dbo.Companies C ON S.CompanyID = C.CompanyID
        WHERE S.SpeakerID NOT IN (SELECT DISTINCT SC.SpeakerID FROM testDB.dbo.SpeakersToConferences SC);




SELECT S2.SpeakerID ,S2.Name, C.Name, C.ConferenceID, SC.Length FROM SpeakersToConferences SC
  INNER JOIN Speakers S2 ON SC.SpeakerID = S2.SpeakerID
  INNER JOIN Conferences C on SC.ConferenceID = C.ConferenceID

EXECUTE Insert_Speaker_conference 3, 1, 10
EXECUTE Insert_Speaker_conference 2, 1, 10
EXECUTE Insert_Speaker_conference 4, 2, 10
SELECT * FROM ShowAbsentSpeakers();
SELECT * FROM ShowConferencesWithAtLeast3Speakers






