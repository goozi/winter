DELIMITER $$
-- ----------------------------
-- View structure for v_sc_checkspeedbal_sub_ym
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_checkspeedbal_sub_ym`$$
-- DROP VIEW IF EXISTS `v_sc_checkspeedbal_sub_ym`$$
CREATE or REPLACE  VIEW `v_sc_checkspeedbal_sub_ym` AS
  SELECT
    date_format(`a`.`date`, '%Y%m') AS `date`,
    `a`.`stockId`                   AS `stockId`,
    `a`.`itemId`                    AS `itemId`,
    `a`.`batchNo`                   AS `batchNo`,
    `a`.`qty`                       AS `qty`,
    `a`.`flag`                      AS `flag`,
    `a`.`secQty`                    AS `secQty`
  FROM `t_sc_ic_speedbal` `a`$$

-- ----------------------------
-- View structure for v_sc_checkspeedbal_sub
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_checkspeedbal_sub`$$
-- DROP VIEW IF EXISTS `v_sc_checkspeedbal_sub`$$
CREATE or REPLACE  VIEW `v_sc_checkspeedbal_sub` AS
  SELECT
    `a`.`date`                       AS `date`,
    `a`.`stockId`                    AS `stockId`,
    `a`.`itemId`                     AS `itemId`,
    `a`.`batchNo`                    AS `batchNo`,
    sum((`a`.`qty` * `a`.`flag`))    AS `qty`,
    sum((`a`.`secQty` * `a`.`flag`)) AS `secQty`
  FROM `v_sc_checkspeedbal_sub_ym` `a`
  WHERE `a`.`date`
  GROUP BY `a`.`date`, `a`.`stockId`, `a`.`itemId`, `a`.`batchNo`$$

-- ----------------------------
-- View structure for v_sc_checkstage_sub
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_checkstage_sub`$$
-- DROP VIEW IF EXISTS `v_sc_checkstage_sub`$$
CREATE or REPLACE  VIEW `v_sc_checkstage_sub` AS SELECT
                                        `a`.`date`       AS `date`,
                                        `a`.`checkState` AS `checkstate`,
                                        `a`.`tranType`   AS `tranType`,
                                        `a`.`billNo`     AS `billno`,
                                        `a`.`sonId`      AS `sonId`,
                                        `a`.`id`         AS `id`
                                      FROM `t_sc_ic_jhstockbill` `a`
                                      WHERE ((`a`.`cancellation` = 0) AND (`a`.`checkState` < 2))
                                      UNION SELECT
                                              `a`.`date`       AS `date`,
                                              `a`.`checkState` AS `checkstate`,
                                              `a`.`tranType`   AS `tranType`,
                                              `a`.`billNo`     AS `billno`,
                                              `a`.`sonId`      AS `sonId`,
                                              `a`.`id`         AS `id`
                                            FROM `t_sc_ic_xsstockbill` `a`
                                            WHERE ((`a`.`cancellation` = 0) AND (`a`.`checkState` < 2))
                                      UNION SELECT
                                              `a`.`Date`       AS `date`,
                                              `a`.`CheckState` AS `checkstate`,
                                              `a`.`trantype`   AS `tranType`,
                                              `a`.`BillNo`     AS `billno`,
                                              `a`.`SonID`      AS `sonId`,
                                              `a`.`id`         AS `id`
                                            FROM `t_sc_order` `a`
                                            WHERE ((`a`.`Cancellation` = 0) AND (`a`.`CheckState` < 2))
                                      UNION SELECT
                                              `a`.`date`       AS `date`,
                                              `a`.`checkState` AS `checkstate`,
                                              `a`.`tranType`   AS `tranType`,
                                              `a`.`billNo`     AS `billno`,
                                              `a`.`sonId`      AS `sonId`,
                                              `a`.`id`         AS `id`
                                            FROM `t_sc_po_order` `a`
                                            WHERE ((`a`.`cancellation` = 0) AND (`a`.`checkState` < 2))
                                      UNION SELECT
                                              `a`.`date`       AS `date`,
                                              `a`.`checkState` AS `checkstate`,
                                              `a`.`tranType`   AS `tranType`,
                                              `a`.`billNo`     AS `billno`,
                                              `a`.`sonId`      AS `sonId`,
                                              `a`.`id`         AS `id`
                                            FROM `t_sc_po_stockbill` `a`
                                            WHERE ((`a`.`cancellation` = 0) AND (`a`.`checkState` < 2))
                                      UNION SELECT
                                              `a`.`date`       AS `date`,
                                              `a`.`checkstate` AS `checkstate`,
                                              `a`.`trantype`   AS `tranType`,
                                              `a`.`billno`     AS `billno`,
                                              `a`.`sonid`      AS `sonId`,
                                              `a`.`id`         AS `id`
                                            FROM `t_sc_quote` `a`
                                            WHERE ((`a`.`cancellation` = 0) AND (`a`.`checkstate` < 2))
                                      UNION SELECT
                                              `a`.`date`       AS `date`,
                                              `a`.`checkstate` AS `checkstate`,
                                              `a`.`tranType`   AS `tranType`,
                                              `a`.`billNo`     AS `billno`,
                                              `a`.`sonId`      AS `sonId`,
                                              `a`.`id`         AS `id`
                                            FROM `t_sc_rp_pbill` `a`
                                            WHERE ((`a`.`cancellation` = 0) AND (`a`.`checkstate` < 2))
                                      UNION SELECT
                                              `a`.`date`       AS `date`,
                                              `a`.`checkstate` AS `checkstate`,
                                              `a`.`tranType`   AS `tranType`,
                                              `a`.`billNo`     AS `billno`,
                                              `a`.`sonId`      AS `sonId`,
                                              `a`.`id`         AS `id`
                                            FROM `t_sc_rp_rbill` `a`
                                            WHERE ((`a`.`cancellation` = 0) AND (`a`.`checkstate` < 2))
                                      UNION SELECT
                                              `a`.`date`       AS `date`,
                                              `a`.`checkState` AS `checkstate`,
                                              `a`.`tranType`   AS `tranType`,
                                              `a`.`billNo`     AS `billno`,
                                              `a`.`sonId`      AS `sonId`,
                                              `a`.`id`         AS `id`
                                            FROM `t_sc_sl_stockbill` `a`
                                            WHERE ((`a`.`cancellation` = 0) AND (`a`.`checkState` < 2))
                                      UNION SELECT
                                              `a`.`date`       AS `date`,
                                              `a`.`checkState` AS `checkstate`,
                                              `a`.`tranType`   AS `tranType`,
                                              `a`.`billNo`     AS `billno`,
                                              `a`.`sonId`      AS `sonId`,
                                              `a`.`id`         AS `id`
                                            FROM `t_sc_ic_chkstockbill` `a`
                                            WHERE ((`a`.`cancellation` = 0) AND (`a`.`checkState` < 2))
                                      UNION SELECT
                                              `a`.`date`       AS `date`,
                                              `a`.`checkState` AS `checkstate`,
                                              `a`.`tranType`   AS `tranType`,
                                              `a`.`billNo`     AS `billno`,
                                              `a`.`sonID`      AS `sonId`,
                                              `a`.`id`         AS `id`
                                            FROM `t_sc_drp_order` `a`
                                            WHERE ((`a`.`cancellation` = 0) AND (`a`.`checkState` < 2))
                                      UNION SELECT
                                              `a`.`date`       AS `date`,
                                              `a`.`checkState` AS `checkstate`,
                                              `a`.`tranType`   AS `tranType`,
                                              `a`.`billNo`     AS `billno`,
                                              `a`.`sonId`      AS `sonId`,
                                              `a`.`id`         AS `id`
                                            FROM `t_sc_drp_rstockbill` `a`
                                            WHERE ((`a`.`cancellation` = 0) AND (`a`.`checkState` < 2))
                                      UNION SELECT
                                              `a`.`date`       AS `date`,
                                              `a`.`checkState` AS `checkstate`,
                                              `a`.`tranType`   AS `tranType`,
                                              `a`.`billNo`     AS `billno`,
                                              `a`.`sonID`      AS `sonId`,
                                              `a`.`id`         AS `id`
                                            FROM `t_sc_drp_stockbill` `a`
                                            WHERE ((`a`.`cancellation` = 0) AND (`a`.`checkState` < 2))
                                      UNION SELECT
                                              `a`.`date`       AS `date`,
                                              `a`.`checkstate` AS `checkstate`,
                                              `a`.`trantype`   AS `tranType`,
                                              `a`.`billNo`     AS `billno`,
                                              `a`.`sonID`      AS `sonId`,
                                              `a`.`id`         AS `id`
                                            FROM `t_sc_prcply` `a`
                                            WHERE ((`a`.`cancellation` = 0) AND (`a`.`checkstate` < 2))
                                      UNION SELECT
                                              `a`.`date`       AS `date`,
                                              `a`.`checkState` AS `checkstate`,
                                              `a`.`tranType`   AS `tranType`,
                                              `a`.`billNo`     AS `billno`,
                                              `a`.`sonID`      AS `sonId`,
                                              `a`.`id`         AS `id`
                                            FROM `t_sc_promotion` `a`
                                            WHERE ((`a`.`cancellation` = 0) AND (`a`.`checkState` < 2))
                                      UNION SELECT
                                              `a`.`date`       AS `date`,
                                              `a`.`checkstate` AS `checkstate`,
                                              `a`.`tranType`   AS `tranType`,
                                              `a`.`billNo`     AS `billno`,
                                              `a`.`sonId`      AS `sonId`,
                                              `a`.`id`         AS `id`
                                            FROM `t_sc_rp_adjustbill` `a`
                                            WHERE ((`a`.`cancellation` = 0) AND (`a`.`checkstate` < 2))
                                      UNION SELECT
                                              `a`.`date`       AS `date`,
                                              `a`.`checkState` AS `checkstate`,
                                              `a`.`tranType`   AS `tranType`,
                                              `a`.`billNo`     AS `billno`,
                                              `a`.`sonId`      AS `sonId`,
                                              `a`.`id`         AS `id`
                                            FROM `t_sc_rp_bankbill` `a`
                                            WHERE ((`a`.`cancellation` = 0) AND (`a`.`checkState` < 2))
                                      UNION SELECT
                                              `a`.`date`       AS `date`,
                                              `a`.`checkState` AS `checkstate`,
                                              `a`.`tranType`   AS `tranType`,
                                              `a`.`billNo`     AS `billno`,
                                              `a`.`sonId`      AS `sonId`,
                                              `a`.`id`         AS `id`
                                            FROM `t_sc_rp_expensesapply` `a`
                                            WHERE ((`a`.`cancellation` = 0) AND (`a`.`checkState` < 2))
                                      UNION SELECT
                                              `a`.`date`       AS `date`,
                                              `a`.`checkState` AS `checkstate`,
                                              `a`.`tranType`   AS `tranType`,
                                              `a`.`billNo`     AS `billno`,
                                              `a`.`sonId`      AS `sonId`,
                                              `a`.`id`         AS `id`
                                            FROM `t_sc_rp_potherbill` `a`
                                            WHERE ((`a`.`cancellation` = 0) AND (`a`.`checkState` < 2))
                                      UNION SELECT
                                              `a`.`date`       AS `date`,
                                              `a`.`checkState` AS `checkstate`,
                                              `a`.`tranType`   AS `tranType`,
                                              `a`.`billNo`     AS `billno`,
                                              `a`.`sonId`      AS `sonId`,
                                              `a`.`id`         AS `id`
                                            FROM `t_sc_rp_rotherbill` `a`
                                            WHERE ((`a`.`cancellation` = 0) AND (`a`.`checkState` < 2))$$

-- ----------------------------
-- View structure for v_sc_rp_rreportbill
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_rp_rreportbill`$$
-- DROP VIEW IF EXISTS `v_sc_rp_rreportbill`$$
CREATE or REPLACE  VIEW `v_sc_rp_rreportbill` AS -- ---------------------初始化数据
  SELECT
    BillNo,
    Date,
    TranType,
    id,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    AllAmount,
    CheckAmount,
    UnCheckAmount,
    0 AS CheckFlag,
    create_date,
    SonID,
    Explanation,
    0 AS cancellation,
    CheckState
  FROM T_SC_BegData
  WHERE TranType = 1021 # AND cancellation=0

  UNION ALL
  SELECT
    BillNo,
    Date,
    TranType,
    id,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    0,
    AllAmount,
    0,
    1 AS CheckFlag,
    create_date,
    SonID,
    Explanation,
    0 AS cancellation,
    CheckState
  FROM T_SC_BegData
  WHERE TranType = 1028 # AND cancellation=0

  UNION ALL
  -- ---------------------销售结算单
  SELECT
    BillNo,
    Date,
    TranType,
    id,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    CASE WHEN TranType = 103
      THEN AllAmount
    ELSE -AllAmount END                 AS AllAmount,
    CASE WHEN TranType = 103
      THEN CheckAmount
    ELSE -CheckAmount END               AS CheckAmount,
    CASE WHEN TranType = 103
      THEN AllAmount - CheckAmount
    ELSE -(AllAmount - CheckAmount) END AS UnCheckAmount,
    0                                   AS CheckFlag,
    create_date,
    SonID,
    Explanation,
    cancellation,
    CheckState
  FROM T_SC_SL_StockBill
  WHERE cancellation = 0

  UNION ALL

  SELECT
    BillNo,
    Date,
    TranType,
    id,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    0                      AS AllAmount,
    CASE WHEN TranType = 103
      THEN CurPayAmount
    ELSE -CurPayAmount END AS CheckAmount,
    0                      AS UnCheckAmount,
    1                      AS CheckFlag,
    create_date,
    SonID,
    Explanation,
    cancellation,
    CheckState
  FROM T_SC_SL_StockBill
  WHERE CurPayAmount <> 0 AND cancellation = 0

  UNION ALL

  SELECT
    BillNo,
    Date,
    TranType,
    id,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    0                      AS AllAmount,
    CASE WHEN TranType = 103
      THEN RebateAmount
    ELSE -RebateAmount END AS CheckAmount,
    0                      AS UnCheckAmount,
    2                      AS CheckFlag,
    create_date,
    SonID,
    Explanation,
    cancellation,
    CheckState
  FROM T_SC_SL_StockBill
  WHERE RebateAmount <> 0 AND cancellation = 0
  -- 销售出库应付物流费用 从t_sc_sl_stockBIll.freight中取（晓峰：有物流单时会回填数据到此）
  UNION ALL
  SELECT
    BillNo,
    Date,
    TranType,
    id,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    0                AS AllAmount,
    CASE WHEN TranType = 103
      THEN freight
    ELSE freight END AS CheckAmount,
    0                AS UnCheckAmount,
    1                AS CheckFlag,
    create_date,
    SonID,
    Explanation,
    cancellation,
    CheckState
  FROM T_SC_SL_StockBill
  WHERE RebateAmount <> 0 AND cancellation = 0
  UNION ALL
  -- ----------------------------采购/销售费用直接计入应收应付
  /*
   --  【明天问经理，采购/销售费用T_IC_Expenses这块我们是不是一期没有做？】
   SELECT s1.FBillNo,s1.FDate,s1.FTranType,s1.id,s1.FItemClassID2,s1.FItemID2,s1.FDeptID,s1.FEmpID,s1.FItemAmount,
          s1.FItemCheckAmount,s1.FItemUnCheckAmount,0 AS FCheckFlag,s1.create_date,s1.FSonID,s1.FExplanation,s1.Fcancellation,
          s1.FCheckState
   FROM T_IC_Expenses s1
   WHERE s1.FTranType=212 AND s1.FItemAmount<>0 AND s1.cancellation=0 AND
     EXISTS(SELECT 1 FROM T_SYS_ProFile WHERE FCategory='IC' AND FKey='ExpensesSL' AND FValue=1)
     AND NOT EXISTS(SELECT 1 FROM T_RP_AdjustBill WHERE FClassID_SRC=212 AND FInterID_SRC=s1.id AND FTranType=201)

   UNION ALL
   SELECT s1.FBillNo,s1.FDate,s1.FTranType,s1.id,s1.FItemClassID2,s1.FItemID2,s1.FDeptID,s1.FEmpID,s1.FItemAmount,
          s1.FItemCheckAmount,s1.FItemUnCheckAmount,0 AS FCheckFlag,s1.create_date,s1.FSonID,s1.FExplanation,
          s1.cancellation,s1.FCheckState
   FROM T_IC_Expenses s1
   WHERE s1.FTranType=262 AND s1.FItemAmount<>0 AND s1.cancellation=0 AND
     EXISTS(SELECT 1 FROM T_SYS_ProFile WHERE FCategory='IC' AND FKey='ExpensesPO' AND FValue=1)
     AND NOT EXISTS(SELECT 1 FROM T_RP_AdjustBill WHERE FClassID_SRC=262 AND FInterID_SRC=s1.id AND FTranType=201)

   UNION ALL*/
  -- ----------------------------销售换货单
  SELECT
    BillNo,
    Date,
    TranType,
    id,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    DiffAmount,
    0,
    0,
    0 AS CheckFlag,
    create_date,
    SonID,
    Explanation,
    cancellation,
    CheckState
  FROM T_SC_IC_JHStockBill
  WHERE TranType = 110 AND DiffAmount <> 0 AND cancellation = 0

  UNION ALL

  SELECT
    BillNo,
    Date,
    TranType,
    id,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    0,
    CurPayAmount,
    0,
    1 AS CheckFlag,
    create_date,
    SonID,
    Explanation,
    cancellation,
    CheckState
  FROM T_SC_IC_JHStockBill
  WHERE CurPayAmount <> 0 AND TranType = 110 AND cancellation = 0

  UNION ALL

  SELECT
    BillNo,
    Date,
    TranType,
    id,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    0,
    RebateAmount,
    0,
    2 AS CheckFlag,
    create_date,
    SonID,
    Explanation,
    cancellation,
    CheckState
  FROM T_SC_IC_JHStockBill
  WHERE RebateAmount <> 0 AND TranType = 110 AND cancellation = 0

  UNION ALL
  -- ---------------------------调账数据
  SELECT
    BillNo,
    Date,
    TranType,
    id,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    CASE WHEN TranType = 201
      THEN AllAmount
    ELSE -AllAmount END,
    CASE WHEN TranType = 201
      THEN CheckAmount
    ELSE -CheckAmount END,
    CASE WHEN TranType = 201
      THEN AllAmount - CheckAmount
    ELSE -(AllAmount - CheckAmount) END,
    0 AS CheckFlag,
    create_date,
    SonID,
    Explanation,
    cancellation,
    CheckState
  FROM T_SC_RP_AdjustBill
  WHERE TranType IN (201, 202) AND cancellation = 0


  UNION ALL
  /*----------------------------核销
   -- 一期没有做
   SELECT t1.FBillNo,t1.FDate,t1.FTranType,t1.id,t1.FItemClassID,t1.FItemID,t1.FDeptID,t1.FEmpID,
          0,t2.FBillSettleAmount,0,1 AS FCheckFlag,t1.create_date,t1.FSonID,
          t1.FExplanation,cancellation,FCheckState
   FROM T_RP_CheckBillEntry1 t2
	 LEFT JOIN T_RP_CheckBill t1 ON t1.id=t2.id
   WHERE FTranType in (208,210) AND t1.cancellation=0

   UNION ALL
   SELECT t1.FBillNo,t1.FDate,t1.FTranType,t1.id,t1.FItemClassID2,t1.FItemID2,t1.FDeptID,t1.FEmpID,0,
          t2.FBillSettleAmount,0,1 AS FCheckFlag,t1.create_date,t1.FSonID,
          t1.FExplanation,cancellation,FCheckState
   FROM T_RP_CheckBillEntry2 t2
	  LEFT JOIN T_RP_CheckBill t1 ON t1.id=t2.id
   WHERE t1.FTranType=260 AND t1.cancellation=0

   UNION ALL*/
  -- -----------------------收款单
  SELECT
    BillNo,
    Date,
    TranType,
    id,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    0,
    CASE WHEN TranType = 105
      THEN AllAmount
    ELSE -AllAmount END,
    0,
    1 AS CheckFlag,
    create_date,
    SonID,
    Explanation,
    cancellation,
    CheckState
  FROM T_SC_RP_RBill
  WHERE cancellation = 0

/*UNION ALL
   ------------------------收款冲账单/预收冲应收
   -- 一期没有做
   SELECT t1.FBillNo,t1.FDate,t1.FTranType,t1.id,t1.FItemClassID,t1.FItemID,t1.FDeptID,t1.FEmpID,
          0,-t2.FBillSettleAmount,0,1 AS FCheckFlag,t1.create_date,t1.FSonID,t1.FExplanation,t1.cancellation,t1.FCheckState
   FROM T_RP_CheckBillEntry1 t2
	 LEFT JOIN T_RP_CheckBill t1 ON t1.id=t2.id
   WHERE t1.FTranType in (209,315) AND t1.cancellation=0

   UNION ALL
   SELECT t1.FBillNo,t1.FDate,t1.FTranType,t1.id,t1.FItemClassID,t1.FItemID,t1.FDeptID,t1.FEmpID,-t2.FBillSettleAmount,
          0,0,0 AS FCheckFlag,t1.create_date,t1.FSonID,t1.FExplanation,t1.cancellation,t1.FCheckState
   FROM T_RP_CheckBillEntry2 t2
	  LEFT JOIN T_RP_CheckBill t1 ON t1.id=t2.id
   WHERE t1.FTranType in (209,315) AND t1.cancellation=0*/
