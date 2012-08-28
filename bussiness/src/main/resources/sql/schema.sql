
    alter table acct_group_permission 
        drop constraint FK19C1023BEFE35BA9;

    alter table acct_user_group 
        drop constraint FKD19A04B7274F65AB;

    alter table acct_user_group 
        drop constraint FKD19A04B7EFE35BA9;

    alter table graphic_infos 
        drop constraint FKA2E5796EE36BFFE3;

    alter table graphic_infos 
        drop constraint FKA2E5796EEBB54FE8;

    alter table graphic_infos 
        drop constraint FKA2E5796ECDC4AB82;

    alter table history_mgraphic 
        drop constraint FK73D101266C146C11;

    alter table holiday 
        drop constraint FK411528586F516363;

    alter table mgraphic 
        drop constraint FK4B95CDDB6C146C11;

    alter table mgraphic 
        drop constraint FK4B95CDDBEBB54FE8;

    alter table mgraphic 
        drop constraint FK4B95CDDBCDC4AB82;

    alter table mgraphic_phone_no 
        drop constraint FK6F8CE016A4886B19;

    alter table suggestion 
        drop constraint FK4763CA0473976E23;

    alter table user_catacts 
        drop constraint FK7C0ED6836C146C11;

    alter table user_catacts 
        drop constraint FK7C0ED6834DEBD61E;

    alter table user_diy_graphic 
        drop constraint FKA2E5796EE36BFFE3b013fbc9;

    alter table user_diy_graphic 
        drop constraint FKB013FBC96C146C11;

    alter table user_diy_graphic 
        drop constraint FKA2E5796ECDC4AB82b013fbc9;

    alter table user_diy_graphic 
        drop constraint FKA2E5796EEBB54FE8b013fbc9;

    alter table user_favorites 
        drop constraint FKF2FF4EE36C146C11;

    alter table user_subscribe_graphic_item 
        drop constraint FK10A9E5D36C146C11;

    alter table user_subscribe_record 
        drop constraint FKECF0ADA6C146C11;

    alter table user_subscribe_record 
        drop constraint FKECF0ADA9E7BF8A6;

    alter table user_subscribe_type 
        drop constraint FK44373C036C146C11;

    alter table user_subscribe_type 
        drop constraint FK44373C039E7BF8A6;

    drop table acct_group if exists;

    drop table acct_group_permission if exists;

    drop table acct_user if exists;

    drop table acct_user_group if exists;

    drop table category if exists;

    drop table graphic_infos if exists;

    drop table graphic_resource if exists;

    drop table history_mgraphic if exists;

    drop table holiday if exists;

    drop table holiday_type if exists;

    drop table mgraphic if exists;

    drop table mgraphic_phone_no if exists;

    drop table signature if exists;

    drop table sms_message if exists;

    drop table status_type if exists;

    drop table subscribe_type if exists;

    drop table suggestion if exists;

    drop table type if exists;

    drop table user_catacts if exists;

    drop table user_diy_graphic if exists;

    drop table user_favorites if exists;

    drop table user_subscribe_graphic_item if exists;

    drop table user_subscribe_record if exists;

    drop table user_subscribe_type if exists;

    drop table userinfo if exists;

    drop table versioninfo if exists;

    create table acct_group (
        id bigint generated by default as identity,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table acct_group_permission (
        group_id bigint not null,
        permission varchar(255)
    );

    create table acct_user (
        id bigint generated by default as identity,
        email varchar(255),
        login_name varchar(255),
        name varchar(255),
        password varchar(255),
        status boolean,
        primary key (id)
    );

    create table acct_user_group (
        user_id bigint not null,
        group_id bigint not null
    );

    create table category (
        id bigint generated by default as identity,
        description varchar(180),
        download_num integer,
        graphic_resource_id varchar(255),
        name varchar(40),
        primary key (id)
    );

    create table graphic_infos (
        id varchar(32) not null,
        created_by varchar(64),
        created_on timestamp,
        updated_by varchar(64),
        updated_on timestamp,
        audit_status varchar(20),
        level integer,
        name varchar(40),
        owner varchar(40),
        price double,
        recommend boolean,
        signature varchar(60),
        use_count integer,
        category_id bigint,
        holiday_type_id bigint,
        status_type_id bigint,
        primary key (id)
    );

    create table graphic_resource (
        id varchar(32) not null,
        created_by varchar(64),
        created_on timestamp,
        updated_by varchar(64),
        updated_on timestamp,
        audit_passed boolean,
        resource_id varchar(40),
        source_path varchar(255),
        thumbnail_path varchar(255),
        type varchar(10),
        graphicinfo_id varchar(32),
        primary key (id)
    );

    create table history_mgraphic (
        id varchar(32) not null,
        created_by varchar(64),
        created_on timestamp,
        updated_by varchar(64),
        updated_on timestamp,
        mode_type integer,
        modify_time timestamp,
        name varchar(255),
        priority integer,
        signature varchar(255),
        graphic_info_id varchar(32),
        user_id varchar(32),
        primary key (id)
    );

    create table holiday (
        id bigint generated by default as identity,
        holiday_day date,
        type bigint,
        primary key (id)
    );

    create table holiday_type (
        id bigint generated by default as identity,
        download_num integer,
        graphic_resource_id varchar(255),
        level integer,
        name varchar(255),
        primary key (id)
    );

    create table mgraphic (
        cast_type varchar(10) not null,
        id varchar(32) not null,
        created_by varchar(64),
        created_on timestamp,
        updated_by varchar(64),
        updated_on timestamp,
        mode_type integer,
        modify_time timestamp,
        name varchar(255),
        priority integer,
        signature varchar(255),
        subscribe boolean,
        common boolean,
        holiday timestamp,
        special_phone_no varchar(20),
        valid_date timestamp,
        begin timestamp,
        end timestamp,
        graphic_info_id varchar(32),
        user_id varchar(32),
        holiday_type_id bigint,
        status_type_id bigint,
        primary key (id)
    );

    create table mgraphic_phone_no (
        mgraphic_id varchar(32) not null,
        phone_number varchar(255)
    );

    create table signature (
        id bigint generated by default as identity,
        created_by varchar(64),
        created_on timestamp,
        updated_by varchar(64),
        updated_on timestamp,
        content varchar(255),
        level Integer default '-1',
        type Integer default '-1',
        primary key (id)
    );

    create table sms_message (
        id bigint generated by default as identity,
        created_by varchar(64),
        created_on timestamp,
        updated_by varchar(64),
        updated_on timestamp,
        from_mobile_no varchar(255),
        is_send boolean,
        sms varchar(255),
        to_mobile_no varchar(255),
        primary key (id)
    );

    create table status_type (
        id bigint generated by default as identity,
        graphic_resource_id varchar(255),
        name varchar(255),
        primary key (id)
    );

    create table subscribe_type (
        cast_type varchar(10) not null,
        id bigint generated by default as identity,
        created_by varchar(64),
        created_on timestamp,
        updated_by varchar(64),
        updated_on timestamp,
        name varchar(255),
        price double,
        primary key (id)
    );

    create table suggestion (
        id bigint generated by default as identity,
        created_by varchar(64),
        created_on timestamp,
        updated_by varchar(64),
        updated_on timestamp,
        content varchar(500),
        userinfo_id varchar(32),
        primary key (id)
    );

    create table type (
        id bigint generated by default as identity,
        created_by varchar(64),
        created_on timestamp,
        updated_by varchar(64),
        updated_on timestamp,
        name varchar(255),
        primary key (id)
    );

    create table user_catacts (
        id bigint generated by default as identity,
        created_by varchar(64),
        created_on timestamp,
        updated_by varchar(64),
        updated_on timestamp,
        name varchar(255),
        phone_no varchar(255),
        self_user_id varchar(32),
        user_id varchar(32),
        primary key (id)
    );

    create table user_diy_graphic (
        id varchar(32) not null,
        created_by varchar(64),
        created_on timestamp,
        updated_by varchar(64),
        updated_on timestamp,
        audit_status varchar(20),
        level integer,
        name varchar(40),
        owner varchar(40),
        price double,
        recommend boolean,
        signature varchar(60),
        use_count integer,
        category_id bigint,
        holiday_type_id bigint,
        status_type_id bigint,
        user_id varchar(32),
        primary key (id)
    );

    create table user_favorites (
        id varchar(32) not null,
        created_by varchar(64),
        created_on timestamp,
        updated_by varchar(64),
        updated_on timestamp,
        graphic_info_id varchar(32),
        user_id varchar(32),
        primary key (id)
    );

    create table user_subscribe_graphic_item (
        id bigint generated by default as identity,
        created_by varchar(64),
        created_on timestamp,
        updated_by varchar(64),
        updated_on timestamp,
        validate_time timestamp,
        graphic_info_id varchar(32),
        user_id varchar(32),
        primary key (id)
    );

    create table user_subscribe_record (
        id bigint generated by default as identity,
        created_by varchar(64),
        created_on timestamp,
        updated_by varchar(64),
        updated_on timestamp,
        description varchar(200),
        expenses double,
        income double,
        subscribe_type_id bigint,
        user_id varchar(32),
        primary key (id)
    );

    create table user_subscribe_type (
        id bigint generated by default as identity,
        created_by varchar(64),
        created_on timestamp,
        updated_by varchar(64),
        updated_on timestamp,
        subscribe_status varchar(20),
        validate_month integer,
        subscribe_type_id bigint,
        user_id varchar(32),
        primary key (id)
    );

    create table userinfo (
        id varchar(32) not null,
        created_by varchar(64),
        created_on timestamp,
        updated_by varchar(64),
        updated_on timestamp,
        device_id varchar(60),
        imsi varchar(255),
        phone_no varchar(255),
        totle_money double,
        user_agent varchar(60),
        primary key (id),
        unique (phone_no)
    );

    create table versioninfo (
        id bigint generated by default as identity,
        created_by varchar(64),
        created_on timestamp,
        updated_by varchar(64),
        updated_on timestamp,
        url varchar(255),
        version varchar(255),
        primary key (id)
    );

    alter table acct_group_permission 
        add constraint FK19C1023BEFE35BA9 
        foreign key (group_id) 
        references acct_group;

    alter table acct_user_group 
        add constraint FKD19A04B7274F65AB 
        foreign key (user_id) 
        references acct_user;

    alter table acct_user_group 
        add constraint FKD19A04B7EFE35BA9 
        foreign key (group_id) 
        references acct_group;

    alter table graphic_infos 
        add constraint FKA2E5796EE36BFFE3 
        foreign key (category_id) 
        references category;

    alter table graphic_infos 
        add constraint FKA2E5796EEBB54FE8 
        foreign key (status_type_id) 
        references status_type;

    alter table graphic_infos 
        add constraint FKA2E5796ECDC4AB82 
        foreign key (holiday_type_id) 
        references holiday_type;

    alter table history_mgraphic 
        add constraint FK73D101266C146C11 
        foreign key (user_id) 
        references userinfo;

    alter table holiday 
        add constraint FK411528586F516363 
        foreign key (type) 
        references holiday_type;

    alter table mgraphic 
        add constraint FK4B95CDDB6C146C11 
        foreign key (user_id) 
        references userinfo;

    alter table mgraphic 
        add constraint FK4B95CDDBEBB54FE8 
        foreign key (status_type_id) 
        references status_type;

    alter table mgraphic 
        add constraint FK4B95CDDBCDC4AB82 
        foreign key (holiday_type_id) 
        references holiday_type;

    alter table mgraphic_phone_no 
        add constraint FK6F8CE016A4886B19 
        foreign key (mgraphic_id) 
        references mgraphic;

    alter table suggestion 
        add constraint FK4763CA0473976E23 
        foreign key (userinfo_id) 
        references userinfo;

    alter table user_catacts 
        add constraint FK7C0ED6836C146C11 
        foreign key (user_id) 
        references userinfo;

    alter table user_catacts 
        add constraint FK7C0ED6834DEBD61E 
        foreign key (self_user_id) 
        references userinfo;

    alter table user_diy_graphic 
        add constraint FKA2E5796EE36BFFE3b013fbc9 
        foreign key (category_id) 
        references category;

    alter table user_diy_graphic 
        add constraint FKB013FBC96C146C11 
        foreign key (user_id) 
        references userinfo;

    alter table user_diy_graphic 
        add constraint FKA2E5796ECDC4AB82b013fbc9 
        foreign key (holiday_type_id) 
        references holiday_type;

    alter table user_diy_graphic 
        add constraint FKA2E5796EEBB54FE8b013fbc9 
        foreign key (status_type_id) 
        references status_type;

    alter table user_favorites 
        add constraint FKF2FF4EE36C146C11 
        foreign key (user_id) 
        references userinfo;

    alter table user_subscribe_graphic_item 
        add constraint FK10A9E5D36C146C11 
        foreign key (user_id) 
        references userinfo;

    alter table user_subscribe_record 
        add constraint FKECF0ADA6C146C11 
        foreign key (user_id) 
        references userinfo;

    alter table user_subscribe_record 
        add constraint FKECF0ADA9E7BF8A6 
        foreign key (subscribe_type_id) 
        references subscribe_type;

    alter table user_subscribe_type 
        add constraint FK44373C036C146C11 
        foreign key (user_id) 
        references userinfo;

    alter table user_subscribe_type 
        add constraint FK44373C039E7BF8A6 
        foreign key (subscribe_type_id) 
        references subscribe_type;
