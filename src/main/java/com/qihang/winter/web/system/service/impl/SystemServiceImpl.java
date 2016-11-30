package com.qihang.winter.web.system.service.impl;

import java.sql.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.qihang.buss.sc.sys.entity.TScAccountConfigEntity;
import com.qihang.buss.sc.sys.service.TScAccountConfigServiceI;
import com.qihang.buss.sc.sysaudit.entity.TSAuditEntity;
import com.qihang.buss.sc.sysaudit.entity.TSAuditLeaveEntity;
import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.buss.sc.sysaudit.entity.TScBillAuditStatusEntity;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.extend.datasource.DataSourceContextHolder;
import com.qihang.winter.core.extend.datasource.DataSourceType;
import com.qihang.winter.core.util.*;
import com.qihang.winter.web.system.dao.JeecgDictDao;
import com.qihang.winter.web.system.pojo.base.*;
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("systemService")
@Transactional
public class SystemServiceImpl extends CommonServiceImpl implements SystemService {
	@Autowired
	private JeecgDictDao jeecgDictDao;
	
	public TSUser checkUserExits(TSUser user) throws Exception {
		return this.commonDao.getUserByUserIdAndUserNameExits(user);
	}
	
	public List<DictEntity> queryDict(String dicTable, String dicCode, String dicText){
		List<DictEntity> dictList = null;
		//step.1 如果没有字典表则使用系统字典表
		if(StringUtil.isEmpty(dicTable)){
			dictList = jeecgDictDao.querySystemDict(dicCode);
			for(DictEntity t:dictList){
				t.setTypename(MutiLangUtil.getMutiLangInstance().getLang(t.getTypename()));
			}
		}else {
			dicText = StringUtil.isEmpty(dicText, dicCode);
			dictList = jeecgDictDao.queryCustomDict(dicTable, dicCode, dicText);
		}
		return dictList;
	}