/*
   要看一下Q7 P165 付款单-业务需求-源单数据，要补充分销、（晓峰）物流
   应付源单=应付初始化+采购入库-采购退货+应付调账+换货应付金额+销售出库应付物流费用；
   */$$

-- ----------------------------
-- View structure for v_sc_rp_preportbill
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_rp_preportbill`$$
-- DROP VIEW IF EXISTS `v_sc_rp_preportbill`$$
CREATE or REPLACE VIEW `v_sc_rp_preportbill` AS -- ---------------------初始化数据
  SELECT
    BillNo,
    Date,
    TranType,
    id,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    AllAmount,
    CheckAmount,
    UnCheckAmount,
    0 AS CheckFlag,
    create_date,
    SonID,
    Explanation,
    0 AS Cancellation,
    CheckState
  FROM T_SC_BegData
  WHERE TranType = 1022 #AND Cancellation=0

  UNION ALL
  SELECT
    BillNo,
    Date,
    TranType,
    id,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    0,
    AllAmount,
    0,
    1 AS CheckFlag,
    create_date,
    SonID,
    Explanation,
    0 AS Cancellation,
    CheckState
  FROM T_SC_BegData
  WHERE TranType = 1029 #AND Cancellation=0

  UNION ALL
  -- ---------------------采购入库单
  SELECT
    BillNo,
    Date,
    TranType,
    id,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    CASE WHEN TranType IN (52, 1604)
      THEN AllAmount
    ELSE -AllAmount END                 AS AllAmount,
    CASE WHEN TranType IN (52, 1604)
      THEN CheckAmount
    ELSE -CheckAmount END               AS CheckAmount,
    CASE WHEN TranType IN (52, 1604)
      THEN AllAmount - CheckAmount
    ELSE -(AllAmount - CheckAmount) END AS FnCheckAmount,
    0                                   AS CheckFlag,
    create_date,
    SonID,
    Explanation,
    Cancellation,
    CheckState
  FROM T_SC_PO_StockBill
  WHERE Cancellation = 0

  UNION ALL

  SELECT
    BillNo,
    Date,
    TranType,
    id,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    0                      AS AllAmount,
    CASE WHEN TranType IN (52, 1604)
      THEN CurPayAmount
    ELSE -CurPayAmount END AS CheckAmount,
    0                      AS UnCheckAmount,
    1                      AS CheckFlag,
    create_date,
    SonID,
    Explanation,
    Cancellation,
    CheckState
  FROM T_SC_PO_StockBill
  WHERE CurPayAmount <> 0 AND Cancellation = 0

  UNION ALL

  SELECT
    BillNo,
    Date,
    TranType,
    id,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    0                      AS AllAmount,
    CASE WHEN TranType IN (52, 1604)
      THEN RebateAmount
    ELSE -RebateAmount END AS CheckAmount,
    0                      AS UnCheckAmount,
    2                      AS CheckFlag,
    create_date,
    SonID,
    Explanation,
    Cancellation,
    CheckState
  FROM T_SC_PO_StockBill
  WHERE RebateAmount <> 0 AND Cancellation = 0

  UNION ALL
  -- ----------------------------采购/销售费用直接计入应收应付
  /*
   --  【明天问经理，采购/销售费用T_IC_Expenses这块我们是不是一期没有做？】SELECT s1.BillNo,s1.Date,s1.TranType,s1.id,#ItemClassID,
          ItemID,s1.DeptID,s1.EmpID,s1.AllAmount,
          s1.AllCheckAmount,s1.AllUnCheckAmount,0 AS CheckFlag,s1.create_date,s1.SonID,s1.Explanation,s1.Cancellation,
          s1.CheckState
   FROM T_IC_Expenses s1 WHERE s1.TranType=262 AND s1.Cancellation=0 AND
     EXISTS(SELECT 1 FROM T_SYS_ProFile WHERE Category='IC' AND Key='ExpensesPO' AND Value=1)
     AND NOT EXISTS(SELECT 1 FROM T_RP_AdjustBill WHERE FClassID_SRC=262 AND FInterID_SRC=s1.id AND FTranType=251)

   UNION ALL

   SELECT s1.FBillNo,s1.FDate,s1.FTranType,s1.id,#ItemClassID,
          ItemID,s1.FDeptID,s1.FEmpID,s1.FAllAmount,
          s1.FAllCheckAmount,s1.FAllUnCheckAmount,0 AS FCheckFlag,s1.Fcreate_date,s1.FSonID,s1.FExplanation,s1.FCancellation,
          s1.FCheckState
   FROM T_IC_Expenses s1 WHERE s1.FTranType=212 AND s1.FCancellation=0 AND
     EXISTS(SELECT 1 FROM T_SYS_ProFile WHERE FCategory='IC' AND FKey='ExpensesSL' AND FValue=1)
     AND NOT EXISTS(SELECT 1 FROM T_RP_AdjustBill WHERE FClassID_SRC=212 AND FInterID_SRC=s1.id AND FTranType=251)

   UNION ALL
   SELECT FBillNo,FDate,FTranType,id,#ItemClassID,
          ItemID,FDeptID,FEmpID,0,FPayAmount,0,1 AS FCheckFlag,
          Fcreate_date,FSonID,FExplanation,FCancellation,FCheckState
   FROM T_IC_Expenses WHERE FPayAmount<>0 AND FCancellation=0

   UNION ALL*/
  -- ----------------------------采购换货单
  SELECT
    BillNo,
    Date,
    TranType,
    id,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    DiffAmount,
    0,
    0,
    0 AS CheckFlag,
    create_date,
    SonID,
    Explanation,
    Cancellation,
    CheckState
  FROM T_SC_IC_JHStockBill
  WHERE TranType = 61 AND DiffAmount <> 0 AND Cancellation = 0

  UNION ALL

  SELECT
    BillNo,
    Date,
    TranType,
    id,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    0,
    CurPayAmount,
    0,
    1 AS CheckFlag,
    create_date,
    SonID,
    Explanation,
    Cancellation,
    CheckState
  FROM T_SC_IC_JHStockBill
  WHERE CurPayAmount <> 0 AND TranType = 61 AND Cancellation = 0

  UNION ALL

  SELECT
    BillNo,
    Date,
    TranType,
    id,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    0,
    RebateAmount,
    0,
    2 AS CheckFlag,
    create_date,
    SonID,
    Explanation,
    Cancellation,
    CheckState
  FROM T_SC_IC_JHStockBill
  WHERE RebateAmount <> 0 AND TranType = 61 AND Cancellation = 0

  UNION ALL
  -- ---------------------------调账数据
  SELECT
    BillNo,
    Date,
    TranType,
    id,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    CASE WHEN TranType = 251
      THEN AllAmount
    ELSE -AllAmount END,
    CASE WHEN TranType = 251
      THEN CheckAmount
    ELSE -CheckAmount END,
    CASE WHEN TranType = 251
      THEN AllAmount - CheckAmount
    ELSE -(AllAmount - CheckAmount) END,
    0 AS CheckFlag,
    create_date,
    SonID,
    Explanation,
    Cancellation,
    CheckState
  FROM T_SC_RP_AdjustBill
  WHERE TranType IN (251, 252) AND Cancellation = 0

  UNION ALL
  /*----------------------------核销
   -- 一期没有做
   SELECT t1.FBillNo,t1.FDate,t1.FTranType,t1.id,#ItemClassID,
          ItemID,t1.FDeptID,t1.FEmpID,
          0,t2.FBillSettleAmount,0,1 AS FCheckFlag,t1.Fcreate_date,t1.FSonID,
          t1.FExplanation,FCancellation,FCheckState
   FROM T_RP_CheckBillEntry1 t2
	 LEFT JOIN T_RP_CheckBill t1 ON t1.id=t2.id
   WHERE FTranType in (258,260) AND t1.FCancellation=0

   UNION ALL
   SELECT t1.FBillNo,t1.FDate,t1.FTranType,t1.id,#ItemClassID,
          ItemID,t1.FDeptID,t1.FEmpID,0,
          t2.FBillSettleAmount,0,1 AS FCheckFlag,t1.Fcreate_date,t1.FSonID,
          t1.FExplanation,FCancellation,FCheckState
   FROM T_RP_CheckBillEntry2 t2
	  LEFT JOIN T_RP_CheckBill t1 ON t1.id=t2.id
   WHERE t1.FTranType=210 AND t1.FCancellation=0

   UNION ALL*/
  -- -----------------------付款单
  SELECT
    BillNo,
    Date,
    TranType,
    id,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    0,
    CASE WHEN TranType = 54
      THEN AllAmount
    ELSE -AllAmount END,
    0,
    1 AS CheckFlag,
    create_date,
    SonID,
    Explanation,
    Cancellation,
    CheckState
  FROM T_SC_RP_PBill
  WHERE Cancellation = 0

/*UNION ALL
   ------------------------付款冲账单/预付冲应付
   -- 一期没有做
   SELECT t1.FBillNo,t1.FDate,t1.FTranType,t1.id,#ItemClassID,
          ItemID,t1.FDeptID,t1.FEmpID,
          0,-t2.FBillSettleAmount,0,1 AS FCheckFlag,t1.Fcreate_date,t1.FSonID,t1.FExplanation,t1.FCancellation,t1.FCheckState
   FROM T_RP_CheckBillEntry1 t2
	 LEFT JOIN T_RP_CheckBill t1 ON t1.id=t2.id
   WHERE t1.FTranType in (259,316) AND t1.FCancellation=0

   UNION ALL
   SELECT t1.FBillNo,t1.FDate,t1.FTranType,t1.id,#ItemClassID,
          ItemID,t1.FDeptID,t1.FEmpID,-t2.FBillSettleAmount,
          0,0,0 AS FCheckFlag,t1.Fcreate_date,t1.FSonID,t1.FExplanation,t1.FCancellation,t1.FCheckState
   FROM T_RP_CheckBillEntry2 t2
	  LEFT JOIN T_RP_CheckBill t1 ON t1.id=t2.id
   WHERE t1.FTranType in (259,316) AND t1.FCancellation=0*/
/*
   要看一下Q7 P161 收款单-业务需求-源单数据，要补充分销、（晓峰）物流
   应收源单=应收初始化+销售出库-销售退货+应收调账+换货应收金额；
   */$$

-- ----------------------------
-- View structure for t_sc_view_depart
-- ----------------------------
DROP TABLE IF EXISTS `t_sc_view_depart`$$
-- DROP VIEW IF EXISTS `t_sc_view_depart`$$
CREATE or REPLACE VIEW `t_sc_view_depart` AS
  SELECT
    `t_s_depart`.`ID`             AS `ID`,
    `t_s_depart`.`DEPARTNAME`     AS `DEPARTNAME`,
    `t_s_depart`.`DESCRIPTION`    AS `DESCRIPTION`,
    `t_s_depart`.`PARENTDEPARTID` AS `PARENTDEPARTID`,
    `t_s_depart`.`ORG_CODE`       AS `ORG_CODE`,
    `t_s_depart`.`ORG_TYPE`       AS `ORG_TYPE`
  FROM `t_s_depart`
  WHERE find_in_set(`t_s_depart`.`ID`, `getChildList_depart`('OA管理'))$$

-- ----------------------------
-- View structure for t_sc_view_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_sc_view_notice`$$
-- DROP VIEW IF EXISTS `t_sc_view_notice`$$
CREATE or REPLACE VIEW `t_sc_view_notice` AS
  SELECT
    `r`.`user_id`      AS `reletion_user_id`,
    `n`.`id`           AS `id`,
    `n`.`create_name`  AS `create_name`,
    `n`.`create_by`    AS `create_by`,
    `n`.`create_date`  AS `create_date`,
    `n`.`update_name`  AS `update_name`,
    `n`.`update_by`    AS `update_by`,
    `n`.`update_date`  AS `update_date`,
    `n`.`Title`        AS `Title`,
    `n`.`Content`      AS `Content`,
    `n`.`type`         AS `type`,
    `n`.`userId`       AS `userId`,
    `n`.`issuance`     AS `Issuance`,
    `n`.`IssuanceDate` AS `IssuanceDate`,
    `n`.`version`      AS `Version`,
    `f`.`file_name`    AS `file_name`,
    `r`.`status`       AS `status`
  FROM ((`t_sc_notice` `n` JOIN `t_sc_notice_file` `f`) JOIN `t_sc_notice_relation` `r`)
  WHERE ((`n`.`id` = `f`.`note_id`) AND (`n`.`id` = `r`.`notice_id`))$$

-- ----------------------------
-- View structure for v_sc_bill_temp
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_bill_temp`$$
-- DROP VIEW IF EXISTS `v_sc_bill_temp`$$
CREATE or REPLACE VIEW `v_sc_bill_temp` AS
  SELECT
    `a`.`id`          AS `id`,
    `a`.`create_name` AS `create_name`,
    `a`.`create_by`   AS `create_by`,
    `a`.`create_date` AS `create_date`,
    `a`.`update_name` AS `update_name`,
    `a`.`update_by`   AS `update_by`,
    `a`.`update_date` AS `update_date`,
    `a`.`tranType`    AS `tranType`,
    `a`.`billno`      AS `billNo`,
    `a`.`date`        AS `date`,
    `a`.`stockid`     AS `stockId`,
    `stock`.`name`    AS `stockName`,
    `a`.`deptid`      AS `deptid`,
    `t`.`DEPARTNAME`  AS `DEPARTNAME`,
    `a`.`itemid`      AS `itemid`,
    `o`.`name`        AS `name`,
    `a`.`empid`       AS `empid`,
    `e`.`Name`        AS `empName`,
    `a`.`content`     AS `content`
  FROM (((((`t_sc_bill_temp` `a` LEFT JOIN `t_sc_stock` `stock` ON ((`a`.`stockid` = `stock`.`id`))) LEFT JOIN
    `t_s_base_user` `u` ON ((`a`.`billerId` = `u`.`ID`))) LEFT JOIN `t_sc_organization` `o`
      ON ((`a`.`itemid` = `o`.`id`))) LEFT JOIN `t_s_depart` `t` ON ((`a`.`deptid` = `t`.`ID`))) LEFT JOIN
    `t_sc_emp` `e` ON ((`a`.`empid` = `e`.`id`)))
  ORDER BY `a`.`date` DESC, `a`.`billno` DESC$$

-- ----------------------------
-- View structure for v_sc_checkaccount
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_checkaccount`$$
-- DROP VIEW IF EXISTS `v_sc_checkaccount`$$
CREATE or REPLACE VIEW `v_sc_checkaccount` AS SELECT
                                     `t_sc_ic_initial`.`checkstate` AS `checkstate`,
                                     `t_sc_ic_initial`.`tranType`   AS `tranType`
                                   FROM `t_sc_ic_initial`
                                   WHERE (`t_sc_ic_initial`.`checkstate` < 2)
                                   UNION SELECT
                                           `t_sc_begdata`.`checkstate` AS `checkstate`,
                                           `t_sc_begdata`.`trantype`   AS `trantype`
                                         FROM `t_sc_begdata`
                                         WHERE (`t_sc_begdata`.`checkstate` < 2)$$

-- ----------------------------
-- View structure for v_sc_checkspeedbal
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_checkspeedbal`$$
-- DROP VIEW IF EXISTS `v_sc_checkspeedbal`$$
CREATE or REPLACE  VIEW `v_sc_checkspeedbal` AS
  SELECT
    concat(
        substr(`a`.`date`, 1, 4),
        '-',
        RIGHT(`a`.`date`, 2),
        '-01'
    )                 AS `date`,
    `a`.`stockId`     AS `stockId`,
    `a`.`itemId`      AS `itemId`,
    `b`.`name`        AS `sonName`,
    `d`.`Number`      AS `itemnumber`,
    `d`.`Name`        AS `itemName`,
    `a`.`batchNo`     AS `batchNo`,
    concat(
        CONVERT(`a`.`date` USING utf8),
        `a`.`stockId`,
        `a`.`itemId`,
        `a`.`batchNo`
    )                 AS `id`,
    `f`.`name`        AS `unitName`,
    `e`.`Coefficient` AS `coefficient`,
    (
      CASE
      WHEN isnull(`e`.`Coefficient`)
        THEN
          0
      ELSE
        (
          `a`.`qty` DIV `e`.`Coefficient`
        )
      END
    )                 AS `bigQty`,
    (
      CASE
      WHEN isnull(`e`.`Coefficient`)
        THEN
          0
      ELSE
        (
          `a`.`qty` % `e`.`Coefficient`
        )
      END
    )                 AS `smallQty`,
    `a`.`qty`         AS `qty`,
    `a`.`secQty`      AS `secQty`,
    `c`.`DEPARTNAME`  AS `departmentName`
  FROM
    (
        (
            (
                (
                    (
                        `v_sc_checkspeedbal_sub` `a`
                        LEFT JOIN `t_sc_stock` `b` ON ((`a`.`stockId` = `b`.`id`))
                      )
                    LEFT JOIN `t_s_depart` `c` ON ((`b`.`sonID` = `c`.`ID`))
                  )
                LEFT JOIN `t_sc_icitem` `d` ON ((`a`.`itemId` = `d`.`id`))
              )
            LEFT JOIN `t_sc_item_price` `e` ON (
            (
              (`a`.`itemId` = `e`.`ItemID`)
              AND (`e`.`UnitType` = '0001')
            )
            )
          )
        LEFT JOIN `t_sc_measureunit` `f` ON ((`e`.`UnitID` = `f`.`id`))
    )$$


-- ----------------------------
-- View structure for v_sc_checkstage
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_checkstage`$$
-- DROP VIEW IF EXISTS `v_sc_checkstage`$$
CREATE or REPLACE  VIEW `v_sc_checkstage` AS
  SELECT
    `a`.`date`       AS `date`,
    `a`.`checkstate` AS `checkstate`,
    `a`.`tranType`   AS `tranType`,
    `a`.`billno`     AS `billno`,
    `a`.`sonId`      AS `sonId`,
    `a`.`id`         AS `id`,
    `b`.`DEPARTNAME` AS `departmentname`,
    `c`.`bill_name`  AS `bill_name`
  FROM
    ((`v_sc_checkstage_sub` `a` LEFT JOIN `t_s_depart` `b` ON ((`a`.`sonId` = `b`.`ID`))) LEFT JOIN `t_s_bill_info` `c`
        ON ((`a`.`tranType` = `c`.`bill_id`)))$$


