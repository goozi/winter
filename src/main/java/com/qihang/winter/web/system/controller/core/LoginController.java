package com.qihang.winter.web.system.controller.core;

import com.qihang.buss.sc.sys.entity.TScAccountConfigEntity;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.constant.DataBaseConstant;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.enums.SysThemesEnum;
import com.qihang.winter.core.extend.datasource.DataSourceContextHolder;
import com.qihang.winter.core.extend.datasource.DataSourceType;
import com.qihang.winter.core.util.*;
import com.qihang.winter.web.system.manager.ClientManager;
import com.qihang.winter.web.system.pojo.base.*;
import com.qihang.winter.web.system.service.MutiLangServiceI;
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.web.system.service.UserService;
import com.softkey.f2k;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * 登陆初始化控制器
 *
 * @author Zerrion
 */
@Scope("prototype")
@Controller
@RequestMapping("/loginController")
public class LoginController extends BaseController {
  private Logger log = Logger.getLogger(LoginController.class);
  private SystemService systemService;
  private UserService userService;
  private String message = null;
  private String sn;

  @Autowired
  private MutiLangServiceI mutiLangService;

//  @Autowired
//  private TApSonCompanyServiceI tApSonCompanyService;

  @Autowired
  public void setSystemService(SystemService systemService) {
    this.systemService = systemService;
  }

  @Autowired
  public void setUserService(UserService userService) {

    this.userService = userService;
  }

  @RequestMapping(params = "goPwdInit")
  public String goPwdInit() {
    return "login/pwd_init";
  }

  /**
   * admin账户密码初始化
   *
   * @param request
   * @return
   */
  @RequestMapping(params = "pwdInit")
  public ModelAndView pwdInit(HttpServletRequest request) {
    ModelAndView modelAndView = null;
    TSUser user = new TSUser();
    user.setUserName("admin");
    String newPwd = "123456";
    userService.pwdInit(user, newPwd);
    modelAndView = new ModelAndView(new RedirectView(
            "loginController.do?login"));
    return modelAndView;
  }

