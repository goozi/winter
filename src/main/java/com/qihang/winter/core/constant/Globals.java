package com.qihang.winter.core.constant;

import com.qihang.winter.core.util.ResourceUtil;


/**
 * 项目名称：jeecg
 * 类名称：Globals
 * 类描述：  全局变量定义
 * 创建人： Zerrion
 * @version
 *
 */
public final class Globals {

	/**是否*/
	public static final String SF_YN = "sf_yn";
	/**
	 * 保存用户到SESSION
	 */
	public static final String USER_SESSION="USER_SESSION";
	/**
	 * 人员类型
	 */
	public static final Short User_Normal=1;//正常
	public static final Short User_Forbidden=0;//禁用
	public static final Short User_ADMIN=-1;//超级管理员
	/**
	 *日志级别定义
	 */
	public static final Short Log_Leavel_INFO=1;
	public static final Short Log_Leavel_WARRING=2;
	public static final Short Log_Leavel_ERROR=3;
	/**
	 * 日志类型
	 */
	public static final Short Log_Type_LOGIN=1; //登陆
	public static final Short Log_Type_EXIT=2;  //退出
	public static final Short Log_Type_INSERT=3; //插入
	public static final Short Log_Type_DEL=4; //删除
	public static final Short Log_Type_UPDATE=5; //更新
	public static final Short Log_Type_UPLOAD=6; //上传
	public static final Short Log_Type_OTHER=7; //其他


	/**
	 * 词典分组定义
	 */
	public static final String TypeGroup_Database="database";//数据表分类

	/**
	 * 权限等级
	 */
	public static final Short Function_Leave_ONE=0;//一级权限
	public static final Short Function_Leave_TWO=1;//二级权限

	/**
	 * 权限等级前缀
	 */
	public static final String Function_Order_ONE="ofun";//一级权限
	public static final String Function_Order_TWO="tfun";//二级权限

	/**
	 * 权限类型
	 */
	public static final Short Function_TYPE_PAGE=0;//页面（菜单：菜单类型）
	public static final Short Function_TYPE_FROM=1;//表单/或者弹出（菜单：访问类型）
	/**
	 * 没有勾选的操作code
	 */
	public static final String NOAUTO_OPERATIONCODES ="noauto_operationCodes";
	/**
	 * 勾选了的操作code
	 */
	public static final String OPERATIONCODES ="operationCodes";


	/**
	 * 权限类型
	 */
	public static final Short OPERATION_TYPE_HIDE = 0;//页面
	public static final Short OPERATION_TYPE_DISABLED = 1;//表单/或者弹出


	/**
	 * 数据权限 - 菜单数据规则集合
	 */
	public static final String MENU_DATA_AUTHOR_RULES ="MENU_DATA_AUTHOR_RULES";
	/**
	 * 数据权限 - 菜单数据规则sql
	 */
	public static final String MENU_DATA_AUTHOR_RULE_SQL ="MENU_DATA_AUTHOR_RULE_SQL";
	/**
	 * 新闻法规
	 */
	public static final Short Document_NEW=0; //新建
	public static final Short Document_PUBLICH=0; //发布


	/** 基础资料类型 */
	public static final String BASE_ITEM_TYPE = "SC_ITEM_TYPE";
	public static final String SON_COMPANY = "13";//分支机构

	/** 区域 */
	public static final String REGION = "SC_REGION";

	/** 城市 */
	public static final String CITY = "SC_CITY";

	/** 开户银行 */
	public static final String BANK = "SC_BANK";

	/** 行业 */
	public static final String TRADE = "SC_TRADE";

	/** 客户分类 */
	public static final String CUSTOMER_TYPE = "SC_CUSTOMER_TYPE";

	/** 送货方式 */
	public static final String DELIVER_TYPE = "SC_DELIVER_TYPE";

	/** 供应商分类 */
	public static final String SUPPLIER_TYPE = "SC_SUPPLIER_TYPE";

	/** 存货类型 */
	public static final String STOCK_TYPE = "SC_STOCK_TYPE";

	/** 计价方法 */
	public static final String PRICE_METHOD = "SC_PRICE_METHOD";

	/** 保质期类型 */
	public static final String DURA_DATE_TYPE = "SC_DURA_DATE_TYPE";

	/** 辅助属性分类1 */
	public static final String  AUXILIARY_TYPE1 = "SC_AUXILIARY_TYPE1";

	/** 辅助属性分类2 */
	public static final String AUXILIARY_TYPE2 = "SC_AUXILIARY_TYPE2";

	/** 品牌 */
	public static final String BRAND_TYPE = "SC_BRAND_TYPE";

