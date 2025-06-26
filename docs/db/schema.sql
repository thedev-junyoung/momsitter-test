create table momsitter.users
(
    active_role tinyint                 not null,
    birth_date  date                    not null,
    created_at  datetime(6)             null,
    id          bigint auto_increment
        primary key,
    updated_at  datetime(6)             null,
    email       varchar(255)            not null,
    name        varchar(255)            not null,
    password    varchar(255)            not null,
    username    varchar(255)            not null,
    gender      enum ('FEMALE', 'MALE') not null,
    constraint UK6dotkott2kjsp8vw4d0m25fb7
        unique (email),
    constraint UKr43af9ap4edm43mmtq01oddj6
        unique (username),
    check (`active_role` between 0 and 2)
);

create table momsitter.parent_profiles
(
    created_at datetime(6) null,
    id         bigint auto_increment
        primary key,
    updated_at datetime(6) null,
    user_id    bigint      not null,
    constraint UK1kwvop6254by3rrqmfh5w2oq5
        unique (user_id),
    constraint FKhx9yojtdt6tocy5d7cbbst0jr
        foreign key (user_id) references momsitter.users (id)
);

create table momsitter.care_requests
(
    created_at        datetime(6)                                          null,
    id                bigint auto_increment
        primary key,
    parent_profile_id bigint                                               not null,
    updated_at        datetime(6)                                          null,
    content           text                                                 not null,
    status            enum ('ACTIVE', 'CANCELLED', 'COMPLETED', 'MATCHED') not null,
    constraint FKogcxomkq40ojlduexadmty4u9
        foreign key (parent_profile_id) references momsitter.parent_profiles (id)
);

create table momsitter.children
(
    birth_date        date                    not null,
    created_at        datetime(6)             null,
    id                bigint auto_increment
        primary key,
    parent_profile_id bigint                  not null,
    updated_at        datetime(6)             null,
    name              varchar(255)            not null,
    gender            enum ('FEMALE', 'MALE') not null,
    constraint FKn0ww2c465x6ubtaqgnf6cw0no
        foreign key (parent_profile_id) references momsitter.parent_profiles (id)
);

create table momsitter.sitter_profiles
(
    max_care_age int         not null,
    min_care_age int         not null,
    created_at   datetime(6) null,
    id           bigint auto_increment
        primary key,
    updated_at   datetime(6) null,
    user_id      bigint      not null,
    introduction text        not null,
    constraint UKkf8leb8y8mg2owklk6qx4gxkx
        unique (user_id),
    constraint FKkce5a4lidna0nwrcjb99jrysr
        foreign key (user_id) references momsitter.users (id)
);

create table momsitter.user_roles
(
    id      bigint auto_increment
        primary key,
    user_id bigint                               not null,
    role    enum ('DEFAULT', 'PARENT', 'SITTER') not null,
    constraint FKhfh9dx7w3ubf1co1vdev94g3f
        foreign key (user_id) references momsitter.users (id)
);

