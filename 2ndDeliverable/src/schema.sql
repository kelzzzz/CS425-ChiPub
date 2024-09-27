create database if not exists chi_pub;

use chi_pub;

create table if not exists author (
    aid uuid primary key,
    first_name varchar(510),
    last_name varchar(510) not null,
    about text
);

create table if not exists genre (
    gid uuid primary key,
    name vachar(127)
);

create table if not exists subject (
    sid uuid primary key,
    name varchar(255)
);

create table if not exists language (
    lid uuid primary key,
    name varchar(255)
);

create table if not exists book (
    bid uuid primary key, 
    isbn char(13) not null, 
    title varchar(255) not null, 
    genre_id uuid, 
    fiction bool, 
    edition varchar(127), 
    pub_date date,
    foreign key (genre_id)
        references genre(gid)
        on delete set null
);

create table if not exists author_book (
    author_id uuid not null,
    book_id uuid not null,
    primary key (author_id, book_id),
    foreign key (author_id)
        references author(aid)
        on delete cascade,
    foreign key (book_id)
        references book(bid)
        on delete cascade
);

create table if not exists subject_book (
    book_id uuid,
    subject_id uuid,
    primary key (subject_id, book_id),
    foreign key (book_id)
        references book(bid)
        on delete cascade,
    foreign key (subject_id)
        references subject(sid)
        on delete cascade
);

create table if not exists language_book (
    book_id uuid,
    language_id uuid,
    primary key (language_id, book_id),
    foreign key (book_id)
        references book(bid)
        on delete cascade,
    foreign key (language_id)
        references language(lid)
        on delete cascade
);

create table if not exists cardholder (
    chid uuid primary key,
    card_num uuid not null,
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
    cardholder_id uuid not null,
    phone_number char(10) not null,
    primary key (cardholder_id, phone_number),
    foreign key (cardholder_id)
        references cardholder(chid)
        on delete cascade
);

create table if not exists book_cardholder (
    timestamp timestamp not null,
    book_id uuid not null,
    cardholder_id uuid not null,
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
    brid uuid primary key,
    name varchar(255) not null,
    addr_num smallint not null,
    addr_street varchar(255) not null,
    addr_apt varchar(31),
    addr_city varchar(127) not null,
    addr_state varchar(127) not null,
    addr_zip smallint not null,
);

create table if not exists copy (
    cid uuid primary key,
    book_id uuid uuid not null,
    branch_id uuid not null,
    foreign key (book_id)
        references book(bid)
        on delete cascade,
    foreign key (branch_id)
        references branch(brid)
        on delete cascade
);

create table if not exists cardholder_copy (
    copy_id uuid not null,
    cardholder_id uuid not null,
    out timestamp not null,
    in timestamp,
    primary key (copy_id, cardholder_id),
    foreign key (copy_id)
        references copy(cid)
        on delete cascade,
    foreign key (cardholder_id)
        references cardholder(chid)
        on delete cascade
);