	/** 单位类型 */
	public static final String UNIT_TYPE = "SC_UNIT_TYPE";

	/** 仓库类型 */
	public static final String REPERTORY_TYPE = "SC_REPERTORY_TYPE";

	/** 职员类别 */
	public static final String EMPLOYEE_TYPE = "SC_EMPLOYEE_TYPE";

	/** 性别 */
	public static final String GENDER = "SC_GENDER";

	/** 职务 */
	public static final String DUTY_TYPE = "SC_DUTY_TYPE";

	/** 文化程度 */
	public static final String CULTURE_TYPE = "SC_CULTURE_TYPE";

	/** 核算类别 */
	public static final String CALCULATION_TYPE = "SC_CALCULATION_TYPE";

	/**付款方式 */
	public static final String PAY_TYPE = "SC_PAY_TYPE";

	/**数据库类型-甲骨文Oracle数据库*/
	public static final String DB_TYPE_ORACLE = "oracle";
	/**数据库类型-甲骨文MySQL数据库*/
	public static final String DB_TYPE_MYSQL = "mysql";
	/**数据库类型-微软SQL Server数据库*/
	public static final String DB_TYPE_SQLSERVER = "sqlserver";

	/**数据库默认实体库-甲骨文Oracle数据库*/
	public static final String DB_MAIN_DB_ORACLE = "";
	/**数据库默认实体库-甲骨文MySQL数据库*/
	public static final String DB_MAIN_DB_MYSQL = "mysql";
	/**数据库默认实体库-微软SQL Server数据库*/
	public static final String DB_MAIN_DB_SQLSERVER = "master";

	/**数据库默认系统端口-甲骨文Oracle数据库*/
	public static final String DB_SYS_USER_ORACLE = "system";
	/**数据库默认系统端口-甲骨文MySQL数据库*/
	public static final String DB_SYS_USER_MYSQL = "root";
	/**数据库默认系统端口-微软SQL Server数据库*/
	public static final String DB_SYS_USER_SQLSERVER = "sa";

	/**数据库默认系统管理员用户-甲骨文Oracle数据库*/
	public static final String DB_SYS_PORT_ORACLE = "1521";
	/**数据库默认系统管理员用户-甲骨文MySQL数据库*/
	public static final String DB_SYS_PORT_MYSQL = "3306";
	/**数据库默认系统管理员用户-微软SQL Server数据库*/
	public static final String DB_SYS_PORT_SQLSERVER = "1433";

	/**数据库默认系统连接数据库exe-甲骨文Oracle数据库*/
	public static final String DB_CONN_EXE_ORACLE = "sqlplus";
	/**数据库默认系统连接数据库exe-甲骨文MySQL数据库*/
	public static final String DB_CONN_EXE_MYSQL = "mysql";
	/**数据库默认系统连接数据库exe-微软SQL Server数据库*/
	public static final String DB_CONN_EXE_SQLSERVER = "sqlcmd";

	/**数据库默认数据库连接串-甲骨文Oracle数据库*/
	public static final String DB_CONN_URL_ORACLE = "jdbc:oracle:thin:@[DB_IP]:[DB_PORT]/[DB_NAME]";
	/**数据库默认数据库连接串-甲骨文MySQL数据库*/
	public static final String DB_CONN_URL_MYSQL = "jdbc:mysql://[DB_IP]:[DB_PORT]/[DB_NAME]?useUnicode=true&characterEncoding=UTF-8";
	/**数据库默认数据库连接串-微软SQL Server数据库*/
	public static final String DB_CONN_URL_SQLSERVER = "jdbc:microsoft:sqlserver://[DB_IP]:[DB_PORT];DatabaseName=[DB_NAME]";

	/**数据库连接驱动类-甲骨文Oracle数据库*/
	public static final String DB_DRIVER_CLASS_ORACLE = "oracle.jdbc.driver.OracleDriver";
	/**数据库连接驱动类-甲骨文MySQL数据库*/
	public static final String DB_DRIVER_CLASS_MYSQL = "com.mysql.jdbc.Driver";
	/**数据库连接驱动类-微软SQL Server数据库*/
	public static final String DB_DRIVER_CLASS_SQLSERVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	/** 默认主数据库数据源dbKey **/
	public  static final String DATA_SOURCE_JEECG ="dataSource_jeecg";

	/** 初始化管理 */
	public static final Short INIT_NAVI=1;

	/** 基础资料管理 */
	public static final Short BASEINFO_NAVI=2;

	/*** 商城管理	 */
	public static final Short SHOP_NAVI=3;

	/*** 仓存管理 */
	public static final Short INVENTORY_NAVI=4;



