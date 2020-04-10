/**
 * Created by mj on 2017/3/30.
 */
/**
 *
 * 初始化角色列表
 */
$(function () {
    $('#roleList').datagrid({
        url: "finAllWithPaging",
        method: "post",
        title: "角色列表",
        rownumbers: true,
        loadMsg: "努力加载中...",
        fit: true,
        fitColumns: true,//是否自动填充适合屏幕
        pagination: true,//是否显示底部分页工具栏
        pageSize: 10,
        pageList: [10, 15, 20, 25],
        showFooter: true,
        autoRowHeight: false,
        columns: [[
            {field: 'id', title: 'select', checkbox: true, align: 'center'},
            {field: 'roleName', title: '角色名', sortable: true, width: 100, align: "center"},
            {field: 'roleDescription', title: '描述', sortable: true, width: 100, align: 'center'},
            {
                field: 'roleAvailable',
                title: '状态',
                sortable: true,
                width: 100,
                align: 'center',
                formatter: function (value, row, index) {
                    if (value == 0) {
                        return "<span style='color: green;'>激活</span>";
                    } else {
                        return "<span style='color: red;'>停用</span>";
                    }
                }
            }
        ]],
        toolbar: [
            {
                text: '增加', iconCls: 'icon-add', handler: function () {
                openRoleWin("RoleWin", "添加角色", "450", "250", "Btn");
            }
            }, '-',
            {
                text: ' 编辑', iconCls: 'icon-edit', handler: function () {
                editRole()
            }
            }, '-',
            {
                text: ' 删除', iconCls: 'icon-remove', handler: function () {
                deleteRecord("roleList")
            }
            }, '-',
            {
                text: ' 修改角色权限', handler: function () {
                setFun();
            }
            }, '-',
            {
                text: ' 改变角色状态', handler: function () {
                changeRoleStatus();
            }
            }, '-',
            {
                text: ' 刷新', iconCls: 'icon-reload', handler: function () {
                $("#roleList").datagrid('reload');
            }
            }, '-',
            {
                text: ' 导出', iconCls: 'icon-save', handler: function () {
            }
            },
            '-']
    });
    $('#roleList').datagrid('getPager').pagination({//分页栏下方文字显示
        beforePageText: '第',
        afterPageText: '页，总共 {pages}页',
        displayMsg: '当前显示从第{from}条到{to}条 共{total}条记录',
    });
});
/**
 *
 * 弹窗
 *
 */
function openRoleWin(winId, title, width, height, btnId) {
    $("#" + winId).dialog({
        title: title,
        width: width,
        height: height,
        modal: true,
        iconCls: 'icon-save',
        buttons: "#" + btnId,
    })
}
/**
 *
 * 改变角色状态
 */
function changeRoleStatus() {
    if (!$.getSingleRecord("roleList")) {
        return;
    }
    var id = $.getSingleRecord("roleList").id;
    $.messager.confirm("提 示", "确认要改变所选择的角色的状态？", function (r) {
        if (r) {
            $.ajax({
                url: "changeRoleStatus",
                data: {roleIds: id},
                type: "post",
                dataType: "json",
                success: function (data) {
                    if (data.msg == "success") {
                        $.messager.alert("提示", "修改成功！")
                    } else {
                        $.messager.alert("提示", "修改失败！")
                    }
                    $('#roleList').datagrid("reload");
                },
                error: function (data) {
                    alert("ss")
                }
            })

        }
    })
}
/**
 *
 * 权限赋值
 */
