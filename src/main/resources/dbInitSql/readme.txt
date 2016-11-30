说明：
1.该目录用于创建新账套，数据复制后，需要执行创建视图、函数以及清除相关单据数据的SQL脚本。
2.其中视图脚本版本截止2016年9月27日192.168.0.245/scm库的视图脚本，如有更新，请更新对应的v_sc_all_view.sql文件。特别注意：要在函数的最头部声明用哪什么符号代表函数的结束，并在函数尾部使用它。
3.其中函数脚本版本截止2016年9月27日192.168.0.245/scm库的函数脚本，如有更新，请更新对应的f_sc_all_function.sql文件。
4.其中清除数据脚本拟为测试草稿，待最终时请补充创建新账套需要清除相关数据表。

建账套的执行顺序：建表-函数-视图-存储过程-清除数据。
今天我们就先来个小约定：
因为有些视图中会用到自定义函数，有些存储过程会用到视图，
建议今后如果你是写给视图用的，你就写成函数；你是要用视图的，你就写成存储过程。
(1)f_sc_all_function.sql
  排前：GETCHILDLIST，getChildList_depart
  一定要把定义者去掉(scm或root)
(2)v_sc_all_view.sql
  所有视图：要排前的：v_sc_checkspeedbal_sub_ym，v_sc_checkspeedbal_sub，v_sc_checkstage_sub,v_sc_rp_preportbill,v_sc_rp_rreportbill
  一定要把定义者去掉(scm或root)
  【搜索工程中sql文件含有`root` 或 `scm`都要处理掉】
(3)f_sc_all_procedure.sql
  排前：P_SC_SYS_GetCurDate
  一定要把定义者去掉(scm或root)
(4)t_sc_delet_from.sql

以下替换方法仅供参考：
    视图更快捷的方法：将比对有差异的视图,进行navicat美化sql后，做以下几个替换：
	    ALGORITHM=UNDEFINED
        DEFINER=`scm`@`%`
        SQL SECURITY DEFINER
	  为空
	  将replace view改为CREATE OR REPLACE
	  ;替换为$$
	  第一行前加：DELIMITER $$
	函数更快捷的方法:
	  CREATE DEFINER = `root`@`%` FUNCTION 替换为 CREATE FUNCTION
	  END; 替换为 END$$
	  第一行前加：DELIMITER $$
[2016-9-27]
1.整理scm库截止2016年9月27日scm库最新的函数、视图、存储过程，并用于245客户第一版部署-建新账套。
[2016-9-28]
1.增加v_sc_ic_bal、v_sc_rp_contactbal、v_Sc_Rp_Expbal视图。
2.方平修改 v_sc_rp_rotherbill，v_sc_rp_expensesapply，v_sc_rp_potherbill视图
3.晓峰修改t_s_bill_info的记录
  update t_s_bill_info set isaudit=1 ， isprice = 1 where BILL_NAME='总部经销管理';
4.王杰配置价格
update  T_S_Bill_Info  set  price_Field = 'cgPrice1'  where  BILL_ID in  (1505,1506,1507) ;
5.王杰修改视图：v_sc_prcply，v_sc_drp_order，v_sc_drp_rstockbill，v_sc_drp_stockbill
6.晓峰修改f_sc_getDepartChildList函数
[2016-10-9]
1.方平：加了视图v_sc_organization
2.天航：加了3个视图v_sc_begdata，v_sc_begdataincomepay，v_sc_begdatapayable
3.王杰修改视图：v_sc_drp_stockbill
[2016-10-13]
1.高振铃：修改了P_SC_GET_PRICE存储过程，修正销售模块不启用智能取价报错问题

[未处理]部署到客户环境SQL：
-- ----------------------------
-- View structure for v_sc_organization
-- ----------------------------
-- -- DROP VIEW IF EXISTS `v_sc_organization`$$
CREATE or REPLACE VIEW `v_sc_organization` as
  SELECT
  	`o`.`CREATE_NAME` AS `createName`,
  	`o`.`CREATE_BY` AS `createBy`,
  	`o`.`CREATE_DATE` AS `createDate`,
  	`o`.`UPDATE_NAME` AS `updateName`,
  	`o`.`UPDATE_BY` AS `updateBy`,
  	`o`.`UPDATE_DATE` AS `updateDate`,
  	`o`.`ID` AS `id`,
  	`o`.`NUMBER` AS `number`,
  	`o`.`NAME` AS `name`,
  	`o`.`SHORTNAME` AS `shortName`,
  	`o`.`SHORTNUMBER` AS `shortNumber`,
  	`o`.`CORPERATE` AS `corperate`,
  	`o`.`CONTACT` AS `contact`,
  	`o`.`MOBILEPHONE` AS `mobilePhone`,
  	`o`.`PHONE` AS `phone`,
  	`o`.`FAX` AS `fax`,
  	`o`.`ISDEALER` AS `isDealer`,
  	`o`.`ADDRESS` AS `address`,
  	`o`.`REGIONID` AS `regIonid`,
  	`op`.`NAME` AS `parentDealerName`,
  	`o`.`TYPEID` AS `typeId`,
  	`e`.`NAME` AS `businessName`,
  	`o`.`LICENCE` AS `licence`,
  	`o`.`CITY` AS `city`,
  	`o`.`POSTALCODE` AS `postAlcode`,
  	`o`.`EMAIL` AS `email`,
  	`o`.`HOMEPAGE` AS `homePage`,
  	`jo`.`NAME` AS `settlementName`,
  	`o`.`BANK` AS `bank`,
  	`o`.`BANKNUMBER` AS `bankNumber`,
  	`o`.`CIQNUMBER` AS `ciqNumber`,
  	`o`.`TRADE` AS `trade`,
  	`o`.`DELIVERTYPE` AS `deliverType`,
  	`o`.`ISCREDITMGR` AS `isCreditmgr`,
  	`o`.`CREDITAMOUNT` AS `creditAmount`,
  	`o`.`PARENTID` AS `parentid`,
  	`o`.`DISABLE` AS `disable`,
  	`o`.`COUNT` AS `count`
  FROM
  	(
  		(
  			(
  				`t_sc_organization` `o`
  				LEFT JOIN `t_sc_organization` `op` ON (
  					(
  						`o`.`DEALER` = `op`.`ID`
  					)
  				)
  			)
  			LEFT JOIN `t_sc_emp` `e` ON (
  				(
  					`o`.`DEFAULTOPERATOR` = `e`.`ID`
  				)
  			)
  		)
  		LEFT JOIN `t_sc_organization` `jo` ON (
  			(
  				`jo`.`ID` = `o`.`SETTLECOMPANY`
  			)
  		)
  	)
   $$

-- ----------------------------
-- View structure for v_sc_begdata
-- ----------------------------
-- -- DROP VIEW IF EXISTS `v_sc_begdata`$$
CREATE or REPLACE VIEW `v_sc_begdata` as
  SELECT
`a`.`ID` AS `id`,
`a`.`CREATE_NAME` AS `create_name`,
`a`.`CREATE_BY` AS `create_by`,
`a`.`UPDATE_NAME` AS `update_name`,
`a`.`UPDATE_BY` AS `update_by`,
`a`.`CREATE_DATE` AS `create_date`,
`a`.`UPDATE_DATE` AS `update_date`,
`a`.`TRANTYPE` AS `trantype`,
`a`.`BILLNO` AS `billno`,
`a`.`DATE` AS `date`,
`c`.`NAME` AS `itemid`,
`a`.`RPDATE` AS `rpdate`,
`d`.`NAME` AS `empid`,
`e`.`NAME` AS `deptid`,
`a`.`ALLAMOUNT` AS `allamount`,
`a`.`CHECKAMOUNT` AS `checkamount`,
`a`.`UNCHECKAMOUNT` AS `uncheckamount`,
`a`.`EXPLANATION` AS `explanation`,
`b`.`REALNAME` AS `billerid`,
`b`.`REALNAME` AS `checkerid`,
`a`.`CHECKDATE` AS `checkdate`,
`a`.`CHECKSTATE` AS `checkstate`,
`f`.`departname` AS `sonid`,
`a`.`VERSION` AS `version`
FROM
	(
		`t_sc_begdata` `a`
		JOIN `t_s_base_user` `b`
		JOIN `t_SC_Organization` `c`
		JOIN `t_SC_EMP` `d`
		JOIN `t_sc_department` `e`
		JOIN `t_s_depart` `f`
	)
WHERE
	(`a`.`billerid` = `b`.`ID`)
AND
  (`a`.`checkerid` = `b`.`ID`)
AND
	(`a`.`itemid` = `c`.`ID`)
AND
	(`a`.`empid` = `d`.`ID`)
AND
	(`a`.`deptid` = `e`.`ID`)
AND
	(`a`.`sonid` = `f`.`ID`)
   $$

-- ----------------------------
-- View structure for v_sc_begdataincomepay
-- ----------------------------
-- -- DROP VIEW IF EXISTS `v_sc_begdataincomepay`$$
CREATE or REPLACE VIEW `v_sc_begdataincomepay` as
  SELECT
`a`.`ID` AS `id`,
`a`.`CREATE_NAME` AS `create_name`,
`a`.`CREATE_BY` AS `create_by`,
`a`.`UPDATE_NAME` AS `update_name`,
`a`.`UPDATE_BY` AS `update_by`,
`a`.`CREATE_DATE` AS `create_date`,
`a`.`UPDATE_DATE` AS `update_date`,
`a`.`TRANTYPE` AS `trantype`,
`a`.`BILLNO` AS `billno`,
`a`.`DATE` AS `date`,
`c`.`NAME` AS `itemid`,
`a`.`RPDATE` AS `rpdate`,
`d`.`NAME` AS `empid`,
`e`.`NAME` AS `deptid`,
`a`.`ALLAMOUNT` AS `allamount`,
`a`.`CHECKAMOUNT` AS `checkamount`,
`a`.`UNCHECKAMOUNT` AS `uncheckamount`,
`a`.`EXPLANATION` AS `explanation`,
`b`.`REALNAME` AS `billerid`,
`b`.`REALNAME` AS `checkerid`,
`a`.`CHECKDATE` AS `checkdate`,
`a`.`CHECKSTATE` AS `checkstate`,
`f`.`departname` AS `sonid`,
`a`.`VERSION` AS `version`
FROM
	(
		`t_sc_begdata` `a`
		JOIN `t_s_base_user` `b`
		JOIN `T_SC_SETTLEACCT` `c`
		JOIN `t_SC_EMP` `d`
		JOIN `t_sc_department` `e`
		JOIN `t_s_depart` `f`
	)
WHERE
	(`a`.`billerid` = `b`.`ID`)
AND
  (`a`.`checkerid` = `b`.`ID`)
AND
	(`a`.`itemid` = `c`.`ID`)
AND
	(`a`.`empid` = `d`.`ID`)
AND
	(`a`.`deptid` = `e`.`ID`)
AND
	(`a`.`sonid` = `f`.`ID`)
   $$

