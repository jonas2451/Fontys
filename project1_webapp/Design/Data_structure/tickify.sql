--- tables ---

create table USERS
(
	USERNAME 	varchar(40)	not null 	unique 	primary key,
	password	varchar(255)	not null,
	country		varchar(255)	not null,
	city		varchar(30)	not null,
	postal_code	varchar(10)	not null,
	street		varchar(20)	not null,
	house_nr	varchar(10)	not null,
	Fname		varchar(20)	not null,
	Minit		varchar(10),
	Lname		varchar(30)	not null, 
	sex			varchar(10)	not null,
	email		varchar(40)	not null	unique,
	Bdate		date 		not null,
	type_user	text		not null
);

CREATE TABLE pswordReset (
	pswordID serial  NOT NULL PRIMARY KEY NOT NULL,
	pswordEmail		 varchar(40) NOT NULL, 
	pswordSelector 	 varchar(100) NOT NULL,
	pswordToken		 varchar(100) NOT NULL,
	pswordExpDate	 varchar(100) NOT NULL
	);

create table ADMIN
(
	USERNAME varchar(40) 	not null,
	ADMIN_ID	serial		not null, 
	primary key (USERNAME, ADMIN_ID),
	foreign key (USERNAME) references USERS(username)	on delete cascade on update cascade
);

create table EVENT
(	EVENT_NAME	varchar(20)	not null	primary key,
	START_TIME	varchar(10)		not null,
	END_TIME	varchar(10)		not null,
	START_DATE	date		not null,
	END_DATE	date		not null, 
	SELLER_NAME	varchar(20)	not null,
	EVENT_TYPE	varchar(20)	not null,
	LOCATION	varchar(40)	not null,
	EVENT_DESCRIPTION text	not null,
	foreign key (seller_name) references USERS(username) on delete cascade on update cascade
);

create table EVENT_REVIEW
(	USERNAME	varchar(40)	not null,
	REVIEW_DATE	date,	
	REVIEW_POINTS	int		not null,
	EVENT_NAME	varchar(20)	not null,
	REVIEW_TEXT	text		not null,
	REVIEW_ID	serial		not null	primary key,
	foreign key (username) references USERS(username) on delete cascade on update cascade,
	foreign key (event_name) references EVENT(event_name) on delete cascade on update cascade
);

create table EVENT_TICKET_CATEGORY
(	ETC_ID		serial		not null	 primary key,
	CATEGORY	varchar(50)	not null,
	TICKETS_AMOUNT int		not null,
	PRICE 		numeric 	not null,
	EVENT_NAME	varchar(20) not null,
	foreign key (event_name) references event(event_name)	on delete cascade on update cascade
);
create table TICKET
( 	TICKET_CODE varchar(10)	not null	primary key,
	ID_CATEGORY	int			not null,
	foreign key (id_category) references EVENT_TICKET_CATEGORY(ETC_id) 	on delete cascade on update restrict
);
create table USER_ORDER
(	
	ORDER_ID	serial		primary key,
	USERNAME 	varchar(40) not null,
	ORDER_DATE	date		not null,
	foreign key (username) references USERS(username)	on delete cascade on update cascade
);

create table ORDER_ITEM
(
	ORDER_ID	int			not null, 
	TICKET_CODE	varchar(10)	not null,
	primary key (ORDER_ID, TICKET_CODE),
	foreign key (TICKET_CODE) references TICKET(TICKET_CODE) on delete cascade on update cascade,
	foreign key (ORDER_ID) references USER_ORDER(ORDER_ID) on delete cascade on update restrict
);

create table IMAGES
(
	EVENT_NAME	varchar(40)	not null, 
	IMAGE_ID	varchar(255)not null, 
	primary key (EVENT_NAME, IMAGE_ID),
	foreign key (EVENT_NAME) references EVENT(EVENT_NAME) on delete cascade on update cascade
);

create table SHOPPINGCART
(
	USERNAME 	varchar(40) not null, 
	CART_ID		serial		not null	unique,
	primary key (username),
	foreign key (USERNAME) references USERS(USERNAME) on delete cascade on update cascade
);

create table SHOPPINGCART_ITEM
(
	CART_ID		int				not null,		
	TICKET_CODE varchar(10)		not null	unique,  
	primary key (ticket_code), 
	foreign key (CART_ID) references SHOPPINGCART(CART_ID) on delete cascade on update cascade,
	foreign key (TICKET_CODE) references TICKET(TICKET_CODE) on delete cascade on update restrict
);


--- triggered ---

create or replace function checkTicketMax()
    returns trigger as $$
declare
	ord int;
	x record;
begin 
	
	for x in (
	select 		count(t.ticket_code) as cart,t.id_category
	from 		ticket t inner join shoppingcart_item s on s.ticket_code = t.ticket_code
	group by 	id_category )
	loop
		select 		count(t.ticket_code)
		into 		ord
		from 		ticket t inner join order_item o on o.ticket_code = t.ticket_code
		where		t.id_category=x.id_category
		group by 	id_category;
	
		if (ord is null) then
			ord = 0;
		end if;
	
		if (ord + x.cart) > 10 then 
			raise exception 'reached maximum of 10 tickets per event';
		end if;
	end loop;
	
	return new;
		
	
end;
$$ language plpgsql;


create trigger checkTicketMax
	after insert or update on SHOPPINGCART_ITEM
	FOR EACH row execute procedure checkTicketMax();


--- Functions ---
create or replace function checkReviewDate()
    returns trigger as $$
declare 
	date date;
begin 
	select 		start_date
	into 		date
	from 		event e	
	where 		event_name = new.event_name;
	
	if (date > new.review_date and new.review_date > current_date) then 
		raise exception 'Date of visit cannot be before the event´s start date!';
	end if;
	
	if (date > current_date) then
		raise exception 'Date of visit cannot be in the future!';
	end if;

	return new;
	
end;
$$ language plpgsql;

create trigger checkReviewDate
	before insert or update on event_review
	FOR EACH row execute procedure checkReviewDate();

create or replace function checkUserTypeReview()
	returns trigger as $$
declare 
	type 	users.type_user%type;
begin 
	select		type_user
	into 		type
	from 		users u
	where 		username = new.username;

	if (type != 'buyer') then 
		raise exception 'You are not a buyer!';
	end if;
	
	return new;
end;
$$ language plpgsql;

create trigger checkUserTypeReview
	before insert or update on event_review
	for each row execute procedure checkUserTypeReview();

SELECT start_date from event where event_name = 'past_event';
--- constraints ---

alter table USERS
	add constraint BDATE_NOT_IN_THE_FUTURE
	check (bdate < current_timestamp);

alter table event_review
	add constraint REVIEW_POINT_RESTRICTION_CONSTRAINT
	check(review_points <= 5);

alter table USERS
	add constraint SEX_CONSTRAINT
	check(sex = 'm' or sex = 'f' or sex = 'o');

alter table EVENT
	add constraint EVENT_IS_VALID_TYPE
	check(event_type = 'Amusement park' or event_type = 'Concert'
		  or event_type = 'Music festival' or event_type = 'Theatre play'
		  or event_type = 'Sports game' or event_type = 'Family'
		  or event_type = 'Other');

alter table users
	add constraint USER_TYPE_CONSTRAINT
	check(TYPE_USER = 'buyer' or TYPE_USER = 'seller' or TYPE_USER = 'admin');

