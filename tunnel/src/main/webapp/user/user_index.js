/**
 * Created by mj on 2017/3/30.
 */
$(function () {
    //初始化用户列表
    $('#userList').datagrid({
        url: "/users/getAllUsers",
        method: "post",
        title: "用户列表",
        striped: true,
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
            {field: 'userName', title: '姓名', sortable: true, width: 100, align: "center"},
            {
                field: 'userAvailable',
                title: '状态',
                sortable: true,
                width: 100,
                align: 'center',
                formatter: function (value, row, index) {
                    if (value == 0) {
                        return "<span style='color: green;'>启 用</span>";
                    } else {
                        return "<span style='color: red;'>停 用</span>";
                    }
                }
            }
        ]],
        toolbar: [
            {
                text: '增加', iconCls: 'icon-add', handler: function () {
                openAddWin();
            }
            }, '-',
            {
                text: '编辑', iconCls: 'icon-edit', handler: function () {
                editUser();
            }
            }, '-',
            {
                text: '删除', iconCls: 'icon-remove', handler: function () {
                deleteRecord("userList");
            }
            }, '-',
            {
                text: '查看', handler: function () {
            }
            }, '-',
            {
                text: '刷新', iconCls: 'icon-reload', handler: function () {
                $('#userList').datagrid("reload");
            }
            }, '-',
            {
                text: '启用/停用', iconCls: 'icon-user-edit', handler: function () {
                changeStatus();
            }
            },
            '-'],
    });
    $('#userList').datagrid('getPager').pagination({//分页栏下方文字显示
        beforePageText: '第',
        afterPageText: '页，总共 {pages}页',
        displayMsg: '当前显示从第{from}条到{to}条 共{total}条记录',
    });
});
//打开添加用户窗口
function openAddWin() {
    $("#addUserWin").show().window("open");
}
//确认新添用户
function addUser() {
    if ($("#addUserForm").form('validate')) {
        $.messager.confirm('确认', '您确认添加吗？', function (r) {
            if (r) {
                $.ajax({
                    type: "POST",
                    url: "/users/saveUser",
                    data: $("#addUserForm").serialize(),
                    timeout: 20000,
                    dataType: "json",
                    cache: false,
                    success: function (data) {
                        if (data.msg == "success") {
                            $.messager.alert("提示", "添加成功！","info",function () {
                                window.location.reload();
                            });
                        } else {
                            $.messager.alert("警告", "添加失败！","info",function () {
                                window.location.reload();
                            })
                        }
                    },
                    error: function () {
                        $.messager.alert("提示", "出现了不可预知的错误，请重新操作！");
                    }
                })
            }
        });
    }
}
//取消添加用户
function cancelAdd() {
    //重置表单
    // $("#userName").val("");
    $("#addUserWin").window("close");
}
//修改用户信息
function editUser() {
    if(!$.getSingleRecord("userList")){
        return;
    }
    var id= $.getSingleRecord("userList").id;
    window.location.href = "toUpdate?id=" + id;
}
/**
 * 改变用户状态
 */
function changeStatus() {
    if(!$.getSingleRecord("userList")){
        return;
    }
    var id= $.getSingleRecord("userList").id;
    $.messager.confirm('确认', '您确认要改变该用户的状态吗？', function (r) {
        if (r) {
            $.ajax({
                type: "POST",
                url: "changeStatus",
                data: {id:id},
                timeout: 20000,
                dataType: "json",
                cache: false,
                success: function (data) {
                    if (data.msg == "success") {
                        $.messager.alert("提示", "状态改变成功！","info",function () {
                            $('#userList').datagrid("reload");
                        })
                    } else {
                        $.messager.alert("提示", "操作失败！","error",function () {
                            $('#userList').datagrid("reload");
                        })
                    }
                }
            })
        }
    });
}
