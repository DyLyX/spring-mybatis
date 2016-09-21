CREATE DATABASE IF NOT EXISTS `db_rzx` default charset utf8 COLLATE utf8_general_ci;

USE `db_rzx`;

-- 
DROP TABLE IF EXISTS dcUser;
DROP TABLE IF EXISTS systemParam;
DROP TABLE IF EXISTS mail;
DROP TABLE IF EXISTS address;
-- ====================================

CREATE TABLE `dcUser` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL COMMENT '用户名',
  `nickname` varchar(64) NOT NULL COMMENT '昵称',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `sex` int(11) DEFAULT '0' COMMENT '性别：0未知，1男，2女',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE` (`name`)
) ENGINE=InnoDB COMMENT='系统用户信息表';

CREATE TABLE systemParam (
  id bigint(11) AUTO_INCREMENT,
  `type` tinyint(1) DEFAULT NULL,
  displayName varchar(100) DEFAULT NULL COMMENT '展示名称',
  name varchar(100) NOT NULL COMMENT '参数名称',
  `value` varchar(1000) NOT NULL COMMENT '参数值',
  isShow tinyint(1) DEFAULT '0' COMMENT '是否展示,默认不显示',
  description varchar(1000) DEFAULT NULL COMMENT '参数说明',
  updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  createTime timestamp NOT NULL DEFAULT '1971-01-01 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY udx_name (name)
) ENGINE=InnoDB COMMENT='系统参数表';
CREATE TABLE mail (
  id_mail bigint(11) AUTO_INCREMENT,
  subject varchar(50) NOT NULL COMMENT '发送主题',
  sender varchar(10) NOT NULL COMMENT '发送者名称',
  content varchar(1000) NOT NULL COMMENT '内容',
  createTime timestamp NOT NULL DEFAULT '1971-01-01 00:00:00' COMMENT '创建时间',
  form_address varchar(50) NOT NULL COMMENT '发送方地址',
  send_address varchar(50) NOT NULL COMMENT '接受方地址',
  PRIMARY KEY (id_mail)
) ENGINE=InnoDB COMMENT='邮件信息表';
CREATE TABLE address (
  id_address bigint(11) AUTO_INCREMENT,
  id_mail bigint(11) NOT NULL,
  name varchar(50) NOT NULL COMMENT '名称',
  relative_path varchar(100) NOT NULL COMMENT '附件路径',
  createTime timestamp NOT NULL DEFAULT '1971-01-01 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (id_address),
  UNIQUE KEY `UNIQUE` (`name`),
  KEY id_mail (id_mail),
  CONSTRAINT `dcMailAddress_ibfk_1` FOREIGN KEY (id_mail) REFERENCES mail (id_mail)
) ENGINE=InnoDB COMMENT='附件信息表';
