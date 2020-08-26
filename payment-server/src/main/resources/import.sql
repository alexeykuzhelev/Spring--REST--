insert into client(id,name,phone) values(0,'Иванов','+79181111111'),(1,'Петров','+79181111112');
insert into account(id,client_id,balance,name) values (0,0,0.00,'Карта зарплатная'),(1,0,0.00,'Карта кредитная'),(2,1,0.00,'Карта зарплатная');
insert into operation(id,src_account_id,dst_account_id,amount,ddate) values (0,1,2,10.00,'2020-08-11');
insert into operation(id,src_account_id,dst_account_id,amount,ddate) values (1,2,1,20.00,'2020-08-12');


