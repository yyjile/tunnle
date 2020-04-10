/**
 * Created by mj on 2017/3/30.
 */
/**
 * 返回
 */
$(function () {
    $("#goBack").click(function () {
        window.location.href = "user_index.html";
    })
});
/**
 * 打开角色列表
 */
$(function () {
    $("#openRoleList").click(function () {
        $("#roleWin").window("open");
        //初始化用户列表
        $('#roleList').datagrid({
            url: "roleFinAllWithPaging",
            method: "post",
            striped: true,
            fitColumns: true,//是否自动填充适合屏幕
            pagination: true,//是否显示底部工具栏
            nowrap: true,
            displayMsg: "",
            pageSize: 8,
            pageList: [8, 16, 24],
            columns: [[
                {field: 'id', title: 'select', checkbox: true, align: 'center'},
                {field: 'roleName', title: '角色名', sortable: true, width: 100, align: "center"},
                {field: 'roleDescription', title: '描述', width: 100, align: "center"},
            ]],
        });
    })
});
/**
 * 显示角色列表
 */
function getRoles() {
    //获取用户选中的角色
    var selRows = $('#roleList').datagrid('getChecked');
    var ids = "";
    var i = 0;
    $("#roleNames").val("");
    while (i != selRows.length) {
        ids = selRows[i].id + "," + ids;
        $("#roleNames").val(selRows[i].roleName + "," + $("#roleNames").val());
        i++;
    }
    $("#roleNames").val($("#roleNames").val().substring(0,$("#roleNames").val().length-1));
    $("#roleIds").val(ids);
    $("#roleWin").window("close");
}
/**
 * 返回
 */
function cancel() {
    $("#roleWin").window("close");
}
/**
 * 确认更新用户信息
 */
$(function () {
    $("#update").click(function () {
        //角色不能为空
       if ($("#roleNames").val() == null || $("#roleNames").val() == $("#userPassword").val()) {
            $.messager.alert("警告", "请为用户添加一个角色");
            return;
        }
        if($("#editForm").form('validate')){
            //询问用户是否需要修改信息
            $.messager.confirm('确认', '您确认想要修改记录吗？', function (r) {
                if (r) {
                    $.ajax({
                        type: "POST",
                        url: "update",
                        data: $("#editForm").serialize(),
                        dataType: "json",
                        success: function (data) {
                            if (data.msg == "success") {
                                $.messager.alert("提示", "修改成功！","info",function () {
                                    window.history.back(-2);
                                })
                            } else {
                                $.messager.alert("提示", "修改失败！","info",function () {
                                    window.history.back(-2);
                                })
                            }
                        },
                        error: function () {
                            $.messager.alert("提示", "出现了不可预知的错误，请刷新重试！");
                        }
                    })
                }
            });
        }
    })
});