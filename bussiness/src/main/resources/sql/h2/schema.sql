
    drop table if exists acct_group;

    drop table if exists acct_group_permission;

    drop table if exists acct_user;

    drop table if exists acct_user_group;

    create table acct_group (
        id bigint generated by default as identity,
        name varchar(255) not null unique,
        primary key (id)
    ) ;

    create table acct_group_permission (
        group_id bigint not null,
        permission varchar(255) not null,
        foreign key(group_id) references acct_group(id),
    ) ;

    create table acct_user (
        id bigint generated by default as identity,
        email varchar(255),
        login_name varchar(255) not null unique,
        name varchar(255),
        password varchar(255),
        dimensionCode varchar(255),
        status bit,
        primary key (id)
    ) ;

    create table acct_user_group (
        user_id bigint not null,
        group_id bigint not null,
     	primary key(user_id,group_id),
        foreign key(user_id) references acct_user(id),
        foreign key(group_id) references acct_group(id)
    ) ;

