create table category
(
    id          bigint auto_increment
        primary key,
    name        varchar(128)     null comment '分类名',
    status      char default '0' null comment '状态0:正常,1禁用',
    create_time timestamp        null,
    del_flag    int  default 0   null comment '删除标志（0代表未删除，1代表已删除）'
)
    comment '分类表';

INSERT INTO blog.category (id, name, status, create_time, del_flag) VALUES (1, '技术', '0', '2025-05-06 22:46:56', 0);
INSERT INTO blog.category (id, name, status, create_time, del_flag) VALUES (2, '生活', '0', '2025-05-06 22:48:06', 0);
INSERT INTO blog.category (id, name, status, create_time, del_flag) VALUES (3, '日志', '0', '2025-05-06 22:48:08', 0);
INSERT INTO blog.category (id, name, status, create_time, del_flag) VALUES (4, '项目', '0', '2025-05-06 22:48:09', 0);
INSERT INTO blog.category (id, name, status, create_time, del_flag) VALUES (5, '其他', '0', '2025-05-07 18:17:37', 0);
