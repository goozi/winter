/**
 * 供业务单据页面进行数量金额等计算的公共脚本库
 * @Athor Zerrion
 * @Date  2016-06-16
 */

/**
 * 计算类
 * @param options
 * @constructor
 */
function ComputingTools(options) {
  this._opt = {
    qty: 0,//数量
    basicQty: 0,//基本数量
    secQty: 0,//辅助数量
    coefficient: 0.00,//换算率
    secCoefficient: 0.00,//辅助换算率
    price: 0.00,//不含税单价
    amount: 0.00,//不含税金额
    discountRate: 100,//折扣率
    discountPrice: 0.00,//不含税折后单价
    discountAmount: 0.00,//不含税折后金额
    taxRate: 0,//税率
    taxPriceEx: 0.00,//单价（也即含税单价）
    taxAmountEx: 0.00,//金额（也即含税金额）
    taxPrice: 0.00,//折后单价
    inTaxAmount: 0.00,//折后金额
    taxAmount: 0.00,//税额
    weight: 0.00,//重量
    itemWeight: 0.00//商品重量
  };
  this.init(options);
}
ComputingTools.prototype = {
  init: function (options) {
    this._opt = $.extend(this._opt, options);
  },
  //基本数量[BasicQty]=数量*换算率
  computeBasicQty: function () {
    this._opt.basicQty = this._opt.qty * this._opt.coefficient;
  },

// 辅助数量[SecQty]= (数量*换算率)/辅助换算率
  computeSecQty: function () {
    this._opt.secQty = (this._opt.qty * this._opt.coefficient) / this._opt.secCoefficient;
  },

// 不含税单价[Price]=单价/((1+税率)/100)；
  computePrice: function () {
    this._opt.price = this._opt.taxPriceEx / ((100 + parseFloat(this._opt.taxRate)) / 100);
  },

// 不含税金额[Amount]=单价/(1+税率)/100*数量；
  computeAmount: function () {
    this._opt.amount = this._opt.taxPriceEx / ((100 + parseFloat(this._opt.taxRate)) / 100) * this._opt.qty;
  },

// 折扣率[DiscountRate]= 折后金额/数量/单价*100；
  computeDiscountRate: function () {
    if(this._opt.taxPriceEx == 0){
      //this._opt.discountRate = 100;
    }else {
      this._opt.discountRate = this._opt.inTaxAmount / this._opt.qty / this._opt.taxPriceEx * 100;
    }
  },
// 不含税折后单价[DiscountPrice]=单价/(1+税率)/100*折扣率/100 或 不含税单价*折后金额/数量/单价；
  computeDiscountPrice: function () {
    if (this._opt.inTaxAmount == 0) {
      this._opt.discountPrice = this._opt.taxPriceEx / ((100 + parseFloat(this._opt.taxRate)) / 100) * this._opt.discountRate / 100;
    } else {
      this._opt.discountPrice = this._opt.price * this._opt.inTaxAmount / this._opt.qty / this._opt.taxPriceEx;
    }
  },

// 不含税折后金额[DiscountAmount]= 单价/(1+税率)/100*折扣率/100*数量 或 折后金额/(1+税率)/100；
  computeDiscountAmount: function () {
    if (this._opt.inTaxAmount == 0) {
      this._opt.discountAmount = this._opt.taxPriceEx / ((100 + parseFloat(this._opt.taxRate)) / 100) * this._opt.discountRate / 100 * this._opt.qty;
    } else {
      this._opt.discountAmount = this._opt.inTaxAmount / ((100 + parseFloat(this._opt.taxRate)) / 100);
    }
  },

// 单价[TaxPriceEx即含税单价]=金额/数量；
  computeTaxPriceEx: function () {
    this._opt.taxPriceEx = this._opt.taxAmountEx / this._opt.qty;
  },

// 金额[Amount即含税金额]=单价*数量；
  computeAmountEx: function () {
    this._opt.taxAmountEx = this._opt.taxPriceEx * this._opt.qty;
  },

// 折后单价[TaxPrice]=单价*折扣率/100 或 折后金额/数量；
  computeTaxPrice: function () {
    if (this._opt.inTaxAmount == 0) {
      this._opt.taxPrice = this._opt.taxPriceEx * this._opt.discountRate / 100;
    } else {
      this._opt.taxPrice = this._opt.inTaxAmount / this._opt.qty;
    }
  },

// 折后金额[InTaxAmount]=单价*折扣率/100*数量；
  computeInTaxAmount: function () {
    this._opt.inTaxAmount = this._opt.taxPriceEx * this._opt.discountRate / 100 * this._opt.qty;
  },

// 税额[TaxAmount]=(单价-不含税单价)*数量
  computeTaxAmount: function () {
    // this._opt.taxAmount = (this._opt.taxPriceEx - this._opt.price) * this._opt.qty;//该算法有BUG，如果只改变税率则单价不会变化导致税额也不变化
    this._opt.taxAmount = (this._opt.taxAmountEx - this._opt.amount) * this._opt.discountRate / 100;
  },
  //重量[weight]=商品重量*数量
  computeWeight: function () {
    this._opt.weight = this._opt.itemWeight * this._opt.qty;
  },
  /**
   * 累计子表中某列并赋值给主表对应字段
   * @param itemName 子表列中输入框name
   * @param mainItemId 主表对应输入框id
   */
  sumItem: function (itemName, mainItemId) {
    var amount = 0;
    $('input[name$=".' + itemName + '"]').each(function () {
      amount += parseFloat($(this).val()) || 0;
    });
    var temp = $('#' + mainItemId);
    //debugger;
    var CFG_MONEY = $("#CFG_MONEY").val();
    amount = amount.toFixed(CFG_MONEY);
    temp.val(amount);
    temp.change();
  }
};


