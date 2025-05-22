create table comment
(
    id              bigint auto_increment
        primary key,
    article_id      bigint            null comment '文章id',
    content         varchar(512)      null comment '评论内容',
    nick_name       char(255)         null,
    ip_address      char(255)         null comment '创建该评论的ip',
    ip              char(255)         null,
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

INSERT INTO blog.comment (id, article_id, content, nick_name, ip_address, ip, reply_nick_name, parent_id, root_parent_id, create_time, is_author, del_flag) VALUES (2, 1, '目前kotlin在国内开发不太流行,虽然安卓开发指定了kotlin,但是安卓开发岗位现在真的很少.仅仅是出于学习目的来学是没问题的,如果是为了就业,我不太推荐.
但是kotlin写起来就是爽!', '陈九', '江苏', null, null, null, null, '2025-05-11 21:29:13', 1, 0);
INSERT INTO blog.comment (id, article_id, content, nick_name, ip_address, ip, reply_nick_name, parent_id, root_parent_id, create_time, is_author, del_flag) VALUES (25, 5, '棒！👍', '一位不知名小姐', '未知', null, null, null, null, '2025-05-20 08:33:54', 0, 0);
INSERT INTO blog.comment (id, article_id, content, nick_name, ip_address, ip, reply_nick_name, parent_id, root_parent_id, create_time, is_author, del_flag) VALUES (27, 5, '😎', '陈九', '未知', '0:0:0:0:0:0:0:1', '一位不知名小姐', null, 25, '2025-05-20 17:36:58', 1, 0);
