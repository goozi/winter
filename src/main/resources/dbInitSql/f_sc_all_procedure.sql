DELIMITER ;
-- ----------------------------
-- Procedure structure for P_SC_SYS_GetCurDate
-- ----------------------------
DROP PROCEDURE IF EXISTS `P_SC_SYS_GetCurDate`;
DELIMITER ;;
CREATE PROCEDURE `P_SC_SYS_GetCurDate`(IN  `FYear`    INT, IN `FPeriod` INT, OUT `FBegDate` DATETIME,
                                       OUT `FEndDate` DATETIME)
  BEGIN
    DECLARE FDate VARCHAR(10);
    SET FDate = CONCAT(FYear, '-', FPeriod, '-01');
    SET FBegDate = CONCAT(FDate, ' 00:00:00');
    IF FPeriod <> 12
    THEN
      SET FDate = CONCAT(FYear, '-', (FPeriod + 1), '-01');
    ELSE
      SET FDate = CONCAT((FYear + 1), '-01-01');
    END IF;
    SET FEndDate = CONCAT(DATE_ADD(FDate, INTERVAL 1 MONTH), ' 23:59:59');
  END
;;
DELIMITER ;
-- ----------------------------
-- Procedure structure for P_SC_Exp_Settle
-- ----------------------------
DROP PROCEDURE IF EXISTS `P_SC_Exp_Settle`;
DELIMITER ;;
CREATE PROCEDURE `P_SC_Exp_Settle`(IN `FYear`     INT, IN `FPeriod` INT, IN `FNexYear` INT, IN `FNexPeriod` INT,
                                   IN `FBegDate`  DATETIME, IN `FEndDate` DATETIME, IN `FCreateName` VARCHAR(50),
                                   IN `FCreateBy` VARCHAR(50))
  BEGIN
    #Routine body goes here...
    DECLARE FFid VARCHAR(50);
    DECLARE FNextFid VARCHAR(50);
    DECLARE iTemp INT;
    -- 调用函数前请存在当前期别和下期期别的记录，且state为0未结账
    SET FFid = '';
    SET FNextFid = '';
    SELECT count(*)
    INTO iTemp
    FROM t_sc_account_stage t
    WHERE year(t.date) = FYear AND month(t.date) = FPeriod;
    IF iTemp > 0
    THEN
      SELECT t.id
      INTO FFid
      FROM t_sc_account_stage t
      WHERE year(t.date) = FYear AND month(t.date) = FPeriod;
    END IF;
    SELECT count(*)
    INTO iTemp
    FROM t_sc_account_stage t
    WHERE year(t.date) = FNexYear AND month(t.date) = FNexPeriod;
    IF iTemp > 0
    THEN
      SELECT t.id
      INTO FNextFid
      FROM t_sc_account_stage t
      WHERE year(t.date) = FNexYear AND month(t.date) = FNexPeriod;
    END IF;
    DROP TEMPORARY TABLE IF EXISTS tmp_DataTable;
    CREATE TEMPORARY TABLE tmp_DataTable
    (
      SettleID  VARCHAR(50),
      AccountID VARCHAR(50),
      DeptID    VARCHAR(50),
      EmpID     VARCHAR(50),
      Debit     DECIMAL(20, 10) DEFAULT 0, -- 收入金额
      Credit    DECIMAL(20, 10) DEFAULT 0, -- 支出金额
      SonID     VARCHAR(50)
    );
    DROP TEMPORARY TABLE IF EXISTS tmp_TempCheck;
    CREATE TEMPORARY TABLE tmp_TempCheck
    (
      SettleID  VARCHAR(50),
      AccountID VARCHAR(50),
      DeptID    VARCHAR(50),
      EmpID     VARCHAR(50),
      Debit     DECIMAL(20, 10) DEFAULT 0, -- 收入金额
      Credit    DECIMAL(20, 10) DEFAULT 0, -- 支出金额
      SonID     VARCHAR(50)
    );
    -- 统计本期收入金额
    INSERT INTO tmp_DataTable (#SettleID,
                               AccountID, DeptID, EmpID, Debit, SonID)
      SELECT
        #SettleID,
        AccountID,
        DeptID,
        EmpID,
        SUM(AllAmount),
        SonID
      FROM V_SC_RP_ExpReportBill
      WHERE Date BETWEEN FBegDate AND FEndDate AND ExpFlag = 1
      GROUP BY #SettleID,
        AccountID, DeptID, EmpID, SonID;
    -- 统计本期支出金额
    INSERT INTO tmp_TempCheck (#SettleID,
                               AccountID, DeptID, EmpID, Debit, SonID)
      SELECT
        #SettleID,
        AccountID,
        DeptID,
        EmpID,
        SUM(AllAmount) AS Credit,
        SonID
      FROM V_SC_RP_ExpReportBill
      WHERE Date BETWEEN FBegDate AND FEndDate AND ExpFlag = -1
      GROUP BY #SettleID,
        AccountID, DeptID, EmpID, SonID;

    INSERT INTO tmp_DataTable (SettleID, AccountID, DeptID, EmpID, Credit, SonID)
      SELECT
        t1.SettleID,
        t1.AccountID,
        t1.DeptID,
        t1.EmpID,
        t1.Credit,
        t1.SonID
      FROM tmp_TempCheck t1
      WHERE NOT EXISTS(SELECT 1
                       FROM V_SC_RP_ExpReportBill t2
                       WHERE #t2.SettleID=t1.SettleID AND
                         t2.Date BETWEEN FBegDate AND FEndDate AND t2.ExpFlag = 1 AND t2.AccountID = t1.AccountID
                         AND t2.DeptID = t1.DeptID AND t2.EmpID = t1.EmpID AND t2.SonID = t1.SonID);

    UPDATE tmp_DataTable t1, tmp_TempCheck t2
    SET t1.Credit = t2.Credit
    WHERE #t1.SettleID=t2.SettleID AND
      t1.AccountID = t2.AccountID AND t1.DeptID = t2.DeptID AND t1.EmpID = t2.EmpID
      AND t1.SonID = t2.SonID;

    -- 插入本期刚新增的账户数据到结账表
    INSERT INTO T_SC_RP_ExpBal (id, create_name, create_by, create_date, Year, Period, #SettleID,
                                AccountID, DeptID, EmpID, Credit, YtdCredit, Debit, YtdDebit, SonID, fid)
      SELECT
        replace(uuid(), '-', ''),
        FCreateName,
        FCreateBy,
        now(),
        FYear,
        FPeriod,
        #t1.SettleID,
        t1.AccountID,
        t1.DeptID,
        t1.EmpID,
        t1.Credit,
        t1.Credit,
        t1.Debit,
        t1.Debit,
        SonID,
        FFid
      FROM tmp_DataTable t1
      WHERE NOT EXISTS(SELECT 1
                       FROM T_SC_RP_ExpBal t2
                       WHERE #t2.SettleID=t1.SettleID AND
                         t2.AccountID = t1.AccountID
                         AND t2.DeptID = t1.DeptID AND t2.EmpID = t1.EmpID AND t2.SonID = t1.SonID);

    -- 更新已存在的数据
    UPDATE T_SC_RP_ExpBal t1, tmp_DataTable t2
    SET t1.YtdCredit = t1.YtdCredit - t1.Credit + t2.Credit, t1.YtdDebit = t1.YtdDebit - t1.Debit + t2.Debit,
      t1.Credit      = t2.Credit, t1.Debit = t2.Debit
    WHERE #t1.SettleID=t2.SettleID AND
      t1.AccountID = t2.AccountID AND t1.DeptID = t2.DeptID AND t1.EmpID = t2.EmpID
      AND t1.Year = FYear AND t1.Period = FPeriod AND t1.SonID = t2.SonID;

    -- 更新本期删除掉的数据[这种情况存在于反结账后的数据操作]
    UPDATE T_SC_RP_ExpBal t1
    SET t1.YtdCredit = t1.YtdCredit - t1.Credit, t1.YtdDebit = t1.YtdDebit - t1.Debit, t1.Credit = 0, t1.Debit = 0
    WHERE NOT EXISTS(SELECT 1
                     FROM tmp_DataTable t2
                     WHERE #t2.SettleID=t1.SettleID AND
                       t2.AccountID = t1.AccountID
                       AND t2.DeptID = t1.DeptID AND t2.EmpID = t1.EmpID AND t2.SonID = t1.SonID)
          AND ((t1.Credit <> 0) OR (t1.Debit <> 0)) AND t1.Year = FYear AND t1.Period = FPeriod;

    -- 计算本期期末数据[期初+收入-支出]
    UPDATE T_SC_RP_ExpBal
    SET EndBal = BegBal + Debit - Credit
    WHERE Year = FYear AND Period = FPeriod;

    -- 产生下期期初数据
    INSERT INTO T_SC_RP_ExpBal (id, create_name, create_by, create_date, Year, Period, #SettleID,
                                AccountID, DeptID, EmpID, BegBal, YtdCredit, YtdDebit, SonID, fid)
      SELECT
        replace(uuid(), '-', ''),
        FCreateName,
        FCreateBy,
        now(),
        FNexYear,
        FNexPeriod,
        #SettleID,
        AccountID,
        DeptID,
        EmpID,
        EndBal,
        YtdCredit,
        YtdDebit,
        SonID,
        FNextFid
      FROM T_SC_RP_ExpBal
      WHERE Year = FYear AND Period = FPeriod;

    DROP TEMPORARY TABLE IF EXISTS tmp_DataTable;
    DROP TEMPORARY TABLE IF EXISTS tmp_TempCheck;
  END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for P_SC_GET_PRICE
