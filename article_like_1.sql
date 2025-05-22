create table article_like
(
    id         bigint auto_increment
        primary key,
    article_id int           not null,
    user_ip    char(255)     not null,
    del_flag   int default 0 null
);

create index article_like_article_id_index
    on article_like (article_id);

INSERT INTO blog.article_like (id, article_id, user_ip, del_flag) VALUES (1, 1, '10.23.45.213', 0);
INSERT INTO blog.article_like (id, article_id, user_ip, del_flag) VALUES (2, 1, '0:0:0:0:0:0:0:1', 0);
INSERT INTO blog.article_like (id, article_id, user_ip, del_flag) VALUES (5, 5, '0:0:0:0:0:0:0:1', 0);
INSERT INTO blog.article_like (id, article_id, user_ip, del_flag) VALUES (6, 4, '0:0:0:0:0:0:0:1', 0);
INSERT INTO blog.article_like (id, article_id, user_ip, del_flag) VALUES (7, 3, '0:0:0:0:0:0:0:1', 0);
INSERT INTO blog.article_like (id, article_id, user_ip, del_flag) VALUES (9, 1, '122.192.90.45', 0);
INSERT INTO blog.article_like (id, article_id, user_ip, del_flag) VALUES (10, 5, '223.104.150.223', 0);
INSERT INTO blog.article_like (id, article_id, user_ip, del_flag) VALUES (11, 4, '223.104.150.223', 0);
INSERT INTO blog.article_like (id, article_id, user_ip, del_flag) VALUES (12, 3, '223.104.150.223', 0);