  /**
   * 检查用户名称
   *
   * @param user
   * @param req
   * @return
   */
  @RequestMapping(params = "checkuser")
  @ResponseBody
  public AjaxJson checkuser(TSUser user, HttpServletRequest req) {
    HttpSession session = ContextHolderUtils.getSession();
    //DataSourceContextHolder.setDataSourceType(DataSourceType.dataSource_jeecg);
    //获得当前要登录的账套，如果账套为空，则默认登录到scm主库
    String dbKey = req.getParameter("dbKey")==null?"":req.getParameter("dbKey").toString();
    if (dbKey.equals("")){
      DataSourceContextHolder.setDataSourceType(DataSourceType.dataSource_jeecg);
      req.getSession().setAttribute("dataSourceType","dataSource_jeecg");
      req.getSession().setAttribute("accountStartDate","");
      //todo:临时测试用，上线前要清除
      req.getSession().setAttribute("accountId",Globals.ACCOUNT_SCM_ID);//根据建议，账套表加一条scm的记录，但在账套管理中不显示, "402880fd574066fe01574068ac400001");//暂连接到scm的newdb16库"
    }else{
      //判断要登录的账套数据源是否已经建立，如果已经建立则直接切换到指定账套数据源
      if (DynamicDataSourceEntity.DynamicDataSourceMap.containsKey(dbKey)){
        //判断枚举是否已经存在
        boolean isExistsEnum = false;
        for (DataSourceType dst : DataSourceType.values()){
          if (dst.toString().equals(dbKey)){
            isExistsEnum = true;
            break;
          }
        }
        if (isExistsEnum){//存在
          DynamicDataSourceEntity dynamicDataSourceEntity = DynamicDataSourceEntity.DynamicDataSourceMap.get(dbKey);
          try {
            DataSourceContextHolder.setDataSourceType(DataSourceType.valueOf(dynamicDataSourceEntity.getDbKey()));
          }catch(Exception e){
            //EnumBuster<DataSourceType> buster = new EnumBuster<DataSourceType>(DataSourceType.class);
            //DataSourceType newdataSourceType = buster.make(dbKey);
            //buster.addByValue(newdataSourceType);
            //DataSourceContextHolder.setDataSourceType(newdataSourceType);
            DynamicEnumUtil.addEnum(DataSourceType.class, dbKey);
            DataSourceContextHolder.setDataSourceType(DataSourceType.valueOf(dynamicDataSourceEntity.getDbKey()));
          }
        }else {//不存在
//          EnumBuster<DataSourceType> buster = new EnumBuster<DataSourceType>(DataSourceType.class);
//          DataSourceType newdataSourceType = buster.make(dbKey);
//          buster.addByValue(newdataSourceType);
//          DataSourceContextHolder.setDataSourceType(newdataSourceType);
          DynamicEnumUtil.addEnum(DataSourceType.class, dbKey);
          DataSourceContextHolder.setDataSourceType(DataSourceType.valueOf(dbKey));
        }
      }else{
        //不存在，则动态创建新的容器数据源连接，并切换到新数据源连接
//          DynamicDataSourceEntity dynamicDataSourceEntity = null;
//          List<DynamicDataSourceEntity> tSDataSourceEntityList= systemService.findHql("from DynamicDataSourceEntity t where t.dbKey=?",dbKey);
//          if (tSDataSourceEntityList.size()>0) {
//              dynamicDataSourceEntity = tSDataSourceEntityList.get(0);
//              DynamicDataSourceEntity.DynamicDataSourceMap.put(dbKey, dynamicDataSourceEntity);
//          }
        //todo:补充往数据源map加对象
        DynamicDataSourceEntity  dynamicSourceEntity = DBUtil.getDynamicDataSourceByParameter("db_key", dbKey);
        DynamicDataSourceEntity.DynamicDataSourceMap.put(dynamicSourceEntity.getDbKey(), dynamicSourceEntity);
//        EnumBuster<DataSourceType> buster = new EnumBuster<DataSourceType>(DataSourceType.class);
//        DataSourceType newdataSourceType = buster.make(dbKey);
//        buster.addByValue(newdataSourceType);
//        DataSourceContextHolder.setDataSourceType(newdataSourceType);
        DynamicEnumUtil.addEnum(DataSourceType.class, dbKey);
        DataSourceContextHolder.setDataSourceType(DataSourceType.valueOf(dbKey));
      }
      DynamicDataSourceEntity  dynamicSourceEntity = DBUtil.getDynamicDataSourceByParameter("db_key", dbKey);
      req.getSession().setAttribute("dataSourceType",dbKey);//当前账套db_key
      req.getSession().setAttribute("accountBookName",dynamicSourceEntity.getDescription());//当前账套名
      TScAccountConfigEntity accountConfigEntity = systemService.getCurrentAccountConfigByDbkey(dbKey);
      req.getSession().setAttribute("accountConfig",accountConfigEntity.getAccountStartDate());//当前账套当期年月
      req.getSession().setAttribute("accountId",accountConfigEntity.getId());//当前账套ID
    }
    //测试切换数据源的连接信息[测试通过后需清除]
    //systemService.getConn();
    AjaxJson j = new AjaxJson();
    Boolean needUSB = Boolean.parseBoolean(ResourceUtil.getConfigByName("NEEDUSB"));
    if (needUSB) {
      f2k f2k = new f2k();
      //判断是否插入加密狗
      String isUSB = f2k.open();
      if (StringUtils.isNotEmpty(isUSB)) {
        j.setSuccess(false);
        j.setMsg(isUSB);
        return j;
      } else {
        String localVersion = ResourceUtil.getConfigByName("VERSION");
        sn = f2k.sn();
        Integer permission = f2k.getPermission(localVersion);
        if (permission == 0) {
          j.setSuccess(false);
          j.setMsg("该加密锁不具有该平台的使用权限");
          return j;
        }
        req.getSession().setAttribute("permission", permission);
      }
    }
    if (req.getParameter("langCode") != null) {
      req.getSession().setAttribute("lang", req.getParameter("langCode"));
    }
    String randCode = req.getParameter(SESSION_KEY_OF_RAND_CODE);
    if (StringUtils.isEmpty(randCode)) {
      j.setMsg(mutiLangService.getLang("common.enter.verifycode"));
      j.setSuccess(false);
    } else if (!randCode.equalsIgnoreCase(String.valueOf(session.getAttribute(SESSION_KEY_OF_RAND_CODE)))) {
      j.setMsg(mutiLangService.getLang("common.verifycode.error"));
      j.setSuccess(false);
    } else {
      int users = userService.getList(TSUser.class).size();

      if (users == 0) {
        j.setMsg("a");
        j.setSuccess(false);
      } else {
        TSUser u = userService.checkUserExits(user);
        if (u == null) {
          j.setMsg(mutiLangService.getLang("common.username.or.password.error"));
          j.setSuccess(false);
          return j;
        }
        TSUser u2 = userService.getEntity(TSUser.class, u.getId());

        if (u != null && u2.getStatus() != 0) {
          // if (user.getUserKey().equals(u.getUserKey())) {


          if (true) {
            Map<String, Object> attrMap = new HashMap<String, Object>();
            j.setAttributes(attrMap);
            if (ResourceUtil.getConfigByName("ISONLY").equals("true")) {
              String userId = u2.getId();
              if (sessionList.get(userId) != null) {
                String sessionId = sessionList.get(userId);
                MySessionContext context = MySessionContext.getInstance();
                HttpSession httpsession = context.getSession(sessionId);
                if (httpsession != null) {
                  httpsession.setAttribute("hasLogOut", "hasLogOut");
                }
                //ClientManager.getInstance().removeClinet(sessionId);
                //httpsession.invalidate();
                sessionList.put(userId, session.getId());
              } else {
                sessionList.put(userId, session.getId());
              }
            }
            String orgId = req.getParameter("orgId");
            if (oConvertUtils.isEmpty(orgId)) { // 没有传组织机构参数，则获取当前用户的组织机构
              Long orgNum = systemService.getCountForJdbc("select count(1) from t_s_user_org where user_id = '" + u.getId() + "'");
              if (orgNum > 1) {
                attrMap.put("orgNum", orgNum);
                attrMap.put("user", u2);
              } else {
                Map<String, Object> userOrgMap = systemService.findOneForJdbc("select org_id as orgId from t_s_user_org where user_id=?", u2.getId());
                saveLoginSuccessInfo(req, u2, (String) userOrgMap.get("orgId"));
              }
            } else {
              attrMap.put("orgNum", 1);

              saveLoginSuccessInfo(req, u2, orgId);
            }
          } else {
            j.setMsg(mutiLangService.getLang("common.check.shield"));
            j.setSuccess(false);
          }
        } else {
          j.setMsg(mutiLangService.getLang("common.check.shield"));
          j.setSuccess(false);
        }
      }
    }
    return j;
  }

