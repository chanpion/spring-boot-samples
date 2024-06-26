CREATE TABLE `entity`
(
    `id`         bigint      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `uid`        varchar(32) NOT NULL COMMENT '唯一id',
    `label`      varchar(50) NULL COMMENT '类型',
    `properties` longtext    NULL COMMENT '属性map，json格式'
);

create index `idx_entity_uid` on `entity` (`uid`);
create index `idx_entity_label` on `entity` (`label`);

CREATE TABLE `relation`
(
    `id`         bigint      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `uid`        varchar(32) NOT NULL COMMENT '唯一id',
    `label`      varchar(50) NULL COMMENT '类型',
    `start_id`   varchar(32) NULL COMMENT '关联起始id',
    `end_id`     varchar(32) NULL COMMENT '关联结束id',
    `properties` longtext    NULL COMMENT '属性map，json格式'
);

create index `idx_relation_uid` on `relation` (`uid`);
create index `idx_relation_label` on `relation` (`label`);
create index `idx_start_end_label` on `relation` (`start_id`, `end_id`, `label`);