$(function(){
    createDataGridT_S_Bill_Info();
    $('#T_S_Bill_InfoList').datagrid({

        onDblClickRow: function (rowIndex, rowData) {
            url = 'tSBillInfoController.do?goUpdate' + '&load=detail&id=' + rowData.id;
            createdetailwindow('查看', url, null, null);

        }

    });
})

function createDataGridT_S_Bill_Info() {
    var initUrl = 'tSBillInfoController.do?datagrid&field=id,createName,createBy,createDate,updateName,updateBy,updateDate,billName,billId,pid,prefix,dateFormatter,billNoLen,isEdit,isOffOn,backZero,billNo';
    initUrl = encodeURI(initUrl);
    $('#T_S_Bill_InfoList').treegrid(
        {
            treeField: 'text',
            url: initUrl,
            idField: 'id', treeField: "billName",
            title: '单据类型设置',
            fit: true,
            fitColumns: true,
            striped: true,
            autoRowHeight: true,
            pageSize: 10,
            pageList: [10, 30, 50, 100],
            pagination: true,
            singleSelect: true,
            sortName: 'createDate',

            sortOrder: 'desc',
            rownumbers: true,
            showFooter: true,
            frozenColumns: [[]],
            columns: [
                [
                    {field: "id", title: "主键", hidden: true},


                    {
                        field: "createName",
                        title: "创建人名称",
                        hidden: true,
                        width: 100
                    },

                    {
                        field: "createBy",
                        title: "创建人登录名称",
                        hidden: true,
                        width: 100
                    },

                    {
                        field: "createDate",
                        title: "创建日期",
                        hidden: true,
                        width: 100
                    },

                    {
                        field: "updateName",
                        title: "更新人名称",
                        hidden: true,
                        width: 100
                    },

                    {
                        field: "updateBy",
                        title: "更新人登录名称",
                        hidden: true,
                        width: 100
                    },

                    {
                        field: "updateDate",
                        title: "更新日期",
                        hidden: true,
                        width: 100
                    },

                    {
                        field: "billName",
                        title: "单据名称",

                        width: 100
                    },

                    {
                        field: "billId",
                        title: "单据类型",

                        width: 100
                    },

                    {
                        field: "pid",
                        title: "父id",
                        hidden: true,
                        width: 100
                    },

                    {
                        field: "prefix",
                        title: "前缀",

                        width: 100
                    },

                    {
                        field: "dateFormatter",
                        title: "日期格式",

                        width: 100
                    },

                    {
                        field: "billNoLen",
                        title: "流水号长度",

                        width: 100
                    },

                    {
                        field: "isEdit",
                        title: "允许手工编辑单据号",

                        width: 100
                    },

                    {
                        field: "isOffOn",
                        title: "允许断号",

                        width: 100
                    },

                    {
                        field: "backZero",
                        title: "流水号归零",

                        width: 100
                    },

                    {
                        field: "billNo",
                        title: "单据数量",
                        hidden: true,
                        width: 100
                    },
                    {
                        field: 'opt', title: '操作', width: 200, formatter: function (value, rec, index) {
                        if (!rec.id) {
                            return '';
                        }
                        var href = '';
                        if(rec.pid!=null) {
                            href += "[<a href='#' onclick=delObj('tSBillInfoController.do?doDel&id="+rec.id+"','T_S_Bill_InfoList','"+rec.id+"')>";
                            href += "删除</a>]";
                        }
                        return href;
                    }
                    }
                ]
            ],
            onLoadSuccess: function (data) {
                $("#T_S_Bill_InfoList").treegrid("clearSelections");
                setTimeout(function(){$('#T_S_Bill_InfoList').treegrid('expandAll');},300);

            },
            onClickRow: function (rowIndex, rowData) {
                rowid = rowData.id;
                gridname = 'T_S_Bill_InfoList';
            }
        });
    $('#T_S_Bill_InfoList').treegrid('getPager').pagination({
        beforePageText: '',
        afterPageText: '/{pages}',
        displayMsg: '{from}-{to}共{total}条',
        showPageList: true,
        showRefresh: true
    });
    $('#T_S_Bill_InfoList').treegrid('getPager').pagination({
        onBeforeRefresh: function (pageNumber, pageSize) {
            $(this).pagination('loading');
            $(this).pagination('loaded');
        }
    });
    //将没有权限的按钮屏蔽掉
}
//列表刷新
function reloadTable() {
    try {
        $('#' + gridname).treegrid('reload');
    } catch (ex) {
        //donothing
    }
}
//列表刷新-推荐使用
function reloadT_S_Bill_InfoList() {
    $('#T_S_Bill_InfoList').treegrid('reload');
}

