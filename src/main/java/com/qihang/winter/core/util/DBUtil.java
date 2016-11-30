package com.qihang.winter.core.util;

import com.qihang.buss.sc.sys.entity.TScAccountConfigEntity;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.web.system.pojo.base.DynamicDataSourceEntity;
import jodd.io.FileUtil;

import java.io.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016-09-09.
 */
public class DBUtil {
    /**
     * 从配置文件读取是否开启多数据源切换配置
     * @return false表示只开启主库（即关闭多数据源切换），true表示启用多数据源切换
     * @author hjh 2016-9-20
     */
    public static boolean isOpenMultDataSource(){
        PropertiesUtil propertiesUtil = new PropertiesUtil("sysConfig.properties");
        String isOpen =propertiesUtil.readProperty("isOpenMultDataSource");//读取数据库bin目录位置
        if (isOpen!=null && isOpen.toLowerCase().equals("true")){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 获得当前连接的db_key及参数,从配置参数文件中读取数据库bin文件路径和初始化数据sql文件，根据新库名创建新的数据库,增加数据源配置记录，并初始化数据
     *   目前仅支持对同一数据库服务器建新用户
     * @param tSDataSource 新数据源
     * @author hjh 2016-8-30
     */
    public static boolean createDatabaseAndInitialize(TScAccountConfigEntity tScAccountConfig, DynamicDataSourceEntity tSDataSource){
        boolean isOk = false;
        try{
            String databaseName = tSDataSource.getDbKey();
            String description = tSDataSource.getDescription();
            PropertiesUtil propertiesUtil = new PropertiesUtil("sysConfig.properties");
            String dbbinpath =propertiesUtil.readProperty("dbbinpath");//读取数据库bin目录位置
            dbbinpath = dbbinpath.replace("\\",File.separator);
            if (dbbinpath!=null && !dbbinpath.substring(dbbinpath.length()-1).equals(File.separator)){
                dbbinpath += File.separator;
            }
            //改用一个新的jdbc connect连接到新数据库
            java.sql.Connection newconn = null;
            try{
                //1.连接到新数据库的默认数据库或实例
                Class.forName(tSDataSource.getDriverClass());
                String dbType = tSDataSource.getDbType().toLowerCase();
                String mainDB = Globals.DB_MAIN_DB_MYSQL;//默认mysql
                if (dbType.equals(Globals.DB_TYPE_ORACLE)){
                    mainDB = tSDataSource.getDbKey();//oracle是在新用户下创建实例
                }else if (dbType.equals(Globals.DB_TYPE_SQLSERVER)){
                    mainDB = Globals.DB_MAIN_DB_SQLSERVER;
                }
                newconn = DriverManager.getConnection(tSDataSource.getUrl().replace(tSDataSource.getDbKey(),mainDB), tSDataSource.getDbUser(), tSDataSource.getDbPassword());//获取新数据库连接
                Statement stmt = newconn.createStatement();
                //2.创建新数据库（sqlserver和mysql会新数据库，oracle只创建用户）
                String dbdatapath = propertiesUtil.readProperty("dbdatapath");//数据库文件存放位置
                String createDbSql = "create database " + databaseName + " DEFAULT CHARSET utf8 COLLATE utf8_unicode_ci";
                if (dbType.equals(Globals.DB_TYPE_SQLSERVER)) {
                    createDbSql = "create database " + databaseName;
                }else if (dbType.equals(Globals.DB_TYPE_SQLSERVER)){
                    //创建表空间
                    createDbSql = "create tablespace " + databaseName + " datafile '" + dbdatapath + File.separator + databaseName + ".dbf' size 500M autoextend on next 5M maxsize 3000M";
                }
                int createDbInt = stmt.executeUpdate(createDbSql);
                LogUtil.info("创建新数据库：" + databaseName);
                //3.创建数据库新用户（用账套名称）
                String createUserSql = "create user '" + databaseName + "' @'%' identified by '" + databaseName + "'";//默认mysql
                if (dbType.equals(Globals.DB_TYPE_ORACLE)){
                    createUserSql = "create user " + databaseName + " identified by " + databaseName + " default tablespace " + databaseName;
                }else if (dbType.equals(Globals.DB_TYPE_SQLSERVER)){
                    createUserSql = "create login " + databaseName + " with password='" + databaseName + "', default_database=" + databaseName;
                }
                int createUserInt = stmt.executeUpdate(createUserSql);
                LogUtil.info("创建新数据库用户：" + databaseName);
                //4.给新用户授权（用账套名称）
                //String createGrantSql = "grant all on " + databaseName + ".* to '" + databaseName + "'@'%'";//默认mysql
                String createGrantSql = "grant SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, CREATE TEMPORARY TABLES, LOCK TABLES, EXECUTE, CREATE VIEW, SHOW VIEW, CREATE ROUTINE, ALTER ROUTINE on " + databaseName + ".* to '" + databaseName + "'@'%'";//因为192.168.0.3数据库有几个权限不能授权，所有暂不用all给指定用户
                //String createGrantSql = "grant all PRIVILEGES on " + databaseName + ".* to '" + databaseName + "'@'%'";//普通用户权限
                if (dbType.equals(Globals.DB_TYPE_ORACLE)){
                    createGrantSql = "grant dba to " + databaseName;
                }else if (dbType.equals(Globals.DB_TYPE_SQLSERVER)){
                    createGrantSql = "use " + databaseName ;
                    stmt.executeUpdate(createGrantSql);
                    createGrantSql = "exec sp_addrolemember 'db_owner', 'dba' ";
                }else if (dbType.equals(Globals.DB_TYPE_MYSQL)){
                    stmt.executeUpdate(createGrantSql);
                    createGrantSql = "grant SELECT on *.* to '" + databaseName + "'@'%'";
                }
                int createGrantInt = stmt.executeUpdate(createGrantSql);
                LogUtil.info("给新用户：" + databaseName + " 授权");
                stmt.close();
                //5.用新创建用户来执行后续数据库操作
                tSDataSource.setDbUser(databaseName);
                tSDataSource.setDbPassword(databaseName);
                //6.(Mysql)从当前主库复制（数据表、视图）到目标（新库）数据库中
                DBUtil.copyDatabase(tSDataSource, dbbinpath);
                //todo:7.是否应该这里创建新的数据源连接放到应用容器中?
                //动态创建新的容器数据源连接，并切换到新数据源连接
//				EnumBuster<DataSourceType> buster = new EnumBuster<DataSourceType>(DataSourceType.class);
//				DataSourceType newdataSourceType = buster.make(tSDataSource.getDbKey());//buster.make(dynamicDataSourceEntity.getDbKey());
//				buster.addByValue(newdataSourceType);
//				DataSourceContextHolder.setDataSourceType(newdataSourceType);
                //todo:8.初始化数据：用于复制后的清除数据SQL或创建函数、存储过程、触发器SQL
                newconn = DriverManager.getConnection(tSDataSource.getUrl(), tSDataSource.getDbUser(), tSDataSource.getDbPassword());//获取新数据库连接
                stmt = newconn.createStatement();
                //从系统配置参数文件中读取数据库bin文件路径
                String sourceFilePath = propertiesUtil.readProperty("dbbakSourceFilePath");
                String[] sourceFilePathArray = sourceFilePath.split(",");
                Map<String, String> newDbmap = DBUtil.getDbParameterByUrl(tSDataSource.getUrl());
                String newip = newDbmap.containsKey("ip")?newDbmap.get("ip"):"";
                String newport = newDbmap.containsKey("port")?newDbmap.get("port"):"";
                String newdatabasename = newDbmap.containsKey("databasename")?newDbmap.get("databasename"):"";
                String dbInitSqlPath = SystemPath.getClassPath();//获得当前工程运行classes目录下dbInitSql目录绝对路径
                String separator= System.getProperty("file.separator");//当前系统文件路径间隔符
                if (!dbInitSqlPath.substring(dbInitSqlPath.length()-1).equals(separator)){
                    dbInitSqlPath += dbInitSqlPath;
                }
                dbInitSqlPath += Globals.DB_INIT_SQL_PATH + separator;
                for (int i=0;i<sourceFilePathArray.length;i++) {
                    if (!sourceFilePathArray[i].trim().equals("")) {
                        //todo:方案二,执行source [sql文件路径],前提要切换数据库连接到新用户下,以上暂支持mysql和oracle，还缺少sql server的数据库复制代码
                        String sourceFile = sourceFilePathArray[i].replace("[ClassPath]",dbInitSqlPath);
                        String impsql = "souce " + sourceFile;//默认mysql,对初始sql路径中含有[ClassPath]替换为当前工程运行classes目录下dbInitSql目录
                        if (dbType.equals(Globals.DB_TYPE_ORACLE)){
                            impsql = disposeSpacePath(dbbinpath + Globals.DB_CONN_EXE_ORACLE) + " " + tSDataSource.getDbUser() + "/" + tSDataSource.getDbPassword() + "@" +  newip + ":" + newport + "/" + newdatabasename + sourceFile;
                            DBUtil.execCmd(impsql, "新用户执行初始数据文件：" + sourceFilePath);
                        }else if (dbType.equals(Globals.DB_TYPE_SQLSERVER)){
                            impsql = disposeSpacePath(dbbinpath + Globals.DB_CONN_EXE_SQLSERVER) + " -i " + sourceFile + " -d " +databaseName + " -s " + newip + " -U " + tSDataSource.getDbUser() + " -P " + tSDataSource.getDbPassword() ;
                            DBUtil.execCmd(impsql, "新用户执行初始数据文件：" + sourceFilePath);
                        }else if (dbType.equals(Globals.DB_TYPE_MYSQL)) {
                            //stmt.executeUpdate(impsql);
                            impsql  = disposeSpacePath(dbbinpath + Globals.DB_CONN_EXE_MYSQL) + " -h " + newip + " -P " + newport + " -u " + tSDataSource.getDbUser();
                            if(tSDataSource.getDbPassword()!=null && !tSDataSource.getDbPassword().equalsIgnoreCase("")){
                                impsql += " -p" +tSDataSource.getDbPassword();
                            }
                            impsql += " -D " + newdatabasename + " <\""  + sourceFile + "\"";
                            DBUtil.execCmd(impsql, "新用户执行初始数据文件：" + sourceFilePath);
                        }
                    }
                }
                //9.建账套后，要往新账套t_s_depart表写入一条部门记录(parentdepartId为null,ORG_CODE为00，ORG_TYPE为0)【待做】
                String initDepartSql = "INSERT INTO t_s_depart (ID, DEPARTNAME, DESCRIPTION, PARENTDEPARTID, ORG_CODE, ORG_TYPE, SHORTNAME, SHORTNUMBER, REGIONID, CONTACT, MOBILEPHONE, " +
                        " PHONE, FAX, ADDRESS, CIQNUMBER, CITY, POSTALCODE, EMAIL, HOMEPAGE, BANK, BANKNUMBER, LICENCE, TRADE, DELETED, NOTE) " +
                        " VALUES (?, ?, ?, NULL, ?, ?, ?, NULL, NULL, NULL, null," +
                        " ?, ?, ?, NULL, NULL, NULL, ?, ?, NULL, ?, '', NULL, '0', NULL)";
                PreparedStatement ps = newconn.prepareStatement(initDepartSql);
                String departId = UUIDGenerator.generate();//replace(uuid(),'-','')
                ps.setString(1, departId);
                ps.setString(2, tScAccountConfig.getCompanyName());
                ps.setString(3, tScAccountConfig.getCompanyName());
                ps.setString(4, "00");
                ps.setString(5, "1");
                ps.setString(6, tScAccountConfig.getCompanyName());
                ps.setString(7, tScAccountConfig.getPhone());
                ps.setString(8, tScAccountConfig.getFax());
                ps.setString(9, tScAccountConfig.getCompanyAddress());
                ps.setString(10, tScAccountConfig.getEmail());
                ps.setString(11, tScAccountConfig.getCompanyUrl());
                ps.setString(12, tScAccountConfig.getBankAccount());
                ps.executeUpdate();
                //TODO 注释原因：新建账套不需要新建账套部门数据，这部分由用户手工新建
                //10.建账套后，要往新账套t_sc_department表插入一个部门记录，其中sonid为t_s_depart表的主部门id
//                initDepartSql = "INSERT INTO t_sc_department (ID, CREATE_NAME, CREATE_BY, CREATE_DATE, UPDATE_NAME, UPDATE_BY, UPDATE_DATE, " +
//                        " NAME, NUMBER, PARENTID, SHORTNUMBER, SHORTNAME, MANAGER, PHONE, FAX, ISCREDITMGR, CREDITAMOUNT, DISABLED, LEVEL, COUNT, NOTE, SONID)" +
//                        " VALUES (replace(uuid(),'-',''), null, null, now(), NULL, NULL, now(), " +
//                        " ?, ?, ?, '', ?, NULL, ?, ?, 0, 0, 0, 0, 0, NULL, ?)";
//                ps = newconn.prepareStatement(initDepartSql);
//                ps.setString(1, tScAccountConfig.getCompanyName());
//                ps.setString(2, "001.0001");
//                ps.setString(3, "10005");
//                ps.setString(4, PinyinUtil.getPinYinHeadChar(tScAccountConfig.getCompanyName()));
//                ps.setString(5, tScAccountConfig.getPhone());
//                ps.setString(6, tScAccountConfig.getFax());
//                ps.setString(7, departId);
//                ps.executeUpdate();
                //11.修改('programmer','scadmin','admin'这三个用户的关联部门表的部门ID为新建部门ID
                initDepartSql = "update t_s_user_org set org_id=? where exists(select 1 from t_s_base_user a where USER_ID=a.id and a.USERNAME in ('programmer','scadmin','admin'))";
                ps = newconn.prepareStatement(initDepartSql);
                ps.setString(1, departId);
                ps.executeUpdate();
                ps.close();
                stmt.close();
            }catch(Exception ee){
                ee.printStackTrace();
                throw new BusinessException("创建新库失败：" + ee.getMessage());
            }finally {
                if (newconn!=null){
                    newconn.close();
                }
            }

            isOk = true;
        }catch(Exception e){
            e.printStackTrace();
            throw new BusinessException("创建新库失败：" + e.getMessage());
        }
        return isOk;
    }

    /**
     * 更新新建账套的系统设置表的账套Id为当前账套ID
     * @param tScAccountConfig
     * @param tSDataSource
     * @return
     */
    public static boolean updateConfigAccountId(TScAccountConfigEntity tScAccountConfig, DynamicDataSourceEntity tSDataSource){
        boolean isOk = false;
        try{
            //改用一个新的jdbc connect连接到新数据库
            java.sql.Connection newconn = null;
            try{
                //1.连接到新数据库的默认数据库或实例
                Class.forName(tSDataSource.getDriverClass());
                String dbType = tSDataSource.getDbType().toLowerCase();
                String mainDB = Globals.DB_MAIN_DB_MYSQL;//默认mysql
                if (dbType.equals(Globals.DB_TYPE_ORACLE)){
                    mainDB = tSDataSource.getDbKey();//oracle是在新用户下创建实例
                }else if (dbType.equals(Globals.DB_TYPE_SQLSERVER)){
                    mainDB = Globals.DB_MAIN_DB_SQLSERVER;
                }
                newconn = DriverManager.getConnection(tSDataSource.getUrl(), tSDataSource.getDbUser(), tSDataSource.getDbPassword());//获取新数据库连接

                //todo:10.建账套后，要往新账套t_s_config表修改accountId
                String initConfigSql = "update t_s_config set accountid=?";
                PreparedStatement ps = newconn.prepareStatement(initConfigSql);
                ps.setString(1, tScAccountConfig.getId());
                ps.executeUpdate();
                ps.close();
            }catch(Exception ee){
                ee.printStackTrace();
                throw new BusinessException("创建新库失败：" + ee.getMessage());
            }finally {
                if (newconn!=null){
                    newconn.close();
                }
            }

            isOk = true;
        }catch(Exception e){
            e.printStackTrace();
            throw new BusinessException("创建新库失败：" + e.getMessage());
        }
        return isOk;
    }
    /**
     * 从当前主库复制（数据表、视图）到目标（新库）数据库中
     * @param tSDataSource
     * @throws Exception
     * @author hjh 2016-9-2
     */
    public static void copyDatabase(DynamicDataSourceEntity tSDataSource, String dbbinpath) throws Exception{
        //2.获得当前连接的db_key
        String dataSourceType = ContextHolderUtils.getSession().getAttribute("dataSourceType").toString();
        String dbKey = dataSourceType;
        //获得当前数据源的连接参数
        DynamicDataSourceEntity dynamicDataSourceEntity = DynamicDataSourceEntity.DynamicDataSourceMap.get(dbKey);
        //3.读取源数据库、目标数据的连接参数，并执行cmd命令进行数据库复制
        String dbType = tSDataSource.getDbType().toLowerCase();
        if (dbType.equals(Globals.DB_TYPE_MYSQL)) {
            copyDatabaseForMysql(tSDataSource, dbbinpath, dynamicDataSourceEntity);
        }else if (dbType.equals(Globals.DB_TYPE_ORACLE)) {
            copyDatabaseForOracle(tSDataSource, dbbinpath, dynamicDataSourceEntity);
        }else if (dbType.equals(Globals.DB_TYPE_SQLSERVER)) {
            copyDatabaseForSqlserver(tSDataSource, dbbinpath, dynamicDataSourceEntity);
        }
    }

    /**
     * mysql版，数据库快速复制
     * @param tSDataSource
     * @param dbbinpath
     * @param dynamicDataSourceEntity
     * @throws Exception
     * @author hjh 2016-9-2
     */
    public static void copyDatabaseForMysql(DynamicDataSourceEntity tSDataSource, String dbbinpath, DynamicDataSourceEntity dynamicDataSourceEntity) throws  Exception{
        //1.读取源数据库、目标数据的连接参数
        //例如： mysqldump -h 192.168.0.245 -P 3306 -u root --default-character-set=utf8 -p964725 scm_dev | mysql -h 192.168.0.245 -P 3306 --default-character-set=utf8 newdb -u root -p964725
        Map<String, String> curDbMap = getDbParameterByUrl(dynamicDataSourceEntity.getUrl());
        Map<String, String> newDbmap = getDbParameterByUrl(tSDataSource.getUrl());
        String curip = curDbMap.containsKey("ip")?curDbMap.get("ip"):"";
        String curport = curDbMap.containsKey("port")?curDbMap.get("port"):"";
        String curdatabasename = curDbMap.containsKey("databasename")?curDbMap.get("databasename"):"";
        String curcharacterEncoding = curDbMap.containsKey("characterEncoding")?curDbMap.get("characterEncoding"):"";
        curcharacterEncoding = curcharacterEncoding.toUpperCase().replace("UTF-8","utf8");
        String newip = newDbmap.containsKey("ip")?newDbmap.get("ip"):"";
        String newport = newDbmap.containsKey("port")?newDbmap.get("port"):"";
        String newdatabasename = newDbmap.containsKey("databasename")?newDbmap.get("databasename"):"";
        String newcharacterEncoding = newDbmap.containsKey("characterEncoding")?newDbmap.get("characterEncoding"):"";
        newcharacterEncoding = newcharacterEncoding.toUpperCase().replace("UTF-8","utf8");
        //2.拼接数据复制命令文件。
        //方案三，从当前主库复制数据到新库：使用mysqldump加管道命令，直接复制数据库-速度最快，问题是在指定导入导出默认字符集前提下表、视图可以复制成功，但函数、存储过程、触发器因存储在information_schema而复制失败
        StringBuffer sb = new StringBuffer(disposeSpacePath(dbbinpath + "mysqldump"));
        sb.append(" -h " + curip + " -P " + curport + " -u " + dynamicDataSourceEntity.getDbUser() );
        if(dynamicDataSourceEntity.getDbPassword()!=null && !dynamicDataSourceEntity.getDbPassword().equalsIgnoreCase("")){
            sb.append(" -p").append(dynamicDataSourceEntity.getDbPassword());
        }
        if(curcharacterEncoding!=null && !curcharacterEncoding.equalsIgnoreCase("")){
            sb.append(" --default-character-set=").append(curcharacterEncoding);
        }
        sb.append(" " + curdatabasename);
        sb.append(" |  "+ disposeSpacePath(dbbinpath + Globals.DB_CONN_EXE_MYSQL) + " ");
        sb.append(" -h " + newip + " -P " + newport + " -u " + tSDataSource.getDbUser() );
        if(tSDataSource.getDbPassword()!=null && !tSDataSource.getDbPassword().equalsIgnoreCase("")){
            sb.append(" -p").append(tSDataSource.getDbPassword());
        }
        if(newcharacterEncoding!=null && !newcharacterEncoding.equalsIgnoreCase("")){
            sb.append(" --default-character-set=").append(newcharacterEncoding);
        }else{
            if(curcharacterEncoding!=null && !curcharacterEncoding.equalsIgnoreCase("")){
                sb.append(" --default-character-set=").append(curcharacterEncoding);
            }
        }
        sb.append(" " + newdatabasename);
        //3.执行cmd命令
        String explain = "数据库复制：源库[\" + dynamicDataSourceEntity.getDbKey() + \"]，新库[\" + tSDataSource.getDbKey() + \"]";
        execCmd(sb.toString(), explain);
    }

    /**
     * 为文件路径中可能存在空格做处理，即前后加双引号
     * @param filePath
     * @return
     * @author hjh 2016-9-12
     */
    public static String disposeSpacePath(String filePath){
        String newfilepath = filePath;
        if (newfilepath!=null && !newfilepath.equals("") && newfilepath.length()>2){
            //方案一，把文件名用双引号括起来，仍会报错：'C:\Program' 不是内部或外部命令，也不是可运行的程序
            if (!newfilepath.substring(0,1).equals("\"")){
                newfilepath =  "\"" + newfilepath;
            }
            if (!newfilepath.substring(newfilepath.length()-1).equals("\"")){
                newfilepath +=  "\"";
            }
            //方案二，把空格转为\"\"
            //newfilepath = newfilepath.replaceAll(" ","\" \"");
        }
        return newfilepath;
    }

    /**
     * Oracle版，源数据导出，导入到新数据库
     * @param tSDataSource
     * @param dbbinpath
     * @param dynamicDataSourceEntity
     * @throws Exception
     * @author hjh 2016-9-2
     */
    public static void copyDatabaseForOracle(DynamicDataSourceEntity tSDataSource, String dbbinpath, DynamicDataSourceEntity dynamicDataSourceEntity) throws Exception{
        //1.读取源数据库、目标数据的连接参数
        //例如： mysqldump -h 192.168.0.245 -P 3306 -u root --default-character-set=utf8 -p964725 scm_dev | mysql -h 192.168.0.245 -P 3306 --default-character-set=utf8 newdb -u root -p964725
        Map<String, String> curDbMap = getDbParameterByUrl(dynamicDataSourceEntity.getUrl());
        Map<String, String> newDbmap = getDbParameterByUrl(tSDataSource.getUrl());
        String curip = curDbMap.containsKey("ip")?curDbMap.get("ip"):"";
        String curport = curDbMap.containsKey("port")?curDbMap.get("port"):"";
        String curdatabasename = curDbMap.containsKey("databasename")?curDbMap.get("databasename"):"";
        String curcharacterEncoding = curDbMap.containsKey("characterEncoding")?curDbMap.get("characterEncoding"):"";
        curcharacterEncoding = curcharacterEncoding.toUpperCase().replace("UTF-8","utf8");
        String newip = newDbmap.containsKey("ip")?newDbmap.get("ip"):"";
        String newport = newDbmap.containsKey("port")?newDbmap.get("port"):"";
        String newdatabasename = newDbmap.containsKey("databasename")?newDbmap.get("databasename"):"";
        String newcharacterEncoding = newDbmap.containsKey("characterEncoding")?newDbmap.get("characterEncoding"):"";
        newcharacterEncoding = newcharacterEncoding.toUpperCase().replace("UTF-8","utf8");
        //2.拼接数据导出命令文件
        StringBuffer sb = new StringBuffer(disposeSpacePath(dbbinpath + "exp"));
        sb.append("  " + dynamicDataSourceEntity.getDbUser() + "/" + dynamicDataSourceEntity.getDbPassword() + "@" + curip + ":" + curport + "/" + curdatabasename);
        sb.append(" file=c:" + File.separator + tSDataSource.getDbUser() + ".dmp owner=(" + dynamicDataSourceEntity.getDbUser() + ")");
        //3.执行cmd数据导出命令
        String explain = "数据库导出：源库[\" + dynamicDataSourceEntity.getDbKey() + \"]，新库[\" + tSDataSource.getDbKey() + \"]";
        execCmd(sb.toString(), explain);
        //4.拼接数据导入命令文件
        sb = new StringBuffer(disposeSpacePath(dbbinpath + "imp"));
        sb.append("  " + tSDataSource.getDbUser() + "/" + tSDataSource.getDbPassword() + "@" +  newip + ":" + newport + "/" + newdatabasename);
        //5.执行cmd数据导入命令
        sb.append(" file=c:" + File.separator + tSDataSource.getDbUser() + ".dmp fromuser=" + dynamicDataSourceEntity.getDbUser() + " touser=" + tSDataSource.getDbUser());
        explain = "数据库导入：源库[\" + dynamicDataSourceEntity.getDbKey() + \"]，新库[\" + tSDataSource.getDbKey() + \"]";
        execCmd(sb.toString(), explain);
    }

    /**
     * Sqlserver版，数据库复制
     * @param tSDataSource
     * @param dbbinpath
     * @param dynamicDataSourceEntity
     * @throws Exception
     * @author hjh 2016-9-2
     */
    public static void copyDatabaseForSqlserver(DynamicDataSourceEntity tSDataSource, String dbbinpath, DynamicDataSourceEntity dynamicDataSourceEntity) throws Exception{
        //1.读取源数据库、目标数据的连接参数
        //例如： mysqldump -h 192.168.0.245 -P 3306 -u root --default-character-set=utf8 -p964725 scm_dev | mysql -h 192.168.0.245 -P 3306 --default-character-set=utf8 newdb -u root -p964725
        Map<String, String> curDbMap = getDbParameterByUrl(dynamicDataSourceEntity.getUrl());
        Map<String, String> newDbmap = getDbParameterByUrl(tSDataSource.getUrl());
        String curip = curDbMap.containsKey("ip")?curDbMap.get("ip"):"";
        String curport = curDbMap.containsKey("port")?curDbMap.get("port"):"";
        String curdatabasename = curDbMap.containsKey("databasename")?curDbMap.get("databasename"):"";
        String curcharacterEncoding = curDbMap.containsKey("characterEncoding")?curDbMap.get("characterEncoding"):"";
        curcharacterEncoding = curcharacterEncoding.toUpperCase().replace("UTF-8","utf8");
        String newip = newDbmap.containsKey("ip")?newDbmap.get("ip"):"";
        String newport = newDbmap.containsKey("port")?newDbmap.get("port"):"";
        String newdatabasename = newDbmap.containsKey("databasename")?newDbmap.get("databasename"):"";
        String newcharacterEncoding = newDbmap.containsKey("characterEncoding")?newDbmap.get("characterEncoding"):"";
        newcharacterEncoding = newcharacterEncoding.toUpperCase().replace("UTF-8","utf8");
        //2.拼接数据备份命令
        String baksql = disposeSpacePath(dbbinpath + Globals.DB_CONN_EXE_SQLSERVER) + " -i   -d master -s " + curip + " -U " + dynamicDataSourceEntity.getDbUser() + " -P " + dynamicDataSourceEntity.getDbPassword()  + " "
                + " -Q \"BACKUP DATABASE " + curdatabasename +" to disk='c:" + File.separator + newdatabasename + ".bak'\"" ;
        //3.执行cmd命令
        String explain = "数据库备份：源库[\" + dynamicDataSourceEntity.getDbKey() + \"]，新库[\" + tSDataSource.getDbKey() + \"]";
        execCmd(baksql, explain);
        //4.拼接数据库恢复命令,todo:要强制恢复,这个方法还有待调试，总提示备份集中的数据库备份与现有的数据库不同
        String restoresql = disposeSpacePath(dbbinpath + Globals.DB_CONN_EXE_SQLSERVER) + " -i   -d master -s " + curip + " -U " + tSDataSource.getDbUser() + " -P " + tSDataSource.getDbPassword()  + " "
                + " -Q \"RESTORE DATABASE " + newdatabasename +" from disk='c:" + File.separator + newdatabasename + ".bak' WITH REPLACE, MOVE '" + newdatabasename + "' TO 'c:" + File.separator + newdatabasename  + ".mdf',"
                +" MOVE '\" + newdatabasename + \"_log' TO 'c:\" + File.separator + newdatabasename  + \".ldf'\"" ;
        //6.执行cmd命令
        explain = "数据库恢复：源库[\" + dynamicDataSourceEntity.getDbKey() + \"]，新库[\" + tSDataSource.getDbKey() + \"]";
        execCmd(restoresql, explain);
    }

    /**
     * 执行cmd命令
     * @param command
     * @param explain
     * @return
     * @throws Exception
     * @author hjh 2016-9-2
     */
    public static void execCmd(String command, String explain) throws Exception{
        //先将命令生成bat文件，再用cmd /c 去执行bat文件,以解决路径带空格问题，bat文件中的路径要用双引号括起来
        //用一个线程执行cmd命令
        //将命令输出到bat文件中
        String filename = writeFile(command, "bat");
        //command = "cmd.exe /C " + command;
        String cmdcommand = "cmd.exe /C " + filename;//执行bat文件
        Process proc = java.lang.Runtime.getRuntime().exec(cmdcommand);
        BufferedInputStream bis = new BufferedInputStream(proc.getErrorStream());
        InputStreamReader inputStreamReader = new InputStreamReader(bis,"GBK");
        BufferedReader br = new BufferedReader(inputStreamReader);
        StringBuffer info = new StringBuffer("执行命令：\n"+command + "\n");
        String line = null;
        LogUtil.info("开始" + explain + "，时间：" + DateUtils.date2Str(new Date(), DateUtils.time_sdf));
        while ( (line = br.readLine()) != null) {
            LogUtil.info(line);
            info.append(line + "\n");
        }
        //todo:测试，先关闭删除；测试后，再恢复。
        //FileUtils.delete(filename);
        writeFile(info.toString(), "txt");
        LogUtil.info("完成\" + explain + \"，时间：\" + DateUtils.date2Str(new Date(), DateUtils.time_sdf)。");
    }

    /**
     * 将内容输出到c盘log目录下时间序列的文件中
     * @param content
     * @param ext
     * @return
     */
    public static String writeFile( String content, String ext){
        String filePath = "c:" + File.separator + "log";
        File file = new File(filePath);
        if (!file.exists()){
            file.mkdir();//目录不存在，则创建
        }
        String filename = filePath + File.separator + DateUtils.date2Str(new Date(), DateUtils.yyyymmddhhmmssSSS) + "." + ext;
        try{
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
        }catch (Exception e){
        }
        return filename;
    }

    /**
     * 从数据库连接url串中解析数据库服务器IP、端口、数据库名称及其它参数
     * @param url
     * @return
     * @author hjh 2016-8-31
     */
    public static Map<String,String> getDbParameterByUrl(String url){
        Map<String,String> dbMap = new HashMap();
        String[] temp1 = url.split("://");//sqlserver或mysql
        if (temp1.length>1){
            String[] temp2 = temp1[1].split(":");
            if (temp2.length>1){
                dbMap.put("ip",temp2[0]);
                String[] temp3 = temp2[1].split("/");
                if (temp3.length>1){
                    dbMap.put("port",temp3[0]);
                    String[] temp4 = temp3[1].split("[?]");//问号是特殊符号，分隔时，要用中括号括起来
                    if (temp4.length>1){
                        dbMap.put("databasename",temp4[0]);
                        String[] temp5 = temp4[1].split("&");
                        for (int i=0;i<temp5.length;i++){
                            String[] temp6 = temp5[i].split("=");
                            if (temp6.length>1) {
                                dbMap.put(temp6[0], temp6[1]);
                            }
                        }
                    }
                }
            }
        }else{//oracle
            temp1 = url.split(":@");
            String[] temp2 = temp1[1].split(":");
            if (temp2.length>1){
                dbMap.put("ip",temp2[0]);
                String[] temp3 = temp2[1].split("/");
                if (temp3.length>1){
                    dbMap.put("port",temp3[0]);
                    String[] temp4 = temp3[1].split("[?]");//问号是特殊符号，分隔时，要用中括号括起来
                    if (temp4.length>1){
                        dbMap.put("databasename",temp4[0]);
                        String[] temp5 = temp4[1].split("&");
                        for (int i=0;i<temp5.length;i++){
                            String[] temp6 = temp5[i].split("=");
                            if (temp6.length>1) {
                                dbMap.put(temp6[0], temp6[1]);
                            }
                        }
                    }
                }
            }
        }
        return dbMap;
    }

    /**
     * 生成指定IP、端口、实例名或数据库名的连接串
     * @param dbType
     * @param ip
     * @param port
     * @param dbName
     * @return
     * @author hjh 2016-9-9
     */
    public static String getDBUrl(String dbType, String ip, String port, String dbName){
        String url = "";
        String dbType2 = dbType.toLowerCase();
        if (dbType2.equals(Globals.DB_TYPE_ORACLE)){
            url = Globals.DB_CONN_URL_ORACLE;
        }else if (dbType2.equals(Globals.DB_TYPE_SQLSERVER)){
            url = Globals.DB_CONN_URL_SQLSERVER;
        }else if (dbType2.equals(Globals.DB_TYPE_MYSQL)){
            url = Globals.DB_CONN_URL_MYSQL;
        }
        url = url.replace("[DB_IP]",ip);
        url = url.replace("[DB_PORT]",port);
        url = url.replace("[DB_NAME]",dbName);
        return url;
    }

    /**
     * 测试数据库连接
     * @param dbType
     * @param ip
     * @param port
     * @param dbName
     * @param userName
     * @param password
     * @return
     * @author hjh 2016-9-9
     */
    public static boolean testConnection(String dbType, String ip, String port, String dbName, String userName, String password){
        boolean isOk = false;
        String dbType2 = dbType.toLowerCase();
        String url = "";
        if (dbType2.equals(Globals.DB_TYPE_SQLSERVER)){
            dbName = Globals.DB_MAIN_DB_SQLSERVER;
        }else if (dbType2.equals(Globals.DB_TYPE_MYSQL)){
            dbName = Globals.DB_MAIN_DB_MYSQL;
        }
        url = getDBUrl(dbType, ip, port, dbName);
        java.sql.Connection newconn = null;
        try{
            String driverClass = "";
            if (dbType2.equals(Globals.DB_TYPE_ORACLE)){
                driverClass = Globals.DB_DRIVER_CLASS_ORACLE;
            }else if (dbType2.equals(Globals.DB_TYPE_SQLSERVER)){
                driverClass = Globals.DB_DRIVER_CLASS_SQLSERVER;
            }else if (dbType2.equals(Globals.DB_TYPE_MYSQL)){
                driverClass = Globals.DB_DRIVER_CLASS_MYSQL;
            }
            Class.forName(driverClass);
            newconn = DriverManager.getConnection(url, userName, password);//获取新数据库连接
            Statement stmt = newconn.createStatement();
            String sql = "SELECT 1";
            if (dbType2.equals(Globals.DB_TYPE_ORACLE)){
                sql = "select 1 from dual";
            }
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()){
                isOk = true;
            }
            rs.close();
            stmt.close();
        }catch(Exception ee){
            ee.printStackTrace();
        }finally {
            try {
                if (newconn != null) {
                    newconn.close();
                }
            }catch (Exception e){

            }
        }
        return isOk;
    }

    /**
     * 检查数据库连接操作exe文件是否存在，即sysConfig.properties配置的dbbinpath路径是否有连接数据库的exe文件
     * @param dbType
     * @return
     * @author hjh 2016-9-9
     */
    public static boolean testDBConnExe(String dbType){
        boolean isOk = false;
        String dbType2 = dbType.toLowerCase();
        String connExe = "";
        if (dbType2.equals(Globals.DB_TYPE_ORACLE)){
            connExe = Globals.DB_CONN_EXE_ORACLE;
        }else if (dbType2.equals(Globals.DB_TYPE_SQLSERVER)){
            connExe = Globals.DB_CONN_EXE_SQLSERVER;
        }else if (dbType2.equals(Globals.DB_TYPE_MYSQL)){
            connExe = Globals.DB_CONN_EXE_MYSQL;
        }
        PropertiesUtil propertiesUtil = new PropertiesUtil("sysConfig.properties");
        String dbbinpath =propertiesUtil.readProperty("dbbinpath");//读取数据库bin目录位置
        dbbinpath = dbbinpath.replace("\\",File.separator);
        if (dbbinpath!=null && !dbbinpath.substring(dbbinpath.length()-1).equals(File.separator)){
            dbbinpath += File.separator;
        }
        File file = new File(dbbinpath + connExe + ".exe");
        if (file.exists()){
            isOk = true;
        }
        return isOk;
    }

    /**
     * 检查数据库文件存放目录是否存在
     * @return
     * @author hjh 2016-9-9
     */
    public static boolean testDBConnDataPath(){
        boolean isOk = false;
        PropertiesUtil propertiesUtil = new PropertiesUtil("sysConfig.properties");
        //再检查数据库文件存放目录是否存在
        String dbdatapath = propertiesUtil.readProperty("dbdatapath");//数据库文件存放位置
        File filePath = new File(dbdatapath);
        if (filePath.exists() && filePath.isDirectory()) {
            isOk = true;
        }
        return isOk;
    }

    public static void main(String[] args) {
		String dburl = "jdbc:mysql://192.168.0.245:3306/scm_dev?useUnicode=true&characterEncoding=UTF-8";
		Map<String, String> dbmap = DBUtil.getDbParameterByUrl(dburl);
		String ip = dbmap.containsKey("ip")?dbmap.get("ip"):"";
		String port = dbmap.containsKey("port")?dbmap.get("port"):"";
		String databasename = dbmap.containsKey("databasename")?dbmap.get("databasename"):"";
		String characterEncoding = dbmap.containsKey("characterEncoding")?dbmap.get("characterEncoding"):"";
		LogUtil.info("ip:" + ip);
		LogUtil.info("port:" + port);
		LogUtil.info("databasename:" + databasename);
		LogUtil.info("characterEncoding:" + characterEncoding);
        boolean isConnect = DBUtil.testConnection("mysql","192.168.0.245","3306","scm","scm","scm");
        boolean isExe = DBUtil.testDBConnExe("mysql");
    }

    /**
     * 按条件到主数据库查询指定账定对应账套信息
     * @param fieldName
     * @param fieldValue
     * @return
     */
    public static DynamicDataSourceEntity getDynamicDataSourceByParameter(String fieldName, String fieldValue){

        //方案一：通过jdbc来查询
        DynamicDataSourceEntity dynamicDataSourceEntity = new DynamicDataSourceEntity();
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
            String sql = "select * from t_s_data_source a where a." + fieldName + "=?";
            PreparedStatement stmt = newconn.prepareStatement(sql);
            stmt.setString(1,fieldValue);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                dynamicDataSourceEntity.setId(rs.getString("id"));
                dynamicDataSourceEntity.setDbKey(rs.getString("db_key"));
                dynamicDataSourceEntity.setDescription(rs.getString("description"));
                dynamicDataSourceEntity.setDriverClass(rs.getString("driver_class"));
                dynamicDataSourceEntity.setUrl(rs.getString("url"));
                dynamicDataSourceEntity.setDbUser(rs.getString("db_user"));
                dynamicDataSourceEntity.setDbPassword(rs.getString("db_password"));
                dynamicDataSourceEntity.setDbType(rs.getString("db_type"));
                dynamicDataSourceEntity.setAccountId(rs.getString("accountid"));
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
        return dynamicDataSourceEntity;
    }

    /**
     * 更新scm主库的t_sc_account_config当前账套的当前期别为指定期别
     * @param stagestartdate
     * @param accountId
     * @return
     * @author hjh 2016-09-26
     */
    public static boolean updateStagestartdateByAccountId(Date stagestartdate, String accountId){
        long num = 0;
        //方案一：通过jdbc来查询
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
            String sql = "update t_sc_account_config set stagestartdate=? where id=?";
            PreparedStatement stmt = newconn.prepareStatement(sql);
            stmt.setDate(1, new java.sql.Date(stagestartdate.getTime()));
            stmt.setString(2,accountId);
            num = stmt.executeUpdate();
            stmt.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (newconn != null) {
                    newconn.close();
                }
            }catch (Exception e){

            }
        }
        return num>0?true:false;
    }
}