-- ----------------------------
DROP PROCEDURE IF EXISTS `P_SC_GET_PRICE`;
DELIMITER ;;
CREATE DEFINER=`smxdlspyxgs`@`%` PROCEDURE `P_SC_GET_PRICE`(IN  `v_billId`     VARCHAR(36), IN `v_customerId` VARCHAR(36),
                                  IN  `v_itemId`     VARCHAR(36), IN `v_unitId` VARCHAR(36), IN `v_no` DOUBLE,
                                  OUT `v_price`      DOUBLE, OUT `v_price_type` VARCHAR(4))

  BEGIN

    DECLARE v_purchase_enable_pri_seq SMALLINT;
    -- 采购模块启用价格调用顺序
    DECLARE v_sales_enable_pri_seq VARCHAR(10);
    -- 销售模块启用价格调用顺序
    DECLARE v_pbill VARCHAR(50);
    -- 父模块名
    DECLARE v_purchase_cfgPriOne VARCHAR(10);
    -- 采购单价第一优先
    DECLARE v_purchase_cfgPriSec VARCHAR(10);
    -- 采购单价第二优先
    DECLARE v_sales_cfgPriOne VARCHAR(10);
    -- 销售单价第一优先
    DECLARE v_sales_cfgPriSec VARCHAR(10);
    -- 销售单价第二优先
    DECLARE v_sales_cfgPriThi VARCHAR(10);
    -- 销售单价第三优先
    DECLARE v_price_field VARCHAR(100);
    -- 单据默认单价字段
    DECLARE v_sql_price_setting VARCHAR(1000);
    -- 取单价设置中默认单价的动态Sql语句
    DECLARE v_purchase_loop SMALLINT;
    -- 采购取价级别
    DECLARE v_sales_loop SMALLINT;
    -- 销售取价级别
    DECLARE v_temp VARCHAR(10);
    -- 临时变量

    SELECT content
    INTO v_purchase_enable_pri_seq
    FROM
      t_s_config
    WHERE
      CODE = 'CFG_PURCHASE_ENABLE_PRI_SEQ';

    SELECT content
    INTO v_sales_enable_pri_seq
    FROM
      t_s_config
    WHERE
      CODE = 'CFG_SALES_ENABLE_PRI_SEQ';

    SELECT content
    INTO v_purchase_cfgPriOne
    FROM
      t_s_config
    WHERE
      CODE = 'CFG_PURCHASE_PRI_ONE';

    SELECT content
    INTO v_purchase_cfgPriSec
    FROM
      t_s_config
    WHERE
      CODE = 'CFG_PURCHASE_PRI_SEC';

    SELECT content
    INTO v_sales_cfgPriOne
    FROM
      t_s_config
    WHERE
      CODE = 'CFG_SALES_PRI_ONE';

    SELECT content
    INTO v_sales_cfgPriSec
    FROM
      t_s_config
    WHERE
      CODE = 'CFG_SALES_PRI_SEC';

    SELECT content
    INTO v_sales_cfgPriThi
    FROM
      t_s_config
    WHERE
      CODE = 'CFG_SALES_PRI_THI';

    -- 通过单据类型查找是销售模块或者采购模块
    SELECT b.bill_name
    INTO v_pbill
    FROM
      t_s_bill_info a,
      t_s_bill_info b
    WHERE
      a.pid = b.id
      AND a.bill_id = v_billId;


    IF v_pbill = '经销商进货管理'
    THEN

      IF v_purchase_enable_pri_seq = '1'
      THEN -- 判断当启用采购取价顺序

        IF v_purchase_cfgPriOne IS NOT NULL
           AND v_purchase_cfgPriOne <> ''
        THEN

          SET v_purchase_loop = 1;


        END
        IF;


        IF v_purchase_cfgPriSec IS NOT NULL
           AND v_purchase_cfgPriSec <> ''
        THEN

          SET v_purchase_loop = 2;


        END
        IF;


        SET v_temp = v_purchase_cfgPriOne;

        pbl :
        WHILE v_purchase_loop > 0 DO
          CASE v_temp
            WHEN 'sp'
            THEN -- 供货报价
              -- 供货报价单返回符合条件的商品价格
              -- 目前供货报价单还未开发，待实现修改下面代码
              SET v_price = 0;


              IF v_price IS NOT NULL
              THEN
                -- 如果调价政策单有取到单价则直接返回并结束存取过程
                SET v_price_type = 'sp';
                -- 取价类型为供货报价
                LEAVE pbl;


              END
              IF;


            WHEN 'ps'
            THEN -- 单价设置返回符合条件的商品价格
              SELECT 'enter ps';
              SELECT b.price_field
              INTO v_price_field
              FROM
                t_s_bill_info b
              WHERE
                b.bill_id = v_billId;


              SET v_sql_price_setting = concat('select ', v_price_field,
                                               ' into @v_price1 from t_sc_item_price p where p.ITEMID=? and p.id=? ');


              SET @SQL = v_sql_price_setting;

              PREPARE stmt
              FROM
                @SQL;


              SET @parm1 = v_itemId;


              SET @parm2 = v_unitId;

              EXECUTE stmt
              USING @parm1,
                @parm2;

              DEALLOCATE PREPARE stmt;


              SET v_price = @v_price1;


              IF v_price IS NOT NULL
              THEN
                -- 如果单价设置有取到单价则直接返回并结束存取过程
                SET v_price_type = 'ps';
                -- 取价类型为单价设置
                LEAVE pbl;


              ELSE
                -- 最后一次循环且单价设置没有取到值则返回0
                IF v_purchase_loop = 1
                THEN

                  SET v_price = 0;


                END
                IF;


              END
              IF;


          END CASE;


          SET v_purchase_loop = v_purchase_loop - 1;


          IF 2 - v_purchase_loop + 1 = 2
          THEN

            SET v_temp = v_purchase_cfgPriSec;


          END
          IF;


        END
        WHILE pbl;

      -- 不启用
      ELSE
        -- 单价设置返回符合条件的商品价格
        SELECT b.price_field
        INTO v_price_field
        FROM
          t_s_bill_info b
        WHERE
          b.bill_id = v_billId;


        SET v_sql_price_setting = concat('select ', v_price_field,
                                         ' into @v_price1 from t_sc_item_price p where p.ITEMID=? and p.id=? ');


        SET @SQL = v_sql_price_setting;

        PREPARE stmt
        FROM
          @SQL;


        SET @parm1 = v_itemId;


        SET @parm2 = v_unitId;

        EXECUTE stmt
        USING @parm1,
          @parm2;

        DEALLOCATE PREPARE stmt;


        SET v_price = @v_price1;
        SET v_price_type = 'ps';
        -- 取价类型为供货报价

        IF v_price IS NULL
        THEN
          -- 如果单价设置没有取到单价则直接返回0
          SET v_price = 0;


        END
        IF;


      END
      IF;

    -- 判断当启用采购取价顺序结束
    END
    IF;

    -- 经销商进货管理结束
    select v_pbill;
    IF v_pbill = '经销商销货管理' OR v_pbill = '总部经销管理'
    THEN
      -- 经销商销售管理开始
      select v_sales_enable_pri_seq;
      IF v_sales_enable_pri_seq = '1'
      THEN
        -- 判断当启用销售取价顺序
        IF v_sales_cfgPriOne IS NOT NULL
           AND v_sales_cfgPriOne <> ''
        THEN

          SET v_sales_loop = 1;


        END
        IF;


        IF v_sales_cfgPriSec IS NOT NULL
           AND v_sales_cfgPriSec <> ''
        THEN

          SET v_sales_loop = 2;


        END
        IF;


        IF v_sales_cfgPriThi IS NOT NULL
           AND v_sales_cfgPriThi <> ''
        THEN

          SET v_sales_loop = 3;


        END
        IF;


        SET v_temp = v_sales_cfgPriOne;

        select v_sales_loop;
        sbl :
        WHILE v_sales_loop > 0 DO
          CASE v_temp
            WHEN 'pap'
            THEN

              -- 调价政策单返回符合条件的商品价格
              SELECT i.NEWPRICE
              INTO v_price
              FROM
                t_sc_prcply p,
                t_sc_prcplyentry1 c,
                t_sc_prcplyentry2 i
              WHERE
                p.ID = c.INTERID
                AND p.id = i.INTERID
                AND p.CHECKSTATE = 2
                AND c.itemid = v_customerId
                AND i.itemid = v_itemId
                AND i.UNITID = v_unitId
                AND (
                  i.BEGDATE <= SYSDATE()
                  AND i.ENDDATE >= SYSDATE()
                )
                AND (
                  i.BEGQTY <= v_no
                  AND (i.ENDQTY >= v_no OR i.ENDQTY = 0)
                )
              ORDER BY
                p.DATE DESC
              LIMIT 1;
              select v_price;

              IF v_price IS NOT NULL
              THEN
                -- 如果调价政策单有取到单价则直接返回并结束存取过程
                SET v_price_type = 'pap';
                -- 取价类型为调价政策
                LEAVE sbl;
              -- 如果调价政策单没有取到单价且取价级别为一级则直接返回0
              ELSE

                IF v_sales_loop = 1
                THEN

                  SET v_price = 0;
                END
                IF;

              END
              IF;


            WHEN 'sq'
            THEN
              -- 销售报价单返回符合条件的商品价格
              SELECT i.PRICE
              INTO v_price
              FROM
                t_sc_quote q,
                t_sc_quotecustomer c,
                t_sc_quoteitems i
              WHERE
                q.ID = c.fid
                AND q.id = i.fid
                AND q.CHECKSTATE = 2
                AND c.ITEMID = v_customerId
                AND i.ITEMID = v_itemId
                AND i.UNITID = v_unitId
                AND q.inuredate <= SYSDATE()
                AND (
                  i.BEGQTY <= v_no
                  AND (i.ENDQTY >= v_no OR i.ENDQTY = 0)
                )
              ORDER BY
                q.inuredate DESC
              LIMIT 1;

              IF v_price IS NOT NULL
              THEN
                -- 如果销售报价单有取到单价则直接返回并结束存取过程
                SET v_price_type = 'sq';
                -- 取价类型为销售报价
                LEAVE sbl;
              -- 如果销售报价单没有取到单价且取价级别为一级则直接返回0
              ELSE

                IF v_sales_loop = 1
                THEN

                  SET v_price = 0;
                END
                IF;

              END
              IF;


            WHEN 'ps'
            THEN
              -- 单价设置返回符合条件的商品价格
              SELECT b.price_field
              INTO v_price_field
              FROM
                t_s_bill_info b
              WHERE
                b.bill_id = v_billId;


              SET v_sql_price_setting = concat('select ', v_price_field,
                                               ' into @v_price1 from t_sc_item_price p where p.ITEMID=? and p.id=? ');


              SET @SQL = v_sql_price_setting;

              PREPARE stmt
              FROM
                @SQL;


              SET @parm1 = v_itemId;


              SET @parm2 = v_unitId;

              EXECUTE stmt
              USING @parm1,
                @parm2;

              DEALLOCATE PREPARE stmt;


              SET v_price = @v_price1;

              SELECT v_price;


              IF v_price IS NOT NULL
              THEN
                -- 如果单价设置有取到单价则直接返回并结束存取过程
                SET v_price_type = 'ps';
                -- 取价类型为单价设置
                LEAVE sbl;

              -- 如果单价设置没有取到单价且取价级别为一级则直接返回0
              ELSE

                IF v_sales_loop = 1
                THEN

                  SET v_price = 0;


                END
                IF;


              END
              IF;


          END CASE;


          SET v_sales_loop = v_sales_loop - 1;


          IF 3 - v_sales_loop + 1 = 2
          THEN

            SET v_temp = v_sales_cfgPriSec;


          END
          IF;


          IF 3 - v_sales_loop + 1 = 3
          THEN

            SET v_temp = v_sales_cfgPriThi;


          END
          IF;


        END
        WHILE sbl;

      -- 不启用单价顺序
      ELSE
        -- 单价设置返回符合条件的商品价格
        select v_billId;
        SELECT b.price_field
        INTO v_price_field
        FROM
          t_s_bill_info b
        WHERE
          b.bill_id = v_billId;

        select v_price_field;
        SET v_sql_price_setting = concat('select ', v_price_field,
                                         ' into @v_price1 from t_sc_item_price p where p.ITEMID=? and p.id=? ');


        SET @SQL = v_sql_price_setting;

        PREPARE stmt
        FROM
          @SQL;


        SET @parm1 = v_itemId;


        SET @parm2 = v_unitId;

        EXECUTE stmt
        USING @parm1,
          @parm2;

        DEALLOCATE PREPARE stmt;


        SET v_price = @v_price1;
        SET v_price_type = 'ps';
        -- 取价类型为单价设置

        IF v_price IS NULL
        THEN
          -- 如果单价设置没有有取到单价则直接返回0
          SET v_price = 0;

        END
        IF;

      END
      IF;


    END
    IF;


  END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for P_SC_IC_Settle
