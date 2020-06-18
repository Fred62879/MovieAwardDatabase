
CREATE TABLE award (
    aID integer PRIMARY KEY,
    startdate varchar2(10),
    enddate varchar2(10),
    name varchar2(20) not null)

CREATE TABLE moviestaff (
    id integer PRIMARY KEY,
    staffname varchar2(20),
    dob varchar2(10),
    role varchar2(20))

CREATE TABLE nominee (
    nom_id integer PRIMARY KEY,
    vote_count integer,
    id integer,
    award_id integer,
    foreign key (id) references moviestaff(id),
    foreign key (award_id) references award(aID))

            INSERT INTO award VALUES (1, '08/12/2019', '08/14/2020', 'The Award'))
            INSERT INTO award VALUES (12, '08/11/2019', '08/15/2020', 'The Award 2'))
            INSERT INTO moviestaff VALUES (19285746, 'Daniel', '08/14/2020', 'Actor'))
            INSERT INTO moviestaff VALUES (12345678, 'Ivan', '08/24/2020', 'Actor'))
            INSERT INTO moviestaff VALUES (15678349, 'David', '08/04/2010', 'Director'))
            INSERT INTO moviestaff VALUES (01928347, 'Felicia', '08/12/2020', 'Director'))
            INSERT INTO moviestaff VALUES (57392047, 'Andy', '08/11/2020', 'Actor'))
            INSERT INTO moviestaff VALUES (02937586, 'Daria', '08/31/2020', 'Actor'))
            INSERT INTO nominee VALUES (0, 0, 19285746, 1))
            INSERT INTO nominee VALUES (1, 6, 12345678, 1))
            INSERT INTO nominee VALUES (2, 0, 01928347, 12))
            INSERT INTO nominee VALUES (3, 6, 12345678, 12))