-- ----------------------------
-- View structure for v_sc_checkunaccount
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_checkunaccount`$$
-- DROP VIEW IF EXISTS `v_sc_checkunaccount`$$
CREATE or REPLACE VIEW `v_sc_checkunaccount` AS SELECT
                                       `a`.`checkState` AS `checkstate`,
                                       `a`.`tranType`   AS `tranType`
                                     FROM `t_sc_po_stockbill` `a`
                                     WHERE (`a`.`checkState` = 2)
                                     UNION SELECT
                                             `a`.`checkState` AS `checkstate`,
                                             `a`.`tranType`   AS `trantype`
                                           FROM `t_sc_ic_jhstockbill` `a`
                                           WHERE (`a`.`checkState` = 2)
                                     UNION SELECT
                                             `a`.`checkState` AS `checkState`,
                                             `a`.`tranType`   AS `tranType`
                                           FROM `t_sc_sl_stockbill` `a`
                                           WHERE (`a`.`checkState` = 2)
                                     UNION SELECT
                                             `a`.`checkState` AS `checkState`,
                                             `a`.`tranType`   AS `tranType`
                                           FROM `t_sc_ic_xsstockbill` `a`
                                           WHERE (`a`.`checkState` = 2)
                                     UNION SELECT
                                             `a`.`checkstate` AS `checkstate`,
                                             `a`.`tranType`   AS `tranType`
                                           FROM `t_sc_rp_pbill` `a`
                                           WHERE (`a`.`checkstate` = 2)
                                     UNION SELECT
                                             `a`.`checkstate` AS `checkstate`,
                                             `a`.`tranType`   AS `tranType`
                                           FROM `t_sc_rp_rbill` `a`
                                           WHERE (`a`.`checkstate` = 2)$$

-- ----------------------------
-- View structure for v_sc_drp_order
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_drp_order`$$
-- DROP VIEW IF EXISTS `v_sc_drp_order`$$
CREATE or REPLACE VIEW `v_sc_drp_order` AS
  SELECT
    `o`.`id` AS `id`,
    oe.id as entryId,
    `o`.`create_by` AS `create_by`,
    `o`.`create_date` AS `create_date`,
    `o`.`create_name` AS `create_name`,
    `o`.`update_by` AS `update_by`,
    `o`.`update_date` AS `update_date`,
    `o`.`update_name` AS `update_name`,
    `o`.`tranType` AS `tranType`,
    `o`.`billNo` AS `billNo`,
    `o`.`date` AS `date`,
    `o`.`itemID` AS `itemID`,
    `oz`.`name` AS `itemName`,
    `o`.`contact` AS `contact`,
    `o`.`mobilePhone` AS `mobilePhone`,
    `o`.`phone` AS `phone`,
    `o`.`fax` AS `fax`,
    `o`.`address` AS `address`,
    `o`.`empID` AS `empID`,
    `e`.`Name` AS `empName`,
    `o`.`deptID` AS `deptID`,
    `d`.`name` AS `deptName`,
    `o`.`stockID` AS `oStockID`,
    `s`.`name` AS `oStockName`,
    `o`.`rebateAmount` AS `rebateAmount`,
    `o`.`allAmount` AS `allAmount`,
    `o`.`checkAmount` AS `checkAmount`,
    `o`.`checkerID` AS `checkerID`,
    `o`.`billerID` AS `billerID`,
    `b`.`REALNAME` AS `billerName`,
    `o`.`checkDate` AS `checkDate`,
    `o`.`checkState` AS `checkState`,
    `o`.`closed` AS `closed`,
    `o`.`autoFlag` AS `autoFlag`,
    `o`.`cancellation` AS `cancellation`,
    `o`.`explanation` AS `explanation`,
    `o`.`sonID` AS `sonID`,
    `dp`.`DEPARTNAME` AS `sonName`,
    `oe`.`id` AS `rOrderId`,
    `oe`.`indexNumber` AS `indexNumber`,
    `oe`.`stockID` AS `stockID`,
    `s2`.`name` AS `stockName`,
    `oe`.`itemID` AS `goodsItemID`,
    `i`.`Name` AS `goodsName`,
    `i`.`Number` AS `goodsNo`,
    `i`.`Model` AS `model`,
    `i`.`ISAssembly` AS `ISAssembly`,
    `i`.`ISKFPeriod` AS `ISKFPeriod`,
    `i`.`BatchManager` AS `BatchManager`,
    `i`.`KFPeriod` AS `KFPeriod`,
    `i`.`KFDateType` AS `KFDateType`,
    `p`.`BarCode` AS `BarCode`,
    `oe`.`unitID` AS `unitID`,
    `m`.`name` AS `unitName`,
    `oe`.`interID` AS `interID`,
    `oe`.`secUnitID` AS `secUnitID`,
    `oe`.`basicUnitID` AS `basicUnitID`,
    `oe`.`qty` AS `qty`,
    `oe`.`secCoefficient` AS `secCoefficient`,
    `oe`.`secQty` AS `secQty`,
    `oe`.`coefficient` AS `coefficient`,
    `oe`.`basicQty` AS `basicQty`,
    `oe`.`price` AS `price`,
    `oe`.`amount` AS `amount`,
    `oe`.`discountRate` AS `discountRate`,
    `oe`.`discountPrice` AS `discountPrice`,
    `oe`.`discountAmount` AS `discountAmount`,
    `oe`.`taxRate` AS `taxRate`,
    `oe`.`taxPriceEx` AS `taxPriceEx`,
    `oe`.`taxAmountEx` AS `taxAmountEx`,
    `oe`.`taxPrice` AS `taxPrice`,
    `oe`.`inTaxAmount` AS `inTaxAmount`,
    `oe`.`taxAmount` AS `taxAmount`,
    `oe`.`jhDate` AS `jhDate`,
    `oe`.`affirmQty` AS `affirmQty`,
    `oe`.`outStockQty` AS `outStockQty`,
    `oe`.`outDate` AS `outDate`,
    `oe`.`affirmDate` AS `affirmDate`,
    `oe`.`note` AS `note`,
    '款到反货' as fetchStyle
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
                                                `t_sc_drp_order` `o`
                                                LEFT JOIN `t_sc_drp_orderentry` `oe` ON ((`o`.`id` = `oe`.`interID`))
                                              )
                                            LEFT JOIN `t_sc_icitem` `i` ON ((`i`.`id` = `oe`.`itemID`))
                                          )
                                        LEFT JOIN `t_sc_item_price` `p` ON ((`oe`.`unitID` = `p`.`id`))
                                      )
                                    LEFT JOIN `t_sc_measureunit` `m` ON ((`m`.`id` = `p`.`UnitID`))
                                  )
                                LEFT JOIN `t_sc_emp` `e` ON ((`o`.`empID` = `e`.`id`))
                              )
                            LEFT JOIN `t_sc_department` `d` ON ((`o`.`deptID` = `d`.`id`))
                          )
                        LEFT JOIN `t_sc_stock` `s` ON ((`s`.`id` = `o`.`stockID`))
                      )
                    LEFT JOIN `t_s_base_user` `b` ON ((`o`.`billerID` = `b`.`ID`))
                  )
                LEFT JOIN `t_s_depart` `dp` ON ((`o`.`sonID` = `dp`.`ID`))
              )
            LEFT JOIN `t_sc_stock` `s2` ON ((`s2`.`id` = `oe`.`stockID`))
          )
        LEFT JOIN `t_sc_organization` `oz` ON ((`oz`.`id` = `o`.`itemID`))
    )
  ORDER BY
    `o`.`date` DESC,
    `o`.`billNo` DESC,
    `oe`.`indexNumber`$$

-- ----------------------------
-- View structure for v_sc_drp_rstockbill
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_drp_rstockbill`$$
-- DROP VIEW IF EXISTS `v_sc_drp_rstockbill`$$
CREATE or REPLACE VIEW `v_sc_drp_rstockbill` AS
  SELECT
    `r`.`id` AS `id`,
    rb.id as entryId,
    `r`.`tranType` AS `tranType`,
    `r`.`date` AS `date`,
    `r`.`billNo` AS `billNo`,
    `r`.`itemId` AS `itemId`,
    `o`.`name` AS `itemName`,
    `r`.`contact` AS `contact`,
    `r`.`mobilePhone` AS `mobilePhone`,
    `r`.`phone` AS `phone`,
    `r`.`fax` AS `fax`,
    `r`.`address` AS `address`,
    `r`.`empId` AS `empId`,
    `e`.`Name` AS `empName`,
    `r`.`deptId` AS `deptId`,
    `d`.`name` AS `deptName`,
    `r`.`stockId` AS `rStockId`,
    `s`.`name` AS `rStockName`,
    `r`.`freight` AS `freight`,
    `r`.`amount` AS `amount`,
    `r`.`allAmount` AS `allAmount`,
    `r`.`weight` AS `weight`,
    `r`.`classId_src` AS `r_classId_src`,
    `r`.`interId_src` AS `r_interId_src`,
    `r`.`billNo_src` AS `r_billNo_src`,
    `r`.`checkerId` AS `checkerId`,
    `r`.`checkDate` AS `checkDate`,
    `r`.`checkState` AS `checkState`,
    `r`.`cancellation` AS `cancellation`,
    `r`.`explanation` AS `explanation`,
    `r`.`billerId` AS `billerId`,
    `r`.`sonId` AS `sonId`,
    `sd`.`DEPARTNAME` AS `sonName`,
    `rb`.`id` AS `rbillId`,
    `rb`.`indexNumber` AS `indexNumber`,
    `rb`.`stockId` AS `stockId`,
    `s2`.`name` AS `stockName`,
    `rb`.`itemId` AS `goodsItemId`,
    `i`.`Name` AS `goodsName`,
    `i`.`Number` AS `Number`,
    `i`.`Model` AS `Model`,
    `i`.`Weight` AS `goodsWeight`,
    `ip`.`BarCode` AS `BarCode`,
    `rb`.`unitId` AS `unitId`,
    `m`.`name` AS `unitName`,
    `rb`.`secUnitId` AS `secUnitId`,
    `m2`.`name` AS `secUnitName`,
    `rb`.`basicUnitId` AS `basicUnitId`,
    `m3`.`name` AS `basicUnitName`,
    `rb`.`batchNo` AS `batchNo`,
    `rb`.`kfDate` AS `kfDate`,
    `rb`.`kfPeriod` AS `kfPeriod`,
    `rb`.`PeriodDate` AS `PeriodDate`,
    `rb`.`kfDateType` AS `kfDateType`,
    `rb`.`qty` AS `qty`,
    `rb`.`secCoefficient` AS `secCoefficient`,
    `rb`.`secQty` AS `secQty`,
    `rb`.`coefficient` AS `coefficient`,
    `rb`.`basicQty` AS `basicQty`,
    `rb`.`price` AS `price`,
    `rb`.`amount` AS `rbAmount`,
    `rb`.`discountRate` AS `discountRate`,
    `rb`.`discountPrice` AS `discountPrice`,
    `rb`.`discountAmount` AS `discountAmount`,
    `rb`.`taxRate` AS `taxRate`,
    `rb`.`taxPriceEx` AS `taxPriceEx`,
    `rb`.`taxAmountEx` AS `taxAmountEx`,
    `rb`.`taxPrice` AS `taxPrice`,
    `rb`.`inTaxAmount` AS `inTaxAmount`,
    `rb`.`taxAmount` AS `taxAmount`,
    `rb`.`interId_src` AS `interId_src`,
    `rb`.`billNo_src` AS `billNo_src`,
    `rb`.`id_src` AS `id_src`,
    `rb`.`interId_Order` AS `interId_Order`,
    `rb`.`billNo_Order` AS `billNo_Order`,
    `rb`.`id_Order` AS `id_Order`,
    `rb`.`note` AS `note`,
    `l`.`REALNAME` AS `billerName`
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
                                                                `t_sc_drp_rstockbill` `r`
                                                                LEFT JOIN `t_sc_organization` `o` ON ((`r`.`itemId` = `o`.`id`))
                                                              )
                                                            LEFT JOIN `t_sc_emp` `e` ON ((`e`.`id` = `r`.`empId`))
                                                          )
                                                        LEFT JOIN `t_sc_department` `d` ON ((`d`.`id` = `r`.`deptId`))
                                                      )
                                                    LEFT JOIN `t_sc_stock` `s` ON ((`s`.`id` = `r`.`stockId`))
                                                  )
                                                LEFT JOIN `t_s_depart` `sd` ON ((`sd`.`ID` = `r`.`sonId`))
                                              )
                                            LEFT JOIN `t_sc_drp_rstockbillentry` `rb` ON ((`rb`.`interId` = `r`.`id`))
                                          )
                                        LEFT JOIN `t_sc_stock` `s2` ON ((`s2`.`id` = `rb`.`stockId`))
                                      )
                                    LEFT JOIN `t_sc_icitem` `i` ON ((`i`.`id` = `rb`.`itemId`))
                                  )
                                LEFT JOIN `t_sc_item_price` `ip` ON ((`ip`.`id` = `rb`.`unitId`))
                              )
                            LEFT JOIN `t_sc_measureunit` `m` ON ((`m`.`id` = `ip`.`UnitID`))
                          )
                        LEFT JOIN `t_sc_item_price` `ip2` ON (
                        (
                          `ip2`.`id` = `rb`.`secUnitId`
                        )
                        )
                      )
                    LEFT JOIN `t_sc_measureunit` `m2` ON ((`m2`.`id` = `ip2`.`UnitID`))
                  )
                LEFT JOIN `t_sc_item_price` `ip3` ON (
                (
                  `ip3`.`id` = `rb`.`basicUnitId`
                )
                )
              )
            LEFT JOIN `t_sc_measureunit` `m3` ON ((`m3`.`id` = `ip3`.`UnitID`))
          )
        LEFT JOIN `t_s_base_user` `l` ON ((`r`.`billerId` = `l`.`ID`))
    )
  ORDER BY
    `r`.`date` DESC,
    `r`.`billNo` DESC,
    `rb`.`indexNumber`$$

-- ----------------------------
-- View structure for v_sc_drp_stockbill
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_drp_stockbill`$$
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

-- ----------------------------
-- View structure for v_sc_ic_chkinfo
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_ic_chkinfo`$$
-- DROP VIEW IF EXISTS `v_sc_ic_chkinfo`$$
CREATE or REPLACE VIEW `v_sc_ic_chkinfo` AS SELECT
                                   `a`.`id`        AS `id`,
                                   `a`.`billNo`    AS `billNo`,
                                   `a`.`date`      AS `date`,
                                   `a`.`tranType`  AS `tranType`,
                                   `b`.`bill_name` AS `billName`
                                 FROM (`t_sc_po_stockbill` `a` LEFT JOIN `t_s_bill_info` `b`
                                     ON ((`a`.`tranType` = `b`.`bill_id`)))
                                 WHERE ((`a`.`checkState` < 2) AND (`a`.`cancellation` = 0))
                                 UNION SELECT
                                         `a`.`id`        AS `id`,
                                         `a`.`billNo`    AS `billNo`,
                                         `a`.`date`      AS `date`,
                                         `a`.`tranType`  AS `tranType`,
                                         `b`.`bill_name` AS `billName`
                                       FROM (`t_sc_ic_jhstockbill` `a` LEFT JOIN `t_s_bill_info` `b`
                                           ON ((`a`.`tranType` = `b`.`bill_id`)))
                                       WHERE ((`a`.`checkState` < 2) AND (`a`.`cancellation` = 0))
                                 UNION SELECT
                                         `a`.`id`        AS `id`,
                                         `a`.`billNo`    AS `billNo`,
                                         `a`.`date`      AS `date`,
                                         `a`.`tranType`  AS `tranType`,
                                         `b`.`bill_name` AS `billName`
                                       FROM (`t_sc_sl_stockbill` `a` LEFT JOIN `t_s_bill_info` `b`
                                           ON ((`a`.`tranType` = `b`.`bill_id`)))
                                       WHERE ((`a`.`checkState` < 2) AND (`a`.`cancellation` = 0))
                                 UNION SELECT
                                         `a`.`id`        AS `id`,
                                         `a`.`billNo`    AS `billNo`,
                                         `a`.`date`      AS `date`,
                                         `a`.`tranType`  AS `tranType`,
                                         `b`.`bill_name` AS `billName`
                                       FROM (`t_sc_ic_xsstockbill` `a` LEFT JOIN `t_s_bill_info` `b`
                                           ON ((`a`.`tranType` = `b`.`bill_id`)))
                                       WHERE ((`a`.`checkState` < 2) AND (`a`.`cancellation` = 0))$$

-- ----------------------------
-- View structure for v_sc_ic_chkstockbill
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_ic_chkstockbill`$$
-- DROP VIEW IF EXISTS `v_sc_ic_chkstockbill`$$
CREATE or REPLACE VIEW `v_sc_ic_chkstockbill` AS
  SELECT
    `a`.`id`           AS `id`,
    `a`.`tranType`     AS `tranType`,
    `a`.`billNo`       AS `billNo`,
    `a`.`date`         AS `date`,
    `a`.`stockId`      AS `stockId`,
    `c`.`name`         AS `stockName`,
    `a`.`empId`        AS `empId`,
    `d`.`Name`         AS `empName`,
    `a`.`deptId`       AS `deptId`,
    `e`.`name`         AS `deptName`,
    `a`.`pdDate`       AS `pdDate`,
    `a`.`chkType`      AS `chkType`,
    `a`.`billerId`     AS `billerId`,
    `f`.`REALNAME`     AS `billerName`,
    `a`.`checkerId`    AS `checkerId`,
    `g`.`REALNAME`     AS `checkerName`,
    `a`.`checkState`   AS `checkState`,
    `a`.`checkDate`    AS `checkDate`,
    `a`.`cancellation` AS `cancellation`,
    `a`.`explanation`  AS `explanation`,
    `l`.`DEPARTNAME`   AS `sonName`,
    a.sonId,
    `b`.`id`           AS `entryId`,
    `b`.`itemId`       AS `itemId`,
    `h`.`Number`       AS `itemNo`,
    `h`.`Name`         AS `itemName`,
    `h`.`Model`        AS `model`,
    `j`.`BarCode`      AS `barCode`,
    `b`.`stockId`      AS `entryStockId`,
    `i`.`name`         AS `entryStockName`,
    `b`.`batchNo`      AS `batchNo`,
    `b`.`unitId`       AS `unitId`,
    `k`.`name`         AS `unitName`,
    `b`.`qty`          AS `qty`,
    `b`.`smallQty`     AS `smallQty`,
    `b`.`basicQty`     AS `basicQty`,
    `b`.`coefficient`  AS `coefficient`,
    `b`.`chkQty`       AS `chkQty`,
    `b`.`chkSmallQty`  AS `chkSmallQty`,
    `b`.`chkBasicQty`  AS `chkBasicQty`,
    `b`.`diffQty`      AS `diffQty`,
    `b`.`costPrice`    AS `costPrice`,
    `b`.`amount`       AS `amount`,
    `b`.`chkAmount`    AS `chkAmount`,
    `b`.`diffAmount`   AS `diffAmount`,
    `b`.`secUnitId`    AS `secUnitId`,
    `b`.`basicUnitId`  AS `basicUnitId`,
    `b`.`secQty`       AS `secQty`,
    `b`.`fid`          AS `fid`,
    `b`.`findex`       AS `findex`,
    a.ISAUTO
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
                                                `t_sc_ic_chkstockbill` `a`
                                                JOIN `t_sc_ic_chkstockbillentry` `b` ON ((`a`.`id` = `b`.`fid`))
                                              )
                                            LEFT JOIN `t_sc_stock` `c` ON ((`a`.`stockId` = `c`.`id`))
                                          )
                                        LEFT JOIN `t_sc_emp` `d` ON ((`a`.`empId` = `d`.`id`))
                                      )
                                    LEFT JOIN `t_sc_department` `e` ON ((`a`.`deptId` = `e`.`id`))
                                  )
                                LEFT JOIN `t_s_base_user` `f` ON ((`a`.`billerId` = `f`.`ID`))
                              )
                            LEFT JOIN `t_s_base_user` `g` ON ((`a`.`checkerId` = `g`.`ID`))
                          )
                        LEFT JOIN `t_sc_icitem` `h` ON ((`b`.`itemId` = `h`.`id`))
                      )
                    LEFT JOIN `t_sc_stock` `i` ON ((`b`.`stockId` = `i`.`id`))
                  )
                LEFT JOIN `t_sc_item_price` `j` ON ((`b`.`unitId` = `j`.`id`))
              )
            LEFT JOIN `t_sc_measureunit` `k` ON ((`j`.`UnitID` = `k`.`id`))
          )
        LEFT JOIN `t_s_depart` `l` ON ((`a`.`sonId` = `l`.`ID`))
    )
  ORDER BY
    `a`.`billNo` DESC,
    `a`.`date` DESC,
    `b`.`findex`$$

