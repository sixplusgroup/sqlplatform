create database if not exists sqlexercise;
use sqlexercise;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `id`           varchar(255) not null,
    `email`        varchar(255) not null unique,
    `role`         varchar(255) not null,
    `name`         varchar(255) not null,
    `passwordHash` varchar(255) not null,
    `created_at`   datetime     not null,
    `updated_at`   datetime     not null,
    `disabled`     boolean,
    primary key (`id`)
) engine = InnoDB
  default charset = utf8;

DROP TABLE IF EXISTS `pass_record`;
CREATE TABLE `pass_record`
(
    `id`         int(11)      not null auto_increment,
    `user_id`    varchar(255) not null,
    `main_id`    int(11)      not null,
    `sub_id`     int(11)      not null,
    `batch_id`   varchar(255) not null,
    `point`      int(11)      not null,
    `created_at` datetime     not null,
    `updated_at` datetime     not null,
    primary key (`id`),
    foreign key (`main_id`) references sqlexercise.main_question (id),
    foreign key (`sub_id`) references sqlexercise.sub_question (id)
) engine = InnoDB
  auto_increment = 1
  default charset = utf8;

DROP TABLE IF EXISTS `batch`;
CREATE TABLE `batch`
(
    `id`         varchar(255) not null,
    `batch_text` text         not null,
    `user_id`    varchar(255) not null,
    `created_at` datetime     not null,
    `updated_at` datetime     not null,
    `main_id`    int(11)      not null,
    `sub_id`     int(11)      not null,
    primary key (`id`),
    foreign key (`main_id`) references sqlexercise.main_question (id),
    foreign key (`sub_id`) references sqlexercise.sub_question (id)
) engine = InnoDB
  default charset = utf8;

drop table if exists draft;
create table draft
(
    id        int          not null auto_increment,
    user_id   varchar(255) not null,
    main_id   int          not null,
    sub_id    int          not null,
    draft     text,
    save_time datetime,
    primary key (id),
    constraint fk_draft_user_id foreign key (user_id) references sqlexercise.users (id) on delete cascade on update cascade,
    constraint fk_draft_main_id foreign key (main_id) references sqlexercise.main_question (id) on delete cascade on update cascade,
    constraint fk_draft_sub_id foreign key (sub_id) references sqlexercise.sub_question (id) on delete cascade on update cascade
) engine = innodb,
  default charset = utf8;

drop table if exists question_state;
create table question_state
(
    id         int          not null auto_increment,
    user_id    varchar(255) not null,
    main_id    int          not null,
    sub_id     int          not null,
    is_starred tinyint(1)      not null comment '是否被收藏，1-是，0-否',
    state      tinyint      not null comment '题目状态，0-未开始，1-提交未通过，2-已通过',
    primary key (id),
    constraint fk_user_id foreign key (user_id) references sqlexercise.users (id) on delete CASCADE on update cascade,
    constraint fk_main_id foreign key (main_id) references sqlexercise.main_question (id) on delete cascade on update cascade,
    constraint fk_sub_id foreign key (sub_id) references sqlexercise.sub_question (id) on delete cascade on update cascade
) engine = innodb,
  default charset = utf8;