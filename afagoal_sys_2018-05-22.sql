# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.19)
# Database: afagoal_sys
# Generation Time: 2018-05-22 06:44:25 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table bc_token
# ------------------------------------------------------------

DROP TABLE IF EXISTS `bc_token`;

CREATE TABLE `bc_token` (
  `id` varchar(40) NOT NULL DEFAULT '' COMMENT 'ID',
  `token_name` varchar(100) DEFAULT '' COMMENT '货币名称',
  `token_code` varchar(40) DEFAULT '' COMMENT '货币编码',
  `total_supply` int(20) DEFAULT '0' COMMENT '币种总量',
  `holders` int(20) DEFAULT '0' COMMENT '持有人数',
  `transfers` int(20) DEFAULT '0' COMMENT '交易次数',
  `decimals` tinyint(4) DEFAULT '0' COMMENT '精确位数',
  `contract` varchar(200) DEFAULT '' COMMENT '合同编号',
  `country` varchar(40) DEFAULT '' COMMENT '发布国家',
  `overview` varchar(500) DEFAULT '' COMMENT '简介',
  `highest_price` decimal(20,10) DEFAULT '0.0000000000' COMMENT '历史最高价格',
  `lowest_price` decimal(20,10) DEFAULT '0.0000000000' COMMENT '历史最低价格',
  `highest_transaction` int(20) DEFAULT '0' COMMENT '历史最高交易量',
  `lowest_transaction` int(20) DEFAULT '0' COMMENT '历史最低交易量',
  `ico_start_date` date DEFAULT NULL COMMENT 'ICO开始日期',
  `ico_end_date` date DEFAULT NULL COMMENT 'ico结束日期',
  `ico_price` decimal(20,10) DEFAULT '0.0000000000' COMMENT 'ico价格',
  `total_cap` decimal(20,10) DEFAULT '0.0000000000' COMMENT 'ICO上限',
  `total_raised` decimal(20,10) DEFAULT '0.0000000000' COMMENT 'ICO总值',
  `state` tinyint(4) DEFAULT '0' COMMENT '状态 0－有效 99-删除',
  `weight` tinyint(4) DEFAULT '0' COMMENT '权重',
  `version` int(11) DEFAULT '0' COMMENT '版本号',
  `created_at` datetime DEFAULT NULL COMMENT ' 创建时间',
  `created_by` varchar(40) DEFAULT '' COMMENT '创建人',
  `updated_at` datetime DEFAULT NULL COMMENT ' 修改时间',
  `updated_by` varchar(40) DEFAULT '' COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `inx_token_code` (`token_code`) USING BTREE,
  UNIQUE KEY `inx_token_name` (`token_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='虚拟货币表';

LOCK TABLES `bc_token` WRITE;
/*!40000 ALTER TABLE `bc_token` DISABLE KEYS */;

INSERT INTO `bc_token` (`id`, `token_name`, `token_code`, `total_supply`, `holders`, `transfers`, `decimals`, `contract`, `country`, `overview`, `highest_price`, `lowest_price`, `highest_transaction`, `lowest_transaction`, `ico_start_date`, `ico_end_date`, `ico_price`, `total_cap`, `total_raised`, `state`, `weight`, `version`, `created_at`, `created_by`, `updated_at`, `updated_by`)
VALUES
	('1','测试','ceshi',1000000,1000000,387971937,8,'znlchasald','中国','测试',18.1231230000,15.0000000000,90000,1000,'2018-05-11','2018-05-11',12.8979000000,78778681.1200000000,13123123.2100000000,0,10,0,'2018-05-11 00:00:00','','2018-05-11 00:00:00','');

/*!40000 ALTER TABLE `bc_token` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table bc_token_detail
# ------------------------------------------------------------

DROP TABLE IF EXISTS `bc_token_detail`;

CREATE TABLE `bc_token_detail` (
  `id` varchar(40) NOT NULL DEFAULT '' COMMENT 'ID',
  `bc_token_name` varchar(100) DEFAULT '' COMMENT '货币名称',
  `bc_token_code` varchar(40) DEFAULT '' COMMENT '货币编码',
  `bc_token_id` varchar(40) DEFAULT '' COMMENT '货币id',
  `price` decimal(20,10) DEFAULT '0.0000000000' COMMENT '今日价格',
  `price_change` decimal(4,2) DEFAULT '0.00' COMMENT '价格浮动比例',
  `volume` int(20) DEFAULT '0' COMMENT '矿池剩余容量',
  `statistic_time` datetime DEFAULT NULL COMMENT '统计时间',
  `total_value` decimal(20,10) DEFAULT '0.0000000000' COMMENT '币种总价值',
  `usd_profitability` decimal(10,4) DEFAULT '0.0000' COMMENT 'USD盈利率',
  `usd` decimal(10,5) DEFAULT '0.00000' COMMENT 'USD税率',
  `eth_profitability` decimal(10,4) DEFAULT '0.0000' COMMENT 'USD盈利率',
  `eth` decimal(10,5) DEFAULT '0.00000' COMMENT 'ETH税率',
  `circulating_supply` decimal(20,4) DEFAULT '0.0000' COMMENT '今日流动量',
  `today_transaction` int(20) DEFAULT '0' COMMENT '今日交易额',
  `state` tinyint(4) DEFAULT '0' COMMENT '状态 0－有效 99-删除',
  `version` int(11) DEFAULT '0' COMMENT '版本号',
  `created_at` datetime DEFAULT NULL COMMENT ' 创建时间',
  `created_by` varchar(40) DEFAULT '' COMMENT '创建人',
  `updated_at` datetime DEFAULT NULL COMMENT ' 修改时间',
  `updated_by` varchar(40) DEFAULT '' COMMENT '修改员工ID',
  PRIMARY KEY (`id`),
  KEY `IDX_TOKEN_DETAIL_TOKEN_CODE` (`bc_token_code`) USING BTREE,
  KEY `IDX_TOKEN_DETAIL_TOKEN_ID` (`bc_token_id`) USING BTREE,
  KEY `IDX_TOKEN_TOP_HOLDER_STATISTIC_TIME` (`statistic_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='虚拟币每日详情表';

LOCK TABLES `bc_token_detail` WRITE;
/*!40000 ALTER TABLE `bc_token_detail` DISABLE KEYS */;

INSERT INTO `bc_token_detail` (`id`, `bc_token_name`, `bc_token_code`, `bc_token_id`, `price`, `price_change`, `volume`, `statistic_time`, `total_value`, `usd_profitability`, `usd`, `eth_profitability`, `eth`, `circulating_supply`, `today_transaction`, `state`, `version`, `created_at`, `created_by`, `updated_at`, `updated_by`)
VALUES
	('1','测试','ceshi','1',12.9891000000,-0.12,123123,'2018-05-19 12:00:00',123123123.1231230000,89.3100,7.11000,90.1310,9.31000,1231313.9010,123123,0,0,NULL,'',NULL,''),
	('2','测试','ceshi','1',19.3123100000,0.14,123123,'2018-05-19 17:00:00',1231231312.1231300000,90.1312,9.31320,90.3120,8.13120,312321312.1321,31231,0,0,NULL,'',NULL,'');

/*!40000 ALTER TABLE `bc_token_detail` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table bc_token_link
# ------------------------------------------------------------

DROP TABLE IF EXISTS `bc_token_link`;

CREATE TABLE `bc_token_link` (
  `id` varchar(40) NOT NULL DEFAULT '' COMMENT 'ID',
  `bc_token_name` varchar(100) DEFAULT '' COMMENT '货币名称',
  `bc_token_code` varchar(40) DEFAULT '' COMMENT '货币编码',
  `bc_token_id` varchar(40) DEFAULT '' COMMENT '货币id',
  `link_type` tinyint(4) DEFAULT '0' COMMENT '链接类型',
  `link_value` varchar(200) DEFAULT '' COMMENT '链接地址',
  `state` tinyint(4) DEFAULT '0' COMMENT '状态 0－有效 99-删除',
  `version` int(11) DEFAULT '0' COMMENT '版本号',
  `created_at` datetime DEFAULT NULL COMMENT ' 创建时间',
  `created_by` varchar(40) DEFAULT '' COMMENT '创建人',
  `updated_at` datetime DEFAULT NULL COMMENT ' 修改时间',
  `updated_by` varchar(40) DEFAULT '' COMMENT '修改员工ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='虚拟币社区链接表';

LOCK TABLES `bc_token_link` WRITE;
/*!40000 ALTER TABLE `bc_token_link` DISABLE KEYS */;

INSERT INTO `bc_token_link` (`id`, `bc_token_name`, `bc_token_code`, `bc_token_id`, `link_type`, `link_value`, `state`, `version`, `created_at`, `created_by`, `updated_at`, `updated_by`)
VALUES
	('1','测试','1312','1',1,'https://www.baidu.com/',0,0,NULL,'',NULL,''),
	('2','测试','121212','1',2,'www.google.com',0,0,NULL,'',NULL,''),
	('3','测试','123','1',3,'www.taobao.com',0,0,NULL,'',NULL,'');

/*!40000 ALTER TABLE `bc_token_link` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table bc_token_top_holder
# ------------------------------------------------------------

DROP TABLE IF EXISTS `bc_token_top_holder`;

CREATE TABLE `bc_token_top_holder` (
  `id` varchar(40) NOT NULL DEFAULT '' COMMENT 'ID',
  `bc_token_name` varchar(100) DEFAULT '' COMMENT '货币名称',
  `bc_token_code` varchar(40) DEFAULT '' COMMENT '货币编码',
  `bc_token_id` varchar(40) DEFAULT '' COMMENT '货币id',
  `address` varchar(200) DEFAULT '0' COMMENT '持有者地址',
  `rank` int(4) DEFAULT '1000' COMMENT '当日排名',
  `quantily` decimal(20,10) DEFAULT '0.0000000000' COMMENT '当日持有量',
  `percentage` decimal(10,6) DEFAULT '0.000000' COMMENT '当日持有比率',
  `yesterday_rank` int(4) DEFAULT '0' COMMENT '昨日排名',
  `yesterday_quantily` decimal(20,10) DEFAULT '0.0000000000' COMMENT '昨日持有量',
  `yesterday_percentage` decimal(10,6) DEFAULT '0.000000' COMMENT '昨日持有比例',
  `percentage_change` decimal(10,6) DEFAULT '0.000000' COMMENT '持有率变动比例',
  `statistic_time` datetime DEFAULT NULL COMMENT '统计时间',
  `state` tinyint(4) DEFAULT '0' COMMENT '状态 0－有效 99-删除',
  `version` int(11) DEFAULT '0' COMMENT '版本号',
  `created_at` datetime DEFAULT NULL COMMENT ' 创建时间',
  `created_by` varchar(40) DEFAULT '' COMMENT '创建人',
  `updated_at` datetime DEFAULT NULL COMMENT ' 修改时间',
  `updated_by` varchar(40) DEFAULT '' COMMENT '修改员工ID',
  PRIMARY KEY (`id`),
  KEY `IDX_TOKEN_TOP_HOLDER_TOKEN_CODE` (`bc_token_code`) USING BTREE,
  KEY `IDX_TOKEN_TOP_HOLDER_TOKEN_ID` (`bc_token_id`) USING BTREE,
  KEY `IDX_TOKEN_TOP_HOLDER_ADDRESS` (`address`) USING BTREE,
  KEY `IDX_TOKEN_TOP_HOLDER_STATISTIC_TIME` (`statistic_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='币种持有者排名';

LOCK TABLES `bc_token_top_holder` WRITE;
/*!40000 ALTER TABLE `bc_token_top_holder` DISABLE KEYS */;

INSERT INTO `bc_token_top_holder` (`id`, `bc_token_name`, `bc_token_code`, `bc_token_id`, `address`, `rank`, `quantily`, `percentage`, `yesterday_rank`, `yesterday_quantily`, `yesterday_percentage`, `percentage_change`, `statistic_time`, `state`, `version`, `created_at`, `created_by`, `updated_at`, `updated_by`)
VALUES
	('1','测试','ceshi','1','123123141',1,123.1231000000,0.100000,1,1231.0000000000,0.100000,0.000000,'2018-05-20 12:00:00',0,0,NULL,'',NULL,''),
	('2','测试','ceshi','1','12312313',2,1231.1312000000,0.080000,2,12312.1231000000,0.090000,-0.100000,'2018-05-20 12:00:00',0,0,NULL,'',NULL,'');

/*!40000 ALTER TABLE `bc_token_top_holder` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sa_user_behavior_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sa_user_behavior_log`;

CREATE TABLE `sa_user_behavior_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `method` varchar(200) DEFAULT '' COMMENT '方法名称',
  `user_id` int(40) DEFAULT '0' COMMENT '用户ID',
  `using_time` int(40) DEFAULT '0' COMMENT '接口耗时',
  `user_name` varchar(200) DEFAULT '' COMMENT '用户名称',
  `operation` varchar(200) DEFAULT '' COMMENT '操作名称',
  `operate_ip` varchar(30) DEFAULT '00.00.00.00' COMMENT 'ip地址',
  `state` tinyint(4) DEFAULT '0' COMMENT '状态 0－有效 99-删除',
  `version` int(11) DEFAULT '0' COMMENT '版本号',
  `created_at` datetime DEFAULT NULL COMMENT ' 创建时间',
  `created_by` varchar(40) DEFAULT '' COMMENT '创建人',
  `updated_at` datetime DEFAULT NULL COMMENT ' 修改时间',
  `updated_by` varchar(40) DEFAULT '' COMMENT '修改员工ID',
  PRIMARY KEY (`id`),
  KEY `IDX_LOG_OPERATION` (`operation`) USING BTREE,
  KEY `IDX_LOG_USERNAME` (`user_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志';

LOCK TABLES `sa_user_behavior_log` WRITE;
/*!40000 ALTER TABLE `sa_user_behavior_log` DISABLE KEYS */;

INSERT INTO `sa_user_behavior_log` (`id`, `method`, `user_id`, `using_time`, `user_name`, `operation`, `operate_ip`, `state`, `version`, `created_at`, `created_by`, `updated_at`, `updated_by`)
VALUES
	(1,'com.afagoal.web.login.LoginController.index()',8,12,'superadmin','用户首页','127.0.0.1',0,0,'2018-05-21 13:39:17','系统自动生成','2018-05-21 13:39:17',NULL),
	(2,'com.afagoal.web.sys.FunctionController.listPage()',8,0,'superadmin','菜单管理','127.0.0.1',0,0,'2018-05-21 13:40:22','系统自动生成','2018-05-21 13:40:22',NULL),
	(3,'com.afagoal.web.sys.FunctionController.listInfo()',8,8,'superadmin','菜单列表','127.0.0.1',0,0,'2018-05-21 13:40:23','系统自动生成','2018-05-21 13:40:23',NULL),
	(4,'com.afagoal.web.login.LoginController.index()',8,76,'superadmin','用户首页','127.0.0.1',0,0,'2018-05-21 13:45:07','系统自动生成','2018-05-21 13:45:07',NULL),
	(5,'com.afagoal.web.blockchain.TokenController.tokenPage()',8,14,'superadmin','虚拟货币管理','127.0.0.1',0,0,'2018-05-21 13:45:09','系统自动生成','2018-05-21 13:45:09',NULL),
	(6,'com.afagoal.web.blockchain.TokenController.list()',8,66,'superadmin','虚拟货币列表','127.0.0.1',0,0,'2018-05-21 13:45:10','系统自动生成','2018-05-21 13:45:10',NULL),
	(7,'com.afagoal.web.blockchain.TokenDetailController.tokenDetailPage()',8,12,'superadmin','币种每日详情','127.0.0.1',0,0,'2018-05-21 13:45:11','系统自动生成','2018-05-21 13:45:11',NULL),
	(8,'com.afagoal.web.blockchain.TokenDetailController.list()',8,45,'superadmin','币种每日详情列表','127.0.0.1',0,0,'2018-05-21 13:45:12','系统自动生成','2018-05-21 13:45:12',NULL),
	(9,'com.afagoal.web.blockchain.TokenController.tokenInfo()',8,25,'superadmin','虚拟货币详情','127.0.0.1',0,0,'2018-05-21 13:45:15','系统自动生成','2018-05-21 13:45:15',NULL),
	(10,'com.afagoal.web.blockchain.TokenDetailController.tokenDetailInfo()',8,18,'superadmin','币种每日详情info','127.0.0.1',0,0,'2018-05-21 13:45:23','系统自动生成','2018-05-21 13:45:23',NULL),
	(11,'com.afagoal.web.blockchain.TokenTopHolderController.listPage()',8,9,'superadmin','币种排名','127.0.0.1',0,0,'2018-05-21 13:45:31','系统自动生成','2018-05-21 13:45:31',NULL),
	(12,'com.afagoal.web.blockchain.TokenTopHolderController.list()',8,30,'superadmin','币种排名列表','127.0.0.1',0,0,'2018-05-21 13:45:31','系统自动生成','2018-05-21 13:45:31',NULL),
	(13,'com.afagoal.web.blockchain.TokenTopHolderController.info()',8,17,'superadmin','币种排名详情','127.0.0.1',0,0,'2018-05-21 13:45:33','系统自动生成','2018-05-21 13:45:33',NULL),
	(14,'com.afagoal.web.sys.FunctionController.listPage()',8,22,'superadmin','菜单管理','127.0.0.1',0,0,'2018-05-21 13:46:29','系统自动生成','2018-05-21 13:46:29',NULL),
	(15,'com.afagoal.web.sys.FunctionController.listInfo()',8,18,'superadmin','菜单列表','127.0.0.1',0,0,'2018-05-21 13:46:29','系统自动生成','2018-05-21 13:46:29',NULL),
	(16,'com.afagoal.web.sys.FunctionController.infoPage()',8,0,'superadmin','菜单页面','127.0.0.1',0,0,'2018-05-21 13:46:32','系统自动生成','2018-05-21 13:46:32',NULL),
	(17,'com.afagoal.web.sys.FunctionController.add()',8,3,'superadmin','添加菜单','127.0.0.1',0,0,'2018-05-21 13:47:09','系统自动生成','2018-05-21 13:47:09',NULL),
	(18,'com.afagoal.web.sys.FunctionController.listInfo()',8,10,'superadmin','菜单列表','127.0.0.1',0,0,'2018-05-21 13:47:09','系统自动生成','2018-05-21 13:47:09',NULL),
	(19,'com.afagoal.web.sys.FunctionController.infoPage()',8,9,'superadmin','菜单页面','127.0.0.1',0,0,'2018-05-21 13:47:17','系统自动生成','2018-05-21 13:47:17',NULL),
	(20,'com.afagoal.web.sys.FunctionController.add()',8,4,'superadmin','添加菜单','127.0.0.1',0,0,'2018-05-21 13:47:48','系统自动生成','2018-05-21 13:47:48',NULL),
	(21,'com.afagoal.web.sys.FunctionController.listInfo()',8,10,'superadmin','菜单列表','127.0.0.1',0,0,'2018-05-21 13:47:48','系统自动生成','2018-05-21 13:47:48',NULL),
	(22,'com.afagoal.web.login.LoginController.index()',8,45,'superadmin','用户首页','127.0.0.1',0,0,'2018-05-21 13:54:01','系统自动生成','2018-05-21 13:54:01',NULL),
	(23,'com.afagoal.web.sys.DeptController.listPage()',8,18,'superadmin','部门管理','127.0.0.1',0,0,'2018-05-21 13:55:08','系统自动生成','2018-05-21 13:55:08',NULL),
	(24,'com.afagoal.web.sys.DeptController.listInfo()',8,18,'superadmin','部门列表','127.0.0.1',0,0,'2018-05-21 13:55:08','系统自动生成','2018-05-21 13:55:08',NULL),
	(25,'com.afagoal.web.blockchain.TokenController.tokenPage()',8,11,'superadmin','虚拟货币管理','127.0.0.1',0,0,'2018-05-21 13:55:13','系统自动生成','2018-05-21 13:55:13',NULL),
	(26,'com.afagoal.web.blockchain.TokenController.list()',8,29,'superadmin','虚拟货币列表','127.0.0.1',0,0,'2018-05-21 13:55:14','系统自动生成','2018-05-21 13:55:14',NULL),
	(27,'com.afagoal.web.blockchain.TokenDetailController.tokenDetailPage()',8,9,'superadmin','币种每日详情','127.0.0.1',0,0,'2018-05-21 13:55:15','系统自动生成','2018-05-21 13:55:15',NULL),
	(28,'com.afagoal.web.blockchain.TokenDetailController.list()',8,36,'superadmin','币种每日详情列表','127.0.0.1',0,0,'2018-05-21 13:55:15','系统自动生成','2018-05-21 13:55:15',NULL),
	(29,'com.afagoal.web.blockchain.TokenTopHolderController.listPage()',8,10,'superadmin','币种排名','127.0.0.1',0,0,'2018-05-21 13:55:16','系统自动生成','2018-05-21 13:55:16',NULL),
	(30,'com.afagoal.web.blockchain.TokenTopHolderController.list()',8,30,'superadmin','币种排名列表','127.0.0.1',0,0,'2018-05-21 13:55:16','系统自动生成','2018-05-21 13:55:16',NULL),
	(31,'com.afagoal.web.blockchain.TokenTopHolderController.info()',8,54,'superadmin','币种排名详情','127.0.0.1',0,0,'2018-05-21 14:02:02','系统自动生成','2018-05-21 14:02:02',NULL),
	(32,'com.afagoal.web.blockchain.TokenTopHolderController.info()',8,5,'superadmin','币种排名详情','127.0.0.1',0,0,'2018-05-21 14:02:11','系统自动生成','2018-05-21 14:02:11',NULL),
	(33,'com.afagoal.web.sys.FunctionController.listPage()',8,40,'superadmin','菜单管理','127.0.0.1',0,0,'2018-05-21 14:03:57','系统自动生成','2018-05-21 14:03:57',NULL),
	(34,'com.afagoal.web.sys.FunctionController.listInfo()',8,18,'superadmin','菜单列表','127.0.0.1',0,0,'2018-05-21 14:03:57','系统自动生成','2018-05-21 14:03:57',NULL),
	(35,'com.afagoal.web.login.LoginController.index()',8,8,'superadmin','用户首页','127.0.0.1',0,0,'2018-05-21 14:05:17','系统自动生成','2018-05-21 14:05:17',NULL),
	(36,'com.afagoal.web.blockchain.TokenController.tokenPage()',8,0,'superadmin','虚拟货币管理','127.0.0.1',0,0,'2018-05-21 14:14:02','系统自动生成','2018-05-21 14:14:02',NULL),
	(37,'com.afagoal.web.blockchain.TokenController.list()',8,20,'superadmin','虚拟货币列表','127.0.0.1',0,0,'2018-05-21 14:14:02','系统自动生成','2018-05-21 14:14:02',NULL),
	(38,'com.afagoal.web.blockchain.TokenDetailController.tokenDetailPage()',8,0,'superadmin','币种每日详情','127.0.0.1',0,0,'2018-05-21 14:14:06','系统自动生成','2018-05-21 14:14:06',NULL),
	(39,'com.afagoal.web.blockchain.TokenDetailController.list()',8,11,'superadmin','币种每日详情列表','127.0.0.1',0,0,'2018-05-21 14:14:06','系统自动生成','2018-05-21 14:14:06',NULL),
	(40,'com.afagoal.web.blockchain.TokenTopHolderController.listPage()',8,0,'superadmin','币种排名','127.0.0.1',0,0,'2018-05-21 14:14:08','系统自动生成','2018-05-21 14:14:08',NULL),
	(41,'com.afagoal.web.blockchain.TokenTopHolderController.list()',8,9,'superadmin','币种排名列表','127.0.0.1',0,0,'2018-05-21 14:14:08','系统自动生成','2018-05-21 14:14:08',NULL),
	(42,'com.afagoal.web.blockchain.TokenDetailController.tokenDetailInfo()',8,19,'superadmin','币种每日详情info','127.0.0.1',0,0,'2018-05-21 14:18:44','系统自动生成','2018-05-21 14:18:44',NULL),
	(43,'com.afagoal.web.login.LoginController.index()',8,9,'superadmin','用户首页','127.0.0.1',0,0,'2018-05-21 14:36:38','系统自动生成','2018-05-21 14:36:38',NULL),
	(44,'com.afagoal.web.blockchain.TokenController.tokenPage()',8,0,'superadmin','虚拟货币管理','127.0.0.1',0,0,'2018-05-21 16:18:38','系统自动生成','2018-05-21 16:18:38',NULL),
	(45,'com.afagoal.web.blockchain.TokenController.list()',8,11,'superadmin','虚拟货币列表','127.0.0.1',0,0,'2018-05-21 16:18:38','系统自动生成','2018-05-21 16:18:38',NULL),
	(46,'com.afagoal.web.login.LoginController.index()',8,8,'superadmin','用户首页','127.0.0.1',0,0,'2018-05-21 16:18:42','系统自动生成','2018-05-21 16:18:42',NULL),
	(47,'com.afagoal.web.login.LoginController.index()',8,7,'superadmin','用户首页','127.0.0.1',0,0,'2018-05-22 11:44:23','系统自动生成','2018-05-22 11:44:23',NULL),
	(48,'com.afagoal.web.blockchain.TokenController.tokenPage()',8,0,'superadmin','虚拟货币管理','127.0.0.1',0,0,'2018-05-22 11:44:41','系统自动生成','2018-05-22 11:44:41',NULL),
	(49,'com.afagoal.web.blockchain.TokenController.list()',8,6,'superadmin','虚拟货币列表','127.0.0.1',0,0,'2018-05-22 11:44:42','系统自动生成','2018-05-22 11:44:42',NULL),
	(50,'com.afagoal.web.blockchain.TokenDetailController.tokenDetailPage()',8,0,'superadmin','币种每日详情','127.0.0.1',0,0,'2018-05-22 11:44:44','系统自动生成','2018-05-22 11:44:44',NULL),
	(51,'com.afagoal.web.blockchain.TokenDetailController.list()',8,9,'superadmin','币种每日详情列表','127.0.0.1',0,0,'2018-05-22 11:44:44','系统自动生成','2018-05-22 11:44:44',NULL),
	(52,'com.afagoal.web.blockchain.TokenTopHolderController.listPage()',8,0,'superadmin','币种排名','127.0.0.1',0,0,'2018-05-22 11:44:46','系统自动生成','2018-05-22 11:44:46',NULL),
	(53,'com.afagoal.web.blockchain.TokenTopHolderController.list()',8,7,'superadmin','币种排名列表','127.0.0.1',0,0,'2018-05-22 11:44:46','系统自动生成','2018-05-22 11:44:46',NULL),
	(54,'com.afagoal.web.sys.FunctionController.listPage()',8,0,'superadmin','菜单管理','127.0.0.1',0,0,'2018-05-22 11:52:29','系统自动生成','2018-05-22 11:52:29',NULL),
	(55,'com.afagoal.web.sys.FunctionController.listInfo()',8,6,'superadmin','菜单列表','127.0.0.1',0,0,'2018-05-22 11:52:30','系统自动生成','2018-05-22 11:52:30',NULL),
	(56,'com.afagoal.web.sys.RoleController.roleList()',8,22,'superadmin','角色管理','127.0.0.1',0,0,'2018-05-22 11:52:32','系统自动生成','2018-05-22 11:52:32',NULL),
	(57,'com.afagoal.web.sys.RoleController.roles()',8,35,'superadmin','角色列表','127.0.0.1',0,0,'2018-05-22 11:52:33','系统自动生成','2018-05-22 11:52:33',NULL),
	(58,'com.afagoal.web.sys.UserController.users()',8,12,'superadmin','用户管理','127.0.0.1',0,0,'2018-05-22 11:52:36','系统自动生成','2018-05-22 11:52:36',NULL),
	(59,'com.afagoal.web.sys.UserController.pageUsers()',8,24,'superadmin','用户列表','127.0.0.1',0,0,'2018-05-22 11:52:36','系统自动生成','2018-05-22 11:52:36',NULL),
	(60,'com.afagoal.web.sys.DeptController.listPage()',8,0,'superadmin','部门管理','127.0.0.1',0,0,'2018-05-22 11:52:38','系统自动生成','2018-05-22 11:52:38',NULL),
	(61,'com.afagoal.web.sys.DeptController.listInfo()',8,3,'superadmin','部门列表','127.0.0.1',0,0,'2018-05-22 11:52:38','系统自动生成','2018-05-22 11:52:38',NULL),
	(62,'com.afagoal.web.login.LoginController.index()',8,7,'superadmin','用户首页','127.0.0.1',0,0,'2018-05-22 13:35:31','系统自动生成','2018-05-22 13:35:31',NULL),
	(63,'com.afagoal.web.blockchain.TokenController.tokenPage()',8,0,'superadmin','虚拟货币管理','127.0.0.1',0,0,'2018-05-22 13:35:34','系统自动生成','2018-05-22 13:35:34',NULL),
	(64,'com.afagoal.web.blockchain.TokenController.list()',8,11,'superadmin','虚拟货币列表','127.0.0.1',0,0,'2018-05-22 13:35:36','系统自动生成','2018-05-22 13:35:36',NULL),
	(65,'com.afagoal.web.blockchain.TokenDetailController.tokenDetailPage()',8,1,'superadmin','币种每日详情','127.0.0.1',0,0,'2018-05-22 13:35:37','系统自动生成','2018-05-22 13:35:37',NULL),
	(66,'com.afagoal.web.blockchain.TokenDetailController.list()',8,7,'superadmin','币种每日详情列表','127.0.0.1',0,0,'2018-05-22 13:35:37','系统自动生成','2018-05-22 13:35:37',NULL),
	(67,'com.afagoal.web.blockchain.TokenTopHolderController.listPage()',8,0,'superadmin','币种排名','127.0.0.1',0,0,'2018-05-22 13:35:40','系统自动生成','2018-05-22 13:35:40',NULL),
	(68,'com.afagoal.web.blockchain.TokenTopHolderController.list()',8,8,'superadmin','币种排名列表','127.0.0.1',0,0,'2018-05-22 13:35:40','系统自动生成','2018-05-22 13:35:40',NULL);

/*!40000 ALTER TABLE `sa_user_behavior_log` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_dept
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_dept`;

CREATE TABLE `sys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT '0',
  `dept_name` varchar(20) NOT NULL DEFAULT '' COMMENT '部门名称',
  `introduce` varchar(100) DEFAULT '' COMMENT '简介',
  `created_at` datetime DEFAULT '2017-01-01 00:00:00' COMMENT '创建日期',
  `updated_at` datetime DEFAULT '2017-01-01 00:00:00' COMMENT '修改日期',
  `created_by` varchar(20) DEFAULT '' COMMENT '创建人',
  `updated_by` varchar(20) DEFAULT '' COMMENT '修改人员',
  `state` int(1) DEFAULT '1' COMMENT '0:无效，1有效',
  `version` int(11) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dept_name` (`dept_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;

INSERT INTO `sys_dept` (`id`, `pid`, `dept_name`, `introduce`, `created_at`, `updated_at`, `created_by`, `updated_by`, `state`, `version`)
VALUES
	(35,0,'人事部门','人事部门','2018-02-24 14:53:05','2018-02-24 14:53:22',NULL,NULL,0,0),
	(36,35,'人事一部','人事一部','2018-02-24 14:53:39','2018-02-24 14:53:39',NULL,NULL,0,0),
	(38,0,'研发中心','研发中心','2018-02-24 15:03:24','2018-02-24 15:03:24',NULL,NULL,0,0),
	(39,38,'研发一部','研发一部','2018-02-24 15:03:35','2018-02-24 15:03:35',NULL,NULL,0,0);

/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_function
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_function`;

CREATE TABLE `sys_function` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `function_name` varchar(20) NOT NULL DEFAULT '' COMMENT '功能名称',
  `function_url` varchar(100) DEFAULT '' COMMENT '请求路径',
  `pid` int(11) DEFAULT '0',
  `introduce` varchar(100) DEFAULT '' COMMENT '简介',
  `type` int(1) DEFAULT '0' COMMENT '0:目录 1:菜单 2:按钮',
  `created_at` datetime DEFAULT '2017-01-01 00:00:00' COMMENT '创建日期',
  `updated_at` datetime DEFAULT '2017-01-01 00:00:00' COMMENT '修改日期',
  `created_by` varchar(20) DEFAULT '' COMMENT '创建人',
  `updated_by` varchar(20) DEFAULT '' COMMENT '修改人员',
  `state` int(1) DEFAULT '1' COMMENT '0:无效，1有效',
  `icon` varchar(30) DEFAULT NULL,
  `version` int(11) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `function_name` (`function_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能模块表';

LOCK TABLES `sys_function` WRITE;
/*!40000 ALTER TABLE `sys_function` DISABLE KEYS */;

INSERT INTO `sys_function` (`id`, `function_name`, `function_url`, `pid`, `introduce`, `type`, `created_at`, `updated_at`, `created_by`, `updated_by`, `state`, `icon`, `version`)
VALUES
	(2,'系统管理','',0,'系统管理',0,'2018-02-11 14:59:00','2018-02-11 14:59:00',NULL,NULL,0,'fa fa-cog',0),
	(3,'菜单管理','/sys/function',2,'菜单管理',1,'2018-02-11 15:09:26','2018-02-11 15:09:26',NULL,NULL,0,'fa fa-bars',0),
	(23,'角色管理','/sys/role',2,'角色管理',1,'2018-02-11 15:00:53','2018-02-11 15:10:43',NULL,NULL,0,'fa fa-male',0),
	(24,'用户管理','/sys/user',2,'用户管理',1,'2018-02-11 15:01:35','2018-02-11 15:10:50',NULL,NULL,0,'fa fa-user',0),
	(25,'菜单授权','/sys/role_function',2,'角色菜单管理',1,'2018-02-23 18:05:02','2018-02-24 15:04:28',NULL,NULL,0,'fa fa-users',0),
	(28,'OA管理','',0,'OA管理',0,'2018-02-24 11:24:29','2018-02-24 11:24:29',NULL,NULL,0,'fa fa-cubes',0),
	(29,'部门管理','/sys/dept',2,'部门管理',1,'2018-02-24 14:48:30','2018-02-24 14:48:30',NULL,NULL,0,'fa fa-university',0),
	(30,'财务管理','',0,'财务管理',0,'2018-02-26 14:16:33','2018-02-26 14:16:33',NULL,NULL,0,'fa fa-building-o',0),
	(31,'工作日志','',28,'工作日志管理',1,'2018-04-14 22:48:21','2018-04-14 22:48:21',NULL,NULL,0,'fa fa-map',0),
	(32,'虚拟币统计','',0,'',0,'2018-05-19 15:09:52','2018-05-19 15:09:52',NULL,NULL,0,'fa fa-database',0),
	(33,'币种统计','/blockchain/token',32,'',1,'2018-05-19 15:11:25','2018-05-19 15:11:25',NULL,NULL,0,'fa fa-adjust',0),
	(34,'币种每日详情','/blockchain/token_detail',32,'',1,'2018-05-19 16:40:40','2018-05-19 16:40:40',NULL,NULL,0,'fa fa-calendar',0),
	(35,'币种排名','/blockchain/token_top_holder',32,'',1,'2018-05-21 11:49:04','2018-05-21 11:49:04',NULL,NULL,0,'fa fa-bar-chart-o',0),
	(36,'系统监控','',0,'',0,'2018-05-21 13:47:09','2018-05-21 13:47:09',NULL,NULL,0,'fa fa-warning',0),
	(37,'操作日志','/user_behavior/user_behavior_log',36,'',1,'2018-05-21 13:47:48','2018-05-21 13:47:48',NULL,NULL,0,'',0);

/*!40000 ALTER TABLE `sys_function` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) NOT NULL DEFAULT '' COMMENT '角色名称',
  `introduce` varchar(100) DEFAULT '' COMMENT '介绍',
  `created_at` datetime DEFAULT '2017-01-01 00:00:00' COMMENT '创建日期',
  `updated_at` datetime DEFAULT '2017-01-01 00:00:00' COMMENT '修改日期',
  `created_by` varchar(20) DEFAULT '' COMMENT '创建人',
  `updated_by` varchar(20) DEFAULT '' COMMENT '修改人员',
  `state` int(1) DEFAULT '1' COMMENT '0:无效，1有效',
  `version` int(11) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色表';

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;

INSERT INTO `sys_role` (`id`, `role_name`, `introduce`, `created_at`, `updated_at`, `created_by`, `updated_by`, `state`, `version`)
VALUES
	(1,'superAdmin','超级管理员','2018-02-11 17:32:05','2018-02-26 14:16:57','',NULL,0,0),
	(2,'user','普通用户','2018-02-11 17:32:05','2018-02-26 14:02:00','',NULL,0,0);

/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_role_function
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_role_function`;

CREATE TABLE `sys_role_function` (
  `role_id` int(11) NOT NULL,
  `function_id` int(11) NOT NULL,
  `version` int(11) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`role_id`,`function_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能_角色关联表';

LOCK TABLES `sys_role_function` WRITE;
/*!40000 ALTER TABLE `sys_role_function` DISABLE KEYS */;

INSERT INTO `sys_role_function` (`role_id`, `function_id`, `version`)
VALUES
	(1,2,0),
	(1,3,0),
	(1,23,0),
	(1,24,0),
	(1,25,0),
	(1,28,0),
	(1,29,0),
	(1,30,0),
	(1,31,0),
	(1,32,0),
	(1,33,0),
	(1,34,0),
	(1,35,0),
	(2,28,0),
	(2,30,0),
	(20,2,0),
	(20,3,0),
	(20,23,0),
	(20,24,0),
	(20,25,0),
	(20,28,0),
	(21,2,0),
	(21,3,0),
	(21,23,0),
	(21,24,0),
	(21,25,0),
	(21,28,0),
	(22,2,0),
	(22,3,0),
	(22,25,0),
	(22,28,0),
	(23,2,0),
	(23,3,0),
	(23,25,0),
	(23,28,0);

/*!40000 ALTER TABLE `sys_role_function` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` int(1) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL DEFAULT '' COMMENT '登录名',
  `nick_name` varchar(20) DEFAULT '' COMMENT '昵称',
  `real_name` varchar(20) DEFAULT '' COMMENT '真实姓名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `login_ip` varchar(16) DEFAULT '00.00.00.00' COMMENT 'ip地址',
  `login_times` int(6) DEFAULT '1' COMMENT '登录次数',
  `email` varchar(100) DEFAULT '' COMMENT '邮箱',
  `mobile` varchar(40) DEFAULT '' COMMENT '电话',
  `created_at` datetime DEFAULT '2017-01-01 00:00:00' COMMENT '创建日期',
  `updated_at` datetime DEFAULT '2017-01-01 00:00:00' COMMENT '修改日期',
  `created_by` varchar(20) DEFAULT '' COMMENT '创建人',
  `updated_by` varchar(20) DEFAULT '' COMMENT '修改人员',
  `state` int(1) DEFAULT '1' COMMENT '0:无效，1有效',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门Id',
  `user_type` int(2) DEFAULT '0' COMMENT '用户类型 0:普通用户 1:系统管理员',
  `version` int(11) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户表';

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;

INSERT INTO `sys_user` (`id`, `user_name`, `nick_name`, `real_name`, `password`, `login_ip`, `login_times`, `email`, `mobile`, `created_at`, `updated_at`, `created_by`, `updated_by`, `state`, `dept_id`, `user_type`, `version`)
VALUES
	(1,'niepengju',NULL,'聂鹏举','0c9689cb4c811dc310111fdb2450d792',NULL,0,'niepangju@qq.com','o6QSv18ippGeNbS/szamKQ==','2017-01-01 00:00:00','2018-02-26 14:06:32','',NULL,0,38,0,0),
	(2,'kuangxiong',NULL,'匡雄','0c9689cb4c811dc310111fdb2450d792',NULL,0,'kuangxiong@qq.com','o6QSv18ippGeNbS/szamKQ==','2017-01-01 00:00:00','2018-02-26 14:06:34','',NULL,0,38,0,0),
	(3,'zhanglixuan',NULL,'张力轩','0c9689cb4c811dc310111fdb2450d792',NULL,0,'zhanglixuan@qq.com','o6QSv18ippGeNbS/szamKQ==','2018-02-24 16:56:17','2018-02-26 14:06:29',NULL,NULL,0,38,0,0),
	(4,'xiaxi',NULL,'夏溪','0c9689cb4c811dc310111fdb2450d792',NULL,0,'xiaxi@qq.com','o6QSv18ippGeNbS/szamKQ==','2018-02-24 16:59:48','2018-02-26 14:06:26',NULL,NULL,0,38,0,0),
	(8,'superadmin',NULL,'超级管理员','0c9689cb4c811dc310111fdb2450d792',NULL,0,'superadmin@afagoal.com','o6QSv18ippGeNbS/szamKQ==','2018-02-26 14:08:27','2018-02-26 14:08:45',NULL,NULL,0,35,1,0),
	(9,'wutong',NULL,'吴仝','0c9689cb4c811dc310111fdb2450d792',NULL,0,'wutong@afagoal.com','o6QSv18ippGeNbS/szamKQ==','2018-04-13 17:29:06','2018-04-13 17:29:06',NULL,NULL,0,38,0,0);

/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_user_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `role_id` int(11) NOT NULL COMMENT 'role_id',
  `user_id` int(11) NOT NULL COMMENT 'user_id',
  `version` int(11) DEFAULT '0' COMMENT '版本号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户_角色关联表';

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;

INSERT INTO `sys_user_role` (`role_id`, `user_id`, `version`)
VALUES
	(1,8,0),
	(2,4,0),
	(2,3,0),
	(2,2,0),
	(2,1,0);

/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
