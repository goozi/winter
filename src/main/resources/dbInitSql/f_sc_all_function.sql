DELIMITER ;
-- ----------------------------
-- Function structure for GETCHILDLIST
-- ----------------------------
DROP FUNCTION IF EXISTS `GETCHILDLIST`;
DELIMITER ;;
CREATE  FUNCTION `GETCHILDLIST`(
  rootId VARCHAR (36)
) RETURNS varchar(1000) CHARSET utf8 COLLATE utf8_unicode_ci
  BEGIN
    DECLARE
    sTemp VARCHAR (1000);

    DECLARE
    sTempChd VARCHAR (1000);


    SET sTemp = '$';


    SET sTempChd = cast(rootId AS CHAR);


    WHILE sTempChd IS NOT NULL DO

      SET sTemp = concat(sTemp, ',', sTempChd);

      SELECT
        group_concat(id) INTO sTempChd
      FROM
        t_sc_soncompany
      WHERE
        FIND_IN_SET(parentID, sTempChd) > 0;


    END
    WHILE;

    RETURN sTemp;


  END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for getChildList_depart
-- ----------------------------
DROP FUNCTION IF EXISTS `getChildList_depart`;
DELIMITER ;;
CREATE  FUNCTION `getChildList_depart`(
  departmentName VARCHAR (100)
) RETURNS varchar(1000) CHARSET utf8 COLLATE utf8_unicode_ci
  BEGIN
    DECLARE rootId varchar(36);
    DECLARE
            sTemp VARCHAR (1000);

    DECLARE
            sTempChd VARCHAR (1000);

    select id into rootId from t_s_depart where departname = departmentName;
    SET sTemp = '$';


    SET sTempChd = cast(rootId AS CHAR);


    WHILE sTempChd IS NOT NULL DO

      SET sTemp = concat(sTemp, ',', sTempChd);

      SELECT
        group_concat(id) INTO sTempChd
      FROM
        t_s_depart
      WHERE
        FIND_IN_SET(parentdepartid, sTempChd) > 0;


    END
    WHILE;

    RETURN sTemp;
  end
;;
DELIMITER ;
-- ----------------------------
-- Function structure for FB_GETCHINESESPELL
-- ----------------------------
DROP FUNCTION IF EXISTS `FB_GETCHINESESPELL`;
DELIMITER ;;
CREATE FUNCTION `FB_GETCHINESESPELL`(P_NAME VARCHAR(255)) RETURNS varchar(255) CHARSET utf8
  BEGIN

    DECLARE V_COMPARE VARCHAR (255);


    DECLARE V_RETURN VARCHAR (255);


    DECLARE I INT;


    SET I = 1;


    SET V_RETURN = '';


    WHILE I < LENGTH(P_NAME) DO

      SET V_COMPARE = SUBSTR(P_NAME, I, 1);


      IF (V_COMPARE != '') THEN
        #SET V_RETURN = CONCAT(V_RETURN, ',', V_COMPARE);
        SET V_RETURN = CONCAT(
            V_RETURN,
            fristPinyin (V_COMPARE)
        );

      #SET V_RETURN = fristPinyin(V_COMPARE);
      END
      IF;


      SET I = I + 1;


    END
    WHILE;


    IF (
      ISNULL(V_RETURN)
      OR V_RETURN = ''
    ) THEN

      SET V_RETURN = P_NAME;


    END
    IF;

    RETURN V_RETURN;


  END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for FRISTPINYIN
