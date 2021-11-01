/**
 * Author:  ozluuh
 * Created: Nov 1, 2021
 */
 -- Connect to the 'Roadlovers' database to run this snippet
USE RoadLovers
GO
-- Drop a table called 'Tbl_Vehicle' in schema 'dbo'
-- Drop the table if it already exists
IF OBJECT_ID('[dbo].[Tbl_Vehicle]', 'U') IS NOT NULL
    DROP TABLE [dbo].[Tbl_Vehicle]
GO

-- Drop a table called 'Tbl_Class' in schema 'dbo'
-- Drop the table if it already exists
IF OBJECT_ID('[dbo].[Tbl_Class]', 'U') IS NOT NULL
    DROP TABLE [dbo].[Tbl_Class]
GO

-- Drop a table called 'Tbl_Manufacturer' in schema 'dbo'
-- Drop the table if it already exists
IF OBJECT_ID('[dbo].[Tbl_Manufacturer]', 'U') IS NOT NULL
    DROP TABLE [dbo].[Tbl_Manufacturer]
GO

-- Drop the database 'RoadLovers'
-- Connect to the 'master' database to run this snippet
USE master
GO
-- Uncomment the ALTER DATABASE statement below to set the database to SINGLE_USER mode if the drop database command fails because the database is in use.
ALTER DATABASE RoadLovers SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
-- Drop the database if it exists
IF EXISTS (
    SELECT [name]
        FROM sys.databases
        WHERE [name] = N'RoadLovers'
)
    DROP DATABASE RoadLovers
GO
