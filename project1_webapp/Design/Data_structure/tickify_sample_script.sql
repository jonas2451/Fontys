-- user --

insert into USERS 
	values('admin', '$2y$10$6/LiMRJFtbbZAFQdcRqP8e.haIBEgQrGRFuVksagpREeev6ryoYc6', 
		   'Netherlands', 'Venlo', '5931', 'Tegelseweg', 255, 
		   'Admin', '', 'Istrator', 'o', 'guusdamen@hotmail.com', 
		   '2000-1-1', 'admin');
		  
insert into ADMIN(username)
	values('admin');

insert into USERS 
	values('Kevin', '$2y$10$6/LiMRJFtbbZAFQdcRqP8e.haIBEgQrGRFuVksagpREeev6ryoYc6', 
		   'Netherlands', 'Venlo', '5931', 'Tegelseweg', 255, 
		   'Kevin', '', 'User', 'm', 'jonaster@online.de', 
		   '2000-1-1', 'buyer');

		  insert into USERS 
	values('Karen', '$2y$10$6/LiMRJFtbbZAFQdcRqP8e.haIBEgQrGRFuVksagpREeev6ryoYc6', 
		   'Netherlands', 'Venlo', '5931', 'Tegelseweg', 255, 
		   'Karen', '', 'Stinson', 'f', 'guusdamen21232@hotmail.com', 
		   '2000-1-1', 'seller');
--- events ---
-- christmas -- 
insert into event
	values(	'Christmas', '12:45:00', '00:45:00', '2018-12-24', 
			'2018-12-24', 'admin', 'Family', 'Chruch', 'This is Christmas! Where are my presents?!');

insert into images
	values('Christmas', '5c0e604954d1a8.14112771.png');

insert into EVENT_TICKET_CATEGORY(category, tickets_amount, price, event_name)
	 values('first row', 10, 10, 'Christmas'), ('seconds row', 10, 10, 'Christmas'), ('third row', 10, 10, 'Christmas');

-- new year's eve --
insert into event
	values(	'New Year´s Eve', '19:45:00', '03:00:00', '2017-12-31', 
			'2018-01-01', 'admin', 'Family', 'Mom´s house', 'Happy New Year!');
		
insert into images
	values('New Year´s Eve', '5c0e61d0ec05e7.43041394.jpg');	

insert into EVENT_TICKET_CATEGORY(category, tickets_amount, price, event_name)
	 values('Outside', 100, 0, 'New Year´s Eve'), ('Inside', 20, 10, 'New Year´s Eve'), ('Cellar', 10, 10, 'New Year´s Eve');



	

	--insert into user_order (order_id, username, order_date) 
	--values(123, 'buyer', '2008-12-24');

--insert into order_item(order_id, ticket_code, amount)
--	values(123, '543556', 3);

--insert into ticket (ticket_code, id_category) 
--	values('543556', 234);

--insert into event_ticket_category (etc_id, category, tickets_amount, price, event_name)
--	values(234, '234', 3, 34, 'Christmas' );