  /**
   * 保存用户登录的信息，并将当前登录用户的组织机构赋值到用户实体中；
   *
   * @param req   request
   * @param user  当前登录用户
   * @param orgId 组织主键
   */
  private void saveLoginSuccessInfo(HttpServletRequest req, TSUser user, String orgId) {
    TSDepart currentDepart = systemService.get(TSDepart.class, orgId);
    user.setCurrentDepart(currentDepart);

    HttpSession session = ContextHolderUtils.getSession();
    message = mutiLangService.getLang("common.user") + ": " + user.getUserName() + "["
            + currentDepart.getDepartname() + "]" + mutiLangService.getLang("common.login.success");

    Client client = new Client();
    client.setIp(IpUtil.getIpAddr(req));
    client.setLogindatetime(new Date());
    client.setUser(user);
    ClientManager.getInstance().addClinet(session.getId(), client);
    // 添加登陆日志
    systemService.addLog(message, Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO);
  }

  /**
   * 用户登录
   *
   * @param request
   * @return
   */
  @RequestMapping(params = "login")
  public String login(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    //DataSourceContextHolder.setDataSourceType(DataSourceType.dataSource_jeecg);//hjhdel20160906注释掉设置默认数据源
    TSUser user = ResourceUtil.getSessionUserName();
    StringBuffer roles = new StringBuffer();
    StringBuffer roleIds = new StringBuffer();
    String rolesStr=null, roleIdsStr=null;
    if (user != null) {
      List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
      for (TSRoleUser ru : rUsers) {
        TSRole role = ru.getTSRole();
        if (role!=null) {
//        roles += role.getRoleName() + ",";
          roles.append(role.getRoleName() + ",");
//        roleIds += role.getId() + ",";
          roleIds.append(role.getId() + ",");
        }
      }
      if (roles.length() > 0) {
        rolesStr = roles.substring(0, roles.length() - 1);
        roleIdsStr = roleIds.substring(0, roleIds.length() - 1);
      }
      //当前分支机构名称；
      TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
      TSDepart depart = systemService.getParentSonInfo(sonInfo);
      request.getSession().setAttribute("departName",depart.getDepartname());

      modelMap.put("roleName", roles);
      modelMap.put("userName", user.getUserName());
      modelMap.put("currentOrgName", ClientManager.getInstance().getClient().getUser().getCurrentDepart().getDepartname());
      //todo:临时测试用，上线前要清除
      //request.getSession().setAttribute("accountId", "402880fb556d2c2701556d2fbf0e0001");
      request.getSession().setAttribute("CKFinder_UserRole", "admin");
      request.getSession().setAttribute(DataBaseConstant.SYS_SON_ID, user.getSonCompanyId());

      request.getSession().setAttribute(DataBaseConstant.SYS_COM_ID, user.getComId());
      request.getSession().setAttribute("roleIds", roleIdsStr);
      request.getSession().setAttribute("user", user);
      request.getSession().setAttribute("role", rolesStr);
/*			// 默认风格
            String indexStyle = "shortcut";
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				if (cookie == null || StringUtils.isEmpty(cookie.getName())) {
					continue;
				}
				if (cookie.getName().equalsIgnoreCase("JEECGINDEXSTYLE")) {
					indexStyle = cookie.getValue();
				}
			}
			// 要添加自己的风格，复制下面三行即可
      request.getSession().setAttribute(DataBaseConstant.SYS_SON_ID, user.getSonId());

      request.getSession().setAttribute(DataBaseConstant.SYS_COM_ID, user.getComId());
      request.getSession().setAttribute("user", user);
      request.getSession().setAttribute("role", roles);

      //传送用户的登录信息到公司webservice,518为农资市场备份平台版本号
      /*try {
        TApSonCompanyEntity company = tApSonCompanyService.getById(user.getSonId());

        if (company != null) {
          new PutUserLog(sn, company.getAddress(), company.getName(), company.getContact(), company.getEmail(), request.getRemoteAddr(), company.getPhone(), user.getUserName(), "518");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }*/

      // 默认风格
//			String indexStyle = "default";
//      String indexStyle = "ace";
//      Cookie cookie = new Cookie("JEECGINDEXSTYLE", indexStyle);
//      cookie.setPath("/");
//      response.addCookie(cookie);
//			Cookie[] cookies = request.getCookies();
//			for (Cookie cookie : cookies) {
//				if (cookie == null || StringUtils.isEmpty(cookie.getName())) {
//					continue;
//				}
//				if (cookie.getName().equalsIgnoreCase("JEECGINDEXSTYLE")) {
//					indexStyle = cookie.getValue();
//				}
//			}
      // 要添加自己的风格，复制下面三行即可
//			if (StringUtils.isNotEmpty(indexStyle)
//					&& indexStyle.equalsIgnoreCase("bootstrap")) {
//				return "main/bootstrap_main";
//			}
//			if (StringUtils.isNotEmpty(indexStyle)
//					&& indexStyle.equalsIgnoreCase("shortcut")) {
//				return "main/shortcut_main";
//			}
//
//			if (StringUtils.isNotEmpty(indexStyle)
//					&& indexStyle.equalsIgnoreCase("sliding")) {
//				return "main/sliding_main";
//			}
//			if (StringUtils.isNotEmpty(indexStyle)&&
//					!"default".equalsIgnoreCase(indexStyle)&&
//					!"undefined".equalsIgnoreCase(indexStyle)) {
//				if("ace".equals(indexStyle)){
//					request.setAttribute("menuMap", getFunctionMap(user));
//				}
//				log.info("main/"+indexStyle.toLowerCase()+"_main");
//				return "main/"+indexStyle.toLowerCase()+"_main";
//			}
      SysThemesEnum sysTheme = SysThemesUtil.getSysTheme(request);
      //if ("shortcut".equals(sysTheme.getStyle())) {
      request.setAttribute("menuMap", getFunctionMap(user));
      //}
      return sysTheme.getIndexPath();
    } else {
      return "login/scm_login";
    }

  }

