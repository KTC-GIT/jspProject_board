create table realTimeSearch(
	idx int not null auto_increment primary key,
	name varchar(20) not null,
	age int default 20,
	gender varchar(10) default '남자',
	address varchar(20)
);

insert into realTimeSearch(name,age,gender,address) values('홍길동','22','남자','서울');
insert into realTimeSearch(name,age,gender,address) values('홍길순','23','여자','부산');
insert into realTimeSearch(name,age,gender,address) values('오미자','50','여자','대구');
insert into realTimeSearch(name,age,gender,address) values('오박사','52','남자','울산');
insert into realTimeSearch(name,age,gender,address) values('한지우','20','남자','광주');
insert into realTimeSearch(name,age,gender,address) values('한치우','24','남자','대전');
insert into realTimeSearch(name,age,gender,address) values('김이슬','28','여자','경주');
insert into realTimeSearch(name,age,gender,address) values('김풀잎','26','여자','원주');
insert into realTimeSearch(name,age,gender,address) values('박사님','40','남자','홍천');
insert into realTimeSearch(name,age,gender,address) values('박순님','37','여자','강릉');

select * from realTimeSearch;