create table article_tag
(
    id         int auto_increment comment '主键'
        primary key,
    article_id int not null comment '文章id',
    tag_id     int not null comment '标签id'
)
    row_format = DYNAMIC;

INSERT INTO blog.article_tag (id, article_id, tag_id) VALUES (1, 1, 5);
INSERT INTO blog.article_tag (id, article_id, tag_id) VALUES (2, 2, 1);
INSERT INTO blog.article_tag (id, article_id, tag_id) VALUES (3, 2, 2);
INSERT INTO blog.article_tag (id, article_id, tag_id) VALUES (4, 2, 3);
