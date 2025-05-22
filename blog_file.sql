create table blog_file
(
    id          int auto_increment comment '文件id'
        primary key,
    file_url    varchar(255)           null comment '文件url',
    file_name   varchar(100)           not null comment '文件名',
    file_size   int         default 0  not null comment '文件大小',
    extend_name varchar(20) default '' not null comment '文件类型',
    file_path   varchar(255)           not null comment '文件路径',
    is_dir      tinyint(1)  default 0  not null comment '是否为目录 (0否 1是)',
    create_time datetime               not null comment '创建时间',
    update_time datetime               null comment '更新时间'
)
    row_format = DYNAMIC;

