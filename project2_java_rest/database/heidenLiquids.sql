-- heiden liquids sql-DDL schema --

drop table if exists trucks cascade;
drop table if exists trailers cascade;
drop table if exists customers cascade;
drop table if exists employees cascade;
drop table if exists drivers cascade;
drop table if exists orders cascade;
drop table if exists orderproducts cascade;
drop table if exists drivetrucks cascade;
drop table if exists workorders cascade;
drop table if exists actions cascade;
drop table if exists receipts cascade;
drop table if exists addresss cascade;
drop table if exists invoices cascade;

create table trucks
(
  licencenumber       varchar(255) primary key,
  typeoftruck         varchar(255) not null,
  status              boolean      not null,
  currentweightloaded decimal      not null,
  maxweight           decimal      not null,
  maxtowingweight     decimal      not null
);

create table trailers
(
  licencenumber       varchar(255) primary key,
  maxweight           decimal      not null,
  trailertype         varchar(255) not null,
  currentweightloaded decimal      not null
);

create table addresss
(
  id      varchar(36) primary key,
  country varchar(255) not null,
  city    varchar(255) not null,
  zip     varchar(255) not null,
  street  varchar(255) not null,
  number  varchar(255) not null
);

create table customers
(
  id                         varchar(36) primary key,
  companyname                varchar(255) not null,
  firstname                  varchar(255) not null,
  lastname                   varchar(255) not null,
  email                      varchar(255) not null,
  taxnumber                  varchar(255) not null,
  phonenumber                varchar(255) not null,
  firstnameresponsibleperson varchar(255) not null,
  lastnameresponsibleperson  varchar(255) not null,
  faxnumber                  varchar(255) not null,
  blockstatus                boolean      not null,
  addressid                  varchar(36)  not null,
  constraint fkaddressscustomers foreign key (addressid) references addresss (id) on delete cascade
);

create table employees
(
  id            varchar(36) primary key,
  username      varchar(255) not null,
  password      varchar(255) not null,
  usertype      varchar(255) not null,
  email         varchar(255) not null,
  firstname     varchar(255) not null,
  lastname      varchar(255) not null,
  birthdate     date         not null,
  dateofjoining date         not null,
  addressid     varchar(36)  not null,
  constraint fkaddresssemployees foreign key (addressid) references addresss (id) on delete cascade
);

create table drivers
(
  id          varchar(36) primary key,
  licencetype varchar(255) not null
);

create table orders
(
  id        varchar(36) primary key,
  orderdate date not null,
  customerid varchar(36) not null,
  constraint fkordercustomer foreign key (customerid) references customers (id) on delete cascade
);

create table invoices
(
  id          varchar(36) NOT NULL,
  invoicedate date        NOT NULL,
  ispaid     boolean	  not null,
  orderid     varchar(36) not null,
  CONSTRAINT invoicespkey PRIMARY KEY (id),
  constraint fkinvoicesorders    foreign key (orderid)    references orders (id) on delete cascade
);



create table orderproducts
(
  id          varchar(36) primary key,
  name        varchar(255) not null,
  ishazardous boolean      not null,
  totalweight decimal      not null,
  priceperton decimal      not null,
  orderid     varchar(36)  not null,
  constraint fkorderproductsorders foreign key (orderid) references orders (id) on delete cascade
);

create table receipts
(
  receiptno    varchar(36) primary key,
  laoding	   decimal not null, 
  actualweight decimal not null,
  date         date    not null
);


create table actions
(
  trackingno     varchar(36) primary key,
  orderingno     int         not null,
  isunloading    boolean     not null,
  expectedweight decimal     not null,
  productid      varchar(36) not null,
  addressid      varchar(36) not null,
  receiptno      varchar(36),
  constraint fkactionsorderproducts foreign key (productid) references orderproducts (id) on delete cascade,
  constraint fkactionsreceipts foreign key (receiptno) references receipts (receiptno) on delete cascade,
  constraint fkactionsaddress foreign key (addressid) references addresss (id)
);


create table workorders
(
  id         varchar(36) primary key,
  productid  varchar(36) not null,
  customerid varchar(36) not null,
  orderid    varchar(36) not null,
  constraint fkworkorderorderproducts foreign key (productid) references orderproducts (id) on DELETE cascade,
  constraint fkworkordercustomers foreign key (customerid) references customers (id) on delete cascade,
  constraint fkworkorderorders foreign key (orderid) references orders (id) on delete cascade
);


create table drivetrucks
(
  driverid         varchar(36) not null,
  trailerlicenceno varchar     not null,
  trucklicenceno   varchar     not null,
  orderid          varchar(36) not null,
  constraint pkdrivetrucks primary key (driverid, trucklicenceno, trailerlicenceno, orderid),
  constraint fkdrivetrucksdrivers foreign key (driverid) references drivers (id) on delete cascade,
  constraint fkdrivetruckstrucks foreign key (trucklicenceno) references trucks (licencenumber) on DELETE cascade,
  constraint fkdrivetruckstrailers foreign key (trailerlicenceno) references trailers (licencenumber) on delete cascade,
  constraint fkdrivetrucksorders foreign key (orderid) references orders (id) on delete cascade
);

drop view if exists executeorders;

create or replace view executeorders as
select dt.driverid, dt.trailerlicenceno, dt.trucklicenceno, dt.orderid, op.name, op.ishazardous, ac.isunloading, a.country, a.street, a.number , a.zip, ac.trackingno
from drivetrucks dt
inner join orders o on o.id = dt.orderid 
inner join orderproducts op on op.orderid = dt.orderid
inner join actions ac on ac.productid = op.id
inner join addresss a on a.id = ac.addressid;

alter table orders
  add constraint checkorderdate check ( orderdate > now());
