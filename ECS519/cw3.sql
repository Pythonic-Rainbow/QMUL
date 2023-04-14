-- MySQL 8.0

/*
 I am in group BX but my group mates didn't work at all.
 I worked on this script entirely by myself.
 */
create database EstateAgentECS21842;
USE EstateAgentECS21842;

-- Create tables
create table agency
(
    Id          int auto_increment primary key,
    Name        varchar(255) not null,
    PhoneNumber varchar(10)  not null, -- Phone number should be string because we don't need arithmetic operations
                                       -- UK phone numbers can have up to 10 digits.
    constraint agency_pk
        unique (PhoneNumber)  -- Every agent should have different numbers
);

create table office
(
    Id          int auto_increment primary key,
    AgentId     int         not null,
    Area        varchar(2)  not null, -- https://ideal-postcodes.co.uk/guides/uk-postcode-format
    District    varchar(2)  not null,
    Sector      char        not null,
    Unit        char(2)     not null,
    PhoneNumber varchar(10) not null,
    constraint office_pk
        unique (PhoneNumber),  -- Every office should have different numbers
    constraint FkAgentId
        foreign key (AgentId) references agency (Id)
);

create table property
(
    Id          int auto_increment primary key,
    Area        varchar(2)   not null,
    District    varchar(2)   not null,
    Sector      char         not null,
    Unit        char(2)      not null,
    City        varchar(255) not null,
    Price       float        not null,
    Type        bit          not null,  -- enum: 0=house 1=flat
    NumBedrooms tinyint      not null,  -- Surely there are no properties with >255 bedrooms
    Description text         not null
);

create table customer
(
    Id          int auto_increment primary key,
    Name        varchar(255) not null,
    PhoneNumber varchar(10)  not null,
    constraint customer_pk
        unique (PhoneNumber)  -- Every customer should have different numbers
);

create table forsale
(
    PropertyId int not null,
    OfficeId   int not null,
    primary key (PropertyId, OfficeId),  -- Each office can only list the same property once
    constraint ForSale_office_null_fk
        foreign key (OfficeId) references office (Id),
    constraint ForSale_property_null_fk
        foreign key (PropertyId) references property (Id)
);

create table forrent
(
    PropertyId int not null,
    OfficeId   int not null,
    primary key (PropertyId, OfficeId),  -- Each office can only list the same property once
    constraint ForRent_office_null_fk
        foreign key (OfficeId) references office (Id),
    constraint ForRent_property_null_fk
        foreign key (PropertyId) references property (Id)
);

create table sold
(
    PropertyId int not null primary key,  -- A property is owned by only one customer at any point
    OfficeId   int not null,
    CustomerId int not null,
    constraint Sold_customer_null_fk
        foreign key (CustomerId) references customer (Id),
    constraint Sold_office_null_fk
        foreign key (OfficeId) references office (Id),
    constraint Sold_property_null_fk
        foreign key (PropertyId) references property (Id)
);

-- Insert records
INSERT INTO agency (Name, PhoneNumber) VALUES ('John Wick', '72145689');
INSERT INTO agency (Name, PhoneNumber) VALUES ('Howard Wong', '75848642');
INSERT INTO agency (Name, PhoneNumber) VALUES ('Isaac Cheung', '84512460');
INSERT INTO agency (Name, PhoneNumber) VALUES ('Jackie Chan', '90541478');
INSERT INTO agency (Name, PhoneNumber) VALUES ('Bruce Lee', '90541479');
INSERT INTO agency (Name, PhoneNumber) VALUES ('Nakiri Ayame', '15487521');
INSERT INTO agency (Name, PhoneNumber) VALUES ('Momosuzu Nene', '854120789');
INSERT INTO agency (Name, PhoneNumber) VALUES ('Carrie Lam', '8074521904');
INSERT INTO agency (Name, PhoneNumber) VALUES ('Jin Ping Xi', '996198964');
INSERT INTO agency (Name, PhoneNumber) VALUES ('Zemin Jiang', '20221130');

INSERT INTO customer (Name, PhoneNumber) VALUES ('John Doe', '23451034');
INSERT INTO customer (Name, PhoneNumber) VALUES ('Harry Potter', '784512659');
INSERT INTO customer (Name, PhoneNumber) VALUES ('Emma Watson', '765198034');
INSERT INTO customer (Name, PhoneNumber) VALUES ('Albus Dumbledore', '9764851230');
INSERT INTO customer (Name, PhoneNumber) VALUES ('Takoyaki Towa', '765410895');
INSERT INTO customer (Name, PhoneNumber) VALUES ('Tony Stark', '56218097');
INSERT INTO customer (Name, PhoneNumber) VALUES ('Howard Stark', '1459785130');
INSERT INTO customer (Name, PhoneNumber) VALUES ('Pepper Jones', '984512607');
INSERT INTO customer (Name, PhoneNumber) VALUES ('Anakin Skywalker', '4859780412');
INSERT INTO customer (Name, PhoneNumber) VALUES ('Darth Vader', '555375375');

