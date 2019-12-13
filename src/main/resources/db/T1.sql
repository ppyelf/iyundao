set foreign_key_checks = 0;

ALTER TABLE `t_evaluation`
    DROP COLUMN `NUMBER`,
    DROP COLUMN `PATIENTNAME`;

#指标数据
delete from t_evaluation_index;
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('02e1e0523fa84786870804d8a980ff35', '20191121000000', '20191121000000', '0', '完成指令性任务', '2.0', '10.0', '0');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('03a258dec1b642d7826ca5e7122e216b', '20191121000000', '20191121000000', '0', '投诉通报批评', '3.0', '3.0', '1');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('0447096aa5894942817bb9e0bc72a095', '20191121000000', '20191121000000', '0', '获得荣誉称号', '1.0', '10.0', '0');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('10741df6f2134e3a8fe14cdf155328e6', '20191121000000', '20191121000000', '0', '医疗服务质量', '1.0', '1.0', '0');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('1a6fa7713e544a66b6492cdf13626b10', '20191121000000', '20191121000000', '0', '违反服务规范', '-0.5', '-0.5', '1');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('1b0f267a120c46188bed0d5cdc407221', '20191121000000', '20191121000000', '1', '违反核心医疗制度', '-1.0', '-1.0', '1');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('1f8e84fde60645578076884ae64a9cb0', '20191121000000', '20191121000000', '0', '受到宣传表扬', '0.5', '10.0', '0');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('2604ddfa91f641f7b2d624a1d96fd73f', '20191121000000', '20191121000000', '0', '违反院纪院规', '-0.5', '-2.0', '1');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('31b472b5f5844b97b6237d45875c892f', '20191121000000', '20191121000000', '0', '发生重大医疗事故或严重', '-1.0', '-1.0', '2');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('3f525b8badff44a98c520af4224ea75a', '20191121000000', '20191121000000', '1', '表扬信', '0.3', '0.3', '0');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('41b77f4008b247629679e1b0c9fadc16', '20191121000000', '20191121000000', '0', '临床医疗处置不当', '-1.0', '-5.0', '1');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('49176927c23549e9af6b143304d31ba8', '20191121000000', '20191121000000', '1', '违反合理用药规定', '0.1', '-5.0', '1');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('4fde862899d9463b84d7c27a65c8c34a', '20191121000000', '20191121000000', '0', '发生违法违纪行为', '-1.0', '-1.0', '2');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('5d28f98ba17c49f6bc1c335f8866d653', '20191121000000', '20191121000000', '0', '支持爱心公益', '0.5', '6.0', '0');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('5e055091438c48d8a2c684574d50c5a5', '20191121000000', '20191121000000', '0', '医疗技术提升', '0.5', '4.0', '0');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('6391041a4f074e31b07b0d8946562860', '20191121000000', '20191121000000', '1', '无偿献血', '3.0', '3.0', '0');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('75885abf977544e99e4bc17a54b1e349', '20191121000000', '20191121000000', '1', '质量服务投诉', '-1.0', '-8.0', '1');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('7a822adfc60742a4b2a54899c5f3cd42', '20191121000000', '20191121000000', '1', '违反诊疗规范', '-0.2', '-0.2', '1');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('7c3ff598f17d45a0b0f59f723c5386e9', '20191121000000', '20191121000000', '0', '医德人文教育', '-0.5', '-20.0', '1');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('86ff786e903c45edad803abb60fe4736', '20191121000000', '20191121000000', '0', '注重学科建设', '1.0', '5.0', '0');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('8fbc6140d6f14e31abcbf6871e9dd370', '20191121000000', '20191121000000', '1', '义诊一天', '0.3', '0.3', '0');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('9e55d1f915fd4a47b4545966f33d64ce', '20191121000000', '20191121000000', '0', '积极开括创新', '2.0', '2.0', '0');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('aa989dd2cd0e499ea55f444583e1559b', '20191121000000', '20191121000000', '1', '患者点名表扬', '0.3', '0.3', '0');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('b2a4379eaa394225bce91365dd8752fb', '20191121000000', '20191121000000', '1', '委屈奖', '0.5', '0.5', '0');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('b96a08c338de4ca6aa504e84687d459d', '20191121000000', '20191121000000', '1', '锦旗', '0.5', '0.5', '0');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('cbaf9ba071e142459916d79829f321cf', '20191121000000', '20191121000000', '0', '违反院纪院规', '-1.0', '-1.0', '1');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('d44df78b68294dd49ccf693148d514df', '20191121000000', '20191121000000', '0', '违反劳动纪律', '-0.5', '-0.5', '1');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('dbed39db0e114d60a281955815305104', '20191121000000', '20191121000000', '0', '其他加分', '0.5', '0.5', '0');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('dd3ca782c639427c9d3ab4064ec6f9da', '20191121000000', '20191121000000', '1', '通报表扬', '1.0', '1.0', '0');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('e00d55a5ac924b7990967a41bd1c0e2d', '20191121000000', '20191121000000', '1', '义诊半天', '0.2', '0.2', '0');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('e874f1e87a3f4ec18a8bfb5d3bbdcf7c', '20191121000000', '20191121000000', '1', '拾金不昧', '0.5', '0.5', '0');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('f9c0f6d1a1514bc29195e980bda9b5c0', '20191121000000', '20191121000000', '0', '投诉1', '-0.5', '-0.5', '1');
INSERT INTO `ayundao`.`t_evaluation_index` (`ID`, `CREATEDATE`, `LASTMODIFIEDTIME`, `VERSION`, `NAME`, `MIN`, `MAX`, `TYPE`) VALUES ('fab2f63a0a2242ce8577a1969642b516', '20191121000000', '20191121000000', '0', '拒收红包500元及以上', '0.5', '5.0', '0');

ALTER TABLE `t_evaluation`
    MODIFY COLUMN `YEAR`  varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL AFTER `VERSION`;

