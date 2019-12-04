set foreign_key_checks = 0;
#删除多余字段
ALTER TABLE `t_depart`
    DROP COLUMN `INFO1`,
    DROP COLUMN `INFO2`,
    DROP COLUMN `INFO3`,
    DROP COLUMN `INFO4`,
    DROP COLUMN `INFO5`;
delete from t_exam_info_depart;
delete from t_depart;