-- ----------------------------
-- View structure for v_sc_ic_initial
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_ic_initial`$$
-- DROP VIEW IF EXISTS `v_sc_ic_initial`$$
CREATE or REPLACE  VIEW `v_sc_ic_initial` AS
  SELECT
    `a`.`id`             AS `id`,
    `a`.`create_name`    AS `create_name`,
    `a`.`create_by`      AS `create_by`,
    `a`.`create_date`    AS `create_date`,
    `a`.`update_name`    AS `update_name`,
    `a`.`update_by`      AS `update_by`,
    `a`.`update_date`    AS `update_date`,
    `a`.`tranType`       AS `tranType`,
    `a`.`billNo`         AS `billNo`,
    `a`.`date`           AS `date`,
    `a`.`stockId`        AS `stockId`,
    `stock`.`name`       AS `stockName`,
    `b`.`indexnumber`    AS `indexnumber`,
    `c`.`Number`         AS `entryItemNo`,
    `c`.`Name`           AS `entryItemName`,
    `c`.`Model`          AS `Model`,
    `d`.`BarCode`        AS `barCode`,
    `b`.`batchNo`        AS `batchNo`,
    `b`.`unitId`         AS `unitId`,
    `f`.`name`           AS `unitName`,
    `b`.`qty`            AS `qty`,
    b.SMALLQTY           AS smallQty,
    `b`.`basicUnitId`    AS `basicUnitId`,
    `h`.`name`           AS `basicUnitName`,
    `b`.`coefficient`    AS `coefficient`,
    `b`.`basicqty`       AS `basicQty`,
    `b`.`secUnitId`      AS `secUnitId`,
    `k`.`name`           AS `secUnitName`,
    `b`.`seccoefficient` AS `secCoefficient`,
    `b`.`secqty`         AS `secQty`,
    `b`.`costprice`      AS `costPrice`,
    `b`.`costamount`     AS `costAmount`,
    `b`.`kfDate`         AS `kfDate`,
    `b`.`kfperiod`       AS `kfPeriod`,
    `b`.`kfDateType`     AS `kfDateTypeInfo`,
    `b`.`periodDate`     AS `periodDate`,
    `b`.`note`           AS `entryexplanation`,
    `a`.`explanation`    AS `explanation`,
    `u`.`REALNAME`       AS `billerID`,
    `a`.`checkerId`      AS `checkerId`,
    `a`.`checkDate`      AS `checkDate`,
    `a`.`checkstate`     AS `checkState`,
    `a`.`cancellation`   AS `cancellation`,
    `a`.`sonId`          AS `sonId`,
    `t`.`DEPARTNAME`     AS `sonName`,
    `a`.`version`        AS `version`,
    `b`.`id`             AS `entryId`,
    `b`.`itemId`         AS `entryItemId`,
    `b`.`stockId`        AS `entryStockId`,
    concat(
        `b`.`kfperiod`,
        `type`.`TYPENAME`
    )                    AS `kfDateType`,
    `b`.`note`           AS `note`,
    `i`.`name`           AS `entryStockName`,
    `d`.`CGLimitPrice`   AS `CGLimitPrice`,
    `q`.`XSLimitPrice`   AS `XSLimitPrice`,
    `c`.`ISKFPeriod`     AS `ISKFPeriod`,
    `c`.`BatchManager`   AS `BatchManager`,
    0                    AS `isStock`,
    (
      CASE
      WHEN (
        (
          `inv`.`basicQty` - `b`.`basicqty`
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
                                                                `t_sc_ic_initial` `a`
                                                                JOIN `t_sc_ic_initialentry` `b`
                                                                  ON ((`a`.`id` = `b`.`fid`))
                                                              )
                                                            LEFT JOIN `t_sc_icitem` `c` ON ((`b`.`itemId` = `c`.`id`))
                                                          )
                                                        LEFT JOIN `t_sc_item_price` `d` ON ((`b`.`unitId` = `d`.`id`))
                                                      )
                                                    LEFT JOIN `t_sc_measureunit` `f` ON ((`d`.`UnitID` = `f`.`id`))
                                                  )
                                                LEFT JOIN `t_sc_item_price` `g` ON (
                                                (`b`.`basicUnitId` = `g`.`id`)
                                                )
                                              )
                                            LEFT JOIN `t_sc_measureunit` `h` ON ((`g`.`UnitID` = `h`.`id`))
                                          )
                                        LEFT JOIN `t_sc_item_price` `j` ON ((`b`.`secUnitId` = `j`.`id`))
                                      )
                                    LEFT JOIN `t_sc_measureunit` `k` ON ((`j`.`UnitID` = `k`.`id`))
                                  )
                                LEFT JOIN `t_sc_stock` `i` ON ((`b`.`stockId` = `i`.`id`))
                              )
                            LEFT JOIN `t_s_base_user` `u` ON ((`a`.`billerId` = `u`.`ID`))
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
  ORDER BY
    `a`.`date` DESC,
    `a`.`billNo` DESC,
    `b`.`indexnumber`$$

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
	)$$

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
	`a`.`batchNo` DESC$$

-- ----------------------------
-- View structure for v_sc_ic_jhstockbill
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_ic_jhstockbill`$$
-- DROP VIEW IF EXISTS `v_sc_ic_jhstockbill`$$
CREATE or REPLACE VIEW `v_sc_ic_jhstockbill` AS
  SELECT
    `a`.`id`             AS `id`,
    `a`.`tranType`       AS `tranType`,
    `a`.`billNo`         AS `billNo`,
    `a`.`date`           AS `date`,
    `g`.`Name`           AS `itemId`,
    `h`.`Name`           AS `empId`,
    `i`.`name`           AS `deptId`,
    `a`.`rebateAmount`   AS `rebateAmount`,
    `a`.`diffAmount`     AS `diffAmount`,
    `a`.`curPayAmount`   AS `curPayAmount`,
    `j`.`name`           AS `accountId`,
    `a`.`allAmount`      AS `allAmount`,
    `a`.`checkAmount`    AS `checkAmount`,
    `a`.`classID_SRC`    AS `classID_SRC`,
    `a`.`id_SRC`         AS `id_SRC`,
    `a`.`billNo_SRC`     AS `billNo_SRC`,
    `a`.`checkerId`      AS `checkerId`,
    `u`.`REALNAME`       AS `billerId`,
    `a`.`checkDate`      AS `checkDate`,
    `a`.`checkState`     AS `checkState`,
    `a`.`cancellation`   AS `cancellation`,
    `a`.`explanation`    AS `explanation`,
    `t`.`DEPARTNAME`     AS `sonName`,
    `a`.`sonId`          AS `sonId`,
    `a`.`version`        AS `version`,
    `b`.`id`             AS `entryId`,
    `c`.`Number`         AS `itemNo`,
    `c`.`Name`           AS `itemName`,
    `c`.`Model`          AS `model`,
    `d`.`BarCode`        AS `barCode`,
    `f`.`name`           AS `stockId`,
    `b`.`batchNo`        AS `batchNo`,
    `e`.`name`           AS `unitId`,
    `b`.`qty`            AS `qty`,
    `b`.`basicUnitId`    AS `basicUnitId`,
    `b`.`coefficient`    AS `coefficient`,
    `b`.`basicQty`       AS `basicQty`,
    `b`.`secUnitId`      AS `secUnitId`,
    `b`.`secCoefficient` AS `secCoefficient`,
    `b`.`secQty`         AS `secQty`,
    `b`.`taxAmountEx`    AS `taxAmountEx`,
    `b`.`discountRate`   AS `discountRate`,
    `b`.`taxPrice`       AS `taxPrice`,
    `b`.`taxAmount`      AS `taxAmount`,
    `b`.`taxRate`        AS `taxRate`,
    `b`.`price`          AS `price`,
    `b`.`amount`         AS `amount`,
    `b`.`discountPrice`  AS `discountPrice`,
    `b`.`discountAmount` AS `discountAmount`,
    `b`.`kfDate`         AS `kfDate`,
    `b`.`kfPeriod`       AS `kfPeriod`,
    concat(
        `b`.`kfPeriod`,
        `type`.`TYPENAME`
    )                    AS `kfDateType`,
    `b`.`periodDate`     AS `periodDate`,
    `b`.`note`           AS `note`,
    `b`.`fid`            AS `fid`,
    `b`.`findex`         AS `findex`,
    `b`.`taxPriceEx`     AS `taxPriceEx`,
    `b`.`inTaxAmount`    AS `inTaxAmount`,
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
                                                    `t_sc_ic_jhstockbill` `a`
                                                    LEFT JOIN `t_sc_ic_jhstockbillentry2` `b`
                                                      ON ((`a`.`id` = `b`.`fid`))
                                                  )
                                                LEFT JOIN `t_sc_icitem` `c` ON ((`b`.`itemId` = `c`.`id`))
                                              )
                                            LEFT JOIN `t_sc_item_price` `d` ON ((`b`.`unitId` = `d`.`id`))
                                          )
                                        LEFT JOIN `t_sc_measureunit` `e` ON ((`d`.`UnitID` = `e`.`id`))
                                      )
                                    LEFT JOIN `t_sc_stock` `f` ON ((`b`.`stockId` = `f`.`id`))
                                  )
                                LEFT JOIN `t_sc_supplier` `g` ON ((`a`.`itemId` = `g`.`id`))
                              )
                            LEFT JOIN `t_sc_emp` `h` ON ((`a`.`empId` = `h`.`id`))
                          )
                        LEFT JOIN `t_sc_department` `i` ON ((`a`.`deptId` = `i`.`id`))
                      )
                    LEFT JOIN `t_sc_settleacct` `j` ON ((`a`.`accountId` = `j`.`id`))
                  )
                LEFT JOIN `t_s_base_user` `u` ON ((`a`.`billerId` = `u`.`ID`))
              )
            LEFT JOIN `t_s_depart` `t` ON ((`a`.`sonId` = `t`.`ID`))
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
    LEFT JOIN t_s_base_user che ON che.id = a.CHECKERID
  ORDER BY
    `a`.`date` DESC,
    `a`.`billNo` DESC,
    `b`.`findex`$$

-- ----------------------------
-- View structure for v_sc_ic_speedbal
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_ic_speedbal`$$
-- DROP VIEW IF EXISTS `v_sc_ic_speedbal`$$
CREATE or REPLACE VIEW `v_sc_ic_speedbal` AS
  SELECT
    `a`.`id`             AS `id`,
    `a`.`create_name`    AS `create_name`,
    `a`.`create_by`      AS `create_by`,
    `a`.`create_date`    AS `create_date`,
    `a`.`update_name`    AS `update_name`,
    `a`.`update_by`      AS `update_by`,
    `a`.`update_date`    AS `update_date`,
    `a`.`date`           AS `date`,
    `f`.`bill_name`      AS `tranType`,
    `a`.`billId`         AS `billId`,
    `a`.`billEntryId`    AS `billEntryId`,
    `a`.`billCreateTime` AS `billCreateTime`,
    `a`.`itemId`         AS `itemId`,
    `a`.`stockId`        AS `stockId`,
    `a`.`batchNo`        AS `batchNo`,
    `a`.`qty`            AS `qty`,
    `a`.`secQty`         AS `secQty`,
    `a`.`price`          AS `price`,
    `a`.`amount`         AS `amount`,
    `a`.`ePrice`         AS `ePrice`,
    `a`.`eAmount`        AS `eAmount`,
    `a`.`diffAmount`     AS `diffAmount`,
    `g`.`bill_name`      AS `blid_SRC`,
    `a`.`flag`           AS `flag`,
    `a`.`status`         AS `status`,
    `a`.`negFlag`        AS `negFlag`,
    `b`.`Name`           AS `itemName`,
    `b`.`Number`         AS `itemNo`,
    `b`.`Model`          AS `model`,
    `c`.`BarCode`        AS `BarCode`,
    `d`.`name`           AS `unitName`,
    `e`.`name`           AS `stockName`
  FROM ((((((`t_sc_ic_speedbal` `a` LEFT JOIN `t_sc_icitem` `b` ON ((`a`.`itemId` = `b`.`id`))) LEFT JOIN
    `t_sc_item_price` `c` ON (((`b`.`id` = `c`.`ItemID`) AND (`c`.`UnitType` = '0001')))) LEFT JOIN
    `t_sc_measureunit` `d` ON ((`c`.`UnitID` = `d`.`id`))) LEFT JOIN `t_sc_stock` `e`
      ON ((`a`.`stockId` = `e`.`id`))) LEFT JOIN `t_s_bill_info` `f` ON ((`a`.`tranType` = `f`.`bill_id`))) LEFT JOIN
    `t_s_bill_info` `g` ON ((`a`.`blid_SRC` = `g`.`bill_id`)))
  ORDER BY `a`.`create_date` DESC$$

-- ----------------------------
-- View structure for v_sc_ic_speedbal_group
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_ic_speedbal_group`$$
-- DROP VIEW IF EXISTS `v_sc_ic_speedbal_group`$$
CREATE or REPLACE VIEW `v_sc_ic_speedbal_group` AS
  SELECT
    `a`.`date`                                                                  AS `date`,
    `a`.`itemId`                                                                AS `id`,
    `a`.`itemId`                                                                AS `itemId`,
    `a`.`stockId`                                                               AS `stockId`,
    `a`.`batchNo`                                                               AS `batchNo`,
    sum((`a`.`qty` *
         `a`.`flag`))                                                           AS `qty`,
    sum((`a`.`secQty` *
         `a`.`flag`))                                                           AS `secQty`,
    format((sum((`a`.`amount` * `a`.`flag`)) / sum((`a`.`qty` * `a`.`flag`))),
           2)                                                                   AS `price`,
    format(sum((`a`.`amount` * `a`.`flag`)),
           2)                                                                   AS `amount`,
    (SELECT `t_sc_ic_inventory`.`id`
     FROM `t_sc_ic_inventory`
     WHERE ((`t_sc_ic_inventory`.`itemId` = `a`.`itemId`) AND (`t_sc_ic_inventory`.`stockId` =
                                                               `a`.`stockId`))) AS `invId`,
    (SELECT `t_sc_ic_inventory_batchno`.`id`
     FROM `t_sc_ic_inventory_batchno`
     WHERE (
       (`t_sc_ic_inventory_batchno`.`itemId` = `a`.`itemId`) AND (`t_sc_ic_inventory_batchno`.`stockId` = `a`.`stockId`)
       AND (`t_sc_ic_inventory_batchno`.`batchNo` = `a`.`batchNo`) AND (`t_sc_ic_inventory_batchno`.`isCheck` =
                                                                        0)))    AS `invBatchId`
  FROM `t_sc_ic_speedbal` `a`
  GROUP BY `a`.`date`, `a`.`itemId`, `a`.`stockId`, `a`.`batchNo`$$

-- ----------------------------
-- View structure for v_sc_ic_xsstockbill
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_ic_xsstockbill`$$
-- DROP VIEW IF EXISTS `v_sc_ic_xsstockbill`$$
CREATE or REPLACE VIEW `v_sc_ic_xsstockbill` AS
  SELECT
    `a`.`id`                                  AS `id`,
    `a`.`create_name`                         AS `create_name`,
    `a`.`create_by`                           AS `create_by`,
    `a`.`create_date`                         AS `create_date`,
    `a`.`update_name`                         AS `update_name`,
    `a`.`update_by`                           AS `update_by`,
    `a`.`update_date`                         AS `update_date`,
    `a`.`tranType`                            AS `tranType`,
    `a`.`billNo`                              AS `billNo`,
    `a`.`date`                                AS `date`,
    `a`.`itemId`                              AS `itemId`,
    `a`.`empId`                               AS `empId`,
    `a`.`deptId`                              AS `deptId`,
    `a`.`rebateAmount`                        AS `rebateAmount`,
    `a`.`curPayAmount`                        AS `curPayAmount`,
    `a`.`accountID`                           AS `accountID`,
    `a`.`freight`                             AS `freight`,
    `a`.`weight`                              AS `weight`,
    `a`.`allAmount`                           AS `allAmount`,
    `a`.`classID_SRC`                         AS `classID_SRC`,
    `a`.`id_SRC`                              AS `id_SRC`,
    `a`.`billNo_SRC`                          AS `billNo_SRC`,
    `a`.`billerId`                            AS `billerId`,
    `a`.`checkerId`                           AS `checkerId`,
    `a`.`checkDate`                           AS `checkDate`,
    `a`.`checkState`                          AS `checkState`,
    `a`.`cancellation`                        AS `cancellation`,
    `a`.`explanation`                         AS `explanation`,
    `a`.`sonId`                               AS `sonId`,
    `a`.`version`                             AS `version`,
    `a`.`checkAmount`                         AS `checkAmount`,
    `a`.`diffAmount`                          AS `diffAmount`,
    `b`.`id`                                  AS `entryId`,
    `b`.`fid`                                 AS `fid`,
    `b`.`findex`                              AS `findex`,
    `b`.`itemId`                              AS `entryItemId`,
    `b`.`stockId`                             AS `stockId`,
    `b`.`batchNo`                             AS `batchNo`,
    `b`.`unitId`                              AS `unitId`,
    `b`.`qty`                                 AS `qty`,
    `b`.`secUnitId`                           AS `secUnitId`,
    `b`.`basicUnitId`                         AS `basicUnitId`,
    `b`.`coefficient`                         AS `coefficient`,
    `b`.`basicQty`                            AS `basicQty`,
    `b`.`secCoefficient`                      AS `secCoefficient`,
    `b`.`secQty`                              AS `secQty`,
    `b`.`taxPriceEx`                          AS `taxPriceEx`,
    `b`.`taxAmountEx`                         AS `taxAmountEx`,
    `b`.`discountRate`                        AS `discountRate`,
    `b`.`taxPrice`                            AS `taxPrice`,
    `b`.`inTaxAmount`                         AS `inTaxAmount`,
    `b`.`taxRate`                             AS `taxRate`,
    `b`.`price`                               AS `price`,
    `b`.`amount`                              AS `amount`,
    `b`.`discountPrice`                       AS `discountPrice`,
    `b`.`discountAmount`                      AS `discountAmount`,
    `b`.`taxAmount`                           AS `taxAmount`,
    `b`.`kfDate`                              AS `kfDate`,
    `b`.`kfPeriod`                            AS `kfPeriod`,
    `b`.`kfDateType`                          AS `kfDateTypeInfo`,
    concat(`b`.`kfPeriod`, `type`.`TYPENAME`) AS `kfDateType`,
    `b`.`periodDate`                          AS `periodDate`,
    `b`.`note`                                AS `note`,
    `b`.`costPrice`                           AS `costPrice`,
    `b`.`costAmount`                          AS `costAmount`,
    `c`.`name`                                AS `itemName`,
    `d`.`name`                                AS `deptName`,
    `e`.`Name`                                AS `empName`,
    `f`.`Name`                                AS `entryItemName`,
    `f`.`Model`                               AS `Model`,
    `f`.`Number`                              AS `entryItemNo`,
    `g`.`BarCode`                             AS `BarCode`,
    `h`.`name`                                AS `unitName`,
    `i`.`name`                                AS `stockName`,
    `j`.`REALNAME`                            AS `billerName`,
    `k`.`name`                                AS `accountName`,
    `l`.`DEPARTNAME`                          AS `sonName`,
    `m`.`REALNAME`                            AS `checkerName`
  FROM (((((((((((
      ((`t_sc_ic_xsstockbill` `a` JOIN `t_sc_ic_xsstockbillentry2` `b` ON ((`a`.`id` = `b`.`fid`))) LEFT JOIN
        `t_sc_organization` `c` ON ((`a`.`itemId` = `c`.`id`))) LEFT JOIN `t_sc_department` `d`
        ON ((`a`.`deptId` = `d`.`id`))) LEFT JOIN `t_sc_emp` `e` ON ((`a`.`empId` = `e`.`id`))) LEFT JOIN
    `t_sc_icitem` `f` ON ((`b`.`itemId` = `f`.`id`))) LEFT JOIN `t_sc_item_price` `g`
      ON ((`b`.`unitId` = `g`.`id`))) LEFT JOIN `t_sc_measureunit` `h` ON ((`g`.`UnitID` = `h`.`id`))) LEFT JOIN
    `t_sc_stock` `i` ON ((`b`.`stockId` = `i`.`id`))) LEFT JOIN `t_s_base_user` `j`
      ON ((`a`.`billerId` = `j`.`ID`))) LEFT JOIN `t_sc_settleacct` `k` ON ((`a`.`accountID` = `k`.`id`))) LEFT JOIN
    `t_s_type` `type`
      ON (((`type`.`TYPECODE` = `b`.`kfDateType`) AND (`type`.`TYPEGROUPID` = (SELECT `t_s_typegroup`.`ID`
                                                                               FROM `t_s_typegroup`
                                                                               WHERE (`t_s_typegroup`.`TYPEGROUPCODE` =
                                                                                      'SC_DURA_DATE_TYPE')))))) LEFT JOIN
    `t_s_depart` `l` ON ((`a`.`sonId` = `l`.`ID`))) LEFT JOIN `t_s_base_user` `m` ON ((`a`.`checkerId` = `m`.`ID`)))
  ORDER BY `a`.`date` DESC, `a`.`billNo` DESC, `b`.`findex`$$

-- ----------------------------
-- View structure for v_sc_order
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_order`$$
-- DROP VIEW IF EXISTS `v_sc_order`$$
CREATE or REPLACE VIEW `v_sc_order` AS
  SELECT
    `b`.`id`                          AS `entryId`,
    `a`.`id`                          AS `id`,
    `a`.`BillNo`                      AS `BillNo`,
    `a`.`Date`                        AS `Date`,
    `a`.`ItemID`                      AS `aitemID`,
    `a`.`EmpID`                       AS `EmpID`,
    `a`.`DeptID`                      AS `DeptID`,
    `a`.`FetchStyle`                  AS `FetchStyle`,
    `a`.`allamount`                   AS `allamount`,
    `a`.`preamount`                   AS `preamount`,
    `a`.`rebateamount`                AS `rebateamount`,
    `a`.`freight`                     AS `freight`,
    `a`.`weight`                      AS `aweight`,
    `a`.`mall`                        AS `mall`,
    `a`.`mallorderid`                 AS `mallorderid`,
    `a`.`Explanation`                 AS `Explanation`,
    `a`.`Contact`                     AS `Contact`,
    `a`.`MobilePhone`                 AS `MobilePhone`,
    `a`.`Phone`                       AS `Phone`,
    `a`.`Address`                     AS `Address`,
    `a`.`checkamount`                 AS `checkamount`,
    `a`.`BillerID`                    AS `BillerID`,
    `a`.`CheckerID`                   AS `CheckerID`,
    `a`.`CheckDate`                   AS `CheckDate`,
    `a`.`CheckState`                  AS `CheckState`,
    `a`.`Closed`                      AS `Closed`,
    `a`.`AutoFlag`                    AS `AutoFlag`,
    `a`.`Cancellation`                AS `Cancellation`,
    `a`.`SonID`                       AS `SonID`,
    `b`.`IndexNumber`                 AS `IndexNumber`,
    (SELECT `c`.`Number`
     FROM `t_sc_icitem` `c`
     WHERE (`c`.`id` = `b`.`ItemID`)) AS `itemNumber`,
    (SELECT `c`.`Name`
     FROM `t_sc_icitem` `c`
     WHERE (`c`.`id` = `b`.`ItemID`)) AS `itemName`,
    (SELECT `c`.`Model`
     FROM `t_sc_icitem` `c`
     WHERE (`c`.`id` = `b`.`ItemID`)) AS `model`,
    (SELECT `d`.`BarCode`
     FROM `t_sc_item_price` `d`
     WHERE (`d`.`id` = `b`.`UnitID`)) AS `barCode`,
    `b`.`StockID`                     AS `StockID`,
    (SELECT `d`.`UnitID`
     FROM `t_sc_item_price` `d`
     WHERE (`d`.`id` = `b`.`UnitID`)) AS `UnitID`,
    `b`.`Qty`                         AS `Qty`,
    `b`.`BasicUnitID`                 AS `BasicUnitID`,
    `b`.`Coefficient`                 AS `Coefficient`,
    `b`.`BasicQty`                    AS `BasicQty`,
    `b`.`SecUnitID`                   AS `SecUnitID`,
    `b`.`SecCoefficient`              AS `SecCoefficient`,
    `b`.`SecQty`                      AS `SecQty`,
    `b`.`TaxPriceEx`                  AS `TaxPriceEx`,
    `b`.`TaxAmountEx`                 AS `TaxAmountEx`,
    `b`.`DiscountRate`                AS `DiscountRate`,
    `b`.`TaxPrice`                    AS `TaxPrice`,
    `b`.`InTaxAmount`                 AS `InTaxAmount`,
    `b`.`MallPrice`                   AS `MallPrice`,
    `b`.`MallAmount`                  AS `MallAmount`,
    `b`.`Weight`                      AS `bweight`,
    `b`.`TaxRate`                     AS `TaxRate`,
    `b`.`Price`                       AS `Price`,
    `b`.`Amount`                      AS `Amount`,
    `b`.`DiscountPrice`               AS `DiscountPrice`,
    `b`.`DiscountAmount`              AS `DiscountAmount`,
    `b`.`TaxAmount`                   AS `TaxAmount`,
    `b`.`FetchDate`                   AS `FetchDate`,
    `b`.`SalesID`                     AS `SalesID`,
    `b`.`FreeGifts`                   AS `FreeGifts`,
    `b`.`StockQty`                    AS `StockQty`,
    `b`.`BillNo_SRC`                  AS `BillNo_SRC`,
    `b`.`ClassID_SRC`                 AS `ClassID_SRC`,
    `b`.`Note`                        AS `Note`,
    `b`.`ItemID`                      AS `itemid`,
    (CASE WHEN (`b`.`BasicQty` <= ifnull(`b`.`StockQty`, 0))
      THEN 1
     ELSE 0 END)                      AS `isStock`
  FROM (`t_sc_order` `a` JOIN `t_sc_order_entry` `b`)
  WHERE (`a`.`id` = `b`.`OrderId`)
  ORDER BY `a`.`Date` DESC, `a`.`id` DESC, `a`.`BillNo` DESC, `b`.`IndexNumber`$$

