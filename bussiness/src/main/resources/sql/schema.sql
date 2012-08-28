
    alter table acct_group_permission 
        drop 
        foreign key FK19C1023BEFE35BA9;

    alter table acct_user_group 
        drop 
        foreign key FKD19A04B7274F65AB;

    alter table acct_user_group 
        drop 
        foreign key FKD19A04B7EFE35BA9;

    alter table graphic_infos 
        drop 
        foreign key FKA2E5796EE36BFFE3;

    alter table graphic_infos 
        drop 
        foreign key FKA2E5796EEBB54FE8;

    alter table graphic_infos 
        drop 
        foreign key FKA2E5796ECDC4AB82;

    alter table history_mgraphic 
        drop 
        foreign key FK73D101266C146C11;

    alter table holiday 
        drop 
        foreign key FK411528586F516363;

    alter table mgraphic 
        drop 
        foreign key FK4B95CDDB6C146C11;

    alter table mgraphic 
        drop 
        foreign key FK4B95CDDBCDC4AB82;

    alter table mgraphic 
        drop 
        foreign key FK4B95CDDBEBB54FE8;

    alter table mgraphic_phone_no 
        drop 
        foreign key FK6F8CE016A4886B19;

    alter table suggestion 
        drop 
        foreign key FK4763CA0473976E23;

    alter table user_catacts 
        drop 
        foreign key FK7C0ED6836C146C11;

    alter table user_catacts 
        drop 
        foreign key FK7C0ED6834DEBD61E;

    alter table user_diy_graphic 
        drop 
        foreign key FKA2E5796EE36BFFE3b013fbc9;

    alter table user_diy_graphic 
        drop 
        foreign key FKB013FBC96C146C11;

    alter table user_diy_graphic 
        drop 
        foreign key FKA2E5796ECDC4AB82b013fbc9;

    alter table user_diy_graphic 
        drop 
        foreign key FKA2E5796EEBB54FE8b013fbc9;

    alter table user_favorites 
        drop 
        foreign key FKF2FF4EE36C146C11;

    alter table user_subscribe_graphic_item 
        drop 
        foreign key FK10A9E5D36C146C11;

    alter table user_subscribe_record 
        drop 
        foreign key FKECF0ADA6C146C11;

    alter table user_subscribe_record 
        drop 
        foreign key FKECF0ADA9E7BF8A6;

    alter table user_subscribe_type 
        drop 
        foreign key FK44373C036C146C11;

    alter table user_subscribe_type 
        drop 
        foreign key FK44373C039E7BF8A6;

    drop table if exists acct_group;

    drop table if exists acct_group_permission;

    drop table if exists acct_user;

    drop table if exists acct_user_group;

    drop table if exists category;

    drop table if exists graphic_infos;

    drop table if exists graphic_resource;

    drop table if exists history_mgraphic;

    drop table if exists holiday;

    drop table if exists holiday_type;

    drop table if exists mgraphic;

    drop table if exists mgraphic_phone_no;

    drop table if exists signature;

    drop table if exists sms_message;

    drop table if exists status_type;

    drop table if exists subscribe_type;

    drop table if exists suggestion;

    drop table if exists type;

    drop table if exists user_catacts;

    drop table if exists user_diy_graphic;

    drop table if exists user_favorites;

    drop table if exists user_subscribe_graphic_item;

    drop table if exists user_subscribe_record;

    drop table if exists user_subscribe_type;

    drop table if exists userinfo;

    drop table if exists versioninfo;

    create table acct_group (
        id bigint not null auto_increment,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table acct_group_permission (
        group_id bigint not null,
        permission varchar(255)
    );

    create table acct_user (
        id bigint not null auto_increment,
        email varchar(255),
        login_name varchar(255),
        name varchar(255),
        password varchar(255),
        status bit,
        primary key (id)
    );

    create table acct_user_group (
        user_id bigint not null,
        group_id bigint not null
    );

    create table category (
        id bigint not null auto_increment,
        description varchar(180),
        download_num integer,
        graphic_resource_id varchar(255),
        name varchar(40),
        primary key (id)
    );

    create table graphic_infos (
        id varchar(32) not null,
        created_by varchar(64),
        created_on datetime,
        updated_by varchar(64),
        updated_on datetime,
        audit_status varchar(20),
        level integer,
        name varchar(40),
        owner varchar(40),
        price double precision,
        recommend bit,
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
        created_on datetime,
        updated_by varchar(64),
        updated_on datetime,
        audit_passed bit,
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
        created_on datetime,
        updated_by varchar(64),
        updated_on datetime,
        mode_type integer,
        modify_time datetime,
        name varchar(255),
        priority integer,
        signature varchar(255),
        graphic_info_id varchar(32),
        user_id varchar(32),
        primary key (id)
    );

    create table holiday (
        id bigint not null auto_increment,
        holiday_day date,
        type bigint,
        primary key (id)
    );

    create table holiday_type (
        id bigint not null auto_increment,
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
        created_on datetime,
        updated_by varchar(64),
        updated_on datetime,
        mode_type integer,
        modify_time datetime,
        name varchar(255),
        priority integer,
        signature varchar(255),
        subscribe bit,
        common bit,
        valid_date datetime,
        holiday datetime,
        special_phone_no varchar(20),
        begin datetime,
        end datetime,
        graphic_info_id varchar(32),
        user_id varchar(32),
        status_type_id bigint,
        holiday_type_id bigint,
        primary key (id)
    );

    create table mgraphic_phone_no (
        mgraphic_id varchar(32) not null,
        phone_number varchar(255)
    );

    create table signature (
        id bigint not null auto_increment,
        created_by varchar(64),
        created_on datetime,
        updated_by varchar(64),
        updated_on datetime,
        content varchar(255),
        level Integer default '-1',
        type Integer default '-1',
        primary key (id)
    );

    create table sms_message (
        id bigint not null auto_increment,
        created_by varchar(64),
        created_on datetime,
        updated_by varchar(64),
        updated_on datetime,
        from_mobile_no varchar(255),
        is_send bit,
        sms varchar(255),
        to_mobile_no varchar(255),
        primary key (id)
    );

    create table status_type (
        id bigint not null auto_increment,
        graphic_resource_id varchar(255),
        name varchar(255),
        primary key (id)
    );

    create table subscribe_type (
        cast_type varchar(10) not null,
        id bigint not null auto_increment,
        created_by varchar(64),
        created_on datetime,
        updated_by varchar(64),
        updated_on datetime,
        name varchar(255),
        price double precision,
        primary key (id)
    );

    create table suggestion (
        id bigint not null auto_increment,
        created_by varchar(64),
        created_on datetime,
        updated_by varchar(64),
        updated_on datetime,
        content longtext,
        userinfo_id varchar(32),
        primary key (id)
    );

    create table type (
        id bigint not null auto_increment,
        created_by varchar(64),
        created_on datetime,
        updated_by varchar(64),
        updated_on datetime,
        name varchar(255),
        primary key (id)
    );

    create table user_catacts (
        id bigint not null auto_increment,
        created_by varchar(64),
        created_on datetime,
        updated_by varchar(64),
        updated_on datetime,
        name varchar(255),
        phone_no varchar(255),
        self_user_id varchar(32),
        user_id varchar(32),
        primary key (id)
    );

    create table user_diy_graphic (
        id varchar(32) not null,
        created_by varchar(64),
        created_on datetime,
        updated_by varchar(64),
        updated_on datetime,
        audit_status varchar(20),
        level integer,
        name varchar(40),
        owner varchar(40),
        price double precision,
        recommend bit,
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
        created_on datetime,
        updated_by varchar(64),
        updated_on datetime,
        graphic_info_id varchar(32),
        user_id varchar(32),
        primary key (id)
    );

    create table user_subscribe_graphic_item (
        id bigint not null auto_increment,
        created_by varchar(64),
        created_on datetime,
        updated_by varchar(64),
        updated_on datetime,
        validate_time datetime,
        graphic_info_id varchar(32),
        user_id varchar(32),
        primary key (id)
    );

    create table user_subscribe_record (
        id bigint not null auto_increment,
        created_by varchar(64),
        created_on datetime,
        updated_by varchar(64),
        updated_on datetime,
        description varchar(200),
        expenses double precision,
        income double precision,
        subscribe_type_id bigint,
        user_id varchar(32),
        primary key (id)
    );

    create table user_subscribe_type (
        id bigint not null auto_increment,
        created_by varchar(64),
        created_on datetime,
        updated_by varchar(64),
        updated_on datetime,
        subscribe_status varchar(20),
        validate_month integer,
        subscribe_type_id bigint,
        user_id varchar(32),
        primary key (id)
    );

    create table userinfo (
        id varchar(32) not null,
        created_by varchar(64),
        created_on datetime,
        updated_by varchar(64),
        updated_on datetime,
        device_id varchar(60),
        imsi varchar(40) unique,
        phone_no varchar(20) unique,
        totle_money double precision,
        user_agent varchar(60),
        primary key (id)
    );

    create table versioninfo (
        id bigint not null auto_increment,
        created_by varchar(64),
        created_on datetime,
        updated_by varchar(64),
        updated_on datetime,
        url varchar(255),
        version varchar(255),
        primary key (id)
    );

    alter table acct_group_permission 
        add index FK19C1023BEFE35BA9 (group_id), 
        add constraint FK19C1023BEFE35BA9 
        foreign key (group_id) 
        references acct_group (id);

    alter table acct_user_group 
        add index FKD19A04B7274F65AB (user_id), 
        add constraint FKD19A04B7274F65AB 
        foreign key (user_id) 
        references acct_user (id);

    alter table acct_user_group 
        add index FKD19A04B7EFE35BA9 (group_id), 
        add constraint FKD19A04B7EFE35BA9 
        foreign key (group_id) 
        references acct_group (id);

    alter table graphic_infos 
        add index FKA2E5796EE36BFFE3 (category_id), 
        add constraint FKA2E5796EE36BFFE3 
        foreign key (category_id) 
        references category (id);

    alter table graphic_infos 
        add index FKA2E5796EEBB54FE8 (status_type_id), 
        add constraint FKA2E5796EEBB54FE8 
        foreign key (status_type_id) 
        references status_type (id);

    alter table graphic_infos 
        add index FKA2E5796ECDC4AB82 (holiday_type_id), 
        add constraint FKA2E5796ECDC4AB82 
        foreign key (holiday_type_id) 
        references holiday_type (id);

    alter table history_mgraphic 
        add index FK73D101266C146C11 (user_id), 
        add constraint FK73D101266C146C11 
        foreign key (user_id) 
        references userinfo (id);

    alter table holiday 
        add index FK411528586F516363 (type), 
        add constraint FK411528586F516363 
        foreign key (type) 
        references holiday_type (id);

    alter table mgraphic 
        add index FK4B95CDDB6C146C11 (user_id), 
        add constraint FK4B95CDDB6C146C11 
        foreign key (user_id) 
        references userinfo (id);

    alter table mgraphic 
        add index FK4B95CDDBCDC4AB82 (holiday_type_id), 
        add constraint FK4B95CDDBCDC4AB82 
        foreign key (holiday_type_id) 
        references holiday_type (id);

    alter table mgraphic 
        add index FK4B95CDDBEBB54FE8 (status_type_id), 
        add constraint FK4B95CDDBEBB54FE8 
        foreign key (status_type_id) 
        references status_type (id);

    alter table mgraphic_phone_no 
        add index FK6F8CE016A4886B19 (mgraphic_id), 
        add constraint FK6F8CE016A4886B19 
        foreign key (mgraphic_id) 
        references mgraphic (id);

    alter table suggestion 
        add index FK4763CA0473976E23 (userinfo_id), 
        add constraint FK4763CA0473976E23 
        foreign key (userinfo_id) 
        references userinfo (id);

    alter table user_catacts 
        add index FK7C0ED6836C146C11 (user_id), 
        add constraint FK7C0ED6836C146C11 
        foreign key (user_id) 
        references userinfo (id);

    alter table user_catacts 
        add index FK7C0ED6834DEBD61E (self_user_id), 
        add constraint FK7C0ED6834DEBD61E 
        foreign key (self_user_id) 
        references userinfo (id);

    alter table user_diy_graphic 
        add index FKA2E5796EE36BFFE3b013fbc9 (category_id), 
        add constraint FKA2E5796EE36BFFE3b013fbc9 
        foreign key (category_id) 
        references category (id);

    alter table user_diy_graphic 
        add index FKB013FBC96C146C11 (user_id), 
        add constraint FKB013FBC96C146C11 
        foreign key (user_id) 
        references userinfo (id);

    alter table user_diy_graphic 
        add index FKA2E5796ECDC4AB82b013fbc9 (holiday_type_id), 
        add constraint FKA2E5796ECDC4AB82b013fbc9 
        foreign key (holiday_type_id) 
        references holiday_type (id);

    alter table user_diy_graphic 
        add index FKA2E5796EEBB54FE8b013fbc9 (status_type_id), 
        add constraint FKA2E5796EEBB54FE8b013fbc9 
        foreign key (status_type_id) 
        references status_type (id);

    alter table user_favorites 
        add index FKF2FF4EE36C146C11 (user_id), 
        add constraint FKF2FF4EE36C146C11 
        foreign key (user_id) 
        references userinfo (id);

    alter table user_subscribe_graphic_item 
        add index FK10A9E5D36C146C11 (user_id), 
        add constraint FK10A9E5D36C146C11 
        foreign key (user_id) 
        references userinfo (id);

    alter table user_subscribe_record 
        add index FKECF0ADA6C146C11 (user_id), 
        add constraint FKECF0ADA6C146C11 
        foreign key (user_id) 
        references userinfo (id);

    alter table user_subscribe_record 
        add index FKECF0ADA9E7BF8A6 (subscribe_type_id), 
        add constraint FKECF0ADA9E7BF8A6 
        foreign key (subscribe_type_id) 
        references subscribe_type (id);

    alter table user_subscribe_type 
        add index FK44373C036C146C11 (user_id), 
        add constraint FK44373C036C146C11 
        foreign key (user_id) 
        references userinfo (id);

    alter table user_subscribe_type 
        add index FK44373C039E7BF8A6 (subscribe_type_id), 
        add constraint FK44373C039E7BF8A6 
        foreign key (subscribe_type_id) 
        references subscribe_type (id);

    create index user_phone_no on userinfo (phone_no);

    create index user_imsi on userinfo (imsi);
