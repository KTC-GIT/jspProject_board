create table account(
	idx int not null auto_increment primary key,
	id varchar(20) not null,
	pw varchar(20) not null,
	nick varchar(10) not null,
	birth timestamp not null,
	email varchar(50) not null,
	tel varchar(20) not null
);

desc account;

insert into account values(default,"admin","1234","완장맨","1999-02-27","admin1234@google.com","010-2134-3245");

select * from account;

SELECT COUNT(id) FROM account WHERE id = "hkd1234";

select count(*) from account where id="admin" and pw="0000";