	/*** 零售管理 */
	public static final Short SALE_NAVI=5;


	/*** 资金管理 */
	public static final Short FINANCING_NAVI=6;


	/*** OA管理 */
	public static final Short OA_NAVI=7;

	/**销售管理*/
	public static final Short XS_NAVI=8;

	/** 树形结构根节点的parentID **/
	public static final String PARENT_ID = "00000";

	/**
	 * 业务参数设置
	 */
	/** 分页记录 **/
	public static  final String PAGE_NUMBER_CODE="CFG_PAGE";
	/** 行高 **/
	public static  final String ROW_HEIGHT_CODE="CFG_ROW_HEIGHT";
	/** 默认税率 **/
	public static  final String DEFAULT_RATE_CODE="CFG_TAX_RATE";
	/** 数量 **/
	public static  final String NUMBER_CODE="CFG_NUMBER";
	/** 单价 **/
	public static  final String UNITP_RICE_CODE="CFG_UNITP_RICE";
	/** 金额 **/
	public static  final String MONEY_CODE="CFG_MONEY";
	/** 税率 **/
	public static  final String RATES_CODE="CFG_RATES";
	/** 折扣率 **/
	public static  final String DISCOUNT_RATE_CODE="CFG_DISCOUNT_RATE";
	/** 其他 **/
	public static  final String OTHER_CODE="CFG_OTHER";
	/** 控制方式 **/
	public static  final String CONTROL_METHOD_CODE="CFG_CONTROL_METHOD";
	/** 控制时点 **/
	public static  final String CONTROL_TIME_POINT_CODE="CFG_CONTROL_TIMEPOINT";
	/** 应收预警天数 **/
	public static  final String RECEARWAR_DAYS_CODE="CFG_RECEARWAR_DAYS";
	/** 采购订单预警天数 **/
	public static  final String PRUORDEARWAR_DAYS_CODE="CFG_PRUORDEARWAR_DAYS";
	/** 应付预警天数 **/
	public static  final String PAYEARWAR_DAYS_CODE="CFG_PAYEARWAR_DAYS";
	/** 销售订单预警天数 **/
	public static  final String SALORDEARWAR_DAYS_CODE="CFG_SALORDEARWAR_DAYS";
	/** 保质期预警天数 **/
	public static  final String SHELFLIFEEARWAR_DAYS_CODE="CFG_SHELFLIFEEARWAR_DAYS";
	/** 系统日志保留天数 **/
	public static  final String SYSLOGHOLD_DAYS_CODE="CFG_SYSLOGHOLD_DAYS";
	/** 创建新账套数据库初始化sql存放在工程classes的相对目录 **/
	public static final String DB_INIT_SQL_PATH = "dbInitSql";

	/**
	 * 供应链设置
	 */
	/**允许负库存结账*/
	public static  final String MINUSINVENTORYACCOUNT_CODE="CFG_MINUSINVENTORY_ACCOUNT";
	/**允许负库存出库*/
	public static  final String MINUSINVENTORYSL_CODE="CFG_MINUSINVENTORYSL";
	/**允许盘点有未审核存货单据的数据*/
	public static  final String STOCKTAKINGNOTAUDITEDSTOCKBILL_CODE="CFG_STOCKTAKINGNOTAUDITEDSTOCK_BILL";
	/**单据保存时系统自动审核*/
	public static  final String BILLSAVESYSTEMWITHEXAMINE_CODE="CFG_BILLSAVESYSTEMWITH_EXAMINE";
	/**单据审核时系统自带后续业务单据*/
	public static  final String BILLEXAMINESYSTEMWITHFOLLOW_CODE="CFG_BILLEXAMINESYSTEMWITH_FOLLOW";
	/**不允许手工开入库单*/
	public static  final String CANNOTMANUALOPENINREPERTORY_CODE="CFG_CANNOTMANUALOPENIN_REPERTORY";
	/**不允许入库单数量大于采购订单数量*/
	public static  final String CANNOTINREPERTORYNYTPURCHASEN_CODE="CFG_CANNOTINREPERTORYNYT_PURCHASEN";
	/**采购模块启用价格调用顺序*/
	public static  final String PURCHASESTARTPRICECALLORDER_CODE="CFG_PURCHASE_ENABLE_PRI_SEQ";
	/**采购设置下拉框一*/
	public static  final String PURCHASESELECTONE_CODE="CFG_PURCHASE_PRI_ONE";
	/**采购设置下拉框二*/
	public static  final String PURCHASESELECTTWO_CODE="CFG_PURCHASE_PRI_SEC";
	/**不允许手工开出库单*/
	public static  final String CANNOTMANUALOPENOUTREPERTORY_CODE="CFG_CANNOTMANUALOPENOUT_REPERTORY";
	/**不允许出库单数量大于销售订单数量*/
	public static  final String CANNOTOUTREPERTORYNGTSALE_CODE="CFG_CANNOTOUTREPERTORYNGT_SALE";
	/**销售模块启用价格调用顺序*/
	public static  final String SALESTARTPRICECALLORDER_CODE="CFG_SALES_ENABLE_PRI_SEQ";
	/**销售设置下拉框一*/
	public static  final String SALESELECTONE_CODE="CFG_SALES_PRI_ONE";
	/**销售设置下拉框二*/
	public static  final String SALESELECTTWO_CODE="CFG_SALES_PRI_SEC";
	/**销售设置下拉框三*/
	public static  final String SALESELECTTHREE_CODE="CFG_SALES_PRI_THI";



