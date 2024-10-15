create database if not exists chi_pub;

use chi_pub;

create table if not exists author (
    aid int primary key,
    first_name varchar(510),
    last_name varchar(510) not null,
    about text
);

create table if not exists genre (
    gid int primary key,
    name varchar(127)
);

create table if not exists subject (
    sid int primary key,
    name varchar(255)
);

create table if not exists language (
    lid int primary key,
    name varchar(255)
);

create table if not exists book (
    bid int primary key,
    isbn char(13) not null,
    title varchar(255) not null,
    genre_id int,
    fiction bit(1), -- single bit bool repr, 0 is false, 1 is true
    edition varchar(127),
    pub_date date,
    foreign key (genre_id)
        references genre(gid)
        on delete set null
);

create table if not exists author_book (
    author_id int not null,
    book_id int not null,
    primary key (author_id, book_id),
    foreign key (author_id)
        references author(aid)
        on delete cascade,
    foreign key (book_id)
        references book(bid)
        on delete cascade
);

create table if not exists subject_book (
    book_id int,
    subject_id int,
    primary key (subject_id, book_id),
    foreign key (book_id)
        references book(bid)
        on delete cascade,
    foreign key (subject_id)
        references subject(sid)
        on delete cascade
);

create table if not exists language_book (
    book_id int,
    language_id int,
    primary key (language_id, book_id),
    foreign key (book_id)
        references book(bid)
        on delete cascade,
    foreign key (language_id)
        references language(lid)
        on delete cascade
);

create table if not exists cardholder (
    chid int primary key,
    card_num int not null,
    first_name varchar(510),
    last_name varchar(510) not null,
    password varchar(127),
    addr_num smallint not null,
    addr_street varchar(255) not null,
    addr_apt varchar(31),
    addr_city varchar(127) not null,
    addr_state varchar(127) not null,
    addr_zip smallint not null,
    email varchar(255) not null
);

create table if not exists cardholder_phone (
    cardholder_id int not null,
    phone_number char(10) not null,
    primary key (cardholder_id, phone_number),
    foreign key (cardholder_id)
        references cardholder(chid)
        on delete cascade
);

create table if not exists book_cardholder (
    timestamp timestamp not null,
    book_id int not null,
    cardholder_id int not null,
    status enum('pending', 'fulfilled', 'cancelled') not null, -- this will default to 'pending' if no value is given
    primary key (timestamp, book_id, cardholder_id),
    foreign key (book_id)
        references book(bid)
        on delete cascade,
    foreign key (cardholder_id)
        references cardholder(chid)
        on delete cascade
);

create table if not exists branch (
    brid int primary key,
    name varchar(255) not null,
    addr_num smallint not null,
    addr_street varchar(255) not null,
    addr_apt varchar(31),
    addr_city varchar(127) not null,
    addr_state varchar(127) not null,
    addr_zip smallint not null
);

create table if not exists copy (
    cid int primary key,
    book_id int not null,
    branch_id int not null,
    foreign key (book_id)
        references book(bid)
        on delete cascade,
    foreign key (branch_id)
        references branch(brid)
        on delete cascade
);

create table if not exists cardholder_copy (
    copy_id int not null,
    cardholder_id int not null,
    checked_out timestamp not null,
    checked_in timestamp,
    primary key (copy_id, cardholder_id),
    foreign key (copy_id)
        references copy(cid)
        on delete cascade,
    foreign key (cardholder_id)
        references cardholder(chid)
        on delete cascade
);
