/* script to be run once database and tables created by hibernate. password=Password@2024 for both */

insert into users(id, full_name, user_name, password, role) values (3, "Administrator", "admin" , "$2a$10$1eUK22LR4Es5rHqbk9D38ep4yLTCdeEpbgxhsoq0E/lfmYX6TOF2W", "ADMIN");
insert into users(id, full_name, user_name, password, role) values (4, "User", "user", "$2a$10$32KGl5vkND5.mkubYWBzg.EsfkohC761GqIlKv0S2kQtpyCWxvifu", "USER");
commit;