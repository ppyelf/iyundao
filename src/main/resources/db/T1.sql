set foreign_key_checks = 0;

CREATE FUNCTION `fristPinyin`(P_NAME VARCHAR(255)) RETURNS varchar(255) CHARSET utf8
BEGIN
    DECLARE V_RETURN VARCHAR(255);
    SET V_RETURN = ELT(INTERVAL(CONV(HEX(left(CONVERT(P_NAME USING gbk),1)),16,10),
                                0xB0A1,0xB0C5,0xB2C1,0xB4EE,0xB6EA,0xB7A2,0xB8C1,0xB9FE,0xBBF7,
                                0xBFA6,0xC0AC,0xC2E8,0xC4C3,0xC5B6,0xC5BE,0xC6DA,0xC8BB,
                                0xC8F6,0xCBFA,0xCDDA,0xCEF4,0xD1B9,0xD4D1),
                       'a','b','c','d','e','f','g','h','j','k','l','m','n','o','p','q','r','s','t','w','x','y','z');
    RETURN V_RETURN;
END;

CREATE FUNCTION `pinyin`(P_NAME VARCHAR(255)) RETURNS varchar(255) CHARSET utf8
BEGIN
    DECLARE V_COMPARE VARCHAR(255);
    DECLARE V_RETURN VARCHAR(255);
    DECLARE I INT;
    SET I = 1;
    SET V_RETURN = '';
    while I < LENGTH(P_NAME) do
    SET V_COMPARE = SUBSTR(P_NAME, I, 1);
    IF (V_COMPARE != '') THEN
        #SET V_RETURN = CONCAT(V_RETURN, ',', V_COMPARE);
        SET V_RETURN = CONCAT(V_RETURN, fristPinyin(V_COMPARE));
        #SET V_RETURN = fristPinyin(V_COMPARE);
    END IF;
    SET I = I + 1;
    end while;
    IF (ISNULL(V_RETURN) or V_RETURN = '') THEN
        SET V_RETURN = P_NAME;
    END IF;
    RETURN V_RETURN;
END;