/**
 * Created by Administrator on 2017/5/11.
 */
/**
 * 打开窗口 dialog形式------
 * @param winId  列表div ID号
 * @param winId  要渲染成窗口的div ID号
 * @param title  窗口的标题
 * @param width  窗口的宽度
 * @param height 窗口的高度
 * @param height 表单的ID
 * @param flag 该窗口为修改窗口还是添加窗口 save:添加窗口；update：更新窗口
 */
function openWin(listId,winId, title, width, height,formId,flag) {
    $("#"+formId).form("reset");
    $("#" + winId).dialog({
        title: title,
        width: width,
        height: height,
        left:350,
        modal: true,
        iconCls: 'icon-save',
        buttons: [{
            text: '保存',
            iconCls: 'icon-ok',
            handler: function () {
                save(listId,winId,formId,flag);
            }
        }, {
            text: '关闭',
            iconCls: 'icon-cancel',
            handler: function () {
                $("#" + winId).window("close")
            }
        }]
    })
}
/**
 * 选择记录，可以选择多条或者一条
 * * @param 列表Id
 */
function selectRecord(listId) {
    var rowArray;
    var ids = "";
    rowArray = $("#" + listId).datagrid("getChecked");
    if (rowArray.length < 1) {
        return false;
    }
    for (var i = 0; i < rowArray.length; i++) {
        ids = rowArray[i].id + "," + ids;
    }
    ids = ids.substring(0, ids.lastIndexOf(","));
    return ids;
}
/**
 * 获取单列的记录
 * @param listId 列表Id
 * @returns {*}
 */
function getSingleRecord(listId) {
    var rowArray;
    rowArray = $("#" + listId).datagrid("getChecked");
    if (rowArray.length != 1) {
        return false;
    }

    return rowArray[0];
}

/**
 * 删除
 * @param listId
 * @returns
 */
function deleteRecord(listId) {
    var ids = selectRecord(listId);
    if (selectRecord(listId) == false) {
        $.messager.alert("警告", "请选择要操作的记录！", "warning");
        return false;
    }
    if (ids == "" || ids == null) {
        return false;
    }
    $.messager.confirm('确认', '您确认要删除吗？', function (r) {
        if (r) {
            $.ajax({
                type: "POST",
                url: "delete",
                data: {ids: ids},
                timeout: 20000,
                dataType: "json",
                cache: false,
                success: function (data) {
                    if (data.msg == "success") {
                        $.messager.alert("提示", "删除成功！", "info", function () {
                            $('#'+listId).datagrid("reload");
                        })
                    } else {
                        $.messager.alert("错误", "删除失败，系统中存在对该记录的的引用！", "error", function () {

                        })
                    }
                }
            })
        }
    });
}
/**
 *
 * 添加和修改方法----根据flag来判断
 */
function save(listId,winId,formId,flag) {
    if(flag=="save"){
        if ($("#"+formId).form("validate")) {
            $.messager.confirm('确认', '您确认要添加该记录吗？', function (r) {
                if (r) {
                    $.ajax({
                        type: "POST",
                        url: "add",
                        data: $("#"+formId).serialize(),
                        timeout: 20000,
                        dataType: "json",
                        cache: false,
                        success: function (data) {
                            if (data.msg == "success") {
                                $.messager.alert("提示", "添加成功！", "info", function () {
                                    $('#'+listId).datagrid("reload");
                                    $('#'+winId).window("close");
                                    $('#'+formId).form("reset");
                                })
                            } else {
                                $.messager.alert("错误", "添加失败！", "error", function () {
                                })
                            }
                        }
                    })
                }
            });
        }
    }else if(flag=="update"){
        if ($("#"+formId).form("validate")) {
            $.messager.confirm('确认', '您确认要修改吗？', function (r) {
                if (r) {
                    $.ajax({
                        type: "POST",
                        url: "add",
                        data: $("#"+formId).serialize(),
                        timeout: 20000,
                        dataType: "json",
                        cache: false,
                        success: function (data) {
                            if (data.msg == "success") {
                                $.messager.alert("提示", "修改成功！", "info", function () {
                                    $('#'+listId).datagrid("reload");
                                    $('#'+winId).window("close");
                                })
                            } else {
                                $.messager.alert("错误", "修改失败！", "error", function () {

                                })
                            }
                        }
                    })
                }
            });
        }
    }
}