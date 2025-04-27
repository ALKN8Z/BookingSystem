alter table "user"
    add column password varchar(255) not null ,
    drop column is_admin;

create table user_role(
    user_id int not null,
    role varchar(50) not null ,
    foreign key (user_id) references "user"(id)
);