	/*
	 *通过字典代码和字典值得到字典名
	 * @Param dicCode 字典代码
	 * @Param typeCode 字典值
	 */
	public String typeName(String dicCode,String typeCode){
		List<TSType> tsTypeList = commonDao.findByQueryString("from TSType where TSTypegroup.typegroupcode='" + dicCode + "' and typecode='" + typeCode + "'");
		if(tsTypeList.size()>0){
			TSType tsType = tsTypeList.get(0);
			return tsType.getTypename();
		}
		return "";
	}
	/*
	 *通过字典代码和字典值得到字典名
	 * @Param dicName 字典名称
	 * @Param typeCode 字典值
 	*/
	public String typeId(String typeGroupCode,String typeName){
		List<TSType> tsTypeList = commonDao.findByQueryString("from TSType where TSTypegroup.typegroupcode='" + typeGroupCode + "' and typename='" + typeName + "'");
		if(tsTypeList.size()>0){
			TSType tsType = tsTypeList.get(0);
			return tsType.getTypecode();
		}
		return "";
	}
	/**
	 * 添加日志
	 */
	public void addLog(String logcontent, Short loglevel, Short operatetype) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String broswer = BrowserUtils.checkBrowse(request);
		TSLog log = new TSLog();
		log.setLogcontent(logcontent);
		log.setLoglevel(loglevel);
		log.setOperatetype(operatetype);
		log.setNote(oConvertUtils.getIp());
		log.setBroswer(broswer);
		log.setOperatetime(DateUtils.gettimestamp());
		log.setTSUser(ResourceUtil.getSessionUserName());
		commonDao.save(log);
	}

	/**
	 * 根据类型编码和类型名称获取Type,如果为空则创建一个
	 * 
	 * @param typecode
	 * @param typename
	 * @return
	 */
	public TSType getType(String typecode, String typename, TSTypegroup tsTypegroup) {
		//TSType actType = commonDao.findUniqueByProperty(TSType.class, "typecode", typecode,tsTypegroup.getId());
		List<TSType> ls = commonDao.findHql("from TSType where typecode = ? and typegroupid = ?",typecode,tsTypegroup.getId());
		TSType actType = null;
		if (ls == null || ls.size()==0) {
			actType = new TSType();
			actType.setTypecode(typecode);
			actType.setTypename(typename);
			actType.setTSTypegroup(tsTypegroup);
			commonDao.save(actType);
		}else{
			actType = ls.get(0);
		}
		return actType;

	}

	/**
	 * 根据类型分组编码和名称获取TypeGroup,如果为空则创建一个
	 * 
	 * @param typegroupcode
	 * @param typgroupename
	 * @return
	 */
	public TSTypegroup getTypeGroup(String typegroupcode, String typgroupename) {
		TSTypegroup tsTypegroup = commonDao.findUniqueByProperty(TSTypegroup.class, "typegroupcode", typegroupcode);
		if (tsTypegroup == null) {
			tsTypegroup = new TSTypegroup();
			tsTypegroup.setTypegroupcode(typegroupcode);
			tsTypegroup.setTypegroupname(typgroupename);
			commonDao.save(tsTypegroup);
		}
		return tsTypegroup;
	}

	
	public TSTypegroup getTypeGroupByCode(String typegroupCode) {
		TSTypegroup tsTypegroup = commonDao.findUniqueByProperty(TSTypegroup.class, "typegroupcode", typegroupCode);
		return tsTypegroup;
	}

	
	public void initAllTypeGroups() {
		List<TSTypegroup> typeGroups = this.commonDao.loadAll(TSTypegroup.class);
		for (TSTypegroup tsTypegroup : typeGroups) {
			TSTypegroup.allTypeGroups.put(tsTypegroup.getTypegroupcode().toLowerCase(), tsTypegroup);
			List<TSType> types = this.commonDao.findByProperty(TSType.class, "TSTypegroup.id", tsTypegroup.getId());
			TSTypegroup.allTypes.put(tsTypegroup.getTypegroupcode().toLowerCase(), types);
		}
	}

	
	public void refleshTypesCach(TSType type) {
		TSTypegroup tsTypegroup = type.getTSTypegroup();
		TSTypegroup typeGroupEntity = this.commonDao.get(TSTypegroup.class, tsTypegroup.getId());
		List<TSType> types = this.commonDao.findByProperty(TSType.class, "TSTypegroup.id", tsTypegroup.getId());
		TSTypegroup.allTypes.put(typeGroupEntity.getTypegroupcode().toLowerCase(), types);
	}

	
	public void refleshTypeGroupCach() {
		TSTypegroup.allTypeGroups.clear();
		List<TSTypegroup> typeGroups = this.commonDao.loadAll(TSTypegroup.class);
		for (TSTypegroup tsTypegroup : typeGroups) {
			TSTypegroup.allTypeGroups.put(tsTypegroup.getTypegroupcode().toLowerCase(), tsTypegroup);
		}
	}

	/**
	 * 根据角色ID 和 菜单Id 获取 具有操作权限的按钮Codes
	 * @param roleId
	 * @param functionId
	 * @return
	 */
	public Set<String> getOperationCodesByRoleIdAndFunctionId(String roleId, String functionId) {
		Set<String> operationCodes = new HashSet<String>();
		TSRole role = commonDao.get(TSRole.class, roleId);
		CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
		cq1.eq("TSRole.id", role.getId());
		cq1.eq("TSFunction.id", functionId);
		cq1.add();
		List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
		if (null != rFunctions && rFunctions.size() > 0) {
			TSRoleFunction tsRoleFunction = rFunctions.get(0);
			if (null != tsRoleFunction.getOperation()) {
				String[] operationArry = tsRoleFunction.getOperation().split(",");
				for (int i = 0; i < operationArry.length; i++) {
					operationCodes.add(operationArry[i]);
				}
			}
		}
		return operationCodes;
	}

	/**
	 * 根据用户ID 和 菜单Id 获取 具有操作权限的按钮Codes
	 * @param userId
	 * @param functionId
	 * @return
	 */
	public Set<String> getOperationCodesByUserIdAndFunctionId(String userId, String functionId) {
		Set<String> operationCodes = new HashSet<String>();
		List<TSRoleUser> rUsers = findByProperty(TSRoleUser.class, "TSUser.id", userId);
		for (TSRoleUser ru : rUsers) {
			TSRole role = ru.getTSRole();
			if (role!=null) {//新建用户时，会带一个空角色，这里做null判断处理
				CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
				cq1.eq("TSRole.id", role.getId());
				cq1.eq("TSFunction.id", functionId);
				cq1.add();
				List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
				if (null != rFunctions && rFunctions.size() > 0) {
					TSRoleFunction tsRoleFunction = rFunctions.get(0);
					if (null != tsRoleFunction.getOperation()) {
						String[] operationArry = tsRoleFunction.getOperation().split(",");
						for (int i = 0; i < operationArry.length; i++) {
							operationCodes.add(operationArry[i]);
						}
					}
				}
			}
		}
		return operationCodes;
	}
	
	public void flushRoleFunciton(String id, TSFunction newFunction) {
		TSFunction functionEntity = this.getEntity(TSFunction.class, id);
		if (functionEntity.getTSIcon() == null || !StringUtil.isNotEmpty(functionEntity.getTSIcon().getId())) {
			return;
		}
		TSIcon oldIcon = this.getEntity(TSIcon.class, functionEntity.getTSIcon().getId());
		if (!oldIcon.getIconClas().equals(newFunction.getTSIcon().getIconClas())) {
			// 刷新缓存
			HttpSession session = ContextHolderUtils.getSession();
			TSUser user = ResourceUtil.getSessionUserName();
			List<TSRoleUser> rUsers = this.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
			for (TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();
				session.removeAttribute(role.getId());
			}
		}
	}

    public String generateOrgCode(String id, String pid) {
        int orgCodeLength = 2; // 默认编码长度
        if ("3".equals(ResourceUtil.getOrgCodeLengthType())) { // 类型2-编码长度为3，如001
            orgCodeLength = 3;
        }

        String  newOrgCode = "";
        if(!StringUtils.hasText(pid)) { // 第一级编码
            String sql = "select max(t.org_code) orgCode from t_s_depart t where t.parentdepartid is null";
            Map<String, Object> pOrgCodeMap = commonDao.findOneForJdbc(sql);
            if(pOrgCodeMap.get("orgCode") != null) {
                String curOrgCode = pOrgCodeMap.get("orgCode").toString();
                newOrgCode = String.format("%0" + orgCodeLength + "d", Integer.valueOf(curOrgCode) + 1);
            } else {
                newOrgCode = String.format("%0" + orgCodeLength + "d", 1);
            }
        } else { // 下级编码
            String sql = "select max(t.org_code) orgCode from t_s_depart t where t.parentdepartid = ?";
            Map<String, Object> orgCodeMap = commonDao.findOneForJdbc(sql, pid);
            if(orgCodeMap.get("orgCode") != null) { // 当前基本有编码时
                String curOrgCode = orgCodeMap.get("orgCode").toString();
                String pOrgCode = curOrgCode.substring(0, curOrgCode.length() - orgCodeLength);
                String subOrgCode = curOrgCode.substring(curOrgCode.length() - orgCodeLength, curOrgCode.length());
                newOrgCode = pOrgCode + String.format("%0" + orgCodeLength + "d", Integer.valueOf(subOrgCode) + 1);
            } else { // 当前级别没有编码时
                String pOrgCodeSql = "select max(t.org_code) orgCode from t_s_depart t where t.id = ?";
                Map<String, Object> pOrgCodeMap = commonDao.findOneForJdbc(pOrgCodeSql, pid);
                String curOrgCode = pOrgCodeMap.get("orgCode").toString();
                newOrgCode = curOrgCode + String.format("%0" + orgCodeLength + "d", 1);
            }
        }

        return newOrgCode;
    }

	public Set<String> getOperationCodesByRoleIdAndruleDataId(String roleId,
			String functionId) {
		Set<String> operationCodes = new HashSet<String>();
		TSRole role = commonDao.get(TSRole.class, roleId);
		CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
		cq1.eq("TSRole.id", role.getId());
		cq1.eq("TSFunction.id", functionId);
		cq1.add();
		List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
		if (null != rFunctions && rFunctions.size() > 0) {
			TSRoleFunction tsRoleFunction = rFunctions.get(0);
			if (null != tsRoleFunction.getDataRule()) {
				String[] operationArry = tsRoleFunction.getDataRule().split(",");
				for (int i = 0; i < operationArry.length; i++) {
					operationCodes.add(operationArry[i]);
				}
			}
		}
		return operationCodes;
	}

	public Set<String> getOperationCodesByUserIdAndDataId(String userId,
			String functionId) {
		// TODO Auto-generated method stub
		Set<String> dataRulecodes = new HashSet<String>();
		List<TSRoleUser> rUsers = findByProperty(TSRoleUser.class, "TSUser.id", userId);
		for (TSRoleUser ru : rUsers) {
			TSRole role = ru.getTSRole();
			if (role!=null) {//新建用户时，会带一个空角色，这里做null判断处理
				CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
				cq1.eq("TSRole.id", role.getId());
				cq1.eq("TSFunction.id", functionId);
				cq1.add();
				List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
				if (null != rFunctions && rFunctions.size() > 0) {
					TSRoleFunction tsRoleFunction = rFunctions.get(0);
					if (null != tsRoleFunction.getDataRule()) {
						String[] operationArry = tsRoleFunction.getDataRule().split(",");
						for (int i = 0; i < operationArry.length; i++) {
							dataRulecodes.add(operationArry[i]);
						}
					}
				}
			}
		}
		return dataRulecodes;
	}
	/**
	 * 加载所有图标
	 * @return
	 */
	public  void initAllTSIcons() {
		List<TSIcon> list = this.loadAll(TSIcon.class);
		for (TSIcon tsIcon : list) {
			TSIcon.allTSIcons.put(tsIcon.getId(), tsIcon);
		}
	}
	/**
	 * 更新图标
	 * @param icon
	 */
	public  void upTSIcons(TSIcon icon) {
		TSIcon.allTSIcons.put(icon.getId(), icon);
	}
	/**
	 * 更新图标
	 * @param icon
	 */
	public  void delTSIcons(TSIcon icon) {
		TSIcon.allTSIcons.remove(icon.getId());
	}

	/**
	 * 获取审核结果
	 * @param id
	 * @param tranType
	 * @return
	 */
	@Override
	public TSAuditRelationEntity getAuditInfo(String id, String tranType) {
		List<TSAuditRelationEntity> relationEntityList = this.findHql("from TSAuditRelationEntity where billId = ? and tranType = ? and deleted=0 and status > 0 order by orderNum desc",new Object[]{id,tranType});
		TSAuditRelationEntity entity = null;
		if(relationEntityList.size() > 0){
			entity = relationEntityList.get(0);
		}
		return entity;
	}

	@Override
	public List<TSAuditRelationEntity> getAuditInfoList(String id, String tranType) {
		List<TSAuditRelationEntity> relationEntityList = this.findHql("from TSAuditRelationEntity where billId = ? and tranType = ? and deleted=0 and status >0 order by orderNum asc", new Object[]{id, tranType});
		return relationEntityList;
	}

	/**
   * 通过CODE获取系统配置
   * @param code
   * @return
   */
  public String getConfigByCode(String code){
    TSConfig config = this.findUniqueByProperty(TSConfig.class,"code",code);
    if(config != null){
      return config.getContents();
    }
    return null;
  }

	/**
	 * 测试当前hibernate会话的连接信息
	 * @author:hjh 20160906
	 */
	public void getConn(){
		try {
			//todo:测试切换前的数据源连接
			//String[] xmlFields = new String[]{
			//		"spring-mvc-hibernate.xml",
			//		"spring-mvc.xml"
			//};
			//ApplicationContext mycontext = new ClassPathXmlApplicationContext(xmlFields);
			//SessionFactory mysessionFactory = (SessionFactory) mycontext.getBean("sessionFactory");
			//Session mysession = mysessionFactory.openSession();\
			//Connection conn = SessionFactoryUtils.getDataSource(mysessionFactory).getConnection();
			String dst = DataSourceContextHolder.getDataSourceType()==null?"":DataSourceContextHolder.getDataSourceType().toString();
			SessionFactory sessionFactory = commonDao.getSession().getSessionFactory();
			//Session session = commonDao.getSession();
			//Connection conn = SessionFactoryUtils.getDataSource(session.getSessionFactory()).getConnection();
			Session session = sessionFactory.openSession();
			Connection conn = SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
			Session currsession = sessionFactory.getCurrentSession();
			conn.getMetaData();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 通过jdbc到主数据库查询当前账套dbKey对应的账套信息
	 * @param dbkey
	 * @author:hjh 20160906
	 */
	public TScAccountConfigEntity getCurrentAccountConfigByDbkey(String dbkey){
		return getCurrentAccountConfigByParameter("dbKey",dbkey);
	}

	/**
	 * 通过jdbc到主数据库查询当前账套Id对应的账套信息
	 * @param dbkey
	 * @author:hjh 20160906
	 */
	public TScAccountConfigEntity getCurrentAccountConfigByAccountid(String dbkey){
		return getCurrentAccountConfigByParameter("id",dbkey);
	}

	@Override
	public Boolean checkBillNo(String tableName, String billNo,String billId) {
		Boolean result = true;
		String checkSql = "select 1 from "+tableName+" where billNo = '"+billNo+"'";
		if(!StringUtils.isEmpty(billId)){
			checkSql += " and id <> '"+billId+"'";
		}
		List<Object> checkInfo = this.findListbySql(checkSql);
		if(checkInfo.size() > 0){
			result = false;
		}
		return result;
	}

	/**
	 * 按条件到主数据库查询指定账定对应账套信息
	 * @param fieldName
	 * @param fieldValue
     * @return
     */
	public TScAccountConfigEntity getCurrentAccountConfigByParameter(String fieldName, String fieldValue){

		//方案一：通过jdbc来查询
		TScAccountConfigEntity accountConfig = new TScAccountConfigEntity();
		DynamicDataSourceEntity maindynamicSourceEntity = new DynamicDataSourceEntity();
		//数据库配置文件中读取默认主库的数据库连接参数
		PropertiesUtil propertiesUtil = new PropertiesUtil("dbconfig.properties");
		maindynamicSourceEntity.setDbKey("dataSource_jeecg");
		maindynamicSourceEntity.setUrl(propertiesUtil.readProperty("jdbc.url.jeecg"));
		maindynamicSourceEntity.setDriverClass(propertiesUtil.readProperty("diver.name"));
		maindynamicSourceEntity.setDbUser(propertiesUtil.readProperty("jdbc.username.jeecg"));
		maindynamicSourceEntity.setDbPassword(propertiesUtil.readProperty("jdbc.password.jeecg"));
		maindynamicSourceEntity.setDbType(propertiesUtil.readProperty("jdbc.dbType"));
		//改用一个新的jdbc connect连接到新数据库
		java.sql.Connection newconn = null;
		try {
			//1.连接到新数据库的默认数据库或实例
			Class.forName(maindynamicSourceEntity.getDriverClass());
			newconn = DriverManager.getConnection(maindynamicSourceEntity.getUrl(), maindynamicSourceEntity.getDbUser(), maindynamicSourceEntity.getDbPassword());//获取新数据库连接
			String sql = "select * from t_sc_account_config a where a." + fieldName + "=?";
			PreparedStatement stmt = newconn.prepareStatement(sql);
			stmt.setString(1,fieldValue);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				accountConfig.setCompanyName(rs.getString("CompanyName"));
				accountConfig.setTaxNumber(rs.getString("TaxNumber"));
				accountConfig.setBankAccount(rs.getString("BankAccount"));
				accountConfig.setCompanyAddress(rs.getString("CompanyAddress"));
				accountConfig.setPhone(rs.getString("Phone"));
				accountConfig.setFax(rs.getString("Fax"));
				accountConfig.setEmail(rs.getString("Email"));
				accountConfig.setCompanyUrl(rs.getString("CompanyUrl"));
				accountConfig.setRegistrationNumber(rs.getString("RegistrationNumber"));
				accountConfig.setMinusInventorySl(rs.getInt("minusinventorysl"));
				accountConfig.setMinusInventoryAccount(rs.getInt("minusinventoryaccount"));
				accountConfig.setDbKey(rs.getString("dbkey"));
				accountConfig.setSystemVersion(rs.getString("systemversion"));
				accountConfig.setOpenState(rs.getInt("openstate"));
				accountConfig.setOpenDate(rs.getDate("opendate"));
				accountConfig.setCloseState(rs.getInt("closestate"));
				accountConfig.setCloseDate(rs.getDate("closedate"));
				accountConfig.setAccountStartDate(rs.getDate("accountstartdate"));
				accountConfig.setStageStartDate(rs.getDate("stagestartdate"));
				accountConfig.setId(rs.getString("id"));
			}
			rs.close();
			stmt.close();
		}catch (Exception e){

		}finally {
			try {
				if (newconn != null) {
					newconn.close();
				}
			}catch (Exception e){

			}
		}
		//方法二：从ehcache中查询
		//TScAccountConfigServiceI tScAccountConfigServiceI = ApplicationContextUtil.getContext().getBean(TScAccountConfigServiceI.class);
		//TScAccountConfigEntity accountConfig = tScAccountConfigServiceI.findByDbKey(dbkey);
		return accountConfig;
	}

	/**
	 * 按条件到主数据库查询指定账定对应账套信息
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public DynamicDataSourceEntity getDynamicDataSourceByParameter(String fieldName, String fieldValue){
		return  DBUtil.getDynamicDataSourceByParameter(fieldName, fieldValue);
	}

	@Override
	public Set<String> formatterSonId(String sonId) {
		String[] idArry = sonId.split(",");
		Set<String> result = new HashSet<String>();
		for(String id : idArry){
			result.add(id);
		}
		return result;
	}

	/**
	 * 获取机构类型为经销商或门店的分支机构id
	 * @param sonInfo
	 * @return
	 */
	@Override
	public TSDepart getParentSonInfo(TSDepart sonInfo) {
		TSDepart pInfo = this.get(TSDepart.class, sonInfo.getId());
		if("1".equals(pInfo.getOrgType()) || "2".equals(pInfo.getOrgType())){
			return  pInfo;
		} else {
			if(null != pInfo.getTSPDepart()) {
				pInfo = getParentSonInfo(pInfo.getTSPDepart());
			}else {
				return pInfo;
			}
		}
		return pInfo;
	}

	/**
	 * 根据父部门id获取所有子部门id
	 * @param id
	 * @return
	 */
	@Override
	public Set<String> getAllSonId(final String id) {
		Set<String> result = new HashSet<String>();
		List<String> sonIdsInfo = this.findListbySql("select f_sc_getDepartChildList('"+id+"')");
		if(sonIdsInfo.size() > 0){
			String sonIds = sonIdsInfo.get(0);
			sonIds = sonIds.substring(2,sonIds.length());
			String[] sonIdArr = sonIds.split(",");
			for(String sonId : sonIdArr){
				result.add(sonId);
			}
		}
//		JdbcTemplate jdbcTemplate = ApplicationContextUtil.getContext().getBean(
//				JdbcTemplate.class);
//		Set<String> sonIds = (Set<String>) jdbcTemplate.execute(new CallableStatementCreator() {
//			@Override
//			public CallableStatement createCallableStatement(Connection con) throws SQLException {
//				String storedProc = "select f_sc_getDepartChildList(?)";
//				CallableStatement cs = con.prepareCall(storedProc);
//				cs.setString(1, id);
//				return cs;
//			}
//		}, new CallableStatementCallback() {
//			public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
//				cs.execute();
//				String sonIdInfo = cs.getString(1);
//				String[] sonIdArr = sonIdInfo.split(",");
//				Set<String> result = new HashSet<String>();
//				for(String sonId : sonIdArr){
//					result.add(sonId);
//				}
//				return result;
//			}
//		});
		return result;
	}

	/**
	 *
	 * @param tranType
	 * @param id
	 */
	@Override
	public void saveBillAuditStatus(String tranType, String id) {
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = this.getParentSonInfo(sonInfo);
		List<TSAuditEntity> checkIsMore = this.findHql("from TSAuditEntity where billId = ? and sonId=? and isAudit = 1",new Object[]{tranType,depart.getId()});
		if(checkIsMore.size() > 0){
			TScBillAuditStatusEntity tScBillAuditStatusEntity = new TScBillAuditStatusEntity();
			tScBillAuditStatusEntity.setSonId(depart.getId());
			tScBillAuditStatusEntity.setBillId(id);
			tScBillAuditStatusEntity.setTranType(tranType);
			tScBillAuditStatusEntity.setStatus(1);
			super.save(tScBillAuditStatusEntity);
		}
	}

	/**
	 *
	 * @param tranType
	 * @param id
	 */
	@Override
	public void delBillAuditStatus(String tranType, String id) {
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = this.getParentSonInfo(sonInfo);
		List<TSAuditEntity> checkIsMore = this.findHql("from TSAuditEntity where billId = ? and sonId=? and isAudit = 1",new Object[]{tranType,depart.getId()});
		if(checkIsMore.size() > 0){
			List<TScBillAuditStatusEntity> tScBillAuditStatusEntity = this.findHql("from TScBillAuditStatusEntity where billId = ? and sonId=? and tranType=?",new Object[]{id,depart.getId(),tranType});
			if(tScBillAuditStatusEntity.size() > 0){
				for(TScBillAuditStatusEntity entity : tScBillAuditStatusEntity){
					super.delete(entity);
				}
			}
		}
	}

	/**
	 * 获取当前用户的审核级别
	 * @param userId
	 * @param sonId
	 * @param tranType
	 * @return
	 */
	@Override
	public Set<Integer> getUserAuditLeave(String userId, String sonId, String tranType) {
		List<TSAuditEntity> tsAuditEntityList = this.findHql("from TSAuditEntity where billId = ? and sonId = ?",new Object[]{tranType,sonId});
		Set<Integer> leaveInfo = new HashSet<Integer>();
		if(tsAuditEntityList.size() > 0){
			TSAuditEntity entity = tsAuditEntityList.get(0);
			String pid = entity.getId();
			List<TSAuditLeaveEntity> leaveEntityList = this.findHql("from TSAuditLeaveEntity where pid = ?",pid);
			if(leaveEntityList.size() > 0){
				for(TSAuditLeaveEntity leaveEntity : leaveEntityList){
					String userIds = leaveEntity.getAuditorId();
					if(userIds.indexOf(userId) > -1){
						leaveInfo.add(Integer.parseInt(leaveEntity.getLeaveNum()));
					}
				}
			}
		}
		return leaveInfo;
	}
}