-- ----------------------------
-- View structure for v_sc_begdatapayable
-- ----------------------------
-- -- DROP VIEW IF EXISTS `v_sc_begdatapayable`$$
CREATE or REPLACE VIEW `v_sc_begdatapayable` as
  SELECT
`a`.`ID` AS `id`,
`a`.`CREATE_NAME` AS `create_name`,
`a`.`CREATE_BY` AS `create_by`,
`a`.`UPDATE_NAME` AS `update_name`,
`a`.`UPDATE_BY` AS `update_by`,
`a`.`CREATE_DATE` AS `create_date`,
`a`.`UPDATE_DATE` AS `update_date`,
`a`.`TRANTYPE` AS `trantype`,
`a`.`BILLNO` AS `billno`,
`a`.`DATE` AS `date`,
`c`.`NAME` AS `itemid`,
`a`.`RPDATE` AS `rpdate`,
`d`.`NAME` AS `empid`,
`e`.`NAME` AS `deptid`,
`a`.`ALLAMOUNT` AS `allamount`,
`a`.`CHECKAMOUNT` AS `checkamount`,
`a`.`UNCHECKAMOUNT` AS `uncheckamount`,
`a`.`EXPLANATION` AS `explanation`,
`b`.`REALNAME` AS `billerid`,
`b`.`REALNAME` AS `checkerid`,
`a`.`CHECKDATE` AS `checkdate`,
`a`.`CHECKSTATE` AS `checkstate`,
`f`.`departname` AS `sonid`,
`a`.`VERSION` AS `version`
FROM
	(
		`t_sc_begdata` `a`
		left JOIN `t_s_base_user` `b` on `a`.`checkerid` = `b`.`ID`
		left JOIN `t_sc_supplier` `c` on `a`.`itemid` = `c`.`ID`
		 left JOIN `t_SC_EMP` `d` on 	`a`.`empid` = `d`.`ID`
		left JOIN `t_sc_department` `e` on `a`.`deptid` = `e`.`ID`
		left JOIN `t_s_depart` `f` on `a`.`sonid` = `f`.`ID`
	)
WHERE `a`.`billerid` = `b`.`ID`
and `a`.`itemid` = `c`.`ID`
   $$

-- ----------------------------
    -- View structure for v_sc_drp_stockbill
    -- ----------------------------
    -- DROP VIEW IF EXISTS `v_sc_drp_stockbill`$$
    CREATE or REPLACE VIEW `v_sc_drp_stockbill` AS
      SELECT
    	`b`.`id` AS `id`,
       be.id as entryId,
    	`b`.`create_name` AS `create_name`,
    	`b`.`create_by` AS `create_by`,
    	`b`.`create_date` AS `create_date`,
    	`b`.`update_name` AS `update_name`,
    	`b`.`update_by` AS `update_by`,
    	`b`.`update_date` AS `update_date`,
    	`b`.`tranType` AS `tranType`,
    	`b`.`billNo` AS `billNo`,
    	`b`.`date` AS `date`,
    	`b`.`dealerID` AS `dealerID`,
    	`oz`.`name` AS `dealerName`,
    	`b`.`contact` AS `contact`,
    	`b`.`mobilePhone` AS `mobilePhone`,
    	`b`.`phone` AS `phone`,
    	`b`.`fax` AS `fax`,
    	`b`.`address` AS `address`,
    	`b`.`empID` AS `empID`,
    	`e`.`Name` AS `empName`,
    	`b`.`deptID` AS `deptID`,
    	`d`.`name` AS `deptName`,
    	`b`.`stockID` AS `bStockId`,
    	`st`.`name` AS `bStockName`,
    	`b`.`rebateamount` AS `rebateamount`,
    	`b`.`freight` AS `freight`,
    	`b`.`amount` AS `amount`,
    	`b`.`allamount` AS `allamount`,
    	`b`.`accountID` AS `accountID`,
    	`sa`.`name` AS `accountName`,
    	`b`.`curpayamount` AS `curpayamount`,
    	`b`.`checkamount` AS `checkamount`,
    	`b`.`weight` AS `weight`,
    	`b`.`affirmstatus` AS `affirmstatus`,
    	`b`.`affirmID` AS `affirmID`,
    	`u2`.`REALNAME` AS `affirmName`,
    	`b`.`affirmDate` AS `affirmDate`,
    	`b`.`amountloss` AS `amountloss`,
    	`b`.`classid_src` AS `classid_src`,
    	`b`.`interID_SRC` AS `interID_SRC`,
    	`b`.`billNo_SRC` AS `billNo_SRC`,
    	`b`.`checkerID` AS `checkerID`,
    	`b`.`billerID` AS `billerID`,
    	`u`.`REALNAME` AS `billerName`,
    	`b`.`checkDate` AS `checkDate`,
    	`b`.`checkState` AS `checkState`,
    	`b`.`cancellation` AS `cancellation`,
    	`b`.`explanation` AS `explanation`,
    	`b`.`sonID` AS `sonID`,
    	`de`.`DEPARTNAME` AS `sonName`,
    	`be`.`indexNumber` AS `indexNumber`,
    	`be`.`stockID` AS `stockId`,
    	`st`.`name` AS `stockName`,
    	`be`.`itemID` AS `itemID`,
    	`be`.`id` AS `stockBillId`,
    	`i`.`Number` AS `number`,
    	`i`.`Name` AS `name`,
    	`i`.`Model` AS `model`,
    	`i`.`ISAssembly` AS `ISAssembly`,
    	`i`.`ISKFPeriod` AS `ISKFPeriod`,
    	`i`.`BatchManager` AS `BatchManager`,
    	`i`.`KFPeriod` AS `itemKfperiod`,
    	`i`.`KFDateType` AS `itemKfDateType`,
    	`p`.`BarCode` AS `barCode`,
    	`be`.`unitID` AS `unitID`,
    	`m`.`name` AS `unitName`,
    	`be`.`secUnitID` AS `secUnitID`,
    	`m2`.`name` AS `secUnitName`,
    	`be`.`basicUnitID` AS `basicUnitID`,
    	`m3`.`name` AS `basicUnitName`,
    	`be`.`batchNo` AS `batchNo`,
    	`be`.`date` AS `kfDate`,
    	`be`.`kfperiod` AS `kfperiod`,
    	`be`.`periodDate` AS `periodDate`,
    	CONCAT(`i`.`KFPeriod`,
    				(
    					CASE i.KFDATETYPE
    					WHEN '0001' THEN '年'
    					WHEN '0002' THEN '月'
    					WHEN '0003' THEN '日'
    					END
    				)
    ) as kfdateType,
    	`be`.`qty` AS `qty`,
    	`be`.`seccoefficient` AS `seccoefficient`,
    	`be`.`secqty` AS `secqty`,
    	`be`.`coefficient` AS `coefficient`,
    	`be`.`basicqty` AS `basicqty`,
    	`be`.`price` AS `price`,
    	`be`.`amount` AS `itemAmount`,
    	`be`.`discountrate` AS `discountrate`,
    	`be`.`discountprice` AS `discountPrice`,
    	`be`.`discountamount` AS `discountamount`,
    	`be`.`taxrate` AS `taxrate`,
    	`be`.`taxpriceex` AS `taxpriceex`,
    	`be`.`taxamountex` AS `taxamountex`,
    	`be`.`taxprice` AS `taxprice`,
    	`be`.`intaxamount` AS `intaxamount`,
    	`be`.`taxamount` AS `taxamount`,
    	`be`.`costprice` AS `costprice`,
    	`be`.`costamount` AS `costamount`,
    	`be`.`weight` AS `itemWeight`,
    	`be`.`salesid` AS `salesid`,
    	(
    		CASE `be`.`freegifts`
    		WHEN 0 THEN
    			'调价政策'
    		WHEN 1 THEN
    			'买一赠一'
    		WHEN 2 THEN
    			''
    		END
    	) AS `salesName`,
    	`be`.`freegifts` AS `freegifts`,
    	`be`.`commitQty` AS `commitQty`,
    	`be`.`stockQty` AS `stockQty`,
    	`be`.`classid_src` AS `itemClassid_src`,
    	`be`.`interID_SRC` AS `itemInterID_SRC`,
    	`be`.`billNo_SRC` AS `itemBillNo_SRC`,
    	`be`.`fid_SRC` AS `fid_SRC`,
    	`be`.`interID_Order` AS `interID_Order`,
    	`be`.`billNo_Order` AS `billNo_Order`,
    	`be`.`fid_Order` AS `fid_Order`,
    	`be`.`note` AS `note`,
    	`bill`.`bill_name` AS `entryClassIdSrc`
    FROM
    	(
    		(
    			(
    				(
    					(
    						(
    							(
    								(
    									(
    										(
    											(
    												(
    													(
    														(
    															(
    																(
    																	(
    																		(
    																			(
    																				(
    																					(
    																						`t_sc_drp_stockbill` `b`
    																						LEFT JOIN `t_sc_drp_stockbillentry` `be` ON ((`b`.`id` = `be`.`fid`))
    																					)
    																					LEFT JOIN `t_sc_icitem` `i` ON ((`be`.`itemID` = `i`.`id`))
    																				)
    																				LEFT JOIN `t_sc_item_price` `p` ON ((`be`.`unitID` = `p`.`id`))
    																			)
    																			LEFT JOIN `t_sc_stock` `s` ON ((`s`.`id` = `b`.`stockID`))
    																		)
    																		LEFT JOIN `t_sc_organization` `oz` ON ((`oz`.`id` = `b`.`dealerID`))
    																	)
    																	LEFT JOIN `t_sc_emp` `e` ON ((`e`.`id` = `b`.`empID`))
    																)
    																LEFT JOIN `t_sc_department` `d` ON ((`d`.`id` = `b`.`deptID`))
    															)
    															LEFT JOIN `t_sc_stock` `st` ON ((`st`.`id` = `be`.`stockID`))
    														)
    														LEFT JOIN `t_s_depart` `de` ON ((`de`.`ID` = `b`.`sonID`))
    													)
    													LEFT JOIN `t_s_base_user` `u` ON ((`u`.`ID` = `b`.`billerID`))
    												)
    												LEFT JOIN `t_sc_measureunit` `m` ON ((`m`.`id` = `p`.`UnitID`))
    											)
    											LEFT JOIN `t_sc_drp_logistical` `ls` ON ((`ls`.`fid` = `b`.`id`))
    										)
    										LEFT JOIN `t_sc_logistical` `l` ON (
    											(
    												`l`.`id` = `ls`.`logisticalID`
    											)
    										)
    									)
    									LEFT JOIN `t_sc_settleacct` `sa` ON (
    										(`sa`.`id` = `b`.`accountID`)
    									)
    								)
    								LEFT JOIN `t_sc_settleacct` `ac` ON (
    									(`ac`.`id` = `ls`.`accountID`)
    								)
    							)
    							LEFT JOIN `t_s_base_user` `u2` ON ((`u2`.`ID` = `b`.`affirmID`))
    						)
    						LEFT JOIN `t_sc_item_price` `p2` ON (
    							(`be`.`secUnitID` = `p2`.`id`)
    						)
    					)
    					LEFT JOIN `t_sc_measureunit` `m2` ON ((`m2`.`id` = `p2`.`UnitID`))
    				)
    				LEFT JOIN `t_sc_item_price` `p3` ON (
    					(
    						`be`.`basicUnitID` = `p3`.`id`
    					)
    				)
    			)
    			LEFT JOIN `t_sc_measureunit` `m3` ON ((`m3`.`id` = `p3`.`UnitID`))
    		)
    		LEFT JOIN `t_s_bill_info` `bill` ON (
    			(
    				`be`.`classid_src` = `bill`.`bill_id`
    			)
    		)
    	)
    ORDER BY
    	`b`.`date` DESC,
    	`b`.`billNo` DESC,
    	`be`.`indexNumber`$$
