create table webMessage(
	idx int not null auto_increment primary key,
	title varchar(100) not null,				-- 메시지 제목
	content text not null,						-- 메시지 내용
	sendId varchar(20) not null,				-- 보내는 사람 아이디
	sendSw char(1) not null default 's',	-- 보낸 메시지(s), 휴지통(g), 삭제(x)
	sendDate timestamp default now(),		-- 보낸 날짜
	receiveId varchar(20) not null,			-- 받는 사람 아이디
	receiveSw char(1) not null default 'n',-- 받는 사람 새 메시지(n), 읽은 메시지(r), 휴지통(g), 삭제(x) 표시
	receiveDate timestamp default now()		-- 받은 날짜 -> 메시지 확인시에는 읽은 날짜로 변경처리한다.
);

desc webMessage;

insert into webMessage values(default,"안녕","이번주 연말 계획머임?","hkd1234",default,default,"aaa1234","n",default);
insert into webMessage values(default,"글쎄","집에만 있을걸?","aaa1234",default,default,"hkd1234","n",default);

select * from webMessage order by idx desc;