create table board(
	bidx int not null auto_increment primary key,
    id varchar(20) not null,
    pw varchar(20) not null,
    tag varchar(20) not null,
    title varchar(50) not null,
    content varchar(10000) not null,
    writedate timestamp default current_timestamp(),
    hits int default 0,
    recommend int default 0
);
show tables;
desc board;

create table reply(
	ridx int not null auto_increment primary key,
    bidx int not null,
    id varchar(20) not null,
    pw varchar(20) not null,
    content varchar(50) not null,
    writedate timestamp default current_timestamp(),
    foreign key(bidx) references board(bidx)
);

desc reply;
desc user;
select * from user;