----------------------------
    -- procedure structure for P_SC_GET_PRICE
    -- ----------------------------
    	DROP PROCEDURE IF EXISTS `P_SC_GET_PRICE`;

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


        END $$
--  职员表“T_SC_EMP” 修改启用金额 数据类型长度 sql 代码
alter table t_sc_emp modify column CREDITAMOUNT decimal(20,10) comment '信用额度';
-- 销售订单"t_sc_order" 修改优惠金额和预收金额类型小数位数
alter table t_sc_order modify column REBATEAMOUNT decimal(20,10) comment '优惠金额';
alter table t_sc_order modify column PREAMOUNT decimal(20,10) comment '预收金额';
-- 客户信息视图 v_sc_organization 修改了字段
-- 商品视图
-- ----------------------------
-- View structure for v_sc_icitem
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_icitem`$$
-- DROP VIEW IF EXISTS `v_sc_icitem`$$
CREATE or REPLACE VIEW `v_sc_icitem` AS
  SELECT
    `a`.`ID`             AS `id`,
    `a`.`NUMBER`         AS `number`,
    `a`.`NAME`           AS `Name`,
    `a`.`SHORTNAME`      AS `ShortName`,
    `a`.`MODEL`          AS `Model`,
    `a`.`WEIGHT`         AS `Weight`,
    `a`.`KFPERIOD`       AS `KFPeriod`,
    `a`.`KFDATETYPE`     AS `KFDateType`,
    `a`.`STOCKID`        AS `StockID`,
    `a`.`SUPPLIERID`     AS `SupplierID`,
    `a`.`FACTORY`        AS `Factory`,
    `a`.`PRODUCINGPLACE` AS `ProducingPlace`,
    `a`.`COSTPRICE`      AS `CostPrice`,
    `a`.`TAXRATEIN`      AS `TaxRateIn`,
    `a`.`TAXRATEOUT`     AS `TaxRateOut`,
    `a`.`ITEMTYPE`       AS `ItemType`,
    `a`.`TRACK`          AS `Track`,
    `a`.`ISKFPERIOD`     AS `ISKFPeriod`,
    `a`.`BATCHMANAGER`   AS `BatchManager`,
    `a`.`ISASSEMBLY`     AS `ISAssembly`,
    `a`.`SHORTNUMBER`    AS `ShortNumber`,
    `a`.`CULTURENO`      AS `CultureNo`,
    `a`.`BRANDID`        AS `BrandID`,
    `a`.`NOTE`           AS `Note`,
    `a`.`CREATE_DATE`    AS `create_date`,
    `a`.`COUNT`          AS `count`,
    `a`.`PARENTID`       AS `ParentID`,
    `a`.`DISABLED`       AS `DISABLED`,
    `b`.`ID`             AS `entryId`,
    `b`.`BARCODE`        AS `BarCode`,
    `b`.`UNITID`         AS `UnitID`,
    `b`.`LSRETAILPRICE`  AS `LSRetailPrice`,
    `b`.`LSMEMBERPRICE`  AS `LSMemberPrice`,
    `b`.`DEFAULTCG`      AS `DefaultCG`,
    `b`.`DEFAULTXS`      AS `DefaultXS`,
    `b`.`DEFAULTCK`      AS `DefaultCK`,
    `b`.`DEFAULTSC`      AS `DefaultSC`,
    `b`.`CGPRICE`        AS `CGPrice`,
    `b`.`CGPRICE1`       AS `CGPrice1`,
    `b`.`CGPRICE2`       AS `CGPrice2`,
    `b`.`CGPRICE3`       AS `CGPrice3`,
    `b`.`CGLIMITPRICE`   AS `CGLimitPrice`,
    `b`.`CGLATESTPRICE`  AS `CGLatestPrice`,
    `b`.`XSPRICE`        AS `XSPrice`,
    `b`.`XSPRICE1`       AS `XSPrice1`,
    `b`.`XSPRICE2`       AS `XSPrice2`,
    `b`.`XSPRICE3`       AS `XSPrice3`,
    `b`.`XSLIMITPRICE`   AS `XSLimitPrice`,
    `b`.`XSLATESTPRICE`  AS `XSLatestPrice`
  FROM
    (
        `t_sc_icitem` `a`
        JOIN `t_sc_item_price` `b`
    )
  WHERE
    (`a`.`ID` = `b`.`ITEMID`) AND (`b`.`UNITTYPE` = '0001')
  ORDER BY
    `a`.`number` $$
[未处理]部署到客户环境表单配置：
T_S_Bill_Info js自定义(增强类型为form)
    $('#bill_id').attr('validType','T_S_Bill_Info,BILL_ID,id');
    $(function(){
    	var id = $("input[name='id']").val();

    	if(id!=""){
    	    var selects = document.getElementById("pid").options;
    	    for(var i=0 ; i < selects.length ; i++){
				var option = selects[i];
				if(id == option.value){
					selects.remove(i);
				}
    	    }
			$("#bill_id").attr("readonly","readonly");
			$("#bill_id").css("background-color","rgb(226,226,226)");
			$.ajax({
				url:"tSBillInfoController.do?goUpdateForJson",
				data: "id=" + id,
				success: function(data){
					//debugger;
					var pid = data.obj.pid;
					var userName= data.obj.userName;
					if (pid==""){
						$("#pid").css("display","none");
					}
					if ('programmer' != userName){
						  var form= document.getElementById("formobj");
						  //创建一个隐藏域，存放父类id
						  var newInput = document.createElement("input");
						  newInput.name="pid";
						  newInput.value=pid;
						  newInput.setAttribute('type','hidden');
						  form.appendChild(newInput);
						  //创建一个隐藏域，存放是否审核值
						  var newInput2 = document.createElement("input");
						  newInput2.name="isAudit";
						  newInput2.value=$("#isAudit").val() == "" ? 0 : $("#isAudit").val();
						  newInput2.setAttribute('type','hidden');
						  form.appendChild(newInput2);
						  //创建一个隐藏域，存放是否单价设置值
						  var newInput3 = document.createElement("input");
						  newInput3.name="isPrice";
						  newInput3.value=$("#isPrice").val() == "" ? 0 : $("#isPrice").val();
						  newInput3.setAttribute('type','hidden');
						  form.appendChild(newInput3);
						  $("#pid").attr("disabled","disabled");
						  $("#isAudit").attr("disabled","disabled");
						  $("#isPrice").attr("disabled","disabled");
						  $("#bill_name").attr("readonly","readonly");
						  $("#bill_name").css("background-color","rgb(226,226,226)");
					 }
				}
			});
    	}else{
               var pid = document.getElementById("pid").options;
               var isEdit = document.getElementById("is_edit").options;
               var isOffOn = document.getElementById("is_off_on").options;
               var backzero = document.getElementById("back_zero").options;
               var dateformatter = document.getElementById("date_formatter").options;
               pid.add(new Option("----请选择----",""));
               pid[pid.length-1].selected="selected";
               isEdit.add(new Option("----请选择----",""));
               isEdit[isEdit .length-1].selected="selected";
               isOffOn.add(new Option("----请选择----",""));
               isOffOn[isOffOn.length-1].selected="selected";
               backzero.add(new Option("----请选择----",""));
               backzero[backzero.length-1].selected="selected";
               dateformatter.add(new Option("----请选择----",""));
               dateformatter[dateformatter.length-1].selected="selected";
            }
    });

T_S_Bill_Info js自定义(增强类型为list)
$(function(){
    $("#batchDelete").css("display","none");
    $("#import").css("display","none");
    $("#excel").css("display","none");
    $.ajax({
		url:"tSBillInfoController.do?goUpdateForJson",
		success: function(data){
			var userName = data.obj.userName;
			if("programmer" != userName){
				 $("#add").css("display","none");
				 $("#T_S_Bill_InfoList").datagrid("hideColumn","opt");
				 $("[onclick^='delObj(']").css("display","none");
			 }
		}
    });
});


T_S_Audit表增加sonId字段
alter table t_s_audit add sonId varchar(32) null;

T_S_Audit表sonId数据初始化
update t_s_audit set sonId = (
select org_id from t_s_user_org where user_id = (select id from t_s_base_user where username='scadmin')
);

t_s_audit_relation表增加sonId字段
ALTER TABLE t_s_audit_relation ADD sonId VARCHAR (32) NULL;

t_s_audit_relation表sonId数据初始化
update t_s_audit_relation set sonid = (select org_id from t_s_user_org where user_id=(select id from t_s_base_user where
username='scadmin'));

新建t_sc_bill_audit_status表
create table t_sc_bill_audit_status (
  id varchar(32) not null ,
  create_name varchar(100) null,
  create_by varchar(100) null,
  create_date datetime null,
  update_name varchar(100) null,
  update_by varchar(100) null,
  update_date datetime null,
  billId varchar(32) null,
  status int null,
  sonId varchar(32) null,
  tranType VARCHAR(10) null
);

视图 v_sc_po_order 修改
-- ----------------------------
-- View structure for v_sc_po_order
-- ----------------------------
-- -- DROP VIEW IF EXISTS `v_sc_po_order`$$
CREATE or REPLACE VIEW `v_sc_po_order` as
SELECT
    `a`.`id`             AS `id`,
    `a`.`create_name`    AS `create_name`,
    `a`.`create_by`      AS `create_by`,
    `a`.`create_date`    AS `create_date`,
    `a`.`update_name`    AS `update_name`,
    `a`.`update_by`      AS `update_by`,
    `a`.`update_date`    AS `update_date`,
    `a`.`tranType`       AS `tranType`,
    `a`.`date`           AS `date`,
    `a`.`billNo`         AS `billNo`,
    `s`.`Name`           AS `itemName`,
    `a`.`itemId`         AS `itemId`,
    `a`.`contact`        AS `contact`,
    `a`.`mobilePhone`    AS `mobilePhone`,
    `a`.`phone`          AS `phone`,
    `a`.`fax`            AS `fax`,
    `a`.`address`        AS `address`,
    `a`.`empId`          AS `empId`,
    `a`.`deptId`         AS `deptId`,
    `a`.`stockId`        AS `stockId`,
    `emp`.`Name`         AS `empName`,
    `dept`.`name`        AS `deptName`,
    `stock`.`name`       AS `stockName`,
    `a`.`rebateAmount`   AS `rebateAmount`,
    `a`.`amount`         AS `amount`,
    `a`.`allAmount`      AS `allAmount`,
    `a`.`checkAmount`    AS `checkAmount`,
    `a`.`classId_SRC`    AS `classId_SRC`,
    `a`.`id_SRC`         AS `id_SRC`,
    `a`.`billNo_SRC`     AS `billNo_SRC`,
    `a`.`checkerId`      AS `checkerId`,
    `u`.`REALNAME`       AS `billerId`,
    `a`.`checkDate`      AS `checkDate`,
    `a`.`checkState`     AS `checkState`,
    `a`.`closed`         AS `closed`,
    `a`.`autoFlag`       AS `autoFlag`,
    `a`.`cancellation`   AS `cancellation`,
    `a`.`explanation`    AS `explanation`,
    `p`.`DEPARTNAME`     AS `sonName`,
    `a`.`sonId`          AS `sonId`,
    `b`.`id`             AS `entryId`,
    `b`.`findex`         AS `findex`,
    `b`.`itemId`         AS `entryItemId`,
    `b`.`stockId`        AS `entryStockId`,
    `c`.`name`           AS `entryStockName`,
    `d`.`Name`           AS `entryItemName`,
    `d`.`Number`         AS `entryItemNo`,
    `d`.`Model`          AS `Model`,
    `d`.`ISKFPeriod`     AS `ISKFPeriod`,
    `d`.`BatchManager`   AS `BatchManager`,
    `d`.`KFDateType`     AS `KFDateType`,
    `d`.`KFPeriod`       AS `KFPeriod`,
    `e`.`BarCode`        AS `barCode`,
    `e`.`CGLimitPrice`   AS `CGLimitPrice`,
    `b`.`unitId`         AS `unitId`,
    `f`.`name`           AS `unitName`,
    `h`.`name`           AS `basicUnitName`,
    `g`.`Coefficient`    AS `basicCoefficient`,
    `b`.`basicUnitId`    AS `basicUnitId`,
    `j`.`name`           AS `secUnitName`,
    `b`.`qty`            AS `qty`,
    `b`.`coefficient`    AS `coefficient`,
    `b`.`basicQty`       AS `basicQty`,
    `b`.`discountRate`   AS `discountRate`,
    `b`.`taxRate`        AS `taxRate`,
    `b`.`taxPriceEx`     AS `taxPriceEx`,
    `b`.`taxAmountEx`    AS `taxAmountEx`,
    `b`.`taxPrice`       AS `taxPrice`,
    `b`.`inTaxAmount`    AS `inTaxAmount`,
    `b`.`taxAmount`      AS `taxAmount`,
    `b`.`jhDate`         AS `jhDate`,
    `b`.`freeGifts`      AS `freeGifts`,
    `b`.`stockQty`       AS `stockQty`,
     b.SECUNITID         AS  secUnitId,
    (
      CASE
      WHEN (
        `b`.`stockQty` >= `b`.`QTY`
      )
        THEN
          1
      ELSE
        0
      END
    )                    AS `isStock`,
    `b`.`price`          AS `entryPrice`,
    `b`.`amount`         AS `entryAmount`,
    `b`.`secQty`         AS `secQty`,
    `b`.`secCoefficient` AS `secCoefficient`,
    `b`.`discountPrice`  AS `discountPrice`,
    `b`.`discountAmount` AS `discountAmount`,
    `b`.`note`           AS `note`,
    `bill`.`bill_name`   AS `tranTypeName`,
    che.REALNAME         AS checkerName
  FROM
    (
        (
            (
                (
                    (
                        (
                            (
                                (
                                    (
                                        (
                                            (
                                                (
                                                    (
                                                        (
                                                            (
                                                                (
                                                                    `t_sc_po_order` `a`
                                                                    JOIN `t_sc_po_orderentry` `b`
                                                                      ON ((`a`.`id` = `b`.`fid`))
                                                                  )
                                                                LEFT JOIN `t_sc_stock` `c`
                                                                  ON ((`b`.`stockId` = `c`.`id`))
                                                              )
                                                            LEFT JOIN `t_sc_icitem` `d` ON ((`b`.`itemId` = `d`.`id`))
                                                          )
                                                        LEFT JOIN `t_sc_item_price` `e` ON (
                                                        (
                                                          (`e`.`ItemID` = `d`.`id`)
                                                          AND (`e`.`id` = `b`.`unitId`)
                                                        )
                                                        )
                                                      )
                                                    LEFT JOIN `t_sc_measureunit` `f` ON ((`e`.`UnitID` = `f`.`id`))
                                                  )
                                                LEFT JOIN `t_sc_item_price` `g` ON (
                                                (`b`.`basicUnitId` = `g`.`id`)
                                                )
                                              )
                                            LEFT JOIN `t_sc_measureunit` `h` ON ((`g`.`UnitID` = `h`.`id`))
                                          )
                                        LEFT JOIN `t_sc_item_price` `i` ON ((`b`.`secUnitId` = `i`.`id`))
                                      )
                                    LEFT JOIN `t_sc_measureunit` `j` ON ((`i`.`UnitID` = `j`.`id`))
                                  )
                                LEFT JOIN `t_sc_supplier` `s` ON ((`a`.`itemId` = `s`.`id`))
                              )
                            LEFT JOIN `t_sc_emp` `emp` ON ((`a`.`empId` = `emp`.`id`))
                          )
                        LEFT JOIN `t_sc_department` `dept` ON ((`a`.`deptId` = `dept`.`id`))
                      )
                    LEFT JOIN `t_sc_stock` `stock` ON (
                    (`a`.`stockId` = `stock`.`id`)
                    )
                  )
                LEFT JOIN `t_s_base_user` `u` ON ((`a`.`billerId` = `u`.`ID`))
              )
            LEFT JOIN `t_s_depart` `p` ON ((`a`.`sonId` = `p`.`ID`))
          )
        LEFT JOIN `t_s_bill_info` `bill` ON (
        (
          `a`.`tranType` = `bill`.`bill_id`
        )
        )
      )
    LEFT JOIN t_s_base_user che ON che.ID = a.CHECKERID
  ORDER BY
    `a`.`date` DESC,
    `a`.`billNo` DESC,
    `b`.`findex`;

视图 v_sc_po_stockbill 修改
-- ----------------------------
                       -- View structure for v_sc_po_stockbill
                       -- ----------------------------
                       DROP TABLE IF EXISTS `v_sc_po_stockbill`$$
                       -- DROP VIEW IF EXISTS `v_sc_po_stockbill`$$
                       CREATE or REPLACE VIEW `v_sc_po_stockbill` AS
	SELECT
    `a`.`id`             AS `id`,
    `a`.`tranType`       AS `tranType`,
    `a`.`billNo`         AS `billNo`,
    `a`.`date`           AS `date`,
    `a`.`itemId`         AS `itemId`,
    `supplier`.`Name`    AS `itemName`,
    `a`.`empId`          AS `empId`,
    `emp`.`Name`         AS `empName`,
    `a`.`deptId`         AS `deptId`,
    `dept`.`name`        AS `deptName`,
    `a`.`stockId`        AS `stockId`,
    `stock`.`name`       AS `stockName`,
    `a`.`allAmount`      AS `allAmount`,
    `a`.`rebateAmount`   AS `rebateAmount`,
    `a`.`curPayAmount`   AS `curPayAmount`,
    `a`.`accountId`      AS `accountId`,
    `l`.`name`           AS `accountName`,
    `a`.`contact`        AS `contact`,
    `a`.`mobilePhone`    AS `mobilePhone`,
    `a`.`phone`          AS `phone`,
    `a`.`fax`            AS `fax`,
    `a`.`address`        AS `address`,
    `a`.`amount`         AS `amount`,
    `a`.`checkAmount`    AS `checkAmount`,
    `a`.`classID_SRC`    AS `classID_SRC`,
    `a`.`id_SRC`         AS `idSRC`,
    `a`.`billNo_SRC`     AS `billNo_SRC`,
    `u`.`REALNAME`       AS `billerID`,
    `a`.`checkerId`      AS `checkerId`,
    `a`.`checkDate`      AS `checkDate`,
    `a`.`checkState`     AS `checkState`,
    `a`.`cancellation`   AS `cancellation`,
    `a`.`explanation`    AS `explanation`,
    `t`.`DEPARTNAME`     AS `sonName`,
    `a`.`sonId`          AS `sonId`,
    `a`.`version`        AS `version`,
    `b`.`id`             AS `entryId`,
    `a`.`create_name`    AS `create_name`,
    `a`.`create_by`      AS `create_by`,
    `a`.`update_name`    AS `update_name`,
    `a`.`update_by`      AS `update_by`,
    `a`.`create_date`    AS `create_date`,
    `a`.`update_date`    AS `update_date`,
    `b`.`fid`            AS `fid`,
    `b`.`itemId`         AS `entryItemId`,
    `b`.`stockId`        AS `entryStockId`,
    `b`.`batchNo`        AS `batchNo`,
    `b`.`unitId`         AS `unitId`,
    `b`.`qty`            AS `qty`,
    `b`.`basicUnitId`    AS `basicUnitId`,
    `b`.`coefficient`    AS `coefficient`,
    `b`.`basicQty`       AS `basicQty`,
    `b`.`secUnitId`      AS `secUnitId`,
    `b`.`secCoefficient` AS `secCoefficient`,
    `b`.`secQty`         AS `secQty`,
    `b`.`taxPriceEx`     AS `taxPriceEx`,
    `b`.`taxAmountEx`    AS `taxAmountEx`,
    `b`.`discountRate`   AS `discountRate`,
    `b`.`taxPrice`       AS `taxPrice`,
    `b`.`inTaxAmount`    AS `inTaxAmount`,
    `b`.`taxRate`        AS `taxRate`,
    `b`.`price`          AS `entryPrice`,
    `b`.`amount`         AS `entryAmount`,
    `b`.`discountPrice`  AS `discountPrice`,
    `b`.`discountAmount` AS `discountAmount`,
    `b`.`taxAmount`      AS `taxAmount`,
    `b`.`kfDate`         AS `kfDate`,
    `b`.`kfPeriod`       AS `kfPeriod`,
    concat(
        `b`.`kfPeriod`,
        `type`.`TYPENAME`
    )                    AS `kfDateType`,
    `b`.`kfDateType`     AS `KFDateTypeInfo`,
    `b`.`periodDate`     AS `periodDate`,
    `b`.`costPrice`      AS `costPrice`,
    `b`.`costAmount`     AS `costAmount`,
    `b`.`freeGifts`      AS `freeGifts`,
    `b`.`commitQty`      AS `commitQty`,
    `b`.`classID_SRC`    AS `entryClassIDSrc`,
    `b`.`billNo_SRC`     AS `entryBillNoSrc`,
    `b`.`entryId_SRC`    AS `entryIdSRC`,
    `b`.`id_Order`       AS `idOrder`,
    `b`.`billNo_Order`   AS `billNoOrder`,
    `b`.`entryId_Order`  AS `entryIdOrder`,
    `b`.`note`           AS `note`,
    `b`.`findex`         AS `findex`,
    `c`.`Name`           AS `entryItemName`,
    `c`.`ISKFPeriod`     AS `ISKFPeriod`,
    `c`.`BatchManager`   AS `BatchManager`,
    `c`.`Number`         AS `entryItemNo`,
    `c`.`Model`          AS `model`,
    `d`.`BarCode`        AS `BarCode`,
    `d`.`CGLimitPrice`   AS `CGLimitPrice`,
    `d`.`XSLimitPrice`   AS `XSLimitPrice`,
    `f`.`name`           AS `unitName`,
    `h`.`name`           AS `basicUnitName`,
    `k`.`name`           AS `secUnitName`,
    `g`.`Coefficient`    AS `basicCoefficient`,
    `i`.`name`           AS `entryStockName`,
    `bill`.`bill_name`   AS `tranTypeName`,
    che.REALNAME         AS checkerName,
    (
      CASE
      WHEN (
        `b`.`QTY` <= ifnull(`b`.`commitQty`, 0)
      )
        THEN
          1
      ELSE
        0
      END
    )                    AS `isStock`,
    (
      CASE
      WHEN (
        (
          `inv`.`basicQty` - `b`.`basicQty`
        ) >= 0
      )
        THEN
          1
      ELSE
        0
      END
    )                    AS `isAudit`
  FROM
    (
        (
            (
                (
                    (
                        (
                            (
                                (
                                    (
                                        (
                                            (
                                                (
                                                    (
                                                        (
                                                            (
                                                                (
                                                                    (
                                                                        (
                                                                            (
                                                                                (
                                                                                    `t_sc_po_stockbill` `a`
                                                                                    JOIN `t_sc_po_stockbillentry` `b`
                                                                                      ON ((`a`.`id` = `b`.`fid`))
                                                                                  )
                                                                                LEFT JOIN `t_sc_icitem` `c`
                                                                                  ON ((`b`.`itemId` = `c`.`id`))
                                                                              )
                                                                            LEFT JOIN `t_sc_item_price` `d`
                                                                              ON ((`b`.`unitId` = `d`.`id`))
                                                                          )
                                                                        LEFT JOIN `t_sc_measureunit` `f`
                                                                          ON ((`d`.`UnitID` = `f`.`id`))
                                                                      )
                                                                    LEFT JOIN `t_sc_item_price` `g` ON (
                                                                    (`b`.`basicUnitId` = `g`.`id`)
                                                                    )
                                                                  )
                                                                LEFT JOIN `t_sc_measureunit` `h`
                                                                  ON ((`g`.`UnitID` = `h`.`id`))
                                                              )
                                                            LEFT JOIN `t_sc_item_price` `j`
                                                              ON ((`b`.`secUnitId` = `j`.`id`))
                                                          )
                                                        LEFT JOIN `t_sc_measureunit` `k` ON ((`j`.`UnitID` = `k`.`id`))
                                                      )
                                                    LEFT JOIN `t_sc_stock` `i` ON ((`b`.`stockId` = `i`.`id`))
                                                  )
                                                LEFT JOIN `t_s_base_user` `u` ON ((`a`.`billerID` = `u`.`ID`))
                                              )
                                            LEFT JOIN `t_sc_stock` `stock` ON (
                                            (`a`.`stockId` = `stock`.`id`)
                                            )
                                          )
                                        LEFT JOIN `t_s_depart` `t` ON ((`a`.`sonId` = `t`.`ID`))
                                      )
                                    LEFT JOIN `t_sc_item_price` `q` ON (
                                    (
                                      (`c`.`id` = `q`.`ItemID`)
                                      AND (`q`.`DefaultCK` = 1)
                                    )
                                    )
                                  )
                                LEFT JOIN `t_sc_ic_inventory` `inv` ON (
                                (
                                  (
                                    `b`.`itemId` = `inv`.`itemId`
                                  )
                                  AND (
                                    `b`.`stockId` = `inv`.`stockId`
                                  )
                                )
                                )
                              )
                            LEFT JOIN `t_s_type` `type` ON (
                            (
                              (
                                `type`.`TYPECODE` = `b`.`kfDateType`
                              )
                              AND (
                                `type`.`TYPEGROUPID` = (
                                  SELECT `t_s_typegroup`.`ID`
                                  FROM
                                    `t_s_typegroup`
                                  WHERE
                                    (
                                      `t_s_typegroup`.`TYPEGROUPCODE` = 'SC_DURA_DATE_TYPE'
                                    )
                                )
                              )
                            )
                            )
                          )
                        LEFT JOIN `t_sc_settleacct` `l` ON ((`a`.`accountId` = `l`.`id`))
                      )
                    LEFT JOIN `t_sc_supplier` `supplier` ON (
                    (
                      `a`.`itemId` = `supplier`.`id`
                    )
                    )
                  )
                LEFT JOIN `t_sc_emp` `emp` ON ((`a`.`empId` = `emp`.`id`))
              )
            LEFT JOIN `t_sc_department` `dept` ON ((`a`.`deptId` = `dept`.`id`))
          )
        LEFT JOIN `t_s_bill_info` `bill` ON (
        (
          `a`.`tranType` = `bill`.`bill_id`
        )
        )
      )
    LEFT JOIN t_s_base_user che ON che.id = a.CHECKERID
  ORDER BY
    `a`.`date` DESC,
    `a`.`billNo` DESC,
    `b`.`findex`;

--工作计划表 t_sc_plan 修改负责人 参与人 领导批注 数据类型长度
ALTER TABLE `t_sc_plan`
MODIFY COLUMN `PLAN_MASTER`  varchar(7100) COMMENT '负责人',
MODIFY COLUMN `PLAN_GROUP`  varchar(7100) COMMENT '参与人',
MODIFY COLUMN `PLAN_LEADDER`  varchar(7100) COMMENT '领导批注';

-- v_sc_ic_inventory 视图修改
-- ----------------------------
-- View structure for v_sc_ic_inventory
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_ic_inventory`$$
-- DROP VIEW IF EXISTS `v_sc_ic_inventory`$$
CREATE or REPLACE VIEW `v_sc_ic_inventory` AS
SELECT
	`a`.`id` AS `id`,
	`a`.`create_name` AS `create_name`,
	`a`.`create_by` AS `create_by`,
	`a`.`create_date` AS `create_date`,
	`a`.`update_name` AS `update_name`,
	`a`.`update_by` AS `update_by`,
	`a`.`update_date` AS `update_date`,
	`a`.`itemId` AS `itemId`,
	`a`.`stockId` AS `stockId`,

