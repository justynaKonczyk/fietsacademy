# insert into docenten(voornaam, familienaam, wedde, emailadres, geslacht)
# VALUES ('testM', 'testM', 1000, 'testM@fietsacademy.be', 'MAN');
#
# insert into docenten (voornaam, familienaam, wedde, emailadres, geslacht)
# VALUES('testV', 'testV', 1000, 'testF@fietsacademy.be', 'VROUW');
#
# insert into docentenbijnamen(docentid, bijnaam)
# values ((select id from docenten where voornaam='testM'), 'test');

insert into docenten(voornaam,familienaam,wedde,emailadres,geslacht,campusid)
values ('testM','testM',1000,'testM@fietsacademy.be','MAN',
        (select id from campussen where naam='test'));

insert into docenten(voornaam,familienaam,wedde,emailadres,geslacht,campusid)
values ('testV','testV',1000,'testV@fietsacademy.be','VROUW',
        (select id from campussen where naam='test'));

insert into docentenbijnamen(docentid,bijnaam)
values ((select id from docenten where voornaam='testM'),'test');