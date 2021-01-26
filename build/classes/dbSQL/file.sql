create table file(
	idx int not null auto_increment primary key,
	bidx int not null default 0,		-- 0은 임시로 올라가 있는 파일들이다.
	filePath varchar(500) not null,
	fileName varchar(50) not null,
	orifileName varchar(50) not null,
	hashCode text,
	uploadDate timestamp not null default now(),
	downloadCount int not null default 0
);

desc file;

drop table file;