IF (
	a.BASICQTY > 0,
	floor(
		`a`.`basicQty` / f.COEFFICIENT
	),
	CEILING(
		`a`.`basicQty` / f.COEFFICIENT
	)
) AS `qty`,
 (
	a.BASICQTY - (
		(

			IF (
				a.BASICQTY > 0,
				floor(
					`a`.`basicQty` / f.COEFFICIENT
				),
				CEILING(
					`a`.`basicQty` / f.COEFFICIENT
				)
			)
		) * f.COEFFICIENT
	)
) AS `smallQty`,
 `a`.`basicQty` AS `basicQty`,
 `a`.`secQty` AS `secQty`,
 `a`.`costPrice` AS `costPrice`,
 `a`.`costAmount` AS `costAmount`,
 `a`.`count` AS `count`,
 `b`.`Number` AS `itemNo`,
 `b`.`Name` AS `itemName`,
 `c`.`name` AS `stockName`,
 `d`.`BarCode` AS `BarCode`,
 (
	CASE
	WHEN (`a`.`basicQty` <= 0) THEN
		1
	ELSE
		0
	END
) AS `isZero`,
 `e`.`DEPARTNAME` AS `sonName`,
 c.SONID,
 b.MODEL
FROM
	(
		(
			(
				(
					`t_sc_ic_inventory` `a`
					LEFT JOIN `t_sc_icitem` `b` ON ((`a`.`itemId` = `b`.`id`))
				)
				LEFT JOIN `t_sc_stock` `c` ON ((`a`.`stockId` = `c`.`id`))
			)
			LEFT JOIN `t_sc_item_price` `d` ON (
				(
					(`b`.`id` = `d`.`ItemID`)
					AND (`d`.`UnitType` = '0001')
				)
			)
			LEFT JOIN t_sc_item_price f ON b.id = f.ITEMID
			AND f.DEFAULTCK = 1
		)
		LEFT JOIN `t_s_depart` `e` ON ((`c`.`sonID` = `e`.`ID`))
	);