  /**
   * 退出系统
   *
   * @param request
   * @return
   */
  @RequestMapping(params = "logout")
  public ModelAndView logout(HttpServletRequest request) {
    HttpSession session = ContextHolderUtils.getSession();
    TSUser user = ResourceUtil.getSessionUserName();
    systemService.addLog("用户" + user.getUserName() + "已退出",
            Globals.Log_Type_EXIT, Globals.Log_Leavel_INFO);
    ClientManager.getInstance().removeClinet(session.getId());
    sessionList.remove(user.getId());
    session.invalidate();
    ModelAndView modelAndView = new ModelAndView(new RedirectView(
            "loginController.do?login"));
    return modelAndView;
  }

  /**
   * 菜单跳转
   *
   * @return
   */
  @RequestMapping(params = "left")
  public ModelAndView left(HttpServletRequest request) {
    TSUser user = ResourceUtil.getSessionUserName();
    HttpSession session = ContextHolderUtils.getSession();
    ModelAndView modelAndView = new ModelAndView();
    // 登陆者的权限
    if (user.getId() == null) {
      session.removeAttribute(Globals.USER_SESSION);
      modelAndView.setView(new RedirectView("loginController.do?login"));
    } else {
      List<TSConfig> configs = userService.loadAll(TSConfig.class);
      for (TSConfig tsConfig : configs) {
        request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
      }
      modelAndView.setViewName("main/left");
      request.setAttribute("menuMap", getFunctionMap(user));
    }
    return modelAndView;
  }

