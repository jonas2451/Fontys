
--trigger for blocking a customer who has not paid upto 2 invoices
drop trigger if exists checkUnpaidInvoices on invoices;

drop function if exists checkUnpaidInvoices();
create or replace function checkUnpaidInvoices() returns trigger as
	$func$
declare
	cName varchar;

begin
	select c.companyname
	into cName
	from (customers c inner join orders o
	on c.id = o.customerid) inner join invoices i
	on o.id = i.orderid
	where i.ispaid = false
	group by (c.companyname)
	having count(c.companyname)>1;

	if new.ispaid = false then
		update customers
		set blockstatus = true
		where companyname = cName;
	end if;

	return new;
end;
$func$ language plpgsql;

create trigger checkUnpaidInvoices before insert or update on invoices
for each row execute procedure checkUnpaidInvoices();



--normal inserts into tables
insert into addresss values('1', 'Germany', 'Duesseldorf', '40210', 'Schillerstraße', '2');
insert into addresss values ('2', 'Germany', 'Moers', '47445', 'Kopernikusstraße', '58');
insert into addresss values ('3', 'Germany', 'Kevelaer', '47623', 'Marktstraße', '2');

insert into customers values('1', 'Ring Police', 'Lukas', 'Luhr', 'info@ringpolice.de', '123wasd:68', '+49284112346', 'Sebastian', 'Stadler', '12345678', false, '1');
insert into customers values('2', 'JP Performance GMBH', 'JP', 'Kreamer', 'info@jpp.de', '123wa4d:68', '+4435682346', 'Sebastian', 'Kubik', '87654321', false, '2');
insert into customers values('3', 'Supermarket', 'Peter', 'Narzinsky', 'rewe@email.com', '456wasd:67', '+49283297765', 'Marta', 'Narzinsky', '2456757', false, '3');


insert into orders values ('1', '2018-10-11', '2');
insert into orders values ('2', '2016-10-23', '2');
insert into orders values ('3', '2018-02-18', '2');
insert into orders values ('4', '2018-08-23', '1');
insert into orders values ('5', '2018-10-11', '1');
insert into orders values ('6', '2018-06-08', '1');
insert into orders values ('7', '2019-04-06', '3');

insert into orderproducts values ('1', 'Milk', false, 5, 50, '1');
insert into orderproducts values ('2', 'Water', false, 10, 55, '7');
insert into orderproducts values ('4', 'Spiritous', true, 20, 60, '4');


insert into invoices values ('1', '2018-10-11', false, '1');
insert into invoices values ('2', '2016-10-23', true, '2');
insert into invoices values ('3', '2018-02-18', true, '3');
insert into invoices values ('4', '2018-08-23', false, '4');
insert into invoices values ('5', '2018-10-11', false, '5');
insert into invoices values ('6', '2018-06-08', false, '6');
insert into invoices values ('7', '2019-04-06', false, '7');

-->insert into receipts values ('982832', false, 100.0, '2018-10-12');

insert into actions values ('asasdjhawdoihasdlkjn', 1, false, 3.0, '1', '2');
insert into actions values ('fkjlnlfijbdynfiojvdl', 7, false, 9.0, '2', '3');
insert into actions values ('dslbfdlnxnrewjjgj', '4', false, 14.0, '4', '1');

insert into actions values ('1', 1, false, 3.0, '1', '2', '2');	

insert into drivers values ('642000', '8');
insert into drivers values ('511999', '7');
insert into drivers values ('237994', '6');

insert into trucks values ('5678', 'big', 'true', '150', '4000', '2000');
insert into trucks values ('6789', 'big', 'true', '120', '3800', '1500');


insert into trailers values ('4321', '2000', 'small', '120');
insert into trailers values ('7686', '2500', 'middle', '130');

insert into drivetrucks values ('642000', '4321', '5678', '1');
insert into drivetrucks values('511999', '7686', '6789', '4');
insert into drivetrucks values('237994', '4321', '6789', '7');





--query for extracting customers who haven't paid upto 2 invoices
select c.companyname
from (customers c inner join orders o
on c.id = o.customerid) inner join invoices i
on o.id = i.orderid
where i.ispaid = false
group by (c.companyname)
having count(c.companyname)>1;


select * from customers



--select distinct max(tracking_no) as karl from actions group by tracking_no;