-- v_sc_ic_inventory_batchno 视图修改
-- ----------------------------
-- View structure for v_sc_ic_inventory_batchno
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_ic_inventory_batchno`$$
-- DROP VIEW IF EXISTS `v_sc_ic_inventory_batchno`$$
CREATE or REPLACE VIEW `v_sc_ic_inventory_batchno` AS
SELECT
	`a`.`id` AS `id`,
	`a`.`create_name` AS `create_name`,
	`a`.`create_by` AS `create_by`,
	`a`.`create_date` AS `create_date`,
	`a`.`update_name` AS `update_name`,
	`a`.`update_by` AS `update_by`,
	`a`.`update_date` AS `update_date`,
	`a`.`itemId` AS `itemId`,
	`a`.`stockId` AS `stockId`,
	`a`.`batchNo` AS `batchNo`,
	`a`.`kfDate` AS `kfDate`,
	`a`.`kfPeriod` AS `kfPeriod`,
	`a`.`kfDateType` AS `kfDateType`,
	concat(
		`a`.`kfPeriod`,
		`type`.`TYPENAME`
	) AS `kfDateType1`,
	`a`.`periodDate` AS `periodDate`,

IF (
	a.BASICQTY > 0,
	floor(
		`a`.`basicQty` / f.COEFFICIENT
	),
	CEILING(
		`a`.`basicQty` / f.COEFFICIENT
	)
) AS `qty`,
 (
	a.BASICQTY - (
		(

			IF (
				a.BASICQTY > 0,
				floor(
					`a`.`basicQty` / f.COEFFICIENT
				),
				CEILING(
					`a`.`basicQty` / f.COEFFICIENT
				)
			)
		) * f.COEFFICIENT
	)
) AS `smallQty`,
 `a`.`basicQty` AS `basicQty`,
 `a`.`secQty` AS `secQty`,
 `a`.`costPrice` AS `costPrice`,
 `a`.`costAmount` AS `costAmount`,
 `a`.`count` AS `count`,
 `b`.`Number` AS `itemNo`,
 `b`.`Name` AS `itemName`,
 `b`.`Model` AS `Model`,
 `c`.`name` AS `stockName`,
 `d`.`BarCode` AS `BarCode`,
 `b`.`ISKFPeriod` AS `ISKFPeriod`,
 `b`.`BatchManager` AS `BatchManager`,
 `b`.`Weight` AS `Weight`,
 `a`.`isCheck` AS `isCheck`,
 `b`.`TaxRateIn` AS `TaxRateIn`,
 `b`.`TaxRateOut` AS `TaxRateOut`,
 (
	CASE
	WHEN (
		ifnull(`a`.`basicQty`, 0) <= 0
	) THEN
		1
	ELSE
		0
	END
) AS `isZero`
FROM
	(
		(
			(
				(
					`t_sc_ic_inventory_batchno` `a`
					LEFT JOIN `t_sc_icitem` `b` ON ((`a`.`itemId` = `b`.`id`))
				)
				LEFT JOIN `t_sc_stock` `c` ON ((`a`.`stockId` = `c`.`id`))
			)
			LEFT JOIN `t_sc_item_price` `d` ON (
				(
					(`b`.`id` = `d`.`ItemID`)
					AND (`d`.`UnitType` = '0001')
				)
			)
			LEFT JOIN t_sc_item_price f ON b.id = f.ITEMID
			AND f.DEFAULTCK = 1
		)
		LEFT JOIN `t_s_type` `type` ON (
			(
				(
					`type`.`TYPECODE` = `a`.`kfDateType`
				)
				AND (
					`type`.`TYPEGROUPID` = (
						SELECT
							`t_s_typegroup`.`ID`
						FROM
							`t_s_typegroup`
						WHERE
							(
								`t_s_typegroup`.`TYPEGROUPCODE` = 'SC_DURA_DATE_TYPE'
							)
					)
				)
			)
		)
	)
ORDER BY
	`a`.`stockId`,
	`a`.`itemId`,
	`a`.`batchNo` DESC;

--通知公告表 t_sc_notice 修改通知人员数据类型长度 更改公告内容的数据类型
ALTER TABLE `t_sc_notice`
MODIFY COLUMN `USERID` varchar(7100) COMMENT '通知人员',
MODIFY COLUMN `CONTENT` text COMMENT '公告内容';

-- t_sc_bill_audit_status表添加cancellation字段
alter table t_sc_bill_audit_status add cancellation int null DEFAULT 0;

-- t_sc_stock给version字段配置默认值
update t_sc_stock set VERSION = 1

--通知公告表t_sc_notice 标题更改数据类型长度
ALTER TABLE `t_sc_notice`
MODIFY COLUMN `TITLE` varchar(200) COMMENT '标题';

--日志表t_sc_daily 日志内容更改数据类型
ALTER table `t_sc_daily`
MODIFY COLUMN `DAILY_INFO` text COMMENT '日志内容';

-- 新建t_sc_audit_bill_info表
CREATE TABLE `t_sc_audit_bill_info` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '主键',
  `create_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `create_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人登录名称',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `update_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人名称',
  `update_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人登录名称',
  `update_date` datetime DEFAULT NULL COMMENT '更新日期',
  `sonId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '分支机构id',
  `billId` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '单据id',
  `tranType` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '单据类型',
  `oldstate` int(1) DEFAULT NULL COMMENT '旧状态值',
  `newstate` int(1) DEFAULT NULL COMMENT '新状态值',
  PRIMARY KEY (`id`)
);

