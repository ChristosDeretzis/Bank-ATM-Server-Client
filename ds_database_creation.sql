create database BankATM;
USE BankATM;
create table Customer(
	id integer not null auto_increment,
    username varchar(40) not null,
    first_name varchar(40),
    last_name varchar(40),
    pin integer not null,
    constraint PK_id primary key(id)
    constraint PK_
);
customer
create table Balance(
	bid integer not null auto_increment,
    cid integer not null,
    balance double not null,
    last_updated datetime not null,
    constraint PK_bid primary key(bid),
    constraint FK_cid foreign key(cid) references Customer(id)
);

create table Deposit(
	did integer not null auto_increment,
    cid integer not null,
    amount double not null,
    last_updated datetime not null,
    constraint PR_did primary key(did),
    constraint FK_customer foreign key(cid) references Customer(id)
);

create table Withdrawal(
	wid integer not null auto_increment,
    cid integer not null,
    amount double not null,
    last_updated datetime not null,
    constraint PR_wid primary key(wid),
    constraint FK_cusId foreign key(cid) references Customer(id)
);

INSERT INTO Customer(username, first_name, last_name, pin) values
	("algo34", "Christos", "Deretzis", 1998),
    ("dimider", "Dimitris", "ioannou", 2004);

INSERT INTO Balance(cid, balance, last_updated) values
	(1,0,now()),
	(2,0,now())
