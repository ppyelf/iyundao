set foreign_key_checks = 0;

DROP FUNCTION IF EXISTS to_pinyin;
DELIMITER $
CREATE FUNCTION to_pinyin(NAME VARCHAR(255) CHARSET gbk)
    RETURNS VARCHAR(255) CHARSET gbk
BEGIN
    DECLARE mycode INT;
    DECLARE tmp_lcode VARCHAR(2) CHARSET gbk;
    DECLARE lcode INT;
    DECLARE tmp_rcode VARCHAR(2) CHARSET gbk;
    DECLARE rcode INT;
    DECLARE mypy VARCHAR(255) CHARSET gbk DEFAULT '';
    DECLARE lp INT;
    SET mycode = 0;
    SET lp = 1;
    SET NAME = HEX(NAME);
    WHILE lp < LENGTH(NAME) DO
    SET tmp_lcode = SUBSTRING(NAME, lp, 2);
    SET lcode = CAST(ASCII(UNHEX(tmp_lcode)) AS UNSIGNED);
    SET tmp_rcode = SUBSTRING(NAME, lp + 2, 2);
    SET rcode = CAST(ASCII(UNHEX(tmp_rcode)) AS UNSIGNED);
    IF lcode > 128 THEN
        SET mycode =65536 - lcode * 256 - rcode ;
        SELECT CONCAT(mypy,pin_yin_) INTO mypy FROM t_base_pinyin WHERE CODE_ >= ABS(mycode) ORDER BY CODE_ ASC LIMIT 1;
        SET lp = lp + 4;
    ELSE
        SET mypy = CONCAT(mypy,CHAR(CAST(ASCII(UNHEX(SUBSTRING(NAME, lp, 2))) AS UNSIGNED)));
        SET lp = lp + 2;
    END IF;
    END WHILE;
    RETURN LOWER(mypy);
END;
$
DELIMITER ;