	/**
	 * 禁用 启用code
	 */
	/** 禁用 **/
	public static  final  Integer DISABLED_CODE=1;
	/** 启用 **/
	public static  final  Integer ENABLE_CODE=0;
	/*账套未开启*/
	public static  final  Integer ACCOUNT_OPEN_NO=0;
	/*账套已开启*/
	public static  final  Integer ACCOUNT_OPEN_YES=1;
	/*账套未关闭*/
	public static  final  Integer ACCOUNT_CLOSE_NO=0;
	/*账套已关闭*/
	public static  final  Integer ACCOUNT_CLOSE_YES=1;
	/*账套不允许负库存结账*/
	public static  final  Integer ACCOUNT_MINUS_INVENTORY_NO=0;
	/*账套允许负库存结账*/
	public static  final  Integer ACCOUNT_MINUS_INVENTORY_YES=1;
	/*账套不允许负库存出库*/
	public static  final  Integer ACCOUNT_MINUS_INVENTORY_SI_NO=0;
	/*账套允许负库存结账出库*/
	public static  final  Integer ACCOUNT_MINUS_INVENTORY_SI_YES=1;

	/*期未结账时进行当期结账操作*/
	public static final String ACCOUNT_STAGE_CODE = "stage";
	/*期未结账时进行上期反结账操作*/
	public static final String ACCOUNT_UNSTAGE_CODE = "unstage";
	/*scm伪账套记录*/
	public static final String ACCOUNT_SCM_ID = "402880fb556d2c2701556d2fbf0e0001";

	/** 单位类型-基本单位 */
	public static final String UNIT_TYPE_BASIC = "0001";
	/** 单位类型-常用单位 */
	public static final String UNIT_TYPE_COMMON = "0002";
	/** 单位类型-辅助单位 */
	public static final String UNIT_TYPE_SEC = "0003";
	/**
	 * 记录引用次数,
	 * 通过type判断是什么操作: 1是新增，2是编辑，3是删除
	 */
	public static final Short COUNT_ADD_TYPE = 1;
	public static final Short COUNT_UPDATE_TYPE = 2;
	public static final Short COUNT_DEL_TYPE = 3;


	/**
	 * 配置系统是否开启按钮权限控制
	 */
	public static boolean BUTTON_AUTHORITY_CHECK = false;
	static{
		String button_authority_jeecg = ResourceUtil.getSessionattachmenttitle("button.authority.jeecg");
		if("true".equals(button_authority_jeecg)){
			BUTTON_AUTHORITY_CHECK = true;
		}
	}

	/**
	 * 审核状态
	 */
	public static final Integer AUDIT_NO = 0;//未审核
	public static final Integer AUDIT_IN = 1;//审核中
	public static final Integer AUDIT_FIN = 2;//已审核

	/**
	 * 是否是经销商
	 */
	public static final Integer IS_DEALER= 1;

	/**
	 * 标记 1为是 0为否
	 */
	public static final String CLOSE_YES = "1";
	public static final String CLOSE_NO = "0";

	/**
	 * 是否合格经销商 1 是 0 否
	 */
	public static  final Integer ELIGIBILITY_YES = 1;
	public static  final Integer ELIGIBILITY_NO = 0;

	/**
	 * 作废标记 0 未作废 1已作废
	 */
	public static  final Integer CANCELLATION_YES = 1;
	public static  final Integer CANCELLATION_NO = 0;
	/***
	 * 单据类型
	 */
	public static final String SC_QUOTE_TRANTYPE = "101"; //销售报价单的单据类型
	public  static final Integer SC_ORDER_TRANTYPE = 102;//销售订单的单据类型
	public  static final Integer SC_SL_STOCKBILL_TRANTYPE = 103;//销售出库的单据类型
	public  static final Integer SC_SL_STOCKBILL_RETURN_TRANTYPE = 104;//销售退货的单据类型
	public  static final Integer SC_IC_XSSTOCKBILL_TRANTYPE = 110;//销售换货的单据类型