-- ----------------------------
-- View structure for v_sc_order_main
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_order_main`$$
-- DROP VIEW IF EXISTS `v_sc_order_main`$$
CREATE or REPLACE VIEW `v_sc_order_main` AS
  SELECT
    `a`.`id`           AS `id`,
    `a`.`create_name`  AS `create_name`,
    `a`.`create_by`    AS `create_by`,
    `a`.`create_date`  AS `create_date`,
    `a`.`update_name`  AS `update_name`,
    `a`.`update_by`    AS `update_by`,
    `a`.`update_date`  AS `update_date`,
    `a`.`version`      AS `version`,
    `a`.`trantype`     AS `trantype`,
    `a`.`Date`         AS `Date`,
    `a`.`BillNo`       AS `BillNo`,
    `a`.`ItemID`       AS `ItemID`,
    `a`.`Contact`      AS `Contact`,
    `a`.`MobilePhone`  AS `MobilePhone`,
    `a`.`Phone`        AS `Phone`,
    `a`.`Fax`          AS `Fax`,
    `a`.`Address`      AS `Address`,
    `a`.`EmpID`        AS `EmpID`,
    `a`.`DeptID`       AS `DeptID`,
    `a`.`StockID`      AS `StockID`,
    `a`.`FetchStyle`   AS `FetchStyle`,
    `a`.`preamount`    AS `preamount`,
    `a`.`mall`         AS `mall`,
    `a`.`mallorderid`  AS `mallorderid`,
    `a`.`rebateamount` AS `rebateamount`,
    `a`.`freight`      AS `freight`,
    `a`.`weight`       AS `weight`,
    `a`.`amount`       AS `amount`,
    `a`.`allamount`    AS `allamount`,
    `a`.`checkamount`  AS `checkamount`,
    `a`.`ClassID_SRC`  AS `ClassID_SRC`,
    `a`.`InterID_SRC`  AS `InterID_SRC`,
    `a`.`BillNo_SRC`   AS `BillNo_SRC`,
    `a`.`CheckerID`    AS `CheckerID`,
    `a`.`BillerID`     AS `BillerID`,
    `a`.`CheckDate`    AS `CheckDate`,
    `a`.`CheckState`   AS `CheckState`,
    `a`.`Closed`       AS `Closed`,
    `a`.`AutoFlag`     AS `AutoFlag`,
    `a`.`Cancellation` AS `Cancellation`,
    `a`.`Explanation`  AS `Explanation`,
    `a`.`SonID`        AS `SonID`,
    `b`.`name`         AS `itemName`,
    `c`.`Name`         AS `empName`,
    `d`.`name`         AS `deptName`,
    `e`.`DEPARTNAME`   AS `sonName`
  FROM ((((`t_sc_order` `a` LEFT JOIN `t_sc_organization` `b` ON ((`a`.`ItemID` = `b`.`id`))) LEFT JOIN `t_sc_emp` `c`
      ON ((`a`.`EmpID` = `c`.`id`))) LEFT JOIN `t_sc_department` `d` ON ((`a`.`DeptID` = `d`.`id`))) LEFT JOIN
    `t_s_depart` `e` ON ((`a`.`SonID` = `e`.`ID`)))
  WHERE (`a`.`checkamount` < `a`.`allamount`)
  ORDER BY `a`.`BillNo` DESC$$

-- ----------------------------
-- View structure for v_sc_order_select
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_order_select`$$
-- DROP VIEW IF EXISTS `v_sc_order_select`$$
CREATE or REPLACE VIEW `v_sc_order_select` AS
  SELECT
    `b`.`id`             AS `entryId`,
    `a`.`id`             AS `id`,
    `a`.`BillNo`         AS `BillNo`,
    `a`.`Date`           AS `Date`,
    `a`.`ItemID`         AS `aitemID`,
    `o`.`name`           AS `aitemName`,
    `o`.`iscreditmgr`    AS `iscreditmgr`,
    `o`.`creditamount`   AS `creditamount`,
    `a`.`EmpID`          AS `EmpID`,
    (
      SELECT
    `t_sc_emp`.`Name`
      FROM
        `t_sc_emp`
      WHERE
        (
          `t_sc_emp`.`id`
          =
          `a`.`EmpID`
        )
    )                    AS `empName`,
    `a`.`DeptID`         AS `DeptID`,
    (
      SELECT
    `t_sc_department`.`name`
      FROM
        `t_sc_department`
      WHERE
        (
          `t_sc_department`.`id`
          =
          `a`.`DeptID`
        )
    )                    AS `deptName`,
    `a`.`FetchStyle`     AS `FetchStyle`,
    `a`.`allamount`      AS `allamount`,
    `a`.`preamount`      AS `preamount`,
    `a`.`rebateamount`   AS `rebateamount`,
    `a`.`freight`        AS `freight`,
    `a`.`weight`         AS `aweight`,
    `a`.`mall`           AS `mall`,
    `a`.`mallorderid`    AS `mallorderid`,
    `a`.`Explanation`    AS `Explanation`,
    `a`.`Contact`        AS `Contact`,
    `a`.`MobilePhone`    AS `MobilePhone`,
    `a`.`Phone`          AS `Phone`,
    `a`.`Address`        AS `Address`,
    `a`.`checkamount`    AS `checkamount`,
    `a`.`BillerID`       AS `BillerID`,
    `a`.`CheckerID`      AS `CheckerID`,
    `a`.`CheckDate`      AS `CheckDate`,
    `a`.`CheckState`     AS `CheckState`,
    `a`.`Closed`         AS `Closed`,
    `a`.`AutoFlag`       AS `AutoFlag`,
    `a`.`Cancellation`   AS `Cancellation`,
    `a`.`SonID`          AS `SonID`,
    `a`.`StockID`        AS `astockId`,
    `a`.`trantype`       AS `trantype`,
    (
      SELECT
    `t_sc_stock`.`name`
      FROM
        `t_sc_stock`
      WHERE
        (
          `t_sc_stock`.`id`
          =
          `a`.`StockID`
        )
    )                    AS `astockName`,
    `b`.`IndexNumber`    AS `IndexNumber`,
    `b`.`ItemID`         AS `itemId`,
    `c`.`Number`         AS `itemNumber`,
    `c`.`Name`           AS `itemName`,
    `c`.`Model`          AS `model`,
    `d`.`BarCode`        AS `BarCode`,
    `b`.`StockID`        AS `StockID`,
    `f`.`name`           AS `stockName`,
    `b`.`UnitID`         AS `UnitID`,
    `e`.`name`           AS `unitName`,
    `b`.`Qty`            AS `Qty`,
    `b`.`BasicUnitID`    AS `BasicUnitID`,
    `b`.`Coefficient`    AS `Coefficient`,
    `b`.`BasicQty`       AS `BasicQty`,
    `b`.`SecUnitID`      AS `SecUnitID`,
    `b`.`SecCoefficient` AS `SecCoefficient`,
    `b`.`SecQty`         AS `SecQty`,
    `b`.`TaxPriceEx`     AS `TaxPriceEx`,
    `b`.`TaxAmountEx`    AS `TaxAmountEx`,
    `b`.`DiscountRate`   AS `DiscountRate`,
    `b`.`TaxPrice`       AS `TaxPrice`,
    `b`.`InTaxAmount`    AS `InTaxAmount`,
    `b`.`MallPrice`      AS `MallPrice`,
    `b`.`MallAmount`     AS `MallAmount`,
    `b`.`Weight`         AS `weight`,
    `b`.`TaxRate`        AS `TaxRate`,
    `b`.`Price`          AS `Price`,
    `b`.`Amount`         AS `Amount`,
    `b`.`DiscountPrice`  AS `DiscountPrice`,
    `b`.`DiscountAmount` AS `DiscountAmount`,
    `b`.`TaxAmount`      AS `TaxAmount`,
    `b`.`FetchDate`      AS `FetchDate`,
    `b`.`SalesID`        AS `SalesID`,
    `b`.`isFreeGift`     AS `isFreeGift`,
    (
      CASE `b`.`isFreeGift`
      WHEN 0
        THEN
          '调价政策'
      WHEN 1
        THEN
          '买一增一'
      WHEN 2
        THEN
          ''
      END
    )                    AS `salesName`,
    `b`.`FreeGifts`      AS `FreeGifts`,
    `b`.`StockQty`       AS `StockQty`,
    `b`.`BillNo_SRC`     AS `billnoSrc`,
    `b`.`ClassID_SRC`    AS `classidSRC`,
    `b`.`Note`           AS `Note`,
    `c`.`ISKFPeriod`     AS `ISKFPeriod`,
    `c`.`BatchManager`   AS `BatchManager`,
    `c`.`Weight`         AS `itemweight`,
    `c`.`KFDateType`     AS `KFDateType`,
    `c`.`KFPeriod`       AS `KFPeriod`,
    `biller`.`REALNAME`  AS `billerName`,
    `son`.`DEPARTNAME`   AS `sonName`,
    ifnull(`g`.`qty`,
           0)            AS `invQty`,
    che.REALNAME         AS checkerName,
    (
      CASE
      WHEN (
        (
          `b`.`QTY` >
          ifnull(
              `b`.`StockQty`,
              0)
        )
        AND (
          ifnull(
              `a`.`preamount`,
              0) <=
          ifnull(
              `a`.`checkamount`,
              0)
        )
      )
        THEN
          0
      ELSE
        1
      END
    )                    AS `isStock`
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
                                        `t_sc_order` `a`
                                        JOIN `t_sc_order_entry` `b` ON ((`a`.`id` = `b`.`OrderId`))
                                      )
                                    LEFT JOIN `t_sc_icitem` `c` ON ((`b`.`ItemID` = `c`.`id`))
                                  )
                                LEFT JOIN `t_sc_item_price` `d` ON ((`b`.`UnitID` = `d`.`id`))
                              )
                            LEFT JOIN `t_sc_measureunit` `e` ON ((`d`.`UnitID` = `e`.`id`))
                          )
                        LEFT JOIN `t_sc_stock` `f` ON ((`b`.`StockID` = `f`.`id`))
                      )
                    LEFT JOIN `t_sc_ic_inventory` `g` ON (
                    (
                      (`b`.`ItemID` = `g`.`itemId`)
                      AND (
                        `b`.`StockID` = `g`.`stockId`
                      )
                    )
                    )
                  )
                LEFT JOIN `t_s_base_user` `biller` ON (
                (
                  `a`.`BillerID` = `biller`.`ID`
                )
                )
              )
            LEFT JOIN `t_sc_organization` `o` ON ((`a`.`ItemID` = `o`.`id`))
          )
        LEFT JOIN `t_s_depart` `son` ON ((`a`.`SonID` = `son`.`ID`))
      )
    LEFT JOIN t_s_base_user che ON che.id = a.CHECKERID
  ORDER BY
    `a`.`Date` DESC,
    `a`.`BillNo` DESC,
    `b`.`IndexNumber`$$

-- ----------------------------
-- View structure for v_sc_po_order
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_po_order`$$
-- DROP VIEW IF EXISTS `v_sc_po_order`$$
CREATE or REPLACE VIEW `v_sc_po_order` AS
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
    `b`.`findex`$$

-- ----------------------------
-- View structure for v_sc_po_order_main
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_po_order_main`$$
-- DROP VIEW IF EXISTS `v_sc_po_order_main`$$
CREATE or REPLACE VIEW `v_sc_po_order_main` AS
  SELECT
    `a`.`id`           AS `id`,
    `a`.`create_name`  AS `create_name`,
    `a`.`create_by`    AS `create_by`,
    `a`.`create_date`  AS `create_date`,
    `a`.`update_name`  AS `update_name`,
    `a`.`update_by`    AS `update_by`,
    `a`.`update_date`  AS `update_date`,
    `a`.`tranType`     AS `tranType`,
    `a`.`date`         AS `date`,
    `a`.`billNo`       AS `billNo`,
    `a`.`itemId`       AS `itemId`,
    `a`.`contact`      AS `contact`,
    `a`.`mobilePhone`  AS `mobilePhone`,
    `a`.`phone`        AS `phone`,
    `a`.`fax`          AS `fax`,
    `a`.`address`      AS `address`,
    `a`.`empId`        AS `empId`,
    `a`.`deptId`       AS `deptId`,
    `a`.`stockId`      AS `stockId`,
    `a`.`rebateAmount` AS `rebateAmount`,
    `a`.`amount`       AS `amount`,
    `a`.`allAmount`    AS `allAmount`,
    `a`.`checkAmount`  AS `checkAmount`,
    `a`.`classId_SRC`  AS `classId_SRC`,
    `a`.`id_SRC`       AS `id_SRC`,
    `a`.`billNo_SRC`   AS `billNo_SRC`,
    `a`.`checkerId`    AS `checkerId`,
    `a`.`billerId`     AS `billerId`,
    `a`.`checkDate`    AS `checkDate`,
    `a`.`checkState`   AS `checkState`,
    `a`.`closed`       AS `closed`,
    `a`.`autoFlag`     AS `autoFlag`,
    `a`.`cancellation` AS `cancellation`,
    `a`.`explanation`  AS `explanation`,
    `a`.`sonId`        AS `sonId`,
    `a`.`version`      AS `version`,
    `a`.`isUse`        AS `isUse`,
    `b`.`Name`         AS `itemName`,
    `c`.`Name`         AS `empName`,
    `d`.`name`         AS `deptName`,
    `e`.`name`         AS `stockName`,
    `f`.`REALNAME`     AS `billerName`,
    `g`.`DEPARTNAME`   AS `sonName`
  FROM ((((((`t_sc_po_order` `a` LEFT JOIN `t_sc_supplier` `b` ON ((`a`.`itemId` = `b`.`id`))) LEFT JOIN `t_sc_emp` `c`
      ON ((`a`.`empId` = `c`.`id`))) LEFT JOIN `t_sc_department` `d` ON ((`a`.`deptId` = `d`.`id`))) LEFT JOIN
    `t_sc_stock` `e` ON ((`a`.`stockId` = `e`.`id`))) LEFT JOIN `t_s_base_user` `f`
      ON ((`a`.`billerId` = `f`.`ID`))) LEFT JOIN `t_s_depart` `g` ON ((`a`.`sonId` = `g`.`ID`)))
  WHERE ((`a`.`checkState` = 2) AND (`a`.`cancellation` = 0) AND (`a`.`checkAmount` < `a`.`allAmount`))
  ORDER BY `a`.`date` DESC, `a`.`billNo` DESC$$