function setFun() {
    if (!$.getSingleRecord("roleList")) {
        return;
    }
    var id = $.getSingleRecord("roleList").id;
    $("#roleId").val(id);
    $("#roleResourceWin").show().window("open");
    //树表格
    $("#treeData").treegrid({
        url: "queryResourceByRoleId?id=" + id,
        idField: 'id',
        treeField: 'resourceName',
        loadMsg: "努力加载中...",
        fit: true,
        singleSelect: false,
        rownumbers: true,
        columns: [[
            {
                field: 'resourceName',
                title: '权限',
                width: 180,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.selected) {
                        return '<input type="checkbox" onclick="caseCheck(' + row.id + ',' + '\'treeData\'' + ')"  id="' + row.id + '" name="rowCheck" checked="checked" value="' + row.id + '"/>' + row.resourceName;
                    } else {
                        return '<input type="checkbox" onclick="caseCheck(' + row.id + ',' + '\'treeData\'' + ')"  id="' + row.id + '" name="rowCheck" value="' + row.id + '"/>' + row.resourceName;
                    }
                }
            },
            {field: 'resourceType', title: '资源类型', width: 200, align: 'center'}
        ]],
        toolbar: [
            {
                text: ' 保 存', iconCls: 'icon-save', handler: function () {
                confirmSetMenu();
            }
            }, '-',
            {
                text: '取 消', iconCls: 'icon-cancel', handler: function () {
                quitSetMenu();
            }
            },
            '-'],
    });
}
/**
 * 取消赋权限
 */
function quitSetMenu() {
    $.messager.confirm("询问", "您确定取消权限设置吗？", function (r) {
        if (r) {
            $("#roleResourceWin").window("close");
        }
    });
}
/**
 *
 * 确定设置权限
 */
function confirmSetMenu() {
    $.messager.confirm("询问", "您确定要为此角色设置权限？", function (r) {
        if (r) {
            var ids = "";
            $('input[name="rowCheck"]:checked').each(function () {
                //alert($(this).val())
                ids = $(this).val() + "," + ids;
            });
            ids = ids.substring(0, ids.length - 1);
            $.ajax({
                url: "setPerssionForRole",
                data: {roleId: $("#roleId").val(), ids: ids},
                type: "post",
                dataType: "json",
                success: function (data) {
                    if (data.msg == "success") {
                        $.messager.alert("提示", "权限设置成功！", "info", function () {
                            $("#roleResourceWin").window("close");
                        })
                    } else {
                        $.messager.alert("提示", "权限设置失败！", "error")
                    }
                }
            })
        }
    });
}
/**
 * Created by qzy on 2017/4/14.
 */
function caseCheck(checkid, treegrid) {
    var node = '#' + checkid;
    /*选子节点*/
    var nodes = $("#" + treegrid).treegrid("getChildren", checkid);
    for (i = 0; i < nodes.length; i++) {
        $(('#' + nodes[i].id))[0].checked = $(node)[0].checked;
    }
    //选上级节点
    if (!$(node)[0].checked) {//选中的时候触发，取消选中则不触发
        var parent = $("#" + treegrid).treegrid("getParent", checkid);
        var flag = true;
        if (parent) {
            var sons = $("#" + treegrid).treegrid("getChildren", parent.id);
            for (var i = 0; i < sons.length; i++) {
                if ($(('#' + sons[i].id))[0].checked) {
                    flag = false;
                    break;
                }
            }
        } else {
            return;
        }
        if (flag) {
            $(('#' + parent.id))[0].checked = false;
        }
    } else {//取消选中时
        var parent = $("#" + treegrid).treegrid("getParent", checkid);
        var flag = true;
        if (!parent)
            return;
        $(('#' + parent.id))[0].checked = true;
        var sons = $("#" + treegrid).treegrid("getChildren", parent.id);
        for (j = 0; j < sons.length; j++) {
            if (!$(('#' + sons[j].id))[0].checked) {
                flag = false;
                break;
            }
        }
        if (flag)
            $(('#' + parent.id))[0].checked = true;
        while (flag) {
            parent = $("#" + treegrid).treegrid("getParent", parent.id);
            if (parent) {
                var sons = $("#" + treegrid).treegrid("getChildren", parent.id);
                for (j = 0; j < sons.length; j++) {
                    if (!$(('#' + sons[j].id))[0].checked) {
                        flag = false;
                        break;
                    }
                }
            } else {
                return;
            }
            if (flag)
                $(('#' + parent.id))[0].checked = true;
        }
    }
}