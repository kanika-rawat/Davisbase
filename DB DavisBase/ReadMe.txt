To Run the DavisSQL execute the following command in the terminal at the current downloaded folder. 

>javac DavisBase.java
>java DavisBase

To test the program execute following query at the output terminal:

Supported sql commands and their syntax are as follows : Please Press Enter after each command(s).
You can test all the feature(Insert, Update, Delete, Select, Drop, Create, Show by testing the following commands).

1) gives the list of all tables
	show tables;

2) Create the table.
	create table test1 (id int primary key,name text [not null],age int);show tables;

3) Insert the record in the table.
	insert into table (id,name,age) test1 values (1,hearty,42);
	insert into table (id,name,age) test1 values (2,handi,22);

4) Display all/specific attribute with/without where condition the values from table.
	select * from test1;
	select name,age from test1;
	select id,age from test1;
	select * from test1 where row_id = 1;
	select * from test1 where name = handi;
	select id,name,age from test1 where row_id = 1;
	select name,age from test1 where name = handi;
	select name from test1 where age > 8;
	select * from test1 where row_id < 3;

5) Update
	select * from test1;
	update test1 set age = 12 where name = hearty;
	select * from test1;

6) Delete
	select * from test1;
	delete from table test1 where rowid = 1;
	select * from test1;

7) Drop
	drop table test1;
	show tables;