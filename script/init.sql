/**create database if not exists sqlexercise;
use sqlexercise;**/
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`(
                      `id` varchar(255) not null ,
                      `email` varchar(255) not null unique,
                      `role` varchar(255) not null,
                      `name` varchar(255) not null,
                      `passhash` varchar(255) not null,
                      `created_at` datetime not null ,
                      `updated_at` datetime not null ,
                      `disabled` boolean,
                      primary key (`id`)
)engine = InnoDB  default charset = utf8;

DROP TABLE IF EXISTS `cache`;
CREATE TABLE `cache` (
  `id` int(11) auto_increment not null ,
  `code` varchar(255) not null ,
  `email` varchar(255) not null ,
  `created_at` datetime not null ,
  `expiry_at` datetime not null ,
  primary key (`id`)
)ENGINE = InnoDB auto_increment = 7 DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `pass_record`;
CREATE TABLE `pass_record`(
  `id` int(11) not null auto_increment,
  `user_id` varchar(255) not null ,
  `main_id` int(11) not null ,
  `sub_id` int(11) not null ,
  `batch_id` varchar(255) not null ,
  `point` int(11) not null ,
  `created_at` datetime not null ,
  `updataed_at` datetime not null ,
  primary key (`id`),
  foreign key (`main_id`) references sqlexercise.main_questions(id),
  foreign key (`sub_id`) references sqlexercise.sub_questions(id)
)engine = InnoDB auto_increment=1 default charset = utf8;

DROP TABLE IF EXISTS `batches`;
CREATE TABLE `batches`(
  `id` varchar(255) not null ,
  `batch_text` text not null,
  `user_id` varchar(255) not null,
  `created_at` datetime not null,
  `updated_at` datetime not null ,
  `main_id` int(11) not null ,
  `sub_id` int(11) not null ,
  primary key (`id`),
  foreign key (`main_id`) references sqlexercise.main_questions(id),
  foreign key (`sub_id`) references sqlexercise.sub_questions(id)
)engine = InnoDB default charset = utf8;