ALTER TABLE `t_sc_promotiongoodsentry`
MODIFY COLUMN `QTY`  decimal(20,10) NULL DEFAULT NULL COMMENT '数量' AFTER `UNITID`;

ALTER TABLE `t_sc_promotiongiftsentry`
MODIFY COLUMN `QTY`  decimal(20,10) NULL DEFAULT NULL COMMENT '数量' AFTER `UNITID`;

ALTER TABLE `t_sc_prcplyentry2`
MODIFY COLUMN `BEGQTY`  decimal(32,10) NULL DEFAULT NULL COMMENT '数量段（从）';

ALTER TABLE `t_sc_prcplyentry2`
MODIFY COLUMN `ENDQTY`  decimal(32,10) NULL DEFAULT NULL COMMENT '数量段（至）';

ALTER TABLE `t_sc_prcplyentry2`
MODIFY COLUMN `PRICE`  decimal(32,10) NULL DEFAULT NULL COMMENT '原价';

ALTER TABLE `t_sc_prcplyentry2`
MODIFY COLUMN `NEWPRICE`  decimal(32,10) NULL DEFAULT NULL COMMENT '新价';

ALTER TABLE `t_sc_prcplyentry2`
MODIFY COLUMN `DIFFERPRICE`  decimal(32,10) NULL DEFAULT NULL COMMENT '差价';

ALTER TABLE `t_sc_prcplyentry2`
MODIFY COLUMN `DISCOUNTRATE`  decimal(32,10) NULL DEFAULT NULL COMMENT '折扣率';

ALTER TABLE `t_sc_prcplyentry2`
MODIFY COLUMN `COSTPRICE`  decimal(32,10) NULL DEFAULT NULL COMMENT '成本单价';

ALTER TABLE `t_sc_prcplyentry2`
MODIFY COLUMN `GROSSPER`  decimal(32,10) NULL DEFAULT NULL COMMENT '原毛利率';

ALTER TABLE `t_sc_prcplyentry2`
MODIFY COLUMN `NEWGROSSPER`  decimal(32,10) NULL DEFAULT NULL COMMENT '新毛利率';


ALTER TABLE `t_sc_drp_orderentry`
MODIFY COLUMN `QTY`  decimal(32,10) NULL DEFAULT NULL COMMENT '数量';

ALTER TABLE `t_sc_drp_orderentry`
MODIFY COLUMN `COEFFICIENT`  decimal(32,10) NULL DEFAULT NULL COMMENT '换算率';

ALTER TABLE `t_sc_drp_orderentry`
MODIFY COLUMN `BASICQTY`  decimal(32,10) NULL DEFAULT NULL COMMENT '基本数量';

ALTER TABLE `t_sc_drp_orderentry`
MODIFY COLUMN `SECCOEFFICIENT`  decimal(32,10) NULL DEFAULT NULL COMMENT '辅助换算率';

ALTER TABLE `t_sc_drp_orderentry`
MODIFY COLUMN `SECQTY`  decimal(32,10) NULL DEFAULT NULL COMMENT '辅助数量';

ALTER TABLE `t_sc_drp_orderentry`
MODIFY COLUMN `TAXPRICEEX`  decimal(32,10) NULL DEFAULT NULL COMMENT '单价';

ALTER TABLE `t_sc_drp_orderentry`
MODIFY COLUMN `TAXAMOUNTEX`  decimal(32,10) NULL DEFAULT NULL COMMENT '金额';

ALTER TABLE `t_sc_drp_orderentry`
MODIFY COLUMN `DISCOUNTRATE`  decimal(32,10) NULL DEFAULT NULL COMMENT '折扣率';

ALTER TABLE `t_sc_drp_orderentry`
MODIFY COLUMN `TAXPRICE`  decimal(32,10) NULL DEFAULT NULL COMMENT '折后单价';

ALTER TABLE `t_sc_drp_orderentry`
MODIFY COLUMN `INTAXAMOUNT`  decimal(32,10) NULL DEFAULT NULL COMMENT '折后金额';

ALTER TABLE `t_sc_drp_orderentry`
MODIFY COLUMN `TAXRATE`  decimal(32,10) NULL DEFAULT NULL COMMENT '税率';

ALTER TABLE `t_sc_drp_orderentry`
MODIFY COLUMN `PRICE`  decimal(32,10) NULL DEFAULT NULL COMMENT '不含税单价';

ALTER TABLE `t_sc_drp_orderentry`
MODIFY COLUMN `AMOUNT`  decimal(32,10) NULL DEFAULT NULL COMMENT '不含税金额';

ALTER TABLE `t_sc_drp_orderentry`
MODIFY COLUMN `DISCOUNTPRICE`  decimal(32,10) NULL DEFAULT NULL COMMENT '不含税折后单价';

ALTER TABLE `t_sc_drp_orderentry`
MODIFY COLUMN `DISCOUNTAMOUNT`  decimal(32,10) NULL DEFAULT NULL COMMENT '不含税折后金额';

ALTER TABLE `t_sc_drp_orderentry`
MODIFY COLUMN `TAXAMOUNT`  decimal(32,10) NULL DEFAULT NULL COMMENT '税额';

ALTER TABLE `t_sc_drp_orderentry`
MODIFY COLUMN `AFFIRMQTY`  decimal(32,10) NULL DEFAULT NULL COMMENT '收货确认数量';

ALTER TABLE `t_sc_drp_orderentry`
MODIFY COLUMN `OUTSTOCKQTY`  decimal(32,10) NULL DEFAULT NULL COMMENT '发货数量';


ALTER TABLE `t_sc_drp_logistical`
MODIFY COLUMN `FREIGHT`  decimal(32,10) NULL DEFAULT NULL COMMENT '物流费用';

ALTER TABLE `t_sc_drp_logistical`
MODIFY COLUMN `CURPAYAMOUNT`  decimal(32,10) NULL DEFAULT NULL COMMENT '本次付款';

ALTER TABLE `t_sc_drp_logistical`
MODIFY COLUMN `CHECKAMOUNT`  decimal(32,10) NULL DEFAULT NULL COMMENT '执行金额';

ALTER TABLE `t_sc_drp_order`
MODIFY COLUMN `AMOUNT`  decimal(32,10) NULL DEFAULT NULL COMMENT '订单金额';

ALTER TABLE `t_sc_drp_order`
MODIFY COLUMN `ALLAMOUNT`  decimal(32,10) NULL DEFAULT NULL COMMENT '应付金额';

ALTER TABLE `t_sc_drp_order`
MODIFY COLUMN `CHECKAMOUNT`  decimal(32,10) NULL DEFAULT NULL COMMENT '执行金额';

ALTER TABLE `t_sc_drp_rstockbill`
MODIFY COLUMN `FREIGHT`  decimal(32,10) NULL DEFAULT NULL COMMENT '物流费用';

ALTER TABLE `t_sc_drp_rstockbill`
MODIFY COLUMN `AMOUNT`  decimal(32,10) NULL DEFAULT NULL COMMENT '单据金额';

ALTER TABLE `t_sc_drp_rstockbill`
MODIFY COLUMN `ALLAMOUNT`  decimal(32,10) NULL DEFAULT NULL COMMENT '应收金额';

ALTER TABLE `t_sc_drp_rstockbill`
MODIFY COLUMN `WEIGHT`  decimal(32,10) NULL DEFAULT NULL COMMENT '重量';

ALTER TABLE `t_sc_drp_rstockbillentry`
MODIFY COLUMN `QTY`  decimal(32,10) NULL DEFAULT NULL COMMENT '数量';

ALTER TABLE `t_sc_drp_rstockbillentry`
MODIFY COLUMN `SECCOEFFICIENT`  decimal(32,10) NULL DEFAULT NULL COMMENT '辅助换算率';

ALTER TABLE `t_sc_drp_rstockbillentry`
MODIFY COLUMN `SECQTY`  decimal(32,10) NULL DEFAULT NULL COMMENT '辅助数量';

