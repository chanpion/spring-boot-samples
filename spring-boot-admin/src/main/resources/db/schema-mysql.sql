CREATE TABLE IF NOT EXISTS `entity`
(
    `id`         bigint UNSIGNED NOT NULL AUTO_INCREMENT,
    `uid`        varchar(32)     NOT NULL COMMENT '唯一id',
    `label`      varchar(50)     NULL COMMENT '类型',
    `properties` longtext        NULL COMMENT '属性map，json格式',
    PRIMARY KEY (`id`),
    KEY `idx_uid` (`uid`),
    KEY `idx_label` (`label`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `relation`
(
    `id`         bigint UNSIGNED NOT NULL AUTO_INCREMENT,
    `uid`        varchar(32)     NOT NULL COMMENT '唯一id',
    `label`      varchar(50)     NULL COMMENT '类型',
    `start_id`   varchar(32)     NULL COMMENT '关联起始id',
    `end_id`     varchar(32)     NULL COMMENT '关联结束id',
    `properties` longtext        NULL COMMENT '属性map，json格式',
    PRIMARY KEY (`id`),
    KEY `idx_uid` (`uid`),
    KEY `idx_start_end_label` (`start_id`,`end_id`,`label`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;