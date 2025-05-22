create table friend
(
    id           int auto_increment comment '友链id'
        primary key,
    name         varchar(20)  not null comment '友链名称',
    color        varchar(20)  not null comment '友链颜色',
    avatar       varchar(255) not null comment '友链头像',
    url          varchar(50)  not null comment '友链地址',
    introduction varchar(100) not null comment '友链介绍',
    create_time  datetime     not null comment '创建时间',
    update_time  datetime     null comment '更新时间'
)
    row_format = DYNAMIC;

create index friend_user
    on friend (name);