-- ----------------------------
-- View structure for v_sc_po_stocalbill_main
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_po_stocalbill_main`$$
-- DROP VIEW IF EXISTS `v_sc_po_stocalbill_main`$$
CREATE or REPLACE VIEW `v_sc_po_stocalbill_main` AS
  SELECT
    `a`.`id`           AS `id`,
    `a`.`create_name`  AS `create_name`,
    `a`.`create_by`    AS `create_by`,
    `a`.`create_date`  AS `create_date`,
    `a`.`update_name`  AS `update_name`,
    `a`.`update_by`    AS `update_by`,
    `a`.`update_date`  AS `update_date`,
    `a`.`tranType`     AS `tranType`,
    `a`.`billNo`       AS `billNo`,
    `a`.`date`         AS `date`,
    `a`.`itemId`       AS `itemId`,
    `s`.`Name`         AS `itemName`,
    `a`.`stockId`      AS `stockId`,
    `stock`.`name`     AS `stockName`,
    `a`.`empId`        AS `empID`,
    `emp`.`Name`       AS `empName`,
    `a`.`deptId`       AS `deptId`,
    `dept`.`name`      AS `deptName`,
    `a`.`allAmount`    AS `allAmount`,
    `a`.`rebateAmount` AS `rebateAmount`,
    `a`.`curPayAmount` AS `curPayAmount`,
    `a`.`accountId`    AS `accountId`,
    `se`.`name`        AS `accountName`,
    `a`.`contact`      AS `contact`,
    `a`.`mobilePhone`  AS `mobilePhone`,
    `a`.`phone`        AS `phone`,
    `a`.`fax`          AS `fax`,
    `a`.`address`      AS `address`,
    `a`.`amount`       AS `amount`,
    `a`.`checkAmount`  AS `checkAmount`,
    `a`.`classID_SRC`  AS `classID_SRC`,
    `a`.`id_SRC`       AS `id_SRC`,
    `a`.`billNo_SRC`   AS `billNo_SRC`,
    `u`.`REALNAME`     AS `billerID`,
    `a`.`checkerId`    AS `checkerId`,
    `a`.`checkDate`    AS `checkDate`,
    `a`.`checkState`   AS `checkState`,
    `a`.`cancellation` AS `cancellation`,
    `a`.`explanation`  AS `explanation`,
    `d`.`DEPARTNAME`   AS `sonId`,
    `a`.`version`      AS `version`
  FROM (((((((`t_sc_po_stockbill` `a` LEFT JOIN `t_sc_department` `dept` ON ((`a`.`deptId` = `dept`.`id`))) LEFT JOIN
    `t_sc_emp` `emp` ON ((`a`.`empId` = `emp`.`id`))) LEFT JOIN `t_sc_stock` `stock`
      ON ((`a`.`stockId` = `stock`.`id`))) LEFT JOIN `t_sc_settleacct` `se`
      ON ((`a`.`accountId` = `se`.`id`))) LEFT JOIN `t_sc_supplier` `s` ON ((`a`.`itemId` = `s`.`id`))) LEFT JOIN
    `t_s_base_user` `u` ON ((`a`.`billerID` = `u`.`ID`))) LEFT JOIN `t_s_depart` `d` ON ((`a`.`sonId` = `d`.`ID`)))
  WHERE (`a`.`id` IN (SELECT `t_sc_po_stockbillentry`.`fid`
                      FROM `t_sc_po_stockbillentry`
                      WHERE (`t_sc_po_stockbillentry`.`qty` > ifnull(`t_sc_po_stockbillentry`.`commitQty`, 0))) AND
         (`a`.`checkState` = 2))$$

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
    `b`.`findex`$$

-- ----------------------------
-- View structure for v_sc_po_stockbill_info
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_po_stockbill_info`$$
-- DROP VIEW IF EXISTS `v_sc_po_stockbill_info`$$
CREATE or REPLACE VIEW `v_sc_po_stockbill_info` AS SELECT
                                          `a`.`id`                     AS `id`,
                                          `a`.`billNo`                 AS `billNo`,
                                          `a`.`date`                   AS `date`,
                                          `a`.`allAmount`              AS `allamount`,
                                          ifnull(`a`.`checkAmount`, 0) AS `checkamount`,
                                          `c`.`Name`                   AS `itemName`,
                                          `a`.`sonId`                  AS `sonId`,
                                          `a`.`tranType`               AS `tranType`,
                                          `d`.`bill_name`              AS `typeName`,
                                          `a`.`itemId`                 AS `itemId`,
                                          `a`.`id_SRC`                 AS `idSrc`
                                        FROM (((`t_sc_po_stockbill` `a` LEFT JOIN `t_sc_supplier` `b`
                                            ON ((`a`.`itemId` = `b`.`id`))) LEFT JOIN `t_sc_supplier` `c`
                                            ON ((`b`.`SettleCompany` = `c`.`id`))) LEFT JOIN `t_s_bill_info` `d`
                                            ON ((`a`.`tranType` = `d`.`bill_id`)))
                                        WHERE
                                          ((`a`.`checkState` = 2) AND (`a`.`tranType` = 52) AND (`a`.`cancellation` = 0)
                                           AND (`a`.`checkAmount` < `a`.`allAmount`))
                                        UNION SELECT
                                                `a`.`id`                     AS `id`,
                                                `a`.`billNo`                 AS `billNo`,
                                                `a`.`date`                   AS `date`,
                                                (-(1) * `a`.`allAmount`)     AS `allamount`,
                                                ifnull(`a`.`checkAmount`, 0) AS `checkamount`,
                                                `c`.`Name`                   AS `itemName`,
                                                `a`.`sonId`                  AS `sonId`,
                                                `a`.`tranType`               AS `tranType`,
                                                `d`.`bill_name`              AS `typeName`,
                                                `a`.`itemId`                 AS `itemId`,
                                                `a`.`id_SRC`                 AS `idSrc`
                                              FROM (((`t_sc_po_stockbill` `a` LEFT JOIN `t_sc_supplier` `b`
                                                  ON ((`a`.`itemId` = `b`.`id`))) LEFT JOIN `t_sc_supplier` `c`
                                                  ON ((`b`.`SettleCompany` = `c`.`id`))) LEFT JOIN `t_s_bill_info` `d`
                                                  ON ((`a`.`tranType` = `d`.`bill_id`)))
                                              WHERE ((`a`.`checkState` = 2) AND (`a`.`tranType` = 53) AND
                                                     (`a`.`cancellation` = 0) AND (`a`.`checkAmount` < `a`.`allAmount`))
                                        UNION SELECT
                                                `a`.`id`                     AS `id`,
                                                `a`.`billNo`                 AS `billNo`,
                                                `a`.`date`                   AS `date`,
                                                `a`.`allamount`              AS `allamount`,
                                                ifnull(`a`.`checkAmount`, 0) AS `checkamount`,
                                                `c`.`Name`                   AS `itemName`,
                                                `a`.`sonId`                  AS `sonId`,
                                                `a`.`tranType`               AS `tranType`,
                                                `d`.`bill_name`              AS `typeName`,
                                                `a`.`itemId`                 AS `itemId`,
                                                ''                           AS `idSrc`
                                              FROM (((`t_sc_rp_adjustbill` `a` LEFT JOIN `t_sc_supplier` `b`
                                                  ON ((`a`.`itemId` = `b`.`id`))) LEFT JOIN `t_sc_supplier` `c`
                                                  ON ((`b`.`SettleCompany` = `c`.`id`))) LEFT JOIN `t_s_bill_info` `d`
                                                  ON ((`a`.`tranType` = `d`.`bill_id`)))
                                              WHERE ((`a`.`tranType` = 202) AND (`a`.`checkstate` = 2) AND
                                                     (`a`.`cancellation` = 0) AND (`a`.`checkAmount` < `a`.`allamount`))
                                        UNION SELECT
                                                `a`.`id`                     AS `id`,
                                                `a`.`billNo`                 AS `billNo`,
                                                `a`.`date`                   AS `date`,
                                                `a`.`allAmount`              AS `allamount`,
                                                ifnull(`a`.`checkAmount`, 0) AS `checkamount`,
                                                `c`.`Name`                   AS `itemName`,
                                                `a`.`sonId`                  AS `sonId`,
                                                `a`.`tranType`               AS `tranType`,
                                                `d`.`bill_name`              AS `typeName`,
                                                `a`.`itemId`                 AS `itemId`,
                                                `a`.`id_SRC`                 AS `idSrc`
                                              FROM (((`t_sc_ic_jhstockbill` `a` LEFT JOIN `t_sc_supplier` `b`
                                                  ON ((`a`.`itemId` = `b`.`id`))) LEFT JOIN `t_sc_supplier` `c`
                                                  ON ((`b`.`SettleCompany` = `c`.`id`))) LEFT JOIN `t_s_bill_info` `d`
                                                  ON ((`a`.`tranType` = `d`.`bill_id`)))
                                              WHERE ((`a`.`checkState` = 2) AND (`a`.`cancellation` = 0) AND
                                                     (`a`.`checkAmount` < `a`.`allAmount`))
                                        UNION SELECT
                                                `a`.`id`                     AS `id`,
                                                `a`.`BillNo`                 AS `billNo`,
                                                `a`.`Date`                   AS `date`,
                                                `a`.`allamount`              AS `allamount`,
                                                ifnull(`a`.`checkamount`, 0) AS `checkamount`,
                                                `c`.`Name`                   AS `itemName`,
                                                `a`.`sonid`                  AS `sonId`,
                                                `a`.`trantype`               AS `tranType`,
                                                `d`.`bill_name`              AS `typeName`,
                                                `a`.`ItemID`                 AS `ItemID`,
                                                ''                           AS `idSrc`
                                              FROM (((`t_sc_begdata` `a` LEFT JOIN `t_sc_supplier` `b`
                                                  ON ((`a`.`ItemID` = `b`.`id`))) LEFT JOIN `t_sc_supplier` `c`
                                                  ON ((`b`.`SettleCompany` = `c`.`id`))) LEFT JOIN `t_s_bill_info` `d`
                                                  ON ((`a`.`trantype` = `d`.`bill_id`)))
                                              WHERE ((`a`.`trantype` = 1031) AND (`a`.`checkstate` = 2) AND
                                                     (`a`.`checkamount` < `a`.`allamount`))
                                        UNION SELECT
                                                `b`.`id`                      AS `id`,
                                                `b`.`logisticalNo`            AS `billNo`,
                                                `a`.`date`                    AS `date`,
                                                `b`.`freight`                 AS `allamount`,
                                                ifnull(`b`.`checkAmount`, 0)  AS `checkAmount`,
                                                `d`.`Name`                    AS `itemName`,
                                                `a`.`sonId`                   AS `sonid`,
                                                `b`.`tranType`                AS `tranType`,
                                                concat(`c`.`bill_name`, '物流') AS `typeName`,
                                                `b`.`logisticalId`            AS `itemId`,
                                                ''                            AS `idSrc`
                                              FROM (((`t_sc_sl_stockbill` `a` JOIN `t_sc_sl_logistical` `b`
                                                  ON (((`a`.`id` = `b`.`fid`) AND
                                                       (`a`.`tranType` = `b`.`tranType`)))) LEFT JOIN
                                                `t_s_bill_info` `c` ON ((`a`.`tranType` = `c`.`bill_id`))) LEFT JOIN
                                                `t_sc_logistical` `d` ON ((`b`.`logisticalId` = `d`.`id`)))
                                              WHERE ((`a`.`tranType` = 103) AND (`a`.`checkState` = 2) AND
                                                     (`b`.`checkAmount` < `b`.`freight`) AND (`a`.`cancellation` = 0))$$

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

-- ----------------------------
-- View structure for v_sc_promotion
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_promotion`$$
-- DROP VIEW IF EXISTS `v_sc_promotion`$$
CREATE or REPLACE VIEW `v_sc_promotion` AS
  SELECT
    `b`.`id`           AS `entryId`,
    `a`.`id`           AS `id`,
    `b`.`interID`      AS `goodsInterID`,
    `e`.`interID`      AS `giftsInterID`,
    `a`.`billNo`       AS `billNo`,
    `a`.`date`         AS `date`,
    `a`.`empID`        AS `empID`,
    `a`.`deptID`       AS `deptID`,
    `a`.`begDate`      AS `begDate`,
    `a`.`endDate`      AS `endDate`,
    `a`.`explanation`  AS `explanation`,
    `a`.`billerID`     AS `billerID`,
    `a`.`checkerID`    AS `checkerID`,
    `a`.`checkDate`    AS `checkDate`,
    `a`.`checkState`   AS `checkState`,
    `a`.`cancellation` AS `cancellation`,
    `a`.`sonID`        AS `sonID`,
    `a`.`tranType`     AS `tranType`,
    `c`.`Number`       AS `goodsNumber`,
    `c`.`Name`         AS `goodsName`,
    `c`.`Model`        AS `goodsModel`,
    `d`.`BarCode`      AS `goodsBarCode`,
    `b`.`unitID`       AS `goodsUnitId`,
    `b`.`qty`          AS `goodsQty`,
    `b`.`note`         AS `goodsNote`,
    `b`.`itemID`       AS `goodsItemId`,
    `e`.`indexnumber`  AS `indexnumber`,
    `f`.`Number`       AS `giftNumber`,
    `f`.`Name`         AS `giftName`,
    `f`.`Model`        AS `giftModel`,
    `g`.`BarCode`      AS `giftBarCode`,
    `e`.`unitID`       AS `giftUnitId`,
    `e`.`qty`          AS `giftQty`,
    `e`.`note`         AS `giftNote`,
    `e`.`itemID`       AS `giftItemId`,
    `k`.`name`         AS `goodsUnitName`,
    `h`.`name`         AS `giftUnitName`,
    `i`.`Name`         AS `empName`,
    `j`.`name`         AS `deptName`,
    `l`.`REALNAME`     AS `billerName`,
    `m`.`DEPARTNAME`   AS `sonName`,
    `g`.`Coefficient`  AS `Coefficient`
  FROM ((((((((((
      ((`t_sc_promotion` `a` LEFT JOIN `t_sc_promotiongoodsentry` `b` ON ((`b`.`interID` = `a`.`id`))) LEFT JOIN
        `t_sc_icitem` `c` ON ((`b`.`itemID` = `c`.`id`))) LEFT JOIN `t_sc_item_price` `d`
        ON ((`b`.`unitID` = `d`.`id`))) LEFT JOIN `t_sc_measureunit` `k` ON ((`k`.`id` = `d`.`UnitID`))) LEFT JOIN
    `t_sc_promotiongiftsentry` `e` ON ((`e`.`interID` = `a`.`id`))) LEFT JOIN `t_sc_icitem` `f`
      ON ((`e`.`itemID` = `f`.`id`))) LEFT JOIN `t_sc_item_price` `g` ON ((`e`.`unitID` = `g`.`id`))) LEFT JOIN
    `t_sc_measureunit` `h` ON ((`h`.`id` = `g`.`UnitID`))) LEFT JOIN `t_sc_emp` `i`
      ON ((`a`.`empID` = `i`.`id`))) LEFT JOIN `t_sc_department` `j` ON ((`a`.`deptID` = `j`.`id`))) LEFT JOIN
    `t_s_base_user` `l` ON ((`a`.`billerID` = `l`.`ID`))) LEFT JOIN `t_s_depart` `m` ON ((`a`.`sonID` = `m`.`ID`)))
  ORDER BY `a`.`date` DESC, `a`.`billNo` DESC, `e`.`indexnumber`$$

-- ----------------------------
-- View structure for v_sc_quote
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_quote`$$
-- DROP VIEW IF EXISTS `v_sc_quote`$$
CREATE or REPLACE VIEW `v_sc_quote` AS
  SELECT
    `c`.`id`           AS `entryId`,
    `b`.`billno`       AS `billno`,
    `b`.`id`           AS `id`,
    `b`.`cancellation` AS `cancellation`,
    `b`.`date`         AS `date`,
    `b`.`empid`        AS `empid`,
    `g`.`Name`         AS `empName`,
    `b`.`deptid`       AS `deptid`,
    `h`.`name`         AS `deptName`,
    `b`.`inuredate`    AS `inureDate`,
    `c`.`indexNumber`  AS `indexNumber`,
    `c`.`ItemID`       AS `itemID`,
    `d`.`Number`       AS `icitemNumber`,
    `d`.`Name`         AS `icitemName`,
    `d`.`Model`        AS `icitemModel`,
    `c`.`UnitID`       AS `unitID`,
    `e`.`BarCode`      AS `icitemBarCode`,
    `d`.`Weight`       AS `icitemWeight`,
    `f`.`id`           AS `icitemUnitId`,
    `f`.`name`         AS `icitemUnitName`,
    `e`.`Coefficient`  AS `icitemCoefficient`,
    `e`.`XSLimitPrice` AS `icitemxsLimitPrice`,
    `c`.`BegQty`       AS `begQty`,
    `c`.`EndQty`       AS `endQty`,
    `c`.`Price`        AS `price`,
    `c`.`CostPrice`    AS `CostPrice`,
    `c`.`GrossAmount`  AS `GrossAmount`,
    `c`.`Grossper`     AS `Grossper`,
    `c`.`Note`         AS `Note`,
    `b`.`checkstate`   AS `checkstate`,
    `i`.`REALNAME`     AS `billerName`,
    `j`.`DEPARTNAME`   AS `sonName`,
    `b`.`sonid`        AS `sonid`,
    `b`.`explanation`  AS `explanation`
  FROM ((((((((`t_sc_quote` `b` LEFT JOIN `t_sc_quoteitems` `c` ON ((`b`.`id` = `c`.`fid`))) LEFT JOIN `t_sc_icitem` `d`
      ON ((`c`.`ItemID` = `d`.`id`))) LEFT JOIN `t_sc_item_price` `e` ON ((`c`.`UnitID` = `e`.`id`))) LEFT JOIN
    `t_sc_measureunit` `f` ON ((`e`.`UnitID` = `f`.`id`))) LEFT JOIN `t_sc_emp` `g`
      ON ((`b`.`empid` = `g`.`id`))) LEFT JOIN `t_sc_department` `h` ON ((`b`.`deptid` = `h`.`id`))) LEFT JOIN
    `t_s_base_user` `i` ON ((`b`.`billerid` = `i`.`ID`))) LEFT JOIN `t_s_depart` `j` ON ((`b`.`sonid` = `j`.`ID`)))
  ORDER BY `b`.`date` DESC, `b`.`billno` DESC, `c`.`indexNumber`$$

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
	`c`.`INDEXNUMBER` $$

-- ----------------------------
-- View structure for v_sc_rp_adjustbill
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_rp_adjustbill`$$
-- DROP VIEW IF EXISTS `v_sc_rp_adjustbill`$$
CREATE or REPLACE VIEW `v_sc_rp_adjustbill` AS
  SELECT
    `a`.`id`                                       AS `id`,
    `a`.`tranType`                                 AS `tranType`,
    `a`.`date`                                     AS `date`,
    `a`.`billNo`                                   AS `billNo`,
    `a`.`itemId`                                   AS `itemId`,
    (CASE `a`.`tranType`
     WHEN 201
       THEN (SELECT `org`.`name`
             FROM `t_sc_organization` `org`
             WHERE (`a`.`itemId` = `org`.`id`))
     ELSE (SELECT `sup`.`Name`
           FROM `t_sc_supplier` `sup`
           WHERE (`a`.`itemId` = `sup`.`id`)) END) AS `itemName`,
    `a`.`empId`                                    AS `empId`,
    `e`.`Name`                                     AS `empName`,
    `a`.`deptId`                                   AS `deptId`,
    `d`.`name`                                     AS `deptName`,
    `a`.`allamount`                                AS `allAmount`,
    `a`.`checkAmount`                              AS `checkAmount`,
    `a`.`classIdSrc`                               AS `classIdSrc`,
    `f`.`bill_name`                                AS `className`,
    `a`.`idSrc`                                    AS `idSrc`,
    `a`.`billNoSrc`                                AS `billNoSrc`,
    `a`.`billerId`                                 AS `billerId`,
    `g`.`REALNAME`                                 AS `billerName`,
    `a`.`checkerId`                                AS `checkerId`,
    `h`.`REALNAME`                                 AS `checkerName`,
    `a`.`checkDate`                                AS `checkDate`,
    `a`.`checkstate`                               AS `checkState`,
    `a`.`cancellation`                             AS `cancellation`,
    `a`.`explanation`                              AS `explanation`,
    `a`.`sonId`                                    AS `sonId`,
    `i`.`DEPARTNAME`                               AS `sonName`,
    `b`.`id`                                       AS `entryId`,
    `b`.`fid`                                      AS `fid`,
    `b`.`findex`                                   AS `findex`,
    `b`.`expId`                                    AS `expId`,
    `j`.`Name`                                     AS `expName`,
    `b`.`amount`                                   AS `amount`,
    `b`.`note`                                     AS `note`
  FROM ((((((((`t_sc_rp_adjustbill` `a` JOIN `t_sc_rp_adjustbillentry` `b` ON ((`a`.`id` = `b`.`fid`))) LEFT JOIN
    `t_sc_department` `d` ON ((`a`.`deptId` = `d`.`id`))) LEFT JOIN `t_sc_emp` `e`
      ON ((`a`.`empId` = `e`.`id`))) LEFT JOIN `t_s_bill_info` `f` ON ((`a`.`classIdSrc` = `f`.`bill_id`))) LEFT JOIN
    `t_s_base_user` `g` ON ((`a`.`billerId` = `g`.`ID`))) LEFT JOIN `t_s_base_user` `h`
      ON ((`a`.`checkerId` = `h`.`ID`))) LEFT JOIN `t_s_depart` `i` ON ((`a`.`sonId` = `i`.`ID`))) LEFT JOIN
    `t_sc_fee` `j` ON ((`b`.`expId` = `j`.`id`)))$$

-- ----------------------------
-- View structure for v_sc_rp_adjustbill_fina
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_rp_adjustbill_fina`$$
-- DROP VIEW IF EXISTS `v_sc_rp_adjustbill_fina`$$
CREATE or REPLACE VIEW `v_sc_rp_adjustbill_fina` AS
  SELECT
    `b`.`id`           AS `entryId`,
    `a`.`id`           AS `id`,
    `a`.`create_name`  AS `createName`,
    `a`.`create_by`    AS `createBy`,
    `a`.`create_date`  AS `createDate`,
    `a`.`update_by`    AS `updateBy`,
    `a`.`update_date`  AS `updateDate`,
    `a`.`update_name`  AS `updateName`,
    `a`.`billNo`       AS `billNo`,
    `a`.`date`         AS `date`,
    `e`.`Name`         AS `empName`,
    `d`.`name`         AS `deptName`,
    `a`.`allamount`    AS `allamount`,
    `b`.`findex`       AS `findex`,
    `f`.`Name`         AS `feeName`,
    `b`.`amount`       AS `amount`,
    b.NOTE             AS note,
    `a`.`explanation`  AS `explanation`,
    `u`.REALNAME       AS `billUserName`,
    `a`.`checkstate`   AS `checkstate`,
    `u1`.REALNAME      AS `checkUserName`,
    `a`.`checkDate`    AS `checkDate`,
    `a`.`cancellation` AS `cancellation`,
    `s`.`name`         AS `accountName`,
    `de`.`DEPARTNAME`  AS `departName`
  FROM
    (
        (
            (
                (
                    (
                        (
                            (
                                (
                                    `t_sc_rp_adjustbill` `a`
                                    LEFT JOIN `t_sc_rp_adjustbillentry` `b` ON ((`a`.`id` = `b`.`fid`))
                                  )
                                LEFT JOIN `t_sc_emp` `e` ON ((`a`.`empId` = `e`.`id`))
                              )
                            LEFT JOIN `t_sc_department` `d` ON ((`a`.`deptId` = `d`.`id`))
                          )
                        LEFT JOIN `t_sc_settleacct` `s` ON ((`a`.`accountId` = `s`.`id`))
                      )
                    LEFT JOIN `t_sc_fee` `f` ON ((`b`.`expId` = `f`.`id`))
                  )
                LEFT JOIN `t_s_base_user` `u` ON ((`a`.`billerId` = `u`.`ID`))
              )
            LEFT JOIN `t_s_base_user` `u1` ON (
            (`a`.`checkerId` = `u1`.`ID`)
            )
          )
        LEFT JOIN `t_s_depart` `de` ON ((`a`.`sonId` = `de`.`ID`))
    )$$

