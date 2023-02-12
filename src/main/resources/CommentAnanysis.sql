CREATE DATABASE `cadb` DEFAULT CHARACTER SET utf8;

USE `cadb`;

DROP TABLE IF EXISTS `roles_user`;
DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) DEFAULT NULL,
  `nickname` varchar(64) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT '1',
  `email` varchar(64) DEFAULT NULL,
  `userface` varchar(255) DEFAULT NULL,
  `regTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

CREATE TABLE `roles_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rid` int(11) DEFAULT '2',
  `uid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `rid` (`rid`),
  KEY `roles_user_ibfk_2` (`uid`),
  CONSTRAINT `roles_user_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `roles` (`id`),
  CONSTRAINT `roles_user_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8;

INSERT INTO `user` VALUES ('1', 'XiaoMing', '小明', '202cb962ac59075b964b07152d234b70', '1', 'XiaoMing@qq.com', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514093920326&di=44a6fa6b597d86f475c2b15fa93008dd&imgtype=0&src=http%3A%2F%2Fwww.qqzhi.com%2Fuploadpic%2F2015-01-12%2F023019564.jpg', '2022-08-08 09:30:22');
INSERT INTO `user` VALUES ('2', 'XiaoHong', '小红', '202cb962ac59075b964b07152d234b70', '1', 'XiaoHong@qq.com', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514093920326&di=44a6fa6b597d86f475c2b15fa93008dd&imgtype=0&src=http%3A%2F%2Fwww.qqzhi.com%2Fuploadpic%2F2015-01-12%2F023019564.jpg', '2022-08-08 09:30:22');

INSERT INTO `roles` VALUES ('1', '超级管理员');
INSERT INTO `roles` VALUES ('2', '普通用户');

INSERT INTO `roles_user` VALUES ('1', '1', '1');
INSERT INTO `roles_user` VALUES ('2', '2', '2');

DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` varchar(16) NOT NULL COMMENT '商品ID',
  `user_id` varchar(24) COMMENT '用户ID',
  `profile_name` varchar(128) COMMENT '用户资料名称',
  `helpfulness_numerator` int(1) COMMENT '有用程度分子',
  `helpfulness_denominator` int(1) COMMENT '有用程度分母',
  `score` int(1) COMMENT '评分',
  `time` datetime NOT NULL COMMENT '评论时间',
  `summary` varchar(512) NOT NULL COMMENT '评论概述',
  `text` text COMMENT '评论详情',
  PRIMARY KEY (`id`)
) COMMENT ='评论详情表' ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `comments_trend`;
CREATE TABLE `comments_trend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` varchar(16) NOT NULL COMMENT '商品ID',
  `year` char(4) COMMENT '年份',
  `avg_score` DECIMAL(2,1) COMMENT '平均评分',
  PRIMARY KEY (`id`)
) COMMENT ='评论趋势表' ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `comments_analysis`;
CREATE TABLE `comments_analysis` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` varchar(16) NOT NULL COMMENT '商品ID',
  `sale_volumn` int(11) COMMENT '销量',
  `avg_score` DECIMAL(2,1) COMMENT '平均评分',
  `max_score` int(1) COMMENT '最高评分',
  `min_score` int(1) COMMENT '最低评分',
  PRIMARY KEY (`id`)
) COMMENT ='评论分析表' ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;