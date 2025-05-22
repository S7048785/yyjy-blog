create table tag
(
    id          bigint auto_increment
        primary key,
    name        varchar(128)  null comment '标签名',
    create_time timestamp     null,
    del_flag    int default 0 null comment '删除标志（0代表未删除，1代表已删除）'
)
    comment '标签';

INSERT INTO blog.tag (id, name, create_time, del_flag) VALUES (1, 'Java', '2025-05-07 22:31:39', 0);
INSERT INTO blog.tag (id, name, create_time, del_flag) VALUES (2, 'SpringBoot', '2025-05-08 12:17:52', 0);
INSERT INTO blog.tag (id, name, create_time, del_flag) VALUES (3, 'kotlin', '2025-05-08 12:17:53', 0);
INSERT INTO blog.tag (id, name, create_time, del_flag) VALUES (4, 'Python', '2025-05-08 12:17:54', 0);
INSERT INTO blog.tag (id, name, create_time, del_flag) VALUES (5, 'TypeScript', '2025-05-14 00:41:05', 0);