  /**
   * 获取权限的map
   *
   * @param user
   * @return
   */
  private Map<Integer, List<TSFunction>> getFunctionMap(TSUser user) {
    HttpSession session = ContextHolderUtils.getSession();
    Client client = ClientManager.getInstance().getClient(session.getId());
    if (client.getFunctionMap() == null || client.getFunctionMap().size() == 0) {
      Map<Integer, List<TSFunction>> functionMap = new HashMap<Integer, List<TSFunction>>();
      Map<String, TSFunction> loginActionlist = getUserFunction(user);
      if (loginActionlist.size() > 0) {
        Collection<TSFunction> allFunctions = loginActionlist.values();
        for (TSFunction function : allFunctions) {
          if (function.getFunctionType().intValue() == Globals.Function_TYPE_FROM.intValue()) {
            //如果为表单或者弹出 不显示在系统菜单里面
            continue;
          }
          if (!functionMap.containsKey(function.getFunctionLevel() + 0)) {
            functionMap.put(function.getFunctionLevel() + 0,
                    new ArrayList<TSFunction>());
          }
          functionMap.get(function.getFunctionLevel() + 0).add(function);
        }
        // 菜单栏排序
        Collection<List<TSFunction>> c = functionMap.values();
        for (List<TSFunction> list : c) {
          Collections.sort(list, new NumberComparator());
        }
      }
      client.setFunctionMap(functionMap);
      return functionMap;
    } else {
      return client.getFunctionMap();
    }
  }

