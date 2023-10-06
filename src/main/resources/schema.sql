drop table if exists rating cascade;
create table if not exists rating
(
    mpa_id INTEGER not null primary key,
    mpa_name varchar(255)
);

drop table if exists genre cascade;
create table if not exists genre
(
    genre_id bigint not null primary key,
    genre_name varchar(255)
);




drop table if exists users cascade;
create table if not exists users
(
    user_id bigint not null primary key auto_increment,
    user_name varchar(255),
    login varchar(255),
    email varchar(255),
    birthday varchar(255)
);
drop sequence if exists user_sequence;
create sequence if not exists user_sequence START with 1 minvalue 1 increment by 1;


drop table if exists films cascade;
create table if not exists films
(
    film_id INTEGER not null primary key auto_increment,
    film_name varchar(255),
    description varchar(255),
    duration bigint not null,
    release_date timestamp,
    mpa_id INTEGER NOT NULL,
    foreign key (mpa_id) references rating(mpa_id) ON DELETE CASCADE
);
drop sequence if exists film_sequence;
create sequence if not exists film_sequence START with 1 minvalue 1 increment by 1;


drop table if exists film_genre cascade;
create table if not exists film_genre
(
    film_id INTEGER NOT NULL,
    genre_id INTEGER NOT NULL,
    PRIMARY KEY (film_id,genre_id),
    foreign key (film_id) references films(film_id) ON DELETE CASCADE,
    foreign key (genre_id) references genre(genre_id) ON DELETE CASCADE
);

drop table if exists friendship cascade;
create table if not exists friendship
(
    user_id INTEGER NOT NULL,
    friend_id INTEGER NOT NULL,
    state_of_friendship BOOLEAN NOT NULL,
    PRIMARY KEY(user_id, friend_id),
    FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY(friend_id) REFERENCES users(user_id) ON DELETE CASCADE
);

drop table if exists film_like cascade;
create table if not exists film_like
(
    film_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    PRIMARY KEY(film_id, user_id),
    FOREIGN KEY(film_id) REFERENCES films(film_id) ON DELETE CASCADE,
    FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE CASCADE
);