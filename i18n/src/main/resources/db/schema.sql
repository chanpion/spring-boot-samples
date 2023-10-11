drop table if exists config_i18n;
create table config_i18n
(
    id       int not null auto_increment,
    model    varchar(50) comment '模块，类型',
    model_id int comment '模块id',
    name     varchar(255) comment '键名',
    text     varchar(1024) comment '值',
    language varchar(255) comment '对应的语言',
    primary key (id)
) comment '国际化配置数据库';