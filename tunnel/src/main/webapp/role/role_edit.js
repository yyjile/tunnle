/**
 *
 * 修改角色
 */
function editRole() {
    //先重置表单
    $("#editRoleForm").form("reset");
    if (!$.getSingleRecord("roleList")) {
        return;
    }
    var row = $.getSingleRecord("roleList");
    if (row.roleAvailable == 0) {
        $("#editRoleAvailable").switchbutton({
            checked: true,
            value: 0,
            onChange: function (checked) {
                if (checked) {
                    $("#editRoleAvailable").switchbutton("setValue", 1);
                }
            }
        });
    } else {
        $("#editRoleAvailable").switchbutton({
            checked: false,
            value: 1,
            onChange: function (checked) {
                if (checked) {
                    $("#editRoleAvailable").switchbutton("setValue", 0);
                }
            }
        });
    }
    $("#editId").val(row.id);
    $("#editRoleName").textbox("setValue", row.roleName);
    $("#editRoleDescription").textbox("setValue", row.roleDescription);
    $("#EditRoleWin").dialog({
        title: "修改角色",
        width: 400,
        height: 400,
        model: true,
        buttons: [{
            text: '保存', iconCls: 'icon-save', handler: function () {
                $.messager.confirm("提示","您确认修改该角色吗？",function (r) {
                    if(r){
                        confirm();
                    }
                })
            }
        }, {
            text: '取消', iconCls: 'icon-cancel', handler: function () {
                $("#EditRoleWin").window("close");
            }
        }],
    })
}
/**
 * 确认修改
 */
function confirm() {
    $('#editRoleForm').form('submit', {
        url: "updateRole",
        onSubmit: function () {
            if (!$("#editRoleForm").form("validate")) {
                return false;
            }
        },
        success: function (data) {
            //把json数据转换成 javascript object
            var jsonObject = JSON.parse(data);
            if (jsonObject.msg == "success") {
                $.messager.alert("提示", "修改成功！", "info", function () {
                    $("#EditRoleWin").window("close");
                    $("#roleList").datagrid("reload");
                })
            } else {
                $.messager.alert("提示", "修改失败！", "error", function () {
                    $("#EditRoleWin").window("close");
                    $("#roleList").datagrid("reload");
                })
            }
        },
        error: function (data) {
            $.messager.alert("警告", "您没有权限！", "error")
        }
    });
}