//$(function () {
  /*var qty = parseFloat($('#qty').val()) || 0;
  var basicQty = parseFloat($('#basicQty').val()) || 0;
  var secQty = parseFloat($('#secQty').val()) || 0;
  var coefficient = parseFloat($('#coefficient').val()) || 0;
  var secCoefficient = parseFloat($('#secCoefficient').val()) || 0;
  var price = parseFloat($('#price').val()) || 0;
  var amount = parseFloat($('#amount').val()) || 0;
  var discountRate = parseFloat($('#discountRate').val()) || 0;
  var discountPrice = parseFloat($('#discountPrice').val()) || 0;
  var discountAmount = parseFloat($('#discountAmount').val()) || 0;
  var taxRate = parseFloat($('#taxRate').val()) || 0;
  var taxPriceEx = parseFloat($('#taxPriceEx').val()) || 0;
  var taxAmountEx = parseFloat($('#taxAmountEx').val()) || 0;
  var taxPrice = parseFloat($('#taxPrice').val()) || 0;
  var inTaxAmount = parseFloat($('#inTaxAmount').val()) || 0;
  var taxAmount = parseFloat($('#taxAmount').val()) || 0;
  var weight = parseFloat($('#weight').val()) || 0;
  var itemWeight = parseFloat($('#itemWeight').val()) || 0;

  var options = {
    qty: qty,
    basicQty: 0,
    secQty: 0,
    coefficient: coefficient,
    secCoefficient: secCoefficient,
    price: 0,
    amount: 0,
    discountRate: discountRate,
    discountPrice: 0,
    discountAmount: 0,
    taxRate: taxRate,
    taxPriceEx: taxPriceEx,
    taxAmountEx: taxAmountEx,
    taxPrice: 0,
    inTaxAmount: inTaxAmount,
    taxAmount: 0,
    weight: 0,
    itemWeight: itemWeight
  };

  var ct = new ComputingTools(options);*/
  //初始计算金额相关工具类
  //initComputingTools();

  /**
   * 计算前先检查
   * 数量和金额至少一项有值
   * 换算率需有值
   * @returns {boolean}
   */
  //function beforeCheck() {
  //  if (ct._opt.taxPriceEx == 0 && ct._opt.taxAmountEx == 0) {
  //    tip('单价及金额至少需一项有值！');
  //    return false;
  //  } else if (ct._opt.coefficient == 0) {
  //    tip('请先设置该商品换算率！');
  //    return false;
  //  } else {
  //    return true;
  //  }
  //}

