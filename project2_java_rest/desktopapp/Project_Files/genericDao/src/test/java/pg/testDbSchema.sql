create table addresss
(
  id        varchar(255)    primary key,
  country   varchar(255)    not null,
  city      varchar(255)    not null,
  street    varchar(255)    not null,
  number    int             not null
);

insert into addresss values ('123einszweidrei', 'Germany', 'Moers', 'DbStreet', '1');