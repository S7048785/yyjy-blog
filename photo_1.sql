create table photo
(
    id          int auto_increment comment '照片id'
        primary key,
    album_id    int          not null comment '相册id',
    photo_name  varchar(20)  not null comment '照片名',
    photo_desc  varchar(50)  null comment '照片描述',
    photo_url   varchar(255) not null comment '照片链接',
    create_time datetime     not null comment '创建时间',
    update_time datetime     null comment '更新时间'
)
    row_format = DYNAMIC;