//  $('#qty, #basicQty, #secQty, #coefficient, #secCoefficient, #price, #amount, #discountRate, #discountPrice, #discountAmount, #taxRate, #taxPriceEx, #taxAmountEx, #taxPrice, #inTaxAmount, #taxAmount, #itemWeight').on('input change', function (event) {
//    var obj = event.currentTarget;
//    var val = parseFloat(obj.value) || 0;
//    var id = obj.id;
//    ct._opt[id] = val;
//
//    if (!beforeCheck()) {
//      return false;
//    }
//
//    ct.computeBasicQty();
//    ct.computeSecQty();
//
//    if (id == 'taxAmountEx') {//金额改变时
//      ct.computeTaxPriceEx();
//      ct.computePrice();
//      ct.computeAmount();
//      ct.computeInTaxAmount();
//      ct.computeDiscountRate();
//      ct.computeDiscountPrice();
//      ct.computeDiscountAmount();
//      ct.computeAmountEx();
//      ct.computeTaxPrice();
//      ct.computeTaxAmount();
//    } else if (id == 'inTaxAmount') {//折后金额改变时
//      ct.computeTaxPrice();//重新计算折后单价
//      ct.computeTaxAmount();//税额
//    } else if (id == 'itemWeight') {
//      ct.computeWeight();
//    } else {
//      ct.computePrice();//不含税单价
//      ct.computeAmount();//不含税金额
//      ct.computeInTaxAmount();//折后金额
//      ct.computeDiscountRate();//折扣率
//      ct.computeDiscountPrice();//折后单价
//      ct.computeDiscountAmount();//不含税折后金额
//      ct.computeAmountEx();//金额
//      ct.computeTaxPriceEx();//单价
//      ct.computeTaxPrice();//折后单价
//      ct.computeTaxAmount();//税额
//      ct.computeWeight();
//    }
//    for (var item in ct._opt) {
//      //当前触发对象则不重新赋值
//      if (item == id) {
//        continue;
//      }
//      //如果某ID输入框不存在则不赋值
//      if ($('#' + item).length <= 0) {
//        continue;
//      }
//      $('#' + item).val(ct._opt[item].toFixed(2));
//    }
//  });
//
//});

//初始计算工具类
function initComputingTools(){
  var qty = parseFloat($('#qty').val()) || 0;
  var basicQty = parseFloat($('#basicQty').val()) || 0;
  var secQty = parseFloat($('#secQty').val()) || 0;
  var coefficient = parseFloat($('#coefficient').val()) || 0;
  var secCoefficient = parseFloat($('#secCoefficient').val()) || 0;
  var price = parseFloat($('#price').val()) || 0;
  var amount = parseFloat($('#amount').val()) || 0;
  var discountRate = parseFloat($('#discountRate').val()) || 0;
  var discountPrice = parseFloat($('#discountPrice').val()) || 0;
  var discountAmount = parseFloat($('#discountAmount').val()) || 0;
  var taxRate = parseFloat($('#taxRate').val()) || 0;
  var taxPriceEx = parseFloat($('#taxPriceEx').val()) || 0;
  var taxAmountEx = parseFloat($('#taxAmountEx').val()) || 0;
  var taxPrice = parseFloat($('#taxPrice').val()) || 0;
  var inTaxAmount = parseFloat($('#inTaxAmount').val()) || 0;
  var taxAmount = parseFloat($('#taxAmount').val()) || 0;
  var weight = parseFloat($('#weight').val()) || 0;
  var itemWeight = parseFloat($('#itemWeight').val()) || 0;

  var options = {
    qty: qty,
    basicQty: 0,
    secQty: 0,
    coefficient: coefficient,
    secCoefficient: secCoefficient,
    price: 0,
    amount: 0,
    discountRate: discountRate,
    discountPrice: 0,
    discountAmount: 0,
    taxRate: taxRate,
    taxPriceEx: taxPriceEx,
    taxAmountEx: taxAmountEx,
    taxPrice: 0,
    inTaxAmount: inTaxAmount,
    taxAmount: 0,
    weight: 0,
    itemWeight: itemWeight
  };

  var ct = new ComputingTools(options);
}