-- ----------------------------
DROP FUNCTION IF EXISTS `FRISTPINYIN`;
DELIMITER ;;
CREATE FUNCTION `FRISTPINYIN`(P_NAME VARCHAR(255)) RETURNS varchar(255) CHARSET utf8
  BEGIN

    DECLARE V_RETURN VARCHAR (255);


    SET V_RETURN = ELT(
        INTERVAL (
            CONV(
                HEX(
                    LEFT (CONVERT(P_NAME USING gbk), 1)
                ),
                16,
                10
            ),
            0xB0A1,
            0xB0C5,
            0xB2C1,
            0xB4EE,
            0xB6EA,
            0xB7A2,
            0xB8C1,
            0xB9FE,
            0xBBF7,
            0xBFA6,
            0xC0AC,
            0xC2E8,
            0xC4C3,
            0xC5B6,
            0xC5BE,
            0xC6DA,
            0xC8BB,
            0xC8F6,
            0xCBFA,
            0xCDDA,
            0xCEF4,
            0xD1B9,
            0xD4D1
        ),
        'A',
        'B',
        'C',
        'D',
        'E',
        'F',
        'G',
        'H',
        'J',
        'K',
        'L',
        'M',
        'N',
        'O',
        'P',
        'Q',
        'R',
        'S',
        'T',
        'W',
        'X',
        'Y',
        'Z'
    );

    RETURN V_RETURN;


  END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for f_sc_getDepartChildList
-- ----------------------------
DROP FUNCTION IF EXISTS `f_sc_getDepartChildList`;
DELIMITER ;;
CREATE  FUNCTION `f_sc_getDepartChildList`(`rootId` varchar(36)) RETURNS varchar(4000) CHARSET utf8
  BEGIN
    DECLARE sTemp VARCHAR (21845);

    DECLARE sTempChd VARCHAR (21845);

    SET sTemp = '$';

    SET sTempChd = cast(rootId AS CHAR);


    WHILE sTempChd IS NOT NULL DO

      SET sTemp = concat(sTemp, ',', sTempChd);

      SELECT
        group_concat(id) INTO sTempChd
      FROM
        t_s_depart
      WHERE
        FIND_IN_SET(parentdepartid, sTempChd) > 0;


    END
    WHILE;

    RETURN sTemp;
  end
;;
DELIMITER ;



-- ----------------------------
-- Function structure for GETCHILDLIST_DOCUMENT
-- ----------------------------
DROP FUNCTION IF EXISTS `GETCHILDLIST_DOCUMENT`;
DELIMITER ;;
CREATE  FUNCTION `GETCHILDLIST_DOCUMENT`(`rootId` varchar(36)) RETURNS varchar(1000) CHARSET utf8 COLLATE utf8_unicode_ci
  BEGIN
    DECLARE
    sTemp VARCHAR (1000);

    DECLARE
    sTempChd VARCHAR (1000);


    SET sTemp = '$';


    SET sTempChd = cast(rootId AS CHAR);


    WHILE sTempChd IS NOT NULL DO

      SET sTemp = concat(sTemp, ',', sTempChd);

      SELECT
        group_concat(id) INTO sTempChd
      FROM
        t_sc_document
      WHERE
        FIND_IN_SET(pid, sTempChd) > 0;


    END
    WHILE;

    RETURN sTemp;


  END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for GETCHILDLIST_OATREE
-- ----------------------------
DROP FUNCTION IF EXISTS `GETCHILDLIST_OATREE`;
DELIMITER ;;
CREATE  FUNCTION `GETCHILDLIST_OATREE`(rootId VARCHAR (36)) RETURNS varchar(1000) CHARSET utf8 COLLATE utf8_unicode_ci
  BEGIN
    DECLARE
    sTemp VARCHAR (1000);

    DECLARE
    sTempChd VARCHAR (1000);


    SET sTemp = '$';


    SET sTempChd = cast(rootId AS CHAR);


    WHILE sTempChd IS NOT NULL DO

      SET sTemp = concat(sTemp, ',', sTempChd);

      SELECT
        group_concat(id) INTO sTempChd
      FROM
        t_s_depart
      WHERE
        FIND_IN_SET(parentdepartid, sTempChd) > 0;


    END
    WHILE;

    RETURN sTemp;


  END
;;
DELIMITER ;