//得到树形表单选中行
function getTreeGridSelection(){
    return $('#T_S_Bill_InfoList').treegrid('getSelected');
}

//展开树形表单
function expandNode(){
    $('#T_S_Bill_InfoList').treegrid('expandAll');
}
/**
 * 获取列表中选中行的数据-推荐使用
 * @param field 数据中字段名
 * @return 选中行的给定字段值
 */
function getT_S_Bill_InfoListSelected(field) {
    var row = $('#T_S_Bill_InfoList').treegrid('getSelected');
    if (row != null) {
        value = row[field];
    } else {
        value = '';
    }
    return value;
}
/**
 * 获取列表中选中行的数据
 * @param field 数据中字段名
 * @return 选中行的给定字段值
 */
function getSelected(field) {
    var row = $('#' + gridname).treegrid('getSelected');
    if (row != null) {
        value = row[field];
    } else {
        value = '';
    }
    return value;
}
/**
 * 获取列表中选中行的数据（多行）
 * @param field 数据中字段名-不传此参数则获取全部数据
 * @return 选中行的给定字段值，以逗号分隔
 */
function getT_S_Bill_InfoListSelections(field) {
    var ids = '';
    var rows = $('#T_S_Bill_InfoList').treegrid('getSelections');
    for (var i = 0; i < rows.length; i++) {
        ids += rows[i][field];
        ids += ',';
    }
    ids = ids.substring(0, ids.length - 1);
    return ids;
}
/**
 * 列表查询
 */