  /**
   * 获取用户菜单列表
   *
   * @param user
   * @return
   */
  private Map<String, TSFunction> getUserFunction(TSUser user) {
    HttpSession session = ContextHolderUtils.getSession();
    Client client = ClientManager.getInstance().getClient(session.getId());
    if (client.getFunctions() == null || client.getFunctions().size() == 0) {
      Map<String, TSFunction> loginActionlist = new HashMap<String, TSFunction>();
       /*String hql="from TSFunction t where t.id in  (select d.TSFunction.id from TSRoleFunction d where d.TSRole.id in(select t.TSRole.id from TSRoleUser t where t.TSUser.id ='"+
             user.getId()+"' ))";
	           String hql2="from TSFunction t where t.id in  ( select b.tsRole.id from TSRoleOrg b where b.tsDepart.id in(select a.tsDepart.id from TSUserOrg a where a.tsUser.id='"+
	           user.getId()+"'))";
	           List<TSFunction> list = systemService.findHql(hql);
	           log.info("role functions:  "+list.size());
	           for(TSFunction function:list){
	              loginActionlist.put(function.getId(),function);
	           }
	           List<TSFunction> list2 = systemService.findHql(hql2);
	           log.info("org functions: "+list2.size());
	           for(TSFunction function:list2){
	              loginActionlist.put(function.getId(),function);
	           }*/
      StringBuilder hqlsb1 = new StringBuilder("select distinct f from TSFunction f,TSRoleFunction rf,TSRoleUser ru  ")
              .append("where ru.TSRole.id=rf.TSRole.id and rf.TSFunction.id=f.id and ru.TSUser.id=? ");
      StringBuilder hqlsb2 = new StringBuilder("select distinct c from TSFunction c,TSRoleOrg b,TSUserOrg a ")
              .append("where a.tsDepart.id=b.tsDepart.id and b.tsRole.id=c.id and a.tsUser.id=?");
      List<TSFunction> list1 = systemService.findHql(hqlsb1.toString(), user.getId());
      List<TSFunction> list2 = systemService.findHql(hqlsb2.toString(), user.getId());
      for (TSFunction function : list1) {
        loginActionlist.put(function.getId(), function);
      }
      for (TSFunction function : list2) {
        loginActionlist.put(function.getId(), function);
      }
      client.setFunctions(loginActionlist);
    }
    return client.getFunctions();
  }

  /**
   * 根据 角色实体 组装 用户权限列表
   *
   * @param loginActionlist 登录用户的权限列表
   * @param role            角色实体
   * @deprecated
   */
  private void assembleFunctionsByRole(Map<String, TSFunction> loginActionlist, TSRole role) {
    List<TSRoleFunction> roleFunctionList = systemService.findByProperty(TSRoleFunction.class, "TSRole.id", role.getId());
    for (TSRoleFunction roleFunction : roleFunctionList) {
      TSFunction function = roleFunction.getTSFunction();
      if (function.getFunctionType().intValue() == Globals.Function_TYPE_FROM.intValue()) {
        //如果为表单或者弹出 不显示在系统菜单里面
        continue;
      }
      loginActionlist.put(function.getId(), function);
    }
  }

  /**
   * 首页跳转
   *
   * @return
   */
  @RequestMapping(params = "home")
  public ModelAndView home(HttpServletRequest request) {
    return new ModelAndView("main/homeNew_scm");
  }

  /**
   * 导航跳转
   * @param request
   * @return
   */
  @RequestMapping(params = "navigation")
  public ModelAndView navigation(HttpServletRequest request) {
    return new ModelAndView("com/qihang/buss/sc/navigate/shop_navi");
  }

  /**
   * 无权限页面提示跳转
   *
   * @return
   */
  @RequestMapping(params = "noAuth")
  public ModelAndView noAuth(HttpServletRequest request) {
    return new ModelAndView("common/noAuth");
  }

  /**
   * @param request
   * @return ModelAndView
   * @throws
   * @Title: top
   * @Description: bootstrap头部菜单请求
   */
  @RequestMapping(params = "top")
  public ModelAndView top(HttpServletRequest request) {
    TSUser user = ResourceUtil.getSessionUserName();
    HttpSession session = ContextHolderUtils.getSession();
    // 登陆者的权限
    if (user.getId() == null) {
      session.removeAttribute(Globals.USER_SESSION);
      return new ModelAndView(
              new RedirectView("loginController.do?login"));
    }
    request.setAttribute("menuMap", getFunctionMap(user));
    List<TSConfig> configs = userService.loadAll(TSConfig.class);
    for (TSConfig tsConfig : configs) {
      request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
    }
    return new ModelAndView("main/bootstrap_top");
  }