-- ----------------------------
DROP PROCEDURE IF EXISTS `P_SC_IC_Settle`;
DELIMITER ;;
CREATE PROCEDURE `P_SC_IC_Settle`(IN `FYear`    INT, IN `FPeriod` INT, IN `FNexYear` INT, IN `FNexPeriod` INT,
                                  IN `FBegDate` DATETIME, IN `FEndDate` DATETIME, IN FCreateName VARCHAR(50),
                                  IN FCreateBy  VARCHAR(50))
  BEGIN
    #Routine body goes here...
    DECLARE FBDate DATETIME;
    DECLARE FEDate DATETIME;
    DECLARE FFid VARCHAR(50);
    DECLARE FNextFid VARCHAR(50);
    DECLARE iTemp INT;
    -- 调用函数前请存在当前期别和下期期别的记录，且state为0未结账
    SET FFid = '';
    SET FNextFid = '';
    SELECT count(*)
    INTO iTemp
    FROM t_sc_account_stage t
    WHERE year(t.date) = FYear AND month(t.date) = FPeriod;
    IF iTemp > 0
    THEN
      SELECT t.id
      INTO FFid
      FROM t_sc_account_stage t
      WHERE year(t.date) = FYear AND month(t.date) = FPeriod;
    END IF;
    SELECT count(*)
    INTO iTemp
    FROM t_sc_account_stage t
    WHERE year(t.date) = FNexYear AND month(t.date) = FNexPeriod;
    IF iTemp > 0
    THEN
      SELECT t.id
      INTO FNextFid
      FROM t_sc_account_stage t
      WHERE year(t.date) = FNexYear AND month(t.date) = FNexPeriod;
    END IF;
    DROP TEMPORARY TABLE IF EXISTS tmp_DataTable;

    CREATE TEMPORARY TABLE tmp_DataTable
    (
      ItemID     VARCHAR(50),
      StockID    VARCHAR(50),
      BatchNo    VARCHAR(100),
      -- KFDate DATETIME NULL,
      -- KFPeriod INT,
      -- PeriodDate DATETIME NULL,
      Send       DECIMAL(20, 10) DEFAULT 0, -- 发出数量
      SecSend    DECIMAL(20, 10) DEFAULT 0, -- 发出辅助数量
      Credit     DECIMAL(20, 10) DEFAULT 0, -- 发出成本金额
      Receive    DECIMAL(20, 10) DEFAULT 0, -- 收入数量
      SecReceive DECIMAL(20, 10) DEFAULT 0, -- 收入辅助数量
      Debit      DECIMAL(20, 10) DEFAULT 0-- 收入成本金额
    );

    DROP TEMPORARY TABLE IF EXISTS tmp_TempReceive;

    CREATE TEMPORARY TABLE tmp_TempReceive
    (
      ItemID     VARCHAR(50),
      StockID    VARCHAR(50),
      BatchNo    VARCHAR(100),
      -- KFDate DATETIME NULL,
      -- KFPeriod INT,
      -- PeriodDate DATETIME NULL,
      -- Receive DECIMAL(20,10) DEFAULT 0,-- 发出数量
      -- SecSend DECIMAL(20,10) DEFAULT 0,-- 发出辅助数量
      -- Credit DECIMAL(20,10) DEFAULT 0,-- 发出成本金额
      Receive    DECIMAL(20, 10) DEFAULT 0, -- 收入数量
      SecReceive DECIMAL(20, 10) DEFAULT 0, -- 收入辅助数量
      Debit      DECIMAL(20, 10) DEFAULT 0-- 收入成本金额
    );

    -- 统计本期发出数据
    INSERT INTO tmp_DataTable (ItemID, StockID, BatchNo, Send, SecSend, Credit)
      SELECT
        ItemID,
        StockID,
        BatchNo,
        SUM(Qty)    AS Send,
        SUM(SecQty) AS SecSend,
        SUM(Amount) AS Credit
      FROM T_SC_IC_SpeedBal
      WHERE Date BETWEEN FBegDate AND FEndDate AND Flag = -1
      GROUP BY StockID, ItemID, BatchNo;
    -- 统计本期收入数据
    INSERT INTO tmp_TempReceive (ItemID, StockID, BatchNo, Receive, SecReceive, Debit)
      SELECT
        ItemID,
        StockID,
        BatchNo,
        SUM(Qty)    AS Receive,
        SUM(SecQty) AS SecReceive,
        SUM(Amount) AS Debit
      FROM T_SC_IC_SpeedBal
      WHERE Date BETWEEN FBegDate AND FEndDate AND Flag = 1
      GROUP BY StockID, ItemID, BatchNo;

    INSERT INTO tmp_DataTable (ItemID, StockID, BatchNo, Receive, SecReceive, Debit)
      SELECT
        t1.ItemID,
        t1.StockID,
        t1.BatchNo,
        t1.Receive,
        t1.SecReceive,
        t1.Debit
      FROM tmp_TempReceive t1
      -- WHERE NOT EXISTS(SELECT 1 FROM tmp_DataTable t2 WHERE t2.ItemID=t1.ItemID AND t2.StockID=t1.StockID AND t2.BatchNo=t1.BatchNo);
      WHERE NOT EXISTS(SELECT 1
                       FROM T_SC_IC_SpeedBal t2
                       WHERE Date BETWEEN FBegDate AND FEndDate AND Flag = -1 AND t2.ItemID = t1.ItemID AND
                             t2.StockID = t1.StockID AND t2.BatchNo = t1.BatchNo);

    UPDATE tmp_DataTable t1, tmp_TempReceive t2
    SET t1.Receive = t2.Receive, t1.SecReceive = t2.SecReceive, t1.Debit = t2.Debit
    WHERE t1.ItemID = t2.ItemID AND t1.StockID = t2.StockID AND t1.BatchNo = t2.BatchNo;

    -- 插入本期刚新增的商品数据到结账表
    INSERT INTO T_SC_IC_Bal (id, create_name, create_by, create_date, Year, Period, ItemID, StockID, BatchNo, Send, SecSend,
                             Credit, YtdSend, SecYtdSend,
                             YtdCredit, Receive, SecReceive,
                             Debit, YtdReceive, SecYtdReceive, YtdDebit, fid)
      SELECT
        replace(uuid(), '-', ''),
        FCreateName,
        FCreateBy,
        now(),
        FYear,
        FPeriod,
        t1.ItemID,
        t1.StockID,
        t1.BatchNo,
        t1.Send,
        t1.SecSend,
        t1.Credit,
        t1.Send,
        t1.SecSend,
        t1.Credit,
        t1.Receive,
        t1.SecReceive,
        t1.Debit,
        t1.Receive,
        t1.SecReceive,
        t1.Debit,
        FFid
      FROM tmp_DataTable t1
      WHERE NOT EXISTS(SELECT 1
                       FROM T_SC_IC_Bal
                       WHERE ItemID = t1.ItemID AND StockID = t1.StockID AND BatchNo = t1.BatchNo);

    -- 更新已存在的数据
    UPDATE T_SC_IC_Bal t1, tmp_DataTable t2
    SET t1.YtdSend     = t1.YtdSend - t1.Send + t2.Send, t1.SecYtdSend = t1.SecYtdSend - t1.SecSend + t2.SecSend,
      t1.YtdCredit     = t1.YtdCredit - t1.Credit + t2.Credit,
      t1.YtdReceive    = t1.YtdReceive - t1.Receive + t2.Receive,
      t1.SecYtdReceive = t1.SecYtdReceive - t1.SecReceive + t2.SecReceive,
      t1.YtdDebit      = t1.YtdDebit - t1.Debit + t2.Debit,
      t1.Send          = t2.Send, t1.SecSend = t2.SecSend, t1.Credit = t2.Credit, t1.Receive = t2.Receive,
      t1.SecReceive    = t2.SecReceive, t1.Debit = t2.Debit
    WHERE t1.ItemID = t2.ItemID AND t1.StockID = t2.StockID AND t1.BatchNo = t2.BatchNo AND t1.Year = FYear AND
          t1.Period = FPeriod;

    -- 更新本期删除掉的数据[这种情况存在于反结账后的数据操作]
    UPDATE T_SC_IC_Bal t1
    SET t1.YtdSend  = t1.YtdSend - t1.Send, t1.SecYtdSend = t1.SecYtdSend - t1.SecSend,
      t1.YtdCredit  = t1.YtdCredit - t1.Credit,
      t1.YtdReceive = t1.YtdReceive - t1.Receive, t1.SecYtdReceive = t1.SecYtdReceive - t1.SecReceive,
      t1.YtdDebit   = t1.YtdDebit - t1.Debit,
      t1.Send       = 0, t1.SecSend = 0, t1.Credit = 0, t1.Receive = 0, t1.SecReceive = 0, t1.Debit = 0
    WHERE NOT EXISTS(SELECT 1
                     FROM tmp_DataTable t2
                     WHERE t2.ItemID = t1.ItemID AND t2.StockID = t1.StockID AND t2.BatchNo = t1.BatchNo) AND
          ((t1.Send <> 0) OR (t1.Receive <> 0))
          AND t1.Year = FYear AND t1.Period = FPeriod;

    -- 计算本期期末数据[期初+收入-发出]
    UPDATE T_SC_IC_Bal
    SET EndQty = BegQty + Receive - Send, EndBal = BegBal + Debit - Credit, SecEndQty = SecBegQty + SecReceive - SecSend
    WHERE Year = FYear AND Period = FPeriod;

    -- 产生下期期初数据
    INSERT INTO T_SC_IC_Bal (id, create_name, create_by, create_date, Year, Period, ItemID, StockID, BatchNo, BegQty, BegBal, YtdSend, SecYtdSend,
                             YtdCredit, YtdReceive, SecYtdReceive, YtdDebit, SecBegQty, fid)
      SELECT
        replace(uuid(), '-', ''),
        FCreateName,
        FCreateBy,
        now(),
        FNexYear,
        FNexPeriod,
        ItemID,
        StockID,
        BatchNo,
        EndQty,
        EndBal,
        YtdSend,
        SecYtdSend,
        YtdCredit,
        YtdReceive,
        SecYtdReceive,
        YtdDebit,
        SecEndQty,
        FNextFid
      FROM T_SC_IC_Bal
      WHERE Year = FYear AND Period = FPeriod;

    -- 将下期计算方法为“月加权”并且已产生业务数据的货品计算状态更新为“未计算”
    CALL P_SC_SYS_GetCurDate(FNexYear, FNexPeriod, FBDate, FEDate);
    IF EXISTS(SELECT 1
              FROM T_SC_IC_SpeedBal t1
              WHERE t1.Date BETWEEN FBDate AND FEDate AND
                    EXISTS(SELECT 1
                           FROM T_SC_ICItem
                           WHERE Track = 37 AND ItemID = t1.ItemID))
    THEN
      UPDATE T_SC_IC_Inventory t1
      SET t1.Status = 1
      WHERE EXISTS(SELECT 1
                   FROM T_SC_IC_SpeedBal s1
                   WHERE s1.Date BETWEEN FBDate AND FEDate AND s1.ItemID = t1.ItemID
                         AND EXISTS(SELECT 1
                                    FROM T_SC_ICItem
                                    WHERE Track = 37 AND ItemID = s1.ItemID)) AND
            EXISTS(SELECT 1
                   FROM T_SC_Stock
                   WHERE TypeID = 1 AND ItemID = t1.StockID);
    END IF;

    DROP TEMPORARY TABLE IF EXISTS tmp_DataTable;

    DROP TEMPORARY TABLE IF EXISTS tmp_TempReceive;
  END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for P_SC_RP_Settle