INSERT INTO office (AgentId, Area, District, Sector, Unit, PhoneNumber) VALUES (1, 'E', '1', '4', 'NS', '78456948');
INSERT INTO office (AgentId, Area, District, Sector, Unit, PhoneNumber) VALUES (2, 'CM', '3', '3', 'HS', '8451207896');
INSERT INTO office (AgentId, Area, District, Sector, Unit, PhoneNumber) VALUES (2, 'AU', '4A', '8', 'Q$', '2077706028');
INSERT INTO office (AgentId, Area, District, Sector, Unit, PhoneNumber) VALUES (4, 'NW', '6', '4', 'HJ', '2076244664');
INSERT INTO office (AgentId, Area, District, Sector, Unit, PhoneNumber) VALUES (6, 'NW', '9', '0', 'AS', '0208200483');
INSERT INTO office (AgentId, Area, District, Sector, Unit, PhoneNumber) VALUES (5, 'E', '11', '1', 'NR', '0208539552');
INSERT INTO office (AgentId, Area, District, Sector, Unit, PhoneNumber) VALUES (3, 'DA', '14', '6', 'LT', '0208302267');
INSERT INTO office (AgentId, Area, District, Sector, Unit, PhoneNumber) VALUES (8, 'SM', '5', '1', 'AA', '0208296200');
INSERT INTO office (AgentId, Area, District, Sector, Unit, PhoneNumber) VALUES (9, 'KT', '6', '7', 'LD', '0208397039');
INSERT INTO office (AgentId, Area, District, Sector, Unit, PhoneNumber) VALUES (1, 'GU', '1', '1', 'UP', '0148344332');

INSERT INTO property (Area, District, Sector, Unit, City, Price, Type, NumBedrooms, Description) VALUES ('LE', '19', '1', 'HY', 'Leicester', 50, true, 3, 'Nice');
INSERT INTO property (Area, District, Sector, Unit, City, Price, Type, NumBedrooms, Description) VALUES ('SU', '35', '6', 'AB', 'Manchester', 49, true, 7, 'Wow');
INSERT INTO property (Area, District, Sector, Unit, City, Price, Type, NumBedrooms, Description) VALUES ('MI', '50', '7', 'SE', 'London', 896, false, 50, 'Wowww');
INSERT INTO property (Area, District, Sector, Unit, City, Price, Type, NumBedrooms, Description) VALUES ('J', '20', '1', 'JI', 'London', 780, true, 3, 'Damn');
INSERT INTO property (Area, District, Sector, Unit, City, Price, Type, NumBedrooms, Description) VALUES ('AE', '15', '8', 'SE', 'Manchester', 784, true, 4, 'Very good');
INSERT INTO property (Area, District, Sector, Unit, City, Price, Type, NumBedrooms, Description) VALUES ('E', '4', '4', 'AG', 'Edinburgh', 486, false, 1, 'Meh');
INSERT INTO property (Area, District, Sector, Unit, City, Price, Type, NumBedrooms, Description) VALUES ('LE', '42', '1', 'HW', 'Leeds', 4800, true, 5, 'Damnnnn');
INSERT INTO property (Area, District, Sector, Unit, City, Price, Type, NumBedrooms, Description) VALUES ('M', '2', '6', 'NA', 'Liverpool', 56, false, 2, 'Noice');
INSERT INTO property (Area, District, Sector, Unit, City, Price, Type, NumBedrooms, Description) VALUES ('CM', '30', '5', 'AX', 'Liverpool', 480, false, 9, 'Good');
INSERT INTO property (Area, District, Sector, Unit, City, Price, Type, NumBedrooms, Description) VALUES ('SU', '48', '5', 'IO', 'London', 489489, true, 24, 'WOW');
-- The 10 rows below are sold properties
INSERT INTO property (Area, District, Sector, Unit, City, Price, Type, NumBedrooms, Description) VALUES ('LE', '19', '2', 'HY', 'Leicester', 50, true, 3, 'Nice');
INSERT INTO property (Area, District, Sector, Unit, City, Price, Type, NumBedrooms, Description) VALUES ('SU', '35', '3', 'AB', 'Manchester', 49, true, 7, 'Wow');
INSERT INTO property (Area, District, Sector, Unit, City, Price, Type, NumBedrooms, Description) VALUES ('MI', '50', '4', 'SE', 'London', 896, false, 50, 'Wowww');
INSERT INTO property (Area, District, Sector, Unit, City, Price, Type, NumBedrooms, Description) VALUES ('J', '20', '2', 'JI', 'London', 780, true, 3, 'Damn');
INSERT INTO property (Area, District, Sector, Unit, City, Price, Type, NumBedrooms, Description) VALUES ('AE', '15', '9', 'SE', 'Manchester', 784, true, 4, 'Very good');
INSERT INTO property (Area, District, Sector, Unit, City, Price, Type, NumBedrooms, Description) VALUES ('E', '4', '5', 'AG', 'Edinburgh', 486, false, 1, 'Meh');
INSERT INTO property (Area, District, Sector, Unit, City, Price, Type, NumBedrooms, Description) VALUES ('LE', '42', '6', 'HW', 'Leeds', 4800, true, 5, 'Damnnnn');
INSERT INTO property (Area, District, Sector, Unit, City, Price, Type, NumBedrooms, Description) VALUES ('M', '2', '7', 'NA', 'Liverpool', 56, false, 2, 'Noice');
INSERT INTO property (Area, District, Sector, Unit, City, Price, Type, NumBedrooms, Description) VALUES ('CM', '30', '2', 'AX', 'Liverpool', 480, false, 9, 'Good');
INSERT INTO property (Area, District, Sector, Unit, City, Price, Type, NumBedrooms, Description) VALUES ('SU', '48', '2', 'IO', 'London', 489489, true, 24, 'WOW');