function T_S_Bill_InfoListsearch() {
    var queryParams = $('#T_S_Bill_InfoList').treegrid('options').queryParams;
    $('#T_S_Bill_InfoListtb').find('*').each(
        function () {
            queryParams[$(this).attr('name')] = $(this).val();
        });
    queryParams['searchbutton']='1';
    $('#T_S_Bill_InfoList').treegrid({
        url: 'tSBillInfoController.do?datagrid',
        pageNumber: 1
    });
}
function dosearch(params) {
    var jsonparams = $.parseJSON(params);
    $('#T_S_Bill_InfoList').treegrid({
        url: 'tSBillInfoController.do?datagrid',
        queryParams: jsonparams
    });
}
function T_S_Bill_InfoListsearchbox(value, name) {
    var queryParams = $('#T_S_Bill_InfoList').treegrid('options').queryParams;
    queryParams[name] = value;
    queryParams.searchfield = name;
    $('#T_S_Bill_InfoList').treegrid('reload');
}
$('#T_S_Bill_InfoListsearchbox').searchbox({
    searcher: function (value, name) {
        T_S_Bill_InfoListsearchbox(value, name);
    },
    menu: '#T_S_Bill_InfoListmm',
    prompt: '请输入查询关键字'
});
//查询重置
function T_S_Bill_InfosearchReset(name) {
    $("#" + name + "tb").find("input[type!='hidden']").val("");
    //为树形表单时，删除id查询参数
    delete $('#T_S_Bill_InfoList').treegrid('options').queryParams.id;
    T_S_Bill_InfoListsearch();
}
//将字段href中的变量替换掉
function applyHref(tabname, href, value, rec, index) {
    //addOneTab(tabname,href);
    var hrefnew = href;
    var re = "";
    var p1 = /\#\{(\w+)\}/g;
    try {
        var vars = hrefnew.match(p1);
        for (var i = 0; i < vars.length; i++) {
            var keyt = vars[i];
            var p2 = /\#\{(\w+)\}/g;
            var key = p2.exec(keyt);
            hrefnew = hrefnew.replace(keyt, rec[key[1]]);
        }
    } catch (ex) {
    }
    re += "<a href = '#' onclick=\"addOneTab('" + tabname + "','" + hrefnew + "')\" ><u>" + value + "</u></a>";
    return re;
}
//SQL增强入口-按钮
function doBusButton(url, content, gridname) {
    var rowData = $('#' + gridname).datagrid('getSelected');
    if (!rowData) {
        tip('请选择一条信息');
        return;
    }
    url = url + '&id=' + rowData.id;
    createdialog('确认 ', '确定' + content + '吗 ?', url, gridname);
}
//SQL增强入口-操作列里的链接
function doBusButtonForLink(url, content, gridname, rowData) {
    if (!rowData) {
        tip('请选择一条信息');
        return;
    }
    url = url + '&id=' + rowData;
    createdialog('确认 ', '确定' + content + '吗 ?', url, gridname);
}
//新增
function T_S_Bill_Infoadd() {
    var url = 'tSBillInfoController.do?goAdd'
    add('单据类型设置录入', url, 'T_S_Bill_InfoList', T_S_Bill_InfoFw, T_S_Bill_InfoFh, 'dialog');
}
//修改
function T_S_Bill_Infoupdate() {
    var url = 'tSBillInfoController.do?goUpdate'
    update('单据类型设置编辑', url, 'T_S_Bill_InfoList', T_S_Bill_InfoFw, T_S_Bill_InfoFh, 'dialog');
}
//查看
function T_S_Bill_Infoview() {
    detail('查看', 'tSBillInfoController.do?goUpdate', 'T_S_Bill_InfoList', T_S_Bill_InfoFw, T_S_Bill_InfoFh);
}
////批量删除
//function T_S_Bill_InfodelBatch() {
//    //获取选中的ID串
//    var ids = getT_S_Bill_InfoListSelections('id');
//    if (ids.length <= 0) {
//        tip('请选择至少一条信息');
//        return;
//    }
//    $.dialog.confirm('确定删除吗?', function (r) {
//            if (!r) {
//                return;
//            }
//            $.ajax({
//                url: "cgAutoListController.do?delBatch",
//                data: {'ids': ids, 'configId': 'T_S_Bill_Info'},
//                type: "Post",
//                dataType: "json",
//                success: function (data) {
//                    tip(data.msg);
//                    reloadT_S_Bill_InfoList();
//                },
//                error: function (data) {
//                    $.messager.alert('错误', data.msg);
//                }
//            });
//        }
//    );
//}

//function T_S_Bill_InfoExportExcel() {
//    var queryParams = $('#T_S_Bill_InfoList').datagrid('options').queryParams;
//    $('#T_S_Bill_InfoListtb').find('*').each(function () {
//        //Zerrion 2015-10-08 发现BUG,当表单查询模式为single时,查询控件中有一提示值‘请输入关键字’
//        //导致excel导出时会将该值作为查询条件过滤数据
//        if ($(this).val() == '请输入关键字') {
//            return true;
//        } else {
//            queryParams[$(this).attr('name')] = $(this).val();
//        }
//    });
//    var params = '&';
//    $.each(queryParams, function (key, val) {
//        params += '&' + key + '=' + val;
//    });
//    var fields = '&field=';
//    $.each($('#T_S_Bill_InfoList').datagrid('options').columns[0], function (i, val) {
//        if (val.field != 'opt' && val.field != 'ck') {
//            fields += val.field + ',';
//        }
//    });
//    window.location.href = "excelTempletController.do?exportXls&tableName=T_S_Bill_Info" + encodeURI(params + fields)
//}
//JS增强