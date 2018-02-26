# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.19)
# Database: afagoal_sys
# Generation Time: 2018-02-26 06:12:31 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


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
  PRIMARY KEY (`id`),
  UNIQUE KEY `dept_name` (`dept_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;

INSERT INTO `sys_dept` (`id`, `pid`, `dept_name`, `introduce`, `created_at`, `updated_at`, `created_by`, `updated_by`, `state`)
VALUES
	(35,0,'人事部门','人事部门','2018-02-24 14:53:05','2018-02-24 14:53:22',NULL,NULL,0),
	(36,35,'人事一部','人事一部','2018-02-24 14:53:39','2018-02-24 14:53:39',NULL,NULL,0),
	(38,0,'研发中心','研发中心','2018-02-24 15:03:24','2018-02-24 15:03:24',NULL,NULL,0),
	(39,38,'研发一部','研发一部','2018-02-24 15:03:35','2018-02-24 15:03:35',NULL,NULL,0);

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
  PRIMARY KEY (`id`),
  UNIQUE KEY `function_name` (`function_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能模块表';

LOCK TABLES `sys_function` WRITE;
/*!40000 ALTER TABLE `sys_function` DISABLE KEYS */;

INSERT INTO `sys_function` (`id`, `function_name`, `function_url`, `pid`, `introduce`, `type`, `created_at`, `updated_at`, `created_by`, `updated_by`, `state`, `icon`)
VALUES
	(2,'系统管理','',0,'系统管理',0,'2018-02-11 14:59:00','2018-02-11 14:59:00',NULL,NULL,0,'fa fa-cog'),
	(3,'菜单管理','/sys/function',2,'菜单管理',1,'2018-02-11 15:09:26','2018-02-11 15:09:26',NULL,NULL,0,'fa fa-bars'),
	(23,'角色管理','/sys/role',2,'角色管理',1,'2018-02-11 15:00:53','2018-02-11 15:10:43',NULL,NULL,0,'fa fa-male'),
	(24,'用户管理','/sys/user',2,'用户管理',1,'2018-02-11 15:01:35','2018-02-11 15:10:50',NULL,NULL,0,'fa fa-user'),
	(25,'菜单授权','/sys/role_function',2,'角色菜单管理',1,'2018-02-23 18:05:02','2018-02-24 15:04:28',NULL,NULL,0,'fa fa-users'),
	(28,'OA管理','',0,'OA管理',0,'2018-02-24 11:24:29','2018-02-24 11:24:29',NULL,NULL,0,'fa fa-cubes'),
	(29,'部门管理','/sys/dept',2,'部门管理',1,'2018-02-24 14:48:30','2018-02-24 14:48:30',NULL,NULL,0,'fa fa-university');

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色表';

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;

INSERT INTO `sys_role` (`id`, `role_name`, `introduce`, `created_at`, `updated_at`, `created_by`, `updated_by`, `state`)
VALUES
	(1,'superAdmin','超级管理员','2018-02-11 17:32:05','2018-02-26 14:01:49','',NULL,0),
	(2,'user','普通用户','2018-02-11 17:32:05','2018-02-26 14:02:00','',NULL,0),
	(9,'测试','测试','2018-02-23 15:53:03','2018-02-23 15:53:03',NULL,NULL,99),
	(10,'测试123','测试qwe12312312312','2018-02-23 16:08:02','2018-02-23 16:08:13',NULL,NULL,99),
	(11,'ceshi','ceshi ','2018-02-24 10:39:24','2018-02-24 10:39:24',NULL,NULL,99),
	(12,'cehsi ','ceshi ','2018-02-24 10:48:03','2018-02-24 10:48:03',NULL,NULL,99),
	(13,'测试','测试','2018-02-24 11:38:54','2018-02-24 11:38:54',NULL,NULL,99),
	(14,'测','测','2018-02-24 11:39:41','2018-02-24 11:39:41',NULL,NULL,99),
	(15,'111','111','2018-02-24 11:41:56','2018-02-24 11:41:56',NULL,NULL,99),
	(16,'测试','测试','2018-02-24 13:28:46','2018-02-24 13:28:46',NULL,NULL,99),
	(17,'测试','测试','2018-02-24 13:32:20','2018-02-24 13:34:49',NULL,NULL,99),
	(18,'测试','测试','2018-02-24 13:36:37','2018-02-24 13:36:37',NULL,NULL,99),
	(19,'测试','测试','2018-02-24 13:37:24','2018-02-24 13:53:07',NULL,NULL,99),
	(20,'测试','测试qwe','2018-02-24 13:53:24','2018-02-24 13:54:06',NULL,NULL,99),
	(21,'测试','测试','2018-02-24 13:54:53','2018-02-24 13:54:53',NULL,NULL,99),
	(22,'测试','测试qwe','2018-02-24 14:04:04','2018-02-24 14:04:10',NULL,NULL,99),
	(23,'测试','测试qwe12312312312','2018-02-24 14:05:20','2018-02-24 14:05:30',NULL,NULL,99);

/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_role_function
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_role_function`;

CREATE TABLE `sys_role_function` (
  `role_id` int(11) NOT NULL,
  `function_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`function_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能_角色关联表';

LOCK TABLES `sys_role_function` WRITE;
/*!40000 ALTER TABLE `sys_role_function` DISABLE KEYS */;

INSERT INTO `sys_role_function` (`role_id`, `function_id`)
VALUES
	(1,2),
	(1,3),
	(1,23),
	(1,24),
	(1,25),
	(1,28),
	(1,29),
	(2,28),
	(20,2),
	(20,3),
	(20,23),
	(20,24),
	(20,25),
	(20,28),
	(21,2),
	(21,3),
	(21,23),
	(21,24),
	(21,25),
	(21,28),
	(22,2),
	(22,3),
	(22,25),
	(22,28),
	(23,2),
	(23,3),
	(23,25),
	(23,28);

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
  `mobile` varchar(13) DEFAULT '' COMMENT '电话',
  `created_at` datetime DEFAULT '2017-01-01 00:00:00' COMMENT '创建日期',
  `updated_at` datetime DEFAULT '2017-01-01 00:00:00' COMMENT '修改日期',
  `created_by` varchar(20) DEFAULT '' COMMENT '创建人',
  `updated_by` varchar(20) DEFAULT '' COMMENT '修改人员',
  `state` int(1) DEFAULT '1' COMMENT '0:无效，1有效',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门Id',
  `user_type` int(2) DEFAULT '0' COMMENT '用户类型 0:普通用户 1:系统管理员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户表';

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;

INSERT INTO `sys_user` (`id`, `user_name`, `nick_name`, `real_name`, `password`, `login_ip`, `login_times`, `email`, `mobile`, `created_at`, `updated_at`, `created_by`, `updated_by`, `state`, `dept_id`, `user_type`)
VALUES
	(1,'niepengju',NULL,'聂鹏举','123456',NULL,0,'niepangju@qq.com','18292323932','2017-01-01 00:00:00','2018-02-26 14:06:32','',NULL,0,38,0),
	(2,'kuangxiong',NULL,'匡雄','123456',NULL,0,'kuangxiong@qq.com','19232329232','2017-01-01 00:00:00','2018-02-26 14:06:34','',NULL,0,38,0),
	(3,'zhanglixuan',NULL,'张力轩','123456',NULL,0,'zhanglixuan@qq.com','18292167891','2018-02-24 16:56:17','2018-02-26 14:06:29',NULL,NULL,0,38,0),
	(4,'xiaxi',NULL,'夏溪','123456',NULL,0,'xiaxi@qq.com','18292329232','2018-02-24 16:59:48','2018-02-26 14:06:26',NULL,NULL,0,38,0),
	(8,'superadmin',NULL,'超级管理员','123456',NULL,0,'superadmin@afagoal.com','18290902930','2018-02-26 14:08:27','2018-02-26 14:08:45',NULL,NULL,0,35,1);

/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sys_user_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `role_id` int(11) NOT NULL COMMENT 'role_id',
  `user_id` int(11) NOT NULL COMMENT 'user_id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户_角色关联表';

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;

INSERT INTO `sys_user_role` (`role_id`, `user_id`)
VALUES
	(1,8),
	(2,4),
	(2,3),
	(2,2),
	(2,1);

/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