INSERT INTO forrent VALUES (1, 1);
INSERT INTO forrent VALUES (1, 5);
INSERT INTO forrent VALUES (3, 2);
INSERT INTO forrent VALUES (3, 1);
INSERT INTO forrent VALUES (7, 4);
INSERT INTO forrent VALUES (8, 2);
INSERT INTO forrent VALUES (8, 8);
INSERT INTO forrent VALUES (10, 9);
INSERT INTO forrent VALUES (10, 1);

INSERT INTO forsale VALUES (2, 1);
INSERT INTO forsale VALUES (2, 5);
INSERT INTO forsale VALUES (2, 2);
INSERT INTO forsale VALUES (2, 7);
INSERT INTO forsale VALUES (2, 4);
INSERT INTO forsale VALUES (4, 2);
INSERT INTO forsale VALUES (4, 8);
INSERT INTO forsale VALUES (4, 9);
INSERT INTO forsale VALUES (4, 1);

INSERT INTO sold VALUES (11, 1, 1);
INSERT INTO sold VALUES (12, 5, 3);
INSERT INTO sold VALUES (13, 1, 3);
INSERT INTO sold VALUES (14, 5, 4);
INSERT INTO sold VALUES (15, 1, 5);
INSERT INTO sold VALUES (16, 5, 2);
INSERT INTO sold VALUES (17, 1, 8);
INSERT INTO sold VALUES (18, 5, 10);
INSERT INTO sold VALUES (19, 1, 10);
INSERT INTO sold VALUES (20, 5, 8);

-- Basic queries
-- 1. Get ID and price of properties bought by Customer 3
SELECT Id, Price
FROM property
JOIN sold ON property.Id = sold.PropertyId AND sold.CustomerId=3;

-- 2. Get customer names that bought properties from Office 5
SELECT Name
from customer
JOIN sold ON customer.Id = sold.CustomerId AND sold.OfficeId=5;

-- Medium queries
-- 1. Get sum of price of properties on sale in each area
SELECT SUM(Price), Area from property p
LEFT JOIN forsale f on p.Id = f.PropertyId
GROUP BY Area;

-- 2. Get agent name and amount of office in all offices
SELECT Name, COUNT(a.Id) from agency a
LEFT JOIN office o on a.Id = o.AgentId
GROUP BY a.Id;

-- Advanced queries
-- 1. Get agents who have sold properties worth >$10000 in total
select sua, AgentId from (
    select sum(su) as sua, o.AgentId from (
        select sum(price) as su, s.OfficeId from property p
        right join sold s on p.Id = s.PropertyId
        group by s.OfficeId) as t
    join office o on o.Id = OfficeId
    group by AgentId) as t2
where sua > 10000;

-- 2. Get properties for rent that are lower than $500 and is in a city that starts with L
select distinct PropertyId from forrent f
where f.propertyId in (
    select Id from property
    where Price < 500 AND City LIKE 'L%');