//子表函数计算
function setFunctionInfo(index,configName) {
  if(!configName) {
    configName = $("#configName").val();
  }
  var CFG_MONEY = $("#CFG_MONEY").val();//金额位数
  var CFG_NUMBER = $("#CFG_NUMBER").val();//数量位数
  var CFG_OTHER = $("#CFG_OTHER").val();//其他位数
  var CFG_UNITP_RICE = $("#CFG_UNITP_RICE").val();//单价位数
  var CFG_RATES = $("#CFG_RATES").val();//税率位数
  var CFG_DISCOUNT_RATE = $("#CFG_DISCOUNT_RATE").val();//折扣率位数
  var sumAmount = $('#sumAmount').val();
  var qty = (parseFloat($("input[name='" + configName + "[" + index + "].qty']").val()) || 0).toFixed(CFG_NUMBER);
  var basicQty = (parseFloat($('input[name="' + configName + '[' + index + '].basicQty"]').val()) || 0).toFixed(CFG_NUMBER);
  var secQty = (parseFloat($('input[name="' + configName + '[' + index + '].secQty"]').val()) || 0).toFixed(CFG_NUMBER);
  var coefficient = (parseFloat($('input[name="' + configName + '[' + index + '].coefficient"]').val()) || 0).toFixed(CFG_OTHER);
  var secCoefficient = (parseFloat($('input[name="' + configName + '[' + index + '].secCoefficient"]').val()) || 0).toFixed(CFG_OTHER);
  var price = (parseFloat($('input[name="' + configName + '[' + index + '].price"]').val()) || 0).toFixed(CFG_UNITP_RICE);
  var amount = (parseFloat($('input[name="' + configName + '[' + index + '].amount"]').val()) || 0).toFixed(CFG_MONEY);
  //var discountRate = parseFloat($('input[name="' + configName + '[' + index + '].discountRate"]').val()) || 100;
  var discountRate = (parseFloat($('input[name="' + configName + '[' + index + '].discountRate"]').val()) == null ? 100 : parseFloat($('input[name="' + configName + '[' + index + '].discountRate"]').val())).toFixed(CFG_DISCOUNT_RATE);
  var discountPrice = (parseFloat($('input[name="' + configName + '[' + index + '].discountPrice"]').val()) || 0).toFixed(CFG_UNITP_RICE);
  var discountAmount = (parseFloat($('input[name="' + configName + '[' + index + '].discountAmount"]').val()) || 0).toFixed(CFG_MONEY);
  var taxRate = (parseFloat($('input[name="' + configName + '[' + index + '].taxRate"]').val()) || 0).toFixed(CFG_RATES);
  var taxPriceEx = (parseFloat($('input[name="' + configName + '[' + index + '].taxPriceEx"]').val()) || 0).toFixed(CFG_UNITP_RICE);
  var taxAmountEx = (parseFloat($('input[name="' + configName + '[' + index + '].taxAmountEx"]').val()) || 0).toFixed(CFG_MONEY);
  var taxPrice = (parseFloat($('input[name="' + configName + '[' + index + '].taxPrice"]').val()) || 0).toFixed(CFG_UNITP_RICE);
  var inTaxAmount = (parseFloat($('input[name="' + configName + '[' + index + '].inTaxAmount"]').val()) || 0).toFixed(CFG_MONEY);
  var taxAmount = (parseFloat($('input[name="' + configName + '[' + index + '].taxAmount"]').val()) || 0).toFixed(CFG_MONEY);
  var weight = (parseFloat($('input[name="' + configName + '[' + index + '].weight"]').val()) || 0).toFixed(CFG_OTHER);
  var itemWeight = (parseFloat($('input[name="' + configName + '[' + index + '].itemWeight"]').val()) || 0).toFixed(CFG_OTHER);

  var options = {
    qty: qty,
    basicQty: 0,
    secQty: 0,
    coefficient: coefficient,
    secCoefficient: secCoefficient,
    price: 0,
    amount: 0,
    discountRate: discountRate,
    discountPrice: 0,
    discountAmount: 0,
    taxRate: taxRate,
    taxPriceEx: taxPriceEx,
    taxAmountEx: taxAmountEx,
    taxPrice: 0,
    inTaxAmount: inTaxAmount,
    taxAmount: 0,
    weight: weight,
    itemWeight: itemWeight
  };

  var ct = new ComputingTools(options);

  var id = 'input[name="' + configName + '[' + index + '].qty"], ' +
      'input[name="' + configName + '[' + index + '].basicQty"], ' +
      'input[name="' + configName + '[' + index + '].secQty"], ' +
      'input[name="' + configName + '[' + index + '].coefficient"], ' +
      'input[name="' + configName + '[' + index + '].secCoefficient"], ' +
      'input[name="' + configName + '[' + index + '].price"], ' +
      'input[name="' + configName + '[' + index + '].amount"], ' +
      'input[name="' + configName + '[' + index + '].discountRate"], ' +
      'input[name="' + configName + '[' + index + '].discountPrice"], ' +
      'input[name="' + configName + '[' + index + '].discountAmount"], ' +
      'input[name="' + configName + '[' + index + '].taxRate"], ' +
      'input[name="' + configName + '[' + index + '].taxPriceEx"], ' +
      'input[name="' + configName + '[' + index + '].taxAmountEx"], ' +
      'input[name="' + configName + '[' + index + '].taxPrice"], ' +
      'input[name="' + configName + '[' + index + '].inTaxAmount"], ' +
      'input[name="' + configName + '[' + index + '].taxAmount"], ' +
      'input[name="' + configName + '[' + index + '].itemWeight"]';
 $(id).unbind("change");
 $(id).bind('change', function (event) {
   var obj = event.currentTarget;
    var val = parseFloat(obj.value) || 0;
    var name = obj.name;
    var configLength = configName.length;
    var indexLength = (index + "").length;
    var nameStr = name.substring(configLength + indexLength + 3, name.length);
     if(nameStr.toLowerCase() == "qty"){
       var CFG_NUMBER = $("#CFG_NUMBER").val();
       ct._opt[nameStr] = parseFloat(val).toFixed(CFG_NUMBER);
       $("input[name='"+configName+"["+index+"]."+nameStr+"']").val(parseFloat(val).toFixed(CFG_NUMBER))
     }else {
       ct._opt[nameStr] = val;
     }

    if (!beforeCheck(index, ct)) {
      return false;
    }

    ct.computeBasicQty();
    //判断辅助换算率是否为0；
    if (ct._opt.secCoefficient != 0) {
      ct.computeSecQty();
    }
    ct.computeWeight();
    if (nameStr == 'taxAmountEx') {//金额改变时
      ct.computeTaxPriceEx();
      ct.computePrice();
      ct.computeAmount();
      ct.computeInTaxAmount();
      ct.computeDiscountRate();
      ct.computeDiscountPrice();
      ct.computeDiscountAmount();
      ct.computeAmountEx();
      ct.computeTaxPrice();
      ct.computeTaxAmount();
    } else if (nameStr == 'inTaxAmount') {//折后金额改变时
      ct.computeTaxPrice();//重新计算折后单价
      ct.computeDiscountRate();//重新计算折扣率
      ct.computeTaxAmount();//重新计算税额
    } else if (nameStr == 'itemWeight') {
      ct.computeWeight();
    } else {
      ct.computePrice();
      ct.computeAmount();
      ct.computeInTaxAmount();
      ct.computeDiscountRate();
      ct.computeDiscountPrice();
      ct.computeDiscountAmount();
      ct.computeAmountEx();
      ct.computeTaxPriceEx();
      ct.computeTaxPrice();
      ct.computeTaxAmount();
      ct.computeWeight();
    }
   var CFG_NUMBER = $("#CFG_NUMBER").val();//数量
   var CFG_MONEY = $("#CFG_MONEY").val();//金额
   var CFG_PRICE = $("#CFG_UNITP_RICE").val();//单价
   var CFG_RATES = $("#CFG_RATES").val();//税率
   var CFG_DISCOUNT_RATE = $("#CFG_DISCOUNT_RATE").val();//折扣率
   var CFG_OTHER = $("#CFG_OTHER").val();//其他
    for (var item in ct._opt) {
      //当前触发对象则不重新赋值
      if (item == nameStr) {
        continue;
      }
      //如果某ID输入框不存在则不赋值
      if ($('input[name="' + configName + '[' + index + '].' + item).length <= 0) {
        continue;
      }
      if(item.indexOf("Amount") > -1) {
        $('input[name="' + configName + '[' + index + '].' + item).val(parseFloat(ct._opt[item]).toFixed(CFG_MONEY));
      } else if(item.indexOf("Price") > -1){
        $('input[name="' + configName + '[' + index + '].' + item).val(parseFloat(ct._opt[item]).toFixed(CFG_PRICE));
      } else if(item == "taxRate"){
        $('input[name="' + configName + '[' + index + '].' + item).val(parseFloat(ct._opt[item]).toFixed(CFG_RATES));
      } else if(item == "discountRate"){
        $('input[name="' + configName + '[' + index + '].' + item).val(parseFloat(ct._opt[item]).toFixed(CFG_DISCOUNT_RATE));
      } else if(item == "weight"){
        $('input[name="' + configName + '[' + index + '].' + item).val(parseFloat(ct._opt[item]).toFixed(CFG_OTHER));
      }
      else{
        $('input[name="' + configName + '[' + index + '].' + item).val(parseFloat(ct._opt[item]).toFixed(CFG_NUMBER));
      }
      if (item == "inTaxAmount") {
        $('input[name="' + configName + '[' + index + '].' + item).trigger("change");
      }
      if(item == "inTaxAmount" || item == "taxAmountEx" || item == "taxAmount" || item =="weight"){
        $('input[name="' + configName + '[' + index + '].' + item).trigger("input");
      }
    }
    //计算需要累计的子表列
/*    if(sumAmount != '' && sumAmount != null) {
      var sumAmountJson = $.parseJSON(sumAmount);
      $.each(sumAmountJson, function () {
        ct.sumItem($(this).itemName, $(this).mainItemId)
      });
    }*/
   if(sumAmount != '' && sumAmount != null) {
      var sumAmountJson = $.parseJSON(sumAmount);
      $.each(sumAmountJson, function (i, item) {
        ct.sumItem(item.itemName, item.mainItemId)
      });
    }
  });

  $("input[name='" + configName + "[" + index + "].secCoefficient']").bind("change", function () {
    var secCoefficient = $("input[name='" + configName + "[" + index + "].secCoefficient']").val();
    if (parseFloat(secCoefficient) > 0) {
      ct._opt.secCoefficient = parseFloat(secCoefficient);
      ct.computeSecQty();
      $('input[name="' + configName + '[' + index + '].secQty"]').val(ct._opt["secQty"].toFixed(2));
    }
  });
  $("input[name='" + configName + "[" + index + "].coefficient']").bind("change", function () {
    // debugger;
    var coefficient = $("input[name='" + configName + "[" + index + "].coefficient']").val();
    ct._opt.coefficient = parseFloat(coefficient);
    ct.computeBasicQty();
    $('input[name="' + configName + '[' + index + '].basicQty"]').val(ct._opt["basicQty"].toFixed(2));
    if (parseFloat(ct._opt.secCoefficient) > 0) {
      ct.computeSecQty();
      $('input[name="' + configName + '[' + index + '].secQty"]').val(ct._opt["secQty"].toFixed(2));
    }
  });

}

/**
 * 计算前先检查
 * 数量和金额至少一项有值
 * 换算率需有值
 * @returns {boolean}
 */
function beforeCheck(index, ct) {
  if (ct && ct._opt.qty == 0) {
    tip('请先设置数量！');
    return false;
  }
  //else if (ct._opt.coefficient == 0) {
  //  tip('请先设置该商品换算率！');
  //  return false;
  //}
  if (ct && !ct._opt.taxPriceEx && !ct._opt.taxAmountEx) {
    tip('单价及金额至少需一项有值！');
    return false;
  }
  else {
    return true;
  }
}