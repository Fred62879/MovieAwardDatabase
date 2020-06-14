CREATE TABLE award (
	aID integer not null PRIMARY KEY,
	startdate varchar2(10),
	enddate varchar2(10),
	name varchar2(20) not null,
);

INSERT INTO award VALUES (19283746, "06/14/2020", "08/14/2020", "The Award");