ALTER TABLE `t_sc_drp_rstockbillentry`
MODIFY COLUMN `COEFFICIENT`  decimal(32,10) NULL DEFAULT NULL COMMENT '换算率';

ALTER TABLE `t_sc_drp_rstockbillentry`
MODIFY COLUMN `BASICQTY`  decimal(32,10) NULL DEFAULT NULL COMMENT '基本数量';

ALTER TABLE `t_sc_drp_rstockbillentry`
MODIFY COLUMN `PRICE`  decimal(32,10) NULL DEFAULT NULL COMMENT '不含税单价';

ALTER TABLE `t_sc_drp_rstockbillentry`
MODIFY COLUMN `AMOUNT`  decimal(32,10) NULL DEFAULT NULL COMMENT '不含税金额';

ALTER TABLE `t_sc_drp_rstockbillentry`
MODIFY COLUMN `DISCOUNTRATE`  decimal(32,10) NULL DEFAULT NULL COMMENT '折扣率(%)';

ALTER TABLE `t_sc_drp_rstockbillentry`
MODIFY COLUMN `DISCOUNTPRICE`  decimal(32,10) NULL DEFAULT NULL COMMENT '不含税折后单价';

ALTER TABLE `t_sc_drp_rstockbillentry`
MODIFY COLUMN `DISCOUNTAMOUNT`  decimal(32,10) NULL DEFAULT NULL COMMENT '不含税折后金额';

ALTER TABLE `t_sc_drp_rstockbillentry`
MODIFY COLUMN `TAXRATE`  decimal(32,10) NULL DEFAULT NULL COMMENT '税率(%)';

ALTER TABLE `t_sc_drp_rstockbillentry`
MODIFY COLUMN `TAXPRICEEX`  decimal(32,10) NULL DEFAULT NULL COMMENT '单价';

ALTER TABLE `t_sc_drp_rstockbillentry`
MODIFY COLUMN `TAXAMOUNTEX`  decimal(32,10) NULL DEFAULT NULL COMMENT '金额';

ALTER TABLE `t_sc_drp_rstockbillentry`
MODIFY COLUMN `TAXPRICE`  decimal(32,10) NULL DEFAULT NULL COMMENT '折后单价';

ALTER TABLE `t_sc_drp_rstockbillentry`
MODIFY COLUMN `INTAXAMOUNT`  decimal(32,10) NULL DEFAULT NULL COMMENT '折后金额';

ALTER TABLE `t_sc_drp_rstockbillentry`
MODIFY COLUMN `TAXAMOUNT`  decimal(32,10) NULL DEFAULT NULL COMMENT '税额';

ALTER TABLE `t_sc_drp_stockbill`
MODIFY COLUMN `REBATEAMOUNT`  decimal(32,10) NULL DEFAULT NULL COMMENT '优惠金额';

ALTER TABLE `t_sc_drp_stockbill`
MODIFY COLUMN `FREIGHT`  decimal(32,10) NULL DEFAULT NULL COMMENT '物流费用';

ALTER TABLE `t_sc_drp_stockbill`
MODIFY COLUMN `AMOUNT`  decimal(32,10) NULL DEFAULT NULL COMMENT '单据金额';

ALTER TABLE `t_sc_drp_stockbill`
MODIFY COLUMN `ALLAMOUNT`  decimal(32,10) NULL DEFAULT NULL COMMENT '应收金额';

ALTER TABLE `t_sc_drp_stockbill`
MODIFY COLUMN `CURPAYAMOUNT`  decimal(32,10) NULL DEFAULT NULL COMMENT '本次收款';

ALTER TABLE `t_sc_drp_stockbill`
MODIFY COLUMN `CHECKAMOUNT`  decimal(32,10) NULL DEFAULT NULL COMMENT '收款金额';

ALTER TABLE `t_sc_drp_stockbill`
MODIFY COLUMN `WEIGHT`  decimal(32,10) NULL DEFAULT NULL COMMENT '重量';

ALTER TABLE `t_sc_drp_stockbill`
MODIFY COLUMN `AMOUNTLOSS`  decimal(32,10) NULL DEFAULT NULL COMMENT '丢失金额';

ALTER TABLE `t_sc_drp_stockbillentry`
MODIFY COLUMN `QTY`  decimal(32,10) NULL DEFAULT NULL COMMENT '数量';

ALTER TABLE `t_sc_drp_stockbillentry`
MODIFY COLUMN `COEFFICIENT`  decimal(32,10) NULL DEFAULT NULL COMMENT '换算率';

ALTER TABLE `t_sc_drp_stockbillentry`
MODIFY COLUMN `BASICQTY`  decimal(32,10) NULL DEFAULT NULL COMMENT '基本数量';

ALTER TABLE `t_sc_drp_stockbillentry`
MODIFY COLUMN `SECCOEFFICIENT`  decimal(32,10) NULL DEFAULT NULL COMMENT '辅助换算率';

ALTER TABLE `t_sc_drp_stockbillentry`
MODIFY COLUMN `SECQTY`  decimal(32,10) NULL DEFAULT NULL COMMENT '辅助数量';

ALTER TABLE `t_sc_drp_stockbillentry`
MODIFY COLUMN `TAXPRICEEX`  decimal(32,10) NULL DEFAULT NULL COMMENT '单价';

ALTER TABLE `t_sc_drp_stockbillentry`
MODIFY COLUMN `TAXAMOUNTEX`  decimal(32,10) NULL DEFAULT NULL COMMENT '金额';

ALTER TABLE `t_sc_drp_stockbillentry`
MODIFY COLUMN `DISCOUNTRATE`  decimal(32,10) NULL DEFAULT NULL COMMENT '折扣率(%)';

ALTER TABLE `t_sc_drp_stockbillentry`
MODIFY COLUMN `TAXPRICE`  decimal(32,10) NULL DEFAULT NULL COMMENT '折后单价';

ALTER TABLE `t_sc_drp_stockbillentry`
MODIFY COLUMN `INTAXAMOUNT`  decimal(32,10) NULL DEFAULT NULL COMMENT '折后金额';

ALTER TABLE `t_sc_drp_stockbillentry`
MODIFY COLUMN `WEIGHT`  decimal(32,10) NULL DEFAULT NULL COMMENT '重量';

ALTER TABLE `t_sc_drp_stockbillentry`
MODIFY COLUMN `TAXRATE`  decimal(32,10) NULL DEFAULT NULL COMMENT '税率(%)';

ALTER TABLE `t_sc_drp_stockbillentry`
MODIFY COLUMN `PRICE`  decimal(32,10) NULL DEFAULT NULL COMMENT '不含税单价';

ALTER TABLE `t_sc_drp_stockbillentry`
MODIFY COLUMN `AMOUNT`  decimal(32,10) NULL DEFAULT NULL COMMENT '不含税金额';

ALTER TABLE `t_sc_drp_stockbillentry`
MODIFY COLUMN `DISCOUNTPRICE`  decimal(32,10) NULL DEFAULT NULL COMMENT '不含税折后单价';

ALTER TABLE `t_sc_drp_stockbillentry`
MODIFY COLUMN `DISCOUNTAMOUNT`  decimal(32,10) NULL DEFAULT NULL COMMENT '不含税折后金额';

ALTER TABLE `t_sc_drp_stockbillentry`
MODIFY COLUMN `TAXAMOUNT`  decimal(32,10) NULL DEFAULT NULL COMMENT '税额';

ALTER TABLE `t_sc_drp_stockbillentry`
MODIFY COLUMN `COMMITQTY`  decimal(32,10) NULL DEFAULT NULL COMMENT '退货数量';

ALTER TABLE `t_sc_drp_stockbillentry`
MODIFY COLUMN `STOCKQTY`  decimal(32,10) NULL DEFAULT NULL COMMENT '退货数量';

ALTER TABLE `t_sc_drp_stockbillentry`
MODIFY COLUMN `COMMITQTY`  decimal(32,10) NULL DEFAULT NULL COMMENT '确认收货数量';

-- ----------------------------
-- View structure for v_sc_quote_select
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_quote_select`$$
-- DROP VIEW IF EXISTS `v_sc_quote_select`$$
CREATE or REPLACE VIEW `v_sc_quote_select` AS
SELECT

	`c`.`ITEMID` AS `ItemID`,
	`a`.`ITEMID` AS `customerId`,
	`c`.`ID` AS `entryId`,
	`b`.`BILLNO` AS `billno`,
	`b`.`ID` AS `id`,
	`b`.`CANCELLATION` AS `cancellation`,
	c.INDEXNUMBER as indexNumber,
	`b`.`DATE` AS `date`,
	`b`.`EMPID` AS `empid`,
	`g`.`NAME` AS `empName`,
	`b`.`DEPTID` AS `deptid`,
	`h`.`NAME` AS `deptName`,
	`b`.`INUREDATE` AS `inureDate`,
	`d`.`NUMBER` AS `icitemNumber`,
	`d`.`NAME` AS `icitemName`,
	`d`.`MODEL` AS `icitemModel`,
	`e`.`BARCODE` AS `icitemBarCode`,
	`d`.`WEIGHT` AS `icitemWeight`,
	`f`.`ID` AS `icitemUnitId`,
	`f`.`NAME` AS `icitemUnitName`,
	`e`.`COEFFICIENT` AS `icitemCoefficient`,
	`e`.`XSLIMITPRICE` AS `icitemxsLimitPrice`,
	`e`.`ID` AS `UnitID`,
	`b`.`CHECKSTATE` AS `checkstate`,
	`i`.`REALNAME` AS `billerName`,
	`j`.`DEPARTNAME` AS `sonName`,
	`c`.`COSTPRICE` AS `CostPrice`,
	`c`.`GROSSPER` AS `Grossper`,
	`c`.`GROSSAMOUNT` AS `GrossAmount`,
	`c`.`BEGQTY` AS `BegQty`,
	`c`.`ENDQTY` AS `EndQty`,
	`c`.`NOTE` AS `Note`,
	`c`.`PRICE` AS `Price`,
	`b`.`TRANTYPE` AS `trantype`,
	`bill`.`BILL_NAME` AS `tranTypeName`
FROM
`t_sc_quote` `b`
LEFT JOIN `t_sc_quoteitems` `c` ON `b`.`ID` = `c`.`FID`
LEFT JOIN `t_sc_quotecustomer` `a` ON `b`.`ID` = `a`.`FID`
LEFT JOIN `t_sc_icitem` `d` ON `c`.`ITEMID` = `d`.`ID`
LEFT JOIN `t_sc_item_price` `e` ON `c`.`UNITID` = `e`.`ID`
LEFT JOIN `t_sc_measureunit` `f` ON `e`.`UNITID` = `f`.`ID`
LEFT JOIN `t_sc_emp` `g` ON `b`.`EMPID` = `g`.`ID`
LEFT JOIN `t_sc_department` `h` ON `b`.`DEPTID` = `h`.`ID`
LEFT JOIN `t_s_base_user` `i` ON `b`.`BILLERID` = `i`.`ID`
LEFT JOIN `t_s_depart` `j` ON `b`.`SONID` = `j`.`ID`
LEFT JOIN `t_s_bill_info` `bill` ON `b`.`TRANTYPE` = `bill`.`BILL_ID`
group by `c`.`ID`
having count(distinct	`c`.`ID` > 1)
	ORDER BY
	`b`.`DATE` DESC,
	`b`.`BILLNO` DESC,
	`c`.`INDEXNUMBER` ;

-- ----------------------------
-- View structure for v_sc_prcply
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_prcply`$$
-- DROP VIEW IF EXISTS `v_sc_prcply`$$
CREATE or REPLACE VIEW `v_sc_prcply` AS
  SELECT
	`p`.`ID` AS `id`,
	`p2`.`ID` AS `entryId`,
	`p`.`BILLNO` AS `billNo`,
	`p`.`DATE` AS `date`,
	`p`.`EMPID` AS `empID`,
	`p`.`DEPTID` AS `deptID`,
	`p`.`EXPLANATION` AS `explanation`,
	`p`.`BILLERID` AS `billerID`,
	`p`.`CHECKERID` AS `checkerID`,
	`p`.`CHECKDATE` AS `checkDate`,
	`p`.`CHECKSTATE` AS `checkstate`,
	`p`.`CANCELLATION` AS `cancellation`,
	`p`.`SONID` AS `sonID`,
	`p2`.`INDEXNUMBER` AS `indexNumber`,
	`p2`.`ITEMID` AS `itemID`,
	`i`.`NUMBER` AS `Number`,
	`i`.`NAME` AS `Name`,
	`i`.`MODEL` AS `Model`,
	`pr`.`BARCODE` AS `BarCode`,
	`pr`.`ID` AS `UnitID`,
	`p2`.`BEGQTY` AS `begqty`,
	`p2`.`ENDQTY` AS `endqty`,
	`p2`.`PRICE` AS `price`,
	`p2`.`NEWPRICE` AS `newprice`,
	`p2`.`DIFFERPRICE` AS `differprice`,
	`p2`.`DISCOUNTRATE` AS `discountrate`,
	`p2`.`BEGDATE` AS `begDate`,
	`p2`.`ENDDATE` AS `endDate`,
	`p2`.`COSTPRICE` AS `costprice`,
	`p2`.`GROSSPER` AS `grossper`,
	`p2`.`NEWGROSSPER` AS `newgrossper`,
	`p2`.`NOTE` AS `note`,
	`u`.`NAME` AS `unitName`,
	`p2`.`INTERID` AS `goodsInterID`,

	`e`.`NAME` AS `empName`,
	`j`.`NAME` AS `deptName`,
	`l`.`REALNAME` AS `billerName`,
	`m`.`DEPARTNAME` AS `sonName`