  /**
   * @param request
   * @return ModelAndView
   * @throws
   * @Title: top
   * @author gaofeng
   * @Description: shortcut头部菜单请求
   */
  @RequestMapping(params = "shortcut_top")
  public ModelAndView shortcut_top(HttpServletRequest request) {
    TSUser user = ResourceUtil.getSessionUserName();
    HttpSession session = ContextHolderUtils.getSession();
    // 登陆者的权限
    if (user.getId() == null) {
      session.removeAttribute(Globals.USER_SESSION);
      return new ModelAndView(
              new RedirectView("loginController.do?login"));
    }
    request.setAttribute("menuMap", getFunctionMap(user));
    List<TSConfig> configs = userService.loadAll(TSConfig.class);
    for (TSConfig tsConfig : configs) {
      request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
    }
    return new ModelAndView("main/shortcut_top");
  }

  /**
   * @return AjaxJson
   * @throws
   * @Title: top
   * @author:gaofeng
   * @Description: shortcut头部菜单一级菜单列表，并将其用ajax传到页面，实现动态控制一级菜单列表
   */
  @RequestMapping(params = "primaryMenu")
  @ResponseBody
  public String getPrimaryMenu() {
    List<TSFunction> primaryMenu = getFunctionMap(ResourceUtil.getSessionUserName()).get(0);
    String floor = "";
    if (primaryMenu == null) {
      return floor;
    }
    for (TSFunction function : primaryMenu) {
      if (function.getFunctionLevel() == 0) {

        String lang_key = function.getFunctionName();
        String lang_context = mutiLangService.getLang(lang_key);

        if ("Online 开发".equals(lang_context)) {

          floor += " <li><img class='imag1' src='plug-in/login/images/online.png' /> "
                  + " <img class='imag2' src='plug-in/login/images/online_up.png' style='display: none;' />" + " </li> ";
        } else if ("统计查询".equals(lang_context)) {

          floor += " <li><img class='imag1' src='plug-in/login/images/guanli.png' /> "
                  + " <img class='imag2' src='plug-in/login/images/guanli_up.png' style='display: none;' />" + " </li> ";
        } else if ("系统管理".equals(lang_context)) {

          floor += " <li><img class='imag1' src='plug-in/login/images/xtgl.png' /> "
                  + " <img class='imag2' src='plug-in/login/images/xtgl_up.png' style='display: none;' />" + " </li> ";
        } else if ("常用示例".equals(lang_context)) {

          floor += " <li><img class='imag1' src='plug-in/login/images/cysl.png' /> "
                  + " <img class='imag2' src='plug-in/login/images/cysl_up.png' style='display: none;' />" + " </li> ";
        } else if ("系统监控".equals(lang_context)) {

          floor += " <li><img class='imag1' src='plug-in/login/images/xtjk.png' /> "
                  + " <img class='imag2' src='plug-in/login/images/xtjk_up.png' style='display: none;' />" + " </li> ";
        } else if (lang_context.contains("消息推送")) {
          String s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'>消息推送</div>";
          floor += " <li style='position: relative;'><img class='imag1' src='plug-in/login/images/msg.png' /> "
                  + " <img class='imag2' src='plug-in/login/images/msg_up.png' style='display: none;' />"
                  + s + "</li> ";
        } else {
          //其他的为默认通用的图片模式
          String s = "";
          if (lang_context.length() >= 5 && lang_context.length() < 7) {
            s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'><span style='letter-spacing:-1px;'>" + lang_context + "</span></div>";
          } else if (lang_context.length() < 5) {
            s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'>" + lang_context + "</div>";
          } else if (lang_context.length() >= 7) {
            s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'><span style='letter-spacing:-1px;'>" + lang_context.substring(0, 6) + "</span></div>";
          }
          floor += " <li style='position: relative;'><img class='imag1' src='plug-in/login/images/default.png' /> "
                  + " <img class='imag2' src='plug-in/login/images/default_up.png' style='display: none;' />"
                  + s + "</li> ";
        }
      }
    }

    return floor;
  }