-- ----------------------------
-- View structure for v_sc_rp_contact
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_rp_contact`$$
-- DROP VIEW IF EXISTS `v_sc_rp_contact`$$
CREATE or REPLACE VIEW `v_sc_rp_contact` AS SELECT
                                                  t.*,
                                                  t.checkflag AS rp
                                                FROM v_sc_rp_rreportbill t
                                                UNION ALL
                                                SELECT
                                                  t.*,
                                                  t.checkflag AS rp
                                                FROM v_sc_rp_preportbill t$$

-- ----------------------------
-- View structure for v_sc_rp_expensesapply
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_rp_expensesapply`$$
-- DROP VIEW IF EXISTS `v_sc_rp_expensesapply`$$
CREATE or REPLACE VIEW `v_sc_rp_expensesapply` AS
  SELECT
    `b`.`id` AS `entryId`,
    `a`.`id` AS `id`,
    `a`.`create_name` AS `createName`,
    `a`.`create_by` AS `createBy`,
    `a`.`create_date` AS `createDate`,
    `a`.`update_by` AS `updateBy`,
    `a`.`update_date` AS `updateDate`,
    `a`.`update_name` AS `updateName`,
    `a`.`billNo` AS `billNo`,
    `a`.`date` AS `date`,
    `e`.`Name` AS `empName`,
    `d`.`name` AS `deptName`,
    `a`.`allAmount` AS `allamount`,
    `b`.`findex` AS `findex`,
    `f`.`Name` AS `feeName`,
    `b`.`amount` AS `amount`,
    `b`.`haveAmount` AS `haveAmount`,
    `b`.`notAmount` AS `notAmount`,
    `a`.`explanation` AS `explanation`,
    `u`.REALNAME AS `billUserName`,
    `a`.`checkState` AS `checkstate`,
    `u1`.REALNAME AS `checkUserName`,
    `a`.`checkDate` AS `checkDate`,
    `a`.`cancellation` AS `cancellation`,
    `de`.`DEPARTNAME` AS `departName`,
    `a`.`itemClassId` AS `itemClassId`,
    `a`.`itemId` AS `itemId`,
    (
      CASE
      WHEN (`a`.`itemClassId` = 1) THEN
        (
          SELECT
            `e1`.`Name`
          FROM
            `t_sc_emp` `e1`
          WHERE
            (`e1`.`id` = `a`.`itemId`)
        )
      ELSE
        (
          SELECT
            `d1`.`name`
          FROM
            `t_sc_department` `d1`
          WHERE
            (`d1`.`id` = `a`.`itemId`)
        )
      END
    ) AS `itemName`,
    `a`.`tranType` AS `tranType`,
    `a`.`empId` AS `empId`,
    `a`.`deptId` AS `deptId`,
    `a`.`checkerId` AS `checkerId`,
    `a`.`billerId` AS `billerId`,
    `a`.`sonId` AS `sonId`,
    `b`.`fid` AS `fid`,
    `b`.`expId` AS `expId`,
    `b`.`note` AS `note`,
    `bi`.`bill_name` AS `billName`
  FROM
    (
        (
            (
                (
                    (
                        (
                            (
                                (
                                    `t_sc_rp_expensesapply` `a`
                                    LEFT JOIN `t_sc_rp_expensesapplyentry` `b` ON ((`a`.`id` = `b`.`fid`))
                                  )
                                LEFT JOIN `t_sc_emp` `e` ON ((`a`.`empId` = `e`.`id`))
                              )
                            LEFT JOIN `t_sc_department` `d` ON ((`a`.`deptId` = `d`.`id`))
                          )
                        LEFT JOIN `t_sc_fee` `f` ON ((`b`.`expId` = `f`.`id`))
                      )
                    LEFT JOIN `t_s_base_user` `u` ON ((`a`.`billerId` = `u`.`ID`))
                  )
                LEFT JOIN `t_s_base_user` `u1` ON (
                (`a`.`checkerId` = `u1`.`ID`)
                )
              )
            LEFT JOIN `t_s_depart` `de` ON ((`a`.`sonId` = `de`.`ID`))
          )
        LEFT JOIN `t_s_bill_info` `bi` ON (
        (
          `a`.`tranType` = `bi`.`bill_id`
        )
        )
    ) $$

-- ----------------------------
-- View structure for v_sc_rp_expreportbill
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_rp_expreportbill`$$
-- DROP VIEW IF EXISTS `v_sc_rp_expreportbill`$$
CREATE or REPLACE VIEW `v_sc_rp_expreportbill` AS -- ---------------------初始化数据
  SELECT
    BillNo,
    Date,
    TranType,
    id,
    0      AS FID,
    #0 AS ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    0      AS SettleID,
    ItemID AS AccountID,
    ''     AS SettleIDNo,
    AllAmount,
    1      AS ExpFlag,
    create_date,
    SonID,
    0      AS Cancellation,
    CheckState,
    BillerID
  FROM T_SC_BegData
  WHERE TranType = 1023 #AND Cancellation=0

  UNION ALL
  /*----------------------采购费用/销售费用
   --一期未做
   SELECT FBillNo,FDate,FTranType,id,0,FItemClassID,FItemID,FDeptID,FEmpID,
	      FSettleID,FAccountID,FSettleIDNo,FPayAmount,-1,create_date,FSonID,FCancellation,FCheckState,FBillerID
   FROM T_IC_Expenses WHERE FPayAmount<>0 AND FCancellation=0

   UNION ALL*/
  -- -------------------采购/销售换货
  SELECT
    BillNo,
    Date,
    TranType,
    id,
    0,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    0  AS SettleID,
    AccountID,
    '' AS SettleIDNo,
    CurPayAmount,
    CASE WHEN TranType = 61
      THEN -1
    ELSE 1 END,
    create_date,
    SonID,
    Cancellation,
    CheckState,
    BillerID
  FROM T_SC_IC_JHStockBill
  WHERE CurPayAmount <> 0 AND Cancellation = 0

  UNION ALL
  -- ------------------调账业务
  SELECT
    BillNo,
    Date,
    TranType,
    Id,
    0,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    0  AS SettleID,
    AccountID,
    '' AS SettleIDNo,
    AllAmount,
    CASE WHEN (TranType = 301)
      THEN 1
    ELSE -1 END,
    create_date,
    SonID,
    Cancellation,
    CheckState,
    BillerID
  FROM T_SC_RP_AdjustBill
  WHERE TranType IN (301, 302) AND Cancellation = 0

  UNION ALL
  -- -----------------银行存取款单
  SELECT
    BillNo,
    Date,
    TranType,
    Id,
    0,
    #0,
    0,
    DeptID,
    EmpID,
    0,
    PAccountID,
    '',
    AllAmount,
    -1,
    create_date,
    SCSonID,
    Cancellation,
    CheckState,
    BillerID
  FROM T_SC_RP_BankBill
  WHERE Cancellation = 0

  UNION ALL
  SELECT
    BillNo,
    Date,
    TranType,
    Id,
    0,
    #0,
    0,
    DeptID,
    EmpID,
    0,
    RAccountID,
    '',
    AllAmount,
    1,
    create_date,
    DCSonID,
    Cancellation,
    CheckState,
    BillerID
  FROM T_SC_RP_BankBill
  WHERE Cancellation = 0

  UNION ALL
  -- -----------------付款单
  SELECT
    t1.BillNo,
    t1.Date,
    t1.TranType,
    t1.Id,
    t2.ID,
    #t1.ItemClassID,
    t1.ItemID,
    t1.DeptID,
    t1.EmpID,
    0  AS SettleID,
    t2.AccountID,
    '' AS SettleIDNo,
    #CASE WHEN t1.TranType=54 THEN t2.CheckAmount ELSE -t2.CheckAmount END,-1,t1.create_date,t1.SonID,
    CASE WHEN t1.TranType = 54
      THEN t2.Amount
    ELSE -t2.Amount END,
    -1,
    t1.create_date,
    t1.SonID,
    Cancellation,
    CheckState,
    BillerID
  FROM T_SC_RP_PBillEntry1 t2
    LEFT JOIN T_SC_RP_PBill t1 ON t1.Id = t2.Id
  WHERE Cancellation = 0

  UNION ALL
  -- ----------------采购入库单
  SELECT
    BillNo,
    Date,
    TranType,
    Id,
    0,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    0  AS SettleID,
    AccountID,
    '' AS SettleIDNo,
    CASE WHEN TranType IN (52, 1604)
      THEN CurPayAmount
    ELSE -CurPayAmount END,
    -1,
    create_date,
    SonID,
    Cancellation,
    CheckState,
    BillerID
  FROM T_SC_PO_StockBill
  WHERE CurPayAmount <> 0 AND Cancellation = 0

  UNION ALL
  -- ----------------其他支出/资金借出
  SELECT
    BillNo,
    Date,
    TranType,
    Id,
    0,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    0  AS SettleID,
    AccountID,
    '' AS SettleIDNo,
    AllAmount,
    -1,
    create_date,
    SonID,
    Cancellation,
    CheckState,
    BillerID
  FROM T_SC_RP_POtherBill
  WHERE Cancellation = 0

  UNION ALL
  -- ----------------收款单
  SELECT
    t1.BillNo,
    t1.Date,
    t1.TranType,
    t1.Id,
    t2.ID,
    #t1.ItemClassID,
    t1.ItemID,
    t1.DeptID,
    t1.EmpID,
    0  AS SettleID,
    t2.AccountID,
    '' AS SettleIDNo,
    #CASE WHEN t1.TranType=105 THEN t2.CheckAmount ELSE -t2.CheckAmount END,1,t1.create_date,t1.SonID,
    CASE WHEN t1.TranType = 105
      THEN t2.Amount
    ELSE -t2.Amount END,
    1,
    t1.create_date,
    t1.SonID,
    Cancellation,
    CheckState,
    BillerID
  FROM T_SC_RP_RBillEntry1 t2
    LEFT JOIN T_SC_RP_RBill t1 ON t1.Id = t2.Id
  WHERE Cancellation = 0

  UNION ALL
  -- ---------------结算单
  SELECT
    BillNo,
    Date,
    TranType,
    Id,
    0,
    #ItemClassID,
    ItemID,
    DeptID,
    EmpID,
    0  AS SettleID,
    AccountID,
    '' AS SettleIDNo,
    CASE WHEN TranType = 103
      THEN CurPayAmount
    ELSE -CurPayAmount END,
    1,
    create_date,
    SonID,
    Cancellation,
    CheckState,
    BillerID
  FROM T_SC_SL_StockBill
  WHERE CurPayAmount <> 0 AND Cancellation = 0

  UNION ALL
  -- --------------其他收入/资金借入
  SELECT
    BillNo,
    Date,
    TranType,
    Id,
    0,
    #ItemClassID,
    0  AS ItemID,
    DeptID,
    EmpID,
    0  AS SettleID,
    AccountID,
    '' AS SettleIDNo,
    AllAmount,
    1,
    create_date,
    SonID,
    Cancellation,
    CheckState,
    BillerID
  FROM T_SC_RP_ROtherBill
  WHERE Cancellation = 0

/*UNION ALL
   -- -------------工资支付
   -- 一期未做
   SELECT FBillNo,FDate,FTranType,Id,0,0,0,FDeptID,FEmpID,FSettleID,FAccountID,FSettleIDNo,FSettleAmount,-1,
          create_date,FSonID,FCancellation,FCheckState,FBillerID
   FROM T_RP_Payment WHERE FCancellation=0

   UNION ALL
   -- ------------零售单
   -- 一期未做
   SELECT s2.FBillNo,s2.FDate,s2.FTranType,s2.Id,s1.FID,0,0,0,0,0,s1.FPayID,
	         s1.FCardNo,CASE WHEN s2.FROB=87 THEN s1.FAmount-s1.FHaveAmount ELSE -(s1.FAmount-s1.FHaveAmount) END,1,
	         s2.create_date,s2.FSonID,s2.FCancellation,s2.FCheckState,s2.FBillerID
   FROM T_LS_Pay s1
	    LEFT JOIN T_LS_Retail s2 ON s1.Id=s2.Id
   WHERE s1.FPayID IN (3,4) AND s2.FCancellation=0

   UNION ALL
   -- -----------会员卡充值
   -- 一期未做
   SELECT CASE WHEN s1.FFillType IN (1,2) THEN '会员卡:'+s2.FNumber+' 发卡、充值时账户收入增加' ELSE
           '会员卡:'+s2.FNumber+' 退卡时账户支出增加' END,
         s1.FDate,-1,s1.FItemID,1,0,0,s1.FDeptID,s1.FEmpID,0,s1.FAccountID,'',
	     s1.FAmount,CASE WHEN s1.FFillType IN (1,2) THEN 1 ELSE -1 END,getdate(),t1.FSonID,
	     0,2,s1.FBillerID
    FROM T_LS_MemberFill s1
      LEFT JOIN T_LS_MemberCard s2 ON s1.FMemberID=s2.FItemID
      LEFT JOIN T_SYS_User t1 ON t1.FUserID=s1.FBillerID
    WHERE s1.FFillType IN (1,2,4) AND s1.FAmount<>0*/$$

-- ----------------------------
-- View structure for v_sc_rp_pbill
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_rp_pbill`$$
-- DROP VIEW IF EXISTS `v_sc_rp_pbill`$$
CREATE or REPLACE VIEW `v_sc_rp_pbill` AS
  SELECT
    `a`.`id`                                                AS `id`,
    `a`.`tranType`                                          AS `tranType`,
    `a`.`date`                                              AS `date`,
    `a`.`billNo`                                            AS `billNo`,
    `a`.`itemId`                                            AS `itemId`,
    `a`.`empId`                                             AS `empId`,
    `a`.`deptId`                                            AS `deptId`,
    `a`.`billtype`                                          AS `billType`,
    `a`.`allamount`                                         AS `allAmount`,
    `a`.`billerId`                                          AS `billerId`,
    `a`.`checkerId`                                         AS `checkerId`,
    `a`.`checkDate`                                         AS `checkDate`,
    `a`.`checkstate`                                        AS `checkState`,
    `a`.`cancellation`                                      AS `cancellation`,
    `a`.`explanation`                                       AS `explanation`,
    `a`.`sonId`                                             AS `sonId`,
    `a`.`checkamount`                                       AS `checkamount`,
    `b`.`id`                                                AS `entryId`,
    `b`.`fid`                                               AS `fid`,
    `b`.`findex`                                            AS `findex`,
    `b`.`accountId`                                         AS `accountId`,
    `b`.`amount`                                            AS `amount`,
    `b`.`note`                                              AS `note`,
    ifnull(`c`.`Name`, (SELECT `org`.`Name`
                        FROM `t_sc_logistical` `org`
                        WHERE (`a`.`itemId` = `org`.`id`))) AS `itemName`,
    `d`.`name`                                              AS `deptName`,
    `e`.`Name`                                              AS `empName`,
    `f`.`name`                                              AS `accountName`,
    `g`.`REALNAME`                                          AS `billerName`,
    `h`.`REALNAME`                                          AS `checkerName`,
    `i`.`DEPARTNAME`                                        AS `sonName`
  FROM ((((((
      ((`t_sc_rp_pbill` `a` JOIN `t_sc_rp_pbillentry1` `b` ON ((`a`.`id` = `b`.`fid`))) LEFT JOIN `t_sc_supplier` `c`
          ON ((`a`.`itemId` = `c`.`id`))) LEFT JOIN `t_sc_department` `d` ON ((`a`.`deptId` = `d`.`id`))) LEFT JOIN
    `t_sc_emp` `e` ON ((`a`.`empId` = `e`.`id`))) LEFT JOIN `t_sc_settleacct` `f`
      ON ((`b`.`accountId` = `f`.`id`))) LEFT JOIN `t_s_base_user` `g` ON ((`a`.`billerId` = `g`.`ID`))) LEFT JOIN
    `t_s_base_user` `h` ON ((`a`.`checkerId` = `h`.`ID`))) LEFT JOIN `t_s_depart` `i` ON ((`a`.`sonId` = `i`.`ID`)))
  ORDER BY `a`.`billNo` DESC, `b`.`findex`$$

-- ----------------------------
-- View structure for v_sc_rp_potherbill
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_rp_potherbill`$$
-- DROP VIEW IF EXISTS `v_sc_rp_potherbill`$$
CREATE or REPLACE VIEW `v_sc_rp_potherbill` AS
  SELECT
    `b`.`id` AS `entryId`,
    `a`.`id` AS `id`,
    `a`.`create_name` AS `createName`,
    `a`.`create_by` AS `createBy`,
    `a`.`create_date` AS `createDate`,
    `a`.`update_by` AS `updateBy`,
    `a`.`update_date` AS `updateDate`,
    `a`.`update_name` AS `updateName`,
    `a`.`billNo` AS `billNo`,
    `a`.`date` AS `date`,
    `a`.`itemClassId` AS `itemClassId`,
    `a`.`itemId` AS `itemId`,
    (
      CASE
      WHEN (`a`.`itemClassId` = 1) THEN
        (
          SELECT
            `e1`.`Name`
          FROM
            `t_sc_emp` `e1`
          WHERE
            (`e1`.`id` = `a`.`itemId`)
        )
      ELSE
        (
          SELECT
            `d1`.`name`
          FROM
            `t_sc_department` `d1`
          WHERE
            (`d1`.`id` = `a`.`itemId`)
        )
      END
    ) AS `itemName`,
    `e`.`Name` AS `empName`,
    `d`.`name` AS `deptName`,
    `s`.`name` AS `accountName`,
    `a`.`allAmount` AS `allamount`,
    `b`.`findex` AS `findex`,
    `f`.`Name` AS `feeName`,
    `b`.`amount` AS `amount`,
    `b`.`haveAmountSrc` AS `haveAmountSrc`,
    `b`.`notAmountSrc` AS `notAmountSrc`,
    `b`.`billNoSrc` AS `billNoSrc`,
    `bi`.`bill_name` AS `billName`,
    `b`.`note` AS `note`,
    `a`.`explanation` AS `explanation`,
    `u`.REALNAME AS `billUserName`,
    `a`.`checkState` AS `checkstate`,
    `u1`.REALNAME AS `checkUserName`,
    `a`.`checkDate` AS `checkDate`,
    `a`.`cancellation` AS `cancellation`,
    `de`.`DEPARTNAME` AS `departName`
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
                                        `t_sc_rp_potherbill` `a`
                                        LEFT JOIN `t_sc_rp_potherbillentry` `b` ON ((`a`.`id` = `b`.`fid`))
                                      )
                                    LEFT JOIN `t_sc_emp` `e` ON ((`a`.`empId` = `e`.`id`))
                                  )
                                LEFT JOIN `t_sc_department` `d` ON ((`a`.`deptId` = `d`.`id`))
                              )
                            LEFT JOIN `t_sc_fee` `f` ON ((`b`.`expId` = `f`.`id`))
                          )
                        LEFT JOIN `t_s_base_user` `u` ON ((`a`.`billerId` = `u`.`ID`))
                      )
                    LEFT JOIN `t_s_base_user` `u1` ON (
                    (`a`.`checkerId` = `u1`.`ID`)
                    )
                  )
                LEFT JOIN `t_s_depart` `de` ON ((`a`.`sonId` = `de`.`ID`))
              )
            LEFT JOIN `t_s_bill_info` `bi` ON (
            (
              `b`.`classIdSrc` = `bi`.`bill_id`
            )
            )
          )
        LEFT JOIN `t_sc_settleacct` `s` ON ((`s`.`id` = `a`.`accountId`))
    ) $$


-- ----------------------------
-- View structure for v_sc_rp_rbill
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_rp_rbill`$$
-- DROP VIEW IF EXISTS `v_sc_rp_rbill`$$
CREATE or REPLACE VIEW `v_sc_rp_rbill` AS
  SELECT
    `a`.`id`           AS `id`,
    `a`.`tranType`     AS `tranType`,
    `a`.`date`         AS `date`,
    `a`.`billNo`       AS `billNo`,
    `a`.`itemId`       AS `itemId`,
    `a`.`empId`        AS `empId`,
    `a`.`deptId`       AS `deptId`,
    `a`.`billType`     AS `billType`,
    `a`.`allamount`    AS `allAmount`,
    `a`.`billerId`     AS `billerId`,
    `a`.`checkerId`    AS `checkerId`,
    `a`.`checkDate`    AS `checkDate`,
    `a`.`checkstate`   AS `checkState`,
    `a`.`cancellation` AS `cancellation`,
    `a`.`explanation`  AS `explanation`,
    `a`.`sonId`        AS `sonId`,
    `a`.`checkamount`  AS `checkamount`,
    `b`.`id`           AS `entryId`,
    `b`.`fid`          AS `fid`,
    `b`.`findex`       AS `findex`,
    `b`.`accountId`    AS `accountId`,
    `b`.`amount`       AS `amount`,
    `b`.`note`         AS `note`,
    `c`.`name`         AS `itemName`,
    `d`.`name`         AS `deptName`,
    `e`.`Name`         AS `empName`,
    `f`.`name`         AS `accountName`,
    `g`.`REALNAME`     AS `billerName`,
    `h`.`REALNAME`     AS `checkerName`,
    `i`.`DEPARTNAME`   AS `sonName`
  FROM ((((((((`t_sc_rp_rbill` `a` JOIN `t_sc_rp_rbillentry1` `b` ON ((`a`.`id` = `b`.`fid`))) LEFT JOIN
    `t_sc_organization` `c` ON ((`a`.`itemId` = `c`.`id`))) LEFT JOIN `t_sc_department` `d`
      ON ((`a`.`deptId` = `d`.`id`))) LEFT JOIN `t_sc_emp` `e` ON ((`a`.`empId` = `e`.`id`))) LEFT JOIN
    `t_sc_settleacct` `f` ON ((`b`.`accountId` = `f`.`id`))) LEFT JOIN `t_s_base_user` `g`
      ON ((`a`.`billerId` = `g`.`ID`))) LEFT JOIN `t_s_base_user` `h` ON ((`a`.`checkerId` = `h`.`ID`))) LEFT JOIN
    `t_s_depart` `i` ON ((`a`.`sonId` = `i`.`ID`)))
  ORDER BY `a`.`billNo` DESC, `b`.`findex`$$

