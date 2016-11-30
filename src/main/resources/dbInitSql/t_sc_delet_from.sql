DELIMITER $$
delete from t_sc_aptitude $$
delete from T_SC_BAL $$
delete from T_SC_BEGDATA $$
delete from T_SC_BILL_TEMP $$
delete from T_SC_CONTACT $$
delete from T_SC_ContactBal $$
delete from T_SC_Contract $$
delete from t_sc_daily $$
delete from T_SC_Department $$
delete from T_SC_DOCUMENT $$
delete from T_SC_DRP_Logistical $$
delete from T_SC_DRP_ORDER $$
delete from T_SC_DRP_OrderEntry $$
delete from T_SC_DRP_RStockBill $$
delete from T_SC_DRP_RStockBillEntry $$
delete from T_SC_DRP_StockBill $$
delete from T_SC_DRP_StockBillEntry $$
delete from T_SC_EMP $$
delete from T_SC_EXPBAL $$
delete from T_SC_Fee $$
delete from T_SC_FILE $$
delete from T_SC_IC_ChkStockBill $$
delete from T_SC_IC_ChkStockBillEntry $$
delete from T_SC_IC_Initial $$
delete from T_SC_IC_InitialEntry $$
delete from T_SC_IC_Inventory $$
delete from T_SC_IC_Inventory_BatchNo $$
delete from T_SC_IC_JHStockBill $$
delete from T_SC_IC_JHStockBillEntry1 $$
delete from T_SC_IC_JHStockBillEntry2 $$
delete from T_SC_IC_SpeedBal $$
delete from T_SC_IC_XsStockBill $$
delete from T_SC_IC_XsStockBillEntry1 $$
delete from T_SC_IC_XsStockBillEntry2 $$
delete from T_SC_ICItem $$
delete from T_SC_Item_Photo $$
delete from T_SC_Item_Price $$
delete from T_SC_ITEM_TYPE where parent_id!='00000' $$
delete from T_SC_LOGISTICAL $$
delete from T_SC_MeasureUnit $$
delete from T_SC_MESSAGE $$
delete from T_SC_MESSAGE_FILE $$
delete from T_SC_MESSAGE_RECEIVE $$
delete from T_SC_NEWS $$
delete from T_SC_NEWS_RELATION $$
delete from T_SC_NOTICE $$
delete from t_sc_notice_file $$
delete from t_sc_notice_relation $$
delete from T_SC_Order $$
delete from T_SC_Order_Entry $$
delete from T_SC_Organization $$
delete from T_SC_PLAN $$
delete from T_sc_PO_Order $$
delete from T_sc_PO_OrderEntry $$
delete from T_SC_Po_StockBill $$
delete from T_SC_Po_StockBillEntry $$
delete from T_SC_PRCPLY $$
delete from T_SC_PRCPLYENTRY1 $$
delete from T_SC_PRCPLYENTRY2 $$
delete from T_SC_Promotion $$
delete from T_SC_PromotionGiftsEntry $$
delete from T_SC_PromotionGoodsEntry $$
delete from T_SC_Quote $$
delete from T_SC_QuoteCustomer $$
delete from T_SC_QuoteItems $$
delete from T_SC_RP_AdjustBill $$
delete from T_SC_RP_AdjustBillEntry $$
delete from t_sc_rp_bankbill $$
delete from t_sc_rp_expensesapply $$
delete from t_sc_rp_expensesapplyEntry $$
delete from t_sc_rp_pbill $$
delete from t_sc_rp_pbillentry1 $$
delete from t_sc_rp_pbillentry2 $$
delete from t_sc_rp_potherbill $$
delete from t_sc_rp_potherbillentry $$
delete from T_SC_RP_RBill $$
delete from T_SC_RP_RBillEntry1 $$
delete from T_SC_RP_RBillEntry2 $$
delete from t_sc_rp_rotherbill $$
delete from t_sc_rp_rotherbillEntry $$
delete from T_SC_SettleAcct $$
delete from T_SC_SL_Logistical $$
delete from t_sc_sl_stockbill $$
delete from t_sc_sl_stockbillentry $$
delete from T_SC_SonCompany $$
delete from T_SC_Stock $$
delete from T_SC_SUPPLIER $$
delete from t_s_depart $$
delete from t_s_base_user where not USERNAME in ('programmer','scadmin','admin') $$
delete from t_s_user_org
where not exists(select 1 from t_s_base_user a where USER_ID=a.id and a.USERNAME in ('programmer','scadmin','admin')) $$
delete from t_s_config  $$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('1', 'CFG_PAGE', '10', '分页记录', '分页记录', '1', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('10', 'CFG_CONTROL_METHOD', '0', '控制方式', '控制方式', '10', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('11', 'CFG_CONTROL_TIMEPOINT', '1', '控制时点', '控制时点', '11', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('12', 'CFG_RECEARWAR_DAYS', '5', '应收预警天数', '应收预警天数', '12', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('13', 'CFG_PRUORDEARWAR_DAYS', '5', '采购订单预警天数', '采购订单预警天数', '13', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('14', 'CFG_PAYEARWAR_DAYS', '5', '应付预警天数', '应付预警天数', '14', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('15', 'CFG_SALORDEARWAR_DAYS', '5', '销售订单预警天数', '销售订单预警天数', '15', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('16', 'CFG_SHELFLIFEEARWAR_DAYS', '5', '保质期预警天数', '保质期预警天数', '16', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('17', 'CFG_SYSLOGHOLD_DAYS', '30', '系统日志保留天数', '系统日志保留天数', '17', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('18', 'CFG_PRICE_ZERO', '1', '订单单价为0提示', '订单单价为0提示', NULL, '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('19', 'CFG_CHKSTOCKBILL_NEED_AUDIT', '1', '是否要管控有未审核存货单据', '是否要管控有未审核存货单据', NULL, '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('2', 'CFG_ROW_HEIGHT', '20', '行高', '行高', '2', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('20', 'CFG_SALES_PRI_ONE', NULL, '销售单价第一优先', '销售单价第一优先', '4028809d552970c20155298824200003', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('21', 'CFG_SALES_PRI_SEC', NULL, '销售单价第二优先', '销售单价第二优先', '4028809d552970c20155298824200003', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('22', 'CFG_SALES_PRI_THI', NULL, '销售单价第三优先', '销售单价第三优先', '4028809d552970c20155298824200003', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('23', 'CFG_PURCHASE_PRI_ONE', NULL, '采购单价第一优先', '采购单价第一优先', '4028809d552970c20155298824200003', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('24', 'CFG_PURCHASE_PRI_SEC', NULL, '采购单价第二优先', '采购单价第二优先', '4028809d552970c20155298824200003', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('25', 'CFG_SALES_ENABLE_PRI_SEQ', '0', '销售模块启用价格调用顺序', '销售模块启用价格调用顺序', '4028809d552970c20155298824200003', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('26', 'CFG_PURCHASE_ENABLE_PRI_SEQ', '0', '采购模块启用价格调用顺序', '采购模块启用价格调用顺序', '4028809d552970c20155298824200003', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('27', 'CFG_MINUSINVENTORY_ACCOUNT', '0', '允许负库存结账', '允许负库存结账', '27', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('28', 'CFG_MINUSINVENTORYSL', '0', '允许负库存出库', '允许负库存出库', '28', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('29', 'CFG_STOCKTAKINGNOTAUDITEDSTOCK_BILL', '0', '允许盘点有未审核存货单据的数据', '允许盘点有未审核存货单据的数据', '29', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('3', 'CFG_TAX_RATE', '17', '默认税率', '默认税率', '3', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('30', 'CFG_BILLSAVESYSTEMWITH_EXAMINE', '0', '单据保存时系统自动审核', '单据保存时系统自动审核', '30', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('31', 'CFG_BILLEXAMINESYSTEMWITH_FOLLOW', '0', '单据审核时系统自带后续业务单据', '单据审核时系统自带后续业务单据', '31', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('32', 'CFG_CANNOTMANUALOPENIN_REPERTORY', '0', '不允许手工开入库单', '不允许手工开入库单', '32', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('33', 'CFG_CANNOTINREPERTORYNYT_PURCHASEN', '0', '不允许入库单数量大于采购订单数量', '不允许入库单数量大于采购订单数量', '33', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('34', 'CFG_CANNOTMANUALOPENOUT_REPERTORY', '0', '不允许手工开出库单', '不允许手工开出库单', '34', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('35', 'CFG_CANNOTOUTREPERTORYNGT_SALE', '0', '不允许出库单数量大于销售订单数量', '不允许出库单数量大于销售订单数量', '35', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('4', 'CFG_NUMBER', '2', '数量', '数量', '4', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('5', 'CFG_UNITP_RICE', '2', '单价', '单价', '5', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('6', 'CFG_MONEY', '2', '金额', '金额', '6', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('7', 'CFG_RATES', '2', '税率', '税率', '7', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('8', 'CFG_DISCOUNT_RATE', '2', '折扣率', '折扣率', '8', '402880fd574066fe01574068ac400001')$$
INSERT INTO t_s_config (`ID`, `CODE`, `CONTENT`, `NAME`, `NOTE`, `USERID`, `accountid`) VALUES ('9', 'CFG_OTHER', '2', '其他', '其他', '9', '402880fd574066fe01574068ac400001')$$
delete from t_s_audit $$
delete from t_s_audit_leave $$
delete from t_s_audit_relation $$