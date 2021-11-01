/**
 * Author:  ozluuh
 * Created: Nov 1, 2021
 */
-- Create a new database called 'RoadLovers'
-- Connect to the 'master' database to run this snippet
USE master
GO
-- Create the new database if it does not exist already
IF NOT EXISTS (
    SELECT [name]
        FROM sys.databases
        WHERE [name] = N'RoadLovers'
)
    CREATE DATABASE RoadLovers
GO

 -- Connect to the 'Roadlovers' database to run this snippet
USE RoadLovers
GO

-- Create a new table called '[Tbl_Vehicle]' in schema '[dbo]'
-- Drop the table if it already exists
IF OBJECT_ID('[dbo].[Tbl_Vehicle]', 'U') IS NOT NULL
    DROP TABLE [dbo].[Tbl_Vehicle]
GO
-- Create the table in the specified schema
CREATE TABLE [dbo].[Tbl_Vehicle]
(
    [Id] INT NOT NULL IDENTITY(1,1) PRIMARY KEY, -- Primary Key column
    [_Year] INT NOT NULL,
    [Model] VARCHAR(100) NOT NULL,
    [Price] NUMERIC(15,2) NOT NULL,
    [Created_At] DATETIME2 DEFAULT CURRENT_TIMESTAMP
);
GO

-- Create a new table called '[Tbl_Class]' in schema '[dbo]'
-- Drop the table if it already exists
IF OBJECT_ID('[dbo].[Tbl_Class]', 'U') IS NOT NULL
    DROP TABLE [dbo].[Tbl_Class]
GO
-- Create the table in the specified schema
CREATE TABLE [dbo].[Tbl_Class]
(
    [Id] INT NOT NULL IDENTITY(1,1) PRIMARY KEY, -- Primary Key column
    [Name] VARCHAR(50) NOT NULL
);
GO

-- Create a new table called '[Tbl_Manufacturer]' in schema '[dbo]'
-- Drop the table if it already exists
IF OBJECT_ID('[dbo].[Tbl_Manufacturer]', 'U') IS NOT NULL
    DROP TABLE [dbo].[Tbl_Manufacturer]
GO
-- Create the table in the specified schema
CREATE TABLE [dbo].[Tbl_Manufacturer]
(
    [Id] INT NOT NULL IDENTITY(1,1) PRIMARY KEY, -- Primary Key column
    [Name] VARCHAR(50) NOT NULL
);
GO

-- Add a new column '[Class_Id]' to table '[Tbl_Vehicle]' in schema '[dbo]'
ALTER TABLE [dbo].[Tbl_Vehicle]
    ADD [Class_Id] INT NOT NULL
GO

-- Add a new column '[Manufacturer_Id]' to table '[Tbl_Vehicle]' in schema '[dbo]'
ALTER TABLE [dbo].[Tbl_Vehicle]
    ADD [Manufacturer_Id] INT NOT NULL
GO

-- Add foreing key 'FK_VehicleClass' to table '[Tbl_Vehicle]' in schema '[dbo]'
ALTER TABLE [dbo].[Tbl_Vehicle]
    ADD CONSTRAINT FK_VehicleClass
        FOREIGN KEY (Class_Id) REFERENCES Tbl_Class(Id);
GO

-- Add foreing key 'FK_VehicleManufacturer' to table '[Tbl_Vehicle]' in schema '[dbo]'
ALTER TABLE [dbo].[Tbl_Vehicle]
    ADD CONSTRAINT FK_VehicleManufacturer
        FOREIGN KEY (Manufacturer_Id) REFERENCES Tbl_Manufacturer(Id);
GO