  /**
   * 云桌面返回：用户权限菜单
   */
  @RequestMapping(params = "getPrimaryMenuForWebos")
  @ResponseBody
  public AjaxJson getPrimaryMenuForWebos() {
    AjaxJson j = new AjaxJson();
    //将菜单加载到Session，用户只在登录的时候加载一次
    Object getPrimaryMenuForWebos = ContextHolderUtils.getSession().getAttribute("getPrimaryMenuForWebos");
    if (oConvertUtils.isNotEmpty(getPrimaryMenuForWebos)) {
      j.setMsg(getPrimaryMenuForWebos.toString());
    } else {
      String PMenu = ListtoMenu.getWebosMenu(getFunctionMap(ResourceUtil.getSessionUserName()));
      ContextHolderUtils.getSession().setAttribute("getPrimaryMenuForWebos", PMenu);
      j.setMsg(PMenu);
    }
    return j;
  }

  @RequestMapping(params = "polling")
  @ResponseBody
  public AjaxJson polling(HttpServletRequest req, HttpSession session) throws InterruptedException {
    String result = "";
    AjaxJson j = new AjaxJson();
    if (ResourceUtil.getConfigByName("ISONLY").equals("true")) {
      while (true) {
        MySessionContext context = MySessionContext.getInstance();
        HttpSession httpsession = context.getSession(session.getId());
        if (httpsession.getAttribute("hasLogOut") != null) {
          session.invalidate();
          j.setMsg("hasLogOut");
          ClientManager.getInstance().removeClinet(session.getId());
          session.invalidate();
          break;
        } else {
          Thread.sleep(2000);
        }
      }
    }

    return j;
  }

  /**
   * 首页跳转
   *
   * @return
   */
  @RequestMapping(params = "navigate")
  public ModelAndView navigate(HttpServletRequest request, @RequestParam("type") short type) {
    if (type == Globals.INIT_NAVI) {
      return new ModelAndView("com/qihang/buss/sc/navigate/init_navi");
    } else if (type == Globals.BASEINFO_NAVI) {
      return new ModelAndView("com/qihang/buss/sc/navigate/baseinfo_navi");
    } else if (type == Globals.SHOP_NAVI) {
      return new ModelAndView("com/qihang/buss/sc/navigate/shop_navi");
    } else if (type == Globals.INVENTORY_NAVI) {
      return new ModelAndView("com/qihang/buss/sc/navigate/inventory_navi");
    } else if (type == Globals.SALE_NAVI) {
      return new ModelAndView("com/qihang/buss/sc/navigate/sale_navi");
    } else if (type == Globals.FINANCING_NAVI) {
      return new ModelAndView("com/qihang/buss/sc/navigate/financing_navi");
    } else if (type == Globals.XS_NAVI){
      return new ModelAndView("com/qihang/buss/sc/navigate/xs_navi");
    }
    else {
      return new ModelAndView("com/qihang/buss/sc/navigate/oa_navi");
    }

  }


  //手机端登录
  @RequestMapping(params = "login_app", method = RequestMethod.POST)
  @ResponseBody
  public AjaxJson login_app(TSUser user) {
    AjaxJson j = new AjaxJson();
    TSUser u = userService.checkUserExits(user);
    if (u == null) {
      j.setMsg(mutiLangService.getLang("common.username.or.password.error"));
      j.setSuccess(false);
      return j;
    } else {
      Map<String, Object> info = new HashMap<String, Object>();
      info.put("userId", u.getId());
      info.put("userName", u.getUserName());
      info.put("userRealName", u.getRealName());
      j.setObj(info);
      return j;
    }
  }

  /**
   * 获取验证码字符
   *
   * @param request
   * @return
   */
  @RequestMapping(params = "getRandCode", method = RequestMethod.GET)
  public void getRandCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String randCode = request.getSession().getAttribute("randCode").toString();
    PrintWriter out = response.getWriter();
    out.print(randCode);
    out.close();
  }
}