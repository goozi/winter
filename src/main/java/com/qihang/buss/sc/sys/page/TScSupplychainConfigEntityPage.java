package com.qihang.buss.sc.sys.page;

/**
 * Created by Administrator on 2016-06-16.
 * jsp 供应链设置页面传值用
 */

public class TScSupplychainConfigEntityPage {
    /**允许负库存结账*/
    private String minusInventoryAccount;
    /**允许负库存出库*/
    private String minusInventorySl;
    /**允许盘点有未审核存货单据的数据*/
    private String stocktakingNotAuditedStockBill;
    /**单据保存时系统自动审核*/
    private String billSaveSystemWithExamine;
    /**单据审核时系统自带后续业务单据*/
    private String billExamineSystemWithFollow;
    /**不允许手工开入库单*/
    private String cannotManualOpenInRepertory;
    /**不允许入库单数量大于采购订单数量*/
    private String cannotInRepertoryngtPurchasen;
    /**采购模块启用价格调用顺序*/
    private String purchaseStartPriceCallOrder;
    /**采购设置下拉框一*/
    private String purchaseselectOne;
    /**采购设置下拉框二*/
    private String purchaseselectTwo;
    /**不允许手工开出库单*/
    private String cannotManualOpenOutRepertory;
    /**不允许出库单数量大于销售订单数量*/
    private String cannotOutRepertoryngtSale;
    /**销售模块启用价格调用顺序*/
    private String saleStartPriceCallOrder;
    /**销售设置下拉框一*/
    private String saleSelectOne;
    /**销售设置下拉框二*/
    private String saleSelectTwo;
    /**销售设置下拉框三*/
    private String saleSelectThree;

    public String getMinusInventoryAccount() {
        return minusInventoryAccount;
    }

    public void setMinusInventoryAccount(String minusInventoryAccount) {
        this.minusInventoryAccount = minusInventoryAccount;
    }

    public String getMinusInventorySl() {
        return minusInventorySl;
    }

    public void setMinusInventorySl(String minusInventorySl) {
        this.minusInventorySl = minusInventorySl;
    }

    public String getStocktakingNotAuditedStockBill() {
        return stocktakingNotAuditedStockBill;
    }

    public void setStocktakingNotAuditedStockBill(String stocktakingNotAuditedStockBill) {
        this.stocktakingNotAuditedStockBill = stocktakingNotAuditedStockBill;
    }

    public String getBillSaveSystemWithExamine() {
        return billSaveSystemWithExamine;
    }

    public void setBillSaveSystemWithExamine(String billSaveSystemWithExamine) {
        this.billSaveSystemWithExamine = billSaveSystemWithExamine;
    }

    public String getBillExamineSystemWithFollow() {
        return billExamineSystemWithFollow;
    }

    public void setBillExamineSystemWithFollow(String billExamineSystemWithFollow) {
        this.billExamineSystemWithFollow = billExamineSystemWithFollow;
    }

    public String getCannotManualOpenInRepertory() {
        return cannotManualOpenInRepertory;
    }

    public void setCannotManualOpenInRepertory(String cannotManualOpenInRepertory) {
        this.cannotManualOpenInRepertory = cannotManualOpenInRepertory;
    }

    public String getCannotInRepertoryngtPurchasen() {
        return cannotInRepertoryngtPurchasen;
    }

    public void setCannotInRepertoryngtPurchasen(String cannotInRepertoryngtPurchasen) {
        this.cannotInRepertoryngtPurchasen = cannotInRepertoryngtPurchasen;
    }

    public String getPurchaseStartPriceCallOrder() {
        return purchaseStartPriceCallOrder;
    }

    public void setPurchaseStartPriceCallOrder(String purchaseStartPriceCallOrder) {
        this.purchaseStartPriceCallOrder = purchaseStartPriceCallOrder;
    }

    public String getPurchaseselectOne() {
        return purchaseselectOne;
    }

    public void setPurchaseselectOne(String purchaseselectOne) {
        this.purchaseselectOne = purchaseselectOne;
    }

    public String getPurchaseselectTwo() {
        return purchaseselectTwo;
    }

    public void setPurchaseselectTwo(String purchaseselectTwo) {
        this.purchaseselectTwo = purchaseselectTwo;
    }

    public String getCannotManualOpenOutRepertory() {
        return cannotManualOpenOutRepertory;
    }

    public void setCannotManualOpenOutRepertory(String cannotManualOpenOutRepertory) {
        this.cannotManualOpenOutRepertory = cannotManualOpenOutRepertory;
    }

    public String getCannotOutRepertoryngtSale() {
        return cannotOutRepertoryngtSale;
    }

    public void setCannotOutRepertoryngtSale(String cannotOutRepertoryngtSale) {
        this.cannotOutRepertoryngtSale = cannotOutRepertoryngtSale;
    }

    public String getSaleStartPriceCallOrder() {
        return saleStartPriceCallOrder;
    }

    public void setSaleStartPriceCallOrder(String saleStartPriceCallOrder) {
        this.saleStartPriceCallOrder = saleStartPriceCallOrder;
    }

    public String getSaleSelectOne() {
        return saleSelectOne;
    }

    public void setSaleSelectOne(String saleSelectOne) {
        this.saleSelectOne = saleSelectOne;
    }

    public String getSaleSelectTwo() {
        return saleSelectTwo;
    }

    public void setSaleSelectTwo(String saleSelectTwo) {
        this.saleSelectTwo = saleSelectTwo;
    }

    public String getSaleSelectThree() {
        return saleSelectThree;
    }

    public void setSaleSelectThree(String saleSelectThree) {
        this.saleSelectThree = saleSelectThree;
    }
}