FROM
`t_sc_prcply` `p`
LEFT JOIN `t_sc_prcplyentry2` `p2` ON `p2`.`INTERID` = `p`.`ID`
LEFT JOIN `t_sc_icitem` `i` ON `i`.`ID` = `p2`.`ITEMID`
LEFT JOIN `t_sc_item_price` `pr` ON `pr`.`ID` = `p2`.`UNITID`
LEFT JOIN `t_sc_measureunit` `u` ON `u`.`ID` = `pr`.`UNITID`
LEFT JOIN `t_sc_emp` `e` ON `p`.`EMPID` = `e`.`ID`
LEFT JOIN `t_sc_department` `j` ON `p`.`DEPTID` = `j`.`ID`
LEFT JOIN `t_s_base_user` `l` ON `p`.`BILLERID` = `l`.`ID`
LEFT JOIN `t_s_depart` `m` ON `p`.`SONID` = `m`.`ID`
ORDER BY
	`p`.`DATE` DESC,
	`p`.`BILLNO` DESC,
	`p2`.`INDEXNUMBER`  $$

-- 基础资料添加sonId字段
ALTER TABLE T_SC_ITEM_TYPE ADD sonId varchar(32) null; -- 基础资料分类
ALTER TABLE T_SC_SUPPLIER ADD sonId varchar(32) null; -- 供应商
ALTER TABLE T_SC_Organization ADD sonId varchar(32) null; -- 客户
ALTER TABLE T_SC_LOGISTICAL ADD sonId varchar(32) null; -- 物流公司
ALTER TABLE T_SC_Fee ADD sonId varchar(32) null; -- 收支项目
ALTER TABLE T_SC_EMP ADD sonId varchar(32) null; -- 职员
ALTER TABLE T_SC_ICItem ADD sonId varchar(32) null; -- 商品

--修改 v_sc_organization 视图

DROP TABLE IF EXISTS `v_sc_organization`$$
-- DROP VIEW IF EXISTS `v_sc_organization`$$
CREATE or REPLACE VIEW `v_sc_organization` AS
SELECT
	`o`.`CREATE_NAME` AS `createName`,
	`o`.`CREATE_BY` AS `createBy`,
	`o`.`CREATE_DATE` AS `createDate`,
	`o`.`UPDATE_NAME` AS `updateName`,
	`o`.`UPDATE_BY` AS `updateBy`,
	`o`.`UPDATE_DATE` AS `updateDate`,
	`o`.`ID` AS `id`,
	`o`.`NUMBER` AS `number`,
	`o`.`NAME` AS `name`,
	`o`.`SHORTNAME` AS `shortName`,
	`o`.`SHORTNUMBER` AS `shortNumber`,
	`o`.`CORPERATE` AS `corperate`,
	`o`.`CONTACT` AS `contact`,
	`o`.`MOBILEPHONE` AS `mobilePhone`,
	`o`.`PHONE` AS `phone`,
	`o`.`FAX` AS `fax`,
	`o`.`ISDEALER` AS `isDealer`,
	`o`.`ADDRESS` AS `address`,
	`o`.`REGIONID` AS `regIonid`,
	`op`.`NAME` AS `parentDealerName`,
	`o`.`TYPEID` AS `typeId`,
	`e`.`NAME` AS `businessName`,
	`o`.`LICENCE` AS `licence`,
	`o`.`CITY` AS `city`,
	`o`.`POSTALCODE` AS `postAlcode`,
	`o`.`EMAIL` AS `email`,
	`o`.`HOMEPAGE` AS `homePage`,
	`jo`.`NAME` AS `settlementName`,
	`o`.`BANK` AS `bank`,
	`o`.`BANKNUMBER` AS `bankNumber`,
	`o`.`CIQNUMBER` AS `ciqNumber`,
	`o`.`TRADE` AS `trade`,
	`o`.`DELIVERTYPE` AS `deliverType`,
	`o`.`ISCREDITMGR` AS `isCreditmgr`,
	`o`.`CREDITAMOUNT` AS `creditAmount`,
	`o`.`PARENTID` AS `parentid`,
	`o`.`DISABLE` AS `disable`,
	`o`.`COUNT` AS `count`,
   o.sonId
FROM
	(
		(
			(
				`t_sc_organization` `o`
				LEFT JOIN `t_sc_organization` `op` ON (
					(
						`o`.`DEALER` = `op`.`ID`
					)
				)
			)
			LEFT JOIN `t_sc_emp` `e` ON (
				(
					`o`.`DEFAULTOPERATOR` = `e`.`ID`
				)
			)
		)
		LEFT JOIN `t_sc_organization` `jo` ON (
			(
				`jo`.`ID` = `o`.`SETTLECOMPANY`
			)
		)
	)  $$

-- 修改 v_sc_quote_select 视图
DROP TABLE IF EXISTS `v_sc_quote_select`$$
-- DROP VIEW IF EXISTS `v_sc_quote_select`$$
CREATE or REPLACE VIEW `v_sc_quote_select` AS
SELECT

	`c`.`ITEMID` AS `ItemID`,
	`a`.`ITEMID` AS `customerId`,
	`c`.`ID` AS `entryId`,
	`b`.`BILLNO` AS `billno`,
	`b`.`ID` AS `id`,
	`b`.`CANCELLATION` AS `cancellation`,
	c.INDEXNUMBER as indexNumber,
	`b`.`DATE` AS `date`,
	`b`.`EMPID` AS `empid`,
	`g`.`NAME` AS `empName`,
	`b`.`DEPTID` AS `deptid`,
	`h`.`NAME` AS `deptName`,
	`b`.`INUREDATE` AS `inureDate`,
	`d`.`NUMBER` AS `icitemNumber`,
	`d`.`NAME` AS `icitemName`,
	`d`.`MODEL` AS `icitemModel`,
	`e`.`BARCODE` AS `icitemBarCode`,
	`d`.`WEIGHT` AS `icitemWeight`,
	`f`.`ID` AS `icitemUnitId`,
	`f`.`NAME` AS `icitemUnitName`,
	`e`.`COEFFICIENT` AS `icitemCoefficient`,
	`e`.`XSLIMITPRICE` AS `icitemxsLimitPrice`,
	`e`.`ID` AS `UnitID`,
	`b`.`CHECKSTATE` AS `checkstate`,
	`i`.`REALNAME` AS `billerName`,
	`j`.`DEPARTNAME` AS `sonName`,
	`c`.`COSTPRICE` AS `CostPrice`,
	`c`.`GROSSPER` AS `Grossper`,
	`c`.`GROSSAMOUNT` AS `GrossAmount`,
	`c`.`BEGQTY` AS `BegQty`,
	`c`.`ENDQTY` AS `EndQty`,
	`c`.`NOTE` AS `Note`,
	`c`.`PRICE` AS `Price`,
	`b`.`TRANTYPE` AS `trantype`,
	`bill`.`BILL_NAME` AS `tranTypeName`,
     b.SONID
FROM
`t_sc_quote` `b`
LEFT JOIN `t_sc_quoteitems` `c` ON `b`.`ID` = `c`.`FID`
LEFT JOIN `t_sc_quotecustomer` `a` ON `b`.`ID` = `a`.`FID`
LEFT JOIN `t_sc_icitem` `d` ON `c`.`ITEMID` = `d`.`ID`
LEFT JOIN `t_sc_item_price` `e` ON `c`.`UNITID` = `e`.`ID`
LEFT JOIN `t_sc_measureunit` `f` ON `e`.`UNITID` = `f`.`ID`
LEFT JOIN `t_sc_emp` `g` ON `b`.`EMPID` = `g`.`ID`
LEFT JOIN `t_sc_department` `h` ON `b`.`DEPTID` = `h`.`ID`
LEFT JOIN `t_s_base_user` `i` ON `b`.`BILLERID` = `i`.`ID`
LEFT JOIN `t_s_depart` `j` ON `b`.`SONID` = `j`.`ID`
LEFT JOIN `t_s_bill_info` `bill` ON `b`.`TRANTYPE` = `bill`.`BILL_ID`
group by `c`.`ID`
having count(distinct	`c`.`ID` > 1)
	ORDER BY
	`b`.`DATE` DESC,
	`b`.`BILLNO` DESC,
	`c`.`INDEXNUMBER`   $$


-- T_SC_DRP_OrderEntry表分录号字段类型修改
ALTER TABLE T_SC_DRP_OrderEntry MODIFY indexNumber INT(10);