	public static final Integer SC_APTITUDE_TRANTYPE = 77;//经销商资格审查单据类型/***
	public static final Integer SC_PRCPLY_TRANTYPE = 353;//经销商调价政策单据类型
	public static final Integer SC_PROMOTION_TRANTYPE = 356;//经销商买一赠一单据类型

	public static final Integer SC_DRP_ORDER_TRANTYPE = 1505;//经销商订单管理单据类型
	public static final Integer SC_DRP_ORDER_STOCKBILL_TRANTYPE = 1506;//经销商发货管理单据类型
	public static final Integer SC_DRP_ORDER_RSTOCKBILL_TRANTYPE = 1507;//经销商退货管理单据类型

	public static final Integer SC_IC_INITIAL_TRANTYPE = 1020;//存货初始化单据类型
	public static final Integer SC_IC_BEGDATA_TRANTYPE = 1030;//应收初始化单据类型
	public static final Integer SC_IC_BEGDATAPAYABLE_TRANTYPE = 1031;//应付初始化单据类型
	public static final Integer SC_IC_BEGDATAINCOMEPAY_TRANTYPE = 1032;//收支初始化单据类型
	public static final Integer SC_RP_BANKBILL_TRANTYPE = 309;//银行存取款单据类型
	public static final Integer SC_RP_BANKBILL_RECON = 301;//资金调账单据类型
	public static final Integer SC_RP_BANKBILL_OTHER = 303;//资金其他收入单
	public static final Integer SC_RP_BANKBILL_EXPENSESAPPLY = 319;//资金费用申报单
	public static final Integer SC_RP_BANKBILL_POTHER = 304;//资金费用开支单

	public static final Integer SC_PO_ORDER_TRANTYPE = 51; //采购订单的单据类型
	public static final Integer SC_PO_STOCKBILL_TRANTYPE = 52; //采购入库单的单据类型
	public static final Integer SC_PO_STOCKBILL_RETURN_TRANTYPE = 53; //采购退货单的单据类型
	public  static final Integer SC_IC_JHSTOCKBILL_TRANTYPE = 61;//采购换货的单据类型

	public static final Integer SC_RP_PBILL = 202;//应付调账
	public static final Integer SC_RP_RBILL = 201;//应收调账

	public static final Integer SC_RP_PBILL_INFO = 54;//付款单
	public static final Integer SC_RP_RBILL_INFO = 105;//收款单

	public static final Integer SC_IC_CHK_STOCKBILL = 160;//盘点单
	/** 客户的启用信用控制
	 * 是为1
	 * 否为0
	 */
	public static final Integer ISCREDITMGRYES = 1;
	public static final Integer ISCREDITMGRNO = 0;

	/**
	 * 单据类型对照
	 */
	public static final String POSTOCKBILL = "52";//采购订单
	public static final String POSTOCKBILLRETURN = "53";//采购退货订单
	public static final String ICJHSTOCKBILL = "61";//采购换货单

	/**
	 * 分销 发货单 确认状态 0：未确认 1：已确认
	 */
	public static final Integer SCDRPSTOCKBILL_YES = 1;//确认
	public static final Integer SCDRPSTOCKBILL_NO = 0;//未确认

	/**
	 * 分支机构类型
	 */
	public static final String ORG_TYPE_PUR = "1";//经销商
	public static final String ORG_TYPE_STORE = "2";//门店
	public static final String ORG_TYPE_DEPT = "3";//部门

	/**
	 * 经销商类别
	 */
	public static final String SC_TYPE_ADEALER = "ADealer";
	public static final String SC_TYPE_BDEALER = "BDealer";
	public static final String SC_TYPE_CDEALER = "CDealer";

	/**
	 * 基础资料类型
	 */
	public static final String SC_BASEINFO_ORGANIZATION_TYPE = "01";//客户
	public static final String SC_BASEINFO_SUPPLIER_TYPE = "02";//供应商
	public static final String SC_BASEINFO_LOGISTICAL_TYPE = "03";//物流公司
	public static final String SC_BASEINFO_ICITEM_TYPE= "04";// 商品
	public static final String SC_BASEINFO_EMP_TYPE = "05";//职员
	public static final String SC_BASEINFO_DEPARTMENT_TYPE = "06";//部门
	public static final String SC_BASEINFO_STOCK_TYPE = "07";//仓库
	public static final String SC_BASEINFO_FEE_TYPE = "09";//收支项目



}