-- ----------------------------
DROP PROCEDURE IF EXISTS `P_SC_RP_Settle`;
DELIMITER ;;
CREATE PROCEDURE `P_SC_RP_Settle`(IN `FYear`     INT, IN `FPeriod` INT, IN `FNexYear` INT, IN `FNexPeriod` INT,
                                  IN `FBegDate`  DATETIME, IN `FEndDate` DATETIME, IN `FCreateName` VARCHAR(50),
                                  IN `FCreateBy` VARCHAR(50))
  BEGIN
    #Routine body goes here...
    DECLARE FFid VARCHAR(50);
    DECLARE FNextFid VARCHAR(50);
    DECLARE iTemp INT;
    -- 调用函数前请存在当前期别和下期期别的记录，且state为0未结账
    SET FFid = '';
    SET FNextFid = '';
    SELECT count(*)
    INTO iTemp
    FROM t_sc_account_stage t
    WHERE year(t.date) = FYear AND month(t.date) = FPeriod;
    IF iTemp > 0
    THEN
      SELECT t.id
      INTO FFid
      FROM t_sc_account_stage t
      WHERE year(t.date) = FYear AND month(t.date) = FPeriod;
    END IF;
    SELECT count(*)
    INTO iTemp
    FROM t_sc_account_stage t
    WHERE year(t.date) = FNexYear AND month(t.date) = FNexPeriod;
    IF iTemp > 0
    THEN
      SELECT t.id
      INTO FNextFid
      FROM t_sc_account_stage t
      WHERE year(t.date) = FNexYear AND month(t.date) = FNexPeriod;
    END IF;
    DROP TEMPORARY TABLE IF EXISTS tmp_DataTable;
    CREATE TEMPORARY TABLE tmp_DataTable
    (
      ItemClassID VARCHAR(50), -- 核算类别
      ItemID      VARCHAR(50), -- 核算项目
      DeptID      VARCHAR(50),
      EmpID       VARCHAR(50),
      AllAmount   DECIMAL(20, 10) DEFAULT 0, -- 应收应付金额
      CheckAmount DECIMAL(20, 10) DEFAULT 0, -- 收付款金额
      RP          SMALLINT,
      SonID       VARCHAR(50)
    );
    DROP TEMPORARY TABLE IF EXISTS tmp_TempCheck;
    CREATE TEMPORARY TABLE tmp_TempCheck
    (
      ItemClassID VARCHAR(50), -- 核算类别
      ItemID      VARCHAR(50), -- 核算项目
      DeptID      VARCHAR(50),
      EmpID       VARCHAR(50),
      AllAmount   DECIMAL(20, 10) DEFAULT 0, -- 应收应付金额
      CheckAmount DECIMAL(20, 10) DEFAULT 0, -- 收付款金额
      RP          SMALLINT,
      SonID       VARCHAR(50)
    );
    -- T_RP_Contact应收应付日结表没有，改用V_SC_RP_PReportBill视图
    -- 统计本期应收应付数据
    INSERT INTO tmp_DataTable (#ItemClassID,
                               ItemID, DeptID, EmpID, AllAmount, RP, SonID)
      SELECT
        #ItemClassID,
        ItemID,
        DeptID,
        EmpID,
        SUM(AllAmount),
        RP,
        SonID
      FROM V_SC_RP_Contact
      WHERE Date BETWEEN FBegDate AND FEndDate AND CheckFlag = 0
      GROUP BY #ItemClassID,
        ItemID, DeptID, EmpID, RP, SonID;
    -- 统计本期收付款数据
    INSERT INTO tmp_TempCheck (#ItemClassID,
                               ItemID, DeptID, EmpID, CheckAmount, RP, SonID)
      SELECT
        #ItemClassID,
        ItemID,
        DeptID,
        EmpID,
        SUM(CheckAmount) AS CheckAmount,
        RP,
        SonID # INTO tmp_TempCheck
      FROM V_SC_RP_Contact
      WHERE Date BETWEEN FBegDate AND FEndDate AND CheckFlag <> 0
      GROUP BY #ItemClassID,
        ItemID, DeptID, EmpID, RP, SonID;

    INSERT INTO tmp_DataTable (#ItemClassID,
                               ItemID, DeptID, EmpID, CheckAmount, RP, SonID)
      SELECT
        #ItemClassID,
        ItemID,
        t1.DeptID,
        t1.EmpID,
        t1.CheckAmount,
        t1.RP,
        t1.SonID
      FROM tmp_TempCheck t1
      #WHERE NOT EXISTS(SELECT 1 FROM tmp_DataTable t2 WHERE #t2.ItemClassID=t1.ItemClassID AND
      #      t2.ItemID=t1.ItemID AND t2.RP=t1.RP AND t2.DeptID=t1.DeptID AND t2.EmpID=t1.EmpID AND t2.SonID=t1.SonID)
      WHERE NOT EXISTS(SELECT 1
                       FROM V_SC_RP_Contact t2
                       WHERE #t2.ItemClassID=t1.ItemClassID AND
                         t2.Date BETWEEN FBegDate AND FEndDate AND t2.CheckFlag = 0 AND t2.ItemID = t1.ItemID AND
                         t2.RP = t1.RP AND t2.DeptID = t1.DeptID AND t2.EmpID = t1.EmpID AND t2.SonID = t1.SonID);

    UPDATE tmp_DataTable t1, tmp_TempCheck t2
    SET t1.CheckAmount = t2.CheckAmount
    WHERE #t1.ItemClassID=t2.ItemClassID AND
      t1.ItemID = t2.ItemID AND t1.RP = t2.RP AND t1.DeptID = t2.DeptID
      AND t1.EmpID = t2.EmpID AND t1.SonID = t2.SonID;

    -- 插入本期刚新增的应收应付数据到结账表
    INSERT INTO T_SC_RP_ContactBal (id, create_name, create_by, create_date, RP, Year, Period, #ItemClassID,
                                    ItemID, DeptID, EmpID, Credit, YtdCredit, Debit, YtdDebit, SonID, fid)
      SELECT
        replace(uuid(), '-', ''),
        FCreateName,
        FCreateBy,
        now(),
        t1.RP,
        FYear,
        FPeriod,
        #ItemClassID,
        ItemID,
        t1.DeptID,
        t1.EmpID,
        t1.CheckAmount,
        t1.CheckAmount,
        t1.AllAmount,
        t1.AllAmount,
        t1.SonID,
        FFid
      FROM tmp_DataTable t1
      WHERE NOT EXISTS(SELECT 1
                       FROM T_SC_RP_ContactBal t2
                       WHERE #t2.ItemClassID=t1.ItemClassID AND
                         t2.ItemID = t1.ItemID AND t2.RP = t1.RP
                         AND t2.DeptID = t1.DeptID AND t2.EmpID = t1.EmpID AND t2.SonID = t1.SonID);

    -- 更新已存在的数据
    UPDATE T_SC_RP_ContactBal t1, tmp_DataTable t2
    SET t1.YtdCredit = t1.YtdCredit - t1.Credit + t2.CheckAmount, t1.YtdDebit = t1.YtdDebit - t1.Debit + t2.AllAmount,
      t1.Credit      = t2.CheckAmount, t1.Debit = t2.AllAmount
    WHERE #t1.ItemClassID=t2.ItemClassID AND
      t1.ItemID = t2.ItemID AND t1.RP = t2.RP AND t1.Year = FYear AND t1.Period = FPeriod
      AND t1.DeptID = t2.DeptID AND t1.EmpID = t2.EmpID AND t1.SonID = t2.SonID;

    -- 更新本期删除掉的数据[这种情况存在于反结账后的数据操作]
    UPDATE T_SC_RP_ContactBal t1
    SET t1.YtdCredit = t1.YtdCredit - t1.Credit, t1.YtdDebit = t1.YtdDebit - t1.Debit, t1.Credit = 0, t1.Debit = 0
    WHERE NOT EXISTS(SELECT 1
                     FROM tmp_DataTable t2
                     WHERE #t2.ItemClassID=t1.ItemClassID AND
                       t2.ItemID = t1.ItemID AND t2.RP = t1.RP
                       AND t2.DeptID = t1.DeptID AND t2.EmpID = t1.EmpID AND t2.SonID = t1.SonID)
          AND ((t1.Credit <> 0) OR (t1.Debit <> 0)) AND t1.Year = FYear AND t1.Period = FPeriod;

    -- 计算本期期末数据[期初+应收、付-收、付款]
    UPDATE T_SC_RP_ContactBal
    SET EndBal = BegBal + Debit - Credit
    WHERE Year = FYear AND Period = FPeriod;

    -- 产生下期期初数据
    INSERT INTO T_SC_RP_ContactBal (id, create_name, create_by, create_date, RP, Year, Period, #ItemClassID,
                                    ItemID, DeptID, EmpID, BegBal, YtdCredit, YtdDebit, SonID, fid)
      SELECT
        replace(uuid(), '-', ''),
        FCreateName,
        FCreateBy,
        now(),
        RP,
        FNexYear,
        FNexPeriod,
        #ItemClassID,
        ItemID,
        DeptID,
        EmpID,
        EndBal,
        YtdCredit,
        YtdDebit,
        SonID,
        FNextFid
      FROM T_SC_RP_ContactBal
      WHERE Year = FYear AND Period = FPeriod;

    DROP TEMPORARY TABLE IF EXISTS tmp_DataTable;

    DROP TEMPORARY TABLE IF EXISTS tmp_TempCheck;
  END
;;
DELIMITER ;