-- ----------------------------
-- View structure for v_sc_rp_rotherbill
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_rp_rotherbill`$$
-- DROP VIEW IF EXISTS `v_sc_rp_rotherbill`$$
CREATE or REPLACE VIEW `v_sc_rp_rotherbill` AS
  SELECT
    `b`.`id` AS `entryId`,
    `a`.`id` AS `id`,
    `a`.`create_name` AS `createName`,
    `a`.`create_by` AS `createBy`,
    `a`.`create_date` AS `createDate`,
    `a`.`update_by` AS `updateBy`,
    `a`.`update_date` AS `updateDate`,
    `a`.`update_name` AS `updateName`,
    `a`.`billNo` AS `billNo`,
    `a`.`date` AS `date`,
    `e`.`Name` AS `empName`,
    `d`.`name` AS `deptName`,
    `a`.`allAmount` AS `allamount`,
    `b`.`findex` AS `findex`,
    `f`.`Name` AS `feeName`,
    `b`.`amount` AS `amount`,
    b.NOTE as note,
    `a`.`explanation` AS `explanation`,
    `u`.REALNAME AS `billUserName`,
    `a`.`checkState` AS `checkstate`,
    `u1`.REALNAME AS `checkUserName`,
    `a`.`checkDate` AS `checkDate`,
    `a`.`cancellation` AS `cancellation`,
    `s`.`name` AS `accountName`,
    `de`.`DEPARTNAME` AS `departName`
  FROM
    (
        (
            (
                (
                    (
                        (
                            (
                                (
                                    `t_sc_rp_rotherbill` `a`
                                    LEFT JOIN `t_sc_rp_rotherbillentry` `b` ON ((`a`.`id` = `b`.`fid`))
                                  )
                                LEFT JOIN `t_sc_emp` `e` ON ((`a`.`empId` = `e`.`id`))
                              )
                            LEFT JOIN `t_sc_department` `d` ON ((`a`.`deptId` = `d`.`id`))
                          )
                        LEFT JOIN `t_sc_settleacct` `s` ON ((`a`.`accountId` = `s`.`id`))
                      )
                    LEFT JOIN `t_sc_fee` `f` ON ((`b`.`expId` = `f`.`id`))
                  )
                LEFT JOIN `t_s_base_user` `u` ON ((`a`.`billerId` = `u`.`ID`))
              )
            LEFT JOIN `t_s_base_user` `u1` ON (
            (`a`.`checkerId` = `u1`.`ID`)
            )
          )
        LEFT JOIN `t_s_depart` `de` ON ((`a`.`sonId` = `de`.`ID`))
    ) $$


-- ----------------------------
-- View structure for v_sc_sl_stockbill
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_sl_stockbill`$$
-- DROP VIEW IF EXISTS `v_sc_sl_stockbill`$$
CREATE or REPLACE VIEW `v_sc_sl_stockbill` AS
  SELECT
    `a`.`id`               AS `id`,
    `a`.`create_name`      AS `create_name`,
    `a`.`create_by`        AS `create_by`,
    `a`.`create_date`      AS `create_date`,
    `a`.`update_name`      AS `update_name`,
    `a`.`update_by`        AS `update_by`,
    `a`.`update_date`      AS `update_date`,
    `a`.`tranType`         AS `tranType`,
    `a`.`billNo`           AS `billNo`,
    `a`.`date`             AS `date`,
    `s`.`name`             AS `itemName`,
    `s`.`iscreditmgr`      AS `iscreditmgr`,
    `s`.`creditamount`     AS `creditamount`,
    `emp`.`Name`           AS `empName`,
    `dept`.`name`          AS `deptName`,
    `stock`.`name`         AS `stockName`,
    `a`.`itemId`           AS `itemId`,
    `a`.`empId`            AS `empId`,
    `a`.`deptId`           AS `deptId`,
    `a`.`stockId`          AS `stockId`,
    `a`.`allamount`        AS `allAmount`,
    `a`.`rebateamount`     AS `rebateAmount`,
    `a`.`curpayamount`     AS `curPayAmount`,
    `se`.`name`            AS `accountId`,
    `a`.`contact`          AS `contact`,
    `a`.`mobilePhone`      AS `mobilePhone`,
    `a`.`phone`            AS `phone`,
    `a`.`fax`              AS `fax`,
    `a`.`address`          AS `address`,
    `a`.`amount`           AS `amount`,
    `a`.`checkamount`      AS `checkAmount`,
    `a`.`classID_SRC`      AS `classID_SRC`,
    `a`.`id_SRC`           AS `id_SRC`,
    `a`.`billNo_SRC`       AS `billNo_SRC`,
    `u`.`REALNAME`         AS `billerID`,
    `a`.`checkerId`        AS `checkerId`,
    `checker`.`REALNAME`   AS `checkerName`,
    `a`.`checkDate`        AS `checkDate`,
    `a`.`checkState`       AS `checkState`,
    `a`.`cancellation`     AS `cancellation`,
    `a`.`explanation`      AS `explanation`,
    `t`.`DEPARTNAME`       AS `sonName`,
    `a`.`sonId`            AS `sonId`,
    `a`.`version`          AS `version`,
    `a`.`weight`           AS `weight`,
    `a`.`freight`          AS `freight`,
    `b`.`id`               AS `entryId`,
    `b`.`itemId`           AS `entryItemId`,
    `b`.`stockId`          AS `entryStockId`,
    `b`.`batchNo`          AS `batchNo`,
    `b`.`unitId`           AS `unitId`,
    `b`.`qty`              AS `qty`,
    `b`.`basicUnitId`      AS `basicUnitId`,
    `b`.`coefficient`      AS `coefficient`,
    `b`.`basicqty`         AS `basicQty`,
    `b`.`secUnitId`        AS `secUnitId`,
    `b`.`seccoefficient`   AS `secCoefficient`,
    `b`.`secqty`           AS `secQty`,
    `b`.`taxpriceex`       AS `taxPriceEx`,
    `b`.`taxamountex`      AS `taxAmountEx`,
    `b`.`discountrate`     AS `discountRate`,
    `b`.`taxprice`         AS `taxPrice`,
    `b`.`intaxamount`      AS `inTaxAmount`,
    `b`.`taxrate`          AS `taxRate`,
    `b`.`price`            AS `entryPrice`,
    `b`.`amount`           AS `entryAmount`,
    `b`.`discountprice`    AS `discountPrice`,
    `b`.`discountamount`   AS `discountAmount`,
    `b`.`taxamount`        AS `taxAmount`,
    `b`.`kfDate`           AS `kfDate`,
    `b`.`kfPeriod`         AS `kfPeriod`,
    `b`.`salesid`          AS `salesid`,
    (
      CASE `b`.`isFreeGift`
      WHEN 0
        THEN
          '调价政策'
      WHEN 1
        THEN
          '买一赠一'
      WHEN 2
        THEN
          ''
      END
    )                      AS `salesName`,
    `b`.`weight`           AS `entryWeight`,
    concat(
        `b`.`kfPeriod`,
        `type`.`TYPENAME`
    )                      AS `kfDateType`,
    `b`.`kfDateType`       AS `KFDateType_`,
    `b`.`periodDate`       AS `periodDate`,
    `b`.`costprice`        AS `costPrice`,
    `b`.`costamount`       AS `costAmount`,
    `b`.`freeGifts`        AS `freeGifts`,
    `b`.`commitqty`        AS `commitQty`,
    `bill`.`bill_name`     AS `entryClassIdSrc`,
    `b`.`id_SRC`           AS `idSrc`,
    `b`.`billNo_SRC`       AS `entryBillNoSrc`,
    `b`.`entryId_SRC`      AS `entryIdSrc`,
    `b`.`id_Order`         AS `idOrder`,
    `b`.`billNo_Order`     AS `billNoOrder`,
    `b`.`entryId_Order`    AS `entryIdOrder`,
    `b`.`note`             AS `note`,
    `b`.`findex`           AS `findex`,
    `i`.`name`             AS `entryStockName`,
    `c`.`Name`             AS `entryItemName`,
    `c`.`Number`           AS `entryItemNo`,
    `c`.`Model`            AS `Model`,
    `d`.`BarCode`          AS `barCode`,
    `f`.`name`             AS `unitName`,
    `h`.`name`             AS `basicUnitName`,
    `k`.`name`             AS `secUnitName`,
    `g`.`Coefficient`      AS `basicCoefficient`,
    `d`.`CGLimitPrice`     AS `CGLimitPrice`,
    `q`.`XSLimitPrice`     AS `XSLimitPrice`,
    `c`.`ISKFPeriod`       AS `ISKFPeriod`,
    `c`.`BatchManager`     AS `BatchManager`,
    `c`.`Weight`           AS `itemWeight`,
    `trantype`.`bill_name` AS `tranTypeName`,
    `o`.`mallorderid`      AS `mallorderid`,
    a.SYSSTATE,
    (
      CASE
      WHEN (
        `b`.`basicqty` <= ifnull(`b`.`commitqty`, 0)
      )
        THEN
          1
      ELSE
        0
      END
    )                      AS `isStock`,
    (
      CASE
      WHEN (
        (
          `inv`.`basicQty` - `b`.`basicqty`
        ) >= 0
      )
        THEN
          1
      ELSE
        0
      END
    )                      AS `isAudit`
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
                                                                                        (
                                                                                            (
                                                                                                `t_sc_sl_stockbill` `a`
                                                                                                JOIN
                                                                                                `t_sc_sl_stockbillentry` `b`
                                                                                                  ON ((`a`.`id` =
                                                                                                       `b`.`fid`))
                                                                                              )
                                                                                            LEFT JOIN `t_sc_icitem` `c`
                                                                                              ON ((`b`.`itemId` =
                                                                                                   `c`.`id`))
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
                                                                    LEFT JOIN `t_sc_measureunit` `k`
                                                                      ON ((`j`.`UnitID` = `k`.`id`))
                                                                  )
                                                                LEFT JOIN `t_sc_stock` `i`
                                                                  ON ((`b`.`stockId` = `i`.`id`))
                                                              )
                                                            LEFT JOIN `t_s_base_user` `u`
                                                              ON ((`a`.`billerID` = `u`.`ID`))
                                                          )
                                                        LEFT JOIN `t_sc_organization` `s` ON ((`a`.`itemId` = `s`.`id`))
                                                      )
                                                    LEFT JOIN `t_sc_emp` `emp` ON ((`a`.`empId` = `emp`.`id`))
                                                  )
                                                LEFT JOIN `t_sc_department` `dept` ON ((`a`.`deptId` = `dept`.`id`))
                                              )
                                            LEFT JOIN `t_sc_stock` `stock` ON (
                                            (`a`.`stockId` = `stock`.`id`)
                                            )
                                          )
                                        LEFT JOIN `t_sc_settleacct` `se` ON (
                                        (`a`.`accountId` = `se`.`id`)
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
                        LEFT JOIN `t_s_bill_info` `bill` ON (
                        (
                          `b`.`classID_SRC` = `bill`.`bill_id`
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
                LEFT JOIN `t_s_base_user` `checker` ON (
                (
                  `a`.`checkerId` = `checker`.`ID`
                )
                )
              )
            LEFT JOIN `t_s_bill_info` `trantype` ON (
            (
              `a`.`tranType` = `trantype`.`bill_id`
            )
            )
          )
        LEFT JOIN `t_sc_order` `o` ON ((`o`.`id` = `a`.`id_SRC`))
    )
  ORDER BY
    `a`.`date` DESC,
    `a`.`billNo` DESC,
    `b`.`findex`$$

-- ----------------------------
-- View structure for v_sc_sl_stockbill_info
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_sl_stockbill_info`$$
-- DROP VIEW IF EXISTS `v_sc_sl_stockbill_info`$$
CREATE or REPLACE VIEW `v_sc_sl_stockbill_info` AS SELECT
                                          `a`.`id`                     AS `id`,
                                          `a`.`billNo`                 AS `billNo`,
                                          `a`.`date`                   AS `date`,
                                          `a`.`allamount`              AS `allamount`,
                                          ifnull(`a`.`checkamount`, 0) AS `checkamount`,
                                          `c`.`name`                   AS `itemName`,
                                          `a`.`sonId`                  AS `sonId`,
                                          `a`.`tranType`               AS `tranType`,
                                          `d`.`bill_name`              AS `typeName`,
                                          `a`.`itemId`                 AS `itemId`,
                                          `a`.`id_SRC`                 AS `idSrc`
                                        FROM (((`t_sc_sl_stockbill` `a` LEFT JOIN `t_sc_organization` `b`
                                            ON ((`a`.`itemId` = `b`.`id`))) LEFT JOIN `t_sc_organization` `c`
                                            ON ((`b`.`settlecompany` = `c`.`id`))) LEFT JOIN `t_s_bill_info` `d`
                                            ON ((`a`.`tranType` = `d`.`bill_id`)))
                                        WHERE (
                                          (`a`.`checkState` = 2) AND (`a`.`tranType` = 103) AND (`a`.`cancellation` = 0)
                                          AND (`a`.`checkamount` < `a`.`allamount`))
                                        UNION SELECT
                                                `a`.`id`                     AS `id`,
                                                `a`.`billNo`                 AS `billNo`,
                                                `a`.`date`                   AS `date`,
                                                (-(1) * `a`.`allamount`)     AS `allamount`,
                                                ifnull(`a`.`checkamount`, 0) AS `checkamount`,
                                                `c`.`name`                   AS `itemName`,
                                                `a`.`sonId`                  AS `sonId`,
                                                `a`.`tranType`               AS `tranType`,
                                                `d`.`bill_name`              AS `typeName`,
                                                `a`.`itemId`                 AS `itemId`,
                                                `a`.`id_SRC`                 AS `idSrc`
                                              FROM (((`t_sc_sl_stockbill` `a` LEFT JOIN `t_sc_organization` `b`
                                                  ON ((`a`.`itemId` = `b`.`id`))) LEFT JOIN `t_sc_organization` `c`
                                                  ON ((`b`.`settlecompany` = `c`.`id`))) LEFT JOIN `t_s_bill_info` `d`
                                                  ON ((`a`.`tranType` = `d`.`bill_id`)))
                                              WHERE ((`a`.`checkState` = 2) AND (`a`.`tranType` = 104) AND
                                                     (`a`.`cancellation` = 0) AND (`a`.`checkamount` < `a`.`allamount`))
                                        UNION SELECT
                                                `a`.`id`                     AS `id`,
                                                `a`.`billNo`                 AS `billNo`,
                                                `a`.`date`                   AS `date`,
                                                `a`.`allamount`              AS `allamount`,
                                                ifnull(`a`.`checkAmount`, 0) AS `checkamount`,
                                                `c`.`name`                   AS `itemName`,
                                                `a`.`sonId`                  AS `sonId`,
                                                `a`.`tranType`               AS `tranType`,
                                                `d`.`bill_name`              AS `typeName`,
                                                `a`.`itemId`                 AS `itemId`,
                                                ''                           AS `idSrc`
                                              FROM (((`t_sc_rp_adjustbill` `a` LEFT JOIN `t_sc_organization` `b`
                                                  ON ((`a`.`itemId` = `b`.`id`))) LEFT JOIN `t_sc_organization` `c`
                                                  ON ((`b`.`settlecompany` = `c`.`id`))) LEFT JOIN `t_s_bill_info` `d`
                                                  ON ((`a`.`tranType` = `d`.`bill_id`)))
                                              WHERE ((`a`.`tranType` = 201) AND (`a`.`checkstate` = 2) AND
                                                     (`a`.`cancellation` = 0) AND (`a`.`checkAmount` < `a`.`allamount`))
                                        UNION SELECT
                                                `a`.`id`                     AS `id`,
                                                `a`.`billNo`                 AS `billNo`,
                                                `a`.`date`                   AS `date`,
                                                `a`.`allAmount`              AS `allamount`,
                                                ifnull(`a`.`checkAmount`, 0) AS `checkamount`,
                                                `c`.`name`                   AS `itemName`,
                                                `a`.`sonId`                  AS `sonId`,
                                                `a`.`tranType`               AS `tranType`,
                                                `d`.`bill_name`              AS `typeName`,
                                                `a`.`itemId`                 AS `itemId`,
                                                `a`.`id_SRC`                 AS `idSrc`
                                              FROM (((`t_sc_ic_xsstockbill` `a` LEFT JOIN `t_sc_organization` `b`
                                                  ON ((`a`.`itemId` = `b`.`id`))) LEFT JOIN `t_sc_organization` `c`
                                                  ON ((`b`.`settlecompany` = `c`.`id`))) LEFT JOIN `t_s_bill_info` `d`
                                                  ON ((`a`.`tranType` = `d`.`bill_id`)))
                                              WHERE ((`a`.`checkState` = 2) AND (`a`.`cancellation` = 0) AND
                                                     (`a`.`checkAmount` < `a`.`allAmount`))
                                        UNION SELECT
                                                `a`.`id`                     AS `id`,
                                                `a`.`BillNo`                 AS `billNo`,
                                                `a`.`Date`                   AS `date`,
                                                `a`.`allamount`              AS `allamount`,
                                                ifnull(`a`.`checkamount`, 0) AS `checkamount`,
                                                `c`.`name`                   AS `itemName`,
                                                `a`.`sonid`                  AS `sonId`,
                                                `a`.`trantype`               AS `tranType`,
                                                `d`.`bill_name`              AS `typeName`,
                                                `a`.`ItemID`                 AS `ItemID`,
                                                ''                           AS `idSrc`
                                              FROM (((`t_sc_begdata` `a` LEFT JOIN `t_sc_organization` `b`
                                                  ON ((`a`.`ItemID` = `b`.`id`))) LEFT JOIN `t_sc_organization` `c`
                                                  ON ((`b`.`settlecompany` = `c`.`id`))) LEFT JOIN `t_s_bill_info` `d`
                                                  ON ((`a`.`trantype` = `d`.`bill_id`)))
                                              WHERE ((`a`.`trantype` = 1030) AND (`a`.`checkstate` = 2) AND
                                                     (`a`.`checkamount` < `a`.`allamount`))$$

-- ----------------------------
-- View structure for v_sc_ic_bal
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_ic_bal`$$
-- DROP VIEW IF EXISTS `v_sc_ic_bal`$$
CREATE or REPLACE VIEW `v_sc_ic_bal` as
  select b.`NAME` as itemname,c.`NAME` as stockname,d.DEPARTNAME,a.*
  from t_sc_ic_bal a
    left JOIN t_sc_icitem b on a.itemid=b.ID
    left join t_sc_stock c on a.stockid=c.ID
    left join t_s_depart d on a.sonid=d.id $$

-- ----------------------------
-- View structure for v_sc_rp_contactbal
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_rp_contactbal`$$
-- DROP VIEW IF EXISTS `v_sc_rp_contactbal`$$
CREATE or REPLACE VIEW `v_sc_rp_contactbal` as
  -- 应收是客户t_Sc_Organization，应付是供应商t_Sc_Supplier
  select '应付' as rpname,b.`NAME`,c.`NAME` as departname,d.`NAME` as empname,a.*
  from t_sc_rp_contactbal a
    left JOIN t_Sc_Supplier b on a.itemid=b.id
    left join t_sc_department c on a.deptid=c.ID
    left join t_sc_emp d on a.EMPID=d.id
  where a.rp='0'
  UNION
  select '应收' as rpname,b.`NAME`,c.`NAME` as departname,d.`NAME` as empname,a.*
  from t_sc_rp_contactbal a
    left JOIN t_Sc_Organization b on a.itemid=b.id
    left join t_sc_department c on a.deptid=c.ID
    left join t_sc_emp d on a.EMPID=d.id
  where a.rp='1'$$

-- ----------------------------
-- View structure for v_Sc_Rp_Expbal
-- ----------------------------
DROP TABLE IF EXISTS `v_Sc_Rp_Expbal`$$
-- DROP VIEW IF EXISTS `v_Sc_Rp_Expbal`$$
CREATE or REPLACE VIEW `v_Sc_Rp_Expbal` as
  select b.`NAME`,c.`NAME` as departname,d.`NAME` as empname,a.*
  from t_Sc_Rp_Expbal a
    LEFT JOIN t_Sc_Settleacct b on a.ACCOUNTID=b.id
    left join t_sc_department c on a.deptid=c.ID
    left join t_sc_emp d on a.EMPID=d.id $$

-- ----------------------------
-- View structure for v_sc_organization
-- ----------------------------
DROP TABLE IF EXISTS `v_sc_organization`$$
-- DROP VIEW IF EXISTS `v_sc_organization`$$
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
DROP TABLE IF EXISTS `v_sc_begdata`$$
-- DROP VIEW IF EXISTS `v_sc_begdata`$$
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
DROP TABLE IF EXISTS `v_sc_begdataincomepay`$$
-- DROP VIEW IF EXISTS `v_sc_begdataincomepay`$$
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
DROP TABLE IF EXISTS `v_sc_begdatapayable`$$
-- DROP VIEW IF EXISTS `v_sc_begdatapayable`$$
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



   DROP TABLE IF EXISTS `v_sc_organization`$$
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