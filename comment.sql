create table comment
(
    id              bigint auto_increment
        primary key,
    article_id      bigint            null comment '文章id',
    content         varchar(512)      null comment '评论内容',
    nick_name       char(255)         null,
    ip_address      char(255)         null comment '创建该评论的ip',
    reply_nick_name char(255)         null,
    parent_id       bigint            null comment '父评论id',
    root_parent_id  bigint            null comment '根评论id',
    create_time     timestamp         null comment '创建时间',
    is_author       tinyint default 0 null,
    del_flag        int     default 0 null comment '删除标志（0代表未删除，1代表已删除）'
)
    comment '评论表';

create index idx_article_id
    on comment (article_id);

INSERT INTO blog.comment (id, article_id, content, nick_name, ip_address, reply_nick_name, parent_id, root_parent_id, create_time, is_author, del_flag) VALUES (1, 1, '很好', '匿名1', '江苏', null, null, null, '2025-05-08 12:53:06', 0, 0);
INSERT INTO blog.comment (id, article_id, content, nick_name, ip_address, reply_nick_name, parent_id, root_parent_id, create_time, is_author, del_flag) VALUES (2, 1, '目前kotlin在国内开发不太流行,虽然安卓开发指定了kotlin,但是安卓开发岗位现在真的很少.仅仅是出于学习目的来学是没问题的,如果是为了就业,我不太推荐.
但是kotlin写起来就是爽!', '陈九', '江苏', null, null, null, '2025-05-11 21:29:13', 1, 0);
INSERT INTO blog.comment (id, article_id, content, nick_name, ip_address, reply_nick_name, parent_id, root_parent_id, create_time, is_author, del_flag) VALUES (3, 1, '继续努力', '张三', '江苏', '陈九', null, 2, '2025-05-10 21:29:14', 0, 0);
INSERT INTO blog.comment (id, article_id, content, nick_name, ip_address, reply_nick_name, parent_id, root_parent_id, create_time, is_author, del_flag) VALUES (4, 1, '测试1', '陈九', '江苏', '陈九', null, 2, '2025-05-10 23:02:05', 1, 0);
INSERT INTO blog.comment (id, article_id, content, nick_name, ip_address, reply_nick_name, parent_id, root_parent_id, create_time, is_author, del_flag) VALUES (5, 1, '测试2', '测试账号', '江苏', '张三', 3, 2, '2025-05-10 23:02:40', 0, 0);
INSERT INTO blog.comment (id, article_id, content, nick_name, ip_address, reply_nick_name, parent_id, root_parent_id, create_time, is_author, del_flag) VALUES (6, 1, '测试3', '李四', '江苏', null, null, null, '2025-05-16 23:25:02', 0, 0);
INSERT INTO blog.comment (id, article_id, content, nick_name, ip_address, reply_nick_name, parent_id, root_parent_id, create_time, is_author, del_flag) VALUES (7, 1, 'asd', '石落u_U', '青海', null, null, null, '2025-05-17 15:34:46', 0, 0);
INSERT INTO blog.comment (id, article_id, content, nick_name, ip_address, reply_nick_name, parent_id, root_parent_id, create_time, is_author, del_flag) VALUES (8, 1, 'fdg', '江南一只鱼', '福建', null, null, null, '2025-05-17 15:34:46', 0, 0);
INSERT INTO blog.comment (id, article_id, content, nick_name, ip_address, reply_nick_name, parent_id, root_parent_id, create_time, is_author, del_flag) VALUES (9, 1, 'rtsryj', '叛逆的苏打君', '四川', null, null, null, '2025-05-17 15:34:47', 0, 0);
INSERT INTO blog.comment (id, article_id, content, nick_name, ip_address, reply_nick_name, parent_id, root_parent_id, create_time, is_author, del_flag) VALUES (10, 1, 'fgnt', '小辉code', '河北', null, null, null, '2025-05-17 15:34:49', 0, 0);
INSERT INTO blog.comment (id, article_id, content, nick_name, ip_address, reply_nick_name, parent_id, root_parent_id, create_time, is_author, del_flag) VALUES (11, 1, 'yul7t', '努力的琳琳记', '河南', null, null, null, '2025-05-17 15:34:50', 0, 0);
INSERT INTO blog.comment (id, article_id, content, nick_name, ip_address, reply_nick_name, parent_id, root_parent_id, create_time, is_author, del_flag) VALUES (15, 2, '111', 'abcd', '未知', null, null, null, '2025-05-18 16:55:29', 0, 0);
INSERT INTO blog.comment (id, article_id, content, nick_name, ip_address, reply_nick_name, parent_id, root_parent_id, create_time, is_author, del_flag) VALUES (16, 2, '111', 'abcd', '未知', null, null, null, '2025-05-18 16:55:57', 0, 0);
INSERT INTO blog.comment (id, article_id, content, nick_name, ip_address, reply_nick_name, parent_id, root_parent_id, create_time, is_author, del_flag) VALUES (17, 1, 'asd', 'asd', '未知', '匿名1', null, 1, '2025-05-18 22:28:24', 0, 0);
INSERT INTO blog.comment (id, article_id, content, nick_name, ip_address, reply_nick_name, parent_id, root_parent_id, create_time, is_author, del_flag) VALUES (18, 1, 'aaaaa', '3333', '未知', null, null, null, '2025-05-18 23:01:04', 0, 0);
INSERT INTO blog.comment (id, article_id, content, nick_name, ip_address, reply_nick_name, parent_id, root_parent_id, create_time, is_author, del_flag) VALUES (19, 1, 'hello', '111', '未知', '陈九', 4, 2, '2025-05-18 23:01:05', 0, 0);
INSERT INTO blog.comment (id, article_id, content, nick_name, ip_address, reply_nick_name, parent_id, root_parent_id, create_time, is_author, del_flag) VALUES (20, 1, '测试5', 'hhh', '未知', '李四', null, 6, '2025-05-18 23:18:07', 0, 0);
INSERT INTO blog.comment (id, article_id, content, nick_name, ip_address, reply_nick_name, parent_id, root_parent_id, create_time, is_author, del_flag) VALUES (21, 1, 'pdd', 'ww', '未知', '石落u_U', null, 7, '2025-05-18 23:23:15', 0, 0);
INSERT INTO blog.comment (id, article_id, content, nick_name, ip_address, reply_nick_name, parent_id, root_parent_id, create_time, is_author, del_flag) VALUES (22, 1, 'rr', '467', '未知', '石落u_U', null, 7, '2025-05-18 23:25:33', 0, 0);
