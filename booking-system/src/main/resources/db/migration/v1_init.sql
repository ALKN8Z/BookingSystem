create table if not exists "user"(
    id serial primary key ,
    email varchar(255) not null UNIQUE ,
    is_admin boolean default false
);

create table if not exists room(
    id serial primary key,
    name varchar(255) not null unique
);

create table if not exists booking(
    id serial primary key,
    user_id integer references "user"(id),
    room_id integer references room(id),
    start_time timestamp not null,
    end_time timestamp not null ,
    status varchar(20) default 'ACTIVE',
    notified boolean default false
);

create index idx_booking_time on booking(room_id, start_time, end_time);